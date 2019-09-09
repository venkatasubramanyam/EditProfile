package com.sparknetwork.editprofile.ui.login;

import com.sparknetwork.editprofile.interactor.LoginUserInteractor;
import com.sparknetwork.editprofile.interactor.SignupInteractor;
import com.sparknetwork.editprofile.repository.firebase.FirebaseAuthRepositoryType;
import com.sparknetwork.editprofile.router.SettingsRouter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    LoginVMFactory loginVMFactory(LoginUserInteractor loginUserInteractor,
                                  SignupInteractor signupInteractor,
                                  SettingsRouter startRouter){
        return new LoginVMFactory(loginUserInteractor, signupInteractor, startRouter);
    }

    @Provides
    @Named("login")
    SettingsRouter startRouter(){
        return new SettingsRouter();
    }

    @Provides
    SignupInteractor signupInteractor(FirebaseAuthRepositoryType firebaseAuthRepository){
        return new SignupInteractor(firebaseAuthRepository);
    }

    @Provides
    LoginUserInteractor loginUserInteractor(FirebaseAuthRepositoryType firebaseAuthRepositoryType){
        return new LoginUserInteractor(firebaseAuthRepositoryType);
    }

}
