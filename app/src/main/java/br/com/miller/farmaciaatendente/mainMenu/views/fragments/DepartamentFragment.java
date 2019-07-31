package br.com.miller.farmaciaatendente.mainMenu.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.domain.Departament;
import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.mainMenu.adapters.recyclersAdapters.RecyclerAdapterDepartament;
import br.com.miller.farmaciaatendente.mainMenu.presenters.DepartamentPresenter;
import br.com.miller.farmaciaatendente.mainMenu.tasks.DepartamentTask;
import br.com.miller.farmaciaatendente.superClass.RecyclerItem;
import br.com.miller.farmaciaatendente.utils.alerts.SpinnerDialogFragment;


public class DepartamentFragment extends Fragment implements DepartamentTask.Presenter,
        RecyclerItem.OnAdapterInteract,
        SpinnerDialogFragment.SpinnerDialogFragmentListener {

    private OnFragmentInteractionListener mListener;
    private DepartamentPresenter departamentPresenter;
    private User user;
    private RecyclerAdapterDepartament recyclerAdapterDepartament;
    private RecyclerView recyclerViewDepatament;
    private FloatingActionButton floatingActionButton;
    private RelativeLayout loadingLayout, mainLayout;

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
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_departament, container, false);

        floatingActionButton = view.findViewById(R.id.floating_action_button);

        recyclerViewDepatament = view.findViewById(R.id.recycler_view_departament);

        floatingActionButton = view.findViewById(R.id.floating_action_button);

        loadingLayout = view.findViewById(R.id.layout_loading);
        mainLayout = view.findViewById(R.id.main_layout);

        bindViews();

        return view;
    }

    private void showLoading(){
        if(this.isVisible()) {
            loadingLayout.setVisibility(View.VISIBLE);
            mainLayout.setVisibility(View.INVISIBLE);
        }
    }

    private void hideLoading(){
        if(this.isVisible()) {
            loadingLayout.setVisibility(View.INVISIBLE);
            mainLayout.setVisibility(View.VISIBLE);
        }
    }

    private void bindViews(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerViewDepatament.setLayoutManager(linearLayoutManager);

        recyclerViewDepatament.setAdapter(recyclerAdapterDepartament);

        recyclerViewDepatament.setHasFixedSize(true);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!user.getStoreId().isEmpty())
                    departamentPresenter.getDepartamentsAvaliables(user.getCity());
                else
                    Toast.makeText(getContext(), "Você não possui lojas vinculadas", Toast.LENGTH_SHORT).show();

            }
        });

        getDepartaments();
    }

    public void getDepartaments(){

        showLoading();

        if(!user.getStoreId().isEmpty())
            departamentPresenter.getDepartaments(user.getCity(), user.getStoreId());
        else {
            hideLoading();
            recyclerViewDepatament.setVisibility(View.INVISIBLE);
        }

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
    public void onAdapterInteract(Bundle bundle) { mListener.onFragmentInteraction(bundle); }

    @Override
    public void onDepartamentsSuccess(ArrayList<Departament> departments) {

        recyclerViewDepatament.setVisibility(View.VISIBLE);
        recyclerAdapterDepartament.setDepartaments(departments);
        hideLoading();

    }

    @Override
    public void onDepartamentsFailed() {
        hideLoading();
        recyclerViewDepatament.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDepartamentsAvaliableSuccess(ArrayList<Departament> departaments) {

        Bundle bundle = new Bundle();

        SpinnerDialogFragment spinnerDialogFragment = SpinnerDialogFragment.newInstance(bundle);

        spinnerDialogFragment.setListener(this, getContext(), departaments);

        spinnerDialogFragment.openDialog(getFragmentManager());

    }

    @Override
    public void onDepartamentAvailablesFailed() { Toast.makeText(getContext(), "Não encontramos departamentos disponíveis", Toast.LENGTH_SHORT).show();}

    @Override
    public void onDepartmentAddFailed() {
        Toast.makeText(getContext(), "Este departamento não pode ser adicionado, tente novamente", Toast.LENGTH_SHORT).show();}

    @Override
    public void onSpinnerDialogFragment(Bundle bundle) {

        if(bundle.containsKey("departament")){

            showLoading();

            departamentPresenter.addDepartment((Departament) bundle.getParcelable("departament"),recyclerAdapterDepartament.getDepartaments(), user.getStoreId());
        }

    }

    public interface OnFragmentInteractionListener { void onFragmentInteraction(Bundle bundle);}
}
