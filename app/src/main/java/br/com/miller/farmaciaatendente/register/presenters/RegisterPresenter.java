package br.com.miller.farmaciaatendente.register.presenters;

import android.content.SharedPreferences;
import android.graphics.Bitmap;

import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.register.models.RegisterModel;
import br.com.miller.farmaciaatendente.register.tasks.RegisterTasks;
import br.com.miller.farmaciaatendente.utils.StringUtils;
import br.com.miller.farmaciaatendente.utils.presenters.AdjustDataUser;
import br.com.miller.farmaciaatendente.utils.tasks.AdjustDataUserTasks;

public class RegisterPresenter implements RegisterTasks.Model, RegisterTasks.View, AdjustDataUserTasks.Presenter {

    private RegisterTasks.Presenter presenter;
    private RegisterModel model;
    private AdjustDataUser adjustDataUser;

    public RegisterPresenter(RegisterTasks.Presenter presenter) {
        this.presenter = presenter;
        model = new RegisterModel(this);
        adjustDataUser = new AdjustDataUser(this);
    }

    @Override
    public void firstLogin() { model.firstUser(); }

    @Override
    public void setSharedPreferences(SharedPreferences sharedPreferences) { adjustDataUser.setSharedPreferences(sharedPreferences); }

    @Override
    public void destroyAnonymousUser() { model.destroyUser(); }

    @Override
    public void uploadImage(User user, Bitmap bitmap) { model.uploadImage("users", user.getCity(), user.getId_firebase() ,bitmap); }

    @Override
    public void checkUserData(String name, String surname, String bithDate, String email, String phone, String password, String repeatPassword) {

        boolean flag = true;

        if(name.isEmpty()){
            presenter.onCheckDataFailed(1);
            flag = false;
        }

        if(surname.isEmpty()){
            presenter.onCheckDataFailed(2);
            flag = false;
        }

        if(bithDate.isEmpty()){
            presenter.onCheckDataFailed(3);
            flag = false;
        }

        if(email.isEmpty()){
            presenter.onCheckDataFailed(4);
            flag = false;
        }

        if(phone.isEmpty()){
            presenter.onCheckDataFailed(5);
            flag = false;
        }

        if(password.isEmpty()){
            presenter.onCheckDataFailed(6);
            flag = false;
        }

        if(repeatPassword.isEmpty()){
            presenter.onCheckDataFailed(7);
            flag = false;
        }

        if(!password.equals(repeatPassword)){
            presenter.onCheckDataFailed(8);
            flag = false;
        }

        if(!StringUtils.isValidEmail(email)){
            presenter.onCheckDataFailed(9);
            flag = false;
        }

        if(flag){
            presenter.onCheckDataOk();
            model.createUser(name, surname, bithDate, email, phone, password);
        }

    }

    @Override
    public void onUserGenerateSuccess(User user, String password) { model.registerUser(user, password); }

    @Override
    public void onAnonimousSuccess() { presenter.onAnonimousSuccess(); }

    @Override
    public void onAnonimousFailed() { presenter.onAnonimousFailed(); }

    @Override
    public void onCredentialSuccess(User user) { model.registerOnDataBaseUser(user);}

    @Override
    public void onCredentialFailed() {  presenter.onCredentialFailed(); }

    @Override
    public void onUserDatabaseSuccess(User user) { presenter.onUserDataBaseSuccess(user); }

    @Override
    public void onUserDatabaseFailed() { presenter.onUserDataBaseFailed(); }

    @Override
    public void onImageUploadSuccess(Bitmap bitmap) {
        presenter.onImageUploadSuccess(bitmap);
        adjustDataUser.getUserCity(model.getUser().getId_firebase());
    }

    @Override
    public void onImageUploadFailed() { presenter.onImageUploadFailed(); }

    @Override
    public void onAnonymmousUserDestroySuccess() { presenter.onAnonymmousUserDestroySuccess(); }

    @Override
    public void onAnonymousUserDestroyFailed() { presenter.onAnonymousUserDestroyFailed(); }

    @Override
    public void onAdjustUserCitySuccess(String firebaseId , String city) { adjustDataUser.getFirebaseUserData(firebaseId, city);}

    @Override
    public void onAdjustCityFailed() { }

    @Override
    public void onAdjustUserDataSuccess(User user) { presenter.onUserDataSuccess(user); }

    @Override
    public void onAdjustUserDataFailed() { }

    //adjustDataUser.getUserCity(user.getId_firebase());

}
