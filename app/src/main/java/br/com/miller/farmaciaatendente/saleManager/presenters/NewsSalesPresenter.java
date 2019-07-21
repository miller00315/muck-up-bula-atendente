package br.com.miller.farmaciaatendente.saleManager.presenters;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.domain.Buy;
import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.saleManager.models.NewsSalesModel;
import br.com.miller.farmaciaatendente.saleManager.tasks.NewsSalesTasks;

public class NewsSalesPresenter implements NewsSalesTasks.Model, NewsSalesTasks.View {

    private NewsSalesModel newsSalesModel;
    private NewsSalesTasks.Presenter presenter;

    public NewsSalesPresenter(NewsSalesTasks.Presenter presenter) {

        this.presenter = presenter;
        this.newsSalesModel = new NewsSalesModel(this);
    }

    @Override
    public void getSolicitations(User user) {

        if(!user.getStoreId().isEmpty())
            newsSalesModel.getNewsBuy(user.getStoreId(), user.getCity());
        else
            presenter.onBuysDataFailed();
    }

    @Override
    public void onDestroy(String storeId, String city) {
        if(!storeId.isEmpty())
            newsSalesModel.removeNewsEventListener(storeId, city);
    }

    @Override
    public void onBuysDataSuccess(ArrayList<Buy> buys) { presenter.onBuysDataSuccess(buys);}

    @Override
    public void onBuysDataFailed() { presenter.onBuysDataFailed(); }
}
