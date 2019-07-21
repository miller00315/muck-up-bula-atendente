package br.com.miller.farmaciaatendente.mainMenu.adapters.recyclersAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.domain.Offer;
import br.com.miller.farmaciaatendente.mainMenu.views.viewHolders.OffersViewHolder;
import br.com.miller.farmaciaatendente.superClass.RecyclerItem;
import br.com.miller.farmaciaatendente.utils.StringUtils;
import br.com.miller.farmaciaatendente.utils.tasks.FirebaseImageTask;

public class RecyclerAdapterOffers extends RecyclerItem  implements FirebaseImageTask.Model {

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

            OffersViewHolder offersViewHolder = (OffersViewHolder) viewHolder;

            offersViewHolder.getDescriptionOffer().setText(offers.get(i).getDescription());
            offersViewHolder.getTitleOffer().setText(offers.get(i).getTitle());
            offersViewHolder.getValueOffer().setText(StringUtils.doubleToMonetaryString(offers.get(i).getValue()));
            offersViewHolder.getValueSendOffer().setText(StringUtils.doubleToMonetaryString(offers.get(i).getSendValue()));

        }

    }

    @Override
    public int getItemCount() {
        return offers != null ? offers.size() : 0;
    }

    @Override
    public void showItem(int i) {

    }

    public ArrayList<Offer> getOffers() {
        return offers;
    }

    protected void setOffers(ArrayList<Offer> offers) {
        this.offers = offers;
        notifyDataSetChanged();
    }

    @Override
    public void uploadImage(String type, String city, String image, Bitmap bitmap) {

    }

    @Override
    public void downloadImage(String type, String city, String image) {

    }

    @Override
    public void onImageUploadSuccess(Bitmap bitmap) {

    }

    @Override
    public void onImageUploadFailed() {

    }

    @Override
    public void onImageDownloadSuccess(Bitmap bitmap) {

    }

    @Override
    public void onImageDownloadFailed() {

    }
}
