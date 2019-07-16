package br.com.miller.farmaciaatendente.recoveryPassword.presenters;

import br.com.miller.farmaciaatendente.recoveryPassword.models.RecoveryPasswordModel;
import br.com.miller.farmaciaatendente.recoveryPassword.tasks.RecoveryPasswordTasks;

public class RecoveryPasswordPresenter implements RecoveryPasswordTasks.View, RecoveryPasswordTasks.Model {

    private RecoveryPasswordTasks.Presenter presenter;
    private RecoveryPasswordModel model;

    public RecoveryPasswordPresenter(RecoveryPasswordTasks.Presenter presenter) {
        this.presenter = presenter;
        model = new RecoveryPasswordModel(this);
    }
}
