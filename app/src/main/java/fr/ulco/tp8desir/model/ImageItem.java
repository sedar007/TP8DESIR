package fr.ulco.tp8desir.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class ImageItem implements Parcelable {

    private String uriString;
    private final String label;

    public ImageItem(Uri uri, String label) {
        this.uriString = uri != null ? uri.toString() : null;
        this.label = label;
    }

    protected ImageItem(Parcel in) {
        uriString = in.readString();
        label = in.readString();
    }

    public static final Creator<ImageItem> CREATOR = new Creator<ImageItem>() {
        @Override
        public ImageItem createFromParcel(Parcel in) {
            return new ImageItem(in);
        }

        @Override
        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };

    public Uri getUri() {
        return uriString != null ? Uri.parse(uriString) : null;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uriString);
        dest.writeString(label);
    }
}
