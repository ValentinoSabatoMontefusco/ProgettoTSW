package com.code_fanatic.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.code_fanatic.control.admin.OrdersRecapServlet;
import com.code_fanatic.model.bean.OrderBean;
import com.code_fanatic.model.dao.IExtendedDAO;
import com.code_fanatic.model.dao.IOrderDAO;
import com.code_fanatic.model.dao.OrderDAO;


@WebServlet("/user/myOrders")
public class MyOrdersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(MyOrdersServlet.class.getName());

    public MyOrdersServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Order Servlet avviata");
		Collection<OrderBean> orders = null;
		
		String username = (String) request.getSession().getAttribute("username");
		
		if (username == null) 
			
			System.err.println("SessionToken assente in pagina illegale");
		else {
			
			IExtendedDAO<OrderBean, Integer> orderDAO = new OrderDAO((DataSource) request.getServletContext().getAttribute("DataSource"));
			
			try {
				orders = orderDAO.doRetrieveAllByUser(username);
			} catch (SQLException e) {
				
				LOGGER.log(Level.SEVERE, e.getMessage());
			}
		}
		
		request.setAttribute("orders", orders);
		
		request.getRequestDispatcher("myorders.jsp").forward(request, response);
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
