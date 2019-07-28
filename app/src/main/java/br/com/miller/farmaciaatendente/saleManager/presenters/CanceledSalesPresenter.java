package br.com.miller.farmaciaatendente.saleManager.presenters;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.domain.Buy;
import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.saleManager.models.CanceledSalesModel;
import br.com.miller.farmaciaatendente.saleManager.tasks.CanceledSaleTask;

public class CanceledSalesPresenter implements CanceledSaleTask.Model, CanceledSaleTask.View {

    private CanceledSalesModel canceledSalesModel;
    private CanceledSaleTask.Presenter presenter;

    public CanceledSalesPresenter(CanceledSaleTask.Presenter presenter) {
        this.presenter = presenter;
        canceledSalesModel = new CanceledSalesModel(this);
    }

    @Override
    public void getSolicitations(User user) {

        if(!user.getStoreId().isEmpty())
            canceledSalesModel.getNewsBuy(user.getStoreId(), user.getCity());
        else
            presenter.onNoStore();
    }

    @Override
    public void onDestroy(String storeId, String city) {
        if(!storeId.isEmpty())
            canceledSalesModel.removeNewsEventListener(storeId, city);
    }

    public void temporaryVerify(String storeId, String city){
        canceledSalesModel.temporaryVerify(storeId, city);
    }

    @Override
    public void onBuysDataSuccess(ArrayList<Buy> buys) { presenter.onBuysDataSuccess(buys);}

    @Override
    public void onBuysDataFailed() { presenter.onBuysDataFailed(); }

}
