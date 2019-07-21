package br.com.miller.farmaciaatendente.mainMenu.tasks;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.domain.Buy;
import br.com.miller.farmaciaatendente.domain.User;

public interface SolicitationTasks {

    interface View{

        void getSolicitations(User user);
        void onDestroy(String storeId, String city);
        void moveToSendedBuy(final String city, final String storeId, final String buyId, String userId);
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
