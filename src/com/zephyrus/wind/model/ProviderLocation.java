package com.zephyrus.wind.model;

import java.util.HashMap;

public class ProviderLocation {
	 private int id;
	    private String coordinates;
	    private double latitude;
	    private double longitude;
	    private double radius;
	    private String name;
	    private HashMap<Integer, ProductCatalog> productCatalogs;

	    
	    public HashMap<Integer, ProductCatalog> getProductCatalogs() {
			return productCatalogs;
		}

		public void setProductCatalogs(HashMap<Integer, ProductCatalog> productCatalogs) {
			this.productCatalogs = productCatalogs;
		}

		public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public String getCoordinates() {
	        return coordinates;
	    }

	    public void setCoordinates(String coordinates) {
	        this.coordinates = coordinates;
	    }

	    public double getLatitude() {
	        return latitude;
	    }

	    public void setLatitude(double latitude) {
	        this.latitude = latitude;
	    }

	    public double getLongitude() {
	        return longitude;
	    }

	    public void setLongitude(double longitude) {
	        this.longitude = longitude;
	    }

	    public double getRadius() {
	        return radius;
	    }

	    public void setRadius(double radius) {
	        this.radius = radius;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

}