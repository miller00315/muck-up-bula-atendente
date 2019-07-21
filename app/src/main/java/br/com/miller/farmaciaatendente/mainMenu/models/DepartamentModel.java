package br.com.miller.farmaciaatendente.mainMenu.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.domain.Departament;
import br.com.miller.farmaciaatendente.mainMenu.tasks.DepartamentTask;

public class DepartamentModel {

    private DepartamentTask.Model model;
    private FirebaseDatabase firebaseDatabase;

    public DepartamentModel(DepartamentTask.Model model) {

        this.model = model;

        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void getDepartaments(String city, String storeId){

        firebaseDatabase.getReference()
                .child("storeDepartaments")
                .child(city)
                .child(storeId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()) {

                            ArrayList<Departament> departaments = new ArrayList<>();

                            for (DataSnapshot child : dataSnapshot.getChildren()) {

                                Departament departament = child.getValue(Departament.class);

                                if(departament != null)
                                    departaments.add(departament);
                            }

                            model.onDepartamentSuccess(departaments);

                        }else
                            model.onDepartamentFailed();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        model.onDepartamentFailed();
                    }
                });

    }

}
