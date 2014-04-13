package com.zephyrus.wind.model;

import java.io.Serializable;



/**
 * The persistent class for the PROVIDER_LOCATIONS database table.
 * 
 */

public class ProviderLocation implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;

	private String locationCoord;

	private String locationName;

	public ProviderLocation() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLocationCoord() {
		return this.locationCoord;
	}

	public void setLocationCoord(String locationCoord) {
		this.locationCoord = locationCoord;
	}

	public String getLocationName() {
		return this.locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

}