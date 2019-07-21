package br.com.miller.farmaciaatendente.utils.tasks;

public interface PermissionsTask {

    interface Presenter{
        void onPermissionOnSuccess();
        void onPermissionOnPermissionFailed();
    }
}
