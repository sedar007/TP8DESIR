package fr.ulco.tp8desir;

import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

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
    }


    public void onImageUpload(View view){
        mGetResult.launch(LAUNCH_IMAGE_INPUT);
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

}