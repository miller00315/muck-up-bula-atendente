package br.com.miller.farmaciaatendente.mainMenu.presenters;

import android.graphics.Bitmap;

import br.com.miller.farmaciaatendente.domain.Address;
import br.com.miller.farmaciaatendente.domain.CompleteName;
import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.mainMenu.models.PerfilModel;
import br.com.miller.farmaciaatendente.mainMenu.tasks.PersonalPerfilTasks;
import br.com.miller.farmaciaatendente.utils.AlertDialogUtils;

public class PersonalPerfilPresenter implements PersonalPerfilTasks.Model, PersonalPerfilTasks.View {

    private PersonalPerfilTasks.Presenter presenter;
    private PerfilModel model;
    private AlertDialogUtils alertDialogUtils;

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
    public void updateUser(User user) { model.updateUserData(user);}

    @Override
    public void checkData(Object o, int type, User user) {

        switch (type){

            case 0:{

                if(o instanceof CompleteName) {
                    user.setName(((CompleteName) o).getName());
                    user.setSurname(((CompleteName) o).getSurname());
                }

                break;
            }

            case 1:{

                if(o instanceof String) user.setEmail((String) o);

                break;
            }

            case 2:{

                if(o instanceof Address) user.setAddress((Address) o);

                break;
            }

            case 3:{

                if(o instanceof String) user.setPhone((String) o);

                break;
            }

            default:
                break;
        }

        updateUser(user);

    }

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
