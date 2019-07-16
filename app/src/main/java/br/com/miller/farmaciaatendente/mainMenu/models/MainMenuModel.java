package br.com.miller.farmaciaatendente.mainMenu.models;

import com.google.firebase.database.FirebaseDatabase;

import br.com.miller.farmaciaatendente.mainMenu.tasks.MainMenuTasks;

public class MainMenuModel {

    private MainMenuTasks.Model model;
    private FirebaseDatabase firebaseDatabase;

    public MainMenuModel(MainMenuTasks.Model model) {

        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();

    }
}
