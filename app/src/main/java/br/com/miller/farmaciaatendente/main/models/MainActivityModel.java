package br.com.miller.farmaciaatendente.main.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import br.com.miller.farmaciaatendente.main.MainActivityTasks;

public class MainActivityModel {

    private FirebaseAuth.AuthStateListener authStateListener;
    private MainActivityTasks.Model model;
    private FirebaseAuth mAuth;

    public MainActivityModel(MainActivityTasks.Model model) {
        this.model = model;
        mAuth = FirebaseAuth.getInstance();

    }

    public void setAuthStateListener(){
        authStateListener = getAuthStateListener();
        mAuth.addAuthStateListener(authStateListener);
    }

    private FirebaseAuth.AuthStateListener getAuthStateListener(){

        return new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {

                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if(firebaseUser != null){
                        model.onLoginSuccess(firebaseUser);
                }else{
                    model.onLoginFailed();
                }

            }
        };
    }

    public void removeListener(){

        if(mAuth != null)
            mAuth.removeAuthStateListener(authStateListener);
    }


}
