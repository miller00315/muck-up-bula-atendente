package br.com.miller.farmaciaatendente.mainMenu.models;

import com.google.firebase.database.FirebaseDatabase;

import br.com.miller.farmaciaatendente.mainMenu.tasks.PerfilTasks;

public class PerfilModel {

    private PerfilTasks.Model model;
    private FirebaseDatabase firebaseDatabase;

    public PerfilModel(PerfilTasks.Model model) {

        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }
}
