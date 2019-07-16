package br.com.miller.farmaciaatendente.saleManager.models;

import com.google.firebase.database.FirebaseDatabase;

import br.com.miller.farmaciaatendente.saleManager.tasks.SaleTasks;

public class SaleModel {

    private SaleTasks.Model model;
    private FirebaseDatabase firebaseDatabase;

    public SaleModel(SaleTasks.Model model) {
        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }
}
