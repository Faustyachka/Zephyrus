package com.zephyrus.wind.helpers;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;



import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ProviderLocation;
import com.zephyrus.wind.model.ServiceLocation;
/**
 * 
 * @author Alexandra Beskorovaynaya
 *
 */
public class DistanceCalculator {
	private static final double EARTH_RADIUS = 6371.;
	
	public double calculateDistance(ProviderLocation providerLocation, ServiceLocation serviceLocation){
		

        // Calculate the distance between points
		String[] prCoords = providerLocation.getLocationCoord().split(",");
		double providerLongitude = Double.parseDouble(prCoords[0]);
		double providerLatitude = Double.parseDouble(prCoords[1]);
		String[] serCoords = serviceLocation.getServiceLocationCoord().split(",");
		double servLongitude = Double.parseDouble(serCoords[0]);
		double servLatitude = Double.parseDouble(serCoords[1]);
       final double dlng = deg2rad(providerLongitude - servLongitude);
        final double dlat = deg2rad(providerLatitude - servLatitude);
       final double a = java.lang.Math.sin(dlat / 2) * java.lang.Math.sin(dlat / 2) + java.lang.Math.cos(deg2rad(servLatitude))
               * java.lang.Math.cos(deg2rad(providerLatitude)) * java.lang.Math.sin(dlng / 2) * java.lang.Math.sin(dlng / 2);
       final double c = 2 * java.lang.Math.atan2(java.lang.Math.sqrt(a), java.lang.Math.sqrt(1 - a))*EARTH_RADIUS;
       return c;
    }
	
	private static double deg2rad(final double degree) {
        return degree * (Math.PI / 180);
    }
	
	public HashMap<Integer, ProductCatalog> getNearestProvidersServices(ServiceLocation sl) {
		ProductCatalog pc1 = new ProductCatalog();
		pc1.setId(1);
		pc1.setProductName("Red Internet");
		pc1.setProviderLocId(new BigDecimal(1));
		ProductCatalog pc2 = new ProductCatalog();
		pc2.setId(2);
		pc2.setProductName("Blue Internet");
		pc2.setProviderLocId(new BigDecimal(1));
		ProductCatalog pc3 = new ProductCatalog();
		pc3.setProductName("Black Internet");
		pc3.setId(3);
		pc3.setProviderLocId(new BigDecimal(2));
		ProductCatalog pc4 = new ProductCatalog();
		pc4.setProductName("Yellow Internet");
		pc4.setId(4);
		pc4.setProviderLocId(new BigDecimal(2));
		ProductCatalog pc5 = new ProductCatalog();
		pc5.setId(5);
		pc5.setProductName("Orange Internet");
		pc5.setProviderLocId(new BigDecimal(3));
		ProductCatalog pc6 = new ProductCatalog();
		pc6.setId(6);
		pc6.setProductName("Green Internet");
		pc6.setProviderLocId(new BigDecimal(3));
		ProviderLocation pl1 = new ProviderLocation();
		pl1.setId(1);
		pl1.setLocationCoord("50.25445454,30.54484145");
		pl1.setLocationName("Provider Location 1");
		ProviderLocation pl2 = new ProviderLocation();
		pl2.setId(2);
		pl2.setLocationCoord("14.546464546,52.326454545");
		pl2.setLocationName("Provider Location 2");
		ProviderLocation pl3 = new ProviderLocation();
		pl3.setId(3);
		pl3.setLocationCoord("-14.5544545,47.5454545");
		pl3.setLocationName("Provider Location 3");
		ArrayList<ProviderLocation> providerLocs= new ArrayList<>();
		providerLocs.add(pl1);
		providerLocs.add(pl2);
		providerLocs.add(pl3);
		ArrayList<ProductCatalog> productCatalogs = new ArrayList<>();
		productCatalogs.add(pc1);
		productCatalogs.add(pc2);
		productCatalogs.add(pc3);
		productCatalogs.add(pc4);
		productCatalogs.add(pc5);
		productCatalogs.add(pc6);
		HashMap<Integer, ProductCatalog> result = null;
		
		double minimalDistance = calculateDistance(providerLocs.get(1), sl);
		
		for (ProviderLocation entry: providerLocs) {
			double distance = calculateDistance(entry, sl);
			if (distance< minimalDistance) {
				result= new HashMap<>();
				for(ProductCatalog pc: productCatalogs) {
					if(pc.getProviderLocId().setScale( 0, BigDecimal.ROUND_HALF_UP ).longValue()==entry.getId()) {
						result.put((int)pc.getId(), pc);
					}
				}
			}
			
		}
		 if (result==null) {
			 result =  new HashMap<>();
				for(ProductCatalog pc: productCatalogs) {
					if(pc.getProviderLocId().setScale( 0, BigDecimal.ROUND_HALF_UP ).longValue()==providerLocs.get(1).getId()) {
						result.put((int)pc.getId(), pc);
					}
				}
			}
		return result;
	}
}
