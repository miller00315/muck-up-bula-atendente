package br.com.miller.farmaciaatendente.recoveryPassword.presenters;

import br.com.miller.farmaciaatendente.recoveryPassword.models.RecoveryPasswordModel;
import br.com.miller.farmaciaatendente.recoveryPassword.tasks.RecoveryPasswordTasks;
import br.com.miller.farmaciaatendente.utils.StringUtils;

public class RecoveryPasswordPresenter implements RecoveryPasswordTasks.View, RecoveryPasswordTasks.Model {

    private RecoveryPasswordTasks.Presenter presenter;
    private RecoveryPasswordModel model;

    public RecoveryPasswordPresenter(RecoveryPasswordTasks.Presenter presenter) {
        this.presenter = presenter;
        model = new RecoveryPasswordModel(this);
    }

    @Override
    public void resetPassword(String email) {

        if(StringUtils.isValidEmail(email))

        if (email.isEmpty()) presenter.emailEmpty();
        else model.resetPassword(email);
    }

    @Override
    public void onRecoverySuccess() { presenter.onRecoverySuccess(); }

    @Override
    public void onRecoveryFailed(Exception e) { presenter.onRecoveryFailed(e); }
}
