package br.com.miller.farmaciaatendente.saleManager.tasks;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.domain.Buy;
import br.com.miller.farmaciaatendente.domain.User;

public interface SendedSalesTasks {

    interface View{

        void getSolicitations(User user);
        void onDestroy(String storeId, String city);
    }

    interface Presenter {
        void onBuysDataSuccess(ArrayList<Buy> buys);
        void onBuysDataFailed();
        void onNoStore();
    }

    interface Model{

        void onBuysDataSuccess(ArrayList<Buy> buys);
        void onBuysDataFailed();
    }

}
