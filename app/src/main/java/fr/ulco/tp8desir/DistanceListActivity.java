package fr.ulco.tp8desir;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class DistanceListActivity extends LongLatActivity {

    private Double xDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_distance_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LongLat longLat = getLongLat();
        Intent intent = getIntentInstance();
        xDistance = intent.getDoubleExtra(SearchActivity.DISTANCE_INTENT, 0.0);

        if(longLat == null) return;

        loadImages(longLat);
    }


    @Override
    public void loadImages(LongLat longLatPictureParent){

        ArrayList<Uri> images = getImages(longLatPictureParent);
        GridView gridView = findViewById(R.id.maGrille);
        TextView tvCount = findViewById(R.id.textView3);

        ArrayList<ImageItem> maListeDeDonnees = new ArrayList<>();
        for(Uri contentUri : images){
            LongLat longLatImages =  GeoManager.readLocation(this, contentUri);
            if(longLatImages == null) continue;
            double distanceOrthodromique = GeoManager.distanceOrthodromique(
                    longLatPictureParent.getLatitude(), longLatPictureParent.getLongitude(), longLatImages.getLatitude(), longLatImages.getLongitude());

            if(distanceOrthodromique > xDistance) continue;
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