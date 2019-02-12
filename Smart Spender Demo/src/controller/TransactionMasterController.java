package controller;

import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CategoryMasterDao;
import dao.SubCategoryDao;
import dao.TransactionMasterDao;
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
		}
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
					SubCategoriesVo subCategoriesVo2=new SubCategoriesVo();
					subCategoriesVo2.setSubCategoryName(subCategoriesVo.getSubCategoryName());
					subCategoriesVo2.setUserVo(userVo);
					subCategoriesVos.add(subCategoriesVo2);
					categoryVo.setSubCategories(subCategoriesVos);
					subCategoriesVo2.setCategoryVo(categoryVo);
					
					CategoryMasterDao categoryMasterDao=new CategoryMasterDao();
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
			List<TransactionVo> transactionList = transactionMasterDao2.getLastTransaction(userVo,
					transactionVo.getForTransaction());

			if (transactionList.isEmpty()) {
				transactionVo.setTotalAvailableBalance(transactionVo.getTransactionAmount());
			} else {
				TransactionVo transactionVo2 = transactionList.get(0);
				float lastBalance = transactionVo2.getTotalAvailableBalance();
				transactionVo.setTotalAvailableBalance(lastBalance + transactionVo.getTransactionAmount());
			}
		}
		TransactionMasterDao transactionMasterDao = new TransactionMasterDao();
		transactionMasterDao.addTransaction(transactionVo);

		session.setAttribute("user", userVo);
		session.setAttribute("userMsg", "Transaction Recorded Successfully.");
		response.sendRedirect(request.getContextPath() + "/view/pages/add-income.jsp");
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
