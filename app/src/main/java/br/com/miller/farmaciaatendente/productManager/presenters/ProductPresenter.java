package br.com.miller.farmaciaatendente.productManager.presenters;

import br.com.miller.farmaciaatendente.productManager.models.ProductModel;
import br.com.miller.farmaciaatendente.productManager.tasks.ProductTasks;

public class ProductPresenter implements ProductTasks.View, ProductTasks.Model {

    private ProductTasks.Presenter presenter;
    private ProductModel productModel;

    public ProductPresenter(ProductTasks.Presenter presenter) {
        this.presenter = presenter;
        productModel = new ProductModel(this);
    }
}
