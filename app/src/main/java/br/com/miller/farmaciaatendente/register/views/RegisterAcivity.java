package br.com.miller.farmaciaatendente.register.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.mainMenu.views.activities.MainMenuActivity;
import br.com.miller.farmaciaatendente.register.presenters.RegisterPresenter;
import br.com.miller.farmaciaatendente.register.tasks.RegisterTasks;
import br.com.miller.farmaciaatendente.utils.Constants;
import br.com.miller.farmaciaatendente.utils.ImageUtils;

public class RegisterAcivity extends AppCompatActivity implements RegisterTasks.Presenter {

    private RegisterPresenter registerPresenter;
    private EditText name, surname, birthDate, email, phone, password, repeatPassword;
    private ImageView imageUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerPresenter = new RegisterPresenter(this);

        registerPresenter.firstLogin();

        registerPresenter.setSharedPreferences(getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE));

        bindViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        registerPresenter.destroyAnonymousUser();
    }

    public void bindViews(){

        imageUser = findViewById(R.id.image_user);
        name = findViewById(R.id.name_user);
        surname = findViewById(R.id.surname_user);
        birthDate = findViewById(R.id.birth_date_user);
        email = findViewById(R.id.email_user);
        phone = findViewById(R.id.phone_user);
        password = findViewById(R.id.password_user);
        repeatPassword = findViewById(R.id.repeat_password_user);

    }

    public void registerUser(View view) {

        registerPresenter.checkUserData(name.getText().toString(), surname.getText().toString(),
                birthDate.getText().toString(), email.getText().toString(), phone.getText().toString(),
                password.getText().toString(), repeatPassword.getText().toString());
    }

    @Override
    public void onCheckDataFailed(int code) {

        switch (code){

            case 1:{

            }
            break;

            case 2:{

            }
            break;

            case  3:{

            }
            break;
            case 4:{

            }
            break;

            case 5:{

            }
            break;

            case  6:{

            }
            break;

            case 7:{

            }
            break;

            case 8:{

            }
            break;

            case  9:{

            }
            break;

            default:
                break;
        }

    }

    @Override
    public void onCredentialFailed() { Toast.makeText(this, "Erro ao criar credencial", Toast.LENGTH_SHORT).show(); }

    @Override
    public void onCheckDataOk() { Toast.makeText(this, "Dados ok, iniciando registro do usuário", Toast.LENGTH_SHORT).show(); }

    @Override
    public void onAnonimousSuccess() { Toast.makeText(this, "Tudo pronto par o cadastro", Toast.LENGTH_SHORT).show();}

    @Override
    public void onAnonimousFailed() {
        Toast.makeText(this, "Erro ao tentar acessar base de dados, tente novamente", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onImageUploadSuccess(Bitmap bitmap) { Toast.makeText(this, "Imgem enviada, pronto para começar", Toast.LENGTH_SHORT).show(); }

    @Override
    public void onImageUploadFailed() { Toast.makeText(this, "Erro ao enviar a imagem, tente novamente", Toast.LENGTH_SHORT).show(); }

    @Override
    public void onAnonymmousUserDestroySuccess() { Toast.makeText(this, "Usuário temporário removido", Toast.LENGTH_SHORT).show();}

    @Override
    public void onAnonymousUserDestroyFailed() { }

    @Override
    public void onUserDataBaseSuccess(User user) { registerPresenter.uploadImage(user, ImageUtils.getImageUser(imageUser)); }

    @Override
    public void onUserDataBaseFailed() { Toast.makeText(this, "Erro ao fazer o upload da imagem, tente novamente", Toast.LENGTH_SHORT).show(); }

    @Override
    public void onUserDataSuccess(User user) {

        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivityForResult(intent, 1222);
        finish();
    }
}
