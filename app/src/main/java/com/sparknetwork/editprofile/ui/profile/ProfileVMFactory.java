package com.sparknetwork.editprofile.ui.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sparknetwork.editprofile.interactor.ChangeProfilePhotoInteractor;
import com.sparknetwork.editprofile.interactor.ChangeProfileDataInteractor;
import com.sparknetwork.editprofile.interactor.ChangeUserPasswordInteractor;
import com.sparknetwork.editprofile.interactor.DeleteAccountInteractor;
import com.sparknetwork.editprofile.interactor.LogoutUserInteractor;
import com.sparknetwork.editprofile.router.LoginRouter;

public class ProfileVMFactory implements ViewModelProvider.Factory {

    private final LogoutUserInteractor logoutUserInteractor;
    private final ChangeUserPasswordInteractor changeUserPasswordInteractor;
    private final LoginRouter loginRouter;
    private final ChangeProfileDataInteractor changeProfileDataInteractor;
    private final ChangeProfilePhotoInteractor changeProfilePhotoInteractor;
    private final DeleteAccountInteractor deleteAccountInteractor;

    ProfileVMFactory(LogoutUserInteractor logoutUserInteractor,
                     ChangeUserPasswordInteractor changeUserPasswordInteractor,
                     LoginRouter loginRouter,
                     ChangeProfileDataInteractor changeProfileDataInteractor,
                     ChangeProfilePhotoInteractor changeProfilePhotoInteractor,
                     DeleteAccountInteractor deleteAccountInteractor) {
        this.logoutUserInteractor = logoutUserInteractor;
        this.changeUserPasswordInteractor = changeUserPasswordInteractor;
        this.loginRouter = loginRouter;
        this.changeProfileDataInteractor = changeProfileDataInteractor;
        this.changeProfilePhotoInteractor = changeProfilePhotoInteractor;
        this.deleteAccountInteractor = deleteAccountInteractor;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProfileViewModel(logoutUserInteractor,
                changeUserPasswordInteractor,
                loginRouter,
                changeProfileDataInteractor,
                changeProfilePhotoInteractor,
                deleteAccountInteractor);
    }
}
