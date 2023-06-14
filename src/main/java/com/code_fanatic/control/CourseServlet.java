package com.code_fanatic.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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

    public CourseServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
			//String lang = tempMethod(request);
			System.out.println("CourseServlet richiamata");
			DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource"); 
			IGenericDAO<CourseBean, Integer> courseDao = new CourseDAO(dataSource);
			
			if (request.getAttribute("courses") == null) {
				
				Collection<CourseBean> courses = new ArrayList<CourseBean>();
				
				try {
					courses = courseDao.doRetrieveAll("name");		
					request.setAttribute("courses", courses);
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Courses exception doublecheck");
				}
			}
//			
			if (request.getParameter("name") != null) {
				
				String courseName = request.getParameter("name");
				CourseBean course = null;
				
				@SuppressWarnings("unchecked")
				Iterator<CourseBean> it = ((Collection<CourseBean>) getServletContext().getAttribute("courses")).iterator();
				while (it.hasNext()) {
					
					course = it.next();
					if (courseName.equals(course.getName()))
						break;
					course = null;					
				}
				
				request.setAttribute("course", course);
			}
//			try {
//				CourseBean selectedCourse = courseDao.doRetrieveByKey("");
//				request.setAttribute("course", selectedCourse);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			
//			

			RequestDispatcher view = request.getRequestDispatcher("view/Course.jsp");
			view.forward(request, response);
				
	
	
	}
			
		
		

		



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

	public String tempMethod(HttpServletRequest request) {
		
		String[] pathSegments = request.getRequestURI().split("/");
		String lang = "";
		for (String string : pathSegments) 
		{
			System.out.println(string);
		}
		
		System.out.println(pathSegments.length);
		try {
			lang = pathSegments[pathSegments.length - 1];
			System.out.println("Lang = " + lang);
			pathSegments = lang.split("\\.");
			for (String string : pathSegments) 
			{
				System.out.println(string);
			}
			lang = pathSegments[0];
		} catch (Exception e) {
			
			e.printStackTrace();
		
		}
		return lang;
	}
	
	
}

/*
 * 
 * 	DatabaseHandler dbHandler = new DatabaseHandler();
			ResultSet result = dbHandler.executeQuery("SELECT description FROM PRODUCTS WHERE name = '" + lang + "';");
 *		if (result == null) {
				System.out.print("Errore col DB");
				dbHandler.closeConnection();
				return;
			} else {
				request.setAttribute("lang",  lang);
				result.next();
				String description = result.getNString("description");
				request.setAttribute("description", description);
				result = dbHandler.executeQuery("SELECT lesson_count FROM courses INNER JOIN products on products.Id = courses.product_id AND products.name = '" + lang + "';");
				result.next();
				request.setAttribute("lesson_count",  result.getInt("lesson_count"));
				System.out.print("Lesson_Count è " + request.getAttribute("lesson_count"));
				
					dbHandler.closeConnection();
 */
  