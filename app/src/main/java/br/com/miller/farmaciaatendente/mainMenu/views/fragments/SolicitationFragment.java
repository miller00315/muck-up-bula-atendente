package br.com.miller.farmaciaatendente.mainMenu.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.domain.Buy;
import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.mainMenu.adapters.recyclersAdapters.RecyclerAdapterSolicitations;
import br.com.miller.farmaciaatendente.mainMenu.presenters.SolicitationPresenter;
import br.com.miller.farmaciaatendente.mainMenu.tasks.SolicitationTasks;
import br.com.miller.farmaciaatendente.superClass.RecyclerItem;

public class SolicitationFragment extends Fragment implements SolicitationTasks.Presenter,
        RecyclerItem.OnAdapterInteract {

    private OnFragmentInteractionListener mListener;
    private SolicitationPresenter solicitationPresenter;
    private RecyclerAdapterSolicitations recyclerAdapterSolicitations;
    private Button managerBuy;
    private RecyclerView recyclerView;
    private User user;
    private RelativeLayout loadingLayout, mainLayout, emptyLayout;
    private Boolean dataBaseChecked = false;

    public SolicitationFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            user = getArguments().getParcelable("user");

        }

        solicitationPresenter = new SolicitationPresenter(this);
        recyclerAdapterSolicitations = new RecyclerAdapterSolicitations(this, getContext());

        solicitationPresenter.getSolicitations(user);


    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_solicitation, container, false);

        recyclerView = view.findViewById(R.id.recycler_solicitation_fragment);
        managerBuy = view.findViewById(R.id.manager_buy_button);
        loadingLayout = view.findViewById(R.id.layout_loading);
        mainLayout = view.findViewById(R.id.main_layout);
        emptyLayout = view.findViewById(R.id.layout_empty);

        showLoading();

        bindViews();

        return view;
    }

    private void showLoading(){
        loadingLayout.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);
        emptyLayout.setVisibility(View.INVISIBLE);
    }

    private void hideLoading(){

        if(recyclerAdapterSolicitations.getItemCount() > 0 ) {
            loadingLayout.setVisibility(View.INVISIBLE);
            mainLayout.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.INVISIBLE);

            Log.w("teste", "teste");
        }else{
            loadingLayout.setVisibility(View.INVISIBLE);
            mainLayout.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.VISIBLE);

            Log.w("teste", "lol");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if(loadingLayout.getVisibility() == View.VISIBLE && dataBaseChecked)
            hideLoading();
        else
            solicitationPresenter.temporaryVerify(user);

    }

    private void bindViews(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(recyclerAdapterSolicitations);

        managerBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putInt("type", 1);

                mListener.onFragmentInteraction(bundle);

            }
        });

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
        solicitationPresenter.onDestroy(user.getStoreId(), user.getCity());
    }

    @Override
    public void onAdapterInteract(Bundle bundle) { mListener.onFragmentInteraction(bundle); }

    @Override
    public void onBuysDataSuccess(ArrayList<Buy> buys) {
        dataBaseChecked = true;
        recyclerView.setVisibility(View.VISIBLE);
        recyclerAdapterSolicitations.setBuys(buys);

        hideLoading();

    }

    @Override
    public void onBuysDataFailed() {
        dataBaseChecked = true;
        recyclerView.setVisibility(View.INVISIBLE);
        hideLoading();
    }

    @Override
    public void onNoStore() {
        Toast.makeText(getContext(), "Você não possui loja vinculada", Toast.LENGTH_SHORT).show();
        dataBaseChecked = true;
        recyclerView.setVisibility(View.INVISIBLE);
        hideLoading();
    }

    @Override
    public void onSaleAdded(Buy buy) {
        dataBaseChecked = true;
        recyclerAdapterSolicitations.addBuy(buy);
        hideLoading();
    }

    @Override
    public void onSaleUpdate(Buy buy) {
        dataBaseChecked = true;
        recyclerAdapterSolicitations.updateBuy(buy);
        hideLoading();
    }

    @Override
    public void onSalesRemoved(Buy buy) {
        dataBaseChecked = true;
        recyclerAdapterSolicitations.removeBuy(buy);
        hideLoading();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Bundle bundle);
    }
}
