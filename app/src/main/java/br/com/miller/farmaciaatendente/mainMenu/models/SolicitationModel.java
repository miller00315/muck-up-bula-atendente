package br.com.miller.farmaciaatendente.mainMenu.models;

import com.google.firebase.database.FirebaseDatabase;

import br.com.miller.farmaciaatendente.mainMenu.tasks.SolicitationTasks;

public class SolicitationModel {

    private SolicitationTasks.Model model;
    private FirebaseDatabase firebaseDatabase;

    public SolicitationModel(SolicitationTasks.Model model) {

        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }
}
