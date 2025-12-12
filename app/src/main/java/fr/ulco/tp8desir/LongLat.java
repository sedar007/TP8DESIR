package fr.ulco.tp8desir;

public class LongLat {
    private final Double longitude;
    private final Double latitude;

    public LongLat(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
    public Double getLatitude() {
        return latitude;
    }

}
