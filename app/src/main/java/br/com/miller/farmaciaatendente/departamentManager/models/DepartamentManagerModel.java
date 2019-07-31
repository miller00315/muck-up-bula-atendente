package br.com.miller.farmaciaatendente.departamentManager.models;

import android.support.annotation.NonNull;
import android.util.Log;

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
                        depublishOffer(offer);
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
                .child("offersDepartaments")
                .child(offer.getCity())
                .child(offer.getDepartamentId())
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

    public void setHint(String city, String title){

        firebaseDatabase.getReference()
                .child("searchHint")
                .child(city)
                .child(StringUtils.normalizer(title))
                .child("title")
                .setValue(title)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.w("this", "new Hint");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("this", "failed Hint");
                    }
                });
    }

    public void publishOffer(final Offer offer){

        Map<String, Object> map = new HashMap<>();

        map.put(offer.getId(), offer);

        firebaseDatabase.getReference()
                .child("offersDepartaments")
                .child(offer.getCity())
                .child(offer.getDepartamentId())
                .child(StringUtils.normalizer(offer.getTitle()))
                .updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        setHint(offer.getCity(), offer.getTitle());
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
