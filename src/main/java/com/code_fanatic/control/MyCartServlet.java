package com.code_fanatic.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.code_fanatic.control.admin.OrdersRecapServlet;
import com.code_fanatic.model.bean.Cartesio;
import com.code_fanatic.model.bean.ProductBean;
import com.code_fanatic.model.dao.MerchDAO;
import com.code_fanatic.model.dao.ProductDAO;

/**
 * Servlet implementation class MyCartServlet
 */
@WebServlet("/user/myCart")
public class MyCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(MyCartServlet.class.getName());
       

    public MyCartServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		Cartesio cart = (Cartesio) request.getSession().getAttribute("cart");
		
		if (cart != null && cart.getTotalQuantity() != 0) {
			
			Collection<Entry<ProductBean, Integer>> products = new ArrayList<Entry<ProductBean,Integer>>();
			DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
			ProductBean currentProduct;
			int currentQuantity;
			
			ProductDAO productDAO = new ProductDAO(ds);
			
			for (Entry<Integer, Integer> entry : cart.getProducts()) {
				
				try {
					currentProduct = productDAO.doRetrieveByKey(entry.getKey());
					if (currentProduct.getType().equals("Merchandise"))
						currentProduct = new MerchDAO(ds).doRetrieveByKey(currentProduct.getId());
					currentQuantity = entry.getValue();
					products.add(new SimpleEntry<ProductBean, Integer>(currentProduct, currentQuantity));
					
				} catch (SQLException e) {
					LOGGER.log(Level.SEVERE, e.getMessage());
				}
			}
			
			request.setAttribute("products",  products);
			
		}
		request.getRequestDispatcher("mycart.jsp").forward(request,  response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
