package br.com.miller.farmaciaatendente.mainMenu.presenters;

import br.com.miller.farmaciaatendente.mainMenu.models.SolicitationModel;
import br.com.miller.farmaciaatendente.mainMenu.tasks.SolicitationTasks;

public class SolicitationPresenter implements SolicitationTasks.View, SolicitationTasks.Model {

    private SolicitationTasks.Presenter presenter;
    private SolicitationModel solicitationModel;

    public SolicitationPresenter(SolicitationTasks.Presenter presenter) {
        this.presenter = presenter;
        solicitationModel = new SolicitationModel(this);
    }
}
