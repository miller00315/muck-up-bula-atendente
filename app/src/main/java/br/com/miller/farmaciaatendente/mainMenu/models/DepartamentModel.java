package br.com.miller.farmaciaatendente.mainMenu.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.miller.farmaciaatendente.domain.Departament;
import br.com.miller.farmaciaatendente.mainMenu.tasks.DepartamentTask;

public class DepartamentModel {

    private DepartamentTask.Model model;
    private FirebaseDatabase firebaseDatabase;

    public DepartamentModel(DepartamentTask.Model model) {

        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void getDepartmentsAvaliables(String city){

        firebaseDatabase.getReference()
        .child("avaliablesDepartaments")
        .child(city)
        .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Departament> departaments = new ArrayList<>();

                for(DataSnapshot child : dataSnapshot.getChildren()){

                    departaments.add(new Departament(child.getValue()));

                }

                model.onDepartamentsItemSuccess(departaments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void addDepartament(final Departament departament, String storeId){

        departament.setIdStore(storeId);

        Map<String, Object> map = new HashMap<>();

        map.put(departament.getId(), departament);

        firebaseDatabase.getReference()
                .child("storeDepartaments")
                .child(departament.getCity())
                .child(departament.getIdStore())
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        getDepartaments(departament.getCity(), departament.getIdStore());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        model.onDepartmentAddFailed();
                    }
                });
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

                            for (DataSnapshot child : dataSnapshot.getChildren()) { departaments.add(new Departament(child.getValue())); }

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
