package br.com.miller.farmaciaatendente.login.presenter;

import br.com.miller.farmaciaatendente.login.model.LoginModel;
import br.com.miller.farmaciaatendente.login.tasks.LoginTasks;

public class LoginPresenter implements LoginTasks.Model, LoginTasks.View {

    private LoginTasks.Presenter presenter;
    private LoginModel loginModel;

    public LoginPresenter(LoginTasks.Presenter presenter) {
        this.presenter = presenter;
        loginModel = new LoginModel(this);
    }

    @Override
    public void login(String user, String password) {

    }
}
