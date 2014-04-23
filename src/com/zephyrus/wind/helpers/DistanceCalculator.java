package com.zephyrus.wind.helpers;


import java.math.BigDecimal;
import java.util.ArrayList;





import com.zephyrus.wind.dao.interfaces.IServiceTypeDAO;
import com.zephyrus.wind.model.ServiceType;
import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IDAO;
import com.zephyrus.wind.dao.interfaces.IProductCatalogDAO;
import com.zephyrus.wind.dao.interfaces.IProviderLocationDAO;
import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ProductCatalogService;
import com.zephyrus.wind.model.ProviderLocation;
import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.User;
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

	public ArrayList<ProductCatalogService> getNearestProvidersServices(ServiceLocation sl, OracleDAOFactory oracleFactory) throws Exception {

		IDAO<ProductCatalog> productCatalog = oracleFactory.getProductCatalogDAO();
		ArrayList<ProductCatalog> productCatalogs = productCatalog.findAll();
		IDAO<ProviderLocation> providerLocation = oracleFactory.getProviderLocationDAO();
		ArrayList<ProviderLocation> providerLocs= providerLocation.findAll();


		ArrayList<ProductCatalogService> result = null;

		double minimalDistance = calculateDistance(providerLocs.get(1), sl);

		for (ProviderLocation entry: providerLocs) {
			double distance = calculateDistance(entry, sl);
			if (distance < minimalDistance) {
				result= new ArrayList<>();
				for(ProductCatalog pc: productCatalogs) {
					if(pc.getProviderLocId() == entry.getId()) {
						ProductCatalogService serviceData = createProductCatalogService(pc, oracleFactory);
						result.add(serviceData);
					}
				}
			}

		}
		if (result==null) {
			result =  new ArrayList<>();
			for(ProductCatalog pc: productCatalogs) {
				if(pc.getProviderLocId() == providerLocs.get(1).getId()) {
					ProductCatalogService serviceData = createProductCatalogService(pc, oracleFactory);
					result.add(serviceData);
				}
			}
		}
		return result;
	}

	public ProductCatalogService createProductCatalogService(ProductCatalog pc, OracleDAOFactory oracleFactory) throws Exception{
		ProductCatalogService serviceData = new ProductCatalogService();
		serviceData.setId(pc.getId());
		serviceData.setPrice( pc.getPrice());
		IServiceTypeDAO serviceType = oracleFactory.getServiceTypeDAO();
		ServiceType service = serviceType.findById(pc.getServiceTypeId().intValue());
		serviceData.setServiceName(service.getServiceType());
		return serviceData;
	}

}
