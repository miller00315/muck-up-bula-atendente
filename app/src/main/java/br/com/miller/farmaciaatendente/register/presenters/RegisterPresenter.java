package br.com.miller.farmaciaatendente.register.presenters;

import br.com.miller.farmaciaatendente.register.models.RegisterModel;
import br.com.miller.farmaciaatendente.register.tasks.RegisterTasks;

public class RegisterPresenter implements RegisterTasks.Model, RegisterTasks.View {

    private RegisterTasks.Presenter presenter;
    private RegisterModel model;

    public RegisterPresenter(RegisterTasks.Presenter presenter) {
        this.presenter = presenter;
        model = new RegisterModel(this);
    }

    @Override
    public void firstLogin() {

    }
}
