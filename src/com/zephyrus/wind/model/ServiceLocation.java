package com.zephyrus.wind.model;

public class ServiceLocation {
	 private int id;
	    private String coordinates;
	    private double latitude;
	    private double longitude;
	    

	    public double getLongitude() {
	        return longitude;
	    }

	    public void setLongitude(double longitude) {
	        this.longitude = longitude;
	    }

	    public double getLatitude() {
	        return latitude;
	    }

	    public void setLatitude(double latitude) {
	        this.latitude = latitude;
	    }

	    public String getCoordinates() {
	        return coordinates;
	    }

	    public void setCoordinates(String coordinates) {
	        this.coordinates = coordinates;
	    }

	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    

}
