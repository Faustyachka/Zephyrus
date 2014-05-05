package com.zephyrus.wind;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.email.Email;
import com.zephyrus.wind.email.EmailSender;
import com.zephyrus.wind.email.RegistrationSuccessfulEmail;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.helpers.CSVConverter;
import com.zephyrus.wind.model.Cable;
import com.zephyrus.wind.model.Port;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.Task;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.reports.DisconnectOrdersPerPeriodReport;
import com.zephyrus.wind.reports.IReport;
import com.zephyrus.wind.reports.NewOrdersPerPeriodReport;
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
    	NewOrdersPerPeriodReport report = null;
		try {
			report = new NewOrdersPerPeriodReport(new Date(99, 12, 10), new Date(114, 12, 10));
			//report.getReportData(offset, count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final int MAX_ROWS_IN_EXCEL = 65535;
    	Workbook wb = report.convertToExel(MAX_ROWS_IN_EXCEL);
    	//write workbook to outputstream
        //offer the user the option of opening or downloading the resulting Excel file
        response.setContentType("application/vnd.ms-excel");
        //response.setHeader("Content-Disposition", "attachment; filename=MyExcel.csv");
        response.setHeader("Content-Disposition", "attachment; filename=MyExcel.xls");
        ServletOutputStream out = response.getOutputStream();
        //byte[] data = CSVConverter.convert(wb);
        //out.write(data);
        wb.write(out);
        out.flush();
        out.close();
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
