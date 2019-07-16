package br.com.miller.farmaciaatendente.register.models;

import com.google.firebase.database.FirebaseDatabase;

import br.com.miller.farmaciaatendente.register.tasks.RegisterTasks;

public class RegisterModel {

    private RegisterTasks.Model model;
    private FirebaseDatabase firebaseDatabase;

    public RegisterModel(RegisterTasks.Model model) {

        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }
}
