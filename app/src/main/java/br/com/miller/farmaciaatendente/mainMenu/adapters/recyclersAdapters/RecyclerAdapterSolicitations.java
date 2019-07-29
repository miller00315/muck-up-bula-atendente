package br.com.miller.farmaciaatendente.mainMenu.adapters.recyclersAdapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.domain.Buy;
import br.com.miller.farmaciaatendente.mainMenu.views.viewHolders.SolicitationViewHolder;
import br.com.miller.farmaciaatendente.superClass.RecyclerItem;
import br.com.miller.farmaciaatendente.utils.StringUtils;

public class RecyclerAdapterSolicitations extends RecyclerItem {

    private ArrayList<Buy> buys;
    private Context context;
    private RecyclerItem.OnAdapterInteract onAdapterInteract;

    public RecyclerAdapterSolicitations(OnAdapterInteract onAdapterInteract, Context context) {
        this.onAdapterInteract = onAdapterInteract;
        buys = new ArrayList<>();
        this.context = context;
    }

    @Override
    public void showItem(int i) {

        Bundle bundle = new Bundle();

        bundle.putInt("type", 4);
        bundle.putString("buyId", buys.get(i).getId());
        bundle.putString("status", buys.get(i).getStatus());
        bundle.putString("userId", buys.get(i).getUserId());
        bundle.putString("city", buys.get(i).getStoreCity());
        bundle.putString("storeId", String.valueOf(buys.get(i).getStoreId()));

        onAdapterInteract.onAdapterInteract(bundle);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SolicitationViewHolder(LayoutInflater.from(context).inflate(R.layout.view_holder_solicitation, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if(viewHolder instanceof  SolicitationViewHolder){

            final SolicitationViewHolder solicitationViewHolder = (SolicitationViewHolder) viewHolder;

            solicitationViewHolder.getSolicitationDate().setText(StringUtils.formatDate(buys.get(i).getSolicitationDate()));
            solicitationViewHolder.getSolicitationId().setText("Compra de ".concat(buys.get(i).getUserName().concat(", para o endereço ").concat(buys.get(i).getAddress())));
            solicitationViewHolder.getSolicitationSend().setText(buys.get(i).getDeliverDate() != null ? StringUtils.formatDate(buys.get(i).getDeliverDate()) : "n/a");
            solicitationViewHolder.getSolicitationReceive().setText( buys.get(i).getReceiverDate() != null ? StringUtils.formatDate( buys.get(i).getReceiverDate())  : "n/a");
            solicitationViewHolder.getSolicitationLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showItem(solicitationViewHolder.getAdapterPosition());
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return buys != null ? buys.size() : 0;
    }

    public ArrayList<Buy> getBuys() {
        return buys;
    }

    public void clear(){
        buys.clear();
        notifyDataSetChanged();
    }

    public void removeBuy(Buy buy){

           for (int i = 0; i < buys.size(); i++) {

              if (buys.get(i).getId().equals(buy.getId())) {
                  buys.remove(i);
                  notifyItemRemoved(i);
                  break;
              }
           }



    }

    public void updateBuy(Buy buy){

        for (int i = 0; i < buys.size(); i++) {

            if (buys.get(i).getId().equals(buy.getId())) {
                buys.add(i, buy);
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void addBuy(Buy buy){
        buys.add(buy);

        notifyDataSetChanged();
    }

    public void setBuys(ArrayList<Buy> buys) {


        this.buys = buys;
        notifyDataSetChanged();
    }
}
