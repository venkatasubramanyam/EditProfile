package com.sparknetwork.editprofile.ui.splash;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.sparknetwork.editprofile.R;
import com.sparknetwork.editprofile.base.BaseActivity;
import com.sparknetwork.editprofile.entity.ErrorCarrier;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity {

    @Inject
    SplashVMFactory factory;
    SplashViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideToolbar();

        viewModel = ViewModelProviders.of(this, factory).get(SplashViewModel.class);
        viewModel.progress().observe(this, this::onProgress);
        viewModel.error().observe(this, this::onError);
        viewModel.user().observe(this, this::onUser);

        viewModel.getUser();

    }

    private void onUser(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            viewModel.openStart(this);
        } else {
            viewModel.openLogin(this);
        }
    }

    private void onError(ErrorCarrier errorCarrier) {
    }

    private void onProgress(Boolean aBoolean) {
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }
}
