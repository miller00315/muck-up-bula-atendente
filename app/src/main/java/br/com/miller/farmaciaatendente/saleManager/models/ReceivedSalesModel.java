package br.com.miller.farmaciaatendente.saleManager.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import br.com.miller.farmaciaatendente.domain.Buy;
import br.com.miller.farmaciaatendente.saleManager.tasks.ReceivedSalesTask;

public class ReceivedSalesModel {

    private ReceivedSalesTask.Model model;
    private FirebaseDatabase firebaseDatabase;
    private ArrayList<Buy> buys;

    public ReceivedSalesModel(ReceivedSalesTask.Model model) {
        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
        buys = new ArrayList<>();
    }

    public void getReceivedBuys(String storeId, String city){

        firebaseDatabase.getReference()
                .child("buys")
                .child(city)
                .child("stores")
                .child(storeId)
                .child("received")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()) model.onBuysDataFailed();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        firebaseDatabase.getReference()
                .child("buys")
                .child(city)
                .child("stores")
                .child(storeId)
                .child("received")
                .addChildEventListener(receivedEventListener);
    }

    public void removeReceivedListener(String storeId, String city){

        firebaseDatabase.getReference()
                .child("buys")
                .child(city)
                .child("stores")
                .child(storeId)
                .child("received")
                .removeEventListener(receivedEventListener);
    }

    private final ChildEventListener receivedEventListener = new ChildEventListener() {
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
