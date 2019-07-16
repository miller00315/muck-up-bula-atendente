package br.com.miller.farmaciaatendente.mainMenu.presenters;

import br.com.miller.farmaciaatendente.mainMenu.models.DepartamentModel;
import br.com.miller.farmaciaatendente.mainMenu.tasks.DepartamentTask;

public class DepartamentPresenter implements DepartamentTask.View, DepartamentTask.Model {

    private DepartamentTask.Presenter presenter;
    private DepartamentModel model;

    public DepartamentPresenter(DepartamentTask.Presenter presenter) {
        this.presenter = presenter;

        model = new DepartamentModel(this);
    }
}
