package com.code_fanatic.control;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
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

import org.apache.jasper.tagplugins.jstl.core.If;

import com.code_fanatic.control.admin.OrdersRecapServlet;
import com.code_fanatic.control.utils.SecurityUtils;
import com.code_fanatic.model.bean.CommentBean;
import com.code_fanatic.model.bean.IUserSpecific;
import com.code_fanatic.model.bean.OrderBean;
import com.code_fanatic.model.dao.CommentDAO;
import com.code_fanatic.model.dao.IExtendedDAO;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet(urlPatterns = {"/user/comment", "/admin/moderation"})
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(CommentServlet.class.getName());
       

    public CommentServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String type = request.getParameter("type");
		
		LOGGER.log(Level.FINE, "Comment Servlet avviata con type = " + type);

		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		ArrayList<String> errors = null;
		
		IExtendedDAO<CommentBean, Integer> commentDAO = new CommentDAO(ds);
		
		if (type == null)
			type = "Sort";
		
		switch(type) {
		
			case "Add": CommentBean newComment = new CommentBean();
						newComment.setProduct_id(Integer.parseInt(request.getParameter("product_id")));
						newComment.setUser_username(request.getParameter("user_username"));
						newComment.setContent(request.getParameter("content_input"));
						
						try {
							commentDAO.doSave(newComment);
						} catch (SQLException e) {
							LOGGER.log(Level.SEVERE, e.getMessage());
						}
						
						
						return;
			
			case "Delete": int comment_id = Integer.parseInt(request.getParameter("comment_id"));
			
							if (comment_id == 0) {
								LOGGER.log(Level.SEVERE, "Comment_id non valido");
								SecurityUtils.addError(errors, "Commento non valido");
								response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
								return;
							}
						
							
							String role = (String) request.getSession().getAttribute("role");
							String username = (String) request.getSession().getAttribute("username");
							Boolean authorized = false;
							
							if (role == null || username == null) {
								LOGGER.log(Level.SEVERE, "Utente non autenticato");
								SecurityUtils.addError(errors, "Utente non autenticato");
								response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
								
							}
							
							try {
	
								commentDeletion(role, username, comment_id, commentDAO);
								
								
							} catch (SQLException e) {
								LOGGER.log(Level.SEVERE, e.getMessage());
							}
	
							
							return;
							
				
			case "Sort": Collection<CommentBean> comments = sortRoutine(commentDAO, request, response);
			
						request.setAttribute("comments", comments);
						request.getRequestDispatcher("moderation.jsp").forward(request, response);
						break;
						default: break;
		}
		
		
	
		
	}

	private void commentDeletion(String role, String username, int comment_id, IExtendedDAO<CommentBean, Integer> commentDAO) throws SQLException {
		
		Boolean authorized = false;
		if (role.equals("user")) {
			
			if (username.equals(commentDAO.doRetrieveByKey(comment_id).getUser_username()))
				authorized = true;
		} else if (role.equals("admin"))
			authorized = true;
		
		if (authorized) {
			
			if (commentDAO.doDelete(comment_id)) {
				
				LOGGER.log(Level.INFO, "Rimozione avvenuta con successo");
			} else {
				LOGGER.log(Level.SEVERE, "Rimozione commento fallimentare");

			}
		
		}
	}
	
	public synchronized Collection<CommentBean> sortRoutine(IExtendedDAO<CommentBean, Integer> ExtendedDAO, HttpServletRequest request, HttpServletResponse response) {
		
		String fromDate = null, toDate = null;
		String allItems = request.getParameter("all_items");
		if (allItems == null) {
			
			fromDate = request.getParameter("from_date");
			toDate = request.getParameter("to_date");
			
		}
		
		String order_by = request.getParameter("order_by");
		String sort_order = request.getParameter("sorting_order");
		
		String sort;
		
		if (order_by != null && order_by.equals("users"))
			sort = "user_username";
		else
			sort = "create_time";
		
		if(sort_order != null && sort_order.equals("asc"))
			sort = sort.concat(" ASC");
		else 
			sort = sort.concat(" DESC");
		
		Collection<CommentBean> items = null;
		try {
			if (fromDate == null || toDate == null) {
				items = ExtendedDAO.doRetrieveAll(sort);
				
			
			} else {
				Timestamp fromD = Timestamp.valueOf(fromDate.replace("T", " "));
				Timestamp toD = Timestamp.valueOf(toDate.replace("T", " "));
				items = ExtendedDAO.doRetrieveAll(fromD, toD, sort);
			}
		} catch (SQLException e) {

			LOGGER.log(Level.SEVERE, e.getMessage());
		} 
		
		return items;
	}
}
