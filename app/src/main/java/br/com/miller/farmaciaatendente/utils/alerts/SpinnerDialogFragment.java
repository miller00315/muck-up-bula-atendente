package br.com.miller.farmaciaatendente.utils.alerts;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.domain.Departament;
import br.com.miller.farmaciaatendente.mainMenu.adapters.arrayAdapters.SpinnnerArrayAdapterDepartament;

public class SpinnerDialogFragment extends DialogFragment {

    private static final String DIALOG_TAG = SpinnerDialogFragment.class.getSimpleName();

    private SpinnerDialogFragmentListener spinnerDialogFragmentListener;

    private SpinnnerArrayAdapterDepartament spinnnerArrayAdapterDepartament;

    public static SpinnerDialogFragment newInstance(Bundle bundle){

        SpinnerDialogFragment spinnerDialogFragment = new SpinnerDialogFragment();
        spinnerDialogFragment.setArguments(bundle);

        return spinnerDialogFragment;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.layout_alert_departament, container, false);

        final Spinner spinner = view.findViewById(R.id.spinner_departament);

        Button confirm = view.findViewById(R.id.confirm);

        Button cancel = view.findViewById(R.id.cancel);

        spinner.setAdapter(this.spinnnerArrayAdapterDepartament);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                spinner.getSelectedItem();

                bundle.putParcelable("departament", (Departament) spinner.getSelectedItem());

                spinnerDialogFragmentListener.onSpinnerDialogFragment(bundle);

                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    public void setListener(SpinnerDialogFragmentListener spinnerDialogFragmentListener,
                            Context context, ArrayList<Departament> departaments) {

        this.spinnerDialogFragmentListener = spinnerDialogFragmentListener;
        spinnnerArrayAdapterDepartament = new SpinnnerArrayAdapterDepartament(context, departaments);
    }

    public interface SpinnerDialogFragmentListener { void onSpinnerDialogFragment(Bundle bundle);}

    public void openDialog(FragmentManager fm) {
        if (fm.findFragmentByTag(DIALOG_TAG) == null) {
            show(fm, DIALOG_TAG);
        }
    }
}
