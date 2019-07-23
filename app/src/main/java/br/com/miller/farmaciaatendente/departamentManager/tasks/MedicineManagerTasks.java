package br.com.miller.farmaciaatendente.departamentManager.tasks;

import android.graphics.Bitmap;
import android.os.Bundle;

import br.com.miller.farmaciaatendente.domain.Offer;

public interface MedicineManagerTasks {

    interface Presenter{
        void onMedicineDataSuccess(Offer offer);
        void onMedicineDataFailed();
        void onUpdateMedicineSuccess(Offer offer);
        void onUpdateMedicineFailed();
        void onUpdateDataFailed(int type);
        void onImageUploadSuccess(Bitmap bitmap);
        void onImageDownloadSuccess(Bitmap bitmap);
        void onPublishMedicineSuccess(Offer offer);
        void onPublishMedicineFailed();
        void onImageDownloadFailed();
        void onImageUploadFailed();
        void newMedicine();
    }

    interface View{
        void setData(Bundle bundle);
        void updateMedicine(String name, String description, String indication, String noIndication, String value, String active, boolean publish);
        void createMedicine(String name, String description, String indication, String noIndication, Bundle bundle, String value, String active, boolean publish);
        void uploadImage(String type, String city, String image, Bitmap bitmap);
        void downloadImage(String type, String city, String image);
    }

    interface Model{
        void onMedicineDataSuccess(Offer offer);
        void onMedicineDataFailed();
        void onImageUploadSuccess(Bitmap bitmap);
        void onImageDownloadSuccess(Bitmap bitmap);
        void onImageDownloadFailed();
        void onImageUploadFailed();
        void onUpdateMedicineSuccess(Offer offer);
        void onUpdateMedicineFailed();
        void onPublishMedicineSuccess(Offer offer);
        void onPublishMedicineFailed();
    }

}
