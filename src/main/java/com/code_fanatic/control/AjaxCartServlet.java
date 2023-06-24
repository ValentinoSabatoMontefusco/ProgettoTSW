package com.code_fanatic.control;

import java.io.IOException;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;

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

    public AjaxCartServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		System.out.println("Cartservlet avviata?"); 
		
		String type = request.getParameter("type");
		int ProductID = Integer.parseInt(request.getParameter("prodID"));
		String username = (String) request.getSession().getAttribute("username");		// Superflous
		
		UserBean user = null;
		DataSource ds = null;
		IGenericDAO<UserBean, String> userDao = null;
		IGenericDAO<OrderBean, Integer> orderDao = null;
		ICartDAO cartDAO = null;
		
		Cartesio cart = (Cartesio) request.getSession().getAttribute("cart");
		if (cart == null) 
			cart = new Cartesio();
		
		
		if (username != null) {
			ds = (DataSource) getServletContext().getAttribute("DataSource"); 
			userDao = new UserDAO(ds);
			user = buildUser(username, (UserDAO) userDao);
			cartDAO = new CartDAO(ds);
			
		}
		
		
			
			
		switch(type) {
		
		
			case "Add":	cart.addProduct(ProductID, 1);
						if (username != null)
							try {
								cartDAO.doUpdateAddProduct(username, ProductID);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						
			
						break;
						
			case "Sub": cart.subtractProduct(ProductID, 1);
						if (username!= null)
							try {
								
								if (cart.getProductQuantity(ProductID) <= 0)
									cartDAO.doUpdateRemoveProduct(username, ProductID);
								else 
									cartDAO.doUpdateSubtractProduct(username, ProductID);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						
						break;
						
			case "Clear":	cart.clear();
							if (username!=null) 
								try {
									
									cartDAO.doDelete(username);
								} catch (SQLException e) {
									e.printStackTrace();
								}
							
							break;
							
			case "Checkout": if (username!= null) {
								if (cart.getTotalQuantity() > 0) {
									orderDao = new OrderDAO(ds);
									OrderBean newOrder = new OrderBean();
									newOrder.setUsername(username);
									newOrder.setCart(cart);
									newOrder.setOrder_date(new Timestamp(System.currentTimeMillis()));
									
									Collection<Integer> newProductsOwned = null;
									
									try {
										orderDao.doSave(newOrder);
										cart.clear();
										cartDAO.doDelete(username);
										newProductsOwned = new UserDAO(ds).doRetrieveByKey(username).getOwnedProducts();
									} catch (SQLException e) {
										e.printStackTrace();
									}
									
									request.getSession().setAttribute("productsOwned", newProductsOwned);
									
									
									
									break;
								} else {
									System.err.println("Can't order if cart empty");
									response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									response.getWriter().write("Tried purchase with empty cart");
									return;
									
								}
			
							} else {
								
								System.err.println("Can't checkout if not logged");
							}
														
			default: System.out.println("Ajax call failed");
					break;
		
		}
		
		request.getSession().setAttribute("cart", cart);
		
		// Packing the response up with JSON
		response.setContentType("application/json");
		
		cartResponse cartResp = new cartResponse(cart.getTotalQuantity(), cart.getProductQuantity(ProductID));
		System.out.println("Expected JS output for cart = " + cartResp.getCartQuantity() );
		String JcartResp = new Gson().toJson(cartResp);
		response.getWriter().write(JcartResp);
		
		
		
	}
			

	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
	
	protected UserBean buildUser(String username, UserDAO userDAO) {
		
		UserBean user = null;
		
		try {
			user = userDAO.doRetrieveByKey(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	private static class cartResponse {
		
		private int cartQuantity;
		private int productQuantity;
		
		public cartResponse(int cartQuantity, int productQuantity) {
			this.cartQuantity = cartQuantity;
			this.productQuantity = productQuantity;
		}

		public int getCartQuantity() {
			return cartQuantity;
		}

		public int getProductQuantity() {
			return productQuantity;
		}
		
		
	}

}
