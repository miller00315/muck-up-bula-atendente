package br.com.miller.farmaciaatendente.saleManager.presenters;
import br.com.miller.farmaciaatendente.domain.Buy;
import br.com.miller.farmaciaatendente.saleManager.models.ManipulateBuyModel;
import br.com.miller.farmaciaatendente.saleManager.tasks.ManipulateBuyTask;

public class ManipulateBuyPresenter implements ManipulateBuyTask.Model, ManipulateBuyTask.View {

    private ManipulateBuyTask.Presenter presenter;
    private ManipulateBuyModel model;

    public ManipulateBuyPresenter(ManipulateBuyTask.Presenter presenter) {
        this.presenter = presenter;
        this.model = new ManipulateBuyModel(this);
    }

    @Override
    public void onBuyDataSuccess(Buy buy) { presenter.onBuyDataSuccess(buy); }

    @Override
    public void onBuyDataFailed() { presenter.onBuyDataFailed(); }

    @Override
    public void onChangeSucces(Buy buy) { presenter.onChangeSucces(buy);}

    @Override
    public void onChangeFailed() { presenter.onChangeFailed(); }

    @Override
    public void getBuy(String city, String storeId, String buyId, String status) { model.getBuy(city, storeId, buyId, status); }

    @Override
    public void sendBuyToSended() { model.sendBuyToSended(); }

    @Override
    public void sendBuyToReceived() { model.sendBuyToReceived(); }

    @Override
    public void sendBuyToCanceled() { model.senBuyToCanceled(); }
}
