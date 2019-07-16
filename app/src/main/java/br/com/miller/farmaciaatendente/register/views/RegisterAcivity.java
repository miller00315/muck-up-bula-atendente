package br.com.miller.farmaciaatendente.register.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.register.presenters.RegisterPresenter;
import br.com.miller.farmaciaatendente.register.tasks.RegisterTasks;

public class RegisterAcivity extends AppCompatActivity implements RegisterTasks.Presenter {

    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_acivity);

        registerPresenter = new RegisterPresenter(this);
    }
}
