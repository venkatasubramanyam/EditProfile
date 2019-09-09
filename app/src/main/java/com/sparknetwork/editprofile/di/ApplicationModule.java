package com.sparknetwork.editprofile.di;

import android.content.Context;

import com.sparknetwork.editprofile.App;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    /**
     * Get application context
     * @param app
     * @return
     */
    @Provides
    @ApplicationScope
    Context context(App app){
        return app.getApplicationContext();
    }

}
