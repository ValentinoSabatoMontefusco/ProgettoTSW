package com.code_fanatic.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


import com.code_fanatic.model.bean.CourseBean;
import com.code_fanatic.model.dao.CourseDAO;
import com.code_fanatic.model.dao.IGenericDAO;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/courses")
public class CourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(CourseServlet.class.getName());
	private static final String COURSE_STRING = "courses";

    public CourseServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
	

			DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource"); 
			IGenericDAO<CourseBean, Integer> courseDao = new CourseDAO(dataSource);
			
			if (request.getAttribute(COURSE_STRING) == null) {
				
				Collection<CourseBean> courses = new ArrayList<CourseBean>();
				
				try {
					courses = courseDao.doRetrieveAll("name");		
					request.setAttribute(COURSE_STRING, courses);
				} catch (SQLException e) {
					LOGGER.log(Level.SEVERE, e.getMessage());
		
				}
			}
//			
			if (request.getParameter("name") == null) {
				// Additional error handling?)
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			
			String courseName = request.getParameter("name");
			CourseBean course = null;
			
			@SuppressWarnings("unchecked")
			Iterator<CourseBean> it = ((Collection<CourseBean>) getServletContext().getAttribute(COURSE_STRING)).iterator();
			while (it.hasNext()) {
				
				course = it.next();
				if (courseName.equals(course.getName()))
					break;
				course = null;					
			}
			
			request.setAttribute("course", course);
			
			String role = (String) request.getSession().getAttribute("role");
			Boolean isOwned = false;
			
			if (role != null) {
				
				Collection<Integer> productsOwned = (Collection<Integer>) request.getSession().getAttribute("productsOwned");
				if (productsOwned != null && productsOwned.contains(course.getId()))
						isOwned = true;
							
			}
			
			request.setAttribute("isOwned", isOwned);
		
		

			

			RequestDispatcher view = request.getRequestDispatcher("view/course.jsp");
			view.forward(request, response);
				
	
	
	}
			
		
		

		



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

	public String tempMethod(HttpServletRequest request) {
		
		String[] pathSegments = request.getRequestURI().split("/");
		String lang = "";

		

		try {
			lang = pathSegments[pathSegments.length - 1];

			pathSegments = lang.split("\\.");

			lang = pathSegments[0];
		} catch (Exception e) {
			
			LOGGER.log(Level.SEVERE, e.getMessage());
		
		}
		return lang;
	}
	
	
}

