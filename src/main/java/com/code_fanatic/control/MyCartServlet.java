package com.code_fanatic.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.code_fanatic.model.bean.Cartesio;
import com.code_fanatic.model.bean.ProductBean;
import com.code_fanatic.model.dao.ProductDAO;

/**
 * Servlet implementation class MyCartServlet
 */
@WebServlet("/user/myCart")
public class MyCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public MyCartServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("MyCart Servlet avviata");
		Cartesio cart = (Cartesio) request.getSession().getAttribute("cart");
		
		if (cart != null && cart.getTotalQuantity() != 0) {
			
			Collection<Entry<ProductBean, Integer>> products = new ArrayList<Entry<ProductBean,Integer>>();
			ProductBean currentProduct;
			int currentQuantity;
			
			ProductDAO productDAO = new ProductDAO((DataSource) getServletContext().getAttribute("DataSource"));
			
			for (Entry<Integer, Integer> entry : cart.getProducts()) {
				
				try {
					currentProduct = productDAO.doRetrieveByKey(entry.getKey());
					currentQuantity = entry.getValue();
					products.add(new SimpleEntry<ProductBean, Integer>(currentProduct, currentQuantity));
					
				} catch (SQLException e) {
					e.printStackTrace();
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
