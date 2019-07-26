package br.com.miller.farmaciaatendente.departamentManager.models;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.miller.farmaciaatendente.departamentManager.tasks.MedicineManagerTasks;
import br.com.miller.farmaciaatendente.domain.Offer;
import br.com.miller.farmaciaatendente.domain.Store;
import br.com.miller.farmaciaatendente.utils.images.FirebaseImageUtils;
import br.com.miller.farmaciaatendente.utils.StringUtils;
import br.com.miller.farmaciaatendente.utils.tasks.FirebaseImageTask;

public class MedicineManagerModel implements FirebaseImageTask.Model {

    private MedicineManagerTasks.Model model;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseImageUtils firebaseImageUtils;
    private Offer offer;

    public MedicineManagerModel(MedicineManagerTasks.Model model) {
        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseImageUtils = new FirebaseImageUtils(this);
    }

    public void updateMedicine(String name, String description, String indication, String noIndication, double value, String active, boolean publish){

        String title = offer.getTitle();

        offer.setTitle(name);
        offer.setDescription(description);
        offer.setIndication(indication);
        offer.setNoIndication(noIndication);
        offer.setValue(value);
        offer.setActive(active);

       removeOldMedicine(title, publish);

    }

    private void removeOldMedicine(String title, final boolean publish){

        if(publish) {

            firebaseDatabase.getReference()
                    .child("offersDepartaments")
                    .child(offer.getCity())
                    .child(offer.getDepartamentId())
                    .child(StringUtils.normalizer(title))
                    .child(offer.getId())
                    .removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) { updateOffer(offer, publish); }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }else{

            updateOffer(offer, publish);
        }

    }

    public void createMedicine(String name, String description, String indication, String noIndication, String city, String departamentId, String storeId, double value, String active, boolean publish){

        offer = new Offer();

        offer.setTitle(name);
        offer.setDescription(description);
        offer.setNoIndication(noIndication);
        offer.setIndication(indication);
        offer.setValue(value);
        offer.setCity(city);
        offer.setDepartamentId(departamentId);
        offer.setId(String.valueOf(new Date().getTime()).concat(storeId));
        offer.setImage(offer.getId());
        offer.setActive(active);
        offer.setType("offers");
        offer.setStoreId(storeId);

        this.getStore(offer, publish);

    }

    private void updateOffer(final Offer offer, final boolean publish){

        Map<String, Object> map = new HashMap<>();

        map.put(String.valueOf(offer.getId()), offer);

        firebaseDatabase.getReference()
                .child("storeDepartaments")
                .child(offer.getCity())
                .child(offer.getStoreId())
                .child(offer.getDepartamentId())
                .child("offers")
                .updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        model.onUpdateMedicineSuccess(offer);

                        if(publish) publish(offer);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        model.onUpdateMedicineFailed();
                    }
                });

    }

    private void getStore(final Offer offer, final boolean publish){

        firebaseDatabase.getReference()
                .child("stores")
                .child(offer.getCity())
                .child(offer.getStoreId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Store store = dataSnapshot.getValue(Store.class);

                        if(store != null) {
                            offer.setStore(store.getName());
                            offer.setSendValue(store.getSendValue());

                            updateOffer(offer, publish);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }

    public void getMedicine(final String offerId, String storeId, String departamentId, String city){

        firebaseDatabase.getReference()
                .child("storeDepartaments")
                .child(city)
                .child(storeId)
                .child(departamentId)
                .child("offers")
                .child(offerId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        offer = dataSnapshot.getValue(Offer.class);

                        if(offer != null)
                            model.onMedicineDataSuccess(offer);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        model.onMedicineDataFailed();
                    }
                });

    }

    private void publish(final Offer offer){

        final Map<String, Object> map = new HashMap<>();

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
                        model.onPublishMedicineSuccess(offer);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        model.onPublishMedicineFailed();

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

    @Override
    public void uploadImage(String type, String city, String image, Bitmap bitmap) { firebaseImageUtils.uploadImage(type, city, image, bitmap);}

    @Override
    public void downloadImage(String type, String city, String image) { firebaseImageUtils.downloadImage(type, city, image);}

    @Override
    public void onImageUploadSuccess(Bitmap bitmap) { model.onImageUploadSuccess(bitmap);}

    @Override
    public void onImageUploadFailed() { model.onImageUploadFailed();}

    @Override
    public void onImageDownloadSuccess(Bitmap bitmap) { model.onImageDownloadSuccess(bitmap);}

    @Override
    public void onImageDownloadFailed() { model.onImageUploadFailed(); }
}
