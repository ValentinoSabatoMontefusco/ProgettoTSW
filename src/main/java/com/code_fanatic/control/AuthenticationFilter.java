package com.code_fanatic.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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
@WebFilter(urlPatterns = {"/user/*", "/admin/*"})
public class AuthenticationFilter extends HttpFilter implements Filter {
       

	private static final long serialVersionUID = 1L;

    public AuthenticationFilter() {
        super();
        
    }

	public void destroy() {
		//NO OVERRIDE
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		// place your code here
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String reqURI = httpRequest.getRequestURI();
		String roleToken = (String) httpRequest.getSession().getAttribute("role"); 
		Collection<String> errors = new ArrayList<String>();
		
		if (roleToken == null) {
			errors.add("You must log in to access this page");
			request.setAttribute("errors", errors);
			RequestDispatcher view = httpRequest.getRequestDispatcher("/access.jsp?type=login");
			view.forward(httpRequest, httpResponse);
		} else if (reqURI.contains("admin") && (!roleToken.equals("admin"))) {
				
				errors.add("You're unauthorized to see this page");
				RequestDispatcher view = httpRequest.getRequestDispatcher("/access.jsp?type=login");
				view.forward(httpRequest, httpResponse);
				
		} else {
			System.out.println("Filter ok");
			chain.doFilter(request, response);
		// pass the request along the filter chain
		}
		
	}


	public void init(FilterConfig fConfig) throws ServletException {
		//NO OVERRIDE
	}

}
