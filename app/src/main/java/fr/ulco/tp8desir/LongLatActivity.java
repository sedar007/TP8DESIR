package fr.ulco.tp8desir;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public abstract class LongLatActivity extends AppCompatActivity {

    private Intent intent;

    protected abstract void loadImages(LongLat longLatPictureParent);
    protected Intent getIntentInstance(){
        return intent;
    }

    protected LongLat getLongLat(){
        try {
            intent = getIntent();
            Double longitude = intent.getDoubleExtra(MainActivity.LONGITUDE_INTENT, 0.0);
            Double latitude = intent.getDoubleExtra(MainActivity.LATITUDE_INTENT, 0.0);
            if(longitude == null || latitude == null){
                Toast.makeText(this, "Longitude ou latitude manquante", Toast.LENGTH_LONG).show();
                return null;
            }
            return new LongLat(longitude, latitude);
        }
        catch (Exception e){
            Toast.makeText(this, "Erreur lors de la récupération des coordonnées", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    protected ArrayList<Uri> getImages(LongLat longLatPictureParent) {
        if(longLatPictureParent.getLongitude() == null || longLatPictureParent.getLatitude() == null){
            Toast.makeText(this, "Longitude ou latitude manquante", Toast.LENGTH_LONG).show();
            return null;
        }

        return ImageManager.loadImagesFromGallery(this);
    }

}
