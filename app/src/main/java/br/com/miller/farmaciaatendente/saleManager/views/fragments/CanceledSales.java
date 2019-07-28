package br.com.miller.farmaciaatendente.saleManager.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import br.com.miller.farmaciaatendente.saleManager.presenters.CanceledSalesPresenter;
import br.com.miller.farmaciaatendente.saleManager.tasks.CanceledSaleTask;
import br.com.miller.farmaciaatendente.superClass.RecyclerItem;

public class CanceledSales extends Fragment implements CanceledSaleTask.Presenter, RecyclerItem.OnAdapterInteract {

    private OnFragmentInteractionListener mListener;
    private RecyclerAdapterSolicitations recyclerAdapterSolicitations;
    private RecyclerView recyclerViewCanceledSales;
    private CanceledSalesPresenter canceledSalesPresenter;
    private User user;
    private ScrollView mainLayout;
    private RelativeLayout loadingLayout;

    public CanceledSales() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getParcelable("user");
        }

        recyclerAdapterSolicitations = new RecyclerAdapterSolicitations(this, getContext());

        canceledSalesPresenter = new CanceledSalesPresenter(this);

        canceledSalesPresenter.getSolicitations(user);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_caneceled_sales, container, false);

        recyclerViewCanceledSales = view.findViewById(R.id.recycler_view_canceled_sales);

        loadingLayout = view.findViewById(R.id.layout_loading);

        mainLayout = view.findViewById(R.id.main_layout);

        showLoading();

        bindViews();

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

    private void bindViews(){

        if(recyclerAdapterSolicitations != null){

            if(recyclerAdapterSolicitations.getItemCount() > 0){
                hideLoading();
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerViewCanceledSales.setLayoutManager(linearLayoutManager);

        recyclerViewCanceledSales.setHasFixedSize(true);

        recyclerViewCanceledSales.setAdapter(recyclerAdapterSolicitations);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        canceledSalesPresenter.onDestroy(user.getStoreId(), user.getCity());
    }

    @Override
    public void onBuysDataSuccess(ArrayList<Buy> buys) {

        hideLoading();

        if(this.isVisible()){

            recyclerViewCanceledSales.setVisibility(View.VISIBLE);
            recyclerAdapterSolicitations.setBuys(buys);
        }
    }

    @Override
    public void onBuysDataFailed() {
        hideLoading();
        recyclerAdapterSolicitations.clear();
        recyclerViewCanceledSales.setVisibility(View.INVISIBLE);}

    @Override
    public void onNoStore() {
        hideLoading();
        recyclerViewCanceledSales.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAdapterInteract(Bundle bundle) { mListener.onFragmentInteraction(bundle);}

    public interface OnFragmentInteractionListener { void onFragmentInteraction(Bundle bundle); }
}
