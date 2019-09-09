package com.sparknetwork.editprofile.ui.profile;

import com.sparknetwork.editprofile.interactor.ChangeProfileDataInteractor;
import com.sparknetwork.editprofile.interactor.ChangeProfilePhotoInteractor;
import com.sparknetwork.editprofile.interactor.ChangeUserPasswordInteractor;
import com.sparknetwork.editprofile.interactor.DeleteAccountInteractor;
import com.sparknetwork.editprofile.interactor.LogoutUserInteractor;
import com.sparknetwork.editprofile.repository.firebase.FirebaseAuthRepositoryType;
import com.sparknetwork.editprofile.repository.firebase.FirebaseDatabaseType;
import com.sparknetwork.editprofile.repository.firebase.FirebaseStorageRepositoryType;
import com.sparknetwork.editprofile.router.LoginRouter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ProfileModule {

    @Provides
    ProfileVMFactory settingsVMFactory(LogoutUserInteractor logoutUserInteractor,
                                       LoginRouter loginRouter,
                                       ChangeUserPasswordInteractor changeUserPasswordInteractor,
                                       ChangeProfileDataInteractor changeProfileDataInteractor,
                                       ChangeProfilePhotoInteractor changeProfilePhotoInteractor,
                                       DeleteAccountInteractor deleteAccountInteractor) {
        return new ProfileVMFactory(logoutUserInteractor,
                changeUserPasswordInteractor,
                loginRouter,
                changeProfileDataInteractor,
                changeProfilePhotoInteractor,
                deleteAccountInteractor);
    }

    @Provides
    DeleteAccountInteractor deleteAccountInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType) {
        return new DeleteAccountInteractor(firebaseAuthRepositoryType);
    }

    @Provides
    ChangeProfilePhotoInteractor changeProfilePhotoInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType,
                                                              FirebaseStorageRepositoryType firebaseStorageRepositoryType) {
        return new ChangeProfilePhotoInteractor(firebaseAuthRepositoryType, firebaseStorageRepositoryType);
    }

    @Provides
    ChangeProfileDataInteractor changeUserNickInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType,
                                                         FirebaseDatabaseType firebaseDatabaseType) {
        return new ChangeProfileDataInteractor(firebaseAuthRepositoryType, firebaseDatabaseType);
    }

    @Provides
    ChangeUserPasswordInteractor changeUserPasswordInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType) {
        return new ChangeUserPasswordInteractor(firebaseAuthRepositoryType);
    }

    @Provides
    @Named("profile")
    LoginRouter loginRouter() {
        return new LoginRouter();
    }

    @Provides
    LogoutUserInteractor logoutUserInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType) {
        return new LogoutUserInteractor(firebaseAuthRepositoryType);
    }

}
