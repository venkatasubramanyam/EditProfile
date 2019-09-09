package com.sparknetwork.editprofile.repository.firebase;

import android.annotation.SuppressLint;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sparknetwork.editprofile.entity.CitiesListItem;
import com.sparknetwork.editprofile.entity.ListItem;
import com.sparknetwork.editprofile.entity.UserProfile;
import com.sparknetwork.editprofile.repository.firebase.rxwrapper.FirebaseDBWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class FirebaseDatabase implements FirebaseDatabaseType {

    private final FirebaseFirestore db;
    private static final String USER = "user";
    private static final String GENDER = "gender";
    private static final String MARITAL_STATUS = "marital_status";
    private static final String MARITAL_STATUS_DATA = "maritalStatus";
    private static final String RELIGION = "religion";
    private static final String ETHNICITY = "ethnicity";
    private static final String FIGURE = "figure";
    private static final String CITIES = "cities";
    private static final String USER_NICK = "nick";
    private static final String DISPLAY_NAME = "displayName";
    private static final String REAL_NAME = "realName";
    private static final String BIRTHDAY = "birthday";
    private static final String OCCUPATION = "occupation";
    private static final String ABOUT_ME = "aboutMe";
    private static final String LOCATION = "location";


    public FirebaseDatabase(FirebaseFirestore db) {
        this.db = db;
    }

//    @Override
//    public Single<Integer> getUserHighscore(String uId) {
//        final DocumentReference ref = db.collection(HIGHSCORE).document(uId);
//        return FirebaseDBWrapper.observeDocumentReference(ref)
//                .firstOrError()
//                .map(d -> (int)(long) d.getData().get(SCORE))
//                .subscribeOn(Schedulers.io());
//    }

    @Override
    public Completable updateNick(String uid, String newNick) {
        final DocumentReference ref = db.collection(USER).document(uid);
        final Map<String, Object> map = new HashMap<>();
        map.put(USER_NICK, newNick);
        return FirebaseDBWrapper.updateDocument(ref, map)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable updateProfile(String uid, UserProfile userProfile) {
        final DocumentReference ref = db.collection(USER).document(uid);
        final Map<String, Object> map = new HashMap<>();
        map.put(DISPLAY_NAME, userProfile.getDisplayName());
        map.put(REAL_NAME, userProfile.getRealName());
        map.put(GENDER, userProfile.getGender());
        map.put(ETHNICITY, userProfile.getEthnicity());
        map.put(RELIGION, userProfile.getReligion());
        map.put(FIGURE, userProfile.getFigure());
        map.put(MARITAL_STATUS_DATA, userProfile.getMaritalStatus());
        map.put(OCCUPATION, userProfile.getOccupation());
        map.put(ABOUT_ME, userProfile.getAboutMe());
        map.put(LOCATION, userProfile.getLocation());
        return FirebaseDBWrapper.updateDocument(ref, map)
                .subscribeOn(Schedulers.io());
    }

    @SuppressLint("NewApi")
    @Override
    public Single<List<ListItem>> getEthnicityList() {
        final Query query = db.collection(ETHNICITY);
        return FirebaseDBWrapper.getCollection(query)
                .map(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                    List<ListItem> list = new ArrayList<>();
                    for (DocumentSnapshot snap : snapshots) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        String jsonString = gson.toJson(snap.getData());
                        ListItem userProfile = gson.fromJson(jsonString, ListItem.class);
                        list.add(userProfile);
                    }
                    return list;
                })
                .toSingle()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<ListItem>> getFigureList() {
        final Query query = db.collection(FIGURE);
        return FirebaseDBWrapper.getCollection(query)
                .map(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                    List<ListItem> list = new ArrayList<>();
                    for (DocumentSnapshot snap : snapshots) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        String jsonString = gson.toJson(snap.getData());
                        ListItem userProfile = gson.fromJson(jsonString, ListItem.class);
                        list.add(userProfile);
                    }
                    return list;
                })
                .toSingle()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<ListItem>> getGenderList() {
        final Query query = db.collection(GENDER);
        return FirebaseDBWrapper.getCollection(query)
                .map(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                    List<ListItem> list = new ArrayList<>();
                    for (DocumentSnapshot snap : snapshots) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        String jsonString = gson.toJson(snap.getData());
                        ListItem userProfile = gson.fromJson(jsonString, ListItem.class);
                        list.add(userProfile);
                    }
                    return list;
                })
                .toSingle()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<ListItem>> getMaritalStatusList() {
        final Query query = db.collection(MARITAL_STATUS);
        return FirebaseDBWrapper.getCollection(query)
                .map(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                    List<ListItem> list = new ArrayList<>();
                    for (DocumentSnapshot snap : snapshots) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        String jsonString = gson.toJson(snap.getData());
                        ListItem userProfile = gson.fromJson(jsonString, ListItem.class);
                        list.add(userProfile);
                    }
                    return list;
                })
                .toSingle()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<ListItem>> getReligion() {
        final Query query = db.collection(RELIGION);
        return FirebaseDBWrapper.getCollection(query)
                .map(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                    List<ListItem> list = new ArrayList<>();
                    for (DocumentSnapshot snap : snapshots) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        String jsonString = gson.toJson(snap.getData());
                        ListItem userProfile = gson.fromJson(jsonString, ListItem.class);
                        list.add(userProfile);
                    }
                    return list;
                })
                .toSingle()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<UserProfile> getUserProfile(String uId) {
        getEthnicityList();
        final DocumentReference ref = db.collection(USER).document(uId);
        return FirebaseDBWrapper.observeDocumentReference(ref)
                .firstOrError()
                .map(d -> {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    String jsonString = gson.toJson(d.getData());
                    return gson.fromJson(jsonString, UserProfile.class);
                })
                .subscribeOn(Schedulers.io());

    }

    @Override
    public Single<List<CitiesListItem>> getCityList() {
        final Query query = db.collection(RELIGION);
        return FirebaseDBWrapper.getCollection(query)
                .map(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                    List<CitiesListItem> list = new ArrayList<>();
                    for (DocumentSnapshot snap : snapshots) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        String jsonString = gson.toJson(snap.getData());
                        CitiesListItem userProfile = gson.fromJson(jsonString, CitiesListItem.class);
                        list.add(userProfile);
                    }
                    return list;
                })
                .toSingle()
                .subscribeOn(Schedulers.io());
    }


//    @Override
//    public Completable updateHighscore(String uid, int addToHighscore) {
//        final DocumentReference ref = db.collection(USER).document(uid);
//        return FirebaseDBWrapper.updateDocument(ref, USER_SCORE, FieldValue.increment((long) addToHighscore))
//                .subscribeOn(Schedulers.io());
//    }
}
