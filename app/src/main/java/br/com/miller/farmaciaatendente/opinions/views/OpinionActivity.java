package br.com.miller.farmaciaatendente.opinions.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.opinions.presenters.OpinionPresenter;
import br.com.miller.farmaciaatendente.opinions.tasks.OpinionTasks;

public class OpinionActivity extends AppCompatActivity implements OpinionTasks.Presenter {

    private OpinionPresenter opinionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);

        opinionPresenter = new OpinionPresenter(this);
    }
}
