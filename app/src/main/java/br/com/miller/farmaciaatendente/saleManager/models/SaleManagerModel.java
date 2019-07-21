package br.com.miller.farmaciaatendente.saleManager.models;

import com.google.firebase.database.FirebaseDatabase;

import br.com.miller.farmaciaatendente.saleManager.tasks.SaleManagerTasks;

public class SaleManagerModel {

    private SaleManagerTasks.Model model;
    private FirebaseDatabase firebaseDatabase;

    public SaleManagerModel(SaleManagerTasks.Model model) {
        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }
}
