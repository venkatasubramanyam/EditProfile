package com.sparknetwork.editprofile.repository.firebase.rxwrapper;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

public final class FirebaseDBWrapper {

    public static Flowable<DocumentSnapshot> observeDocumentReference(@NonNull final DocumentReference reference,
                                                                      @NonNull final MetadataChanges changes,
                                                                      @NonNull final Activity activity){
        return observeDocumentReference(reference, changes,BackpressureStrategy.DROP,activity);
    }

    public static Flowable<DocumentSnapshot> observeDocumentReference(@NonNull final DocumentReference reference,
                                                                      @NonNull BackpressureStrategy strategy,
                                                                          @NonNull final Activity activity){
        return observeDocumentReference(reference, MetadataChanges.EXCLUDE,strategy, activity);
    }

    public static Flowable<DocumentSnapshot> observeDocumentReference(@NonNull final DocumentReference reference,
                                                                      @NonNull final Activity activity){
        return observeDocumentReference(reference, MetadataChanges.EXCLUDE,BackpressureStrategy.DROP,
                activity);
    }

    public static Flowable<DocumentSnapshot> observeDocumentReference(@NonNull final DocumentReference reference,
                                                                      @NonNull final MetadataChanges changes){
        return observeDocumentReference(reference, changes,BackpressureStrategy.DROP);
    }

    public static Flowable<DocumentSnapshot> observeDocumentReference(@NonNull final DocumentReference reference,
                                                                      @NonNull BackpressureStrategy strategy){
        return observeDocumentReference(reference, MetadataChanges.EXCLUDE,strategy);
    }

    public static Flowable<DocumentSnapshot> observeDocumentReference(@NonNull final DocumentReference reference){
        return observeDocumentReference(reference, MetadataChanges.EXCLUDE,BackpressureStrategy.DROP);
    }

    public static Flowable<DocumentSnapshot> observeDocumentReference(@NonNull final DocumentReference reference,
                                                                      @NonNull final MetadataChanges metadataChanges,
                                                                      @NonNull BackpressureStrategy strategy){
        return Flowable.create(emitter -> {
            final ListenerRegistration registration = reference.addSnapshotListener(metadataChanges, (documentSnapshot, error) -> {
                if(error != null && !emitter.isCancelled()){
                    emitter.onError(error);
                    return;
                }
                emitter.onNext(documentSnapshot);
            });
            emitter.setCancellable(registration::remove);
        }, strategy);
    }

   public static Flowable<DocumentSnapshot> observeDocumentReference(@NonNull final DocumentReference reference,
                                                                     @NonNull final MetadataChanges metadataChanges,
                                                                     @NonNull BackpressureStrategy strategy,
                                                                     @NonNull final Activity activity){
        return Flowable.create(emitter -> {
            final ListenerRegistration registration = reference.addSnapshotListener(activity,metadataChanges, (documentSnapshot, error) -> {
                if(error != null && !emitter.isCancelled()){
                    emitter.onError(error);
                    return;
                }
                emitter.onNext(documentSnapshot);
            });
            emitter.setCancellable(registration::remove);
        }, strategy);
   }

   public static Maybe<QuerySnapshot> getCollection(CollectionReference ref){
        return Maybe.create(emitter -> ref.get().addOnSuccessListener(queryDocumentSnapshots -> {
            System.out.println("docs:"+queryDocumentSnapshots.getDocuments());
            if(queryDocumentSnapshots.isEmpty()){
                emitter.onComplete();
            }else{
                emitter.onSuccess(queryDocumentSnapshots);
            }
        }).addOnFailureListener(e -> {
            if(!emitter.isDisposed()){
                emitter.onError(e);
            }
        }));
   }

   public static Maybe<QuerySnapshot> getCollection(Query query){
        return Maybe.create(emitter -> query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if(queryDocumentSnapshots.isEmpty()){
                emitter.onComplete();
            }else{
                emitter.onSuccess(queryDocumentSnapshots);
            }
        }).addOnFailureListener(e -> {
            if(!emitter.isDisposed()){
                emitter.onError(e);
            }
        }));
   }

   public static Completable updateDocument(DocumentReference ref, Map<String,Object> fieldsAndValues){
        final Task<Void> task = ref.update(fieldsAndValues);
        return Completable.create(emitter -> CompletableTask.assign(emitter, task));
   }

   public static Completable updateDocument(DocumentReference ref, String field, FieldValue fieldValue) {
       final Task<Void> task = ref.update(field, fieldValue);
       return Completable.create(emitter -> CompletableTask.assign(emitter, task));
   }

}
