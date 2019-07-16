package br.com.miller.farmaciaatendente.login.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import br.com.miller.farmaciaatendente.login.tasks.LoginTasks;

public class LoginModel {

    private LoginTasks.Model model;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    public LoginModel(LoginTasks.Model model) {
        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }
}
