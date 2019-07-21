package br.com.miller.farmaciaatendente.login.tasks;

import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseUser;

import br.com.miller.farmaciaatendente.domain.User;

public interface LoginTasks {

    interface View {
        void login(String user, String password);
        void setSharedPreferences(SharedPreferences sharedPreferences);
    }

    interface Presenter {
        void inputEmpty(int code);
        void onLoginFailed();
        void onAdjustDataUserFailed();
        void onAdjustDataUserSuccess(User user);
    }

    interface Model{

        void onLoginSuccess(FirebaseUser firebaseUser);
        void onLoginFailed();

    }
}
