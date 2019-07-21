package br.com.miller.farmaciaatendente.main;

import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseUser;

import br.com.miller.farmaciaatendente.domain.User;

public interface MainActivityTasks {

    interface Presenter{
        void onLoginSuccess(FirebaseUser firebaseUser);
        void onLoginFailed();
        void onUserDataSuccess(User user);
    }

    interface Model{
        void onLoginSuccess(FirebaseUser firebaseUser);
        void onLoginFailed();
    }

    interface View{
        void setAuthListener();
        void onDestroy();
        void setSharedPreferences(SharedPreferences sharedPreferences);
    }
}
