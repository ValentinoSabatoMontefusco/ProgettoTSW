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
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import com.code_fanatic.control.admin.OrdersRecapServlet;
import com.code_fanatic.model.MainContext;
import com.code_fanatic.model.bean.CommentBean;
import com.code_fanatic.model.bean.CourseBean;
import com.code_fanatic.model.bean.MerchBean;
import com.code_fanatic.model.bean.ProductBean;
import com.code_fanatic.model.dao.CommentDAO;
import com.code_fanatic.model.dao.CourseDAO;
import com.code_fanatic.model.dao.IExtendedDAO;
import com.code_fanatic.model.dao.IGenericDAO;
import com.code_fanatic.model.dao.MerchDAO;
import com.code_fanatic.model.dao.ProductDAO;


@WebServlet(urlPatterns = {"/product", "/admin/productEdit", "/admin/productCreate"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024*1024*10, maxRequestSize = 1024*1024*100)
public class ProductControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(ProductControlServlet.class.getName());
    private static final String PID_STRING = "product_id";
    private static final String TCOURSE_STRING = "Course";
    private static final String TMERC_STRING = "Merchandise";
    private static final String INTEGRITY_ERROR = "Hai provato a inserire un prodotto già presente in catalogo";
    private static final String HOME_PATH = "../home.jsp";
    
    public ProductControlServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		String type = request.getParameter("type");
		
		ArrayList<String> errors = new ArrayList<String>();
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		IGenericDAO<ProductBean, Integer> productDao = new ProductDAO(ds);
		ProductBean product = null;
		RequestDispatcher reqDisp = null;
		
		switch(type) {
		
			/*********************************************** 
			 ************** RETRIEVE PRODUCT ***************
			 ***********************************************/
		
			case "retrieve": int productID = Integer.parseInt(request.getParameter(PID_STRING));
			
			if (productID == -1) {
				product = new ProductBean();
				product.setName("New Product");
				product.setPrice(9.99f);
				product.setDescription("Insert new description");
				
			} else {
				try {
					product = productDao.doRetrieveByKey(productID);
					switch (product.getType()) {
					
					case TCOURSE_STRING: product = (CourseBean) new CourseDAO(ds).doRetrieveByKey(product.getId());
									break;
									
					case TMERC_STRING: product = (MerchBean) new MerchDAO(ds).doRetrieveByKey(product.getId());
										break;
									
					default: break; 
					}
				} catch (SQLException e) {
					LOGGER.log(Level.SEVERE, e.getMessage());
				}
			}
			
			
			request.setAttribute("product", product);
				
			reqDisp = routeRequest(request, response, ds, productID);
			
			break;
			
			
			/*********************************************** 
			 **************** EDIT PRODUCT *****************
			 ***********************************************/
			
			case "edit": String newName = request.getParameter("name_input");
			String newDescription = request.getParameter("description_input");
			float newPrice = Float.parseFloat(request.getParameter("price_input"));
			int productId = Integer.parseInt(request.getParameter(PID_STRING));
			String newType = request.getParameter("type_input");
			String nameChanged = request.getParameter("oldname_input");
			
			ProductBean newProduct = new ProductBean();
			newProduct.setName(newName);
			newProduct.setDescription(newDescription);
			newProduct.setPrice(newPrice);
			newProduct.setId(productId);
			newProduct.setType(newType);
			
			
			
			switch (newProduct.getType()) {
			
				case TCOURSE_STRING: CourseBean createdCourse = new CourseBean(newProduct);
								createdCourse.setLesson_count(Integer.parseInt(request.getParameter("lescount_input")));
				try {
					new CourseDAO(ds).doSave(createdCourse);
					imageSave(request, newProduct.getName(), nameChanged);
				} catch (SQLIntegrityConstraintViolationException e) {
					
					errors.add(INTEGRITY_ERROR);
					
				} catch (SQLException e1) {
	
					LOGGER.log(Level.SEVERE, e1.getMessage());
				}
							break;
							
				case TMERC_STRING: MerchBean createdMerch = new MerchBean(newProduct);
									createdMerch.setAmount(Integer.parseInt(request.getParameter("amount_input")));
				try {
					new MerchDAO(ds).doSave(createdMerch);
					imageSave(request, newProduct.getName(), nameChanged);
				} catch (SQLIntegrityConstraintViolationException e) {
					
					errors.add(INTEGRITY_ERROR);
					
				} catch (SQLException e1) {
				
					
					LOGGER.log(Level.SEVERE, e1.getMessage());
				}
								break;
							
				default: break;
			
			
		}

			
	

			MainContext.updateAttributes(getServletContext());
			reqDisp = request.getRequestDispatcher(HOME_PATH);
			
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
				
					case TCOURSE_STRING: CourseBean createdCourse = new CourseBean(createdProduct);
									createdCourse.setLesson_count(Integer.parseInt(request.getParameter("lescount_input")));
					try {
						new CourseDAO(ds).doSave(createdCourse);
						imageSave(request, createdProduct.getName(), null);
					} catch (SQLIntegrityConstraintViolationException e) {
						
						errors.add(INTEGRITY_ERROR);
						
					} catch (SQLException e1) {

						LOGGER.log(Level.SEVERE, e1.getMessage());
					}
								break;
								
					case TMERC_STRING: MerchBean createdMerch = new MerchBean(createdProduct);
										createdMerch.setAmount(Integer.parseInt(request.getParameter("amount_input")));
					try {
						new MerchDAO(ds).doSave(createdMerch);
						imageSave(request, createdProduct.getName(), null);
					} catch (SQLIntegrityConstraintViolationException e) {
						
						errors.add(INTEGRITY_ERROR);
						
					} catch (SQLException e1) {
					
						
						LOGGER.log(Level.SEVERE, e1.getMessage());
					}
									break;
								
					default: break;
					
					
				}
				
				MainContext.updateAttributes(getServletContext());
				reqDisp = request.getRequestDispatcher(HOME_PATH);
				
				break;
			
			/*********************************************** 
			 *************** DELETE PRODUCT ****************
			 ***********************************************/
			
			case "delete": int prodId = Integer.parseInt(request.getParameter(PID_STRING));
			
			try {
				productDao.doDelete(prodId);
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, e.getMessage());
			}
			
			// NOTIFY CONTEXT ATTRIBUTE
			
			MainContext.updateAttributes(getServletContext());
			reqDisp = request.getRequestDispatcher(HOME_PATH);
			
			
			break;
		
		default: break;
		
		
		}
		

	
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
					
					
					bindImageFile(filename, save_path, part);
						
					
					
			}  else if (oldname != null) {
				
				File oldFile = new File(save_path + oldname);
				
				if (oldFile.exists()) 
					if(oldFile.renameTo(new File(save_path + filename)))
						LOGGER.log(Level.INFO, "Immagine rinominata");
					else {
						LOGGER.log(Level.WARNING, "Manipolazione immagine fallita");
					}
					
				}
			
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		} catch (ServletException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}
	
	private void bindImageFile(String filename, String save_path, Part part) throws IOException {
		
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
				
			part.write(save_path + filename);
			
			LOGGER.log(Level.INFO, "Dovrebbesi aver salvato in: " + save_path + filename);
		
		}
	}
	
	
	public Path updatePath(Path oldPath, int counter) {
		
		String uri = oldPath.toString();
		StringBuilder stringBuilder = new StringBuilder(uri);
		int index = uri.lastIndexOf(".");
		uri = stringBuilder.insert(index, "(" + counter + ")").toString();
		
		LOGGER.log(Level.FINE, "updatePath says: new Uri = " + uri);

		
		return Paths.get(uri);
		
	}
	
	private RequestDispatcher routeRequest(HttpServletRequest request, HttpServletResponse response, DataSource ds, int productID) {
		
		RequestDispatcher reqDisp = null;
		if (request.getRequestURI().contains("productCreate")) {
			
			request.setAttribute("isEdit", false);
			reqDisp = request.getRequestDispatcher("productEdit.jsp");
			
		} else if (request.getRequestURI().contains("productEdit")) {
			request.setAttribute("isEdit", true);
			reqDisp = request.getRequestDispatcher("productEdit.jsp");
		}	else {
			
			// RECUPERO COMMENTI
			
			IExtendedDAO<CommentBean, Integer> commentDAO = new CommentDAO(ds);
			Collection<CommentBean> comments = null;
			try {
				 comments = commentDAO.doRetrieveAllByProduct(productID);
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, e.getMessage());
			} 
			
			request.setAttribute("comments", comments);
			
			reqDisp = request.getRequestDispatcher("product.jsp");
			
		}
		
		return reqDisp;
	}
}
	


