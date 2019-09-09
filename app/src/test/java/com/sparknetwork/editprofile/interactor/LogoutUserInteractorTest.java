package com.sparknetwork.editprofile.interactor;

import com.sparknetwork.editprofile.RxResources;
import com.sparknetwork.editprofile.repository.firebase.FirebaseAuthRepositoryType;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class LogoutUserInteractorTest {

    @ClassRule
    public static RxResources rxres = new RxResources();

    @Mock
    FirebaseAuthRepositoryType firebaseAuthRepositoryType;

    @Mock
    Throwable error;

    @InjectMocks
    LogoutUserInteractor interactor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void logout() {
        when(firebaseAuthRepositoryType.logoutUser()).thenReturn(Completable.complete());

        interactor.logout()
                .test()
                .assertNoErrors()
                .assertComplete();

        verify(firebaseAuthRepositoryType).logoutUser();
    }

    @Test
    public void logoutError() {
        when(firebaseAuthRepositoryType.logoutUser()).thenReturn(Completable.error(error));

        interactor.logout()
                .test()
                .assertNotComplete()
                .assertNoValues()
                .assertError(error);

        verify(firebaseAuthRepositoryType).logoutUser();
    }
}