package fr.ulco.tp8desir;

import android.app.Activity;
import android.media.ExifInterface;
import android.net.Uri;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class GeoManager {

    // Rayon moyen de la Terre en mètres
    private static final double EARTH_RADIUS = 6371000;

    public static LongLat readLocation(Activity activity, Uri uri) {
        if (uri == null) return null;
        try (InputStream in = activity.getContentResolver().openInputStream(uri)) {
            if (in == null) {
                return null;
            }
            ExifInterface exif = new ExifInterface(in);
            float[] latLong = new float[2];
            if (exif.getLatLong(latLong))
                return new LongLat(latLong[0], latLong[1]);
        } catch (IOException e) {
        }
        return null;
    }

    public static Boolean isValidCoordinates(Activity activity, String latitude, String longitude) {
        try {
            double lat = Double.parseDouble(latitude);
            double lon = Double.parseDouble(longitude);

            if(lat < -90 || lat > 90){
                Toast.makeText(activity, activity.getString(R.string.invalid_latitude_toast), Toast.LENGTH_SHORT).show();
                return false;
            }

            if(lon < -180 || lon > 180){
                Toast.makeText(activity, activity.getString(R.string.invalid_longitude_toast), Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(activity ,activity.getString(R.string.invalid_coordinates_toast), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    public static double distanceOrthodromique(Float lat1, Float lon1, Float lat2, Float lon2) {

        // Conversion degrés → radians
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Formule de Haversine
        double dlat = lat2Rad - lat1Rad;
        double dlon = lon2Rad - lon1Rad;

        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(dlon / 2) * Math.sin(dlon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
}
