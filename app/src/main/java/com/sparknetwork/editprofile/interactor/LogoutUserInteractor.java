package com.sparknetwork.editprofile.interactor;

import com.sparknetwork.editprofile.repository.firebase.FirebaseAuthRepositoryType;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Interactor to log out FirebaseUser
 */
public class LogoutUserInteractor {

    private final FirebaseAuthRepositoryType firebaseAuthRepositoryType;

    public LogoutUserInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType) {
        this.firebaseAuthRepositoryType = firebaseAuthRepositoryType;
    }

    /**
     * Log out FirebaseUser
     * @return Completable
     */
    public Completable logout(){
        return firebaseAuthRepositoryType.logoutUser()
                .observeOn(AndroidSchedulers.mainThread());
    }
}
