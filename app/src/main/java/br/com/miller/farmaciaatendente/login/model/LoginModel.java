package br.com.miller.farmaciaatendente.login.model;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.miller.farmaciaatendente.login.tasks.LoginTasks;

public class LoginModel {

    private LoginTasks.Model model;
    private FirebaseAuth firebaseAuth;

    public LoginModel(LoginTasks.Model model) {
        this.model = model;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void login(String user, String password){

        firebaseAuth.signInWithEmailAndPassword(user, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) { model.onLoginSuccess(authResult.getUser()); }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure( Exception e) { model.onLoginFailed(); }
        });

    }
}
