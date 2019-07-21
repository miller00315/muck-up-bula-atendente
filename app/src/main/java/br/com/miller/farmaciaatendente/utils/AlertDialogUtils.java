package br.com.miller.farmaciaatendente.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.utils.tasks.AlertDialogUtilsTask;

public class AlertDialogUtils {

    private AlertDialogUtilsTask.Presenter presenter;
    private Context context;

    public AlertDialogUtils(AlertDialogUtilsTask.Presenter presenter) {
        this.presenter = presenter;
    }

    public void setContext(Context context){
        this.context = context;
    }

    public void creatAlertImageView(final View view, final int type){

        AlertDialog alertDialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setView(view);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                presenter.onALertNegative();

            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ImageView imageView = view.findViewById(R.id.image_memory);
                presenter.onAlertPositive(ImageUtils.getImageUser(imageView), type);
            }
        });

        alertDialog = builder.create();

        alertDialog.show();
    }
}
