package br.com.miller.farmaciaatendente.register.models;

import android.graphics.Bitmap;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.register.tasks.RegisterTasks;
import br.com.miller.farmaciaatendente.utils.images.FirebaseImageUtils;
import br.com.miller.farmaciaatendente.utils.StringUtils;
import br.com.miller.farmaciaatendente.utils.tasks.FirebaseImageTask;

public class RegisterModel implements FirebaseImageTask.Model {

    private RegisterTasks.Model model;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseImageUtils firebaseImageUtils;
    private  User user;

    public RegisterModel(RegisterTasks.Model model) {

        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseImageUtils = new FirebaseImageUtils(this);
    }

    public void firstUser(){

        if(firebaseAuth.getCurrentUser() == null) {
            firebaseAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    model.onAnonimousSuccess();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NotNull Exception e) {
                    model.onAnonimousFailed();
                }
            });
        }else{
            firebaseAuth.signOut();
            this.firstUser();
        }
    }

    public void createUser(String name, String surname, String bithDate, String email, String phone, String password){

        User user = new User();

        user.setName(name);
        user.setCity("Lavras");
        user.setSurname(surname);
        user.setBirth_date(StringUtils.parseDate(bithDate));
        user.setEmail(email);
        user.setPhone(phone);

        model.onUserGenerateSuccess(user, password);

    }

    public void registerUser(final User user, String password){

        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), password);

        firebaseAuth.signInWithCredential(authCredential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                user.setId_firebase(authResult.getUser().getUid());

                model.onCredentialSuccess(user);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NotNull Exception e) {
                model.onCredentialFailed();
            }
        });

    }

    public void registerOnDataBaseUser(final User user){

        Map<String, Object> map = new HashMap<>();

        map.put(user.getId_firebase(), user);

        firebaseDatabase.getReference()
                .child("users")
                .child(user.getCity())
                .updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) { registerCity(user); }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NotNull Exception e) { model.onUserDatabaseFailed(); }
                });
    }

    private void registerCity(final User user){

        Map<String, Object> map = new HashMap<>();

        map.put(user.getId_firebase(), user.getCity());

        this.user = user;

        firebaseDatabase.getReference().child("city").updateChildren(map).
                addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) { model.onUserDatabaseSuccess(user); }
        })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NotNull Exception e) { model.onUserDatabaseFailed(); }
        });
    }

    public void destroyUser(){
        if(firebaseAuth.getCurrentUser() != null)
            if(firebaseAuth.getCurrentUser().isAnonymous())
                firebaseAuth.getCurrentUser().delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) { model.onAnonimousSuccess(); }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NotNull Exception e) { model.onAnonimousFailed();
                    }
                });

    }

    @Override
    public void uploadImage(String type, String city, String image, Bitmap bitmap) { firebaseImageUtils.uploadImage(type, city, image, bitmap);}

    @Override
    public void downloadImage(String type, String city, String image) { }

    @Override
    public void onImageUploadSuccess(Bitmap bitmap) { model.onImageUploadSuccess(bitmap);}

    @Override
    public void onImageUploadFailed() { model.onImageUploadFailed(); }

    @Override
    public void onImageDownloadSuccess(Bitmap bitmap) { }

    @Override
    public void onImageDownloadFailed() { }

    public User getUser() { return user; }
}
