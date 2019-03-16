package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import dao.BudgetMasterDao;
import dao.CategoryMasterDao;
import dao.NotificationDao;
import dao.SubCategoryDao;
import dao.TransactionMasterDao;
import dao.UserMasterDao;
import vo.BudgetVo;
import vo.CategoryVo;
import vo.NotificationVo;
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
			try {
				addExpenseTransaction(request, response);
			} catch (ParseException e) {
				System.out.println(e.getMessage());
			}
		} else if (flag.equals("loadIncomeTransaction")) {
			loadIncomeTransaction(request, response);
		} else if (flag.equals("loadExpenseTransaction")) {
			loadExpenseTransaction(request, response);
		} else if (flag.equals("uploadReceiptImage")) {
			uploadReceiptImage(request, response);
		} else if (flag.equals("loadTransactionDetails")) {
			loadTransactionDetails(request, response);
		} else if (flag.equals("editTransaction")) {
			try {
				editTransaction(request, response);
			} catch (ParseException e) {
				System.out.println(e.getMessage());
			}
		} else if (flag.equals("deleteTransaction")) {
			try {
				deleteTransaction(request, response);
			} catch (ParseException e) {
				System.out.println(e.getMessage());
			}
		} else if (flag.equals("generateReport")) {
			try {
				generateReport(request, response);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		}
	}

	private void generateReport(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ParseException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");
		int month = Integer.parseInt(request.getParameter("month"));
		String year = request.getParameter("year");

		if (year != null) {
			String fileName = userVo.getUserName() + "-" + month + "-" + year;

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet income = workbook.createSheet("Income");
			XSSFSheet expense = workbook.createSheet("Expense");
			XSSFRow incomeRow, expenseRow;
			float totalIncome = 0, totalExpense = 0, temp = 0;

			TransactionMasterDao transactionMasterDao = new TransactionMasterDao();
			List<TransactionVo> transactionList = transactionMasterDao.getAllTransactions(userVo);

			if (true) {
				incomeRow = income.createRow(0);
				Cell cell1 = incomeRow.createCell(0);
				cell1.setCellValue((String) "Id");

				Cell cell3 = incomeRow.createCell(1);
				cell3.setCellValue((String) "Payee Name");

				Cell cell4 = incomeRow.createCell(2);
				cell4.setCellValue((String) "Amount");

				Cell cell5 = incomeRow.createCell(3);
				cell5.setCellValue((String) "Date");

				Cell cell7 = incomeRow.createCell(4);
				cell7.setCellValue((String) "Category Name");

				Cell cell8 = incomeRow.createCell(5);
				cell8.setCellValue((String) "Sub-Category Name");

				Cell cell9 = incomeRow.createCell(6);
				cell9.setCellValue((String) "Payment Method");

				Cell cell10 = incomeRow.createCell(7);
				cell10.setCellValue((String) "Status");

				Cell cell11 = incomeRow.createCell(8);
				cell11.setCellValue((String) "Reference Number");

				Cell cell12 = incomeRow.createCell(9);
				cell12.setCellValue((String) "Extra Description");
			}
			if (true) {
				expenseRow = expense.createRow(0);
				Cell cell1 = expenseRow.createCell(0);
				cell1.setCellValue((String) "Id");

				Cell cell3 = expenseRow.createCell(1);
				cell3.setCellValue((String) "Payee Name");

				Cell cell4 = expenseRow.createCell(2);
				cell4.setCellValue((String) "Amount");

				Cell cell5 = expenseRow.createCell(3);
				cell5.setCellValue((String) "Date");

				Cell cell7 = expenseRow.createCell(4);
				cell7.setCellValue((String) "Category Name");

				Cell cell8 = expenseRow.createCell(5);
				cell8.setCellValue((String) "Sub-Category Name");

				Cell cell9 = expenseRow.createCell(6);
				cell9.setCellValue((String) "Payment Method");

				Cell cell10 = expenseRow.createCell(7);
				cell10.setCellValue((String) "Status");

				Cell cell11 = expenseRow.createCell(8);
				cell11.setCellValue((String) "Reference Number");

				Cell cell12 = expenseRow.createCell(9);
				cell12.setCellValue((String) "Extra Description");
			}

			int incomeRowId = 1, expenseRowId = 1;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm");

			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy");
			Date userDate = dateFormat.parse(year);
			
			for (TransactionVo transactionVo : transactionList) {
				Date transactionDate = simpleDateFormat.parse(transactionVo.getTransactionDateTime());
				if (((transactionDate.getMonth() + 1) == month) && (transactionDate.getYear() == userDate.getYear())) {
					if (transactionVo.getForTransaction().equals("income")) {
						incomeRow = income.createRow(incomeRowId++);
						Cell cell1 = incomeRow.createCell(0);
						cell1.setCellValue((String) "" + transactionVo.getTransactionIdentificationNumber());

						Cell cell3 = incomeRow.createCell(1);
						cell3.setCellValue((String) "" + transactionVo.getPayeeName());

						Cell cell4 = incomeRow.createCell(2);
						cell4.setCellValue((String) "" + transactionVo.getTransactionAmount());

						Cell cell5 = incomeRow.createCell(3);
						cell5.setCellValue((String) "" + transactionVo.getTransactionDateTime());

						Cell cell7 = incomeRow.createCell(4);
						cell7.setCellValue((String) "" + transactionVo.getCategoryVo().getCategoryName());

						Cell cell8 = incomeRow.createCell(5);
						cell8.setCellValue((String) "" + transactionVo.getSubCategoriesVo().getSubCategoryName());

						Cell cell9 = incomeRow.createCell(6);
						cell9.setCellValue((String) "" + transactionVo.getPaymentMethod());

						Cell cell10 = incomeRow.createCell(7);
						cell10.setCellValue((String) "" + transactionVo.getStatusOfTransaction());

						Cell cell11 = incomeRow.createCell(8);
						cell11.setCellValue((String) "" + transactionVo.getTransactionNumber());

						Cell cell12 = incomeRow.createCell(9);
						cell12.setCellValue((String) "" + transactionVo.getExtraDescription());

						totalIncome += transactionVo.getTransactionAmount();
					} else {
						expenseRow = expense.createRow(expenseRowId++);
						Cell cell1 = expenseRow.createCell(0);
						cell1.setCellValue((String) "" + transactionVo.getTransactionIdentificationNumber());

						Cell cell3 = expenseRow.createCell(1);
						cell3.setCellValue((String) "" + transactionVo.getPayeeName());

						Cell cell4 = expenseRow.createCell(2);
						cell4.setCellValue((String) "" + transactionVo.getTransactionAmount());

						Cell cell5 = expenseRow.createCell(3);
						cell5.setCellValue((String) "" + transactionVo.getTransactionDateTime());

						Cell cell7 = expenseRow.createCell(4);
						cell7.setCellValue((String) "" + transactionVo.getCategoryVo().getCategoryName());

						Cell cell8 = expenseRow.createCell(5);
						cell8.setCellValue((String) "" + transactionVo.getSubCategoriesVo().getSubCategoryName());

						Cell cell9 = expenseRow.createCell(6);
						cell9.setCellValue((String) "" + transactionVo.getPaymentMethod());

						Cell cell10 = expenseRow.createCell(7);
						cell10.setCellValue((String) "" + transactionVo.getStatusOfTransaction());

						Cell cell11 = expenseRow.createCell(8);
						cell11.setCellValue((String) "" + transactionVo.getTransactionNumber());

						Cell cell12 = expenseRow.createCell(9);
						cell12.setCellValue((String) "" + transactionVo.getExtraDescription());

						totalExpense += transactionVo.getTransactionAmount();
					}
				}
			}

			incomeRow = income.createRow(incomeRowId + 2);
			Cell incomeCellName = incomeRow.createCell(2);
			incomeCellName.setCellValue((String) "Total Monthly Income is:-");
			Cell incomeCellData = incomeRow.createCell(3);
			incomeCellData.setCellValue((String) "" + totalIncome);

			expenseRow = expense.createRow(expenseRowId + 2);
			Cell expenseCellName = expenseRow.createCell(2);
			expenseCellName.setCellValue((String) "Total Monthly Expense is:-");
			Cell expenseCellData = expenseRow.createCell(3);
			expenseCellData.setCellValue((String) "" + totalExpense);

			// File Temporary Path Code
			String relativePath = "img/userExportData";
			String rootPath = getServletContext().getRealPath(relativePath);
			File file = new File(rootPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			File dataFile = new File(rootPath + "/" + fileName + ".xlsx");
			if (dataFile.exists()) {
				dataFile.delete();
			}
			FileOutputStream out = new FileOutputStream(dataFile);
			workbook.write(out);
			out.close();

			// Download File Code
			InputStream fis = new FileInputStream(dataFile);
			response.setContentType("application/vnd.ms-excel");
			response.setContentLength((int) dataFile.length());
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".xlsx\"");

			ServletOutputStream os = response.getOutputStream();
			byte[] bufferData = new byte[1024];
			int read = 0;
			while ((read = fis.read(bufferData)) != -1) {
				os.write(bufferData, 0, read);
			}
			os.flush();
			os.close();
			fis.close();

			session.setAttribute("user", userVo);
			if (!response.isCommitted()) {
				response.sendRedirect(request.getContextPath() + "/view/pages/transaction=list.jsp");
			}
		} else {
			session.setAttribute("user", userVo);
			response.sendRedirect(request.getContextPath() + "/view/pages/transaction=list.jsp");
		}
	}

	private void deleteTransaction(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ParseException {
		// TODO Auto-generated method stub
		String transactionIdentificationNumber = request.getParameter("transactionIdentificationNumber");
		TransactionVo transactionVo = new TransactionVo();
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		if (transactionIdentificationNumber != null) {
			TransactionMasterDao transactionMasterDao = new TransactionMasterDao();
			transactionVo = transactionMasterDao.getTransactionByIdentificationNumber(transactionIdentificationNumber);
		}

		transactionVo.setIsDeleted(true);

		TransactionMasterDao transactionMasterDao = new TransactionMasterDao();
		transactionMasterDao.updateTransaction(transactionVo);

		if (true) {
			TransactionMasterDao transactionMasterDao2 = new TransactionMasterDao();
			List<TransactionVo> transactionList = transactionMasterDao2.getAllTransactions(userVo);

			TransactionVo previousVo = null;
			for (TransactionVo vo : transactionList) {
				if (vo.getTransactionId() >= transactionVo.getTransactionId()) {
					if (vo.getForTransaction().equals("income")) {
						if (previousVo == null) {
							vo.setTotalAvailableBalance(vo.getTransactionAmount());
						} else {
							float newAvailableBalance = previousVo.getTotalAvailableBalance()
									+ vo.getTransactionAmount();
							vo.setTotalAvailableBalance(newAvailableBalance);
						}
					} else {
						if (previousVo == null) {
							vo.setTotalAvailableBalance(0 - vo.getTransactionAmount());
						} else {
							float newAvailableBalance = previousVo.getTotalAvailableBalance()
									- vo.getTransactionAmount();
							vo.setTotalAvailableBalance(newAvailableBalance);
						}

					}
					TransactionMasterDao transactionMasterDao3 = new TransactionMasterDao();
					transactionMasterDao3.updateTransaction(vo);
				}
				previousVo = vo;
			}
		}

		if (true) {
			if (transactionVo.getForTransaction().equals("expense")) {
				BudgetMasterDao budgetMasterDao = new BudgetMasterDao();
				List<BudgetVo> budgetList = budgetMasterDao.getAllBudgets(userVo);

				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				Date currentTransactionDate = dateFormat.parse(transactionVo.getTransactionDateTime());
				int currentDate = currentTransactionDate.getDate();
				int currentMonth = currentTransactionDate.getMonth();
				int currentYear = currentTransactionDate.getYear();

				for (BudgetVo budgetVo : budgetList) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date budgetStartDate = format.parse(budgetVo.getBudgetStartDate());
					Date budgetEndDate = format.parse(budgetVo.getBudgetEndDate());

					int startDate = budgetStartDate.getDate();
					int startMonth = budgetStartDate.getMonth();
					int startYear = budgetStartDate.getYear();
					int endDate = budgetEndDate.getDate();
					int endMonth = budgetEndDate.getMonth();
					int endYear = budgetEndDate.getYear();

					if ((currentYear >= startYear) && (currentYear <= endYear)) {
						if ((currentMonth >= startMonth) && (currentMonth <= endMonth)) {
							if ((currentDate >= startDate) && (currentDate <= endDate)) {
								budgetVo.setBudgetAmountLeft(
										budgetVo.getBudgetAmountLeft() + transactionVo.getTransactionAmount());
								BudgetMasterDao budgetMasterDao2 = new BudgetMasterDao();
								budgetMasterDao2.updateBudget(budgetVo);
							}
						}
					}
				}
			}
		}

		TransactionMasterDao transactionMasterDao4 = new TransactionMasterDao();
		List<TransactionVo> transactionBalance = transactionMasterDao4.getLastTransactionForBalance(userVo);
		if (transactionBalance.isEmpty()) {
			session.setAttribute("myBalance", "" + 0.00);
		} else {
			session.setAttribute("myBalance", "" + transactionBalance.get(0).getTotalAvailableBalance());
		}

		session.setAttribute("user", userVo);
		session.setAttribute("userMsg", "Transaction Deleted Successfully.");
		response.sendRedirect(request.getContextPath() + "/TransactionMasterController?flag=loadIncomeTransaction");
	}

	private void editTransaction(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ParseException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		TransactionVo transactionVo = (TransactionVo) session.getAttribute("transactionDetails");

		transactionVo.setUserVo(userVo);

		String payeeName = request.getParameter("payeeName");
		transactionVo.setPayeeName(payeeName);

		float transactionNewAmount = Float.parseFloat(request.getParameter("transactionAmount"));
		float transactionOldAmount = transactionVo.getTransactionAmount();
		transactionVo.setTransactionAmount(transactionNewAmount);

		String transactionDateTime = request.getParameter("transactionDate");
		transactionVo.setTransactionDateTime(transactionDateTime);

		CategoryVo categoryVo = new CategoryVo();
		Set<SubCategoriesVo> subCategoriesVos = new HashSet<SubCategoriesVo>();
		String categoryName = request.getParameter("categoryName");
		boolean isAvailableCategoryFound = false;

		if (!categoryName.equals("")) {
			CategoryMasterDao categoryMasterDao = new CategoryMasterDao();
			List<CategoryVo> categoryList = categoryMasterDao.checkCategoryBasedOnName(categoryName,
					transactionVo.getForTransaction(), userVo);
			categoryMasterDao.dbOperation.session.close();
			if (categoryList.isEmpty()) {
				categoryVo.setCategoryName(categoryName);
				categoryVo.setForCategory(transactionVo.getForTransaction());
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
					transactionVo.getForTransaction(), userVo);
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

		transactionVo.setForTransaction(transactionVo.getForTransaction());

		float difference;

		if (transactionVo.getForTransaction().equals("income")) {
			if (transactionNewAmount > transactionOldAmount) {
				difference = transactionNewAmount - transactionOldAmount;
				float newBalance = transactionVo.getTotalAvailableBalance() + difference;
				transactionVo.setTotalAvailableBalance(newBalance);
			} else {
				difference = transactionOldAmount - transactionNewAmount;
				float newBalance = transactionVo.getTotalAvailableBalance() - difference;
				transactionVo.setTotalAvailableBalance(newBalance);
			}
		} else {
			if (transactionNewAmount > transactionOldAmount) {
				difference = transactionNewAmount - transactionOldAmount;
				float newBalance = transactionVo.getTotalAvailableBalance() - difference;
				transactionVo.setTotalAvailableBalance(newBalance);
			} else {
				difference = transactionOldAmount - transactionNewAmount;
				float newBalance = transactionVo.getTotalAvailableBalance() + difference;
				transactionVo.setTotalAvailableBalance(newBalance);
			}
		}

		transactionVo.setTransactionReceiptImage(transactionVo.getTransactionReceiptImage());
		transactionVo.setIsDeleted(transactionVo.isDeleted());

		TransactionMasterDao transactionMasterDao = new TransactionMasterDao();
		transactionMasterDao.updateTransaction(transactionVo);

		if (true) {
			TransactionMasterDao transactionMasterDao2 = new TransactionMasterDao();
			List<TransactionVo> transactionList = transactionMasterDao2.getAllTransactions(userVo);

			TransactionVo previousVo = null;
			boolean value = false;
			for (TransactionVo vo : transactionList) {
				if (vo.getTransactionId() != transactionVo.getTransactionId()) {
					if (value == false) {
						continue;
					} else {
						if (vo.getForTransaction().equals("income")) {
							float newAvailableBalance = previousVo.getTotalAvailableBalance()
									+ vo.getTransactionAmount();
							vo.setTotalAvailableBalance(newAvailableBalance);
						} else {
							float newAvailableBalance = previousVo.getTotalAvailableBalance()
									- vo.getTransactionAmount();
							vo.setTotalAvailableBalance(newAvailableBalance);
						}
					}
					TransactionMasterDao transactionMasterDao3 = new TransactionMasterDao();
					transactionMasterDao3.updateTransaction(vo);
				} else if (vo.getTransactionId() == transactionVo.getTransactionId()) {
					value = true;
				}
				previousVo = vo;
			}
		}

		TransactionMasterDao transactionMasterDao4 = new TransactionMasterDao();
		List<TransactionVo> transactionBalance = transactionMasterDao4.getLastTransactionForBalance(userVo);
		if (transactionBalance.isEmpty()) {
			session.setAttribute("myBalance", "" + 0.00);
		} else {
			session.setAttribute("myBalance", "" + transactionBalance.get(0).getTotalAvailableBalance());
		}

		if (transactionVo.getForTransaction().equals("expense")) {
			if (true) {
				BudgetMasterDao budgetMasterDao = new BudgetMasterDao();
				List<BudgetVo> budgetList = budgetMasterDao.getAllBudgets(userVo);

				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				Date currentTransactionDate = dateFormat.parse(transactionVo.getTransactionDateTime());
				int currentDate = currentTransactionDate.getDate();
				int currentMonth = currentTransactionDate.getMonth();
				int currentYear = currentTransactionDate.getYear();

				for (BudgetVo budgetVo : budgetList) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date budgetStartDate = format.parse(budgetVo.getBudgetStartDate());
					Date budgetEndDate = format.parse(budgetVo.getBudgetEndDate());

					int startDate = budgetStartDate.getDate();
					int startMonth = budgetStartDate.getMonth();
					int startYear = budgetStartDate.getYear();
					int endDate = budgetEndDate.getDate();
					int endMonth = budgetEndDate.getMonth();
					int endYear = budgetEndDate.getYear();

					if ((currentYear >= startYear) && (currentYear <= endYear)) {
						if ((currentMonth >= startMonth) && (currentMonth <= endMonth)) {
							if ((currentDate >= startDate) && (currentDate <= endDate)) {
								float amount = transactionOldAmount - transactionNewAmount;
								budgetVo.setBudgetAmountLeft(budgetVo.getBudgetAmountLeft() + amount);

								if (budgetVo.getBudgetAmountLeft() <= budgetVo.getBudgetAlertAmount()) {
									NotificationVo notificationVo = new NotificationVo();
									notificationVo.setNotificationDateTime(new Date().toString());
									notificationVo.setNotificationTitle("Budget Alert");
									notificationVo.setNotificationMessage("Alert to notify Budget Named:- "
											+ budgetVo.getBudgetName()
											+ "'s alert amount has been surpassed. Budget Amount Remaining is:- "
											+ budgetVo.getBudgetAmountLeft()
											+ ". If their's a problem review your transactions from Transaction List page.");
									notificationVo.setNotificationType("budgetAmountAlert");
									notificationVo.setRead(false);
									notificationVo.setNotificationUrl(
											"NotificationController?flag=loadBudget&value=" + budgetVo.getBudgetId());
									notificationVo.setUserVo(userVo);

									NotificationDao notificationDao = new NotificationDao();
									notificationDao.addNotification(notificationVo);
									session.setAttribute("checkNotification", true);
								}
								BudgetMasterDao budgetMasterDao2 = new BudgetMasterDao();
								budgetMasterDao2.updateBudget(budgetVo);
							}
						}
					}
				}
			}
		}

		session.setAttribute("user", userVo);
		session.setAttribute("userMsg", "Transaction Updated Successfully.");
		response.sendRedirect(request.getContextPath() + "/TransactionMasterController?flag=loadIncomeTransaction");
	}

	private void loadTransactionDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String transactionIdentificationNumber = request.getParameter("id");
		TransactionVo transactionVo = null;
		if (transactionIdentificationNumber != null) {
			TransactionMasterDao transactionMasterDao = new TransactionMasterDao();
			transactionVo = transactionMasterDao.getTransactionByIdentificationNumber(transactionIdentificationNumber);
		}

		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");
		String forTransaction = transactionVo.getForTransaction();

		if (forTransaction != null) {
			CategoryMasterDao categoryMasterDao = new CategoryMasterDao();
			List<CategoryVo> categoryList = categoryMasterDao.getCategoryList(forTransaction, userVo);
			session.setAttribute("forTransaction", forTransaction);
			session.setAttribute("categoryData", categoryList);

		}
		if (forTransaction != null) {
			SubCategoryDao subCategoryDao = new SubCategoryDao();
			List<SubCategoriesVo> subCategoryList = subCategoryDao
					.getSubCategoryBasedOnName(transactionVo.getCategoryVo(), transactionVo.getUserVo());
			session.setAttribute("subCategoryList", subCategoryList);
		}

		session.setAttribute("user", userVo);
		session.setAttribute("transactionDetails", transactionVo);
		response.sendRedirect(request.getContextPath() + "/view/pages/transaction-detail.jsp");
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
						transactionVo.setTransactionReceiptImage("../../img/transactionImages/"
								+ transactionVo.getTransactionIdentificationNumber() + ".jpg");
						TransactionMasterDao transactionMasterDao = new TransactionMasterDao();
						transactionMasterDao.updateReceiptImage(transactionVo);
						session.setAttribute("imageChanged", true);
						response.sendRedirect(request.getContextPath()
								+ "/TransactionMasterController?flag=loadTransactionDetails&id="
								+ transactionVo.getTransactionIdentificationNumber());
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

	private void addExpenseTransaction(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ParseException {
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

		TransactionMasterDao transactionMasterDao = new TransactionMasterDao();
		transactionMasterDao.addTransaction(transactionVo);

		TransactionMasterDao transactionMasterDao4 = new TransactionMasterDao();
		List<TransactionVo> transactionBalance = transactionMasterDao4.getLastTransactionForBalance(userVo);
		if (transactionBalance.isEmpty()) {
			session.setAttribute("myBalance", "" + 0.00);
		} else {
			session.setAttribute("myBalance", "" + transactionBalance.get(0).getTotalAvailableBalance());
		}

		if (transactionBalance.get(0).getTotalAvailableBalance() < 0) {
			NotificationVo notificationVo = new NotificationVo();
			notificationVo.setNotificationDateTime(new Date().toString());
			notificationVo.setNotificationTitle("Balance Alert");
			notificationVo.setNotificationMessage(
					"Balance has been down to negative value. If their's a problem review your transactions from Transaction List page.");
			notificationVo.setNotificationType("negativeBalance");
			notificationVo.setRead(false);
			notificationVo.setNotificationUrl("TransactionMasterController?flag=loadIncomeTransaction");
			notificationVo.setUserVo(userVo);

			NotificationDao notificationDao = new NotificationDao();
			notificationDao.addNotification(notificationVo);
			session.setAttribute("checkNotification", true);
		}

		if (true) {
			BudgetMasterDao budgetMasterDao = new BudgetMasterDao();
			List<BudgetVo> budgetList = budgetMasterDao.getAllBudgets(userVo);

			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date currentTransactionDate = dateFormat.parse(transactionDateTime);
			int currentDate = currentTransactionDate.getDate();
			int currentMonth = currentTransactionDate.getMonth();
			int currentYear = currentTransactionDate.getYear();

			for (BudgetVo budgetVo : budgetList) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date budgetStartDate = format.parse(budgetVo.getBudgetStartDate());
				Date budgetEndDate = format.parse(budgetVo.getBudgetEndDate());

				int startDate = budgetStartDate.getDate();
				int startMonth = budgetStartDate.getMonth();
				int startYear = budgetStartDate.getYear();
				int endDate = budgetEndDate.getDate();
				int endMonth = budgetEndDate.getMonth();
				int endYear = budgetEndDate.getYear();

				System.out.println("Current dd-mm-yyyy " + currentDate + "-" + currentMonth + "-" + currentYear);
				System.out.println("Budget start:- " + startDate + "-" + startMonth + "-" + startYear);
				System.out.println("End date:- " + endDate + "-" + endMonth + "-" + endYear);

				if ((currentYear >= startYear) && (currentYear <= endYear)) {
					if ((currentMonth >= startMonth) && (currentMonth <= endMonth)) {
						if ((currentDate >= startDate) && (currentDate <= endDate)) {
							budgetVo.setBudgetAmountLeft(
									budgetVo.getBudgetAmountLeft() - transactionVo.getTransactionAmount());

							if (budgetVo.getBudgetAmountLeft() <= budgetVo.getBudgetAlertAmount()) {
								NotificationVo notificationVo = new NotificationVo();
								notificationVo.setNotificationDateTime(new Date().toString());
								notificationVo.setNotificationTitle("Budget Alert");
								notificationVo.setNotificationMessage("Alert to notify Budget Named:- "
										+ budgetVo.getBudgetName()
										+ "'s alert amount has been surpassed. Budget Amount Remaining is:- "
										+ budgetVo.getBudgetAmountLeft()
										+ ". If their's a problem review your transactions from Transaction List page.");
								notificationVo.setNotificationType("budgetAmountAlert");
								notificationVo.setRead(false);
								notificationVo.setNotificationUrl(
										"NotificationController?flag=loadBudget&value=" + budgetVo.getBudgetId());
								notificationVo.setUserVo(userVo);

								NotificationDao notificationDao = new NotificationDao();
								notificationDao.addNotification(notificationVo);
								session.setAttribute("checkNotification", true);
							}

							BudgetMasterDao budgetMasterDao2 = new BudgetMasterDao();
							budgetMasterDao2.updateBudget(budgetVo);
						}
					}
				}
			}
		}

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

		TransactionMasterDao transactionMasterDao = new TransactionMasterDao();
		transactionMasterDao.addTransaction(transactionVo);

		TransactionMasterDao transactionMasterDao4 = new TransactionMasterDao();
		List<TransactionVo> transactionBalance = transactionMasterDao4.getLastTransactionForBalance(userVo);
		if (transactionBalance.isEmpty()) {
			session.setAttribute("myBalance", "" + 0.00);
		} else {
			session.setAttribute("myBalance", "" + transactionBalance.get(0).getTotalAvailableBalance());
		}

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