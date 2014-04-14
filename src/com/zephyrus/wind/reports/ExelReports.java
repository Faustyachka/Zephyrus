package com.zephyrus.wind.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.sun.xml.bind.v2.schemagen.xmlschema.List;

/**
 * Servlet implementation class ExelReports
 */
@WebServlet("/ExelReports")
public class ExelReports extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExelReports() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		// out.println("12312313");
		ArrayList<String> list = new ArrayList<String>();
		list.add("Fist");
		list.add("Second");
		list.add("Last");
		try {
			String path = "E:\\reports\\";
			Workbook workbook = ExelExport.getRouterUtilizationReport(list);
			SimpleDateFormat sdf = new SimpleDateFormat("dd_M_yyyy_hh_mm_ss");
			String fileName = "report" + sdf.format(new Date()) + ".xls";
			File exelFile = new File(path + fileName);
			FileOutputStream outFile = new FileOutputStream(exelFile);
			out.println("<a href=\"reports/" + fileName + "\">Download</a>");
			workbook.write(outFile);
			outFile.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
