package br.com.miller.farmaciaatendente.productManager.models;

import com.google.firebase.database.FirebaseDatabase;

import br.com.miller.farmaciaatendente.productManager.tasks.ProductTasks;

public class ProductModel {

    private ProductTasks.Model model;
    private FirebaseDatabase firebaseDatabase;

    public ProductModel(ProductTasks.Model model) {
        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }
}
