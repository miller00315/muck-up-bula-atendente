package br.com.miller.farmaciaatendente.mainMenu.presenters;

import android.graphics.Bitmap;
import android.os.Bundle;

import java.util.Objects;

import br.com.miller.farmaciaatendente.domain.Address;
import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.mainMenu.models.PerfilModel;
import br.com.miller.farmaciaatendente.mainMenu.tasks.PersonalPerfilTasks;
import br.com.miller.farmaciaatendente.utils.Constants;

public class PersonalPerfilPresenter implements PersonalPerfilTasks.Model, PersonalPerfilTasks.View {

    private PersonalPerfilTasks.Presenter presenter;
    private PerfilModel model;

    public PersonalPerfilPresenter(PersonalPerfilTasks.Presenter presenter) {
        this.presenter = presenter;
        model = new PerfilModel(this);
    }

    @Override
    public void getUserData(String firebaseId, String city) { model.getUserData(firebaseId, city);}

    @Override
    public void downloadImage(String type, String city, String image) { model.downloadImage(type, city, image.concat(".jpg"));}

    @Override
    public void uploadImage(String type, String city, String image, Bitmap bitmap) { model.uploadImage(type, city, image, bitmap);}

    @Override
    public void updateUser(Bundle bundle) {

        User user = model.getUser();

        switch (Objects.requireNonNull(bundle.getString("type"))){

            case Constants.USER_NAME:{

                user.setName(bundle.getString("firstResult"));
                user.setSurname(bundle.getString("secondResult"));

                break;
            }

            case Constants.USER_PHONE:{
                user.setPhone(bundle.getString("result"));
                break;
            }

            case Constants.USER_ADDRESS:{

                if(user.getAddress() != null)
                    user.getAddress().setAddress(bundle.getString("result"));
                else{

                    user.setAddress(new Address());

                    user.getAddress().setAddress(bundle.getString("result"));
                }

                break;
            }

            case Constants.USER_EMAIL:{
                user.setEmail(bundle.getString("result"));
                break;
            }

        }

        model.updateUserData(user);

    }

    @Override
    public User getUser() { return model.getUser(); }

    @Override
    public void onImageDownloadSuccess(Bitmap bitmap) { presenter.onImageDownloadSuccess(bitmap);}

    @Override
    public void onImageDownloadFailed() { presenter.onImageDownloadFailed(); }

    @Override
    public void onImageUploadSuccess(Bitmap bitmap) { presenter.onImageUploadSuccess(bitmap);}

    @Override
    public void onImageUploadFailed() { presenter.onImageUploadFailed();}

    @Override
    public void onUserDataSuccess(User user) { presenter.onUserDataSuccess(user);}

    @Override
    public void onUserDataFailed() { presenter.onUserDataFailed(); }

    @Override
    public void onUpdateUserSuccess(User user) { presenter.onUpdateUserSuccess(user);}

    @Override
    public void onUpdateUserFailed() { presenter.onUpdateUserFailed(); }

}
