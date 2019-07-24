package br.com.miller.farmaciaatendente.utils.alerts;

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

public class AlertOptionsMedicineManager extends DialogFragment{

    private static final String DIALOG_TAG = AlertOptionsMedicineManager.class.getSimpleName();

    public interface AlertOptionsResult{ void onAlertOptionResult(Bundle bundle, int type);}

    private AlertOptionsResult alertOptionsResult;

    public static AlertOptionsMedicineManager newInstance(Bundle bundle) {

        AlertOptionsMedicineManager dialog = new AlertOptionsMedicineManager();
        dialog.setArguments(bundle);
        return dialog;
    }

    public void setListener(AlertOptionsResult alertOptionsResult){ this.alertOptionsResult = alertOptionsResult; }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){


        View view = inflater.inflate(R.layout.layout_alert_medicine_manager, container);

        Button fechar = view.findViewById(R.id.close);
        Button justSave = view.findViewById(R.id.just_save);
        Button savePublish = view.findViewById(R.id.save_publish);
        TextView text = view.findViewById(R.id.textAlert);

        if(getArguments() != null)
            text.setText("O que você deseja fazer com o produto");


        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });

        justSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertOptionsResult.onAlertOptionResult(getArguments(), 1);
                dismiss();
            }
        });

        savePublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertOptionsResult.onAlertOptionResult(getArguments(), 2);
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
