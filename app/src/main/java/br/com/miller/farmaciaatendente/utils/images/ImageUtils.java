package br.com.miller.farmaciaatendente.utils.images;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import br.com.miller.farmaciaatendente.R;

public class ImageUtils {

    public static Bitmap getImageFormImageView(ImageView image){

        Bitmap bitmap;

        if (image.getDrawable() instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        } else {
            Drawable d = image.getDrawable();
            bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            d.draw(canvas);
        }

        return bitmap;

    }

    public static byte[] convertBitmapToByteArray(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();

    }

    public static Bitmap convertByteArraytoBitmap(byte[] bytes){

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }



    public static void getImageFromMemory(Intent data, ImageView imageView){

        Uri uri;

        if(data != null){

            Bundle bundle = data.getExtras();

            if(bundle != null){

                uri = (Uri) bundle.get(Intent.EXTRA_STREAM);

            }else{

                uri = data.getData();
            }

            if(uri != null){

                Picasso.get().load(uri).error(R.drawable.ic_launcher_foreground).into(imageView);

            }
        }
    }


}

