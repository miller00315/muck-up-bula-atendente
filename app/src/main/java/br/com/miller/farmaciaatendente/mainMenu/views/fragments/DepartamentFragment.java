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
import android.widget.Spinner;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.domain.Departament;
import br.com.miller.farmaciaatendente.domain.User;
import br.com.miller.farmaciaatendente.mainMenu.adapters.arrayAdapters.SpinnnerArrayAdapterDepartament;
import br.com.miller.farmaciaatendente.mainMenu.adapters.recyclersAdapters.RecyclerAdapterDepartament;
import br.com.miller.farmaciaatendente.mainMenu.presenters.DepartamentPresenter;
import br.com.miller.farmaciaatendente.mainMenu.tasks.DepartamentTask;
import br.com.miller.farmaciaatendente.superClass.RecyclerItem;
import br.com.miller.farmaciaatendente.utils.AlertDialogUtils;
import br.com.miller.farmaciaatendente.utils.tasks.AlertDialogUtilsTask;


public class DepartamentFragment extends Fragment implements DepartamentTask.Presenter, RecyclerItem.OnAdapterInteract, AlertDialogUtilsTask.Presenter {

    private OnFragmentInteractionListener mListener;
    private DepartamentPresenter departamentPresenter;
    private User user;
    private AlertDialogUtils alertDialogUtils;
    private RecyclerAdapterDepartament recyclerAdapterDepartament;
    private RecyclerView recyclerViewDepatament;
    private FloatingActionButton floatingActionButton;

    public DepartamentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getParcelable("user");
        }

        alertDialogUtils = new AlertDialogUtils(this);
        alertDialogUtils.setContext(getContext());
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

        bindViews();

        return view;
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

        if(!user.getStoreId().isEmpty())
            departamentPresenter.getDepartaments(user.getCity(), user.getStoreId());
        else {
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

    }

    @Override
    public void onDepartamentsFailed() { recyclerViewDepatament.setVisibility(View.INVISIBLE); }

    @Override
    public void onDepartamentsAvaliablesuccess(ArrayList<Departament> departaments) {

        SpinnnerArrayAdapterDepartament spinnnerArrayAdapterDepartament = new SpinnnerArrayAdapterDepartament(getContext(), departaments);

        ViewGroup viewGroup = Objects.requireNonNull(getActivity()).findViewById(android.R.id.content);

        View view = getLayoutInflater().inflate(R.layout.alert_dialog_new_departament, viewGroup, false);

        Spinner spinner = view.findViewById(R.id.spinner_departament);

        spinner.setAdapter(spinnnerArrayAdapterDepartament);

        alertDialogUtils.createAlertDialogSpinner(view, 1);
    }

    @Override
    public void onDepartmentAddFailed() { Toast.makeText(getContext(), "Este departamento não pode ser adicionado, tentenovamente", Toast.LENGTH_SHORT).show();}

    @Override
    public void onAlertPositive(Object o, int type) {

        if(type == 1) if(o instanceof Departament) departamentPresenter.addDepartment((Departament) o, recyclerAdapterDepartament.getDepartaments(), user.getStoreId());

    }

    @Override
    public void onALertNegative() { }

    public interface OnFragmentInteractionListener { void onFragmentInteraction(Bundle bundle);}
}
