package br.com.miller.farmaciaatendente.login.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.login.presenter.LoginPresenter;
import br.com.miller.farmaciaatendente.login.tasks.LoginTasks;
import br.com.miller.farmaciaatendente.mainMenu.views.activities.MainMenuActivity;
import br.com.miller.farmaciaatendente.recoveryPassword.views.RecoveryPasswordActivity;
import br.com.miller.farmaciaatendente.register.views.RegisterAcivity;
import br.com.miller.farmaciaatendente.utils.Constants;

public class Login extends AppCompatActivity implements LoginTasks.Presenter {

    private LoginPresenter presenter;
    private EditText user, password;
    private RelativeLayout loadingLayout;
    private ScrollView mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this);
        presenter.setSharedPreferences(getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE));

        bindViews();
    }

    private void bindViews(){

        user        = findViewById(R.id.login);
        password    = findViewById(R.id.password);

        loadingLayout = findViewById(R.id.layout_loading);
        mainLayout = findViewById(R.id.main_layout);

        hideLoading();
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterAcivity.class);
        startActivityForResult(intent, 2222);
    }

    private void showLoading(){
        loadingLayout.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);
    }

    private void hideLoading(){
        loadingLayout.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.VISIBLE);
    }

    public void recuperarSenha(View view) { startActivity(new Intent(this, RecoveryPasswordActivity.class)); }

    public void login(View view) {

        showLoading();
        presenter.login(user.getText().toString(), password.getText().toString());
    }

    @Override
    public void inputEmpty(int code) {

        Toast.makeText(this, "Falta alguma informação", Toast.LENGTH_SHORT).show();

        switch (code){

            case 1:{
                user.setHintTextColor(getResources().getColor(R.color.colorRed));
            }
            break;

            case 2:{
                password.setHintTextColor(getResources().getColor(R.color.colorRed));
            }
            break;

            default:
                break;
        }

        hideLoading();

    }

    @Override
    public void onLoginFailed() {
        hideLoading();
        Toast.makeText(this, "Erro ao realizar login, tente novamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdjustDataUserFailed() {
        hideLoading();
    }

    @Override
    public void onAdjustDataUserSuccess(User user) {

        Toast.makeText(this, "Bem-vindo ", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivityForResult(intent, 222);
        finish();

    }

}
