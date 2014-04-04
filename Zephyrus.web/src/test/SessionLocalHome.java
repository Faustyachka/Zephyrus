package test;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface SessionLocalHome extends EJBLocalHome {

	public SessionLocalComponent create() throws CreateException;
	
}
