package com.zephyrus.wind.model;

import java.io.Serializable;



/**
 * The persistent class for the PROVIDER_LOCATIONS database table.
 * 																						// REVIEW: author expected
 */

public class ProviderLocation implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String locationCoord;

	private String locationName;

	public ProviderLocation() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
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