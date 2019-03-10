package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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

import dao.BudgetMasterDao;
import dao.NotificationDao;
import dao.ProductDao;
import dao.PurchaseDao;
import dao.SalesDao;
import dao.StockDao;
import dao.TrackingMasterDao;
import dao.TransactionMasterDao;
import dao.UserMasterDao;
import global.GraphData;
import vo.BudgetVo;
import vo.NotificationVo;
import vo.ProductVo;
import vo.PurchaseVo;
import vo.SalesVo;
import vo.StockVo;
import vo.SuperUserVo;
import vo.TrackingVo;
import vo.TransactionVo;
import vo.UserVo;
import vo.Way2SmsPost;

/**
 * Servlet implementation class UserMasterController
 */
@WebServlet("/UserMasterController")
public class UserMasterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String apiKey = "J9J5E9UILDG7RNI36ZDEPMZUJ8P4VQZC";
	private final String secretKey = "1X6EG9LRITX1755Y";
	private final String useType = "stage";
	private final String senderId = "SPENDR";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserMasterController() {
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
		// response.getWriter().append("Served at: ").append(request.getContextPath());

		String flag = request.getParameter("flag");

		if (flag.equals("signup")) {
			registerUser(request, response);
		} else if (flag.equals("login")) {
			try {
				loginUser(request, response);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
		} else if (flag.equals("verifyOtp")) {
			verifyOtp(request, response);
		} else if (flag.equals("forgot-password")) {
			forgotPassword(request, response);
		} else if (flag.equals("change-password")) {
			changePassword(request, response);
		} else if (flag.equals("uploadProfileImage")) {
			uploadProfileImage(request, response);
		} else if (flag.equals("userUpdate")) {
			userUpdate(request, response);
		} else if (flag.equals("deleteUser")) {
			deleteUser(request, response);
		} else if (flag.equals("changePassword")) {
			changeNewPassword(request, response);
		} else if (flag.equals("exportData")) {
			exportData(request, response);
		} else if (flag.equals("loadDashboard")) {
			try {
				loadDashboard(request, response);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
		} else if (flag.equals("changeThemeDiv")) {
			changeThemeDiv(request, response);
		} else if(flag.equals("deactivateUserByAdmin")) {
			deactivateUserByAdmin(request,response);
		}
	}

	private void deactivateUserByAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		SuperUserVo superUserVo=(SuperUserVo)session.getAttribute("superUser");
		
		int userId = Integer.parseInt(request.getParameter("userId"));
		UserVo userVo=new UserVo();
		userVo.setUserId(userId);
		
		if(true) {
			UserMasterDao userMasterDao=new UserMasterDao();
			List<UserVo> list = userMasterDao.getUserDetails(userVo);
			userVo=list.get(0);
		}
		userVo.setDeactivated(true);
		
		UserMasterDao userMasterDao=new UserMasterDao();
		userMasterDao.updateUser(userVo);
		
		session.setAttribute("superUser", superUserVo);
		response.sendRedirect(request.getContextPath() + "/SuperUserController?flag=listAllUsers");
	}

	private void changeThemeDiv(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String classList = request.getParameter("value");
		String[] classLst = classList.split(" ");

		String finalClass = "";
		for (String str : classLst) {
			if (!str.equalsIgnoreCase("open-setting-panel")) {
				if (!str.equalsIgnoreCase("no-transition")) {
					finalClass += str + " ";
				}
			}
		}
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");
		userVo.setPreLoaderClass(finalClass);

		UserMasterDao userMasterDao = new UserMasterDao();
		userMasterDao.updateUser(userVo);

		session.setAttribute("user", userVo);
		response.getWriter().println(finalClass);
	}

	private void loadDashboard(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ParseException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		String currentBalance = "0.00", todayIncome = "0.00", weekIncome = "0.00", monthIncome = "0.00",
				yearIncome = "0.00";
		String todayExpense = "0.00", weekExpense = "0.00", monthExpense = "0.00", yearExpense = "0.00";

		TransactionMasterDao transactionMasterDao = new TransactionMasterDao();
		List<TransactionVo> transactionList = transactionMasterDao.getAllTransactions(userVo);
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

		// Current Balance
		if (true) {
			TransactionMasterDao transactionMasterDao2 = new TransactionMasterDao();
			List<TransactionVo> list = transactionMasterDao2.getLastTransactionForBalance(userVo);
			if (list.isEmpty()) {
				currentBalance = "0.00";
			} else {
				currentBalance = "" + list.get(0).getTotalAvailableBalance();
			}
		}

		// Today's Income
		Date todayDate = new Date();
		for (TransactionVo transactionVo : transactionList) {
			if (transactionVo.getForTransaction().equals("income")) {
				Date tranDate = dateFormat.parse(transactionVo.getTransactionDateTime());
				if ((tranDate.getDate() == todayDate.getDate()) && (tranDate.getMonth() == todayDate.getMonth())
						&& (tranDate.getYear() == todayDate.getYear())) {
					float transactionAmount = transactionVo.getTransactionAmount();
					todayIncome = "" + (Float.parseFloat(todayIncome) + transactionAmount);
				}
			}
		}

		// Today's Expense
		for (TransactionVo transactionVo : transactionList) {
			if (transactionVo.getForTransaction().equals("expense")) {
				Date tranDate = dateFormat.parse(transactionVo.getTransactionDateTime());
				if ((tranDate.getDate() == todayDate.getDate()) && (tranDate.getMonth() == todayDate.getMonth())
						&& (tranDate.getYear() == todayDate.getYear())) {
					float transactionAmount = transactionVo.getTransactionAmount();
					todayExpense = "" + (Float.parseFloat(todayExpense) + transactionAmount);
				}
			}
		}

		// Month's Income
		for (TransactionVo transactionVo : transactionList) {
			if (transactionVo.getForTransaction().equals("income")) {
				Date tranDate = dateFormat.parse(transactionVo.getTransactionDateTime());
				if ((tranDate.getMonth() == todayDate.getMonth()) && (tranDate.getYear() == todayDate.getYear())) {
					float transactionAmount = transactionVo.getTransactionAmount();
					monthIncome = "" + (Float.parseFloat(monthIncome) + transactionAmount);
				}
			}
		}

		// Month's Expense
		for (TransactionVo transactionVo : transactionList) {
			if (transactionVo.getForTransaction().equals("expense")) {
				Date tranDate = dateFormat.parse(transactionVo.getTransactionDateTime());
				if ((tranDate.getMonth() == todayDate.getMonth()) && (tranDate.getYear() == todayDate.getYear())) {
					float transactionAmount = transactionVo.getTransactionAmount();
					monthExpense = "" + (Float.parseFloat(monthExpense) + transactionAmount);
				}
			}
		}

		// Year's Income
		for (TransactionVo transactionVo : transactionList) {
			if (transactionVo.getForTransaction().equals("income")) {
				Date tranDate = dateFormat.parse(transactionVo.getTransactionDateTime());
				if ((tranDate.getYear() == todayDate.getYear())) {
					float transactionAmount = transactionVo.getTransactionAmount();
					yearIncome = "" + (Float.parseFloat(yearIncome) + transactionAmount);
				}
			}
		}

		// Year's Expense
		for (TransactionVo transactionVo : transactionList) {
			if (transactionVo.getForTransaction().equals("expense")) {
				Date tranDate = dateFormat.parse(transactionVo.getTransactionDateTime());
				if ((tranDate.getYear() == todayDate.getYear())) {
					float transactionAmount = transactionVo.getTransactionAmount();
					yearExpense = "" + (Float.parseFloat(yearExpense) + transactionAmount);
				}
			}
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(todayDate);
		int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);

		// Week's Income
		for (TransactionVo transactionVo : transactionList) {
			if (transactionVo.getForTransaction().equals("income")) {
				Date tranDate = dateFormat.parse(transactionVo.getTransactionDateTime());
				calendar.setTime(tranDate);
				int tranWeek = calendar.get(Calendar.WEEK_OF_YEAR);
				if (currentWeek == tranWeek) {
					float transactionAmount = transactionVo.getTransactionAmount();
					weekIncome = "" + (Float.parseFloat(weekIncome) + transactionAmount);
				}
			}
		}

		// Week's Expense
		for (TransactionVo transactionVo : transactionList) {
			if (transactionVo.getForTransaction().equals("expense")) {
				Date tranDate = dateFormat.parse(transactionVo.getTransactionDateTime());
				calendar.setTime(tranDate);
				int tranWeek = calendar.get(Calendar.WEEK_OF_YEAR);
				if (currentWeek == tranWeek) {
					float transactionAmount = transactionVo.getTransactionAmount();
					weekExpense = "" + (Float.parseFloat(weekExpense) + transactionAmount);
				}
			}
		}

		// Monthly Income Expense Calculations
		GraphData graphData = new GraphData();
		for (TransactionVo transactionVo : transactionList) {
			if (transactionVo.getForTransaction().equals("income")) {
				Date tranDate = dateFormat.parse(transactionVo.getTransactionDateTime());
				if (tranDate.getMonth() == 0 && tranDate.getYear() == todayDate.getYear()) {
					float janIncome = Float.parseFloat(graphData.getJanIncome()) + transactionVo.getTransactionAmount();
					graphData.setJanIncome("" + janIncome);
				} else if (tranDate.getMonth() == 1 && tranDate.getYear() == todayDate.getYear()) {
					float febIncome = Float.parseFloat(graphData.getFebIncome()) + transactionVo.getTransactionAmount();
					graphData.setFebIncome("" + febIncome);
				} else if (tranDate.getMonth() == 2 && tranDate.getYear() == todayDate.getYear()) {
					float marIncome = Float.parseFloat(graphData.getMarIncome()) + transactionVo.getTransactionAmount();
					graphData.setMarIncome("" + marIncome);
				} else if (tranDate.getMonth() == 3 && tranDate.getYear() == todayDate.getYear()) {
					float aprIncome = Float.parseFloat(graphData.getAprIncome()) + transactionVo.getTransactionAmount();
					graphData.setAprIncome("" + aprIncome);
				} else if (tranDate.getMonth() == 4 && tranDate.getYear() == todayDate.getYear()) {
					float mayIncome = Float.parseFloat(graphData.getMayIncome()) + transactionVo.getTransactionAmount();
					graphData.setMayIncome("" + mayIncome);
				} else if (tranDate.getMonth() == 5 && tranDate.getYear() == todayDate.getYear()) {
					float juneIncome = Float.parseFloat(graphData.getJuneIncome())
							+ transactionVo.getTransactionAmount();
					graphData.setJuneIncome("" + juneIncome);
				} else if (tranDate.getMonth() == 6 && tranDate.getYear() == todayDate.getYear()) {
					float julyIncome = Float.parseFloat(graphData.getJulyIncome())
							+ transactionVo.getTransactionAmount();
					graphData.setJulyIncome("" + julyIncome);
				} else if (tranDate.getMonth() == 7 && tranDate.getYear() == todayDate.getYear()) {
					float augIncome = Float.parseFloat(graphData.getAugIncome()) + transactionVo.getTransactionAmount();
					graphData.setAugIncome("" + augIncome);
				} else if (tranDate.getMonth() == 8 && tranDate.getYear() == todayDate.getYear()) {
					float sepIncome = Float.parseFloat(graphData.getSepIncome()) + transactionVo.getTransactionAmount();
					graphData.setSepIncome("" + sepIncome);
				} else if (tranDate.getMonth() == 9 && tranDate.getYear() == todayDate.getYear()) {
					float octIncome = Float.parseFloat(graphData.getOctIncome()) + transactionVo.getTransactionAmount();
					graphData.setOctIncome("" + octIncome);
				} else if (tranDate.getMonth() == 10 && tranDate.getYear() == todayDate.getYear()) {
					float novIncome = Float.parseFloat(graphData.getNovIncome()) + transactionVo.getTransactionAmount();
					graphData.setNovIncome("" + novIncome);
				} else if (tranDate.getMonth() == 11 && tranDate.getYear() == todayDate.getYear()) {
					float decIncome = Float.parseFloat(graphData.getDecIncome()) + transactionVo.getTransactionAmount();
					graphData.setDecIncome("" + decIncome);
				}
			}
		}
		for (TransactionVo transactionVo : transactionList) {
			if (transactionVo.getForTransaction().equals("expense")) {
				Date tranDate = dateFormat.parse(transactionVo.getTransactionDateTime());
				if (tranDate.getMonth() == 0 && tranDate.getYear() == todayDate.getYear()) {
					float janExpense = Float.parseFloat(graphData.getJanExpense())
							+ transactionVo.getTransactionAmount();
					graphData.setJanExpense("" + janExpense);
				} else if (tranDate.getMonth() == 1 && tranDate.getYear() == todayDate.getYear()) {
					float febExpense = Float.parseFloat(graphData.getFebExpense())
							+ transactionVo.getTransactionAmount();
					graphData.setFebExpense("" + febExpense);
				} else if (tranDate.getMonth() == 2 && tranDate.getYear() == todayDate.getYear()) {
					float marExpense = Float.parseFloat(graphData.getMarExpense())
							+ transactionVo.getTransactionAmount();
					graphData.setMarExpense("" + marExpense);
				} else if (tranDate.getMonth() == 3 && tranDate.getYear() == todayDate.getYear()) {
					float aprExpense = Float.parseFloat(graphData.getAprExpense())
							+ transactionVo.getTransactionAmount();
					graphData.setAprExpense("" + aprExpense);
				} else if (tranDate.getMonth() == 4 && tranDate.getYear() == todayDate.getYear()) {
					float mayExpense = Float.parseFloat(graphData.getMayExpense())
							+ transactionVo.getTransactionAmount();
					graphData.setMayExpense("" + mayExpense);
				} else if (tranDate.getMonth() == 5 && tranDate.getYear() == todayDate.getYear()) {
					float juneExpense = Float.parseFloat(graphData.getJuneExpense())
							+ transactionVo.getTransactionAmount();
					graphData.setJuneExpense("" + juneExpense);
				} else if (tranDate.getMonth() == 6 && tranDate.getYear() == todayDate.getYear()) {
					float julyExpense = Float.parseFloat(graphData.getJulyExpense())
							+ transactionVo.getTransactionAmount();
					graphData.setJulyExpense("" + julyExpense);
				} else if (tranDate.getMonth() == 7 && tranDate.getYear() == todayDate.getYear()) {
					float augExpense = Float.parseFloat(graphData.getAugExpense())
							+ transactionVo.getTransactionAmount();
					graphData.setAugExpense("" + augExpense);
				} else if (tranDate.getMonth() == 8 && tranDate.getYear() == todayDate.getYear()) {
					float sepExpense = Float.parseFloat(graphData.getSepExpense())
							+ transactionVo.getTransactionAmount();
					graphData.setSepExpense("" + sepExpense);
				} else if (tranDate.getMonth() == 9 && tranDate.getYear() == todayDate.getYear()) {
					float octExpense = Float.parseFloat(graphData.getOctExpense())
							+ transactionVo.getTransactionAmount();
					graphData.setOctExpense("" + octExpense);
				} else if (tranDate.getMonth() == 10 && tranDate.getYear() == todayDate.getYear()) {
					float novExpense = Float.parseFloat(graphData.getNovExpense())
							+ transactionVo.getTransactionAmount();
					graphData.setNovExpense("" + novExpense);
				} else if (tranDate.getMonth() == 11 && tranDate.getYear() == todayDate.getYear()) {
					float decExpense = Float.parseFloat(graphData.getDecExpense())
							+ transactionVo.getTransactionAmount();
					graphData.setDecExpense("" + decExpense);
				}
			}
		}

		session.setAttribute("currentBalance", currentBalance);
		session.setAttribute("todayIncome", todayIncome);
		session.setAttribute("todayExpense", todayExpense);
		session.setAttribute("weekIncome", weekIncome);
		session.setAttribute("weekExpense", weekExpense);
		session.setAttribute("monthIncome", monthIncome);
		session.setAttribute("monthExpense", monthExpense);
		session.setAttribute("yearIncome", yearIncome);
		session.setAttribute("yearExpense", yearExpense);
		session.setAttribute("graphData", graphData);
		session.setAttribute("user", userVo);
		response.sendRedirect(request.getContextPath() + "/view/pages/home.jsp");
	}

	private void exportData(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String fileName = request.getParameter("userFileName");
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		// Create blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet("Transactions");

		// Create row object
		XSSFRow row;

		TransactionMasterDao transactionMasterDao = new TransactionMasterDao();
		List<TransactionVo> transactionList = transactionMasterDao.getAllTransactions(userVo);

		if (true) {
			row = spreadsheet.createRow(0);
			Cell cell1 = row.createCell(0);
			cell1.setCellValue((String) "Id");

			Cell cell2 = row.createCell(1);
			cell2.setCellValue((String) "For Transaction");

			Cell cell3 = row.createCell(2);
			cell3.setCellValue((String) "Payee Name");

			Cell cell4 = row.createCell(3);
			cell4.setCellValue((String) "Amount");

			Cell cell5 = row.createCell(4);
			cell5.setCellValue((String) "Date");

			Cell cell6 = row.createCell(5);
			cell6.setCellValue((String) "Available Balance");

			Cell cell7 = row.createCell(6);
			cell7.setCellValue((String) "Category Name");

			Cell cell8 = row.createCell(7);
			cell8.setCellValue((String) "Sub-Category Name");

			Cell cell9 = row.createCell(8);
			cell9.setCellValue((String) "Payment Method");

			Cell cell10 = row.createCell(9);
			cell10.setCellValue((String) "Status");

			Cell cell11 = row.createCell(10);
			cell11.setCellValue((String) "Reference Number");

			Cell cell12 = row.createCell(11);
			cell12.setCellValue((String) "Extra Description");
		}

		int rowid = 1;
		for (TransactionVo transactionVo : transactionList) {
			row = spreadsheet.createRow(rowid++);
			Cell cell1 = row.createCell(0);
			cell1.setCellValue((String) "" + transactionVo.getTransactionIdentificationNumber());

			Cell cell2 = row.createCell(1);
			cell2.setCellValue((String) "" + transactionVo.getForTransaction());

			Cell cell3 = row.createCell(2);
			cell3.setCellValue((String) "" + transactionVo.getPayeeName());

			Cell cell4 = row.createCell(3);
			cell4.setCellValue((String) "" + transactionVo.getTransactionAmount());

			Cell cell5 = row.createCell(4);
			cell5.setCellValue((String) "" + transactionVo.getTransactionDateTime());

			Cell cell6 = row.createCell(5);
			cell6.setCellValue((String) "" + transactionVo.getTotalAvailableBalance());

			Cell cell7 = row.createCell(6);
			cell7.setCellValue((String) "" + transactionVo.getCategoryVo().getCategoryName());

			Cell cell8 = row.createCell(7);
			cell8.setCellValue((String) "" + transactionVo.getSubCategoriesVo().getSubCategoryName());

			Cell cell9 = row.createCell(8);
			cell9.setCellValue((String) "" + transactionVo.getPaymentMethod());

			Cell cell10 = row.createCell(9);
			cell10.setCellValue((String) "" + transactionVo.getStatusOfTransaction());

			Cell cell11 = row.createCell(10);
			cell11.setCellValue((String) "" + transactionVo.getTransactionNumber());

			Cell cell12 = row.createCell(11);
			cell12.setCellValue((String) "" + transactionVo.getExtraDescription());
		}

		// Budget Data Write to file
		XSSFSheet budgetSheet = workbook.createSheet("Budgets");
		XSSFRow row2;

		BudgetMasterDao budgetMasterDao = new BudgetMasterDao();
		List<BudgetVo> budgetList = budgetMasterDao.getAllBudgets(userVo);

		if (true) {
			row2 = budgetSheet.createRow(0);
			Cell cell1 = row2.createCell(0);
			cell1.setCellValue((String) "Id");

			Cell cell2 = row2.createCell(1);
			cell2.setCellValue((String) "Name");

			Cell cell3 = row2.createCell(2);
			cell3.setCellValue((String) "Amount");

			Cell cell4 = row2.createCell(3);
			cell4.setCellValue((String) "Start Date");

			Cell cell5 = row2.createCell(4);
			cell5.setCellValue((String) "End Date");

			Cell cell6 = row2.createCell(5);
			cell6.setCellValue((String) "Amount Left");

			Cell cell7 = row2.createCell(6);
			cell7.setCellValue((String) "Alert Amount");

			Cell cell8 = row2.createCell(7);
			cell8.setCellValue((String) "Extra Description");
		}

		rowid = 1;
		for (BudgetVo budgetVo : budgetList) {
			row2 = budgetSheet.createRow(rowid++);
			Cell cell1 = row2.createCell(0);
			cell1.setCellValue((String) "" + (rowid - 1));

			Cell cell2 = row2.createCell(1);
			cell2.setCellValue((String) "" + budgetVo.getBudgetName());

			Cell cell3 = row2.createCell(2);
			cell3.setCellValue((String) "" + budgetVo.getBudgetAmount());

			Cell cell4 = row2.createCell(3);
			cell4.setCellValue((String) "" + budgetVo.getBudgetStartDate());

			Cell cell5 = row2.createCell(4);
			cell5.setCellValue((String) "" + budgetVo.getBudgetEndDate());

			Cell cell6 = row2.createCell(5);
			cell6.setCellValue((String) "" + budgetVo.getBudgetAmountLeft());

			Cell cell7 = row2.createCell(6);
			cell7.setCellValue((String) "" + budgetVo.getBudgetAlertAmount());

			Cell cell8 = row2.createCell(7);
			cell8.setCellValue((String) "" + budgetVo.getBudgetDescription());
		}

		if (userVo.isStockPermission() == true) {
			// Product Data Write to file
			XSSFSheet productSheet = workbook.createSheet("Products");
			XSSFRow row3;

			ProductDao productDao = new ProductDao();
			List<ProductVo> productList = productDao.loadProducts(userVo);

			if (true) {
				row3 = productSheet.createRow(0);
				Cell cell1 = row3.createCell(0);
				cell1.setCellValue((String) "Id");

				Cell cell2 = row3.createCell(1);
				cell2.setCellValue((String) "Product Name");

				Cell cell3 = row3.createCell(2);
				cell3.setCellValue((String) "Brand Name");

				Cell cell4 = row3.createCell(3);
				cell4.setCellValue((String) "Unit Of Mesaurement");
			}

			rowid = 1;
			for (ProductVo productVo : productList) {
				row3 = productSheet.createRow(rowid++);
				Cell cell1 = row3.createCell(0);
				cell1.setCellValue((String) "" + (rowid - 1));

				Cell cell2 = row3.createCell(1);
				cell2.setCellValue((String) "" + productVo.getProductName());

				Cell cell3 = row3.createCell(2);
				cell3.setCellValue((String) "" + productVo.getBrandName());

				Cell cell4 = row3.createCell(3);
				cell4.setCellValue((String) "" + productVo.getUnitOfMesaurement());
			}

			// Purchase Data Write to file
			XSSFSheet purchaseSheet = workbook.createSheet("Purchases");
			XSSFRow row4;

			PurchaseDao purchaseDao = new PurchaseDao();
			List<PurchaseVo> purchaseList = purchaseDao.loadPurchases(userVo);

			if (true) {
				row4 = purchaseSheet.createRow(0);
				Cell cell1 = row4.createCell(0);
				cell1.setCellValue((String) "Id");

				Cell cell2 = row4.createCell(1);
				cell2.setCellValue((String) "Purchase Identification Number");

				Cell cell3 = row4.createCell(2);
				cell3.setCellValue((String) "Dealer Name");

				Cell cell4 = row4.createCell(3);
				cell4.setCellValue((String) "Purchase Date & Time");

				Cell cell5 = row4.createCell(4);
				cell5.setCellValue((String) "Product Name");

				Cell cell6 = row4.createCell(5);
				cell6.setCellValue((String) "Purchase Price");

				Cell cell7 = row4.createCell(6);
				cell7.setCellValue((String) "Purchase Quantity");
			}

			rowid = 1;
			for (PurchaseVo purchaseVo : purchaseList) {
				row4 = purchaseSheet.createRow(rowid++);
				Cell cell1 = row4.createCell(0);
				cell1.setCellValue((String) "" + (rowid - 1));

				Cell cell2 = row4.createCell(1);
				cell2.setCellValue((String) "" + purchaseVo.getPurchaseIdentificationNumber());

				Cell cell3 = row4.createCell(2);
				cell3.setCellValue((String) "" + purchaseVo.getDealerName());

				Cell cell4 = row4.createCell(3);
				cell4.setCellValue((String) "" + purchaseVo.getPurchaseDateTime());

				Cell cell5 = row4.createCell(4);
				cell5.setCellValue((String) "" + purchaseVo.getProductVo().getProductName());

				Cell cell6 = row4.createCell(5);
				cell6.setCellValue((String) "" + purchaseVo.getPurchasePrice());

				Cell cell7 = row4.createCell(6);
				cell7.setCellValue((String) "" + purchaseVo.getQuantity());
			}

			// Sales Data Write to file
			XSSFSheet salesSheet = workbook.createSheet("Sales");
			XSSFRow row5;

			SalesDao salesDao = new SalesDao();
			List<SalesVo> salesList = salesDao.loadSales(userVo);

			if (true) {
				row5 = salesSheet.createRow(0);
				Cell cell1 = row5.createCell(0);
				cell1.setCellValue((String) "Id");

				Cell cell2 = row5.createCell(1);
				cell2.setCellValue((String) "Sales Identification Number");

				Cell cell3 = row5.createCell(2);
				cell3.setCellValue((String) "Customer Name");

				Cell cell4 = row5.createCell(3);
				cell4.setCellValue((String) "Sales Date & Time");

				Cell cell5 = row5.createCell(4);
				cell5.setCellValue((String) "Product Name");

				Cell cell6 = row5.createCell(5);
				cell6.setCellValue((String) "Sales Price");

				Cell cell7 = row5.createCell(6);
				cell7.setCellValue((String) "Sales Quantity");

				Cell cell8 = row5.createCell(7);
				cell8.setCellValue((String) "Sales Discount");
			}

			rowid = 1;
			for (SalesVo salesVo : salesList) {
				row5 = salesSheet.createRow(rowid++);
				Cell cell1 = row5.createCell(0);
				cell1.setCellValue((String) "" + (rowid - 1));

				Cell cell2 = row5.createCell(1);
				cell2.setCellValue((String) "" + salesVo.getSalesIdentificationNumber());

				Cell cell3 = row5.createCell(2);
				cell3.setCellValue((String) "" + salesVo.getCustomerName());

				Cell cell4 = row5.createCell(3);
				cell4.setCellValue((String) "" + salesVo.getSalesDateTime());

				Cell cell5 = row5.createCell(4);
				cell5.setCellValue((String) "" + salesVo.getProductVo().getProductName());

				Cell cell6 = row5.createCell(5);
				cell6.setCellValue((String) "" + salesVo.getSalesPrice());

				Cell cell7 = row5.createCell(6);
				cell7.setCellValue((String) "" + salesVo.getSalesQuantity());

				Cell cell8 = row5.createCell(7);
				cell8.setCellValue((String) "" + salesVo.getDiscountPrice());
			}

			// Product Data Write to file
			XSSFSheet stockSheet = workbook.createSheet("Stock");
			XSSFRow row6;

			StockDao stockDao=new StockDao();
			List<StockVo> stockList = stockDao.loadStock(userVo);

			if (true) {
				row6 = stockSheet.createRow(0);
				Cell cell1 = row6.createCell(0);
				cell1.setCellValue((String) "Id");

				Cell cell2 = row6.createCell(1);
				cell2.setCellValue((String) "Product Name");

				Cell cell3 = row6.createCell(2);
				cell3.setCellValue((String) "Quantity Left");
			}

			rowid = 1;
			for (StockVo stockVo : stockList) {
				row6 = stockSheet.createRow(rowid++);
				Cell cell1 = row6.createCell(0);
				cell1.setCellValue((String) "" + (rowid - 1));

				Cell cell2 = row6.createCell(1);
				cell2.setCellValue((String) "" + stockVo.getProductVo().getProductName());

				Cell cell3 = row6.createCell(2);
				cell3.setCellValue((String) "" + stockVo.getQuantityLeft());
			}
		}

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
			response.sendRedirect(request.getContextPath() + "/view/user/user-settings.jsp");
		}
	}

	private void changeNewPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		String userNewPassword = request.getParameter("userNewPassword");
		session.setAttribute("loginPassword", true);
		session.setAttribute("userNewPassword", userNewPassword);

		Random random = new Random();
		int otp = random.nextInt(999999);
		session.setAttribute("otpValue", otp);

		// Send OPT Code
		Way2SmsPost smsPost = new Way2SmsPost();
		String phone = userVo.getUserMobile();
		String message = "Your OTP for Smart-Spender Change Password is:- " + otp
				+ ". Enter OTP to complete your update request.";
		smsPost.sendCampaign(apiKey, secretKey, useType, phone, message, senderId);

		session.setAttribute("user", userVo);
		response.sendRedirect(request.getContextPath() + "/view/user/otp-verification.jsp");
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String userId = request.getParameter("userId");

		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		if (Integer.parseInt(userId) == userVo.getUserId()) {
			userVo.setIsDeleted("1");
			UserMasterDao userMasterDao = new UserMasterDao();
			userMasterDao.updateUser(userVo);
		}

		response.sendRedirect(request.getContextPath() + "/view/user/user-logout.jsp");
	}

	private void userUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		String userName = request.getParameter("userName");
		String userEmail = request.getParameter("userEmail");
		String userMobile = request.getParameter("userMobile");
		String userGender = request.getParameter("userGender");
		String userCity = request.getParameter("userCity");
		String userPincode = request.getParameter("userPincode");
		String userDob = request.getParameter("userDob");

		if (userVo.getUserMobile().equals(userMobile)) {
			userVo.setUserName(userName);
			userVo.setUserEmail(userEmail);
			userVo.setUserMobile(userMobile);
			userVo.setUserGender(userGender);
			userVo.setUserCity(userCity);
			userVo.setUserPinCode(userPincode);
			userVo.setUserDob(userDob);

			UserMasterDao userMasterDao = new UserMasterDao();
			userMasterDao.updateUser(userVo);

			session.setAttribute("user", userVo);
			session.setAttribute("userMsg", "Profile Details successfully updated");
			response.sendRedirect(request.getContextPath() + "/view/user/user-settings.jsp");
		} else {
			userVo.setUserName(userName);
			userVo.setUserEmail(userEmail);
			userVo.setUserMobile(userMobile);
			userVo.setUserGender(userGender);
			userVo.setUserCity(userCity);
			userVo.setUserPinCode(userPincode);
			userVo.setUserDob(userDob);

			session.setAttribute("user", userVo);
			session.setAttribute("mobileChange", true);

			Random random = new Random();
			int otp = random.nextInt(999999);

			session.setAttribute("otpValue", otp);
			// Send OPT Code
			Way2SmsPost smsPost = new Way2SmsPost();
			String phone = userVo.getUserMobile();
			String message = "Your OTP for Smart-Spender Mobile Number Updation is:- " + otp
					+ ". Enter OTP to complete your update request.";
			smsPost.sendCampaign(apiKey, secretKey, useType, phone, message, senderId);

			response.sendRedirect(request.getContextPath() + "/view/user/otp-verification.jsp");
		}
	}

	private void uploadProfileImage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// String rootPath = System.getProperty("catalina.home");
		// to store image in user project directory use System.getProperty("user.dir");
		/*
		 * File currentDirFile = new File("."); String helper =
		 * currentDirFile.getAbsolutePath(); String currentDir = helper.substring(0,
		 * helper.length() - currentDirFile.getCanonicalPath().length());
		 * System.out.println(currentDir);
		 */

		String relativePath = "img/profile";
		String rootPath = getServletContext().getRealPath(relativePath);
		File file = new File(rootPath);
		if (!file.exists()) {
			file.mkdirs();
		}

		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new ServletException("Content type is not multipart/form-data");
		}

		PrintWriter out = response.getWriter();
		DiskFileItemFactory fileFactory = new DiskFileItemFactory();
		fileFactory.setRepository(file);
		ServletFileUpload uploader = new ServletFileUpload(fileFactory);
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		try {
			List<FileItem> fileItemsList = uploader.parseRequest(request);
			Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
			while (fileItemsIterator.hasNext()) {
				FileItem fileItem = fileItemsIterator.next();

				File uploadFile = new File(file + File.separator + userVo.getUserEmail() + ".jpg");
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
						userVo.setUserImage("../../img/profile/" + userVo.getUserEmail() + ".jpg");
						UserMasterDao userMasterDao = new UserMasterDao();
						userMasterDao.updateUser(userVo);
						session.setAttribute("imageChanged", true);
					}
				} else {
					session.setAttribute("userMsg", "Can only upload jpeg files");
				}
			}
		} catch (FileUploadException e) {
			out.write("Exception in uploading file. FIleUpload");
		} catch (Exception e) {
			out.write("Exception in uploading file.");
		}
		session.setAttribute("user", userVo);
		response.sendRedirect(request.getContextPath() + "/view/user/user-settings.jsp");
	}

	private void loginUser(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ParseException {
		// TODO Auto-generated method stub
		String userEmail = request.getParameter("userEmail");
		String userPassword = request.getParameter("userPassword");

		UserVo userVo = new UserVo();
		userVo.setUserEmail(userEmail);
		userVo.setUserPassword(userPassword);

		UserMasterDao userMasterDao = new UserMasterDao();
		List<UserVo> userList = userMasterDao.loginUser(userVo);

		HttpSession session = request.getSession();
		if (userList.isEmpty()) {
			session.setAttribute("userExists", true);
			session.setAttribute("userMsg", "Email or Password is incorrect.");
			response.sendRedirect(request.getContextPath() + "/view/user/login.jsp");
		} else {
			userVo = userList.get(0);
			if (userVo.isDeactivated() == true) {
				session.setAttribute("userExists", true);
				session.setAttribute("userMsg",
						"This account has been Deactivated by Admin. Please contact admin for more details regarding your account.");
				response.sendRedirect(request.getContextPath() + "/view/user/login.jsp");
			} else if (userVo.getIsActive().equals("0")) {
				userVo.setIsActive("1");
				UserMasterDao masterDao = new UserMasterDao();
				masterDao.updateUser(userVo);

				TrackingVo trackingVo = new TrackingVo();
				trackingVo.setBrowserName(request.getHeader("user-agent"));
				trackingVo.setHostName(request.getRemoteHost());
				trackingVo.setIpAddress(request.getRemoteAddr());
				trackingVo.setLoginDateTime(new Date().toString());
				trackingVo.setPortNumber("" + request.getRemotePort());
				trackingVo.setUserEmail(userVo.getUserEmail());
				trackingVo.setUserVo(userVo);

				TrackingMasterDao trackingMasterDao = new TrackingMasterDao();
				trackingMasterDao.addTrack(trackingVo);

				TransactionMasterDao transactionMasterDao4 = new TransactionMasterDao();
				List<TransactionVo> transactionBalance = transactionMasterDao4.getLastTransactionForBalance(userVo);
				if (transactionBalance.isEmpty()) {
					session.setAttribute("myBalance", "" + 0.00);
				} else {
					session.setAttribute("myBalance", "" + transactionBalance.get(0).getTotalAvailableBalance());
				}

				if (true) {
					NotificationDao notificationDao = new NotificationDao();
					List<NotificationVo> notificationsList = notificationDao.getAllNotifications(userVo);
					session.setAttribute("notificationsList", notificationsList);
					session.setAttribute("notificationSize", notificationsList.size());
				}

				session.setAttribute("user", userVo);
				loadDashboard(request, response);
//				response.sendRedirect(request.getContextPath() + "/view/pages/home.jsp");
			} else {
				session.setAttribute("user", userVo);
				session.setAttribute("userExists", true);
				session.setAttribute("userMsg",
						"This account is already logged in from another source. If it wasn't you, please change your password and review account activity after changing your password");
				session.setAttribute("choice", "Do you want to logout?");
				response.sendRedirect(request.getContextPath() + "/view/user/login.jsp");
			}
		}
	}

	private void changePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String userPassword = request.getParameter("userPassword");
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("userForgot");

		UserMasterDao userMasterDao = new UserMasterDao();
		userVo.setUserPassword(userPassword);
		userMasterDao.updateUser(userVo);

		response.sendRedirect(request.getContextPath() + "/view/user/login.jsp");
	}

	private void forgotPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String userEmail = request.getParameter("userEmail");
		String userMobile = request.getParameter("userMobile");

		UserVo userVo = new UserVo();
		userVo.setUserEmail(userEmail);
		userVo.setUserMobile(userMobile);

		UserMasterDao userMasterDao = new UserMasterDao();
		boolean msg = userMasterDao.checkUserForForgot(userVo);
		HttpSession session = request.getSession();

		if (msg == true) {
			UserMasterDao userMasterDao2 = new UserMasterDao();
			userVo = userMasterDao2.getForgotUserDeatils(userVo);
			session.setAttribute("user", userVo);
			session.setAttribute("userForgotFLag", true);
			session.setAttribute("userForgot", userVo);

			Random random = new Random();
			int otp = random.nextInt(999999);

			session.setAttribute("otpValue", otp);

			// Send OPT Code
			Way2SmsPost smsPost = new Way2SmsPost();
			String phone = userVo.getUserMobile();
			String message = "Your OTP for Smart-Spender Forgot Password is:- " + otp
					+ ". Enter OTP to complete your forgot password request.";
			smsPost.sendCampaign(apiKey, secretKey, useType, phone, message, senderId);

			response.sendRedirect(request.getContextPath() + "/view/user/otp-verification.jsp");
		} else {
			session.setAttribute("userExists", true);
			session.setAttribute("userMsg", "No such user exists with provided email and mobile number");
			response.sendRedirect(request.getContextPath() + "/view/user/login.jsp");
		}
	}

	private void verifyOtp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int otpValue = Integer.parseInt(request.getParameter("value"));
		HttpSession session = request.getSession();
		int otp = (int) session.getAttribute("otpValue");

		if (otp == otpValue) {
			Object userObj = session.getAttribute("userForgotFLag");
			Object mobileChange = session.getAttribute("mobileChange");
			Object loginPassword = session.getAttribute("loginPassword");
			if (userObj != null) {
				session.removeAttribute("userForgotFLag");
				response.getWriter().println("Correct");
			} else if (mobileChange != null) {
				session.removeAttribute("mobileChange");
				response.getWriter().println("MobileChange");
			} else if (loginPassword != null) {
				session.removeAttribute("loginPassword");
				String userNewPassword = (String) session.getAttribute("userNewPassword");
				UserMasterDao userMasterDao = new UserMasterDao();
				UserVo userVo = (UserVo) session.getAttribute("user");
				userVo.setUserPassword(userNewPassword);
				userMasterDao.updateUser(userVo);
				response.getWriter().println("loginPassword");
			} else {
				UserVo userVo = (UserVo) session.getAttribute("user");
				UserMasterDao userMasterDao = new UserMasterDao();

				userVo.setConfirmed(true);
				userMasterDao.updateUser(userVo);
				response.getWriter().println("Success");
			}
		} else {
			response.getWriter().println("Wrong OTP");
		}
	}

	private void registerUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String userName, userMobile, userEmail, userPassword;

		userName = request.getParameter("userName");
		userMobile = request.getParameter("userMobile");
		userEmail = request.getParameter("userEmail");
		userPassword = request.getParameter("userPassword");

		UserVo userVo = new UserVo();
		userVo.setIsActive("0");
		userVo.setIsDeleted("0");
		userVo.setUserCreationDate(new Date().toString());
		userVo.setUserEmail(userEmail);
		userVo.setUserMobile(userMobile);
		userVo.setUserName(userName);
		userVo.setUserPassword(userPassword);
		userVo.setConfirmed(false);
		userVo.setUserCity("");
		userVo.setUserPinCode("");
		userVo.setUserImage("../../img/profile/empty_user_icon.jpg");
		userVo.setUserGender("abc");
		userVo.setUserDob("");
		userVo.setStockPermission(false);
		userVo.setPreLoaderClass("wrapper theme-1-active pimary-color-red");
		userVo.setDeactivated(false);

		UserMasterDao masterDao = new UserMasterDao();
		boolean userExisits = masterDao.checkUserExists(userVo);

		if (userExisits == false) {
			UserMasterDao userMasterDao = new UserMasterDao();
			userMasterDao.registerUser(userVo);

			HttpSession session = request.getSession();
			session.setAttribute("user", userVo);

			Random random = new Random();
			int otp = random.nextInt(999999);

			session.setAttribute("otpValue", otp);

			// Send OPT Code
			Way2SmsPost smsPost = new Way2SmsPost();
			String phone = userVo.getUserMobile();
			String message = "Your OTP for Smart-Spender account verification is:- " + otp
					+ ". Enter OTP to complete your user registration request.";
			smsPost.sendCampaign(apiKey, secretKey, useType, phone, message, senderId);

			response.sendRedirect(request.getContextPath() + "/view/user/otp-verification.jsp");
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("userExists", true);
			session.setAttribute("userMsg", "User Already Esists. You can't signup with provided details.");
			response.sendRedirect(request.getContextPath() + "/view/user/login.jsp");
		}
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