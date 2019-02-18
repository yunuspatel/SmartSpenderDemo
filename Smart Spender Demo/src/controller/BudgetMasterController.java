package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import dao.BudgetMasterDao;
import dao.NotificationDao;
import vo.BudgetVo;
import vo.NotificationVo;
import vo.UserVo;

/**
 * Servlet implementation class BudgetMasterController
 */
@WebServlet("/BudgetMasterController")
public class BudgetMasterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BudgetMasterController() {
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

		if (flag.equals("loadAllBudgets")) {
			loadAllBudgets(request, response);
		} else if (flag.equals("addBudget")) {
			addBudget(request, response);
		} else if (flag.equals("loadSpecificBudget")) {
			loadSpecificBudget(request, response);
		} else if (flag.equals("deleteBudget")) {
			deleteBudget(request, response);
		} else if (flag.equals("editBudget")) {
			editBudget(request, response);
		}
	}

	private void editBudget(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		BudgetVo budgetVo = (BudgetVo) session.getAttribute("budgetDetail");
		UserVo userVo = (UserVo) session.getAttribute("user");

		if (budgetVo != null) {
			budgetVo.setBudgetName(request.getParameter("budgetName"));
			float oldAmount = budgetVo.getBudgetAmount();
			float newAmount = Float.parseFloat(request.getParameter("budgetAmount"));
			budgetVo.setBudgetAmount(Float.parseFloat(request.getParameter("budgetAmount")));
			budgetVo.setBudgetStartDate(request.getParameter("startDate"));
			budgetVo.setBudgetEndDate(request.getParameter("endDate"));
			budgetVo.setBudgetAlertAmount(Float.parseFloat(request.getParameter("alertAmount")));
			budgetVo.setBudgetDescription(request.getParameter("budgetDescription"));

			float difference;
			if (newAmount > oldAmount) {
				difference = newAmount - oldAmount;
				budgetVo.setBudgetAmountLeft(budgetVo.getBudgetAmountLeft() + difference);
			} else if (newAmount < oldAmount) {
				difference = newAmount - oldAmount;
				budgetVo.setBudgetAmountLeft(budgetVo.getBudgetAmountLeft() + difference);
			}
			
			BudgetMasterDao budgetMasterDao = new BudgetMasterDao();
			budgetMasterDao.updateBudget(budgetVo);

			if (budgetVo.getBudgetAmountLeft() <= budgetVo.getBudgetAlertAmount()) {
				NotificationVo notificationVo = new NotificationVo();
				notificationVo.setNotificationDateTime(new Date().toString());
				notificationVo.setNotificationTitle("Budget Alert");
				notificationVo.setNotificationMessage("Alert to notify Budget Named:- " + budgetVo.getBudgetName()
						+ "'s alert amount has been surpassed. Budget Amount Remaining is:- "
						+ budgetVo.getBudgetAmountLeft()
						+ ". If their's a problem review your transactions from Transaction List page.");
				notificationVo.setNotificationType("budgetAmountAlert");
				notificationVo.setRead(false);
				notificationVo
						.setNotificationUrl("NotificationController?flag=loadBudget&value=" + budgetVo.getBudgetId());
				notificationVo.setUserVo(userVo);

				NotificationDao notificationDao = new NotificationDao();
				notificationDao.addNotification(notificationVo);

				NotificationDao notificationDao2 = new NotificationDao();
				List<NotificationVo> notificationsList = notificationDao2.getAllNotifications(userVo);
				session.setAttribute("notificationSize", notificationsList.size());
				session.setAttribute("checkNotification", true);
			}
			session.setAttribute("userMsg", "Budget Updated Successfully.");
		} else {
			session.setAttribute("userMsg", "Error in updating budget details");
		}

		session.removeAttribute("budgetDetail");
		session.setAttribute("user", userVo);
		response.sendRedirect(request.getContextPath() + "/BudgetMasterController?flag=loadAllBudgets");
	}

	private void deleteBudget(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int budgetId = Integer.parseInt(request.getParameter("budgetId"));
		BudgetVo budgetVo = new BudgetVo();
		budgetVo.setBudgetId(budgetId);

		List<BudgetVo> budgetList = new ArrayList<BudgetVo>();
		if (budgetId != 0) {
			BudgetMasterDao budgetMasterDao = new BudgetMasterDao();
			budgetList = budgetMasterDao.loadBudgetById(budgetVo);
		}

		if (!budgetList.isEmpty()) {
			budgetVo = budgetList.get(0);
			budgetVo.setDeleted(true);
			BudgetMasterDao budgetMasterDao = new BudgetMasterDao();
			budgetMasterDao.updateBudget(budgetVo);
			HttpSession session = request.getSession();
			session.setAttribute("userMsg", "Budget Deleted Successfully.");
		}
		response.sendRedirect(request.getContextPath() + "/BudgetMasterController?flag=loadAllBudgets");
	}

	private void loadSpecificBudget(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession();
		int budgetId = Integer.parseInt(request.getParameter("value"));
		BudgetVo budgetVo = new BudgetVo();
		budgetVo.setBudgetId(budgetId);

		BudgetMasterDao budgetMasterDao = new BudgetMasterDao();
		List<BudgetVo> budgetList = budgetMasterDao.loadBudgetById(budgetVo);

		if (budgetList.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/view/pages/budget.jsp");
		} else {
			budgetVo = budgetList.get(0);
			session.setAttribute("budgetDetail", budgetVo);
			response.sendRedirect(request.getContextPath() + "/view/pages/budget-detail.jsp");
		}
	}

	private void addBudget(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		BudgetVo budgetVo = new BudgetVo();
		budgetVo.setBudgetAlertAmount(Float.parseFloat(request.getParameter("alertAmount")));
		budgetVo.setBudgetAmount(Float.parseFloat(request.getParameter("budgetAmount")));
		budgetVo.setBudgetAmountLeft(budgetVo.getBudgetAmount());
		budgetVo.setBudgetDescription(request.getParameter("budgetDecription"));
		budgetVo.setBudgetEndDate(request.getParameter("endDate"));
		budgetVo.setBudgetName(request.getParameter("budgetName"));
		budgetVo.setBudgetStartDate(request.getParameter("startDate"));
		budgetVo.setDeleted(false);
		budgetVo.setUserVo(userVo);

		BudgetMasterDao budgetMasterDao = new BudgetMasterDao();
		budgetMasterDao.addBudget(budgetVo);

		session.setAttribute("user", userVo);
		response.sendRedirect(request.getContextPath() + "/BudgetMasterController?flag=loadAllBudgets");
	}

	private void loadAllBudgets(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		BudgetMasterDao budgetMasterDao = new BudgetMasterDao();
		List<BudgetVo> budgetList = budgetMasterDao.getAllBudgets(userVo);

		session.setAttribute("budgetList", budgetList);
		session.setAttribute("user", userVo);
		response.sendRedirect(request.getContextPath() + "/view/pages/budget.jsp");
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
