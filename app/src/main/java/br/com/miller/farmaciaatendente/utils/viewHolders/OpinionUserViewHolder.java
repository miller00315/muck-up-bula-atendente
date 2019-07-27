package br.com.miller.farmaciaatendente.utils.viewHolders;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.utils.images.FirebaseImageUtils;
import br.com.miller.farmaciaatendente.utils.tasks.FirebaseImageTask;

public class OpinionUserViewHolder extends RecyclerView.ViewHolder implements FirebaseImageTask.Model {

    private TextView messageUser, nameUser, classificatioStore;
    private ImageView imageUser;
    private RelativeLayout layoutStore;

    private FirebaseImageUtils firebaseImageUtils;

    public OpinionUserViewHolder(@NonNull View itemView) {
        super(itemView);

        firebaseImageUtils = new FirebaseImageUtils(this);

        messageUser = itemView.findViewById(R.id.message_user);
        nameUser = itemView.findViewById(R.id.name_user);
        imageUser = itemView.findViewById(R.id.image_user);
        layoutStore = itemView.findViewById(R.id.layout_opinion);
        classificatioStore = itemView.findViewById(R.id.classification_store);
    }

    public TextView getMessageUser() {
        return messageUser;
    }

    public TextView getNameUser() {
        return nameUser;
    }

    public TextView getClassificatioStore() {
        return classificatioStore;
    }

    public ImageView getImageUser() {
        return imageUser;
    }

    public RelativeLayout getLayoutStore() {
        return layoutStore;
    }

    public FirebaseImageUtils getFirebaseImageUtils() {
        return firebaseImageUtils;
    }

    @Override
    public void uploadImage(String type, String city, String image, Bitmap bitmap) { }

    @Override
    public void downloadImage(String type, String city, String image) { firebaseImageUtils.downloadImage(type, city, image.concat(".jpg"));}

    @Override
    public void onImageUploadSuccess(Bitmap bitmap) { }

    @Override
    public void onImageUploadFailed() { }

    @Override
    public void onImageDownloadSuccess(Bitmap bitmap) { imageUser.setImageBitmap(bitmap);}

    @Override
    public void onImageDownloadFailed() { }
}
