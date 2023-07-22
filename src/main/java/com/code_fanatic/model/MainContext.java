package com.code_fanatic.model;


import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.*;
import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.sql.*;

import org.apache.jasper.compiler.NewlineReductionServletWriter;

import com.code_fanatic.control.admin.OrdersRecapServlet;
import com.code_fanatic.model.dao.CourseDAO;


@WebListener
public class MainContext implements ServletContextListener  {

	static DataSource dataSource;
	private static final Logger LOGGER = Logger.getLogger(OrdersRecapServlet.class.getName());
	
	public void contextInitialized(ServletContextEvent sce) {
		
		ServletContext context = sce.getServletContext();
		
		System.out.println("Path di salvataggio: " + sce.getServletContext().getRealPath(""));
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			
			dataSource = (DataSource) envContext.lookup("jdbc/Code_Fanatic");	
			
			context.setAttribute("courses", new CourseDAO(dataSource).doRetrieveAll("name"));
			
		} catch (NamingException e) {
			System.out.println("Error:" + e.getMessage());
		}	catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		
		context.setAttribute("DataSource", dataSource);
		System.out.println("DataSource set as context attribute");
		
		// Setting contextPath as a web app wide variable for appropriate management of paths
		
		String ctxPath = context.getContextPath();
		context.setAttribute("ctxPath", ctxPath);
		
		System.out.println(String.format("Proviamo a stampare una variabile dinamicamente: %s", ctxPath));
		
	
	}
	
	public void contextDestroyed(ServletContextEvent sce){
		
		ServletContext context = sce.getServletContext();
		
		DataSource dataSource = (DataSource) context.getAttribute("DataSource");
		System.out.print("DataSource"+ dataSource.toString() + " handled on servlet expiring");
		
		
	}
	
	public static void updateAttributes(ServletContext ctx) {
		
		try {
			ctx.setAttribute("courses", new CourseDAO(dataSource).doRetrieveAll("name") );
		} catch (SQLException e) {
	
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}
	
//	private ArrayList<CourseBean> fetchDBCourses(DataSource ds){
//		
//		Connection conn;
//		Statement stmt;
//		
//		try {
//			conn = ds.getConnection();
//			stmt = conn.createStatement();
//			
//			
//		}
//	}
}
