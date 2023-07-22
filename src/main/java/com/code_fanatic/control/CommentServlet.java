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

import org.apache.jasper.tagplugins.jstl.core.If;

import com.code_fanatic.model.bean.CommentBean;
import com.code_fanatic.model.bean.OrderBean;
import com.code_fanatic.model.dao.CommentDAO;
import com.code_fanatic.model.dao.ICommentDAO;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet(urlPatterns = {"/user/comment", "/admin/moderation"})
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public CommentServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String type = request.getParameter("type");
		
		System.out.println("Comment Servlet avviata con type = " + type);
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		
		ICommentDAO commentDAO = new CommentDAO(ds);
		
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
							e.printStackTrace();
						}
						
						
						//request.getRequestDispatcher("product?type=retrieve").forward(request, response);
						return;
			
			case "Delete": int comment_id = Integer.parseInt(request.getParameter("comment_id"));
							if (comment_id != 0) {
								
								String role = (String) request.getSession().getAttribute("role");
								Boolean authorized = false;
							try {
								if (role != null) {
									
									if (role.equals("user")) {
										
										String username = (String) request.getSession().getAttribute("username");
										if (username != null)
											if (username.equals(commentDAO.doRetrieveByKey(comment_id).getUser_username()))
												authorized = true;
									} else if (role.equals("admin"))
										authorized = true;
									
									if (authorized) {
										
										if (commentDAO.doDelete(comment_id)) {
											
											System.out.println("Rimozione avvenuta con successo");
										} else {
											System.out.println("Rimozione fallimentare");
										}
									
									}
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
							} else {
								
								System.err.println("Comment_id non valido");
							}
							
							return;
							
				
			case "Sort": 	String fromDate = null, toDate = null;
							String allComments = request.getParameter("all_items");
							if (allComments == null) {
								
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
							
							Collection<CommentBean> comments = null;
							try {
								if (fromDate == null || toDate == null) {
									comments = commentDAO.doRetrieveAll(sort);
									
									System.err.println(String.format("Calling doRetrieveAll with %s", sort));
								} else {
									Timestamp fromD = Timestamp.valueOf(fromDate.replace("T", " "));
									Timestamp toD = Timestamp.valueOf(toDate.replace("T", " "));
									comments = commentDAO.doRetrieveAll(fromD, toD, sort);
								}
							} catch (SQLException e) {

								e.printStackTrace();
							} 
							
							request.setAttribute("comments", comments);
							request.getRequestDispatcher("moderation.jsp").forward(request, response);
							return;

			
						default: break;
		}
		
		
		// TEMP
//		StringBuilder originalURL = new StringBuilder(request.getRequestURL());
//		if (request.getQueryString() != null)
//			originalURL.append("?").append(request.getQueryString());
//		
//		System.out.println("Request URL: " + request.getRequestURL() + ", QueryStringz: " + 
//		(request.getQueryString() != null ? request.getQueryString() : "none"));
		
		
		
	}

}
