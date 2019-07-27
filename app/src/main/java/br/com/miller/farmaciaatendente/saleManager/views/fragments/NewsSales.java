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
import br.com.miller.farmaciaatendente.saleManager.presenters.NewsSalesPresenter;
import br.com.miller.farmaciaatendente.saleManager.tasks.NewsSalesTasks;

public class NewsSales extends Fragment implements NewsSalesTasks.Presenter, RecyclerAdapterSolicitations.OnAdapterInteract {


    private OnFragmentInteractionListener mListener;
    private NewsSalesPresenter presenter;
    private RecyclerAdapterSolicitations recyclerAdapterSolicitations;
    private User user;
    private RecyclerView recyclerViewNewsSales;

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

        bindViews();

        return view;
    }

    private void bindViews(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerViewNewsSales.setLayoutManager(linearLayoutManager);

        recyclerViewNewsSales.setHasFixedSize(true);

        recyclerViewNewsSales.setAdapter(recyclerAdapterSolicitations);
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

        if(this.isVisible()){

        //    if(recyclerAdapterSolicitations.getItemCount() > 0) recyclerAdapterSolicitations.clear();
            recyclerViewNewsSales.setVisibility(View.VISIBLE);
            recyclerAdapterSolicitations.setBuys(buys);
        }

    }

    @Override
    public void onBuysDataFailed() {

        recyclerAdapterSolicitations.clear();
        recyclerViewNewsSales.setVisibility(View.INVISIBLE);}

    @Override
    public void onNoStore() { recyclerViewNewsSales.setVisibility(View.INVISIBLE);}

    @Override
    public void onAdapterInteract(Bundle bundle) { mListener.onFragmentInteraction(bundle); }

    public interface OnFragmentInteractionListener {void onFragmentInteraction(Bundle bundle);}
}
