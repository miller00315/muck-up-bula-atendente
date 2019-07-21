package br.com.miller.farmaciaatendente.mainMenu.adapters.recyclersAdapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.domain.Departament;
import br.com.miller.farmaciaatendente.domain.Offer;
import br.com.miller.farmaciaatendente.mainMenu.views.viewHolders.DepartamentViewHolder;
import br.com.miller.farmaciaatendente.superClass.RecyclerItem;

public class RecyclerAdapterDepartament extends RecyclerItem {

    private Context context;
    private RecyclerItem.OnAdapterInteract onAdapterInteract;
    private ArrayList<Departament> departaments;

    public RecyclerAdapterDepartament(RecyclerItem.OnAdapterInteract onAdapterInteract,Context context) {
        this.context = context;
        this.onAdapterInteract = onAdapterInteract;
    }

    @Override
    public void showItem(int i) {

        Bundle bundle = new Bundle();
        onAdapterInteract.onAdapterInteract(bundle);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DepartamentViewHolder(LayoutInflater.from(context).inflate(R.layout.view_holder_departament, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder,  int i) {

        if(viewHolder instanceof  DepartamentViewHolder){

            final DepartamentViewHolder departamentViewHolder = (DepartamentViewHolder) viewHolder;

            RecyclerAdapterOffers recyclerAdapterOffers = new RecyclerAdapterOffers(this.onAdapterInteract, this.context);

            recyclerAdapterOffers.setOffers(departaments.get(i).getOffers() != null ? departaments.get(i).getOffers() : new ArrayList<Offer>());

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

            departamentViewHolder.getDepartamentRecyclerView().setAdapter(recyclerAdapterOffers);
            departamentViewHolder.getDepartamentRecyclerView().setLayoutManager(linearLayoutManager);
            departamentViewHolder.getDepartamentRecyclerView().setHasFixedSize(true);

            departamentViewHolder.getDepartamentTitle().setText(departaments.get(i).getTitle());

            departamentViewHolder.getDepartamentMenu().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showItem(departamentViewHolder.getAdapterPosition());
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return departaments != null ? departaments.size() : 0;
    }

    public ArrayList<Departament> getDepartaments() {
        return departaments;
    }

    public void setDepartaments(ArrayList<Departament> departaments) {
        this.departaments = departaments;
        notifyDataSetChanged();
    }
}
