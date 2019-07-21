package br.com.miller.farmaciaatendente.mainMenu.presenters;

import android.graphics.Bitmap;

import br.com.miller.farmaciaatendente.domain.Store;
import br.com.miller.farmaciaatendente.mainMenu.models.StorePerfilModel;
import br.com.miller.farmaciaatendente.mainMenu.tasks.StorePerfilTasks;

public class StorePerfilPresenter implements StorePerfilTasks.Model, StorePerfilTasks.View {

    private StorePerfilTasks.Presenter presenter;
    private StorePerfilModel storePerfilModel;

    public StorePerfilPresenter(StorePerfilTasks.Presenter presenter) {
        this.presenter = presenter;
        storePerfilModel = new StorePerfilModel(this);
    }

    @Override
    public void onStoreDataSuccess(Store store) { presenter.onStoreDataSuccess(store);}

    @Override
    public void onStoreDataFailed() { presenter.onStoreDataFailed();}

    @Override
    public void onImageDownloadSuccess(Bitmap bitmap) { presenter.onImageDownloadSuccess(bitmap);}

    @Override
    public void onImageDownloadFailed() { presenter.onImageDownloadFailed(); }

    @Override
    public void onUploadImageSuccess(Bitmap bitmap) { presenter.onUploadImageSuccess(bitmap);}

    @Override
    public void onUploadImageFailed() { presenter.onUploadImageFailed();}

    @Override
    public void onUpdateStoreSuccess(Store store) { presenter.onUpdateStoreSuccess(store);}

    @Override
    public void onUpdateStoreFailed() { presenter.onUpdateStoreFailed();}

    @Override
    public void getStore(String storeId, String city) { storePerfilModel.getStore(storeId, city);}

    @Override
    public void updateStore(Store store) { storePerfilModel.updateStore(store); }

    @Override
    public void downloadImage(String type, String city, String image) { storePerfilModel.downloadImage(type, city, image);}

    @Override
    public void uploadImage(String type, String city, String image, Bitmap bitmap) {

        String temp = "";

        if(image.contains(".jpg")){ temp = image.replace(".jpg", ""); }

        storePerfilModel.uploadImage(type, city, temp, bitmap);}
}
