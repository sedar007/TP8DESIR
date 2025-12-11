package fr.ulco.tp8desir;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ImageOnlyAdapter extends ArrayAdapter<ImageItem> {
    public ImageOnlyAdapter(Context context, ArrayList<ImageItem> images) {
        super(context, 0, images);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Création de la vue si elle n'existe pas
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_image, parent, false);
        }


        ImageItem item = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.img_item);
        TextView textView = convertView.findViewById(R.id.tv_name);

        if (item != null) {
            imageView.setImageURI(item.getUri());
            textView.setText(item.getLabel());
        }

        return convertView;
    }
}