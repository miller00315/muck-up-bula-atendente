package br.com.miller.farmaciaatendente.recoveryPassword.tasks;

public interface RecoveryPasswordTasks {

    interface View{
        void resetPassword(String email);
    }

    interface Presenter {
        void emailEmpty();
        void onRecoverySuccess();
        void onRecoveryFailed(Exception e);
    }

    interface Model{
        void onRecoverySuccess();
        void onRecoveryFailed(Exception e);
    }
}
