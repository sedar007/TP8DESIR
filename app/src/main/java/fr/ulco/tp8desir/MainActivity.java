package fr.ulco.tp8desir;

import android.content.Intent;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends ImageLoader {
    private final String LAUNCH_IMAGE_INPUT = "image/*";
    private final static String GEO_URI = "geo:";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTableLayoutVisible(false);
        setInconnuVisibility(false);
        ImageButton imageButton = findViewById(R.id.imageGeo);
        imageButton.setVisibility(View.GONE);



    }


    public void onImageUpload(View view){
        mGetResult.launch(LAUNCH_IMAGE_INPUT);
    }



    public void onGetGeo(View view){

        TextView latitudeView = getLatTextView();
        String latitudeString = latitudeView.getText().toString();

        TextView longitudeView = getLongTextView();
        String longitudeString = longitudeView.getText().toString();

        // Vérification que les coordonnées sont valides
        if(!isValidCoordinates(latitudeString, longitudeString))
            return;
        startActivity(GEO_URI + latitudeString + "," + longitudeString, Intent.ACTION_VIEW);
    }


    private final ActivityResultLauncher<String> mGetResult = registerForActivityResult(
            // classe de contrat pour une intention implicite
            new ActivityResultContracts.GetContent(),
            // callback pour une intention implicite
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    if (uri == null) return;
                    loadImage(uri);
                    readLocation(uri);

                }// onActivityResult
            }// ActivityResultCallback
    );// registerForActivityResult

    private void readLocation(Uri uri) {
        if (uri == null) return;
        try (InputStream in = getContentResolver().openInputStream(uri)) {
            if (in == null) {
                return;
            }
            ExifInterface exif = new ExifInterface(in);
            float[] latLong = new float[2];
            if (exif.getLatLong(latLong)) {
                showLongLat(latLong);
            } else {
                setInconnuVisibility(true);
                setTableLayoutVisible(false);
            }
        } catch (IOException e) {
        }
    }
    private void startActivity(final String uriString, final String intentAction){
        Intent intent = new Intent();
        Uri uri = Uri.parse(uriString);
        intent.setData(uri);
        intent.setAction(intentAction);

        startActivity_(intent);
    }
    private void startActivity_(Intent intent){
        try{
            startActivity(intent);
        } catch (Exception e){
            Toast.makeText(this, getString(R.string.no_app_available_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        finish();
    }

    private Boolean isValidCoordinates(String latitude, String longitude){

        try {
            double lat = Double.parseDouble(latitude);
            double lon = Double.parseDouble(longitude);

            if(lat < -90 || lat > 90){
                Toast.makeText(this, getString(R.string.invalid_latitude_toast), Toast.LENGTH_SHORT).show();
                return false;
            }

            if(lon < -180 || lon > 180){
                Toast.makeText(this, getString(R.string.invalid_longitude_toast), Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.invalid_coordinates_toast), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}