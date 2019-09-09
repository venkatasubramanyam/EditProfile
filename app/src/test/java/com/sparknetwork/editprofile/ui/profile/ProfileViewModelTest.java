package com.sparknetwork.editprofile.ui.profile;

import android.content.Context;
import android.net.Uri;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;

import com.sparknetwork.editprofile.entity.ErrorCarrier;
import com.sparknetwork.editprofile.entity.UserProfile;
import com.sparknetwork.editprofile.interactor.ChangeProfileDataInteractor;
import com.sparknetwork.editprofile.interactor.ChangeProfilePhotoInteractor;
import com.sparknetwork.editprofile.interactor.ChangeUserPasswordInteractor;
import com.sparknetwork.editprofile.interactor.DeleteAccountInteractor;
import com.sparknetwork.editprofile.interactor.LogoutUserInteractor;
import com.sparknetwork.editprofile.router.LoginRouter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;
import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProfileViewModelTest {

    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();

    @Mock
    LifecycleOwner lifecycleOwner;
    @Mock
    LogoutUserInteractor logoutUserInteractor;
    @Mock
    ChangeUserPasswordInteractor changeUserPasswordInteractor;
    @Mock
    LoginRouter loginRouter;
    @Mock
    ChangeProfileDataInteractor changeProfileDataInteractor;
    @Mock
    ChangeProfilePhotoInteractor changeProfilePhotoInteractor;
    @Mock
    DeleteAccountInteractor deleteAccountInteractor;
    @Mock
    Context context;
    @Mock
    Uri uri;

    @InjectMocks
    ProfileViewModel viewModel;

    private UserProfile userProfile;

    private LifecycleRegistry lifecycleRegistry;
    private Observer<Boolean> progressObserver;
    private Observer<ErrorCarrier> errorObserver;
    private Observer<Boolean> logoutObserver;
    private Observer<Boolean> passChangedObserver;
    private Observer<Boolean> nickChangeObserver;
    private Observer<Boolean> photoChangeObserver;
    private Observer<Boolean> accountDeleteObserver;
    private Throwable throwable;

    private static final String OLD_PASS = "pass123";
    private static final String NEW_PASS = "123pass";
    private static final String NICK = "nick";
    private static final String PASS = "test123";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        lifecycleRegistry = new LifecycleRegistry(lifecycleOwner);
        when(lifecycleOwner.getLifecycle()).thenReturn(lifecycleRegistry);
        lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
        userProfile = new UserProfile("test", "photoUrl");

        progressObserver = (Observer<Boolean>) mock(Observer.class);
        errorObserver = (Observer<ErrorCarrier>) mock(Observer.class);
        logoutObserver = (Observer<Boolean>) mock(Observer.class);
        passChangedObserver = (Observer<Boolean>) mock(Observer.class);
        nickChangeObserver = (Observer<Boolean>) mock(Observer.class);
        photoChangeObserver = (Observer<Boolean>) mock(Observer.class);
        accountDeleteObserver = (Observer<Boolean>) mock(Observer.class);

        throwable = new Throwable();
    }

    @Test
    public void logoutUser() {
        when(logoutUserInteractor.logout()).thenReturn(Completable.complete());

        viewModel.progress().observe(lifecycleOwner, progressObserver);
        viewModel.logout().observe(lifecycleOwner, logoutObserver);
        viewModel.logoutUser();

        verify(logoutUserInteractor).logout();
        verify(logoutObserver).onChanged(true);
        verify(progressObserver, times(2)).onChanged(anyBoolean());

        InOrder inOrder = Mockito.inOrder(progressObserver);
        inOrder.verify(progressObserver).onChanged(true);
        inOrder.verify(progressObserver).onChanged(false);
    }

    @Test
    public void logoutUserError() {
        when(logoutUserInteractor.logout()).thenReturn(Completable.error(throwable));

        viewModel.progress().observe(lifecycleOwner, progressObserver);
        viewModel.logout().observe(lifecycleOwner, logoutObserver);
        viewModel.error().observe(lifecycleOwner, errorObserver);
        viewModel.logoutUser();

        verify(logoutUserInteractor).logout();
        verify(logoutObserver, never()).onChanged(anyBoolean());
        verify(progressObserver, times(2)).onChanged(anyBoolean());
        verify(errorObserver).onChanged(any(ErrorCarrier.class));

        InOrder inOrder = Mockito.inOrder(progressObserver);
        inOrder.verify(progressObserver).onChanged(true);
        inOrder.verify(progressObserver).onChanged(false);

    }

    @Test
    public void changePassword() {
        when(changeUserPasswordInteractor.changeUserPassword(OLD_PASS, NEW_PASS)).thenReturn(Completable.complete());

        viewModel.progress().observe(lifecycleOwner, progressObserver);
        viewModel.passChange().observe(lifecycleOwner, passChangedObserver);
        viewModel.changePassword(OLD_PASS, NEW_PASS);

        verify(changeUserPasswordInteractor).changeUserPassword(OLD_PASS, NEW_PASS);
        verify(passChangedObserver).onChanged(true);
        verify(progressObserver, times(2)).onChanged(anyBoolean());

        InOrder inOrder = Mockito.inOrder(progressObserver);
        inOrder.verify(progressObserver).onChanged(true);
        inOrder.verify(progressObserver).onChanged(false);

    }

    @Test
    public void changePasswordError() {
        when(changeUserPasswordInteractor.changeUserPassword(OLD_PASS, NEW_PASS)).thenReturn(Completable.error(throwable));

        viewModel.progress().observe(lifecycleOwner, progressObserver);
        viewModel.passChange().observe(lifecycleOwner, passChangedObserver);
        viewModel.error().observe(lifecycleOwner, errorObserver);
        viewModel.changePassword(OLD_PASS, NEW_PASS);

        verify(changeUserPasswordInteractor).changeUserPassword(OLD_PASS, NEW_PASS);
        verify(passChangedObserver, never()).onChanged(anyBoolean());
        verify(progressObserver, times(2)).onChanged(anyBoolean());
        verify(errorObserver).onChanged(any(ErrorCarrier.class));

        InOrder inOrder = Mockito.inOrder(progressObserver);
        inOrder.verify(progressObserver).onChanged(true);
        inOrder.verify(progressObserver).onChanged(false);

    }

    @Test
    public void changeNick() {
        when(changeProfileDataInteractor.changeNick(NICK)).thenReturn(Completable.complete());

        viewModel.progress().observe(lifecycleOwner, progressObserver);
        viewModel.nickChange().observe(lifecycleOwner, nickChangeObserver);
        viewModel.changeNick(NICK);

        verify(changeProfileDataInteractor).changeNick(NICK);
        verify(nickChangeObserver).onChanged(true);
        verify(progressObserver, times(2)).onChanged(anyBoolean());

        InOrder inOrder = Mockito.inOrder(progressObserver);
        inOrder.verify(progressObserver).onChanged(true);
        inOrder.verify(progressObserver).onChanged(false);
    }

    @Test
    public void changeNickError() {
        when(changeProfileDataInteractor.changeNick(NICK)).thenReturn(Completable.error(throwable));

        viewModel.progress().observe(lifecycleOwner, progressObserver);
        viewModel.nickChange().observe(lifecycleOwner, nickChangeObserver);
        viewModel.error().observe(lifecycleOwner, errorObserver);
        viewModel.changeNick(NICK);

        verify(changeProfileDataInteractor).changeNick(NICK);
        verify(nickChangeObserver, never()).onChanged(anyBoolean());
        verify(errorObserver).onChanged(any(ErrorCarrier.class));
        verify(progressObserver, times(2)).onChanged(anyBoolean());

        InOrder inOrder = Mockito.inOrder(progressObserver);
        inOrder.verify(progressObserver).onChanged(true);
        inOrder.verify(progressObserver).onChanged(false);

    }

    @Test
    public void changePhoto() {
        when(changeProfilePhotoInteractor.changeProfilePhoto(uri)).thenReturn(Completable.complete());

        viewModel.progress().observe(lifecycleOwner, progressObserver);
        viewModel.photoChange().observe(lifecycleOwner, photoChangeObserver);
        viewModel.changePhoto(uri);

        verify(changeProfilePhotoInteractor).changeProfilePhoto(uri);
        verify(photoChangeObserver).onChanged(true);
        verify(progressObserver, times(2)).onChanged(anyBoolean());

        InOrder inOrder = Mockito.inOrder(progressObserver);
        inOrder.verify(progressObserver).onChanged(true);
        inOrder.verify(progressObserver).onChanged(false);
    }

    @Test
    public void changePhotoError() {
        when(changeProfilePhotoInteractor.changeProfilePhoto(uri)).thenReturn(Completable.error(throwable));

        viewModel.progress().observe(lifecycleOwner, progressObserver);
        viewModel.photoChange().observe(lifecycleOwner, photoChangeObserver);
        viewModel.error().observe(lifecycleOwner, errorObserver);
        viewModel.changePhoto(uri);

        verify(changeProfilePhotoInteractor).changeProfilePhoto(uri);
        verify(photoChangeObserver, never()).onChanged(anyBoolean());
        verify(progressObserver, times(2)).onChanged(anyBoolean());
        verify(errorObserver).onChanged(any(ErrorCarrier.class));

        InOrder inOrder = Mockito.inOrder(progressObserver);
        inOrder.verify(progressObserver).onChanged(true);
        inOrder.verify(progressObserver).onChanged(false);
    }

    @Test
    public void deleteAccount() {
        when(deleteAccountInteractor.delete(PASS)).thenReturn(Completable.complete());

        viewModel.progress().observe(lifecycleOwner, progressObserver);
        viewModel.accountDelete().observe(lifecycleOwner, accountDeleteObserver);
        viewModel.deleteAccount(PASS);

        verify(deleteAccountInteractor).delete(PASS);
        verify(accountDeleteObserver).onChanged(true);
        verify(progressObserver, times(2)).onChanged(anyBoolean());

        InOrder inOrder = Mockito.inOrder(progressObserver);
        inOrder.verify(progressObserver).onChanged(true);
        inOrder.verify(progressObserver).onChanged(false);
    }

    @Test
    public void deleteAccounError() {
        when(deleteAccountInteractor.delete(PASS)).thenReturn(Completable.error(throwable));

        viewModel.progress().observe(lifecycleOwner, progressObserver);
        viewModel.accountDelete().observe(lifecycleOwner, accountDeleteObserver);
        viewModel.error().observe(lifecycleOwner, errorObserver);
        viewModel.deleteAccount(PASS);

        verify(deleteAccountInteractor).delete(PASS);
        verify(accountDeleteObserver, never()).onChanged(anyBoolean());
        verify(progressObserver, times(2)).onChanged(anyBoolean());
        verify(errorObserver).onChanged(any(ErrorCarrier.class));

        InOrder inOrder = Mockito.inOrder(progressObserver);
        inOrder.verify(progressObserver).onChanged(true);
        inOrder.verify(progressObserver).onChanged(false);

    }

    @Test
    public void startLogin() {
        viewModel.startLogin(context, true);
        verify(loginRouter).open(context, true);
    }


    @Test
    public void getUserDetails() {
        when(changeProfileDataInteractor.getUserDetails()).thenReturn(Single.just(userProfile));
        viewModel.getUserInfo();
        verify(changeProfileDataInteractor).getUserDetails();
    }

}