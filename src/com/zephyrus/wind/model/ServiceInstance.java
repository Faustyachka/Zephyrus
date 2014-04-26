package com.zephyrus.wind.model;

import java.io.Serializable;
import java.util.Date;



/**
 * The persistent class for the SERVICE_INSTANCES database table.
 * 																							// REVIEW: author expected
 */

public class ServiceInstance implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Circuit circuit;

	private ProductCatalog productCatalog;
	
	private Date startDate;

	private ServiceInstanceStatus servInstanceStatus;

	private User user;

	public ServiceInstance() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public Circuit getCircuit() {
		return circuit;
	}

	public void setCircuit(Circuit circuit) {
		this.circuit = circuit;
	}

	public ProductCatalog getProductCatalog() {
		return productCatalog;
	}

	public void setProductCatalog(ProductCatalog productCatalog) {
		this.productCatalog = productCatalog;
	}

	public ServiceInstanceStatus getServInstanceStatus() {
		return servInstanceStatus;
	}

	public void setServInstanceStatus(ServiceInstanceStatus servInstanceStatus) {
		this.servInstanceStatus = servInstanceStatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}



	
}