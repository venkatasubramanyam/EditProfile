package com.sparknetwork.editprofile.ui.splash;

import com.sparknetwork.editprofile.interactor.GetUserInteractor;
import com.sparknetwork.editprofile.repository.firebase.FirebaseAuthRepositoryType;
import com.sparknetwork.editprofile.router.LoginRouter;
import com.sparknetwork.editprofile.router.SettingsRouter;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashModule {

    @Provides
    SplashVMFactory splashVMFactory(SettingsRouter startRouter, GetUserInteractor getUserInteractor, LoginRouter loginRouter){
        return new SplashVMFactory(startRouter, getUserInteractor, loginRouter);
    }

    @Provides
    GetUserInteractor getUserInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType){
        return new GetUserInteractor(firebaseAuthRepositoryType);
    }

    @Provides
    LoginRouter loginRouter(){
        return new LoginRouter();
    }

    @Provides
    SettingsRouter startRouter(){
        return new SettingsRouter();
    }

}

