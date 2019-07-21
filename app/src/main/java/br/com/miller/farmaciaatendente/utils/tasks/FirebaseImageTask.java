package br.com.miller.farmaciaatendente.utils.tasks;

import android.graphics.Bitmap;

public interface FirebaseImageTask {

    interface Model {
        void uploadImage(String type, String city, String image, Bitmap bitmap);
        void downloadImage(String type, String city, String image);
        void onImageUploadSuccess(Bitmap bitmap);
        void onImageUploadFailed();
        void onImageDownloadSuccess(Bitmap bitmap);
        void onImageDownloadFailed();
    }
}
