package com.code_fanatic.model;


import java.sql.SQLException;


import javax.naming.*;
import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.sql.*;

import com.code_fanatic.model.dao.CourseDAO;


@WebListener
public class MainContext implements ServletContextListener  {

	public void contextInitialized(ServletContextEvent sce) {
		
		ServletContext context = sce.getServletContext();
		DataSource dataSource = null;
		
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			
			dataSource = (DataSource) envContext.lookup("jdbc/Code_Fanatic");	
			
			context.setAttribute("courses", new CourseDAO(dataSource).doRetrieveAll("name"));
			
		} catch (NamingException e) {
			System.out.println("Error:" + e.getMessage());
		}	catch (SQLException e) {
			e.printStackTrace();
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
