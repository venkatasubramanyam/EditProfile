package com.sparknetwork.editprofile.ui.profile;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.sparknetwork.editprofile.base.BaseViewModel;
import com.sparknetwork.editprofile.entity.CitiesListItem;
import com.sparknetwork.editprofile.entity.ListItem;
import com.sparknetwork.editprofile.entity.UserProfile;
import com.sparknetwork.editprofile.interactor.ChangeProfileDataInteractor;
import com.sparknetwork.editprofile.interactor.ChangeProfilePhotoInteractor;
import com.sparknetwork.editprofile.interactor.ChangeUserPasswordInteractor;
import com.sparknetwork.editprofile.interactor.DeleteAccountInteractor;
import com.sparknetwork.editprofile.interactor.LogoutUserInteractor;
import com.sparknetwork.editprofile.router.LoginRouter;

import java.util.List;

class ProfileViewModel extends BaseViewModel {

    private final MutableLiveData<Boolean> logout = new MutableLiveData<>();
    private final MutableLiveData<Boolean> passChanged = new MutableLiveData<>();
    private final MutableLiveData<Boolean> nickChange = new MutableLiveData<>();
    private final MutableLiveData<Boolean> profileUpdate = new MutableLiveData<>();
    private final MutableLiveData<Boolean> photoChange = new MutableLiveData<>();
    private final MutableLiveData<Boolean> accountDelete = new MutableLiveData<>();
    private final MutableLiveData<UserProfile> userInfoReceived = new MutableLiveData<>();
    private final MutableLiveData<List<ListItem>> ethnicityReceived = new MutableLiveData<>();
    private final MutableLiveData<List<ListItem>> genderData = new MutableLiveData<>();
    private final MutableLiveData<List<ListItem>> religionData = new MutableLiveData<>();
    private final MutableLiveData<List<ListItem>> maritalStatusData = new MutableLiveData<>();
    private final MutableLiveData<List<ListItem>> figureData = new MutableLiveData<>();
    private final MutableLiveData<List<CitiesListItem>> cityData = new MutableLiveData<>();

    private final LogoutUserInteractor logoutUserInteractor;
    private final ChangeUserPasswordInteractor changeUserPasswordInteractor;
    private final LoginRouter loginRouter;
    private final ChangeProfileDataInteractor changeProfileDataInteractor;
    private final ChangeProfilePhotoInteractor changeProfilePhotoInteractor;
    private final DeleteAccountInteractor deleteAccountInteractor;

    ProfileViewModel(LogoutUserInteractor logoutUserInteractor,
                     ChangeUserPasswordInteractor changeUserPasswordInteractor,
                     LoginRouter loginRouter,
                     ChangeProfileDataInteractor changeProfileDataInteractor,
                     ChangeProfilePhotoInteractor changeProfilePhotoInteractor,
                     DeleteAccountInteractor deleteAccountInteractor) {
        this.logoutUserInteractor = logoutUserInteractor;
        this.changeUserPasswordInteractor = changeUserPasswordInteractor;
        this.loginRouter = loginRouter;
        this.changeProfileDataInteractor = changeProfileDataInteractor;
        this.changeProfilePhotoInteractor = changeProfilePhotoInteractor;
        this.deleteAccountInteractor = deleteAccountInteractor;

    }

    void logoutUser() {
        progress.setValue(true);
        disposable = logoutUserInteractor.logout()
                .subscribe(this::onLogout, this::onError);
    }

    void changePassword(String oldPass, String newPass) {
        progress.setValue(true);
        disposable = changeUserPasswordInteractor.changeUserPassword(oldPass, newPass)
                .subscribe(this::onPassChange, this::onError);
    }

    void changeNick(String newNick) {
        progress.setValue(true);
        disposable = changeProfileDataInteractor.changeNick(newNick)
                .subscribe(this::onNick, this::onError);
    }

    void updateProfileData(UserProfile userProfile) {
        progress.setValue(true);
        disposable = changeProfileDataInteractor.updateProfile(userProfile)
                .subscribe(this::onUpdate, this::onError);
    }

    void changePhoto(Uri uri) {
        progress.setValue(true);
        disposable = changeProfilePhotoInteractor.changeProfilePhoto(uri)
                .subscribe(this::onPhotoChanged, this::onError);

    }

    void deleteAccount(String password) {
        progress.setValue(true);
        disposable = deleteAccountInteractor.delete(password)
                .subscribe(this::onAccountDeleted, this::onError);
    }

    private void onAccountDeleted() {
        progress.setValue(false);
        accountDelete.setValue(true);
    }

    private void onPhotoChanged() {
        progress.setValue(false);
        photoChange.setValue(true);
    }

    private void onNick() {
        progress.setValue(false);
        nickChange.setValue(true);
    }

    private void onUpdate() {
        progress.setValue(false);
        profileUpdate.setValue(true);
    }

    private void onPassChange() {
        progress.setValue(false);
        passChanged.setValue(true);
    }

    private void onLogout() {
        progress.setValue(false);
        logout.setValue(true);
    }

    void getUserInfo() {
        disposable = changeProfileDataInteractor
                .getUserDetails()
                .subscribe(this::onUser, this::onError);
    }

    void getEthnicity() {
        disposable = changeProfileDataInteractor
                .getEthnicity()
                .subscribe(this::onEthnicity, this::onError);
    }

    private void onUser(UserProfile userProfile) {
        userInfoReceived.setValue(userProfile);
    }

    private void onEthnicity(List<ListItem> ethnicityDetails) {
        ethnicityReceived.setValue(ethnicityDetails);
    }

    private void onGenderDataReceive(List<ListItem> genderList) {
        genderData.setValue(genderList);
    }

    void getGenderList() {
        disposable = changeProfileDataInteractor
                .getGender()
                .subscribe(this::onGenderDataReceive, this::onError);
    }

    private void onReligionDataReceive(List<ListItem> genderList) {
        religionData.setValue(genderList);
    }

    void getReligionData() {
        disposable = changeProfileDataInteractor
                .getReligion()
                .subscribe(this::onReligionDataReceive, this::onError);
    }

    private void onMaritalStatusDataReceive(List<ListItem> genderList) {
        maritalStatusData.setValue(genderList);
    }

    void getMaritalStatusData() {
        disposable = changeProfileDataInteractor
                .getMaritalStatus()
                .subscribe(this::onMaritalStatusDataReceive, this::onError);
    }

    private void onFigureDataReceive(List<ListItem> genderList) {
        figureData.setValue(genderList);
    }

    void getFigureData() {
        disposable = changeProfileDataInteractor
                .getFigure()
                .subscribe(this::onFigureDataReceive, this::onError);
    }

    private void onCityDataReceive(List<CitiesListItem> cityList) {
        cityData.setValue(cityList);
    }

    void getCityData() {
        disposable = changeProfileDataInteractor
                .getCities()
                .subscribe(this::onCityDataReceive, this::onError);
    }


    void startLogin(Context context, boolean clearstack) {
        loginRouter.open(context, clearstack);
    }

    MutableLiveData<Boolean> logout() {
        return logout;
    }

    MutableLiveData<Boolean> passChange() {
        return passChanged;
    }

    MutableLiveData<Boolean> nickChange() {
        return nickChange;
    }

    MutableLiveData<Boolean> photoChange() {
        return photoChange;
    }

    MutableLiveData<Boolean> accountDelete() {
        return accountDelete;
    }

    MutableLiveData<UserProfile> userInfoReceived() {
        return userInfoReceived;
    }

    MutableLiveData<List<ListItem>> ethnicityReceived() {
        return ethnicityReceived;
    }

    MutableLiveData<List<ListItem>> getGender() {
        return genderData;
    }

    MutableLiveData<List<ListItem>> getFigureList() {
        return figureData;
    }

    MutableLiveData<List<ListItem>> getReligionList() {
        return religionData;
    }

    MutableLiveData<List<ListItem>> getMaritalStatusList() {
        return maritalStatusData;
    }

    MutableLiveData<List<CitiesListItem>> getCityList() {
        return cityData;
    }


}


