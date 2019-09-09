package com.sparknetwork.editprofile.router;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.sparknetwork.editprofile.ui.profile.ProfileActivity;

/**
 * Opens ProfileActivity.class
 */
public class SettingsRouter {

    /**
     * Open ProfileActivity.class
     * @param context of current Activity
     * @param clearStack clear Activity stack. true clears stack
     */
    public void open(@NonNull Context context, boolean clearStack){
        Intent intent = new Intent(context, ProfileActivity.class);
        if (clearStack) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        context.startActivity(intent);
    }

}
