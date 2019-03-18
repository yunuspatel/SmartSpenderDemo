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
import dao.PurchaseDao;
import dao.StockDao;
import dao.TransactionMasterDao;
import vo.ProductVo;
import vo.PurchaseVo;
import vo.StockVo;
import vo.TransactionVo;
import vo.UserVo;

/**
 * Servlet implementation class PurchaseController
 */
@WebServlet("/PurchaseController")
public class PurchaseController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PurchaseController() {
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
			if (flag.equals("loadPurchases")) {
				loadPurchases(request, response);
			} else if (flag.equals("addPurchase")) {
				addPurchase(request, response);
			} else if (flag.equals("deletePurchase")) {
				deletePurchase(request, response);
			} else if (flag.equals("uploadReceiptImage")) {
				uploadReceiptImage(request, response);
			} else if (flag.equals("editPurchase")) {
				editPurchase(request, response);
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

	private void editPurchase(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		PurchaseVo purchaseVo = new PurchaseVo();
		int purchaseId = Integer.parseInt(request.getParameter("purchaseId"));

		PurchaseDao purchaseDao = new PurchaseDao();
		purchaseVo = purchaseDao.getPurchaseById("" + purchaseId);

		purchaseVo.setDealerName(request.getParameter("dealerName"));
		purchaseVo.setPurchasePrice(Float.parseFloat(request.getParameter("purchasePrice")));
		purchaseVo.setUserVo(userVo);

		ProductVo oldProductVo = purchaseVo.getProductVo();
		int oldProductQuantity = purchaseVo.getQuantity();

		ProductVo productVo = new ProductVo();
		productVo.setProductId(Integer.parseInt(request.getParameter("productName")));
		purchaseVo.setProductVo(productVo);
		purchaseVo.setQuantity(Integer.parseInt(request.getParameter("purchaseQuantity")));

		// Stock Opertion Starts Here..
		StockDao stockDao = new StockDao();
		List<StockVo> stockList = stockDao.checkProductExists(oldProductVo, userVo);
		StockVo stockVo = stockList.get(0);

		if (stockVo.getProductVo().getProductId() == purchaseVo.getProductVo().getProductId()) {
			int difference = purchaseVo.getQuantity() - oldProductQuantity;
			if (oldProductQuantity < purchaseVo.getQuantity()) {
				stockVo.setQuantityLeft(stockVo.getQuantityLeft() + difference);
			} else if (oldProductQuantity > purchaseVo.getQuantity()) {
				stockVo.setQuantityLeft(stockVo.getQuantityLeft() + difference);
			}
		} else {
			stockList = stockDao.checkProductExists(purchaseVo.getProductVo(), userVo);
			if (stockList.isEmpty()) {
				StockVo newStockVo = new StockVo();
				newStockVo.setProductVo(purchaseVo.getProductVo());
				newStockVo.setQuantityLeft(purchaseVo.getQuantity());
				newStockVo.setUserVo(userVo);
				stockDao.addStock(newStockVo);
				stockVo.setQuantityLeft(stockVo.getQuantityLeft() - oldProductQuantity);
			} else {
				StockVo newStockVo = stockList.get(0);
				newStockVo.setQuantityLeft(newStockVo.getQuantityLeft() + purchaseVo.getQuantity());
				stockDao.updateStock(newStockVo);
				stockVo.setQuantityLeft(stockVo.getQuantityLeft() - oldProductQuantity);
			}
		}

		try {
			PurchaseDao purchaseDao2 = new PurchaseDao();
			purchaseDao2.updatePurchase(purchaseVo);
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
		loadPurchases(request, response);
	}

	private void uploadReceiptImage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String purchaseId = request.getParameter("id");

		PurchaseVo purchaseVo = null;

		if (purchaseId != null) {
			PurchaseDao purchaseDao = new PurchaseDao();
			purchaseVo = purchaseDao.getPurchaseById(purchaseId);
		}

		String relativePath = "img/purchaseReceipts";
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

				File uploadFile = new File(
						file + File.separator + purchaseVo.getPurchaseIdentificationNumber() + ".jpg");
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
						purchaseVo.setPurchaseReceiptImage(
								"../../img/purchaseReceipts/" + purchaseVo.getPurchaseIdentificationNumber() + ".jpg");
						PurchaseDao purchaseDao = new PurchaseDao();
						purchaseDao.updatePurchase(purchaseVo);
						session.setAttribute("userMsg", "Purchase Receipt Image Successfully Uploaded.");
						loadPurchases(request, response);
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
			loadPurchases(request, response);
		}
	}

	private void deletePurchase(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");
		PurchaseVo purchaseVo = new PurchaseVo();
		int purchaseId = Integer.parseInt(request.getParameter("value"));
		purchaseVo.setPurchaseId(purchaseId);

		StockDao stockDao = new StockDao();
		PurchaseDao purchaseDao = new PurchaseDao();
		purchaseVo = purchaseDao.getPurchaseById("" + purchaseId);

		List<StockVo> stockList = stockDao.checkProductExists(purchaseVo.getProductVo(), userVo);
		StockVo stockVo = stockList.get(0);
		stockVo.setQuantityLeft(stockVo.getQuantityLeft() - purchaseVo.getQuantity());

		try {
			purchaseDao.deletePurchase(purchaseVo);
			stockDao.updateStock(stockVo);
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
		loadPurchases(request, response);
	}

	private void addPurchase(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		PurchaseVo purchaseVo = new PurchaseVo();
		purchaseVo.setDealerName(request.getParameter("dealerName"));
		ProductVo productVo = new ProductVo();
		productVo.setProductId(Integer.parseInt(request.getParameter("productName")));
		purchaseVo.setProductVo(productVo);
		purchaseVo.setPurchaseDateTime(new Date().toString());
		if (true) {
			PurchaseDao purchaseDao = new PurchaseDao();
			BigInteger refernceNumber = purchaseDao.getReferenceNumber(
					"select case count(purchase_id) when 0 then 1 else count(purchase_id)+1 end from purchase_master where user_id='"
							+ userVo.getUserId() + "'");
			Date date = new Date();
			String dateFormat = new SimpleDateFormat("ddMMHHmmss").format(date);
			String identificationNumber = "PUR" + userVo.getUserId() + dateFormat + refernceNumber.intValue();
			purchaseVo.setPurchaseIdentificationNumber(identificationNumber);
		}
		purchaseVo.setPurchasePrice(Float.parseFloat(request.getParameter("purchasePrice")));
		purchaseVo.setPurchaseReceiptImage("");
		int quantity = Integer.parseInt(request.getParameter("purchaseQuantity"));
		purchaseVo.setQuantity(quantity);
		purchaseVo.setUserVo(userVo);

		session.setAttribute("user", userVo);
		try {
			PurchaseDao purchaseDao = new PurchaseDao();
			purchaseDao.addPurchase(purchaseVo);
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

		StockDao stockDao = new StockDao();
		List<StockVo> stockList = stockDao.checkProductExists(productVo, userVo);
		StockVo stockVo;
		if (stockList.isEmpty()) {
			stockVo = new StockVo();
			stockVo.setProductVo(productVo);
			stockVo.setQuantityLeft(quantity);
			stockVo.setUserVo(userVo);
			stockDao.addStock(stockVo);
		} else {
			stockVo = stockList.get(0);
			stockVo.setQuantityLeft(stockVo.getQuantityLeft() + quantity);
			stockDao.updateStock(stockVo);
		}

		loadPurchases(request, response);
	}

	private void loadPurchases(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		ProductDao productDao = new ProductDao();
		List<ProductVo> productList = productDao.loadProducts(userVo);
		session.setAttribute("productList", productList);

		PurchaseDao purchaseDao = new PurchaseDao();
		List<PurchaseVo> purchaseList = purchaseDao.loadPurchases(userVo);
		session.setAttribute("purchaseList", purchaseList);

		session.setAttribute("user", userVo);
		response.sendRedirect(request.getContextPath() + "/view/pages/purchase.jsp");
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