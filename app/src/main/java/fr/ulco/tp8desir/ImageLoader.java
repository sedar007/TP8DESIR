package fr.ulco.tp8desir;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ImageLoader extends AppCompatActivity {

    protected Bitmap initialBitmap;
    protected TextView getLongTextView(){
        return findViewById(R.id.longitude_editText);
    }
    protected TextView getInconnuTextView(){
        return findViewById(R.id.inconnu_label);
    }
    protected void setTextViewVisible(TextView tv, boolean visible) {
        // affiche ou masque la TextView
        if (tv == null) return;
        tv.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    protected void setTableLayoutVisible( boolean visible) {
        setTextViewVisible(getLongTextView(), visible);
        setTextViewVisible(getLatTextView(), visible);
        setTextViewVisible(findViewById(R.id.longitude_label), visible);
        setTextViewVisible(findViewById(R.id.latitude_label), visible);
    }
    protected void setInconnuVisibility(boolean visible){
        setTextViewVisible(getInconnuTextView(), visible);
    }

    protected TextView getLatTextView(){
        return findViewById(R.id.latitude_editText);
    }
    protected ImageView getImageView(){
        return findViewById(R.id.imageView);
    }
    protected void showLongLat(final float[] latLong){
        if (latLong == null || latLong.length < 2) {
            return;
        }
        String latitude = String.valueOf(latLong[1]);
        String longitude = String.valueOf(latLong[0]);
        getLongTextView().setText(longitude);
        getLatTextView().setText(latitude);
        setTableLayoutVisible(true);
        setInconnuVisibility(false);
    }



    protected void loadImage(final Uri imageUri){
        // ----- préparer les options de chargement de l’image
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inMutable = true; // l’image pourra ^etre modifi´ee
        // ------ chargement de l’image - valeur retourn´ee null en cas d’erreur
        try {
            Bitmap bm = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri), null, option);
            getImageView().setImageBitmap(bm);
            initialBitmap = bm;
        }
        catch (Exception e){
            // Affiche un message d'erreur au user
            Toast.makeText(this, getString(R.string.error_upload_image), Toast.LENGTH_SHORT).show();
        }
    }

    // Méthode pour vérifier si une image est chargée dans l'ImageView et afficher un message d'erreur si ce n'est pas le cas
    protected Boolean isImageLoaded(final BitmapDrawable bitmapDrawable) {
        if(bitmapDrawable == null) {
            Toast.makeText(this, getString(R.string.error_no_image), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}