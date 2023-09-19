package com.code_fanatic.control;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


import com.code_fanatic.control.utils.SecurityUtils;
import com.code_fanatic.model.bean.Cartesio;
import com.code_fanatic.model.bean.UserBean;
import com.code_fanatic.model.dao.CartDAO;
import com.code_fanatic.model.dao.IGenericDAO;
import com.code_fanatic.model.dao.UserDAO;


@WebServlet ("/access")
public class AccessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(AccessServlet.class.getName());
	private static final String ERROR_STRING = "errors";
    public AccessServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
	
		
		//ad hoc class with static methods to provide Security Management
		password = SecurityUtils.toHash(password);	

		
		UserBean newUser = new UserBean();
		
		newUser.setUsername(username);
		newUser.setPassword(password);
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		boolean responseForwarded = false;
		RequestDispatcher view = null;
	
		
		IGenericDAO<UserBean, String> userDAO = new UserDAO(ds);
		
		if (request.getParameter("action").equals("register")) {
			
			responseForwarded = tryRegisterUser(newUser, userDAO, request, response);
			
			
		} else if (request.getParameter("action").equals("login")) {
			
			responseForwarded = tryLogUser(newUser, userDAO, request, response, password, ds);
		}
		
		if (!responseForwarded) {
			view = request.getRequestDispatcher("/home.jsp");
			view.forward(request, response);
		}
			
	}
	
	private synchronized boolean tryRegisterUser(UserBean newUser, IGenericDAO<UserBean, String> userDAO,
		 HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			userDAO.doSave(newUser);
			LOGGER.log(Level.FINE, "Registrazione effettuata con successo");
			
			
		} catch (SQLIntegrityConstraintViolationException e) {
			
			List<String> errors = new ArrayList<>(); 
			 errors = SecurityUtils.addError(errors, "The username '" + newUser.getUsername() + "' is already in use");
			
		
			LOGGER.log(Level.SEVERE, e.getMessage());
			
			request.setAttribute(ERROR_STRING,  errors);
			request.getRequestDispatcher("access.jsp?type=register").forward(request, response);
			return true;
			
			
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		
		}
		
		return false;
	}
	
	private synchronized boolean tryLogUser(UserBean newUser, IGenericDAO<UserBean, String> userDAO, 
			 HttpServletRequest request, HttpServletResponse response, String password, DataSource ds) throws ServletException, IOException {
		
		try {
			
			if ((newUser = userDAO.doRetrieveByKey(newUser.getUsername())) == null) {
					
					
					List<String> errors = new ArrayList<>(); 
					 errors = SecurityUtils.addError(errors, "L'username inserito non esiste");
					request.setAttribute(ERROR_STRING,  errors);
					request.getRequestDispatcher("access.jsp?type=login").forward(request, response);
					return true;
					
					// GESTIRE ERRORE
			}
			if (!newUser.getPassword().equals(password)) {
				List<String> errors = new ArrayList<>(); 
				 errors = SecurityUtils.addError(errors, "La password inserita non è corretta");
				
				request.setAttribute(ERROR_STRING,  errors);
				request.getRequestDispatcher("access.jsp?type=login").forward(request, response);
				return true;
			}
			
		

			request.getSession(true).setAttribute("username", newUser.getUsername());
			request.getSession(true).setAttribute("role", newUser.getRole());
			request.getSession(true).setAttribute("productsOwned", newUser.getOwnedProducts());
			
			// Handling guest Cart; following GPT hint of destroying guest cart 
			
			Cartesio cart = null;
				cart = (new CartDAO(ds).doRetrieveByKey(newUser.getUsername()));
				
				
			if (cart.getTotalQuantity() == 0) { 
				cart = (Cartesio) request.getSession().getAttribute("cart");
				if (cart != null) {
					new CartDAO(ds).doSave(cart, newUser.getUsername());
				}
			}

			if (cart == null)
				cart = new Cartesio();
			
			
			request.getSession().setAttribute("cart", cart);
				
				
		} catch (SQLException e) {
			
			LOGGER.log(Level.SEVERE, e.getMessage());

		}
		
		return false;
	}

}
