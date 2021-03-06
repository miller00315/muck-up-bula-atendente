package br.com.miller.farmaciaatendente.mainMenu.models;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import br.com.miller.farmaciaatendente.domain.Store;
import br.com.miller.farmaciaatendente.mainMenu.tasks.StorePerfilTasks;
import br.com.miller.farmaciaatendente.utils.images.FirebaseImageUtils;
import br.com.miller.farmaciaatendente.utils.tasks.FirebaseImageTask;

public class StorePerfilModel implements FirebaseImageTask.Model {

    private StorePerfilTasks.Model model;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseImageUtils firebaseImageUtils;
    private Store store;

    public StorePerfilModel(StorePerfilTasks.Model model) {
        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseImageUtils = new FirebaseImageUtils(this);
    }

    public void getStore(String storeId, String city){

        firebaseDatabase.getReference()
                .child("stores")
                .child(city)
                .child(storeId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                            store = dataSnapshot.getValue(Store.class);
                            if(store != null)
                                model.onStoreDataSuccess(store);
                        else
                            model.onStoreDataFailed();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        model.onStoreDataFailed();
                    }
                });

    }

    public void updateStore(final Store s){

        Map<String, Object> map = new HashMap<>();

        map.put(String.valueOf(store.getId()), store);

        firebaseDatabase.getReference()
                .child("stores")
                .child(store.getCity())
                .updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        store = s;
                        model.onUpdateStoreSuccess(s);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        model.onUpdateStoreFailed();
                    }
                });

    }

    @Override
    public void uploadImage(String type, String city, String image, Bitmap bitmap) {
        Log.wtf("test",image);
        firebaseImageUtils.uploadImage(type, city, image, bitmap);}

    @Override
    public void downloadImage(String type, String city, String image) { firebaseImageUtils.downloadImage(type, city, image);}

    @Override
    public void onImageUploadSuccess(Bitmap bitmap) { model.onUploadImageSuccess(bitmap);}

    @Override
    public void onImageUploadFailed() { model.onUploadImageFailed();}

    @Override
    public void onImageDownloadSuccess(Bitmap bitmap) { model.onImageDownloadSuccess(bitmap);}

    @Override
    public void onImageDownloadFailed() { model.onImageDownloadFailed();}

    public Store getStore() { return store; }
}
