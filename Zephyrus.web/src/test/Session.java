package test;

import javax.ejb.LocalBean;
import javax.ejb.LocalHome;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 * Session Bean implementation class Session
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
@LocalHome(SessionLocalHome.class)
public class Session implements SessionLocal {

    /**
     * Default constructor. 
     */
    public Session() {
        // TODO Auto-generated constructor stub
    }

}
