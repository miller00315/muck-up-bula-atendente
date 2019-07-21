package br.com.miller.farmaciaatendente.mainMenu.views.viewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.miller.farmaciaatendente.R;

public class DepartamentViewHolder extends RecyclerView.ViewHolder {

    private RecyclerView departamentRecyclerView;
    private ImageView departamentMenu;
    private TextView departamentTitle;

    public DepartamentViewHolder(@NonNull View itemView) {
        super(itemView);

        departamentMenu = itemView.findViewById(R.id.departament_menu);
        departamentRecyclerView = itemView.findViewById(R.id.departament_recycler_view);
        departamentTitle = itemView.findViewById(R.id.departament_title);

    }

    public RecyclerView getDepartamentRecyclerView() {
        return departamentRecyclerView;
    }

    public void setDepartamentRecyclerView(RecyclerView departamentRecyclerView) {
        this.departamentRecyclerView = departamentRecyclerView;
    }

    public ImageView getDepartamentMenu() {
        return departamentMenu;
    }

    public void setDepartamentMenu(ImageView departamentMenu) {
        this.departamentMenu = departamentMenu;
    }

    public TextView getDepartamentTitle() {
        return departamentTitle;
    }

    public void setDepartamentTitle(TextView departamentTitle) {
        this.departamentTitle = departamentTitle;
    }
}
