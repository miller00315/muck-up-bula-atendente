package br.com.miller.farmaciaatendente.recoveryPassword.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.recoveryPassword.presenters.RecoveryPasswordPresenter;
import br.com.miller.farmaciaatendente.recoveryPassword.tasks.RecoveryPasswordTasks;

public class RecoveryPasswordActivity extends AppCompatActivity implements RecoveryPasswordTasks.Presenter {

    private RecoveryPasswordPresenter recoveryPasswordPresenter;
    private EditText email;
    private RelativeLayout mainLayout, loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_password_actvity);

        recoveryPasswordPresenter = new RecoveryPasswordPresenter(this);

        mainLayout = findViewById(R.id.main_layout);
        loadingLayout = findViewById(R.id.layout_loading);

        bindViews();
    }

    private void bindViews(){ email = findViewById(R.id.email); }

    public void reset(View view) {

        showLoading();
        recoveryPasswordPresenter.resetPassword(email.getText().toString());
    }

    private void showLoading(){
        loadingLayout.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);
    }

    private void hideLoading(){
        loadingLayout.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void emailEmpty() {
        hideLoading();
        email.setHintTextColor(getResources().getColor(R.color.colorRed));
    }

    @Override
    public void onRecoverySuccess() {
        Toast.makeText(this, "Email de recuperação enviado", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onRecoveryFailed(Exception e) { Toast.makeText(this, "Falha ao recuperar a senha, tente novamente", Toast.LENGTH_SHORT).show();}
}
