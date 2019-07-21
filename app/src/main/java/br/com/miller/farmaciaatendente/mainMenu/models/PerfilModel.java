package br.com.miller.farmaciaatendente.mainMenu.models;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.mainMenu.tasks.PersonalPerfilTasks;
import br.com.miller.farmaciaatendente.utils.FirebaseImageUtils;
import br.com.miller.farmaciaatendente.utils.tasks.FirebaseImageTask;

public class PerfilModel implements FirebaseImageTask.Model {

    private PersonalPerfilTasks.Model model;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseImageUtils firebaseImageUtils;
    private User user;

    public PerfilModel(PersonalPerfilTasks.Model model) {

        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseImageUtils = new FirebaseImageUtils(this);
    }


    public void getUserData(String firebaseId, String city){

        firebaseDatabase.getReference()
                .child("users")
                .child(city)
                .child(firebaseId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()) {

                            user = dataSnapshot.getValue(User.class);
                            model.onUserDataSuccess(user);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                            model.onUserDataFailed();
                    }
                });
    }

    public void updateUserData(final User user){

        Map<String, Object> map = new HashMap<>();

        map.put(user.getId_firebase(), user);

        this.user = user;

        firebaseDatabase.getReference()
                .child("users")
                .child(user.getCity())
                .updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        model.onUpdateUserSuccess(user);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                model.onUpdateUserFailed();
            }
        });
    }

    @Override
    public void uploadImage(String type, String city, String image, Bitmap bitmap) { firebaseImageUtils.uploadImage(type, city, image, bitmap);}

    @Override
    public void downloadImage(String type, String city, String image) { firebaseImageUtils.downloadImage(type, city, image);}

    @Override
    public void onImageUploadSuccess(Bitmap bitmap) { model.onImageUploadSuccess(bitmap);}

    @Override
    public void onImageUploadFailed() { model.onImageUploadFailed(); }

    @Override
    public void onImageDownloadSuccess(Bitmap bitmap) { model.onImageDownloadSuccess(bitmap); }

    @Override
    public void onImageDownloadFailed() { model.onImageDownloadFailed(); }

    public User getUser() { return user; }
}
