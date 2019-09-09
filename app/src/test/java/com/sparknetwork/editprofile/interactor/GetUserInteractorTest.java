package com.sparknetwork.editprofile.interactor;

import com.sparknetwork.editprofile.RxResources;
import com.sparknetwork.editprofile.repository.firebase.FirebaseAuthRepositoryType;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class GetUserInteractorTest {

    @ClassRule
    public static RxResources rxres = new RxResources();

    @Mock
    FirebaseAuthRepositoryType firebaseAuthRepositoryType;

    @Mock
    FirebaseUser firebaseUser;

    @Mock
    Throwable error;

    @InjectMocks
    GetUserInteractor interactor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUser() {
        when(firebaseAuthRepositoryType.getCurrentUser()).thenReturn(Observable.just(firebaseUser));

        FirebaseUser f = interactor.getUser()
                .test()
                .assertNoErrors()
                .assertValueCount(1)
                .values()
                .get(0);

        assertEquals(firebaseUser, f);
        verify(firebaseAuthRepositoryType).getCurrentUser();
    }

    @Test
    public void getUserError() {
        when(firebaseAuthRepositoryType.getCurrentUser()).thenReturn(Observable.error(error));

        interactor.getUser()
                .test()
                .assertNotComplete()
                .assertNoValues()
                .assertError(error);

        verify(firebaseAuthRepositoryType).getCurrentUser();
    }

}