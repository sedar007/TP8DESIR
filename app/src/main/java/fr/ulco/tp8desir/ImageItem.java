package fr.ulco.tp8desir;

import android.net.Uri;

public class ImageItem {
    private final Uri uri;
    private final String label;

    public ImageItem(Uri uri, String label) {
        this.uri = uri;
        this.label = label;
    }
    public Uri getUri() {
        return uri;
    }
    public String getLabel() {
        return label;
    }
}
