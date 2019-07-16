package br.com.miller.farmaciaatendente.productManager.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.productManager.presenters.ProductPresenter;
import br.com.miller.farmaciaatendente.productManager.tasks.ProductTasks;

public class ProductMangerActivity extends AppCompatActivity implements ProductTasks.Presenter {

    private ProductPresenter productPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_manger);

        productPresenter = new ProductPresenter(this);
    }
}
