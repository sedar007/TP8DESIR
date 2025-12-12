package fr.ulco.tp8desir;

import android.app.Activity;
import android.net.Uri;

import java.util.ArrayList;

import fr.ulco.tp8desir.model.ImageItem;
import fr.ulco.tp8desir.model.LongLat;

public class ImageListManager implements IListManager {

    private final Double Longitude;
    private final Double Latitude;
    private final Activity activity;

    public ImageListManager(Double longitude, Double latitude,Activity activity){
        this.Longitude = longitude;
        this.Latitude = latitude;
        this.activity = activity;
    }

    @Override
    public ArrayList<ImageItem> getImagesList() {
        LongLat longLatPictureParent = new LongLat(Longitude, Latitude);
        ArrayList<Uri> images = ImageManager.getImages(Longitude, Latitude, activity);

        ArrayList<ImageItem> maListeDeDonnees = new ArrayList<>();
        for(Uri contentUri : images){
            LongLat longLatImages =  GeoManager.readLocation(activity, contentUri);
            if(longLatImages == null) continue;
            double distanceOrthodromique = GeoManager.distanceOrthodromique(
                    longLatPictureParent.getLatitude(), longLatPictureParent.getLongitude(), longLatImages.getLatitude(), longLatImages.getLongitude());
            String txt = "Distance : " + String.format("%.2f", distanceOrthodromique) + " km";
            maListeDeDonnees.add(new ImageItem(contentUri, txt));

        }
        return maListeDeDonnees;
    }

}
