package br.com.miller.farmaciaatendente.mainMenu.tasks;

import android.graphics.Bitmap;

import br.com.miller.farmaciaatendente.domain.Store;

public interface StorePerfilTasks {

    interface Presenter{

        void onStoreDataSuccess(Store store);
        void onStoreDataFailed();
        void onImageDownloadSuccess(Bitmap bitmap);
        void onImageDownloadFailed();
        void onUploadImageSuccess(Bitmap bitmap);
        void onUploadImageFailed();
        void onUpdateStoreSuccess(Store store);
        void onUpdateStoreFailed();
    }

    interface View{

        void getStore(String storeId, String city);
        void updateStore(Store store);
        void downloadImage(String type, String city, String image);
        void uploadImage(String type, String city, String image, Bitmap bitmap);

    }

    interface Model{

        void onStoreDataSuccess(Store store);
        void onStoreDataFailed();
        void onImageDownloadSuccess(Bitmap bitmap);
        void onImageDownloadFailed();
        void onUploadImageSuccess(Bitmap bitmap);
        void onUploadImageFailed();
        void onUpdateStoreSuccess(Store store);
        void onUpdateStoreFailed();

    }
}
