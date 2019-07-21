package br.com.miller.farmaciaatendente.main.presenters;

import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseUser;

import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.main.MainActivityTasks;
import br.com.miller.farmaciaatendente.main.models.MainActivityModel;
import br.com.miller.farmaciaatendente.utils.presenters.AdjustDataUser;
import br.com.miller.farmaciaatendente.utils.tasks.AdjustDataUserTasks;

public class MainActivityPresenter implements MainActivityTasks.Model, MainActivityTasks.View, AdjustDataUserTasks.Presenter {

    private MainActivityModel mainActivityModel;
    private MainActivityTasks.Presenter presenter;
    private AdjustDataUser adjustDataUser;

    public MainActivityPresenter(MainActivityTasks.Presenter presenter) {
        this.presenter = presenter;
        mainActivityModel = new MainActivityModel(this);
        adjustDataUser = new AdjustDataUser(this);
    }

    @Override
    public void onLoginSuccess(FirebaseUser firebaseUser) {
        if(!firebaseUser.isAnonymous()) {
            presenter.onLoginSuccess(firebaseUser);
            adjustDataUser.getUserCity(firebaseUser.getUid());
        }
    }

    @Override
    public void onLoginFailed() { presenter.onLoginFailed(); }

    @Override
    public void setAuthListener() { mainActivityModel.setAuthStateListener(); }

    @Override
    public void onDestroy() { mainActivityModel.removeListener(); }

    @Override
    public void setSharedPreferences(SharedPreferences sharedPreferences) { adjustDataUser.setSharedPreferences(sharedPreferences); }

    @Override
    public void onAdjustUserCitySuccess(String firebaseId, String city) { adjustDataUser.getFirebaseUserData(firebaseId, city); }

    @Override
    public void onAdjustCityFailed() { }

    @Override
    public void onAdjustUserDataSuccess(User user) { presenter.onUserDataSuccess(user); }

    @Override
    public void onAdjustUserDataFailed() { }
}
