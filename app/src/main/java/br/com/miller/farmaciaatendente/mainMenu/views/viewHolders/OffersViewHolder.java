package br.com.miller.farmaciaatendente.mainMenu.views.viewHolders;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.utils.FirebaseImageUtils;
import br.com.miller.farmaciaatendente.utils.tasks.FirebaseImageTask;

public class OffersViewHolder extends RecyclerView.ViewHolder implements FirebaseImageTask.Model {

    private TextView titleOffer, descriptionOffer, valueOffer, valueSendOffer;
    private ImageView imageOffer;
    private RelativeLayout offerLayout;
    private FirebaseImageUtils firebaseImageUtils;

    public OffersViewHolder(@NonNull View itemView) {
        super(itemView);

        firebaseImageUtils = new FirebaseImageUtils(this);

        titleOffer = itemView.findViewById(R.id.title_offer);
        descriptionOffer = itemView.findViewById(R.id.description_offer);
        valueOffer = itemView.findViewById(R.id.value_offer);
        valueSendOffer = itemView.findViewById(R.id.value_send_offer);
        imageOffer = itemView.findViewById(R.id.image_offer);
        offerLayout = itemView.findViewById(R.id.offersLayout);
    }

    public TextView getTitleOffer() {
        return titleOffer;
    }



    public TextView getDescriptionOffer() {
        return descriptionOffer;
    }

    public TextView getValueOffer() {
        return valueOffer;
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

    public RelativeLayout getOfferLayout() {
        return offerLayout;
    }

    public void setOfferLayout(RelativeLayout offerLayout) {
        this.offerLayout = offerLayout;
    }

    @Override
    public void uploadImage(String type, String city, String image, Bitmap bitmap) { }

    @Override
    public void downloadImage(String type, String city, String image) { firebaseImageUtils.downloadImage(type, city, image.concat(".jpg")); }

    @Override
    public void onImageUploadSuccess(Bitmap bitmap) { }

    @Override
    public void onImageUploadFailed() { }

    @Override
    public void onImageDownloadSuccess(Bitmap bitmap) { imageOffer.setImageBitmap(bitmap);}

    @Override
    public void onImageDownloadFailed() { }
}
