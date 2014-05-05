package com.zephyrus.wind.dao.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ProviderLocation;
																								// REVIEW: documentation expected
public interface IProductCatalogDAO extends IDAO<ProductCatalog> {

	/**
	 * Method allows to get products for given Provider Location.
	 * @param Provider Location for which it is necessary to find products
	 * @return list of products from Product Catalog
	 * @author Miroshnychenko Nataliya
	 */
	ArrayList<ProductCatalog> getProductsByProviderLocation(ProviderLocation providerLocation)  throws Exception;

}
