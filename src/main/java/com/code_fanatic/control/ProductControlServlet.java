package com.code_fanatic.control;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;



import com.code_fanatic.model.MainContext;
import com.code_fanatic.model.bean.CourseBean;
import com.code_fanatic.model.bean.MerchBean;
import com.code_fanatic.model.bean.ProductBean;
import com.code_fanatic.model.dao.CourseDAO;
import com.code_fanatic.model.dao.IGenericDAO;
import com.code_fanatic.model.dao.MerchDAO;
import com.code_fanatic.model.dao.ProductDAO;


@WebServlet(urlPatterns = {"/product", "/admin/productEdit", "/admin/productCreate"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024*1024*10, maxRequestSize = 1024*1024*100)
public class ProductControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ProductControlServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		String type = request.getParameter("type");
		System.err.println(request.getParameter("name"));
		
		ArrayList<String> errors = new ArrayList<String>();
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		IGenericDAO<ProductBean, Integer> productDao = new ProductDAO(ds);
		ProductBean product = null;
		RequestDispatcher reqDisp = null;
		
		switch(type) {
		
			/*********************************************** 
			 ************** RETRIEVE PRODUCT ***************
			 ***********************************************/
		
			case "retrieve": int productID = Integer.parseInt(request.getParameter("product_id"));
			
			if (productID == -1) {
				product = new ProductBean();
				product.setName("New Product");
				product.setPrice(9.99f);
				product.setDescription("Insert new description");
				
			} else {
				try {
					product = productDao.doRetrieveByKey(productID);
					switch (product.getType()) {
					
					case "Course": product = (CourseBean) new CourseDAO(ds).doRetrieveByKey(product.getId());
									break;
									
					case "Merchandise": product = (MerchBean) new MerchDAO(ds).doRetrieveByKey(product.getId());
										break;
									
					default: break; 
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			
			request.setAttribute("product", product);
			if (request.getRequestURI().contains("productCreate")) {
				
				request.setAttribute("isEdit", false);
				reqDisp = request.getRequestDispatcher("productEdit.jsp");
				
			} else if (request.getRequestURI().contains("productEdit")) {
				request.setAttribute("isEdit", true);
				reqDisp = request.getRequestDispatcher("productEdit.jsp");
			}	else 
				reqDisp = request.getRequestDispatcher("product.jsp");
			
			break;
			
			
			/*********************************************** 
			 **************** EDIT PRODUCT *****************
			 ***********************************************/
			
			case "edit": String newName = request.getParameter("name_input");
			String newDescription = request.getParameter("description_input");
			float newPrice = Float.parseFloat(request.getParameter("price_input"));
			int productId = Integer.parseInt(request.getParameter("product_id"));
			String newType = request.getParameter("type_input");
			String nameChanged = request.getParameter("oldname_input");
			
			ProductBean newProduct = new ProductBean();
			newProduct.setName(newName);
			newProduct.setDescription(newDescription);
			newProduct.setPrice(newPrice);
			newProduct.setId(productId);
			newProduct.setType(newType);
			
			
			
			switch (newProduct.getType()) {
			
				case "Course": CourseBean createdCourse = new CourseBean(newProduct);
								createdCourse.setLesson_count(Integer.parseInt(request.getParameter("lescount_input")));
				try {
					new CourseDAO(ds).doSave(createdCourse);
					imageSave(request, newProduct.getName(), nameChanged);
				} catch (SQLIntegrityConstraintViolationException e) {
					
					errors.add("Hai provato a inserire un prodotto già presente in catalogo");
					
				} catch (SQLException e1) {
	
					e1.printStackTrace();
				}
							break;
							
				case "Merchandise": MerchBean createdMerch = new MerchBean(newProduct);
									createdMerch.setAmount(Integer.parseInt(request.getParameter("amount_input")));
				try {
					new MerchDAO(ds).doSave(createdMerch);
					imageSave(request, newProduct.getName(), nameChanged);
				} catch (SQLIntegrityConstraintViolationException e) {
					
					errors.add("Hai provato a inserire un prodotto già presente in catalogo");
					
				} catch (SQLException e1) {
				
					
					e1.printStackTrace();
				}
								break;
							
				default: break;
			
			
		}

			
	
			//System.out.println("Request URI: " + request.getRequestURI());
			MainContext.updateAttributes(getServletContext());
			reqDisp = request.getRequestDispatcher("../home.jsp");
			
			break;
			
			/*********************************************** 
			 *************** CREATE PRODUCT ****************
			 ***********************************************/
			
			
			case "create": 
				
				ProductBean createdProduct = new ProductBean();
				createdProduct.setName(request.getParameter("name_input"));
				createdProduct.setDescription(request.getParameter("description_input"));
				createdProduct.setPrice(Float.parseFloat(request.getParameter("price_input")));
				createdProduct.setType(request.getParameter("type_input"));
				
				switch (createdProduct.getType()) {
				
					case "Course": CourseBean createdCourse = new CourseBean(createdProduct);
									createdCourse.setLesson_count(Integer.parseInt(request.getParameter("lescount_input")));
					try {
						new CourseDAO(ds).doSave(createdCourse);
						imageSave(request, createdProduct.getName(), null);
					} catch (SQLIntegrityConstraintViolationException e) {
						
						errors.add("Hai provato a inserire un prodotto già presente in catalogo");
						
					} catch (SQLException e1) {

						e1.printStackTrace();
					}
								break;
								
					case "Merchandise": MerchBean createdMerch = new MerchBean(createdProduct);
										createdMerch.setAmount(Integer.parseInt(request.getParameter("amount_input")));
					try {
						new MerchDAO(ds).doSave(createdMerch);
						imageSave(request, createdProduct.getName(), null);
					} catch (SQLIntegrityConstraintViolationException e) {
						
						errors.add("Hai provato a inserire un prodotto già presente in catalogo");
						
					} catch (SQLException e1) {
					
						
						e1.printStackTrace();
					}
									break;
								
					default: break;
					
					
				}
				
				MainContext.updateAttributes(getServletContext());
				reqDisp = request.getRequestDispatcher("../home.jsp");
				
				break;
			
			/*********************************************** 
			 *************** DELETE PRODUCT ****************
			 ***********************************************/
			
			case "delete": int prodId = Integer.parseInt(request.getParameter("product_id"));
			
			try {
				productDao.doDelete(prodId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			// NOTIFY CONTEXT ATTRIBUTE
			
			MainContext.updateAttributes(getServletContext());
			reqDisp = request.getRequestDispatcher("../home.jsp");
			
			
			break;
		
		default: break;
		
		
		}
		
		System.err.println("ProductControl servlet avviata");
	
		reqDisp.forward(request, response);
		
	}
	
	private synchronized void imageSave(HttpServletRequest request, String filename, String oldname)  {
		
		String save_path = request.getServletContext().getRealPath("") + "images\\logos\\";
		
		File fileSaveDir = new File(save_path);
		if (!fileSaveDir.exists())
			fileSaveDir.mkdir();
		try {
			Part part = request.getPart("logo_input");
			
			if (part != null && part.getSize() > 0) {
					
					
					filename = filename.toLowerCase().replaceAll("\\s",  "") + ".png";//part.getSubmittedFileName();
					Path path = Paths.get(save_path + File.separator + filename);
					
					if (filename != null && !filename.equals("")) {
						
						int i = 0;
						Path newPath = path;
						while(Files.exists(newPath)) {
							i++;
							newPath = updatePath(path, i);
						}
						
						if (i != 0)
							Files.move(path, newPath, StandardCopyOption.REPLACE_EXISTING);
							
						part.write(save_path + File.separator + filename);
						
						System.out.println("Dovrebbesi aver salvato in: " + save_path + File.separator + filename);
						
						
					}
				} else if (oldname != null) {
					
					File oldFile = new File(save_path + oldname);
					
					if (oldFile.exists()) 
						oldFile.renameTo(new File(save_path + filename));
					
					
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	public Path updatePath(Path oldPath, int counter) {
		
		String uri = oldPath.toString();
		StringBuilder stringBuilder = new StringBuilder(uri);
		int index = uri.lastIndexOf(".");
		uri = stringBuilder.insert(index, "(" + counter + ")").toString();
		
		System.out.println("updatePath says: new Uri = " + uri);
		
		return Paths.get(uri);
		
	}
	
	}

