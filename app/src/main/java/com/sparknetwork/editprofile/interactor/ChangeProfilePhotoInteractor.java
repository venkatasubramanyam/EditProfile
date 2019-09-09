package com.sparknetwork.editprofile.interactor;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.sparknetwork.editprofile.repository.firebase.FirebaseAuthRepositoryType;
import com.sparknetwork.editprofile.repository.firebase.FirebaseStorageRepositoryType;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ChangeProfilePhotoInteractor {

    private final FirebaseAuthRepositoryType authRepo;
    private final FirebaseStorageRepositoryType storageRepo;

    public ChangeProfilePhotoInteractor(FirebaseAuthRepositoryType authRepo,
                                        FirebaseStorageRepositoryType storageRepo) {
        this.authRepo = authRepo;
        this.storageRepo = storageRepo;
    }


    public Completable changeProfilePhoto(@NonNull Uri uri){
        return authRepo.getCurrentUser()
                .take(1)
                .flatMapCompletable(firebaseUser -> storageRepo.uploadPhoto(firebaseUser.getUid(), uri)
                        .flatMap(taskSnapshot -> storageRepo.getDownloadUrl(taskSnapshot.getMetadata().getReference()))
                        .flatMapCompletable(uploadedUri -> authRepo.changeUserPhoto(firebaseUser, uploadedUri)))
                .observeOn(AndroidSchedulers.mainThread());
    }

}
