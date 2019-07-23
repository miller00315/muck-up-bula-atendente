package br.com.miller.farmaciaatendente.departamentManager.tasks;
import br.com.miller.farmaciaatendente.domain.Departament;
import br.com.miller.farmaciaatendente.domain.Offer;

public interface DepartamentManagerTasks {

    interface Presenter{

        void onDepartamentDataSuccess(Departament departament);
        void onDepartamentDataFailed();
        void onMedicineDeleteSucces(Offer offer);
        void onMedicineDeleteFailed();
        void onDepublishSuccess(Offer offer);
        void onDepublishFailed();
        void onMedicinePublishFailed();
        void onMedicinePublishSucces(Offer offer);
    }

    interface Model{
        void onDepartamentDataSuccess(Departament departament);
        void onDepartamentDataFailed();
        void onMedicineDeleteSucces(Offer offer);
        void onMedicineDeleteFailed();
        void onDespublishSuccess(Offer offer);
        void onDespublishFailed();
        void onMedicinePublishFailed();
        void onMedicinePublishSucces(Offer offer);
    }

    interface View{
        void getDepartament(final String departamentId, String storeId, String city);
        void deleteOffer(Offer offer);
        void dePublishOffer(Offer offer);
        void publishOffer(Offer offer);
    }
}
