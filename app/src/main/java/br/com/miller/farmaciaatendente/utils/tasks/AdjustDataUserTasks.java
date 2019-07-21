package br.com.miller.farmaciaatendente.utils.tasks;

import br.com.miller.farmaciaatendente.domain.User;

public interface AdjustDataUserTasks {

    interface Presenter {

        void onAdjustUserCitySuccess(String firebaseId, String city);
        void onAdjustCityFailed();
        void onAdjustUserDataSuccess(User user);
        void onAdjustUserDataFailed();

    }

}
