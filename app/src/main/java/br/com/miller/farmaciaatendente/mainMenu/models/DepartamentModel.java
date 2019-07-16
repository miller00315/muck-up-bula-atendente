package br.com.miller.farmaciaatendente.mainMenu.models;

import com.google.firebase.database.FirebaseDatabase;

import br.com.miller.farmaciaatendente.mainMenu.tasks.DepartamentTask;

public class DepartamentModel {

    private DepartamentTask.Model model;
    private FirebaseDatabase firebaseDatabase;

    public DepartamentModel(DepartamentTask.Model model) {

        this.model = model;

        firebaseDatabase = FirebaseDatabase.getInstance();
    }
}
