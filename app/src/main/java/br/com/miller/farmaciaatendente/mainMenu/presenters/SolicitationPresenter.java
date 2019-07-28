package br.com.miller.farmaciaatendente.mainMenu.presenters;

import android.util.Log;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.domain.Buy;
import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.mainMenu.models.SolicitationModel;
import br.com.miller.farmaciaatendente.mainMenu.tasks.SolicitationTasks;
import br.com.miller.farmaciaatendente.utils.StringUtils;

public class SolicitationPresenter implements SolicitationTasks.View, SolicitationTasks.Model {

    private SolicitationTasks.Presenter presenter;
    private SolicitationModel solicitationModel;

    public SolicitationPresenter(SolicitationTasks.Presenter presenter) {
        this.presenter = presenter;
        solicitationModel = new SolicitationModel(this);
    }

    @Override
    public void onBuysDataSuccess(ArrayList<Buy> buys) { presenter.onBuysDataSuccess(buys); }

    @Override
    public void onBuysDataFailed() { presenter.onBuysDataFailed(); }

    @Override
    public void getSolicitations(User user) {

        if(!user.getStoreId().isEmpty())
            solicitationModel.getNewsBuy(user.getStoreId(), user.getCity());
        else
            presenter.onNoStore();
    }

    @Override
    public void onDestroy(String storeId, String city) {
        if(!storeId.isEmpty())
            solicitationModel.removeNewsEventListener(storeId, city);
    }

    public void temporaryVerify(User user){

        if(!user.getStoreId().isEmpty())
            solicitationModel.temporaryVerify(user.getStoreId(), user.getCity());
        else
            presenter.onNoStore();
    }

    @Override
    public void moveToSendedBuy(String city, String storeId, String buyId, String userId) { }
}
