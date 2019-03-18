package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dao.ProductDao;
import dao.SalesDao;
import dao.StockDao;
import vo.ProductVo;
import vo.SalesVo;
import vo.StockVo;
import vo.UserVo;

/**
 * Servlet implementation class SalesController
 */
@WebServlet("/SalesController")
public class SalesController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SalesController() {
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

		try {
			if (flag.equals("loadSales")) {
				loadSales(request, response);
			} else if (flag.equals("addSales")) {
				addSales(request, response);
			} else if (flag.equals("uploadReceiptImage")) {
				uploadReceiptImage(request, response);
			} else if (flag.equals("deleteSales")) {
				deleteSales(request, response);
			} else if (flag.equals("editSales")) {
				editSales(request, response);
			}
		} catch (Exception exception) {
			String relativePath = "logs/error-log.txt";
			String rootPath = getServletContext().getRealPath(relativePath);
			File logFile = new File(rootPath);
			if (!logFile.exists()) {
				logFile.mkdirs();
			}
			FileWriter fileWriter = new FileWriter(logFile, true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.write(exception.getMessage() + "/n");
			printWriter.close();
		}
	}

	private void editSales(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");
		String salesId = request.getParameter("salesId");

		SalesVo salesVo = null;
		StockVo stockVo = null;
		if (salesId != null) {
			SalesDao salesDao = new SalesDao();
			salesVo = salesDao.getSalesById(salesId);

			StockDao stockDao = new StockDao();
			List<StockVo> stockList = stockDao.checkProductExists(salesVo.getProductVo(), userVo);
			stockVo = stockList.get(0);
		}

		salesVo.setCustomerName(request.getParameter("customerName"));
		salesVo.setDiscountPrice(request.getParameter("discountPrice"));
		ProductVo oldProductVo = salesVo.getProductVo();
		ProductVo productVo = new ProductVo();
		productVo.setProductId(Integer.parseInt(request.getParameter("productName")));
		salesVo.setProductVo(productVo);
		salesVo.setSalesPrice(Float.parseFloat(request.getParameter("salesPrice")));
		int oldSalesQuantity = salesVo.getSalesQuantity();
		salesVo.setSalesQuantity(Integer.parseInt(request.getParameter("salesQuantity")));

		try {
			if (oldProductVo.getProductId() == salesVo.getProductVo().getProductId()) {
				int quantityDifference = oldSalesQuantity - salesVo.getSalesQuantity();
				if (oldSalesQuantity > salesVo.getSalesQuantity()) {
					stockVo.setQuantityLeft(stockVo.getQuantityLeft() + quantityDifference);
					StockDao stockDao = new StockDao();
					stockDao.updateStock(stockVo);
					SalesDao salesDao = new SalesDao();
					salesDao.updateSales(salesVo);
				} else {
					int stockQuantity = stockVo.getQuantityLeft();
					quantityDifference = salesVo.getSalesQuantity() - oldSalesQuantity;
					if (stockQuantity >= quantityDifference) {
						stockVo.setQuantityLeft(stockVo.getQuantityLeft() - quantityDifference);
						StockDao stockDao = new StockDao();
						stockDao.updateStock(stockVo);
						SalesDao salesDao = new SalesDao();
						salesDao.updateSales(salesVo);
					} else {
						session.setAttribute("userMsg",
								"There are only " + stockQuantity + " for product "
										+ stockVo.getProductVo().getProductName()
										+ " . So cannot update the sales record due to insufficient stock quantity.");
					}
				}
			} else {
				StockDao stockDao = new StockDao();
				List<StockVo> stockList = stockDao.checkProductExists(salesVo.getProductVo(), userVo);
				StockVo newStockVo = stockList.get(0);

				if (newStockVo.getQuantityLeft() >= salesVo.getSalesQuantity()) {
					newStockVo.setQuantityLeft(newStockVo.getQuantityLeft() - salesVo.getSalesQuantity());
					stockDao.updateStock(newStockVo);
					StockDao stockDao2 = new StockDao();
					stockVo.setQuantityLeft(stockVo.getQuantityLeft() + oldSalesQuantity);
					stockDao2.updateStock(stockVo);
					SalesDao salesDao = new SalesDao();
					salesDao.updateSales(salesVo);
				} else {
					session.setAttribute("userMsg", "There are only " + newStockVo.getQuantityLeft()
							+ " quantity left for the " + newStockVo.getProductVo().getProductName()
							+ " product in stock. Cannot update sales record due to insufficient stock quantity.");
				}
			}
		} catch (Exception exception) {
			String relativePath = "logs/error-log.txt";
			String rootPath = getServletContext().getRealPath(relativePath);
			File logFile = new File(rootPath);
			if (!logFile.exists()) {
				logFile.mkdirs();
			}
			FileWriter fileWriter = new FileWriter(logFile, true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.write(exception.getMessage() + "/n");
			printWriter.close();
		}

		session.setAttribute("user", userVo);
		loadSales(request, response);
	}

	private void deleteSales(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");
		String salesId = request.getParameter("value");

		SalesDao salesDao = new SalesDao();
		SalesVo salesVo = salesDao.getSalesById(salesId);

		StockDao stockDao = new StockDao();
		List<StockVo> stockList = stockDao.checkProductExists(salesVo.getProductVo(), userVo);

		StockVo stockVo = stockList.get(0);
		stockVo.setQuantityLeft(stockVo.getQuantityLeft() + salesVo.getSalesQuantity());

		try {
			salesDao.deleteSales(salesVo);
			StockDao stockDao2 = new StockDao();
			stockDao2.updateStock(stockVo);
		} catch (Exception exception) {
			String relativePath = "logs/error-log.txt";
			String rootPath = getServletContext().getRealPath(relativePath);
			File logFile = new File(rootPath);
			if (!logFile.exists()) {
				logFile.mkdirs();
			}
			FileWriter fileWriter = new FileWriter(logFile, true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.write(exception.getMessage() + "/n");
			printWriter.close();
		}

		session.setAttribute("user", userVo);
		loadSales(request, response);
	}

	private void uploadReceiptImage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String salesId = request.getParameter("id");

		SalesVo salesVo = null;
		if (salesId != null) {
			SalesDao salesDao = new SalesDao();
			salesVo = salesDao.getSalesById(salesId);
		}

		String relativePath = "img/salesReceipts";
		String rootPath = getServletContext().getRealPath(relativePath);
		File file = new File(rootPath);
		if (!file.exists()) {
			file.mkdirs();
		}

		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new ServletException("Content type is not multipart/form-data");
		}

		DiskFileItemFactory fileFactory = new DiskFileItemFactory();
		fileFactory.setRepository(file);
		ServletFileUpload uploader = new ServletFileUpload(fileFactory);
		HttpSession session = request.getSession();

		try {
			List<FileItem> fileItemsList = uploader.parseRequest(request);
			Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
			while (fileItemsIterator.hasNext()) {
				FileItem fileItem = fileItemsIterator.next();

				File uploadFile = new File(file + File.separator + salesVo.getSalesIdentificationNumber() + ".jpg");
				String fileName = fileItem.getName();
				if (fileName.contains(".jpg") || fileName.contains(".jpeg") || fileName.contains(".png")
						|| fileName.contains(".JPG") || fileName.contains(".JPEG") || fileName.contains(".PNG")) {
					if (fileItem.getSize() >= 1048576) {
						session.setAttribute("userMsg", "Can only upload image upto 1 MB");
					} else {
						if (uploadFile.exists()) {
							uploadFile.delete();
						}
						fileItem.write(uploadFile);
						salesVo.setSalesReceiptImage(
								"../../img/salesReceipts/" + salesVo.getSalesIdentificationNumber() + ".jpg");
						SalesDao salesDao = new SalesDao();
						salesDao.updateSales(salesVo);
						session.setAttribute("userMsg", "Sales Bill Image Successfully Uploaded.");
						loadSales(request, response);
					}
				} else {
					session.setAttribute("userMsg", "Can only upload jpeg files");
				}

			}
		} catch (FileUploadException exception) {
			String relativePathLog = "logs/error-log.txt";
			String rootPathLog = getServletContext().getRealPath(relativePathLog);
			File logFile = new File(rootPathLog);
			if (!logFile.exists()) {
				logFile.mkdirs();
			}
			FileWriter fileWriter = new FileWriter(logFile, true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.write(exception.getMessage() + "/n");
			printWriter.close();
		} catch (Exception exception) {
			String relativePathLog = "logs/error-log.txt";
			String rootPathLog = getServletContext().getRealPath(relativePathLog);
			File logFile = new File(rootPathLog);
			if (!logFile.exists()) {
				logFile.mkdirs();
			}
			FileWriter fileWriter = new FileWriter(logFile, true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.write(exception.getMessage() + "/n");
			printWriter.close();
		}
		if (!response.isCommitted()) {
			loadSales(request, response);
		}
	}

	private void addSales(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		SalesVo salesVo = new SalesVo();
		salesVo.setCustomerName(request.getParameter("customerName"));
		salesVo.setDiscountPrice(request.getParameter("discountPrice"));
		ProductVo productVo = new ProductVo();
		int productId = Integer.parseInt(request.getParameter("productName"));
		productVo.setProductId(productId);
		salesVo.setProductVo(productVo);
		salesVo.setSalesDateTime(new Date().toString());

		SalesDao salesDao = new SalesDao();
		if (true) {
			BigInteger referenceNumber = salesDao.getReferenceNumber(
					"select case count(sales_id) when 0 then 1 else count(sales_id)+1 end from sales_master where user_id='"
							+ userVo.getUserId() + "'");
			Date date = new Date();
			String dateFormat = new SimpleDateFormat("ddMMHHmmss").format(date);
			String identificationNumber = "SAL" + userVo.getUserId() + dateFormat + referenceNumber.intValue();
			salesVo.setSalesIdentificationNumber(identificationNumber);
		}
		salesVo.setSalesPrice(Float.parseFloat(request.getParameter("salesPrice")));
		int salesQuantity = Integer.parseInt(request.getParameter("salesQuantity"));
		salesVo.setSalesQuantity(salesQuantity);
		salesVo.setSalesReceiptImage("");
		salesVo.setUserVo(userVo);

		StockDao stockDao = new StockDao();
		List<StockVo> stockList = stockDao.checkProductExists(productVo, userVo);

		try {
			StockVo stockVo = stockList.get(0);
			if (stockVo.getQuantityLeft() >= salesQuantity) {
				salesDao.addSales(salesVo);
				stockVo.setQuantityLeft(stockVo.getQuantityLeft() - salesQuantity);
				stockDao.updateStock(stockVo);
			} else {
				session.setAttribute("userMsg",
						"Only " + stockVo.getQuantityLeft() + " quantity are left for "
								+ stockVo.getProductVo().getProductName()
								+ " . Sorry can't place order due to insufficient stock for selected product.");
				response.sendRedirect(request.getContextPath() + "/view/pages/sales.jsp");
			}
		} catch (Exception exception) {
			String relativePath = "logs/error-log.txt";
			String rootPath = getServletContext().getRealPath(relativePath);
			File logFile = new File(rootPath);
			if (!logFile.exists()) {
				logFile.mkdirs();
			}
			FileWriter fileWriter = new FileWriter(logFile, true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.write(exception.getMessage() + "/n");
			printWriter.close();
		}

		session.setAttribute("user", userVo);
		if (!response.isCommitted()) {
			loadSales(request, response);
		}
	}

	private void loadSales(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		SalesDao salesDao = new SalesDao();
		List<SalesVo> salesList = salesDao.loadSales(userVo);
		session.setAttribute("salesList", salesList);

		ProductDao productDao = new ProductDao();
		List<ProductVo> productList = productDao.loadProducts(userVo);
		session.setAttribute("productList", productList);

		session.setAttribute("user", userVo);
		response.sendRedirect(request.getContextPath() + "/view/pages/sales.jsp");
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
