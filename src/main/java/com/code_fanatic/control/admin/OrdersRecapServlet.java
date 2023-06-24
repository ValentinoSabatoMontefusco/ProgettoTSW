package com.code_fanatic.control.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.code_fanatic.model.bean.OrderBean;
import com.code_fanatic.model.dao.IOrderDAO;
import com.code_fanatic.model.dao.OrderDAO;
import com.google.gson.Gson;


@WebServlet("/admin/ordersRecap")
public class OrdersRecapServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public OrdersRecapServlet() {
        super();

    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
		
	}
	
	// JSON Responsive approach
//	String jSONOrderResponse = new Gson().toJson(orders.toArray());
//	System.out.println(jSONOrderResponse);
//	
//	response.getWriter().write(jSONOrderResponse);
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		IOrderDAO<OrderBean, Integer> orderDAO = new OrderDAO(ds);
		
		//String sort = (String) request.getParameter("sort");
		
		String fromDate = null, toDate = null;
		String allOrders = request.getParameter("all_items");
		if (allOrders == null) {
			
			fromDate = request.getParameter("from_date");
			toDate = request.getParameter("to_date");
			
		}
		
		String order_by = request.getParameter("order_by");
		String sort_order = request.getParameter("sorting_order");
		
		String sort;
		
		if (order_by != null && order_by.equals("users"))
			sort = "user_username";
		else
			sort = "order_date";
		
		if(sort_order != null && sort_order.equals("asc"))
			sort = sort.concat(" ASC");
		else 
			sort = sort.concat(" DESC");
		
		
		
		Collection<OrderBean> orders = null;
		try {
			if (fromDate == null || toDate == null) {
				orders = orderDAO.doRetrieveAll(sort);
				
				System.err.println(String.format("Calling doRetrieveAll with %s", sort));
			} else {
				Timestamp fromD = Timestamp.valueOf(fromDate.replace("T", " "));
				Timestamp toD = Timestamp.valueOf(toDate.replace("T", " "));
				orders = orderDAO.doRetrieveAll(fromD, toD, sort);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} 
		
		request.setAttribute("view", "recap");
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("ordersRecap.jsp").forward(request, response);
		
	}

}