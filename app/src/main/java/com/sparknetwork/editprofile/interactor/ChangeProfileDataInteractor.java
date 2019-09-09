package com.sparknetwork.editprofile.interactor;

import com.sparknetwork.editprofile.entity.CitiesListItem;
import com.sparknetwork.editprofile.entity.ListItem;
import com.sparknetwork.editprofile.entity.UserProfile;
import com.sparknetwork.editprofile.repository.firebase.FirebaseAuthRepositoryType;
import com.sparknetwork.editprofile.repository.firebase.FirebaseDatabaseType;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ChangeProfileDataInteractor {

    private final FirebaseAuthRepositoryType firebaseAuthRepositoryType;
    private final FirebaseDatabaseType firebaseDatabaseType;

    public ChangeProfileDataInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType, FirebaseDatabaseType firebaseDatabaseType) {
        this.firebaseAuthRepositoryType = firebaseAuthRepositoryType;
        this.firebaseDatabaseType = firebaseDatabaseType;
    }

    public Completable changeNick(String nick) {
        return firebaseAuthRepositoryType.getCurrentUser()
                .take(1)
                .flatMapCompletable(firebaseUser -> firebaseAuthRepositoryType.changeUserNick(firebaseUser, nick)
                        .doOnComplete(() -> firebaseDatabaseType.updateNick(firebaseUser.getUid(), nick)))
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable updateProfile(UserProfile userProfile) {
        return firebaseAuthRepositoryType.getCurrentUser()
                .take(1)
                .flatMapCompletable(firebaseUser -> firebaseAuthRepositoryType.changeUserNick(firebaseUser, userProfile.getDisplayName())
                        .doOnComplete(() -> firebaseDatabaseType.updateProfile(firebaseUser.getUid(), userProfile)))
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<UserProfile> getUserDetails() {
        return Single.fromObservable(firebaseAuthRepositoryType.getCurrentUser()
                .take(1)
                .flatMapSingle(firebaseUser -> firebaseDatabaseType.getUserProfile(firebaseUser.getUid())))
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<ListItem>> getEthnicity() {
        return firebaseDatabaseType.getEthnicityList()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<ListItem>> getGender() {
        return firebaseDatabaseType.getGenderList()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<ListItem>> getReligion() {
        return firebaseDatabaseType.getReligion()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<ListItem>> getMaritalStatus() {
        return firebaseDatabaseType.getMaritalStatusList()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<ListItem>> getFigure() {
        return firebaseDatabaseType.getFigureList()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<CitiesListItem>> getCities() {
        return firebaseDatabaseType.getCityList()
                .observeOn(AndroidSchedulers.mainThread());
    }

}
