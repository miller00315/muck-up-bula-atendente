package br.com.miller.farmaciaatendente.utils.tasks;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.domain.Evaluate;

public interface OpnionDialogFragmentTasks {

    interface Presenter{
        void onGetEvaluateSuccess(ArrayList<Evaluate> evaluates);
        void onGetEvaluateFailed();
    }
    interface Model{
        void onGetEvaluateSuccess(ArrayList<Evaluate> evaluates);
        void onGetEvaluateFailed();
    }
    interface View{
        void getEvaluate(String idStore, String city);
    }
}
