package br.com.miller.farmaciaatendente.opinions.models;

import com.google.firebase.database.FirebaseDatabase;

import br.com.miller.farmaciaatendente.opinions.tasks.OpinionTasks;

public class OpinionModel {

    private OpinionTasks.Model model;
    private FirebaseDatabase firebaseDatabase;

    public OpinionModel(OpinionTasks.Model model) {
        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }
}
