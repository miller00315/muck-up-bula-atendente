package br.com.miller.farmaciaatendente.saleManager.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        bindViews();

        return view;
    }

    private void bindViews(){

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

        if(this.isVisible()){

            if(recyclerAdapterSolicitations.getItemCount() > 0) recyclerAdapterSolicitations.clear();

            recyclerViewCanceledSales.setVisibility(View.VISIBLE);
            recyclerAdapterSolicitations.setBuys(buys);
        }
    }

    @Override
    public void onBuysDataFailed() {
        recyclerAdapterSolicitations.clear();
        recyclerViewCanceledSales.setVisibility(View.INVISIBLE);}

    @Override
    public void onNoStore() { recyclerViewCanceledSales.setVisibility(View.INVISIBLE);}

    @Override
    public void onAdapterInteract(Bundle bundle) { mListener.onFragmentInteraction(bundle);}

    public interface OnFragmentInteractionListener { void onFragmentInteraction(Bundle bundle); }
}
