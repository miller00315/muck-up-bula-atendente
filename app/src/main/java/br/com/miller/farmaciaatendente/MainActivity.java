package br.com.miller.farmaciaatendente;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;

import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.login.view.Login;
import br.com.miller.farmaciaatendente.main.MainActivityTasks;
import br.com.miller.farmaciaatendente.main.presenters.MainActivityPresenter;
import br.com.miller.farmaciaatendente.mainMenu.views.activities.MainMenuActivity;
import br.com.miller.farmaciaatendente.utils.Constants;
import br.com.miller.farmaciaatendente.utils.NotifiactionUtils;
import br.com.miller.farmaciaatendente.utils.presenters.Permissions;
import br.com.miller.farmaciaatendente.utils.tasks.PermissionsTask;

public class MainActivity extends AppCompatActivity implements PermissionsTask.Presenter, MainActivityTasks.Presenter {

    private MainActivityPresenter mainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Permissions permissions = new Permissions(this, this);
        mainActivityPresenter = new MainActivityPresenter(this);

        NotifiactionUtils.createNotificationChannel(getApplicationContext());

        permissions.checkSelfPermission();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainActivityPresenter.onDestroy();
    }

    @Override
    public void onPermissionOnSuccess() {

        mainActivityPresenter.setSharedPreferences(getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE));
        mainActivityPresenter.setAuthListener();

    }

    @Override
    public void onPermissionOnPermissionFailed() {
        Toast.makeText(this, "Não é possivel executar o aplicativo sem as devidas permissões", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == Permissions.pCode){

            boolean flag = true;

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                for (int i = 0, len = permissions.length; i < len; i++){
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        flag = false;

                    }
            }

                if(flag) this.onPermissionOnSuccess();
                else this.onPermissionOnPermissionFailed();

            }
        }
    }

    @Override
    public void onLoginSuccess(FirebaseUser firebaseUser) { Toast.makeText(this, "Login realizado", Toast.LENGTH_SHORT).show(); }

    @Override
    public void onLoginFailed() {
        Intent intent = new Intent(this, Login.class);
        startActivityForResult(intent, 222);
        finish();
    }

    @Override
    public void onUserDataSuccess(User user) {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivityForResult(intent, 222);
        finish();
    }
}
