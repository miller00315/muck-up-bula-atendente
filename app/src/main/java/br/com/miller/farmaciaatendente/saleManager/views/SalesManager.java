package br.com.miller.farmaciaatendente.saleManager.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.saleManager.presenters.SalePresenter;
import br.com.miller.farmaciaatendente.saleManager.tasks.SaleTasks;

public class SalesManager extends AppCompatActivity implements SaleTasks.Presenter {

    private SalePresenter salePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_manager);

        salePresenter = new SalePresenter(this);
    }
}
