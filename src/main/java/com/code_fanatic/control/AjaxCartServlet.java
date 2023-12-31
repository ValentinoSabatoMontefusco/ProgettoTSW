package com.code_fanatic.control;

import java.io.IOException;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


import com.code_fanatic.model.bean.Cartesio;
import com.code_fanatic.model.bean.OrderBean;
import com.code_fanatic.model.bean.UserBean;
import com.code_fanatic.model.dao.CartDAO;
import com.code_fanatic.model.dao.ICartDAO;
import com.code_fanatic.model.dao.IGenericDAO;
import com.code_fanatic.model.dao.OrderDAO;
import com.code_fanatic.model.dao.UserDAO;
import com.google.gson.Gson;



@WebServlet("/ajaxCart")
public class AjaxCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(AjaxCartServlet.class.getName());

    public AjaxCartServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

				
		String type = request.getParameter("type");
		int productID = Integer.parseInt(request.getParameter("prodID"));
		String username = (String) request.getSession().getAttribute("username");		// Superflous
		

		DataSource ds = null;

		IGenericDAO<OrderBean, Integer> orderDao = null;
		ICartDAO cartDAO = null;
		
		Cartesio cart = (Cartesio) request.getSession().getAttribute("cart");
		if (cart == null) 
			cart = new Cartesio();
		
		
		if (username != null) {
			ds = (DataSource) getServletContext().getAttribute("DataSource"); 
			cartDAO = new CartDAO(ds);
			
		}
		
		
			
			
		switch(type) {
		
		
			case "Add":	cart.addProduct(productID, 1);
			
						if (username == null)
							break;

						try {
							cartDAO.doUpdateAddProduct(username, productID);
						} catch (SQLException e) {
							LOGGER.log(Level.SEVERE, e.getMessage());
						}
					
			
						break;
						
			case "Sub": cart.subtractProduct(productID, 1);
			
						if (username == null)
							break;
						
						try {
							
							if (cart.getProductQuantity(productID) <= 0)
								cartDAO.doUpdateRemoveProduct(username, productID);
							else 
								cartDAO.doUpdateSubtractProduct(username, productID);
						} catch (SQLException e) {
							LOGGER.log(Level.SEVERE, e.getMessage());
						}
						
						break;
						
			case "Clear":	cart.clear();
							if (username == null)
								break;
							try {
								cartDAO.doDelete(username);
							} catch (SQLException e) {
								LOGGER.log(Level.SEVERE, e.getMessage());
							}
							
							break;
							
			case "Checkout":	if (username == null) {
									response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									response.getWriter().write("You\'re currently not logged in");
									LOGGER.log(Level.WARNING, "Can\'t checkout if not logged");
									return;
									
								
								}
								
								if (cart.getTotalQuantity() <= 0) {
									LOGGER.log(Level.WARNING, "Can\'t order if cart empty");
									response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									response.getWriter().write("Tried purchase with empty cart");
									return;
									
								}
		
				
								orderDao = new OrderDAO(ds);
								OrderBean newOrder = new OrderBean();
								newOrder.setUser_username(username);
								newOrder.setCart(cart);
								newOrder.setOrder_date(new Timestamp(System.currentTimeMillis()));
								
								Collection<Integer> newProductsOwned = null;
								
								try {
									orderDao.doSave(newOrder);
									cart.clear();
									cartDAO.doDelete(username);
									newProductsOwned = new UserDAO(ds).doRetrieveByKey(username).getOwnedProducts();
								} catch (SQLException e) {
									LOGGER.log(Level.SEVERE, e.getMessage());
								}
								
								request.getSession().setAttribute("productsOwned", newProductsOwned);
								
								
								
								break;
								
							
														
			default: LOGGER.log(Level.SEVERE, "Ajax call failed");
					break;
		
		}
		
		request.getSession().setAttribute("cart", cart);
		
		// Packing the response up with JSON
		response.setContentType("application/json");
		
		CartResponse cartResp = new CartResponse(cart.getTotalQuantity(),
				productID == 0 ? 0 : cart.getProductQuantity(productID));
		String jCartResp = new Gson().toJson(cartResp);
		response.getWriter().write(jCartResp);
		
		
		
	}
			

	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
	
	protected UserBean buildUser(String username, UserDAO userDAO) {
		
		UserBean user = null;
		
		try {
			user = userDAO.doRetrieveByKey(username);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		
		return user;
	}
	
	private static class CartResponse {
		
		private int cartQuantity;
		private int productQuantity;
		
		public CartResponse(int cartQuantity, int productQuantity) {
			this.cartQuantity = cartQuantity;
			this.productQuantity = productQuantity;
		}

		@SuppressWarnings("unused")
		public int getCartQuantity() {
			return cartQuantity;
		}

		@SuppressWarnings("unused")
		public int getProductQuantity() {
			return productQuantity;
		}
		
		
	}

}
