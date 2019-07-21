package br.com.miller.farmaciaatendente.utils.tasks;

public interface AlertDialogUtilsTask {

    interface Presenter{

        void onAlertPositive(Object o, int type);
        void onALertNegative();
    }

}
