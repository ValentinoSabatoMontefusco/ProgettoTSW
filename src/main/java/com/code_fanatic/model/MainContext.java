package com.code_fanatic.model;


import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.*;
import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.sql.*;


import com.code_fanatic.model.dao.CourseDAO;


@WebListener
public class MainContext implements ServletContextListener  {

	private static final String DS_STRING = "DataSource";
	private static final Logger LOGGER = Logger.getLogger(MainContext.class.getName());
	
	public void contextInitialized(ServletContextEvent sce) {
		
		ServletContext context = sce.getServletContext();
		DataSource dataSource = null;
		
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			
			dataSource = (DataSource) envContext.lookup("jdbc/Code_Fanatic");	
			
			context.setAttribute("courses", new CourseDAO(dataSource).doRetrieveAll("name"));
			
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,  e.getMessage());
		}
		
		context.setAttribute(DS_STRING, dataSource);
		LOGGER.log(Level.FINE, "DataSource set as context attribute");
		
		// Setting contextPath as a web app wide variable for appropriate management of paths
		
		String ctxPath = context.getContextPath();
		context.setAttribute("ctxPath", ctxPath);
		
		
		
	
	}
	
	public void contextDestroyed(ServletContextEvent sce){
		
		ServletContext context = sce.getServletContext();
		
		DataSource dataSource = (DataSource) context.getAttribute(DS_STRING);
		if (dataSource != null)
			LOGGER.log(Level.INFO, "DataSource handled on servlet expiring");
		
	}
	
	public static synchronized void updateAttributes(ServletContext ctx) {
		
		DataSource dataSource = (DataSource) ctx.getAttribute(DS_STRING);
		try {
			ctx.setAttribute("courses", new CourseDAO(dataSource).doRetrieveAll("name") );
		} catch (SQLException e) {
	
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}
	
}
