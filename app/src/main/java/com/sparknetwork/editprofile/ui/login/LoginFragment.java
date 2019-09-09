package com.sparknetwork.editprofile.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;
import com.sparknetwork.editprofile.R;
import com.sparknetwork.editprofile.base.BaseFragment;
import com.sparknetwork.editprofile.bus.RxBus;
import com.sparknetwork.editprofile.bus.event.BooleanEvent;
import com.sparknetwork.editprofile.bus.event.LoginEvent;
import com.sparknetwork.editprofile.validator.EmailValidate;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;


public class LoginFragment extends BaseFragment {

    @BindView(R.id.editText_email)
    EditText email;
    @BindView(R.id.email_layout)
    TextInputLayout emailLayout;
    @BindView(R.id.editText_password)
    EditText password;
    @BindView(R.id.password_layout)
    TextInputLayout passwordLayout;
    @BindView(R.id.button_login)
    Button login;
    @BindView(R.id.button_signup)
    Button signup;
    @BindView(R.id.login_load_progress)
    ProgressBar progressBar;

    @Override
    protected int getLayout() {
        return R.layout.fragment_log_or_sign;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
    }

    @OnClick(R.id.button_signup)
    void signupClick() {
        RxBus.publish(RxBus.SHOW_SIGN_UP_FRAGMENT, new BooleanEvent(true));
    }

    @OnClick(R.id.button_login)
    void loginClick() {
        String sEmail = email.getText().toString().trim();
        String sPass = password.getText().toString();
        if (validateForm(sEmail, sPass)) {
            RxBus.publish(RxBus.TRY_LOG_IN, new LoginEvent(sEmail, sPass));
        }
    }

    private boolean validateForm(String email, String pass) {
        EmailValidate emailValidate = new EmailValidate();
        boolean emailValid = emailValidate.isValid(email);
        boolean passValid = pass.length() >= 8;
        if (!emailValid) {
            emailLayout.setError(emailValidate.getErrorMessage());
        } else {
            emailLayout.setError(null);
        }
        if (!passValid) {
            passwordLayout.setError("Not a valid password");
        } else {
            passwordLayout.setError(null);
        }
        return emailValid && passValid;
    }

}
