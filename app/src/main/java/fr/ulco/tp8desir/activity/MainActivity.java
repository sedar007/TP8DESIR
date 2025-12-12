package fr.ulco.tp8desir.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import fr.ulco.tp8desir.GeoManager;
import fr.ulco.tp8desir.IListManager;
import fr.ulco.tp8desir.model.ImageItem;
import fr.ulco.tp8desir.ImageListManager;
import fr.ulco.tp8desir.ImageManager;
import fr.ulco.tp8desir.model.LongLat;
import fr.ulco.tp8desir.R;


public class MainActivity extends AppPermissions {
    private final String LAUNCH_IMAGE_INPUT = "image/*";
    public final static String LONGITUDE_INTENT = "com.example.fichedesir.LONGITUDE" ;
    public final static String LATITUDE_INTENT = "com.example.fichedesir.LATITUDE" ;
    private final static String GEO_URI = "geo:";
    private Double longitude;
    private Double latitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.images_textView2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String[] permissions = {
                Manifest.permission.ACCESS_MEDIA_LOCATION,
                Manifest.permission.READ_MEDIA_IMAGES,
        };
        requestPermissions(permissions);

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


    public void onImageButtonShowList(View view){
        Intent intent = new Intent(this, ListActivity.class);

        if (longitude == null && latitude == null) {
          return;
        }

        ImageListManager imageListManager = new ImageListManager(longitude, latitude, this);
        ArrayList<ImageItem> images = imageListManager.getImagesList();
        intent.putParcelableArrayListExtra(IListManager.PICTURE_LIST, images);
        startActivity(intent);
    }

    public void onGetGeo(View view){
        // Vérification que les coordonnées sont valides
        if(!GeoManager.isValidCoordinates(this, latitude, longitude))
            return;

        Intent intent = new Intent();
        intent.putExtra(LONGITUDE_INTENT, longitude);
        intent.putExtra(LATITUDE_INTENT, latitude);
        Uri uri = Uri.parse(GEO_URI + latitude + "," + longitude);
        intent.setData(uri);
        intent.setAction(Intent.ACTION_VIEW);

        try{
            startActivity(intent);
        } catch (Exception e){
            Toast.makeText(this, getString(R.string.no_app_available_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        finish();
    }

    public void onDistanceImageList(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);

    }


    private final ActivityResultLauncher<String> mGetResult = registerForActivityResult(
            // classe de contrat pour une intention implicite
            new ActivityResultContracts.GetContent(),
            // callback pour une intention implicite
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    if (uri == null) return;
                    ImageView imageView = getImageView();
                    ImageManager.loadImage(MainActivity.this, uri, imageView);
                    LongLat latLong = GeoManager.readLocation(MainActivity.this, uri);
                    if(latLong == null){
                        setInconnuVisibility(true);
                        setTableLayoutVisible(false);
                    }
                    else
                        showLongLat(latLong);
                }// onActivityResult
            }// ActivityResultCallback
    );// registerForActivityResult


    private TextView getLongTextView(){
        return findViewById(R.id.longitude_editText);
    }
    private TextView getInconnuTextView(){
        return findViewById(R.id.inconnu_label);
    }
    private void setTextViewVisible(TextView tv, boolean visible) {
        // affiche ou masque la TextView
        if (tv == null) return;
        tv.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void setInconnuVisibility(boolean visible){
        setTextViewVisible(getInconnuTextView(), visible);
    }
    private TextView getLatTextView(){
        return findViewById(R.id.latitude_editText);
    }
    protected ImageView getImageView(){
        return findViewById(R.id.imageView);
    }


    private void setTableLayoutVisible( boolean visible) {
        setTextViewVisible(getLongTextView(), visible);
        setTextViewVisible(getLatTextView(), visible);
        setTextViewVisible(findViewById(R.id.longitude_label), visible);
        setTextViewVisible(findViewById(R.id.latitude_label), visible);
        ImageButton imageButton = findViewById(R.id.imageGeo);
        ImageButton imageButtonShowList = findViewById(R.id.imageButtonShowList);

        if(visible){
            imageButton.setVisibility(View.VISIBLE);
            imageButtonShowList.setVisibility(View.VISIBLE);
        }
        else{
            imageButton.setVisibility(View.GONE);
            imageButtonShowList.setVisibility(View.GONE);

        }
    }

    private void showLongLat(final LongLat latLong){
        if (latLong == null) {
            return;
        }
        latitude = latLong.getLatitude();
        longitude = latLong.getLongitude();
        getLongTextView().setText(String.valueOf(longitude));
        getLatTextView().setText(String.valueOf(latitude));
        setTableLayoutVisible(true);
        setInconnuVisibility(false);
    }


}