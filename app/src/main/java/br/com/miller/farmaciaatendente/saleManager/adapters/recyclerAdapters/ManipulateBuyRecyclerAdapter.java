package br.com.miller.farmaciaatendente.saleManager.adapters.recyclerAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.domain.Offer;
import br.com.miller.farmaciaatendente.saleManager.views.viewHolders.ManipulateBuyViewHolder;
import br.com.miller.farmaciaatendente.superClass.RecyclerItem;
import br.com.miller.farmaciaatendente.utils.StringUtils;

public class ManipulateBuyRecyclerAdapter extends RecyclerItem {

    private ArrayList<Offer> offers;
    private Context context;

    public ManipulateBuyRecyclerAdapter(RecyclerItem.OnAdapterInteract onAdapterInteract, Context context) {
        this.context = context;
        listener = onAdapterInteract;
    }

    @Override
    public void showItem(int i) {

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ManipulateBuyViewHolder(LayoutInflater.from(context).inflate(R.layout.view_holder_manipulate_buy_offer, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if(viewHolder instanceof ManipulateBuyViewHolder){

            ManipulateBuyViewHolder manipulateBuyViewHolder = (ManipulateBuyViewHolder) viewHolder;

            manipulateBuyViewHolder.getOffer_price().setText("Preço: ".concat(StringUtils.doubleToMonetaryString(offers.get(i).getValue())));
            manipulateBuyViewHolder.getOffer_quantity().setText("Quantidade: ".concat(String.valueOf(offers.get(i).getQuantity())));
            manipulateBuyViewHolder.getOffer_title().setText(offers.get(i).getTitle());
        }

    }

    @Override
    public int getItemCount() {
        return offers != null ? offers.size() : 0;
    }

    public ArrayList<Offer> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<Offer> offers) {
        this.offers = offers;
        notifyDataSetChanged();
    }
}
