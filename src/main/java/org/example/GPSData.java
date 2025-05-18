package org.example;

public class GPSData {
    private String busId;
    private String timestamp;
    private double latitude;
    private double longitude;
    private double speed;

    public GPSData(String busId, String timestamp, double latitude, double longitude, double speed) {
        this.busId = busId;
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
    }

    public String getBusId() { return busId; }
    public String getTimestamp() { return timestamp; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public double getSpeed() { return speed; }

    public String toCSV() {
        return busId + "," + timestamp + "," + latitude + "," + longitude + "," + speed;
    }
}

