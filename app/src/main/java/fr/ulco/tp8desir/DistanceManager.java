package fr.ulco.tp8desir;

import android.app.Activity;
import android.net.Uri;

import java.util.ArrayList;

import fr.ulco.tp8desir.model.ImageItem;
import fr.ulco.tp8desir.model.LongLat;

public class DistanceManager implements IListManager {

    private final Double Longitude;
    private final Double Latitude;
    private final Double xDistance;
    private final Activity activity;

    public DistanceManager(Double longitude, Double latitude, Double distance, Activity activity){
        this.xDistance = distance;
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

            if(distanceOrthodromique > xDistance) continue;
            String txt = "Distance : " + String.format("%.2f", distanceOrthodromique) + " km";

            maListeDeDonnees.add(new ImageItem(contentUri, txt));

        }
        return maListeDeDonnees;
    }

}
