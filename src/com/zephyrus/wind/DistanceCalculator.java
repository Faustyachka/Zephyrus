package com.zephyrus.wind;

import com.zephyrus.wind.model.ProviderLocation;
import com.zephyrus.wind.model.ServiceLocation;
import static java.lang.Math.*;

public class DistanceCalculator {
	private static final double EARTH_RADIUS = 6371.;
	
	public double calculateDistance(ProviderLocation providerLocation, ServiceLocation serviceLocation){


        // Рассчитываем расстояние между точками
        final double dlng = deg2rad(providerLocation.getLongitude() - serviceLocation.getLongitude());
        final double dlat = deg2rad(providerLocation.getLatitude() - serviceLocation.getLatitude());
        final double a = sin(dlat / 2) * sin(dlat / 2) + cos(deg2rad(serviceLocation.getLatitude()))
                * cos(deg2rad(providerLocation.getLatitude())) * sin(dlng / 2) * sin(dlng / 2);
        final double c = 2 * atan2(sqrt(a), sqrt(1 - a))*EARTH_RADIUS;
        return c;
//        System.out.println("distance: " + c * EARTH_RADIUS); // получаем расстояние в километрах
    }
	
	private static double deg2rad(final double degree) {
        return degree * (Math.PI / 180);
    }
}
