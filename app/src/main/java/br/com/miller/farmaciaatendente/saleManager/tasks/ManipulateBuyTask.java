package br.com.miller.farmaciaatendente.saleManager.tasks;

import br.com.miller.farmaciaatendente.domain.Buy;

public interface ManipulateBuyTask {

    interface View{
        void getBuy(String city, String storeId, String buyId, String status);
        void sendBuyToSended();
        void sendBuyToReceived();
        void sendBuyToCanceled();
    }

    interface Presenter {
        void onBuyDataSuccess(Buy buy);
        void onBuyDataFailed();
        void onChangeSucces(Buy buy);
        void onChangeFailed();
    }

    interface Model{
        void onBuyDataSuccess(Buy buy);
        void onBuyDataFailed();
        void onChangeSucces(Buy buy);
        void onChangeFailed();

    }
}
