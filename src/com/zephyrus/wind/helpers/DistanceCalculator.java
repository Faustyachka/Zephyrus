package com.zephyrus.wind.helpers;

import java.util.ArrayList;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IProductCatalogDAO;
import com.zephyrus.wind.dao.interfaces.IProviderLocationDAO;
import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ProviderLocation;
import com.zephyrus.wind.model.ServiceLocation;

import java.lang.Math;

/**
 * This class provides functionality of calculating distance from users Service
 * Location to the all Provider Locations and finding of services available in
 * users Service Location
 * 
 * @author Alexandra Beskorovaynaya
 * 
 */
public class DistanceCalculator {

	/**
	 * Maximum number of provider locations that are taken into account when
	 * calculating distance to the nearest one
	 */
	private final int MAX_NUMBER_OF_PROVIDER_LOCATIONS = 5;

	/** Earth radius in km */
	private static final double EARTH_RADIUS = 6371.;

	/** Provider Locations coverage radius in km */
	private static final int COVERAGE_RADIUS = 10;

	/**
	 * This method calculates the distance in kilometers between Provider
	 * Location and Service Location.
	 * 
	 * @param providerLocation location of provider
	 * @param serviceLocation location selected by user
	 * @return distance between coordinates of Provider Location and Service
	 *         Location in kilometers
	 */
	public double calculateDistance(ProviderLocation providerLocation,
			ServiceLocation serviceLocation) {

		String[] prCoords = providerLocation.getLocationCoord().split(",");
		String[] serCoords = serviceLocation.getServiceLocationCoord().split(
				",");
		double servLongitude = 0;
		double servLatitude = 0;
		double providerLatitude = 0;
		double providerLongitude = 0;

		try {

			// parse Provider location coordinates from string into longitude
			// and latitude
			providerLatitude = Double.parseDouble(prCoords[0]); 
			providerLongitude = Double.parseDouble(prCoords[1]);

			// parse Service location coordinates from string into longitude and
			// latitude
			servLatitude = Double.parseDouble(serCoords[0]);
			servLongitude = Double.parseDouble(serCoords[1]);

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
		// convert coordinates from degrees to radians
		final double dlng = deg2rad(providerLongitude - servLongitude);
		final double dlat = deg2rad(providerLatitude - servLatitude);

		// calculate the distance between locations
		final double a = Math.sin(dlat / 2) * Math.sin(dlat / 2)
				+ Math.cos(deg2rad(servLatitude))
				* Math.cos(deg2rad(providerLatitude)) * Math.sin(dlng / 2)
				* Math.sin(dlng / 2);
		final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
				* EARTH_RADIUS;
		return c;
	}

	private static double deg2rad(final double degree) {
		return degree * (Math.PI / 180);
	}

	/**
	 * This method forms the list of available services in the given Service
	 * Location in dependence from nearest Provider Location
	 * 
	 * @param sl selected by User Service Location           
	 * @param oracleFactory DAO factory
	 * @return the list of available services in the selected Service Location.
	 *         Returns null if distance between Service Location and nearest
	 *         Provider Location is more than coverage radius         
	 * @throws Exception
	 */
	public ArrayList<ProductCatalog> getNearestProvidersServices(
			ServiceLocation sl, OracleDAOFactory oracleFactory) throws Exception {

		IProviderLocationDAO providerLocation = oracleFactory
				.getProviderLocationDAO();
		ArrayList<ProviderLocation> providerLocs = providerLocation.find(1,
				MAX_NUMBER_OF_PROVIDER_LOCATIONS);
		IProductCatalogDAO productCatalogDAO = oracleFactory
				.getProductCatalogDAO();

		ArrayList<ProductCatalog> result = null;

		// let's assume that first provider location is the nearest
		double minimalDistance = calculateDistance(providerLocs.get(0), sl);

		// searching of nearest provider location
		for (ProviderLocation entry : providerLocs) {
			double distance = calculateDistance(entry, sl);
			if (distance < minimalDistance) {
				minimalDistance = distance;
				result = productCatalogDAO.getProductsByProviderLocation(entry);
			}

		}
		if (result == null) {
			result = productCatalogDAO
					.getProductsByProviderLocation(providerLocs.get(0));
		}

		// if the minimal distance to provider location is more than coverage
		// radius, then there
		// are no available services in this Service Location
		if (minimalDistance > COVERAGE_RADIUS) {
			return null;
		}
		return result;
	}

}
