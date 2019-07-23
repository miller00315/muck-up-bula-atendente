package br.com.miller.farmaciaatendente.utils;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import br.com.miller.farmaciaatendente.R;

public class AlertOptions extends DialogFragment{

    private static final
    String DIALOG_TAG = "editDialog";

    public interface AlertOptionsResult{ void onAlertOptionResult(Bundle bundle, int type);}

    private AlertOptionsResult alertOptionsResult;

    public static AlertOptions newInstance(Bundle bundle) {


        AlertOptions dialog = new AlertOptions();
        dialog.setArguments(bundle);
        return dialog;
    }

    public void setListener(AlertOptionsResult alertOptionsResult){ this.alertOptionsResult = alertOptionsResult; }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){


        View view = inflater.inflate(R.layout.layout_alert_decision, container);

        Button apagar = view.findViewById(R.id.apagar);
        Button editar = view.findViewById(R.id.editar);
        Button fechar = view.findViewById(R.id.fechar);
        Button publish = view.findViewById(R.id.save_publish);
        Button depublish = view.findViewById(R.id.depublish);
        TextView text = view.findViewById(R.id.textAlert);

        if(getArguments() != null)
            text.setText("O que você deseja fazer com o produto ". concat(getArguments().getString("offerName", "")));

        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertOptionsResult.onAlertOptionResult(getArguments(), 1);
                dismiss();
            }
        });

        apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertOptionsResult.onAlertOptionResult(getArguments(), 2);
                dismiss();
            }
        });

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertOptionsResult.onAlertOptionResult(getArguments(), 3);
                dismiss();
            }
        });

        depublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertOptionsResult.onAlertOptionResult(getArguments(), 4);
                dismiss();
            }
        });

        return view;
    }

    public void openDialog(FragmentManager fm) {
        if (fm.findFragmentByTag(DIALOG_TAG) == null) {
            show(fm, DIALOG_TAG);
        }
    }
}
