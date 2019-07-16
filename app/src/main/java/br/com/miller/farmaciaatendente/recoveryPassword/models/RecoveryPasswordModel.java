package br.com.miller.farmaciaatendente.recoveryPassword.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import br.com.miller.farmaciaatendente.recoveryPassword.tasks.RecoveryPasswordTasks;

public class RecoveryPasswordModel {

    private RecoveryPasswordTasks.Model model;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    public RecoveryPasswordModel(RecoveryPasswordTasks.Model model) {
        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }
}
