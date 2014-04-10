package com.zephyrus.wind;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ServiceLocation;

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
               
        longitude = request.getParameter("longitude");
        latitude = request.getParameter("latitude");

        double longi = Double.parseDouble(longitude);
        double lati = Double.parseDouble(latitude);
     
        ServiceLocation sl = new ServiceLocation();
        sl.setLongitude(longi);
        sl.setLatitude(lati);
        
        DistanceCalculator dc = new DistanceCalculator();

        HashMap<Integer, ProductCatalog> services = dc.getNearestProvidersServices(sl);
      
       
        String json = new Gson().toJson(services);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
	}

}
