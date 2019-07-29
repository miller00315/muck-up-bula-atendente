package br.com.miller.farmaciaatendente.saleManager.presenters;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.domain.Buy;
import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.saleManager.models.SendedSalesModel;
import br.com.miller.farmaciaatendente.saleManager.tasks.SendedSalesTasks;

public class SendedSalesPresenter implements SendedSalesTasks.Model, SendedSalesTasks.View {

    private SendedSalesModel sendedSalesModel;
    private SendedSalesTasks.Presenter presenter;

    public SendedSalesPresenter(SendedSalesTasks.Presenter presenter) {
        this.presenter = presenter;
        sendedSalesModel = new SendedSalesModel(this);
    }

    @Override
    public void getSolicitations(User user) {
        if(!user.getStoreId().isEmpty())
            sendedSalesModel.getSendedBuys(user.getStoreId(), user.getCity());
        else
            presenter.onBuysDataFailed();
    }

    @Override
    public void onDestroy(String storeId, String city) {
        if(!storeId.isEmpty())
            sendedSalesModel.removeSendedEventListener(storeId, city);
    }

    @Override
    public void onBuysDataSuccess(ArrayList<Buy> buys) { presenter.onBuysDataSuccess(buys);}

    @Override
    public void onBuysDataFailed() { presenter.onBuysDataFailed(); }

    @Override
    public void onSaleUpdate(Buy buy) { presenter.onSaleUpdate(buy);}

    @Override
    public void onSaleAdded(Buy buy) { presenter.onSaleAdded(buy);}

    @Override
    public void onSalesRemoved(Buy buy) { presenter.onSalesRemoved(buy);}

    public void temporaryVerify(String storeId, String city){
        sendedSalesModel.temporaryVerify(storeId, city);
    }
}
