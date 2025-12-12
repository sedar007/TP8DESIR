package fr.ulco.tp8desir;

import static fr.ulco.tp8desir.MainActivity.LATITUDE_INTENT;
import static fr.ulco.tp8desir.MainActivity.LONGITUDE_INTENT;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;

public class SearchActivity extends LocationAppActivity {
    public final static String DISTANCE_INTENT = "com.example.fichedesir.DISTANCE_INTENT" ;



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

        Intent intent = new Intent(this, DistanceListActivity.class);
        intent.putExtra(LONGITUDE_INTENT, longitude);
        intent.putExtra(LATITUDE_INTENT, latitude);
        intent.putExtra(DISTANCE_INTENT, xDistance);

        startActivity(intent);
    }


}