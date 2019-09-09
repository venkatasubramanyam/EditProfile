package com.sparknetwork.editprofile.ui.profile;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.lifecycle.ViewModelProviders;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.sparknetwork.editprofile.R;
import com.sparknetwork.editprofile.base.BaseActivity;
import com.sparknetwork.editprofile.bus.RxBus;
import com.sparknetwork.editprofile.entity.CitiesListItem;
import com.sparknetwork.editprofile.entity.ErrorCarrier;
import com.sparknetwork.editprofile.entity.ListItem;
import com.sparknetwork.editprofile.entity.UserProfile;
import com.sparknetwork.editprofile.ui.dialog.CustomMaterialDialog;
import com.sparknetwork.editprofile.validator.EmptyValidate;
import com.sparknetwork.editprofile.validator.PasswordValidate;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ProfileActivity extends BaseActivity {

    @Inject
    ProfileVMFactory factory;
    ProfileViewModel viewModel;

    private final RxPermissions rxPermissions = new RxPermissions(this);
    private static final int READ_REQUEST_CODE = 42;

    @BindView(R.id.editText_display_name)
    EditText etDisplayName;
    @BindView(R.id.layout_display_name)
    TextInputLayout tiDisplayName;
    @BindView(R.id.editText_real_name)
    EditText etRealName;
    @BindView(R.id.layout_real_name)
    TextInputLayout tiRealName;
    @BindView(R.id.editText_about_me)
    EditText etAboutme;
    @BindView(R.id.layout_about_me)
    TextInputLayout tiAboutMe;
    @BindView(R.id.editText_occupation)
    EditText etOccupation;
    @BindView(R.id.layout_occupation)
    TextInputLayout tiOccupation;
    @BindView(R.id.spinner_ethnicity)
    AppCompatSpinner ethnicityDropDown;
    @BindView(R.id.tv_ethnicity)
    TextView tiEthnicity;
    @BindView(R.id.tv_border)
    View vEthnicity;
    @BindView(R.id.spinner_gender)
    AppCompatSpinner genderDropDown;
    @BindView(R.id.tv_gender)
    TextView tiGender;
    @BindView(R.id.tv_border_gender)
    View vGender;
    @BindView(R.id.spinner_religion)
    AppCompatSpinner religionDropDown;
    @BindView(R.id.tv_religion)
    TextView tiReligion;
    @BindView(R.id.tv_border_religion)
    View vReligion;
    @BindView(R.id.spinner_figure)
    AppCompatSpinner figureDropDown;
    @BindView(R.id.tv_figure)
    TextView tiFigure;
    @BindView(R.id.tv_border_figure)
    View vFigure;
    @BindView(R.id.spinner_marital_status)
    AppCompatSpinner maritalStatusDropDown;
    @BindView(R.id.tv_marital_status)
    TextView tiMaritalStatus;
    @BindView(R.id.tv_border_marital_status)
    View vMaritalStatus;
    @BindView(R.id.autocomplete_city_list)
    AppCompatAutoCompleteTextView autoCompleteCityList;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enableDisplayHomeAsUp();

        setTitle(R.string.user_profile);

        viewModel = ViewModelProviders.of(this, factory).get(ProfileViewModel.class);
        viewModel.logout().observe(this, this::onLogoutSuccess);
        viewModel.error().observe(this, this::onError);
        viewModel.progress().observe(this, this::onProgress);
        viewModel.passChange().observe(this, this::onPasswordChange);
        viewModel.nickChange().observe(this, this::onNickChanged);
        viewModel.photoChange().observe(this, this::onPhotoChaned);
        viewModel.accountDelete().observe(this, this::onAccountDeleted);
        viewModel.userInfoReceived().observe(this, this::getUserDetails);
        viewModel.ethnicityReceived().observe(this, this::getEthnicity);
        viewModel.getGender().observe(this, this::getGenderList);
        viewModel.getReligionList().observe(this, this::getReligionList);
        viewModel.getMaritalStatusList().observe(this, this::getMaritalStatusList);
        viewModel.getFigureList().observe(this, this::getFigureList);
        viewModel.getCityList().observe(this, this::getCitiesList);

        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        //do nothing
                    } else {
                        MaterialDialog.Builder builder = CustomMaterialDialog
                                .okWithText("Permission needed", this, "Permission is needed for profile to properly work. Without it you can not change user profile photo.");
                        showCustomDialog(builder);
                    }
                });

        viewModel.getUserInfo();
        viewModel.getEthnicity();
        viewModel.getGenderList();
        viewModel.getReligionData();
        viewModel.getFigureData();
        viewModel.getMaritalStatusData();
        viewModel.getCityData();
    }

    @OnClick(R.id.im_profile_pic)
    void onProfilePicChange() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @OnClick(R.id.button_delete_account)
    void onDeleteAccount() {
        LinearLayout layout = getLayoutForDialog(R.layout.dialog_confirm_pass, R.id.dialog_conf_pass_layout);
        TextView passText = layout.findViewById(R.id.edit_dialog_conf_pass);

        MaterialDialog.Builder builder = CustomMaterialDialog.customDialog("Confirm deletion", this, layout)
                .onNegative((dialog, which) -> dialog.dismiss())
                .onPositive((dialog, which) -> {
                    dialog.dismiss();
                    String pass = passText.getText().toString();
                    viewModel.deleteAccount(pass);
                });
        showCustomDialog(builder);
    }

    @OnClick(R.id.button_logout)
    void onLogout() {
        MaterialDialog.Builder builder = CustomMaterialDialog.areYouSure("Are you sure?", this)
                .onNegative((dialog, which) -> dialog.dismiss())
                .onPositive((dialog, which) -> {
                    dialog.dismiss();
                    viewModel.logoutUser();
                });
        showCustomDialog(builder);
    }

    private boolean validate(String text, TextInputLayout tiLayout) {
        EmptyValidate emptyValidate = new EmptyValidate();
        if (!emptyValidate.isValid(text)) {
            tiLayout.setError(emptyValidate.getErrorMessage());
            return false;
        }
        return true;
    }

    private boolean validate(int position, TextView tiLayout, View vBorder) {
        EmptyValidate emptyValidate = new EmptyValidate();
        if (position == 0) {
            tiLayout.setTextColor(Color.RED);
            vBorder.setBackgroundColor(Color.RED);
            return false;
        } else {
            tiLayout.setTextColor(getResources().getColor(R.color.colorAccent));
            vBorder.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            return true;
        }

    }

    @OnClick(R.id.button_update_profile)
    void onUpdateProfile() {
        MaterialDialog.Builder builder = CustomMaterialDialog.areYouSure("Are you sure to update profile?", this)
                .onNegative((dialog, which) -> dialog.dismiss())
                .onPositive((dialog, which) -> {
                    dialog.dismiss();
                    String displayName = etDisplayName.getText().toString().trim();
                    String realName = etRealName.getText().toString().trim();
                    String aboutMe = etAboutme.getText().toString().trim();
                    String occupation = etOccupation.getText().toString().trim();
                    String location = autoCompleteCityList.getText().toString().trim();
                    int ethnicity = ethnicityDropDown.getSelectedItemPosition();
                    int figure = figureDropDown.getSelectedItemPosition();
                    int gender = genderDropDown.getSelectedItemPosition();
                    int religion = religionDropDown.getSelectedItemPosition();
                    int maritalStatus = maritalStatusDropDown.getSelectedItemPosition();
                    boolean isDisplayName = validate(displayName, tiDisplayName);
                    boolean isRealName = validate(realName, tiRealName);
                    boolean isAboutMe = validate(aboutMe, tiAboutMe);
                    boolean isOccupation = validate(occupation, tiOccupation);
                    boolean isEthnicity = validate(ethnicity, tiEthnicity, vEthnicity);
                    boolean isFigure = validate(figure, tiFigure, vFigure);
                    boolean isGender = validate(gender, tiGender, vGender);
                    boolean isReligion = validate(religion, tiReligion, vReligion);
                    boolean isMaritalStatus = validate(maritalStatus, tiMaritalStatus, vMaritalStatus);

                    if (isDisplayName && isRealName && isAboutMe && isOccupation && isEthnicity && isFigure && isGender && isReligion && isMaritalStatus) {
                        if (location.length() > 0) {
                            autoCompleteCityList.setError("Please select Location");
                        } else {
                            UserProfile userProfile = new UserProfile();
                            userProfile.setDisplayName(displayName);
                            userProfile.setRealName(realName);
                            userProfile.setAboutMe(aboutMe);
                            userProfile.setOccupation(occupation);
                            userProfile.setLocation(location);
                            userProfile.setEthnicity(ethnicityDropDown.getSelectedItem().toString());
                            userProfile.setFigure(figureDropDown.getSelectedItem().toString());
                            userProfile.setReligion(religionDropDown.getSelectedItem().toString());
                            userProfile.setMaritalStatus(maritalStatusDropDown.getSelectedItem().toString());
                            viewModel.updateProfileData(userProfile);
                        }
                    }

                });
        showCustomDialog(builder);
    }

    private void onAccountDeleted(Boolean aBoolean) {
        viewModel.startLogin(this, true);
    }

    private void getUserDetails(UserProfile userProfile) {
        //got details
        System.out.println("reached here:" + userProfile.getNick());
    }

    private void getEthnicity(List<ListItem> ethnicityList) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (ListItem listItem : ethnicityList) {
            arrayList.add(listItem.getName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, arrayList);
        ethnicityDropDown.setAdapter(arrayAdapter);
    }

    private void getGenderList(List<ListItem> genderList) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (ListItem listItem : genderList) {
            arrayList.add(listItem.getName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, arrayList);
        genderDropDown.setAdapter(arrayAdapter);
    }

    private void getFigureList(List<ListItem> figureList) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (ListItem listItem : figureList) {
            arrayList.add(listItem.getName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, arrayList);
        figureDropDown.setAdapter(arrayAdapter);
    }

    private void getCitiesList(List<CitiesListItem> citiesList) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (CitiesListItem listItem : citiesList) {
            arrayList.add(listItem.getCity());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, arrayList);
        autoCompleteCityList.setAdapter(arrayAdapter);
    }

    private void getMaritalStatusList(List<ListItem> maritalStatusList) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (ListItem listItem : maritalStatusList) {
            arrayList.add(listItem.getName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, arrayList);
        maritalStatusDropDown.setAdapter(arrayAdapter);
    }

    private void getReligionList(List<ListItem> religionList) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (ListItem listItem : religionList) {
            arrayList.add(listItem.getName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, arrayList);
        religionDropDown.setAdapter(arrayAdapter);
    }

    private void onPhotoChaned(Boolean changed) {
        if (changed) {
            MaterialDialog.Builder builder = CustomMaterialDialog.okDialog("Photo changed!", this);
            showCustomDialog(builder);
        }
    }

    private void onError(ErrorCarrier errorCarrier) {
        MaterialDialog.Builder builder = CustomMaterialDialog.error("Error", this, errorCarrier.getMessage());
        showCustomDialog(builder);
    }

    private void onNickChanged(Boolean changed) {
        if (changed) {
            MaterialDialog.Builder builder = CustomMaterialDialog.okDialog("Nickname changed.", this);
            showCustomDialog(builder);
        }
    }

    private void onPasswordChange(Boolean changed) {
        if (changed) {
            MaterialDialog.Builder builder = CustomMaterialDialog.okDialog("Password changed successful!", this)
                    .onPositive((dialog, which) -> dialog.dismiss());
            showCustomDialog(builder);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        RxBus.subscribe(RxBus.TRY_LOG_OUT, this, o -> {

        });

        RxBus.subscribe(RxBus.TRY_CHANGE_PASSWORD, this, o -> {
            LinearLayout layout = getLayoutForDialog(R.layout.dialog_change_pass, R.id.change_pass_linear);

            TextView oldpass = layout.findViewById(R.id.dialog_cp_pass_old);
            TextView pass1 = layout.findViewById(R.id.dialog_cp_pass_one);
            TextView pass2 = layout.findViewById(R.id.dialog_cp_pass_two);
            TextInputLayout input1 = layout.findViewById(R.id.dialog_cp_input_one);
            TextInputLayout input2 = layout.findViewById(R.id.dialog_cp_input_two);

            MaterialDialog.Builder builder = CustomMaterialDialog.customDialog("Change your password", this, layout)
                    .onNegative((dialog, which) -> dialog.dismiss())
                    .onPositive((dialog, which) -> {

                        dialog.dismiss();
                        String oldPass = oldpass.getText().toString();
                        String pass1text = pass1.getText().toString();
                        String pass2text = pass2.getText().toString();
                        PasswordValidate passwordValidate = new PasswordValidate();
                        if (passwordValidate.isValid(pass1text)) {
                            if (pass1text.equals(pass2text)) {
                                viewModel.changePassword(oldPass, pass1text);
                            } else {
                                input1.setError("Passwords does not equal");
                                input2.setError("Passwords does not equal");
                            }
                        } else {
                            input1.setError(passwordValidate.getErrorMessage());
                            input2.setError(passwordValidate.getErrorMessage());
                        }
                    });
            showCustomDialog(builder);
        });

        RxBus.subscribe(RxBus.TRY_CHANGE_NICK, this, o -> {
            MaterialDialog.Builder builder = CustomMaterialDialog.emptyDialog("Change nickname", this)
                    .input("new nickname", null, (dialog, input) -> {
                    })
                    .onNegative((dialog, which) -> dialog.dismiss())
                    .onPositive((dialog, which) -> {
                        String nick = dialog.getInputEditText().getText().toString();
                        viewModel.changeNick(nick);
                        dialog.dismiss();
                    });
            showCustomDialog(builder);
        });

        RxBus.subscribe(RxBus.TRY_CHANGE_PHOTO, this, o -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, READ_REQUEST_CODE);
        });


        RxBus.subscribe(RxBus.TRY_DELETE_ACCOUNT, this, o -> {

        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                Uri uri = resultData.getData();
                if (uri != null) {
                    viewModel.changePhoto(uri);
                }
            }
        }
    }

    private void onProgress(Boolean progress) {
        if (progress) {
            MaterialDialog.Builder mDialog = CustomMaterialDialog.loading("Loading...", this)
                    .onNegative((dialog, which) -> {
                        viewModel.cancel();
                        dialog.dismiss();
                    });
            showCustomDialog(mDialog);
        }
    }

    private void onLogoutSuccess(Boolean aBoolean) {
        if (aBoolean) {
            viewModel.startLogin(this, true);
        }
    }

    private LinearLayout getLayoutForDialog(@LayoutRes int layout, @IdRes int root) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View stdView = factory.inflate(layout, null);
        return stdView.findViewById(root);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_settings;
    }

}
