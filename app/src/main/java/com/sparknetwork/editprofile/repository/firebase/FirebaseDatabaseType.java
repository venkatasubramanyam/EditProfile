package com.sparknetwork.editprofile.repository.firebase;

import com.sparknetwork.editprofile.entity.CitiesListItem;
import com.sparknetwork.editprofile.entity.ListItem;
import com.sparknetwork.editprofile.entity.UserProfile;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface FirebaseDatabaseType {


    Completable updateNick(String uid, String newNick);

    Completable updateProfile(String uid, UserProfile userProfile);

    Single<List<ListItem>> getEthnicityList();

    Single<List<ListItem>> getFigureList();

    Single<List<ListItem>> getGenderList();

    Single<List<ListItem>> getMaritalStatusList();

    Single<List<ListItem>> getReligion();

    Single<UserProfile> getUserProfile(String uid);

    Single<List<CitiesListItem>> getCityList();

}
