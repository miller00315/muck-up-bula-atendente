package br.com.miller.farmaciaatendente.saleManager.presenters;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.domain.Buy;
import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.saleManager.models.ReceivedSalesModel;
import br.com.miller.farmaciaatendente.saleManager.tasks.ReceivedSalesTask;

public class ReceivedSalesPresenter implements ReceivedSalesTask.Model, ReceivedSalesTask.View {

    private ReceivedSalesModel receivedSalesModel;
    private ReceivedSalesTask.Presenter presenter;

    public ReceivedSalesPresenter(ReceivedSalesTask.Presenter presenter) {
        this.presenter = presenter;
        receivedSalesModel = new ReceivedSalesModel(this);
    }

    @Override
    public void getSolicitations(User user) {
        if(!user.getStoreId().isEmpty())
            receivedSalesModel.getReceivedBuys(user.getStoreId(), user.getCity());
        else
            presenter.onBuysDataFailed();

    }

    @Override
    public void onDestroy(String storeId, String city) {
        if(!storeId.isEmpty()) receivedSalesModel.removeReceivedListener(storeId, city);
    }

    public void temporaryVerify(String storeId, String city){
        receivedSalesModel.temporaryVerify(storeId, city);
    }

    @Override
    public void onBuysDataSuccess(ArrayList<Buy> buys) { presenter.onBuysDataSuccess(buys);}

    @Override
    public void onBuysDataFailed() { presenter.onBuysDataFailed(); }

    @Override
    public void onSaleAdded(Buy buy) { presenter.onSaleAdded(buy); }

    @Override
    public void onSaleUpdate(Buy buy) { presenter.onSaleUpdate(buy); }

    @Override
    public void onSalesRemoved(Buy buy) { presenter.onSalesRemoved(buy); }
}
