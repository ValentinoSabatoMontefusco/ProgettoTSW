package com.code_fanatic.control;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
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
import com.code_fanatic.control.utils.SecurityUtils;
import com.code_fanatic.model.bean.Cartesio;
import com.code_fanatic.model.bean.UserBean;
import com.code_fanatic.model.dao.CartDAO;
import com.code_fanatic.model.dao.IGenericDAO;
import com.code_fanatic.model.dao.UserDAO;


@WebServlet ("/access")
public class AccessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(OrdersRecapServlet.class.getName());
    public AccessServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		ArrayList<String> errors = new ArrayList<String>();
		
		password = SecurityUtils.toHash(password);	//ad hoc class with static methods to provide Security Management

		
		UserBean newUser = new UserBean();
		
		newUser.setUsername(username);
		newUser.setPassword(password);
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	
		
		IGenericDAO<UserBean, String> userDAO = new UserDAO(ds);
		
		if (request.getParameter("action").equals("register")) {
			try {
				userDAO.doSave(newUser);
				System.out.print("Registrazione effettuata, credo");
				
				
			} catch (SQLIntegrityConstraintViolationException e) {
				
				errors.add("The username '" + username + "' is already in use");
				
			
				LOGGER.log(Level.SEVERE, e.getMessage());
				
				request.setAttribute("errors",  errors);
				request.getRequestDispatcher("access.jsp?type=register").forward(request, response);
				return;
				
				
			} catch (SQLException e) {
				//LOGGER.log(Level.SEVERE, e.getMessage());
				System.out.println(e.getMessage());
			}
		} else if (request.getParameter("action").equals("login")) {
			try {
				
				if ((newUser = userDAO.doRetrieveByKey(newUser.getUsername())) != null) {
				
					if (newUser.getPassword().equals(password)){
						
						//request.getSession(true).removeAttribute("user");
						request.getSession(true).setAttribute("username", newUser.getUsername());
						request.getSession(true).setAttribute("role", newUser.getRole());
						request.getSession(true).setAttribute("productsOwned", newUser.getOwnedProducts());
						
						// Handling guest Cart; following GPT hint of destroying guest cart 
						
						Cartesio cart = null;
						try {
							cart = (new CartDAO(ds).doRetrieveByKey(username));
							if (cart.getTotalQuantity() > 0)
								System.out.println("Il carrello dell'utente " + username + " ha stato riconosciuto e contiene " + cart.getTotalQuantity() + " articoli");
							else { 
								cart = (Cartesio) request.getSession().getAttribute("cart");
								if (cart != null) {
									new CartDAO(ds).doSave(cart, newUser.getUsername());
								}
							}
						} catch (SQLException e) {
						
							LOGGER.log(Level.SEVERE, e.getMessage());
						}
						if (cart == null)
							cart = new Cartesio();
						
						
						request.getSession().setAttribute("cart", cart);
							
					
						System.out.print("Corrispondenza trovata");
					} else {
						errors.add("La password inserita non è corretta");
					
						request.setAttribute("errors",  errors);
						request.getRequestDispatcher("access.jsp?type=login").forward(request, response);
						return;
				}} else {
					
					errors.add("L'username inserito non esiste");
					request.setAttribute("errors",  errors);
					request.getRequestDispatcher("access.jsp?type=login").forward(request, response);
					return;
					
					// GESTIRE ERRORE
				}
			} catch (SQLException e) {
				
				LOGGER.log(Level.SEVERE, e.getMessage());
			}
		}
		

		System.err.println("Fine access raggiunto");
		
		RequestDispatcher view = request.getRequestDispatcher("/home.jsp");
		view.forward(request, response);
	}

}
