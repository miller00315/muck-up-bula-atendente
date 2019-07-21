package br.com.miller.farmaciaatendente.saleManager.views.viewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.miller.farmaciaatendente.R;

public class ManipulateBuyViewHolder extends RecyclerView.ViewHolder {

    private TextView offer_title, offer_quantity, offer_price;

    public ManipulateBuyViewHolder(@NonNull View itemView) {
        super(itemView);

        offer_title = itemView.findViewById(R.id.offer_title);
        offer_quantity = itemView.findViewById(R.id.offer_quantity);
        offer_price = itemView.findViewById(R.id.offer_price);
    }

    public TextView getOffer_title() {
        return offer_title;
    }

    public void setOffer_title(TextView offer_title) {
        this.offer_title = offer_title;
    }

    public TextView getOffer_quantity() {
        return offer_quantity;
    }

    public void setOffer_quantity(TextView offer_quantity) {
        this.offer_quantity = offer_quantity;
    }

    public TextView getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(TextView offer_price) {
        this.offer_price = offer_price;
    }
}
