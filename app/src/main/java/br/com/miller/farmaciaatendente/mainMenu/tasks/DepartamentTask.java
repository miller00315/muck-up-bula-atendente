package br.com.miller.farmaciaatendente.mainMenu.tasks;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.domain.Departament;

public interface DepartamentTask {

    interface View{
        void getDepartaments(String city, String storeId);
    }

    interface Presenter {
        void onDepartamentsSuccess(ArrayList<Departament> departaments);
        void onDepartamentsFailed();
    }

    interface Model{
        void onDepartamentSuccess(ArrayList<Departament> departaments);
        void onDepartamentFailed();
    }
}
