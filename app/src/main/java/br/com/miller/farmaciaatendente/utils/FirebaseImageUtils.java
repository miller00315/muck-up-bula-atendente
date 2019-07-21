package br.com.miller.farmaciaatendente.utils;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import br.com.miller.farmaciaatendente.utils.tasks.FirebaseImageTask;


public class FirebaseImageUtils {

    private FirebaseImageTask.Model model;
    private StorageReference storageReference;

    public FirebaseImageUtils(FirebaseImageTask.Model model) {
        this.model = model;
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public void downloadImage(String type, String city, String image){

        storageReference.child("images")
                .child(type)
                .child(city)
                .child(image)
                .getBytes(Long.MAX_VALUE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) { model.onImageDownloadSuccess(ImageUtils.convertByteArraytoBitmap(bytes)); }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                model.onImageDownloadFailed();
            }
        });
    }

    public void uploadImage(String type, String city, String image, final Bitmap bitmap){

         storageReference.child("images")
                .child(type)
                .child(city)
                .child(image.concat(".jpg"))
                .putBytes(ImageUtils.convertBitmapToByteArray(bitmap))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) { model.onImageUploadSuccess(bitmap); }
                })
                 .addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 model.onImageDownloadFailed();
             }
         });
    }

}
