package com.code_fanatic.control;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@WebFilter("/user/*")
public class AuthenticationFilter extends HttpFilter implements Filter {
       

	private static final long serialVersionUID = 1L;

    public AuthenticationFilter() {
        super();

    }

	public void destroy() {

	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		// place your code here
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response; 
		
		if (httpRequest.getSession().getAttribute("role") == null) {
			RequestDispatcher view = httpRequest.getRequestDispatcher("/access.jsp?type=login");
			view.forward(httpRequest, httpResponse);
		} else {
			System.out.println("Filter ok");
			chain.doFilter(request, response);
		// pass the request along the filter chain
		}
		
	}


	public void init(FilterConfig fConfig) throws ServletException {

	}

}
