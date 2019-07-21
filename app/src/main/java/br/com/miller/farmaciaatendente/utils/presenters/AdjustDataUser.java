package br.com.miller.farmaciaatendente.utils.presenters;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.utils.Constants;
import br.com.miller.farmaciaatendente.utils.tasks.AdjustDataUserTasks;

public class AdjustDataUser {

    private FirebaseDatabase firebaseDatabase;
    private AdjustDataUserTasks.Presenter presenter;
    private SharedPreferences sharedPreferences;

    public AdjustDataUser(AdjustDataUserTasks.Presenter presenter) {
        this.presenter = presenter;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void getFirebaseUserData(String firebseId, String city){

        firebaseDatabase.getReference()
                .child("users")
                .child(city)
                .child(firebseId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){

                            User user = dataSnapshot.getValue(User.class);

                            if(user != null) {


                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putString(Constants.USER_NAME, user.getName());
                                editor.putString(Constants.USER_EMAIL, user.getEmail());
                                editor.putString(Constants.USER_ID_FIREBASE, user.getId_firebase());
                                editor.putString(Constants.USER_CITY, user.getCity());
                                editor.putString(Constants.USER_SURNAME, user.getSurname());
                                editor.putString(Constants.USER_PHONE, user.getPhone());
                                editor.putString(Constants.USER_ADDRESS, user.getAddress() != null ? user.getAddress().getAddress() : "");
                                editor.putString(Constants.STORE_ID, user.getStoreId() != null ? user.getStoreId() : "");

                                editor.apply();

                                presenter.onAdjustUserDataSuccess(user);

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NotNull DatabaseError databaseError) {
                        presenter.onAdjustUserDataFailed();
                    }
                });

    }

    public void getUserCity(final String firebaseId){

        firebaseDatabase.getReference()
                .child("city")
                .child(firebaseId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                            presenter.onAdjustUserCitySuccess(firebaseId, dataSnapshot.getValue(String.class));
                    }

                    @Override
                    public void onCancelled( DatabaseError databaseError) {
                        presenter.onAdjustCityFailed();
                    }
                });
    }

}
