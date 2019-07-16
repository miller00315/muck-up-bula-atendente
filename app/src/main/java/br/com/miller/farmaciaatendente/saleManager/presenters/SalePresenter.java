package br.com.miller.farmaciaatendente.saleManager.presenters;

import br.com.miller.farmaciaatendente.saleManager.models.SaleModel;
import br.com.miller.farmaciaatendente.saleManager.tasks.SaleTasks;

public class SalePresenter implements SaleTasks.Model, SaleTasks.View {

    private SaleModel model;
    private SaleTasks.Presenter presenter;

    public SalePresenter(SaleTasks.Presenter presenter) {
        this.presenter = presenter;
        model = new SaleModel(this);
    }
}
