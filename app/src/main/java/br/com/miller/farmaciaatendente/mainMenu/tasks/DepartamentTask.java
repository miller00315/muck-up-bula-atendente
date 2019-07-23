package br.com.miller.farmaciaatendente.mainMenu.tasks;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.domain.Departament;

public interface DepartamentTask {

    interface View{
        void getDepartaments(String city, String storeId);
        void addDepartment(Departament departament, ArrayList<Departament> departaments, String storeId);
        void getDepartamentsAvaliables(String city);
    }

    interface Presenter {
        void onDepartamentsSuccess(ArrayList<Departament> departaments);
        void onDepartamentsFailed();
        void onDepartamentsAvaliablesuccess(ArrayList<Departament> departaments);
        void onDepartmentAddFailed();
    }

    interface Model{
        void onDepartamentSuccess(ArrayList<Departament> departaments);
        void onDepartamentFailed();
        void onDepartamentsItemSuccess(ArrayList<Departament> departaments);
        void onDepartmentAddFailed();
    }
}
