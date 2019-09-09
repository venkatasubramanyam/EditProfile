package com.sparknetwork.editprofile.interactor;

import com.google.firebase.auth.FirebaseUser;
import com.sparknetwork.editprofile.RxResources;
import com.sparknetwork.editprofile.repository.firebase.FirebaseAuthRepositoryType;
import com.sparknetwork.editprofile.repository.firebase.FirebaseDatabaseType;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;
import io.reactivex.Observable;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ChangeProfileDataInteractorTest {

    @ClassRule
    public static RxResources rxres = new RxResources();

    @Mock
    FirebaseAuthRepositoryType firebaseAuthRepositoryType;

    @Mock
    FirebaseDatabaseType firebaseDatabaseType;

    @Mock
    FirebaseUser firebaseUser;

    @Mock
    Throwable error;

    private static final String UID = "123";

    @InjectMocks
    ChangeProfileDataInteractor changeProfileDataInteractor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void changeUser() {
        when(firebaseAuthRepositoryType.getCurrentUser()).thenReturn(Observable.just(firebaseUser));
        when(firebaseUser.getUid()).thenReturn(UID);
        when(firebaseAuthRepositoryType.changeUserNick(firebaseUser, "test")).thenReturn(Completable.complete());
        when(firebaseDatabaseType.updateNick(UID, "test")).thenReturn(Completable.complete());

        changeProfileDataInteractor.changeNick("test")
                .test()
                .assertNoErrors()
                .assertComplete();

        verify(firebaseAuthRepositoryType).changeUserNick(firebaseUser, "test");
        verify(firebaseDatabaseType).updateNick(UID, "test");

    }

    @Test
    public void changeUserError() {
        when(firebaseAuthRepositoryType.getCurrentUser()).thenReturn(Observable.just(firebaseUser));
        when(firebaseUser.getUid()).thenReturn(UID);
        when(firebaseAuthRepositoryType.changeUserNick(firebaseUser, "test")).thenReturn(Completable.error(error));
        when(firebaseDatabaseType.updateNick(UID, "test")).thenReturn(Completable.complete());

        changeProfileDataInteractor.changeNick("test")
                .test()
                .assertNotComplete()
                .assertError(error);

        verify(firebaseAuthRepositoryType).changeUserNick(firebaseUser, "test");
        verify(firebaseDatabaseType, never()).updateNick(UID, "test");

    }

    @Test
    public void changeUserDBError() {
        when(firebaseAuthRepositoryType.getCurrentUser()).thenReturn(Observable.just(firebaseUser));
        when(firebaseUser.getUid()).thenReturn(UID);
        when(firebaseAuthRepositoryType.changeUserNick(firebaseUser, "test")).thenReturn(Completable.complete());
        when(firebaseDatabaseType.updateNick(UID, "test")).thenReturn(Completable.error(error));

        changeProfileDataInteractor.changeNick("test")
                .test()
                .assertError(error);

        verify(firebaseAuthRepositoryType).changeUserNick(firebaseUser, "test");
        verify(firebaseDatabaseType).updateNick(UID, "test");

    }

}