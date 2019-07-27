package br.com.miller.farmaciaatendente.mainMenu.presenters;

import android.graphics.Bitmap;
import android.os.Bundle;

import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;

import br.com.miller.farmaciaatendente.domain.Store;
import br.com.miller.farmaciaatendente.mainMenu.models.StorePerfilModel;
import br.com.miller.farmaciaatendente.mainMenu.tasks.StorePerfilTasks;
import br.com.miller.farmaciaatendente.utils.Constants;
import br.com.miller.farmaciaatendente.utils.StringUtils;

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
    public Store getStore() { return storePerfilModel.getStore(); }

    @Override
    public void getStore(String storeId, String city) { storePerfilModel.getStore(storeId, city);}

    @Override
    public void updateStore(Bundle bundle) {

        Store store = storePerfilModel.getStore();

        switch (Objects.requireNonNull(bundle.getString("type"))){

            case Constants.STORE_NAME : {

                store.setName(bundle.getString("result"));

                break;
            }

            case Constants.STORE_SESCRIPTION: {

                store.setDescription(bundle.getString("result"));

                break;
            }

            case Constants.STORE_SEND_VALUE:{

                try {
                    store.setSendValue(StringUtils.parseMonetaryStringToBigDecimal(bundle.getString("result"), Locale.getDefault()).doubleValue());
                } catch (ParseException e) {
                    e.printStackTrace();

                    return;
                }

                break;
            }

            case Constants.STORE_TIME:{

                store.setTime(bundle.getString("result"));

                break;
            }

        }

        storePerfilModel.updateStore(store);
    }

    @Override
    public void downloadImage(String type, String city, String image) { storePerfilModel.downloadImage(type, city, image.concat(".jpg"));}

    @Override
    public void uploadImage(String type, String city, String image, Bitmap bitmap) {

        String temp = "";

        if(image.contains(".jpg")){ temp = image.replace(".jpg", ""); }

        storePerfilModel.uploadImage(type, city, temp, bitmap);
    }
}
