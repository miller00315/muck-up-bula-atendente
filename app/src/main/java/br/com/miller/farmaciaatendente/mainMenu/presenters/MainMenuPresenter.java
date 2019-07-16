package br.com.miller.farmaciaatendente.mainMenu.presenters;

import br.com.miller.farmaciaatendente.mainMenu.models.MainMenuModel;
import br.com.miller.farmaciaatendente.mainMenu.tasks.MainMenuTasks;

public class MainMenuPresenter implements MainMenuTasks.Model, MainMenuTasks.View {

    private MainMenuTasks.Presenter presenter;
    private MainMenuModel model;

    public MainMenuPresenter(MainMenuTasks.Presenter presenter) {
        this.presenter = presenter;
        model = new MainMenuModel(this);
    }
}
