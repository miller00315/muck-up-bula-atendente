package br.com.miller.farmaciaatendente.mainMenu.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import br.com.miller.farmaciaatendente.domain.Buy;
import br.com.miller.farmaciaatendente.mainMenu.tasks.SolicitationTasks;

public class SolicitationModel {

    private SolicitationTasks.Model model;
    private FirebaseDatabase firebaseDatabase;
    private ArrayList<Buy> buys;

    public SolicitationModel(SolicitationTasks.Model model) {

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
                .child("news")
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
                .child("news")
                .addChildEventListener(newsEventListener);
    }

    public void removeNewsEventListener(String storeId, String city){

        firebaseDatabase.getReference()
                .child("buys")
                .child(city)
                .child("stores")
                .child(storeId)
                .child("news")
                .removeEventListener(newsEventListener);

    }

    private final ChildEventListener newsEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            Log.w("teste", "ola 1");

            if(!buys.contains(dataSnapshot.getValue(Buy.class))){

                buys.add(dataSnapshot.getValue(Buy.class));
                model.onBuysDataSuccess(buys);
            }
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            Log.w("teste", "ola");

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
                        Log.w("teste", String.valueOf(buys.size()));
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
