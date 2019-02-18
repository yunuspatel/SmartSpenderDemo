package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BudgetMasterDao;
import dao.NotificationDao;
import vo.BudgetVo;
import vo.NotificationVo;
import vo.UserVo;

/**
 * Servlet implementation class NotificationController
 */
@WebServlet("/NotificationController")
public class NotificationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NotificationController() {
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

		if (flag.equals("clearAll")) {
			clearAll(request, response);
		} else if (flag.equals("displayAll")) {
			displayAll(request, response);
		} else if (flag.equals("loadNotification")) {
			loadNotification(request, response);
		} else if(flag.equals("loadBudget")) {
			loadBudget(request,response);
		}
	}

	private void loadBudget(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int budgetId = Integer.parseInt(request.getParameter("value"));
		BudgetVo budgetVo=new BudgetVo();
		budgetVo.setBudgetId(budgetId);
		
		BudgetMasterDao budgetMasterDao=new BudgetMasterDao();
		List<BudgetVo> budgetList = budgetMasterDao.loadBudgetById(budgetVo);
		
		budgetVo = budgetList.get(0);
		HttpSession session=request.getSession();
		session.setAttribute("budgetDetail", budgetVo);
		response.sendRedirect(request.getContextPath() + "/view/pages/budget-detail.jsp");
	}

	private void loadNotification(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int notificationId = Integer.parseInt(request.getParameter("value"));
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		NotificationVo notificationVo = new NotificationVo();
		notificationVo.setNotificationId(notificationId);

		NotificationDao notificationDao = new NotificationDao();
		notificationVo = notificationDao.getNotificationById(notificationVo);

		if (notificationVo != null) {
			notificationVo.setRead(true);
			NotificationDao notificationDao2 = new NotificationDao();
			notificationDao2.updateNotification(notificationVo);
		}

		List<NotificationVo> notificationsList = notificationDao.getAllNotifications(userVo);
		session.setAttribute("notificationsList", notificationsList);
		session.setAttribute("notificationSize", notificationsList.size());
		session.setAttribute("user", userVo);
		response.sendRedirect(request.getContextPath() + "/" + notificationVo.getNotificationUrl());
	}

	private void displayAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		NotificationDao notificationDao = new NotificationDao();
		List<NotificationVo> notificationsList = notificationDao.getAllNotifications(userVo);

		session.setAttribute("notifications", notificationsList);
		session.setAttribute("user", userVo);
		response.sendRedirect(request.getContextPath() + "/view/pages/notifications-list.jsp");
	}

	private void clearAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		List<NotificationVo> notificationList = new ArrayList<NotificationVo>();
		if (true) {
			NotificationDao notificationDao = new NotificationDao();
			notificationList = notificationDao.getAllNotifications(userVo);
		}

		for (NotificationVo notificationVo : notificationList) {
			notificationVo.setRead(true);
			NotificationDao notificationDao = new NotificationDao();
			notificationDao.updateNotification(notificationVo);
		}

		NotificationDao notificationDao = new NotificationDao();
		notificationList = notificationDao.getAllNotifications(userVo);

		session.setAttribute("notificationsList", notificationList);
		session.setAttribute("notificationSize", notificationList.size());
		session.setAttribute("user", userVo);
		response.sendRedirect(request.getContextPath() + "/view/pages/home.jsp");
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
