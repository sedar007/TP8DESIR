package fr.ulco.tp8desir;

import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ImageManager {
    public static void loadImage(Activity activity, final Uri imageUri, ImageView imageView) {
        // ----- préparer les options de chargement de l'image
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inMutable = true; // l'image pourra etre modifiee
        // ------ chargement de l'image - valeur retourn´ee null en cas d’erreur
        try {
            Bitmap bm = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(imageUri), null, option);
            imageView.setImageBitmap(bm);
        }
        catch (Exception e){
            // Affiche un message d'erreur au user
            Toast.makeText(activity, activity.getString(R.string.error_upload_image), Toast.LENGTH_SHORT).show();
        }
    }
    public static ArrayList<Uri> loadImagesFromGallery(Activity activity) {
        ArrayList<Uri> imagesList = new ArrayList<>();

        String[] projection = new String[] {
                MediaStore.Images.Media._ID
        };

        Uri imagesUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        Cursor cur = activity.getContentResolver().query(
                imagesUri,
                projection,
                null,
                null,
                MediaStore.Images.Media.DATE_TAKEN + " DESC"
        );

        if (cur != null) {
            if (cur.moveToFirst()) {
                int idColumn = cur.getColumnIndexOrThrow(MediaStore.Images.Media._ID);

                do {
                    long id = cur.getLong(idColumn);

                    Uri contentUri = ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

                    imagesList.add(contentUri);

                } while (cur.moveToNext());
            }
            cur.close();

        }
        return imagesList;
    }

}
