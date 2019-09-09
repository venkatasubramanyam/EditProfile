package com.sparknetwork.editprofile.ui.login;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.sparknetwork.editprofile.base.BaseViewModel;
import com.sparknetwork.editprofile.interactor.LoginUserInteractor;
import com.sparknetwork.editprofile.interactor.SignupInteractor;
import com.sparknetwork.editprofile.router.SettingsRouter;

class LoginViewModel extends BaseViewModel {

    private final MutableLiveData<Boolean> logIn = new MutableLiveData<>();
    private final MutableLiveData<Boolean> signUp = new MutableLiveData<>();

    private final LoginUserInteractor loginUserInteractor;
    private final SignupInteractor signupInteractor;
    private final SettingsRouter startRouter;

    LoginViewModel(LoginUserInteractor loginUserInteractor,
                          SignupInteractor signupInteractor,
                   SettingsRouter startRouter) {
        this.loginUserInteractor = loginUserInteractor;
        this.signupInteractor = signupInteractor;
        this.startRouter = startRouter;
    }

    void logInUser(String email, String password){
        progress.setValue(true);
        disposable = loginUserInteractor.login(email, password)
                .subscribe(this::onLoginSuccess, this::onError);
    }

    void signupUser(String email, String password){
        progress.setValue(true);
        disposable = signupInteractor.signupUser(email, password)
                .subscribe(this::onSignupSuccess, this::onError);
    }

    private void onSignupSuccess() {
        progress.setValue(false);
        signUp.setValue(true);
    }

    private void onLoginSuccess() {
        progress.setValue(false);
        logIn.setValue(true);
    }

    void openStart(Context context, boolean clearStack){
        startRouter.open(context,clearStack);
    }

    MutableLiveData<Boolean> login() {
        return logIn;
    }

    MutableLiveData<Boolean> singup() {
        return signUp;
    }
}
