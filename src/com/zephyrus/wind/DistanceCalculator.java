package com.zephyrus.wind;

import java.util.HashMap;
import java.util.Map;

import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ProviderLocation;
import com.zephyrus.wind.model.ServiceLocation;

import static java.lang.Math.*;

public class DistanceCalculator {
	private static final double EARTH_RADIUS = 6371.;
	
	public double calculateDistance(ProviderLocation providerLocation, ServiceLocation serviceLocation){


        // Calculate the distance between points
        final double dlng = deg2rad(providerLocation.getLongitude() - serviceLocation.getLongitude());
        final double dlat = deg2rad(providerLocation.getLatitude() - serviceLocation.getLatitude());
        final double a = sin(dlat / 2) * sin(dlat / 2) + cos(deg2rad(serviceLocation.getLatitude()))
                * cos(deg2rad(providerLocation.getLatitude())) * sin(dlng / 2) * sin(dlng / 2);
        final double c = 2 * atan2(sqrt(a), sqrt(1 - a))*EARTH_RADIUS;
        return c;
    }
	
	private static double deg2rad(final double degree) {
        return degree * (Math.PI / 180);
    }
	
	public HashMap<Integer, ProductCatalog> getNearestProvidersServices(ServiceLocation sl) {
		ProductCatalog pc1 = new ProductCatalog();
		pc1.setId(1);
		pc1.setProductName("Red Internet");
		ProductCatalog pc2 = new ProductCatalog();
		pc2.setId(2);
		pc2.setProductName("Blue Internet");
		ProductCatalog pc3 = new ProductCatalog();
		pc3.setProductName("Black Internet");
		pc3.setId(3);
		ProductCatalog pc4 = new ProductCatalog();
		pc4.setProductName("Yellow Internet");
		pc4.setId(4);
		ProductCatalog pc5 = new ProductCatalog();
		pc5.setId(5);
		pc5.setProductName("Orange Internet");
		ProductCatalog pc6 = new ProductCatalog();
		pc6.setId(6);
		pc6.setProductName("Green Internet");
		ProviderLocation pl1 = new ProviderLocation();
		pl1.setId(1);
		pl1.setLatitude(40.1254655);
		pl1.setLongitude(20.3655445);
		pl1.setName("Provider Location 1");
		HashMap<Integer, ProductCatalog> prc1 = new HashMap<>();
		prc1.put(1, pc1);
		prc1.put(2, pc2);
		prc1.put(3, pc3);
		pl1.setProductCatalogs(prc1);
		ProviderLocation pl2 = new ProviderLocation();
		HashMap<Integer, ProductCatalog> prc2 = new HashMap<>();
		pl2.setId(2);
		pl2.setLatitude(50.1254655);
		pl2.setLongitude(30.45778889);
		pl2.setName("Provider Location 2");
		prc2.put(3, pc3);
		prc2.put(4, pc4);
		prc2.put(5, pc5);
		pl2.setProductCatalogs(prc2);
		ProviderLocation pl3 = new ProviderLocation();
		HashMap<Integer, ProductCatalog> prc3 = new HashMap<>();
		pl3.setId(3);
		pl3.setLatitude(-96.5411);
		pl3.setLongitude(74.4545111);
		pl3.setName("Provider Location 3");
		prc3.put(5, pc5);
		prc3.put(6, pc6);
		prc3.put(3, pc3);
		prc3.put(4, pc4);
		pl3.setProductCatalogs(prc3);
		
		HashMap<Integer, ProviderLocation> prLocs = new HashMap<>();
		prLocs.put(pl1.getId(), pl1);
		prLocs.put(pl2.getId(), pl2);
		prLocs.put(pl3.getId(), pl3);
		HashMap<Integer, ProductCatalog> result = new HashMap<>();
		
		double minimalDistance = calculateDistance(prLocs.get(1), sl);
		
		for (Map.Entry<Integer, ProviderLocation> entry: prLocs.entrySet()) {
			double distance = calculateDistance(entry.getValue(), sl);
			if (distance< minimalDistance) {
				result = entry.getValue().getProductCatalogs();
			} else {
				result = prLocs.get(1).getProductCatalogs();
			}
			
		}
		return result;
	}
}
