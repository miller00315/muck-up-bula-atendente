package br.com.miller.farmaciaatendente.saleManager.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.domain.Buy;
import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.mainMenu.adapters.recyclersAdapters.RecyclerAdapterSolicitations;
import br.com.miller.farmaciaatendente.saleManager.presenters.ReceivedSalesPresenter;
import br.com.miller.farmaciaatendente.saleManager.tasks.ReceivedSalesTask;

public class ReceivedSales extends Fragment implements ReceivedSalesTask.Presenter, RecyclerAdapterSolicitations.OnAdapterInteract {

    private OnFragmentInteractionListener mListener;
    private ReceivedSalesPresenter presenter;
    private RecyclerAdapterSolicitations recyclerAdapterSolicitations;
    private User user;
    private RecyclerView recyclerViewReceivedSales;
    private ScrollView mainLayout;
    private RelativeLayout loadingLayout;

    public ReceivedSales() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getParcelable("user");
        }

        presenter = new ReceivedSalesPresenter(this);
        recyclerAdapterSolicitations = new RecyclerAdapterSolicitations(this, getContext());
        presenter.getSolicitations(user);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_received_sales, container, false);

        recyclerViewReceivedSales = view.findViewById(R.id.recycler_view_received_sales);

        loadingLayout = view.findViewById(R.id.layout_loading);

        mainLayout = view.findViewById(R.id.main_layout);

        showLoading();

        bindViews();

        return view;
    }

    private void bindViews(){

        if(recyclerAdapterSolicitations != null){

            if(recyclerAdapterSolicitations.getItemCount() > 0){
                hideLoading();
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewReceivedSales.setLayoutManager(linearLayoutManager);
        recyclerViewReceivedSales.setHasFixedSize(true);
        recyclerViewReceivedSales.setAdapter(recyclerAdapterSolicitations);
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) { mListener = (OnFragmentInteractionListener) context; }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        presenter.onDestroy(user.getStoreId(), user.getCity());
    }

    @Override
    public void onBuysDataSuccess(ArrayList<Buy> buys) {

        hideLoading();

        if(this.isVisible()){

            recyclerViewReceivedSales.setVisibility(View.VISIBLE);
            recyclerAdapterSolicitations.setBuys(buys);
        }
    }

    @Override
    public void onBuysDataFailed() {
        hideLoading();
        recyclerAdapterSolicitations.clear();
        recyclerViewReceivedSales.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onNoStore() {
        hideLoading();
        recyclerViewReceivedSales.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAdapterInteract(Bundle bundle) { mListener.onFragmentInteraction(bundle); }

    public interface OnFragmentInteractionListener { void onFragmentInteraction(Bundle bundle); }
}
