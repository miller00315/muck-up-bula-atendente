package br.com.miller.farmaciaatendente.departamentManager.presenter;

import android.widget.Toast;

import br.com.miller.farmaciaatendente.departamentManager.models.DepartamentManagerModel;
import br.com.miller.farmaciaatendente.departamentManager.tasks.DepartamentManagerTasks;
import br.com.miller.farmaciaatendente.domain.Departament;
import br.com.miller.farmaciaatendente.domain.Offer;

public class DepartamentManagerPresenter implements DepartamentManagerTasks.View, DepartamentManagerTasks.Model {

    private DepartamentManagerTasks.Presenter presenter;
    private DepartamentManagerModel departamentManagerModel;

    public DepartamentManagerPresenter(DepartamentManagerTasks.Presenter presenter) {
        this.presenter = presenter;
        departamentManagerModel = new DepartamentManagerModel(this);
    }

    @Override
    public void onDepartamentDataSuccess(Departament departament) { presenter.onDepartamentDataSuccess(departament); }

    @Override
    public void onDepartamentDataFailed() { presenter.onDepartamentDataFailed(); }

    @Override
    public void onMedicineDeleteSucces(Offer offer) { presenter.onMedicineDeleteSucces(offer);}

    @Override
    public void onMedicineDeleteFailed() { presenter.onMedicineDeleteFailed(); }

    @Override
    public void onDespublishSuccess(Offer offer) { presenter.onDepublishSuccess(offer);}

    @Override
    public void onDespublishFailed() { presenter.onDepublishFailed(); }

    @Override
    public void onMedicinePublishFailed() { presenter.onMedicinePublishFailed();}

    @Override
    public void onMedicinePublishSucces(Offer offer) { presenter.onMedicinePublishSucces(offer);}

    @Override
    public void getDepartament(String departamentId, String storeId, String city) {departamentManagerModel.getDepartament(departamentId, storeId, city);}

    @Override
    public void deleteOffer(Offer offer) { departamentManagerModel.deleteOffer(offer); }

    @Override
    public void dePublishOffer(Offer offer) { departamentManagerModel.depublishOffer(offer); }

    @Override
    public void publishOffer(Offer offer) { departamentManagerModel.publishOffer(offer); }
}
