package fr.ulco.tp8desir.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import fr.ulco.tp8desir.DistanceManager;
import fr.ulco.tp8desir.IListManager;
import fr.ulco.tp8desir.model.ImageItem;
import fr.ulco.tp8desir.R;

public class SearchActivity extends LocationAppActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void onClickGoToMainActivity(View view){
        finish();
    }

    public void onClickSearchByDistance(View view){
        EditText editTextDistance = findViewById(R.id.editText);
        Double xDistance = null;

        try {
            xDistance = Double.valueOf(editTextDistance.getText().toString());
        } catch (NumberFormatException e) {
            editTextDistance.setError("Veuillez entrer un nombre valide");
            return;
        }
        Location location = getLocation();
        Double longitude = location.getLongitude();
        Double latitude = location.getLatitude();

        Intent intent = new Intent(this, ListActivity.class);

        DistanceManager distanceManager = new DistanceManager(longitude, latitude, xDistance, this);
        ArrayList<ImageItem> images = distanceManager.getImagesList();
        intent.putParcelableArrayListExtra(IListManager.PICTURE_LIST, images);
        startActivity(intent);
    }


}