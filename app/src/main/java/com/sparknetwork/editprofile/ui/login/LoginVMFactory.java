package com.sparknetwork.editprofile.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sparknetwork.editprofile.interactor.LoginUserInteractor;
import com.sparknetwork.editprofile.interactor.SignupInteractor;
import com.sparknetwork.editprofile.router.SettingsRouter;

public class LoginVMFactory implements ViewModelProvider.Factory {

    private final LoginUserInteractor loginUserInteractor;
    private final SignupInteractor signupInteractor;
    private final SettingsRouter startRouter;

    LoginVMFactory(LoginUserInteractor loginUserInteractor,
                          SignupInteractor signupInteractor,
                   SettingsRouter startRouter) {
        this.loginUserInteractor = loginUserInteractor;
        this.signupInteractor = signupInteractor;
        this.startRouter = startRouter;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LoginViewModel(loginUserInteractor, signupInteractor, startRouter);
    }

}
