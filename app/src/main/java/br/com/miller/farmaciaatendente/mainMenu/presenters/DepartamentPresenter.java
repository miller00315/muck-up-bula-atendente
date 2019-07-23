package br.com.miller.farmaciaatendente.mainMenu.presenters;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.domain.Departament;
import br.com.miller.farmaciaatendente.mainMenu.models.DepartamentModel;
import br.com.miller.farmaciaatendente.mainMenu.tasks.DepartamentTask;

public class DepartamentPresenter implements DepartamentTask.View, DepartamentTask.Model {

    private DepartamentTask.Presenter presenter;
    private DepartamentModel model;

    public DepartamentPresenter(DepartamentTask.Presenter presenter) {
        this.presenter = presenter;

        model = new DepartamentModel(this);
    }

    @Override
    public void onDepartamentSuccess(ArrayList<Departament> departaments) { presenter.onDepartamentsSuccess(departaments);}

    @Override
    public void onDepartamentFailed() { presenter.onDepartamentsFailed(); }

    @Override
    public void onDepartamentsItemSuccess(ArrayList<Departament> departaments) { presenter.onDepartamentsAvaliablesuccess(departaments);}

    @Override
    public void onDepartmentAddFailed() { presenter.onDepartmentAddFailed(); }

    @Override
    public void getDepartaments(String city, String storeId) { model.getDepartaments(city, storeId); }

    @Override
    public void addDepartment(Departament departament, ArrayList<Departament> departaments, String storeId) {

        boolean flag = true;

        for (Departament d : departaments){

            if(d.getId().equals(departament.getId())){

                flag = false;

                break;
            }
        }

        if(flag)
            model.addDepartament(departament, storeId);
        else
            presenter.onDepartmentAddFailed();
    }

    @Override
    public void getDepartamentsAvaliables(String city) { model.getDepartmentsAvaliables(city);}
}
