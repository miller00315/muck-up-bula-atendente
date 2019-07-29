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
import br.com.miller.farmaciaatendente.saleManager.presenters.NewsSalesPresenter;
import br.com.miller.farmaciaatendente.saleManager.tasks.NewsSalesTasks;

public class NewsSales extends Fragment implements NewsSalesTasks.Presenter,
        RecyclerAdapterSolicitations.OnAdapterInteract {


    private OnFragmentInteractionListener mListener;
    private NewsSalesPresenter presenter;
    private RecyclerAdapterSolicitations recyclerAdapterSolicitations;
    private User user;
    private RecyclerView recyclerViewNewsSales;
    private ScrollView mainLayout;
    private RelativeLayout loadingLayout;
    private Boolean dataBaseChecked = false;

    public NewsSales() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getParcelable("user");
        }

        presenter = new NewsSalesPresenter(this);
        recyclerAdapterSolicitations = new RecyclerAdapterSolicitations(this, getContext());
        presenter.getSolicitations(user);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_sales, container, false);

        recyclerViewNewsSales = view.findViewById(R.id.recycler_view_news_sales);

        loadingLayout = view.findViewById(R.id.layout_loading);

        mainLayout = view.findViewById(R.id.main_layout);

        showLoading();

        bindViews();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if(loadingLayout.getVisibility() == View.VISIBLE && dataBaseChecked)
            hideLoading();
        else
            presenter.temporaryVerify(user.getStoreId(), user.getCity());

    }

    private void bindViews(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerViewNewsSales.setLayoutManager(linearLayoutManager);

        recyclerViewNewsSales.setHasFixedSize(true);

        recyclerViewNewsSales.setAdapter(recyclerAdapterSolicitations);
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
        presenter.onDestroy(user.getStoreId(), user.getCity());
    }

    @Override
    public void onBuysDataSuccess(ArrayList<Buy> buys) {

        dataBaseChecked = true;
        hideLoading();

        if(this.isVisible()){

            recyclerViewNewsSales.setVisibility(View.VISIBLE);
            recyclerAdapterSolicitations.setBuys(buys);
        }

    }

    @Override
    public void onBuysDataFailed() {

        dataBaseChecked = true;
        hideLoading();
        recyclerAdapterSolicitations.clear();
        recyclerViewNewsSales.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onNoStore() {
        dataBaseChecked = true;
        hideLoading();
        recyclerViewNewsSales.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSaleAdded(Buy buy) {

        dataBaseChecked = true;
        hideLoading();
        recyclerAdapterSolicitations.addBuy(buy);
    }

    @Override
    public void onSaleUpdate(Buy buy) {
        dataBaseChecked = true;
        hideLoading();
        recyclerAdapterSolicitations.updateBuy(buy);
    }

    @Override
    public void onSalesRemoved(Buy buy) {
        dataBaseChecked = true;
        hideLoading();
        recyclerAdapterSolicitations.removeBuy(buy);
    }

    @Override
    public void onAdapterInteract(Bundle bundle) { mListener.onFragmentInteraction(bundle); }

    public interface OnFragmentInteractionListener {void onFragmentInteraction(Bundle bundle);}
}
