package com.sparknetwork.editprofile.validator;

import com.google.android.material.textfield.TextInputLayout;

public class UserProfileValidate {

    public UserProfileValidate() {

    }

    private boolean validate(String text, TextInputLayout tiLayout) {
        EmptyValidate emptyValidate = new EmptyValidate();
        if (!emptyValidate.isValid(text)) {
            tiLayout.setError(emptyValidate.getErrorMessage());
            return false;
        }
        return true;
    }
}
