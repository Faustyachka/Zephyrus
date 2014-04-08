package com.zephyrus.wind;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MapingServlet
 */
@WebServlet("/MapingServlet")
public class MapingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MapingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String longitude = null;
        String latitude = null;
        String dainfor = null;

        
        longitude = request.getParameter("longitude");
        latitude = request.getParameter("latitude");

        if (request.getParameter("longitude").toString().equals("")) {
        	longitude = "Uncle Sam";
        }

        if (request.getParameter("latitude").toString().equals("")) {
        	latitude = "Hello Worrrrld!";
        }
        
        dainfor = "Longitude: " + longitude + "; Latitude: " + latitude;
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(dainfor);
	}

}
