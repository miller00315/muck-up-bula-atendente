package br.com.miller.farmaciaatendente.recoveryPassword.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.recoveryPassword.presenters.RecoveryPasswordPresenter;
import br.com.miller.farmaciaatendente.recoveryPassword.tasks.RecoveryPasswordTasks;

public class RecoveryPasswordActivity extends AppCompatActivity implements RecoveryPasswordTasks.Presenter {

    private RecoveryPasswordPresenter recoveryPasswordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_password_actvity);

        recoveryPasswordPresenter = new RecoveryPasswordPresenter(this);
    }
}
