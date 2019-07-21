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
import br.com.miller.farmaciaatendente.saleManager.tasks.CanceledSaleTask;

public class CanceledSalesModel {

    private CanceledSaleTask.Model model;
    private FirebaseDatabase firebaseDatabase;
    private ArrayList<Buy> buys;

    public CanceledSalesModel(CanceledSaleTask.Model model) {
        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
        buys = new ArrayList<>();
    }

    public void getNewsBuy(String storeId, String city){

        firebaseDatabase.getReference()
                .child("buys")
                .child(city)
                .child("stores")
                .child(storeId)
                .child("canceled")
                .addChildEventListener(canceledEventListener);
    }

    public void removeNewsEventListener(String storeId, String city){

        firebaseDatabase.getReference()
                .child("buys")
                .child(city)
                .child("stores")
                .child(storeId)
                .child("canceled")
                .removeEventListener(canceledEventListener);

    }

    private final ChildEventListener canceledEventListener = new ChildEventListener() {
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
