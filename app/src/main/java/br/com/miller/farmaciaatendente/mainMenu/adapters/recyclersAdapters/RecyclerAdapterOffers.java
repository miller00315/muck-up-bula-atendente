package br.com.miller.farmaciaatendente.mainMenu.adapters.recyclersAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.domain.Offer;
import br.com.miller.farmaciaatendente.mainMenu.views.viewHolders.OffersViewHolder;
import br.com.miller.farmaciaatendente.superClass.RecyclerItem;
import br.com.miller.farmaciaatendente.utils.StringUtils;
import br.com.miller.farmaciaatendente.utils.tasks.FirebaseImageTask;

public class RecyclerAdapterOffers extends RecyclerItem {

    private ArrayList<Offer> offers;
    private Context context;

    public RecyclerAdapterOffers(RecyclerItem.OnAdapterInteract onAdapterInteract, Context context){

        listener = onAdapterInteract;
        this.context = context;
        offers = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OffersViewHolder(LayoutInflater.from(context).inflate(R.layout.view_holder_offer, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if(viewHolder instanceof  OffersViewHolder){

            final OffersViewHolder offersViewHolder = (OffersViewHolder) viewHolder;

            offersViewHolder.getDescriptionOffer().setText(offers.get(i).getDescription());
            offersViewHolder.getTitleOffer().setText(offers.get(i).getTitle());
            offersViewHolder.getValueOffer().setText(StringUtils.doubleToMonetaryString(offers.get(i).getValue()));
            offersViewHolder.getValueSendOffer().setText(StringUtils.doubleToMonetaryString(offers.get(i).getSendValue()));
            offersViewHolder.getOfferLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showItem(offersViewHolder.getAdapterPosition());
                }
            });

            offersViewHolder.downloadImage("offers", offers.get(i).getCity(), offers.get(i).getImage());

        }

    }

    @Override
    public int getItemCount() {
        return offers != null ? offers.size() : 0;
    }

    @Override
    public void showItem(int i) {

        Bundle bundle = new Bundle();

        bundle.putInt("type", 6);
        bundle.putInt("adapterPosition", i);
        bundle.putString("offerName", offers.get(i).getTitle());
        bundle.putString("offerId", offers.get(i).getId());
        bundle.putString("storeId", offers.get(i).getStoreId());
        bundle.putString("departamentId", offers.get(i).getDepartamentId());
        bundle.putString("city", offers.get(i).getCity());

        listener.onAdapterInteract(bundle);

    }

    public Offer getOffer(int position){
        return  offers.get(position);
    }

    public ArrayList<Offer> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<Offer> offers) {
        this.offers = offers;
        notifyDataSetChanged();
    }

}
