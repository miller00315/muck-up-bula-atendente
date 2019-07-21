package br.com.miller.farmaciaatendente.saleManager.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

import br.com.miller.farmaciaatendente.domain.Buy;
import br.com.miller.farmaciaatendente.saleManager.tasks.SendedSalesTasks;

public class SendedSalesModel {

    private SendedSalesTasks.Model model;
    private ArrayList<Buy> buys;
    private FirebaseDatabase firebaseDatabase;

    public SendedSalesModel(SendedSalesTasks.Model model) {
        this.model = model;
        buys = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void getSendedBuys(String storeId, String city){

        firebaseDatabase.getReference()
                .child("buys")
                .child(city)
                .child("stores")
                .child(storeId)
                .child("sended")
                .addChildEventListener(sendedEventListener);
    }

    public void removeSendedEventListener(String storeId, String city){

        firebaseDatabase.getReference()
                .child("buys")
                .child(city)
                .child("stores")
                .child(storeId)
                .child("sended")
                .removeEventListener(sendedEventListener);

    }

    private final ChildEventListener sendedEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            if(!buys.contains(dataSnapshot.getValue(Buy.class))){

                buys.add(dataSnapshot.getValue(Buy.class));
                model.onBuysDataSuccess(buys);
            }
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            if(!buys.contains(dataSnapshot.getValue(Buy.class))){

                buys.add(dataSnapshot.getValue(Buy.class));
                model.onBuysDataSuccess(buys);

            }

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            if(dataSnapshot.exists()) {

                for (int i = 0; i < buys.size(); i++) {

                    if (buys.get(i).getId().equals(Objects.requireNonNull(dataSnapshot.getValue(Buy.class)).getId())) {
                        buys.remove(i);
                        break;
                    }
                }

                model.onBuysDataSuccess(buys);
            }

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            model.onBuysDataFailed();
        }
    };

}
