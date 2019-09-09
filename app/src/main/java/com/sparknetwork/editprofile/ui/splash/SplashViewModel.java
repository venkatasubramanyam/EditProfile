package com.sparknetwork.editprofile.ui.splash;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.sparknetwork.editprofile.base.BaseViewModel;
import com.sparknetwork.editprofile.interactor.GetUserInteractor;
import com.sparknetwork.editprofile.router.LoginRouter;
import com.sparknetwork.editprofile.router.SettingsRouter;
import com.google.firebase.auth.FirebaseUser;

public class SplashViewModel extends BaseViewModel {

    private final SettingsRouter startRouter;
    private final LoginRouter loginRouter;
    private final GetUserInteractor getUserInteractor;
    private final MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    SplashViewModel(SettingsRouter startRouter, LoginRouter loginRouter, GetUserInteractor getUserInteractor) {
        this.startRouter = startRouter;
        this.loginRouter = loginRouter;
        this.getUserInteractor = getUserInteractor;
    }

    void getUser(){
        disposable = getUserInteractor.getUser()
                .subscribe(this::onUser, this::onError);
    }

    private void onUser(FirebaseUser firebaseUser) {
        user.setValue(firebaseUser);
    }

    void openStart(Context context){
        startRouter.open(context, true);
    }

    void openLogin(Context context){
        loginRouter.open(context, true);
    }

    public MutableLiveData<FirebaseUser> user(){
        return user;
    }

    @Override
    protected void onError(Throwable throwable) {
        if(throwable instanceof NullPointerException){
            user.setValue(null);
        }else{
            super.onError(throwable);
        }
    }
}
