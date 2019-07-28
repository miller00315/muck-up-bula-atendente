package br.com.miller.farmaciaatendente.utils.alerts;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.domain.Evaluate;
import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.superClass.RecyclerItem;
import br.com.miller.farmaciaatendente.utils.adapters.OpinionStoreRecyclerAdapter;
import br.com.miller.farmaciaatendente.utils.presenters.OpinionDialogFramentPresenter;
import br.com.miller.farmaciaatendente.utils.tasks.OpnionDialogFragmentTasks;

public class OpinionsDialogFragment extends DialogFragment implements OpnionDialogFragmentTasks.Presenter,
        RecyclerItem.OnAdapterInteract {

    private OpinionDialogFramentPresenter opinionDialogFramentPresenter;

    private static final String DIALOG_TAG = OpinionsDialogFragment.class.getName();

    private OpinionStoreRecyclerAdapter opinionStoreRecyclerAdapter;

    private RelativeLayout mainLayout, loadingLayout;

    public static OpinionsDialogFragment newInstance(Bundle bundle){

        OpinionsDialogFragment opinionsDialogFragment = new OpinionsDialogFragment();
        opinionsDialogFragment.setArguments(bundle);
        return opinionsDialogFragment;
    }

    public void setItems(Context context){

        opinionStoreRecyclerAdapter = OpinionStoreRecyclerAdapter.newInstance();
        opinionStoreRecyclerAdapter.configure(this, context);

        if(getArguments() != null) {

            User user = getArguments().getParcelable("user");

            opinionDialogFramentPresenter = new OpinionDialogFramentPresenter(this);

            if(user != null)
                opinionDialogFramentPresenter.getEvaluate(user.getStoreId(), user.getCity());
            else
                dismiss();
        }else{
            dismiss();
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.layout_alert_opinios, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_opinion);

        mainLayout = view.findViewById(R.id.main_layout);

        loadingLayout = view.findViewById(R.id.layout_loading);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(opinionStoreRecyclerAdapter);

        Button button = view.findViewById(R.id.close);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        showLoading();

        return view;

    }

    private void showLoading(){
        loadingLayout.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);
    }

    private void hideLoading(){
        loadingLayout.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void onGetEvaluateSuccess(ArrayList<Evaluate> evaluates) {
        hideLoading();
        opinionStoreRecyclerAdapter.setEvaluates(evaluates); }

    @Override
    public void onGetEvaluateFailed() {
        hideLoading();
        dismiss();
    }

    public void openDialog(FragmentManager fm) {
        if (fm.findFragmentByTag(DIALOG_TAG) == null) {
            show(fm, DIALOG_TAG);
        }
    }

    @Override
    public void onAdapterInteract(Bundle bundle) {

    }
}
