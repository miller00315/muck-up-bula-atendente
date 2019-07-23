package br.com.miller.farmaciaatendente.departamentManager.presenter;

import android.graphics.Bitmap;
import android.os.Bundle;

import java.text.ParseException;
import java.util.Locale;

import br.com.miller.farmaciaatendente.departamentManager.models.MedicineManagerModel;
import br.com.miller.farmaciaatendente.departamentManager.tasks.MedicineManagerTasks;
import br.com.miller.farmaciaatendente.domain.Offer;
import br.com.miller.farmaciaatendente.utils.StringUtils;

public class MedicineManagerPresenter implements MedicineManagerTasks.View, MedicineManagerTasks.Model {

    private MedicineManagerTasks.Presenter presenter;
    private MedicineManagerModel model;

    public MedicineManagerPresenter(MedicineManagerTasks.Presenter presenter) {
        this.presenter = presenter;
        model = new MedicineManagerModel(this);
    }

    @Override
    public void onMedicineDataSuccess(Offer offer) { presenter.onMedicineDataSuccess(offer); }

    @Override
    public void onMedicineDataFailed() { presenter.onMedicineDataFailed(); }

    @Override
    public void onImageUploadSuccess(Bitmap bitmap) { presenter.onImageUploadSuccess(bitmap); }

    @Override
    public void onImageDownloadSuccess(Bitmap bitmap) { presenter.onImageDownloadSuccess(bitmap); }

    @Override
    public void onImageDownloadFailed() { presenter.onImageDownloadFailed(); }

    @Override
    public void onImageUploadFailed() { presenter.onImageUploadFailed(); }

    @Override
    public void onUpdateMedicineSuccess(Offer offer) { presenter.onUpdateMedicineSuccess(offer); }

    @Override
    public void onUpdateMedicineFailed() { presenter.onUpdateMedicineFailed(); }

    @Override
    public void onPublishMedicineSuccess(Offer offer) { presenter.onPublishMedicineSuccess(offer); }

    @Override
    public void onPublishMedicineFailed() { presenter.onPublishMedicineFailed(); }

    @Override
    public void setData(Bundle bundle) {

        if(bundle.containsKey("offerId")){

            model.getMedicine(bundle.getString("offerId"),
                    bundle.getString("storeId"),
                    bundle.getString("departamentId"),
                    bundle.getString("city"));
        }else{

            presenter.newMedicine();

        }

    }

    @Override
    public void updateMedicine(String name, String description, String indication, String noIndication, String value, String active, boolean publish) {

        boolean flag = true;

        if(name.isEmpty()){

            flag  = false;

        }

        if(description.isEmpty()){

            flag = false;
        }

        if(indication.isEmpty()){

            flag = false;
        }

        if(noIndication.isEmpty()){

            flag = false;
        }

        if(value.isEmpty()){

            flag = false;
        }

        if(flag) {
            try {
                model.updateMedicine(name, description, indication, noIndication, StringUtils.parseMonetaryStringToBigDecimal(value, Locale.getDefault()).doubleValue(), active, publish);
            } catch (ParseException e) {
                e.printStackTrace();
                presenter.onUpdateDataFailed(0);
            }
        }else{
            presenter.onUpdateDataFailed(0);
        }
    }

    @Override
    public void createMedicine(String name, String description, String indication, String noIndication, Bundle bundle, String value, String active, boolean publish) {

        boolean flag = true;

        if(name.isEmpty()){

            flag  = false;
        }

        if(description.isEmpty()){

            flag = false;
        }

        if(indication.isEmpty()){

            flag = false;
        }

        if(noIndication.isEmpty()){

            flag = false;
        }

        if(value.isEmpty()){

            flag = false;
        }

        if(flag) {
            try {
                model.createMedicine(name, description, indication, noIndication, bundle.getString("city"), bundle.getString("departamentId"), bundle.getString("storeId"), StringUtils.parseMonetaryStringToBigDecimal(value, Locale.getDefault()).doubleValue(), active,publish);
            } catch (ParseException e) {
                e.printStackTrace();
                presenter.onUpdateDataFailed(0);
            }
        }else{
            presenter.onUpdateDataFailed(0);
        }

    }

    @Override
    public void uploadImage(String type, String city, String image, Bitmap bitmap) { model.uploadImage(type, city, image, bitmap);}

    @Override
    public void downloadImage(String type, String city, String image) { model.downloadImage(type, city, image.concat(".jpg")); }
}
