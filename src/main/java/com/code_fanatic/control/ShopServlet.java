package com.code_fanatic.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.code_fanatic.control.admin.OrdersRecapServlet;
import com.code_fanatic.model.bean.CourseBean;
import com.code_fanatic.model.bean.MerchBean;
import com.code_fanatic.model.bean.ProductBean;
import com.code_fanatic.model.dao.CourseDAO;
import com.code_fanatic.model.dao.IGenericDAO;
import com.code_fanatic.model.dao.MerchDAO;
import com.code_fanatic.model.dao.ProductDAO;

@WebServlet(urlPatterns = {"/shop", "/admin/productManagement"})
public class ShopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(ShopServlet.class.getName());
       
  
    public ShopServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("ShopServlet avviata con request URI: " + request.getRequestURI());
		
		// Temporary method to verify everything works
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		ProductDAO productDao = new ProductDAO(ds);
		Collection<ProductBean> products = null; 
		 
		
		try {
			products = productDao.doRetrieveAllSubclasses("name");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		
		if (!request.getRequestURI().contains("admin")) {
			Collection<Integer> productsOwned = (Collection<Integer>) request.getSession().getAttribute("productsOwned");
			if (productsOwned != null && productsOwned.size() > 0) {
				
				//for (ProductBean product : products) {
					
					//if (productsOwned.contains((Integer) product.getId()))
						products.removeIf( p -> productsOwned.contains(p.getId()));
				//}
			}
		}
		
		
		request.setAttribute("products", products);
		RequestDispatcher view = null;
		if (!request.getRequestURI().contains("admin"))
			view = request.getRequestDispatcher("shop.jsp");
		else
			view = request.getRequestDispatcher("productManagement.jsp");
		view.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
