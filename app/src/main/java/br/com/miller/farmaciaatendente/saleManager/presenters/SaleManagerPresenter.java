package br.com.miller.farmaciaatendente.saleManager.presenters;

import br.com.miller.farmaciaatendente.saleManager.models.SaleManagerModel;
import br.com.miller.farmaciaatendente.saleManager.tasks.SaleManagerTasks;

public class SaleManagerPresenter implements SaleManagerTasks.Model, SaleManagerTasks.View {

    private SaleManagerModel model;
    private SaleManagerTasks.Presenter presenter;

    public SaleManagerPresenter(SaleManagerTasks.Presenter presenter) {
        this.presenter = presenter;
        model = new SaleManagerModel(this);
    }
}
