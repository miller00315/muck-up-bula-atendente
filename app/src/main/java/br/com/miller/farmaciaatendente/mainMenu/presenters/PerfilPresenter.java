package br.com.miller.farmaciaatendente.mainMenu.presenters;

import br.com.miller.farmaciaatendente.mainMenu.models.PerfilModel;
import br.com.miller.farmaciaatendente.mainMenu.tasks.PerfilTasks;

public class PerfilPresenter implements PerfilTasks.Model, PerfilTasks.View {

    private PerfilTasks.Presenter presenter;
    private PerfilModel model;

    public PerfilPresenter(PerfilTasks.Presenter presenter) {
        this.presenter = presenter;
        model = new PerfilModel(this);
    }
}
