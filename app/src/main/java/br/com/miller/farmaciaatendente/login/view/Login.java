package br.com.miller.farmaciaatendente.login.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.login.presenter.LoginPresenter;
import br.com.miller.farmaciaatendente.login.tasks.LoginTasks;

public class Login extends AppCompatActivity implements LoginTasks.Presenter {

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this);
    }
}
