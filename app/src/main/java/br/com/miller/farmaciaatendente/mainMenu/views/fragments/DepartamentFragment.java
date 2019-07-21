package br.com.miller.farmaciaatendente.mainMenu.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.domain.Departament;
import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.mainMenu.adapters.recyclersAdapters.RecyclerAdapterDepartament;
import br.com.miller.farmaciaatendente.mainMenu.presenters.DepartamentPresenter;
import br.com.miller.farmaciaatendente.mainMenu.tasks.DepartamentTask;
import br.com.miller.farmaciaatendente.superClass.RecyclerItem;


public class DepartamentFragment extends Fragment implements DepartamentTask.Presenter, RecyclerItem.OnAdapterInteract {
    private OnFragmentInteractionListener mListener;
    private DepartamentPresenter departamentPresenter;
    private User user;
    private RecyclerAdapterDepartament recyclerAdapterDepartament;
    private RecyclerView recyclerViewDepatament;

    public DepartamentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getParcelable("user");
        }

        recyclerAdapterDepartament = new RecyclerAdapterDepartament(this, getContext());
        departamentPresenter = new DepartamentPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_departament, container, false);

        recyclerViewDepatament = view.findViewById(R.id.recycler_view_departament);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerViewDepatament.setLayoutManager(linearLayoutManager);

        recyclerViewDepatament.setAdapter(recyclerAdapterDepartament);

        recyclerViewDepatament.setHasFixedSize(true);

        if(!user.getStoreId().isEmpty())
            departamentPresenter.getDepartaments(user.getCity(), user.getStoreId());
        else
            recyclerViewDepatament.setVisibility(View.INVISIBLE);

        return view;
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
    }

    @Override
    public void onAdapterInteract(Bundle bundle) {
        mListener.onFragmentInteraction(bundle);
    }

    @Override
    public void onDepartamentsSuccess(ArrayList<Departament> departaments) {

        recyclerViewDepatament.setVisibility(View.VISIBLE);
        recyclerAdapterDepartament.setDepartaments(departaments);

    }

    @Override
    public void onDepartamentsFailed() {
        recyclerViewDepatament.setVisibility(View.INVISIBLE);
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Bundle bundle);
    }
}
