package com.sparknetwork.editprofile.di;

import com.sparknetwork.editprofile.ui.login.LoginActivity;
import com.sparknetwork.editprofile.ui.login.LoginFragment;
import com.sparknetwork.editprofile.ui.login.LoginModule;
import com.sparknetwork.editprofile.ui.profile.ProfileActivity;
import com.sparknetwork.editprofile.ui.profile.ProfileModule;
import com.sparknetwork.editprofile.ui.splash.SplashActivity;
import com.sparknetwork.editprofile.ui.splash.SplashModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Bind all Activity package Module(s) to their Activity-
 */
@Module
public abstract class BinderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = SplashModule.class)
    abstract SplashActivity bindSplashActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity bindLoginActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = ProfileModule.class)
    abstract ProfileActivity bindSettingsActivity();

    @FragmentScope
    @ContributesAndroidInjector()
    abstract LoginFragment bindLoginFragment();

}
