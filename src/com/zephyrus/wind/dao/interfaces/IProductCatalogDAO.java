package com.zephyrus.wind.dao.interfaces;

import java.util.ArrayList;

import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ProviderLocation;

/**
* The interface enforces the operations needed to deal with OracleProductCatalogDAO instances.
* @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
*/
public interface IProductCatalogDAO extends IDAO<ProductCatalog> {

	/**
	 * Method allows to get products for given Provider Location.
	 * @param Provider Location for which it is necessary to find products
	 * @return list of products from Product Catalog
	 */
	ArrayList<ProductCatalog> getProductsByProviderLocation(ProviderLocation providerLocation)  throws Exception;

}
