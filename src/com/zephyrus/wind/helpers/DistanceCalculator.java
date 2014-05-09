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
 * This class provides functionality of calculating distance 
 * from users Service Location to the all Provider Locations 
 * and finding of services available in users Service Location
 * 																				
 * @author Alexandra Beskorovaynaya
 *
 */
public class DistanceCalculator {
	
	/** The quantity of Providers Locations exist in system*/					// REVIEW: there can be continuous growth in amount of provider locations, so comment is wrong. It should be like this "Maximum number of provider locations that are taken into account when calculating distance to the nearest one". Or you could take all provider locations into account. As you wish.
	private final int MAX_NUMBER_OF_PROVIDER_LOCATIONS = 5;
	
	/**Earth radius in km*/
	private static final double EARTH_RADIUS = 6371.;
	
	/**Provider Locations coverage radius*/										// REVIEW: no units specified
	private static final int COVERAGE_RADIUS = 10;
	
	
	/**
	 * This method calculates the distance in kilometers between Provider
	 *  Location and Service Location.
	 * @param providerLocation location of provider
	 * @param serviceLocation location selected by user 
	 * @return distance between coordinates of Provider Location and Service
	 * Location in kilometers
	 */
	public double calculateDistance(ProviderLocation providerLocation, ServiceLocation serviceLocation){

		//parse Provider location coordinates from string into longitude and latitude
		String[] prCoords = providerLocation.getLocationCoord().split(",");
		double providerLatitude = Double.parseDouble(prCoords[0]);							// REVIEW: how do you catch exceptions here?
		double providerLongitude = Double.parseDouble(prCoords[1]);

		//parse Service location coordinates from string into longitude and latitude
		String[] serCoords = serviceLocation.getServiceLocationCoord().split(",");
		double servLatitude = Double.parseDouble(serCoords[0]);
		double servLongitude = Double.parseDouble(serCoords[1]);

		//convert coordinates from degrees to radians
		final double dlng = deg2rad(providerLongitude - servLongitude);
		final double dlat = deg2rad(providerLatitude - servLatitude);
		
		//calculate the distance between locations
		final double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) + Math.cos(deg2rad(servLatitude))
				* Math.cos(deg2rad(providerLatitude)) * Math.sin(dlng / 2) * Math.sin(dlng / 2);
		final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))*EARTH_RADIUS;
		return c;
	}

	private static double deg2rad(final double degree) {
		return degree * (Math.PI / 180);
	}

	
	/**
	 * This method forms the list of available services in the given Service Location
	 * in dependence from nearest Provider Location
	 * @param sl selected by User Service Location
	 * @param oracleFactory DAO factory
	 * @return the list of available services in the selected Service Location		// REVIEW: when null is returned
	 * @throws Exception															// REVIEW: description expected
	 */																				// REVIEW: too long line
	public ArrayList<ProductCatalog> getNearestProvidersServices(ServiceLocation sl, OracleDAOFactory oracleFactory) throws Exception {	

		IProviderLocationDAO providerLocation = oracleFactory.getProviderLocationDAO();
		ArrayList<ProviderLocation> providerLocs= providerLocation.find(1, MAX_NUMBER_OF_PROVIDER_LOCATIONS);
		IProductCatalogDAO productCatalogDAO = oracleFactory.getProductCatalogDAO();

		ArrayList<ProductCatalog> result = null;

		//let's assume that first provider location is the nearest
		double minimalDistance = calculateDistance(providerLocs.get(0), sl);
		
		//searching of nearest provider location
		for (ProviderLocation entry: providerLocs) {
			double distance = calculateDistance(entry, sl);
			if (distance < minimalDistance) {
				minimalDistance = distance;
				result = productCatalogDAO.getProductsByProviderLocation(entry);
			}

		}
		if (result==null) {
			result = productCatalogDAO.getProductsByProviderLocation(providerLocs.get(0));
		}
		
		//if the minimal distance to provider location is more than coverage radius, then there 
		//are no available services in this Service Location 
		if (minimalDistance > COVERAGE_RADIUS) {
			return null;
		}
		return result;
	}

}
