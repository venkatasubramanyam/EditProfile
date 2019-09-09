//init required
const functions = require('firebase-functions');
const admin = require('firebase-admin');
const gcm = require('@google-cloud/storage');

//init app
admin.initializeApp();

//init services
const db = admin.firestore()
const storage = admin.storage()

//init service paths
const user_collection = db.collection("user")
const bucket = storage.bucket('gs://my-project-1497021209086.appspot.com')

//--------------------- functions -----------------------------

//On user creation, trigger: Add information to user
exports.onUserCreation = functions.auth.user().onCreate((user) => {

	const userid = user.uid
	var usernick = user.displayName

	if(usernick === null){
		usernick = "unknown"
	}

    return user_collection.doc(userid).set({
				nick: usernick,
				photo: null,
				displayName:"",
				realName:"",
				gender:"",
				ethnicity:"",
				religion:"",
				figure:"",
				maritalStatus:"",
				occupation:"",
				aboutMe:"",
				location:""
				
    })

})

//On user deletion, trigger: Delete information from /user and image from storage
exports.onUserDeletion = functions.auth.user().onDelete((user) => {

	const userid = user.uid

	return user_collection.doc(userid).delete()

})

//trigger to delete photo on user deleted
exports.deleteUser = functions.firestore.document('user/{userID}').onDelete((snap, context) => {

	const photoName = snap.data().photo;
	const filePath = `user_photo/${photoName}`
	const file = bucket.file(filePath)

	return file.delete()

});

exports.onUserNickUpdate = functions.firestore.document('user/{userid}').onWrite((change,context) => {

	const changednick = change.after.data().nick
	const userid = context.params.userid

	return user_collection.doc(userid).update({
		nick: changednick
	})

})

//update profile data
exports.onUserNickUpdate = functions.firestore.document('user/{userid}').onWrite((change,context) => {

	const display_name = change.after.data().displayName
	const real_name = change.after.data().realName
	const genderC = change.after.data().gender
	const ethnicityC = change.after.data().ethnicity
	const religionC = change.after.data().religion
	const figureC = change.after.data().figure
	const marital_status = change.after.data().maritalStatus
	const occupationC = context.params.occupation
	const about_me = context.params.aboutMe
	const locationC = context.params.location

	return user_collection.doc(userid).update({
		displayName: display_name,
				realName:real_name,
				gender:genderC,
				ethnicity:ethnicityC,
				religion:religionC,
				figure:figureC,
				maritalStatus:marital_status,
				occupation:occupationC,
				aboutMe:about_me,
				location:locationC
	})

})


//when photo uploaded
exports.onUserPhotoUpload = functions.storage.object().onFinalize(async (object) => {

																																// this below obvisouly will only work in case of foldername == user_photo
	const filePath = object.name;																	//this is something like user_photo/
	const fileNameWithExtension = filePath.substring(11) 					//remove user_photo/ from const filepath
	const user = fileNameWithExtension.replace(/\.[^/.]+$/, "") 	//remove extension from const fileNameWithExtension
	const folder = filePath.substring(0, 11) 											// get user_photo from const filePath

	console.log(`path: ${filePath} and fileNameWithExtension: ${fileNameWithExtension} and user: ${user} and folder ${folder} `)

	if(folder.startsWith('user_photo/')){
		console.log('folder start with user_photo/')
		return user_collection.doc(user).update({
			photo: fileNameWithExtension
		})
	}else{
		return null
	}

});
