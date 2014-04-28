package com.zephyrus.wind;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.Task;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.workflow.NewScenarioWorkflow;
import com.zephyrus.wind.workflow.WorkflowException;

/**
 * Servlet implementation class TestWorkflowServlet
 */
@WebServlet("/TestWorkflowServlet")
public class TestWorkflowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestWorkflowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response)			
		    throws ServletException, IOException {
    	ServiceOrder order = null;
    	OracleDAOFactory factory = new OracleDAOFactory();
		try {
			factory.beginConnection();
			int orderID = 2;
			order = factory.getServiceOrderDAO().findById(orderID);
		} catch (Exception exc) {
			throw new WorkflowException("Assign task exception", exc);
		} finally {
			factory.endConnection();
		}
		
		NewScenarioWorkflow wf = new NewScenarioWorkflow(order);
		wf.proceedOrder();
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}

}
