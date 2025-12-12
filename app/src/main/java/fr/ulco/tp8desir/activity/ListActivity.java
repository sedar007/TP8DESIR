package fr.ulco.tp8desir.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import fr.ulco.tp8desir.IListManager;
import fr.ulco.tp8desir.model.ImageItem;
import fr.ulco.tp8desir.ImageOnlyAdapter;
import fr.ulco.tp8desir.R;

/**
 * Activité qui affiche une liste d'images dans une grille.
 */
public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        ArrayList<ImageItem> list = intent.getParcelableArrayListExtra(IListManager.PICTURE_LIST);

        loadImages(list);

    }


    public void loadImages(ArrayList<ImageItem> list){
        GridView gridView = findViewById(R.id.maGrille);
        TextView tvCount = findViewById(R.id.textView3);

        ImageOnlyAdapter adapter = new ImageOnlyAdapter(this, list);
        gridView.setAdapter(adapter);
        tvCount.setText("Nombre d'images : " + list.size());

    }
}