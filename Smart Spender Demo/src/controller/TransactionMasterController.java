package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

import dao.CategoryMasterDao;
import dao.SubCategoryDao;
import dao.TransactionMasterDao;
import dao.UserMasterDao;
import vo.CategoryVo;
import vo.SubCategoriesVo;
import vo.TransactionVo;
import vo.UserVo;

/**
 * Servlet implementation class TransactionMasterController
 */
@WebServlet("/TransactionMasterController")
public class TransactionMasterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TransactionMasterController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String flag = request.getParameter("flag");

		if (flag.equals("addIncomeTransaction")) {
			addIncomeTransaction(request, response);
		} else if (flag.equals("addExpenseTransaction")) {
			addExpenseTransaction(request, response);
		} else if (flag.equals("loadIncomeTransaction")) {
			loadIncomeTransaction(request, response);
		} else if (flag.equals("loadExpenseTransaction")) {
			loadExpenseTransaction(request, response);
		} else if (flag.equals("uploadReceiptImage")) {
			uploadReceiptImage(request, response);
		} else if(flag.equals("loadTransactionDetails")) {
			loadTransactionDetails(request,response);
		}
	}

	private void loadTransactionDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String transactionIdentificationNumber = request.getParameter("id");
		TransactionVo transactionVo = null;
		if(transactionIdentificationNumber!=null)
		{
			TransactionMasterDao transactionMasterDao=new TransactionMasterDao();
			transactionVo=transactionMasterDao.getTransactionByIdentificationNumber(transactionIdentificationNumber);
		}
		
		HttpSession session=request.getSession();
		session.setAttribute("transactionDetails", transactionVo);
		response.sendRedirect(request.getContextPath()+"/view/pages/transaction-detail.jsp");
	}

	private void uploadReceiptImage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// String rootPath = System.getProperty("catalina.home");
		// to store image in user project directory use System.getProperty("user.dir");
		/*
		 * File currentDirFile = new File("."); String helper =
		 * currentDirFile.getAbsolutePath(); String currentDir = helper.substring(0,
		 * helper.length() - currentDirFile.getCanonicalPath().length());
		 * System.out.println(currentDir);
		 */

		String transactionIdentificationNumber = request.getParameter("id");

		TransactionVo transactionVo = null;

		if (transactionIdentificationNumber != null) {
			TransactionMasterDao transactionMasterDao = new TransactionMasterDao();
			transactionVo = transactionMasterDao.getTransactionByIdentificationNumber(transactionIdentificationNumber);
		}

		String relativePath = "img/transactionImages";
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
						file + File.separator + transactionVo.getTransactionIdentificationNumber() + ".jpg");
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
						transactionVo.setTransactionReceiptImage("../../img/transactionImages/"+ transactionVo.getTransactionIdentificationNumber() + ".jpg");
						TransactionMasterDao transactionMasterDao = new TransactionMasterDao();
						transactionMasterDao.updateReceiptImage(transactionVo);
						session.setAttribute("imageChanged", true);
						response.sendRedirect(request.getContextPath()+ "/TransactionMasterController?flag=loadTransactionDetails&id="+ transactionVo.getTransactionIdentificationNumber());
					}
				} else {
					session.setAttribute("userMsg", "Can only upload jpeg files");
				}
			}
		} catch (FileUploadException e) {
			System.out.println("Exception in uploading file. FIleUpload");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (!response.isCommitted()) {
			response.sendRedirect(request.getContextPath() + "/TransactionMasterController?flag=loadIncomeTransaction");
		}
	}

	private void loadExpenseTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		TransactionMasterDao transactionMasterDao = new TransactionMasterDao();
		List<TransactionVo> transactionList = transactionMasterDao.getTransactionForDisplay(userVo, "expense");

		session.setAttribute("transactionList", transactionList);
		session.setAttribute("user", userVo);
		session.setAttribute("expenseFlag", true);
		response.sendRedirect(request.getContextPath() + "/view/pages/transaction-list.jsp");
	}

	private void loadIncomeTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		TransactionMasterDao transactionMasterDao = new TransactionMasterDao();
		List<TransactionVo> transactionList = transactionMasterDao.getTransactionForDisplay(userVo, "income");

		session.setAttribute("transactionList", transactionList);
		session.setAttribute("user", userVo);
		session.setAttribute("incomeFlag", true);
		response.sendRedirect(request.getContextPath() + "/view/pages/transaction-list.jsp");
	}

	private void addExpenseTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");
		TransactionVo transactionVo = new TransactionVo();

		transactionVo.setUserVo(userVo);

		String payeeName = request.getParameter("payeeName");
		transactionVo.setPayeeName(payeeName);

		float transactionAmount = Float.parseFloat(request.getParameter("transactionAmount"));
		transactionVo.setTransactionAmount(transactionAmount);

		String transactionDateTime = request.getParameter("transactionDate");
		transactionVo.setTransactionDateTime(transactionDateTime);

		CategoryVo categoryVo = new CategoryVo();
		Set<SubCategoriesVo> subCategoriesVos = new HashSet<SubCategoriesVo>();
		String categoryName = request.getParameter("categoryName");
		boolean isAvailableCategoryFound = false;

		if (!categoryName.equals("")) {
			CategoryMasterDao categoryMasterDao = new CategoryMasterDao();
			List<CategoryVo> categoryList = categoryMasterDao.checkCategoryBasedOnName(categoryName, "expense", userVo);
			categoryMasterDao.dbOperation.session.close();
			if (categoryList.isEmpty()) {
				categoryVo.setCategoryName(categoryName);
				categoryVo.setForCategory("expense");
				categoryVo.setUserVo(userVo);
			} else {
				isAvailableCategoryFound = true;
				categoryVo = categoryList.get(0);
				transactionVo.setCategoryVo(categoryVo);
			}
		} else {
			String categoryNameFromSelect = request.getParameter("categorySelect");
			CategoryMasterDao categoryMasterDao = new CategoryMasterDao();
			List<CategoryVo> categoryList = categoryMasterDao.checkCategoryBasedOnName(categoryNameFromSelect,
					"expense", userVo);
			categoryVo = categoryList.get(0);
			categoryMasterDao.dbOperation.session.close();
			transactionVo.setCategoryVo(categoryVo);
		}

		String subCategoryName = request.getParameter("subCategoryName");

		if (!subCategoryName.equals("")) {
			SubCategoryDao subCategoryDao = new SubCategoryDao();
			List<SubCategoriesVo> subCategoryList = subCategoryDao.checkSubCategory(subCategoryName, userVo);
			subCategoryDao.dbOperation.session.clear();
			if (subCategoryList.isEmpty()) {
				if (isAvailableCategoryFound) {
					SubCategoriesVo subCategoriesVo = new SubCategoriesVo();
					subCategoriesVo.setUserVo(userVo);
					subCategoriesVo.setSubCategoryName(subCategoryName);
					categoryVo.getSubCategories().add(subCategoriesVo);
					subCategoriesVo.setCategoryVo(categoryVo);

					CategoryMasterDao categoryMasterDao = new CategoryMasterDao();
					categoryMasterDao.updateCategory(categoryVo);

					transactionVo.setCategoryVo(categoryVo);
					transactionVo.setSubCategoriesVo(subCategoriesVo);
				} else {
					if (categoryVo.getSubCategories() == null) {
						SubCategoriesVo subCategoriesVo = new SubCategoriesVo();
						subCategoriesVo.setSubCategoryName(subCategoryName);
						subCategoriesVo.setUserVo(userVo);
						subCategoriesVos.add(subCategoriesVo);
						subCategoriesVo.setCategoryVo(categoryVo);
						categoryVo.setSubCategories(subCategoriesVos);

						CategoryMasterDao categoryMasterDao = new CategoryMasterDao();
						categoryMasterDao.addCategory(categoryVo);

						transactionVo.setCategoryVo(categoryVo);
						transactionVo.setSubCategoriesVo(subCategoriesVo);
					} else {
						SubCategoriesVo subCategoriesVo = new SubCategoriesVo();
						subCategoriesVo.setSubCategoryName(subCategoryName);
						subCategoriesVo.setUserVo(userVo);
						categoryVo.getSubCategories().add(subCategoriesVo);
						subCategoriesVo.setCategoryVo(categoryVo);

						CategoryMasterDao categoryMasterDao = new CategoryMasterDao();
						categoryMasterDao.updateCategory(categoryVo);

						transactionVo.setCategoryVo(categoryVo);
						transactionVo.setSubCategoriesVo(subCategoriesVo);
					}
				}
			} else {
				SubCategoriesVo subCategoriesVo = subCategoryList.get(0);
				if (categoryVo.getSubCategories() == null) {
					SubCategoriesVo subCategoriesVo2 = new SubCategoriesVo();
					subCategoriesVo2.setSubCategoryName(subCategoriesVo.getSubCategoryName());
					subCategoriesVo2.setUserVo(userVo);
					subCategoriesVos.add(subCategoriesVo2);
					categoryVo.setSubCategories(subCategoriesVos);
					subCategoriesVo2.setCategoryVo(categoryVo);

					CategoryMasterDao categoryMasterDao = new CategoryMasterDao();
					categoryMasterDao.addCategory(categoryVo);

					transactionVo.setCategoryVo(categoryVo);
					transactionVo.setSubCategoriesVo(subCategoriesVo);
				} else {
					transactionVo.setSubCategoriesVo(subCategoriesVo);
				}
			}
		} else {
			String subCategoryFromSelect = request.getParameter("subCategorySelect");
			SubCategoryDao subCategoryDao = new SubCategoryDao();
			List<SubCategoriesVo> subCategoryList = subCategoryDao.checkSubCategory(subCategoryFromSelect, userVo);
			SubCategoriesVo subCategoriesVo = subCategoryList.get(0);
			transactionVo.setSubCategoriesVo(subCategoriesVo);
		}

		String paymentMethod = request.getParameter("paymentMethod");
		transactionVo.setPaymentMethod(paymentMethod);

		if (!paymentMethod.equals("Cash")) {
			String transactionNumber = request.getParameter("transactionNumber");
			transactionVo.setTransactionNumber(transactionNumber);
		} else {
			transactionVo.setTransactionNumber("");
		}

		String paymentStatus = request.getParameter("paymentStatus");
		transactionVo.setStatusOfTransaction(paymentStatus);

		String paymentDescription = request.getParameter("paymentDescription");
		if (paymentDescription.equals("")) {
			transactionVo.setExtraDescription("");
		} else {
			transactionVo.setExtraDescription(paymentDescription);
		}

		transactionVo.setForTransaction("expense");

		if (true) {
			TransactionMasterDao transactionMasterDao = new TransactionMasterDao();
			BigInteger referenceNo = transactionMasterDao.getIdentificationNumber(
					"select case count(transaction_id) when 0 then 1 else count(transaction_id)+1 end from transaction_master where user_id='"
							+ userVo.getUserId() + "'");
			Date date = new Date();
			String dateFormat = new SimpleDateFormat("ddMMHHmmss").format(date);
			String identificationNumber = "TRAN" + userVo.getUserId() + dateFormat + referenceNo.intValue();
			transactionVo.setTransactionIdentificationNumber(identificationNumber);

			TransactionMasterDao transactionMasterDao2 = new TransactionMasterDao();
			List<TransactionVo> transactionList = transactionMasterDao2.getLastTransaction(userVo);

			if (transactionList.isEmpty()) {

				transactionVo.setTotalAvailableBalance(0 - transactionVo.getTransactionAmount());
			} else {
				TransactionVo transactionVo2 = transactionList.get(0);
				float lastBalance = transactionVo2.getTotalAvailableBalance();
				transactionVo.setTotalAvailableBalance(lastBalance - transactionVo.getTransactionAmount());
			}
		}

		transactionVo.setTransactionReceiptImage("");
		transactionVo.setIsDeleted(false);
		transactionVo.setBalanceAddedFromTransactionId("");

		TransactionMasterDao transactionMasterDao = new TransactionMasterDao();
		transactionMasterDao.addTransaction(transactionVo);

		session.setAttribute("user", userVo);
		session.setAttribute("userMsg", "Transaction Recorded Successfully.");
		response.sendRedirect(request.getContextPath() + "/view/pages/add-expense.jsp");
	}

	private void addIncomeTransaction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");
		TransactionVo transactionVo = new TransactionVo();

		transactionVo.setUserVo(userVo);

		String payeeName = request.getParameter("payeeName");
		transactionVo.setPayeeName(payeeName);

		float transactionAmount = Float.parseFloat(request.getParameter("transactionAmount"));
		transactionVo.setTransactionAmount(transactionAmount);

		String transactionDateTime = request.getParameter("transactionDate");
		transactionVo.setTransactionDateTime(transactionDateTime);

		CategoryVo categoryVo = new CategoryVo();
		Set<SubCategoriesVo> subCategoriesVos = new HashSet<SubCategoriesVo>();
		String categoryName = request.getParameter("categoryName");
		boolean isAvailableCategoryFound = false;

		if (!categoryName.equals("")) {
			CategoryMasterDao categoryMasterDao = new CategoryMasterDao();
			List<CategoryVo> categoryList = categoryMasterDao.checkCategoryBasedOnName(categoryName, "income", userVo);
			categoryMasterDao.dbOperation.session.close();
			if (categoryList.isEmpty()) {
				categoryVo.setCategoryName(categoryName);
				categoryVo.setForCategory("income");
				categoryVo.setUserVo(userVo);
			} else {
				isAvailableCategoryFound = true;
				categoryVo = categoryList.get(0);
				transactionVo.setCategoryVo(categoryVo);
			}
		} else {
			String categoryNameFromSelect = request.getParameter("categorySelect");
			CategoryMasterDao categoryMasterDao = new CategoryMasterDao();
			List<CategoryVo> categoryList = categoryMasterDao.checkCategoryBasedOnName(categoryNameFromSelect, "income",
					userVo);
			categoryVo = categoryList.get(0);
			categoryMasterDao.dbOperation.session.close();
			transactionVo.setCategoryVo(categoryVo);
		}

		String subCategoryName = request.getParameter("subCategoryName");

		if (!subCategoryName.equals("")) {
			SubCategoryDao subCategoryDao = new SubCategoryDao();
			List<SubCategoriesVo> subCategoryList = subCategoryDao.checkSubCategory(subCategoryName, userVo);
			subCategoryDao.dbOperation.session.clear();
			if (subCategoryList.isEmpty()) {
				if (isAvailableCategoryFound) {
					SubCategoriesVo subCategoriesVo = new SubCategoriesVo();
					subCategoriesVo.setUserVo(userVo);
					subCategoriesVo.setSubCategoryName(subCategoryName);
					categoryVo.getSubCategories().add(subCategoriesVo);
					subCategoriesVo.setCategoryVo(categoryVo);

					CategoryMasterDao categoryMasterDao = new CategoryMasterDao();
					categoryMasterDao.updateCategory(categoryVo);

					transactionVo.setCategoryVo(categoryVo);
					transactionVo.setSubCategoriesVo(subCategoriesVo);
				} else {
					if (categoryVo.getSubCategories() == null) {
						SubCategoriesVo subCategoriesVo = new SubCategoriesVo();
						subCategoriesVo.setSubCategoryName(subCategoryName);
						subCategoriesVo.setUserVo(userVo);
						subCategoriesVos.add(subCategoriesVo);
						subCategoriesVo.setCategoryVo(categoryVo);
						categoryVo.setSubCategories(subCategoriesVos);

						CategoryMasterDao categoryMasterDao = new CategoryMasterDao();
						categoryMasterDao.addCategory(categoryVo);

						transactionVo.setCategoryVo(categoryVo);
						transactionVo.setSubCategoriesVo(subCategoriesVo);
					} else {
						SubCategoriesVo subCategoriesVo = new SubCategoriesVo();
						subCategoriesVo.setSubCategoryName(subCategoryName);
						subCategoriesVo.setUserVo(userVo);
						categoryVo.getSubCategories().add(subCategoriesVo);
						subCategoriesVo.setCategoryVo(categoryVo);

						CategoryMasterDao categoryMasterDao = new CategoryMasterDao();
						categoryMasterDao.updateCategory(categoryVo);

						transactionVo.setCategoryVo(categoryVo);
						transactionVo.setSubCategoriesVo(subCategoriesVo);
					}
				}
			} else {
				SubCategoriesVo subCategoriesVo = subCategoryList.get(0);
				if (categoryVo.getSubCategories() == null) {
					SubCategoriesVo subCategoriesVo2 = new SubCategoriesVo();
					subCategoriesVo2.setSubCategoryName(subCategoriesVo.getSubCategoryName());
					subCategoriesVo2.setUserVo(userVo);
					subCategoriesVos.add(subCategoriesVo2);
					categoryVo.setSubCategories(subCategoriesVos);
					subCategoriesVo2.setCategoryVo(categoryVo);

					CategoryMasterDao categoryMasterDao = new CategoryMasterDao();
					categoryMasterDao.addCategory(categoryVo);

					transactionVo.setCategoryVo(categoryVo);
					transactionVo.setSubCategoriesVo(subCategoriesVo);
				} else {
					transactionVo.setSubCategoriesVo(subCategoriesVo);
				}
			}
		} else {
			String subCategoryFromSelect = request.getParameter("subCategorySelect");
			SubCategoryDao subCategoryDao = new SubCategoryDao();
			List<SubCategoriesVo> subCategoryList = subCategoryDao.checkSubCategory(subCategoryFromSelect, userVo);
			SubCategoriesVo subCategoriesVo = subCategoryList.get(0);
			transactionVo.setSubCategoriesVo(subCategoriesVo);
		}

		String paymentMethod = request.getParameter("paymentMethod");
		transactionVo.setPaymentMethod(paymentMethod);

		if (!paymentMethod.equals("Cash")) {
			String transactionNumber = request.getParameter("transactionNumber");
			transactionVo.setTransactionNumber(transactionNumber);
		} else {
			transactionVo.setTransactionNumber("");
		}

		String paymentStatus = request.getParameter("paymentStatus");
		transactionVo.setStatusOfTransaction(paymentStatus);

		String paymentDescription = request.getParameter("paymentDescription");
		if (paymentDescription.equals("")) {
			transactionVo.setExtraDescription("");
		} else {
			transactionVo.setExtraDescription(paymentDescription);
		}

		transactionVo.setForTransaction("income");

		if (true) {
			TransactionMasterDao transactionMasterDao = new TransactionMasterDao();
			BigInteger referenceNo = transactionMasterDao.getIdentificationNumber(
					"select case count(transaction_id) when 0 then 1 else count(transaction_id)+1 end from transaction_master where user_id='"
							+ userVo.getUserId() + "'");
			Date date = new Date();
			String dateFormat = new SimpleDateFormat("ddMMHHmmss").format(date);
			String identificationNumber = "TRAN" + userVo.getUserId() + dateFormat + referenceNo.intValue();
			transactionVo.setTransactionIdentificationNumber(identificationNumber);

			TransactionMasterDao transactionMasterDao2 = new TransactionMasterDao();
			List<TransactionVo> transactionList = transactionMasterDao2.getLastTransaction(userVo);

			if (transactionList.isEmpty()) {
				transactionVo.setTotalAvailableBalance(transactionVo.getTransactionAmount());
			} else {
				TransactionVo transactionVo2 = transactionList.get(0);
				float lastBalance = transactionVo2.getTotalAvailableBalance();
				transactionVo.setTotalAvailableBalance(lastBalance + transactionVo.getTransactionAmount());
			}
		}

		transactionVo.setTransactionReceiptImage("");
		transactionVo.setIsDeleted(false);
		transactionVo.setBalanceAddedFromTransactionId("");

		TransactionMasterDao transactionMasterDao = new TransactionMasterDao();
		transactionMasterDao.addTransaction(transactionVo);

		session.setAttribute("user", userVo);
		session.setAttribute("userMsg", "Transaction Recorded Successfully.");
		response.sendRedirect(request.getContextPath() + "/view/pages/add-income.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
