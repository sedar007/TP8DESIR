package fr.ulco.tp8desir;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ImageListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_image_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.images_textView2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        LongLat longLat = getLongLat();
        if(longLat == null) return;
        loadImages(longLat);
    }

    private LongLat getLongLat(){
        try {
            Intent intent = getIntent();
            String longitude = intent.getStringExtra(MainActivity.LONGITUDE_INTENT);
            String latitude = intent.getStringExtra(MainActivity.LATITUDE_INTENT);
            if(longitude == null || latitude == null){
                Toast.makeText(this, "Longitude ou latitude manquante", Toast.LENGTH_LONG).show();
                return null;
            }
            return new LongLat(Float.valueOf(longitude), Float.valueOf(latitude));
        }
        catch (Exception e){
            Toast.makeText(this, "Erreur lors de la récupération des coordonnées", Toast.LENGTH_LONG).show();
            return null;
        }


    }

    private void loadImages(LongLat longLatPictureParent) {
        if(longLatPictureParent.getLongitude() == null || longLatPictureParent.getLatitude() == null){
            Toast.makeText(this, "Longitude ou latitude manquante", Toast.LENGTH_LONG).show();
            return;
        }

        ArrayList<Uri> images = ImageManager.loadImagesFromGallery(this);
        GridView gridView = findViewById(R.id.maGrille);
        TextView tvCount = findViewById(R.id.textView3);


        ArrayList<ImageItem> maListeDeDonnees = new ArrayList<>();
        for(Uri contentUri : images){
            LongLat longLatImages =  GeoManager.readLocation(this, contentUri);
            if(longLatImages == null) continue;
            double distanceOrthodromique = GeoManager.distanceOrthodromique(
                    longLatPictureParent.getLatitude(), longLatPictureParent.getLongitude(), longLatImages.getLatitude(), longLatImages.getLongitude());
            String txt = "Distance : " + String.format("%.2f", distanceOrthodromique) + " km";
            maListeDeDonnees.add(new ImageItem(contentUri, txt));

        }


        ImageOnlyAdapter adapter = new ImageOnlyAdapter(this, maListeDeDonnees);
        gridView.setAdapter(adapter);
        tvCount.setText("Nombre d'images : " + maListeDeDonnees.size());
    }

    public void onClickGoToMainActivity(View view){
        finish();
    }


}