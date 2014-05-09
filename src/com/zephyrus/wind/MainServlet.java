package com.zephyrus.wind;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.Command;
import com.zephyrus.wind.enums.PAGES;													
import com.zephyrus.wind.managers.CommandManager;
import com.zephyrus.wind.managers.MessageManager;

// The Main entry point

																									// REVIEW: documentation expected
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static CommandManager commandManager;

    public MainServlet() {
        super();
    }


    // for initial parameters 
	public void init(ServletConfig config) throws ServletException {
		
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)			
		    throws ServletException, IOException {
		        String page = null;
		        try{
		            //defining the command from jsp page
		            commandManager = new CommandManager();
		            Command command=commandManager.getCommand(request);
		            
		            //calling execute() on Command, method returns the answer page
		            page = command.execute(request,response);    
		            
		        }catch (ServletException e){
		            e.printStackTrace();
		            //generating error message
		            request.setAttribute("errorMessage", MessageManager.SERVLET_EXCEPTION_ERROR_MESSAGE);
		            //calling error-jsp
		            request.setAttribute("title", MessageManager.ERROR);
		            page = PAGES.MESSAGE_PAGE.getValue();
		        }catch(IOException e){
		            e.printStackTrace();
		            request.setAttribute("errorMessage", MessageManager.IO_EXCEPTION_ERROR_MESSAGE);
		            request.setAttribute("title", MessageManager.ERROR);
		            page = PAGES.MESSAGE_PAGE.getValue();
		        }catch(Exception e){
		        	e.printStackTrace();
			        request.setAttribute("errorMessage", e.toString() + e.getMessage());
			        request.setAttribute("title", MessageManager.ERROR);
			        page = PAGES.MESSAGE_PAGE.getValue();
		        }
		        if(page != null){
		        //forwarding to answer page
		        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		        dispatcher.forward(request, response);
		        }
		    }																							// REVIEW: watch formatting: } brace is out of line

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // REVIEW: too long line
		processRequest(request, response);
		try{
//		MailSend.send("The letter1", new String[]{"misterdan@bigmir.net"},"Hello, Bogdan!");			// REVIEW: mail templates should be used
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // REVIEW: too long line
		processRequest(request, response);
	}

}
