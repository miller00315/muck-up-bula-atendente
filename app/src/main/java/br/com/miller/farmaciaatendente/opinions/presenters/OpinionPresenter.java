package br.com.miller.farmaciaatendente.opinions.presenters;

import br.com.miller.farmaciaatendente.opinions.models.OpinionModel;
import br.com.miller.farmaciaatendente.opinions.tasks.OpinionTasks;

public class OpinionPresenter implements OpinionTasks.View, OpinionTasks.Model{

    private OpinionTasks.Presenter presenter;
    private OpinionModel opinionModel;

    public OpinionPresenter(OpinionTasks.Presenter presenter) {
        this.presenter = presenter;
        opinionModel = new OpinionModel(this);
    }
}
