package com.sparknetwork.editprofile.di;

import com.sparknetwork.editprofile.App;
import com.sparknetwork.editprofile.ui.login.LoginModule;
import com.sparknetwork.editprofile.ui.profile.ProfileModule;
import com.sparknetwork.editprofile.ui.splash.SplashModule;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Dagger Component
 */
@ApplicationScope
@Component(modules = {AndroidInjectionModule.class,
        FirebaseModule.class,
        BinderModule.class,
        ApplicationModule.class,
        SplashModule.class,
        LoginModule.class,
        ProfileModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(App app);

        AppComponent build();
    }

    void inject(App app);

}

