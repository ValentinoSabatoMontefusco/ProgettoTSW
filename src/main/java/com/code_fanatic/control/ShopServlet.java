package com.code_fanatic.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.code_fanatic.model.bean.CourseBean;
import com.code_fanatic.model.bean.ProductBean;
import com.code_fanatic.model.dao.CourseDAO;
import com.code_fanatic.model.dao.IGenericDAO;
import com.code_fanatic.model.dao.ProductDAO;

@WebServlet("/shop")
public class ShopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public ShopServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("ShopServlet avviata");
		
		// Temporary method to verify everything works
		
		IGenericDAO<ProductBean, Integer> productDao = new ProductDAO((DataSource) getServletContext().getAttribute("DataSource"));
		Collection<ProductBean> products = null; 
		Collection<Integer> productsOwned = (Collection<Integer>) request.getSession().getAttribute("productsOwned"); 
		
		try {
			products = productDao.doRetrieveAll("name");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (productsOwned != null && productsOwned.size() > 0) {
			
			//for (ProductBean product : products) {
				
				//if (productsOwned.contains((Integer) product.getId()))
					products.removeIf( p -> productsOwned.contains(p.getId()));
			//}
		}
		
		request.setAttribute("products", products);
		
		RequestDispatcher view = request.getRequestDispatcher("shop.jsp");
		view.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
