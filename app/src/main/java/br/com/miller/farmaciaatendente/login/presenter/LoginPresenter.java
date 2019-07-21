package br.com.miller.farmaciaatendente.login.presenter;

import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseUser;

import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.login.model.LoginModel;
import br.com.miller.farmaciaatendente.login.tasks.LoginTasks;
import br.com.miller.farmaciaatendente.utils.StringUtils;
import br.com.miller.farmaciaatendente.utils.presenters.AdjustDataUser;
import br.com.miller.farmaciaatendente.utils.tasks.AdjustDataUserTasks;

public class LoginPresenter implements LoginTasks.Model, LoginTasks.View, AdjustDataUserTasks.Presenter {

    private LoginTasks.Presenter presenter;
    private LoginModel loginModel;
    private AdjustDataUser adjustDataUser;

    public LoginPresenter(LoginTasks.Presenter presenter) {
        this.presenter = presenter;
        loginModel = new LoginModel(this);
        adjustDataUser = new AdjustDataUser(this);
    }

    @Override
    public void login(String user, String password) {

        boolean inputIsOk = true;

        if(user.isEmpty()){

            presenter.inputEmpty(1);
            inputIsOk = false;

        }

        if(password.isEmpty()){

            presenter.inputEmpty(2);
            inputIsOk = false;

        }

        if(!StringUtils.isValidEmail(user)){

            presenter.inputEmpty(3);
            inputIsOk = false;

        }

        if(inputIsOk)
            loginModel.login(user, password);

    }

    @Override
    public void setSharedPreferences(SharedPreferences sharedPreferences) { adjustDataUser.setSharedPreferences(sharedPreferences); }

    @Override
    public void onLoginSuccess(FirebaseUser firebaseUser) { adjustDataUser.getUserCity(firebaseUser.getUid());}

    @Override
    public void onLoginFailed() { presenter.onLoginFailed(); }

    @Override
    public void onAdjustUserCitySuccess(String firebaseId, String city) { adjustDataUser.getFirebaseUserData(firebaseId, city);}

    @Override
    public void onAdjustCityFailed() { }

    @Override
    public void onAdjustUserDataSuccess(User user) { presenter.onAdjustDataUserSuccess(user); }

    @Override
    public void onAdjustUserDataFailed() { presenter.onAdjustDataUserFailed(); }
}
