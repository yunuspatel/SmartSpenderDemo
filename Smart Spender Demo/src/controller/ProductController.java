package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProductDao;
import vo.ProductVo;
import vo.UserVo;

/**
 * Servlet implementation class ProductController
 */
@WebServlet("/ProductController")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String flag = request.getParameter("flag");

		if (flag.equals("loadProducts")) {
			loadProducts(request, response);
		} else if (flag.equals("addProduct")) {
			addProduct(request, response);
		} else if (flag.equals("deleteProduct")) {
			deleteProduct(request, response);
		} else if(flag.equals("editProduct")) {
			editProduct(request,response);
		}
	}

	private void editProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		UserVo userVo=(UserVo)session.getAttribute("user");
		
		ProductVo productVo=new ProductVo();
		productVo.setBrandName(request.getParameter("brandName"));
		productVo.setProductId(Integer.parseInt(request.getParameter("editProductId")));
		productVo.setProductName(request.getParameter("productName"));
		productVo.setUnitOfMesaurement(request.getParameter("unitOfMesaurement"));
		productVo.setUserVo(userVo);
		
		
		session.setAttribute("user", userVo);
		ProductDao productDao=new ProductDao();
		try {
			productDao.updateProduct(productVo);
			loadProducts(request, response);
		}catch(Exception exception) {
			System.out.println("Error in product updation:- " + exception.getMessage());
			loadProducts(request, response);
		}
	}

	private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		ProductVo productVo = new ProductVo();
		productVo.setProductId(Integer.parseInt(request.getParameter("value")));

		ProductDao productDao = new ProductDao();
		session.setAttribute("user", userVo);
		try {
			productDao.deleteProduct(productVo);
			loadProducts(request, response);
		} catch (Exception exception) {
			session.setAttribute("userMsg", "Sorry, Error in deleting your product. Maybe it is used with some purchase or sale record.");
			loadProducts(request, response);
		}
	}

	private void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		ProductVo productVo = new ProductVo();
		productVo.setBrandName(request.getParameter("brandName"));
		productVo.setProductName(request.getParameter("productName"));
		productVo.setUnitOfMesaurement(request.getParameter("unitOfMesaurement"));
		productVo.setUserVo(userVo);

		ProductDao productDao = new ProductDao();
		productDao.addProduct(productVo);

		session.setAttribute("user", userVo);
		loadProducts(request, response);
	}

	private void loadProducts(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		ProductDao productDao = new ProductDao();
		List<ProductVo> productList = productDao.loadProducts(userVo);
		session.setAttribute("productList", productList);

		session.setAttribute("user", userVo);
		response.sendRedirect(request.getContextPath() + "/view/pages/product.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
