package com.code_fanatic.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.code_fanatic.control.admin.OrdersRecapServlet;
import com.code_fanatic.model.bean.CourseBean;
import com.code_fanatic.model.dao.CourseDAO;
import com.code_fanatic.model.dao.IGenericDAO;


@WebServlet("/user/myCourses")
public class MyCoursesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(MyCoursesServlet.class.getName());
       

    public MyCoursesServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		@SuppressWarnings("unchecked")
		Collection<Integer> productsOwned = (Collection<Integer>) request.getSession().getAttribute("productsOwned");
		Collection<CourseBean> courses = new ArrayList<CourseBean>();
		
		if (productsOwned != null) {
			
			
			DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
			
			IGenericDAO<CourseBean, Integer> courseDao = new CourseDAO(ds);
			try {
				for (Integer key : productsOwned) {
					
					courses.add(courseDao.doRetrieveByKey(key));
				}
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, e.getMessage());			}
			
			request.setAttribute("courses", courses);
		}
		
		
		request.getRequestDispatcher("mycourses.jsp").forward(request, response);
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
