package br.com.miller.farmaciaatendente.saleManager.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.miller.farmaciaatendente.domain.Buy;
import br.com.miller.farmaciaatendente.saleManager.tasks.ManipulateBuyTask;

public class ManipulateBuyModel {

    private FirebaseDatabase firebaseDatabase;
    private ManipulateBuyTask.Model model;
    private Buy buy;

    public ManipulateBuyModel(ManipulateBuyTask.Model model) {
        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void getBuy(final String city, final String storeId, final String buyId, String status){

        Log.w(this.getClass().getName(), status);

        firebaseDatabase.getReference()
                .child("buys")
                .child(city)
                .child("stores")
                .child(storeId)
                .child(status)
                .child(buyId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            buy = dataSnapshot.getValue(Buy.class);
                            model.onBuyDataSuccess(buy);

                        }else{

                            model.onBuyDataFailed();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                            model.onBuyDataFailed();
                    }
                });

    }

    private void updateUserInformation(final Buy buy){

        Map<String, Object> map = new HashMap<>();

        map.put(buy.getId(), buy);

        firebaseDatabase.getReference()
                .child("buys")
                .child(buy.getUserCity())
                .child("users")
                .child(buy.getUserId())
                .updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        model.onChangeSucces(buy);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                model.onChangeFailed();
            }
        });

    }

    public void sendBuyToSended(){

        firebaseDatabase.getReference()
                .child("buys")
                .child(buy.getStoreCity())
                .child("stores")
                .child(String.valueOf(buy.getStoreId()))
                .child(buy.getStatus())
                .child(buy.getId())
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        buy.setStatus("sended");
                        buy.setDeliverDate(new Date());

                        Map<String, Object> map = new HashMap<>();

                        map.put(buy.getId(), buy);

                        firebaseDatabase.getReference()
                                .child("buys")
                                .child(buy.getStoreCity())
                                .child("stores")
                                .child(String.valueOf(buy.getStoreId()))
                                .child(buy.getStatus())
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        updateUserInformation(buy);

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                model.onBuyDataFailed();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        model.onBuyDataFailed();
                    }
                });
    }

    public void sendBuyToReceived(){

        firebaseDatabase.getReference()
                .child("buys")
                .child(buy.getStoreCity())
                .child("stores")
                .child(String.valueOf(buy.getStoreId()))
                .child(buy.getStatus())
                .child(buy.getId())
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        buy.setStatus("received");
                        buy.setReceiverDate(new Date());

                        Map<String, Object> map = new HashMap<>();

                        map.put(buy.getId(), buy);

                        firebaseDatabase.getReference()
                                .child("buys")
                                .child(buy.getStoreCity())
                                .child("stores")
                                .child(String.valueOf(buy.getStoreId()))
                                .child(buy.getStatus())
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        updateUserInformation(buy);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                model.onBuyDataFailed();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        model.onBuyDataFailed();
                    }
                });

    }

    public void senBuyToCanceled(){

        firebaseDatabase.getReference()
                .child("buys")
                .child(buy.getStoreCity())
                .child("stores")
                .child(String.valueOf(buy.getStoreId()))
                .child(buy.getStatus())
                .child(buy.getId())
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        buy.setStatus("canceled");

                        Map<String, Object> map = new HashMap<>();

                        map.put(buy.getId(), buy);

                        firebaseDatabase.getReference()
                                .child("buys")
                                .child(buy.getStoreCity())
                                .child("stores")
                                .child(String.valueOf(buy.getStoreId()))
                                .child(buy.getStatus())
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        updateUserInformation(buy);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                model.onBuyDataFailed();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        model.onBuyDataFailed();
                    }
                });
    }
}
