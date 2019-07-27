package br.com.miller.farmaciaatendente.utils.presenters;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.domain.Evaluate;
import br.com.miller.farmaciaatendente.utils.models.OpinionDialogFragmentModel;
import br.com.miller.farmaciaatendente.utils.tasks.OpnionDialogFragmentTasks;

public class OpinionDialogFramentPresenter implements OpnionDialogFragmentTasks.Model, OpnionDialogFragmentTasks.View {

    private OpnionDialogFragmentTasks.Presenter presenter;
    private OpinionDialogFragmentModel model;

    public OpinionDialogFramentPresenter(OpnionDialogFragmentTasks.Presenter presenter) {
        this.presenter = presenter;
        model = new OpinionDialogFragmentModel(this);
    }

    @Override
    public void onGetEvaluateSuccess(ArrayList<Evaluate> evaluates) { presenter.onGetEvaluateSuccess(evaluates); }

    @Override
    public void onGetEvaluateFailed() { presenter.onGetEvaluateFailed(); }

    @Override
    public void getEvaluate(String idStore, String city) { model.getOpinions(idStore, city); }
}
