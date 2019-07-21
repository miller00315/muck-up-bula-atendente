package br.com.miller.farmaciaatendente.register.tasks;

import android.content.SharedPreferences;
import android.graphics.Bitmap;

import br.com.miller.farmaciaatendente.domain.User;

public interface RegisterTasks {

    interface Model{
        void onUserGenerateSuccess(User user, String password);
        void onAnonimousSuccess();
        void onAnonimousFailed();
        void onCredentialSuccess(User user);
        void onCredentialFailed();
        void onUserDatabaseSuccess(User user);
        void onUserDatabaseFailed();
        void onImageUploadSuccess(Bitmap bitmap);
        void onImageUploadFailed();
        void onAnonymmousUserDestroySuccess();
        void onAnonymousUserDestroyFailed();
    }
    interface Presenter{
        void onCheckDataFailed(int code);
        void onCredentialFailed();
        void onCheckDataOk();
        void onAnonimousSuccess();
        void onAnonimousFailed();
        void onImageUploadSuccess(Bitmap bitmap);
        void onImageUploadFailed();
        void onAnonymmousUserDestroySuccess();
        void onAnonymousUserDestroyFailed();
        void onUserDataBaseSuccess(User user);
        void onUserDataBaseFailed();
        void onUserDataSuccess(User user);
    }

    interface View {
        void firstLogin();
        void setSharedPreferences(SharedPreferences sharedPreferences);
        void destroyAnonymousUser();
        void uploadImage(User user, Bitmap bitmap);
        void checkUserData(String name, String surname, String bithDate, String email, String phone, String password, String RepeatPassword);
    }
}
