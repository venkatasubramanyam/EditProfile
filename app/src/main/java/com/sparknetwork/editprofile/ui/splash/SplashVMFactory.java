package com.sparknetwork.editprofile.ui.splash;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sparknetwork.editprofile.interactor.GetUserInteractor;
import com.sparknetwork.editprofile.router.LoginRouter;
import com.sparknetwork.editprofile.router.SettingsRouter;

public class SplashVMFactory implements ViewModelProvider.Factory {

    private final SettingsRouter startRouter;
    private final GetUserInteractor getUserInteractor;
    private final LoginRouter loginRouter;

    SplashVMFactory(SettingsRouter startRouter, GetUserInteractor getUserInteractor, LoginRouter loginRouter) {
        this.startRouter = startRouter;
        this.getUserInteractor = getUserInteractor;
        this.loginRouter = loginRouter;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SplashViewModel(startRouter, loginRouter, getUserInteractor);
    }

}
