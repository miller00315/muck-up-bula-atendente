package br.com.miller.farmaciaatendente.departamentManager.models;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import br.com.miller.farmaciaatendente.departamentManager.tasks.DepartamentManagerTasks;
import br.com.miller.farmaciaatendente.domain.Departament;
import br.com.miller.farmaciaatendente.domain.Offer;
import br.com.miller.farmaciaatendente.utils.StringUtils;

public class DepartamentManagerModel {

    private DepartamentManagerTasks.Model model;
    private FirebaseDatabase firebaseDatabase;

    public DepartamentManagerModel(DepartamentManagerTasks.Model model) {
        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void getDepartament(final String departamentId, String storeId, String city){

        firebaseDatabase.getReference()
                .child("storeDepartaments")
                .child(city)
                .child(storeId)
                .child(departamentId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists())
                            model.onDepartamentDataSuccess(new Departament(dataSnapshot.getValue()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        model.onDepartamentDataFailed();
                    }
                });
    }

    public void deleteOffer(final Offer offer){

        firebaseDatabase.getReference()
                .child("storeDepartaments")
                .child(offer.getCity())
                .child(offer.getStoreId())
                .child(offer.getDepartamentId())
                .child("offers")
                .child(offer.getId())
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        model.onMedicineDeleteSucces(offer);
                        deleteOffer(offer);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        model.onMedicineDeleteFailed();
                    }
                });
    }

    public void depublishOffer(final Offer offer){

        firebaseDatabase.getReference()
                .child("offers")
                .child(offer.getCity())
                .child(StringUtils.normalizer(offer.getTitle()))
                .child(offer.getId())
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        model.onDespublishSuccess(offer);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        model.onDespublishFailed();
                    }
                });
    }

    public void publishOffer(final Offer offer){

        Map<String, Object> map = new HashMap<>();

        map.put(offer.getId(), offer);

        firebaseDatabase.getReference()
                .child("offers")
                .child(offer.getCity())
                .child(StringUtils.normalizer(offer.getTitle()))
                .updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        model.onMedicinePublishSucces(offer);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

               model.onMedicinePublishFailed();

            }
        });

    }

}
