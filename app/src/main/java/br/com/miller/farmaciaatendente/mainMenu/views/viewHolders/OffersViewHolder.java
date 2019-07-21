package br.com.miller.farmaciaatendente.mainMenu.views.viewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.miller.farmaciaatendente.R;

public class OffersViewHolder extends RecyclerView.ViewHolder {

    private TextView titleOffer, descriptionOffer, valueOffer, valueSendOffer;
    private ImageView imageOffer;

    public OffersViewHolder(@NonNull View itemView) {
        super(itemView);

        titleOffer = itemView.findViewById(R.id.title_offer);
        descriptionOffer = itemView.findViewById(R.id.description_offer);
        valueOffer = itemView.findViewById(R.id.value_offer);
        valueSendOffer = itemView.findViewById(R.id.value_send_offer);
        imageOffer = itemView.findViewById(R.id.image_offer);
    }

    public TextView getTitleOffer() {
        return titleOffer;
    }

    public void setTitleOffer(TextView titleOffer) {
        this.titleOffer = titleOffer;
    }

    public TextView getDescriptionOffer() {
        return descriptionOffer;
    }

    public void setDescriptionOffer(TextView descriptionOffer) {
        this.descriptionOffer = descriptionOffer;
    }

    public TextView getValueOffer() {
        return valueOffer;
    }

    public void setValueOffer(TextView valueOffer) {
        this.valueOffer = valueOffer;
    }

    public TextView getValueSendOffer() {
        return valueSendOffer;
    }

    public void setValueSendOffer(TextView valueSendOffer) {
        this.valueSendOffer = valueSendOffer;
    }

    public ImageView getImageOffer() {
        return imageOffer;
    }

    public void setImageOffer(ImageView imageOffer) {
        this.imageOffer = imageOffer;
    }
}
