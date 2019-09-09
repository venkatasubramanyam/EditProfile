package com.sparknetwork.editprofile;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.fragment.app.Fragment;

import com.sparknetwork.editprofile.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Application Class
 */
public class App extends Application implements HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingFragmentInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);

    }

    /**
     * Checks whether network is available or not, returns true if network is available
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkAvailable = null;
        if (connectivityManager != null) {
            networkAvailable = connectivityManager.getActiveNetworkInfo();
        }
        return networkAvailable != null && networkAvailable.isConnectedOrConnecting();
    }

    /**
     * Checks whether Wifi network is available or not, returns true if Wifi network is available
     */
    public boolean isWifiNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkAvailable = null;
        if (connectivityManager != null) {
            networkAvailable = connectivityManager.getActiveNetworkInfo();
        }
        return networkAvailable != null && networkAvailable.getType() == ConnectivityManager.TYPE_WIFI;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingFragmentInjector;
    }
}
