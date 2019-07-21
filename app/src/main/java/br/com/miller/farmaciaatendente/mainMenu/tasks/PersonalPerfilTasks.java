package br.com.miller.farmaciaatendente.mainMenu.tasks;

import android.graphics.Bitmap;

import br.com.miller.farmaciaatendente.domain.User;

public interface PersonalPerfilTasks {

    interface View{

        void getUserData(String firebaseId, String city);
        void downloadImage(String type, String city, String image);
        void uploadImage(String type, String city, String image, Bitmap bitmap);
        void updateUser(User user);
        void checkData(Object o, int type, User user);

    }

    interface Presenter {

        void onImageUploadSuccess(Bitmap bitmap);
        void onImageUploadFailed();
        void onImageDownloadSuccess(Bitmap bitmap);
        void onImageDownloadFailed();
        void onUserDataSuccess(User user);
        void onUserDataFailed();
        void onUpdateUserSuccess(User user);
        void onUpdateUserFailed();
    }

    interface Model{

        void onImageDownloadSuccess(Bitmap bitmap);
        void onImageDownloadFailed();
        void onImageUploadSuccess(Bitmap bitmap);
        void onImageUploadFailed();
        void onUserDataSuccess(User user);
        void onUserDataFailed();
        void onUpdateUserSuccess(User user);
        void onUpdateUserFailed();
    }
}
