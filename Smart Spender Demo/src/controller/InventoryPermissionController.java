package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.InventoryPermissionDao;
import dao.NotificationDao;
import dao.UserMasterDao;
import global.SmsApiKeys;
import vo.InventoryPermissionVo;
import vo.NotificationVo;
import vo.SuperUserVo;
import vo.UserVo;
import vo.Way2SmsPost;

/**
 * Servlet implementation class InventoryPermissionController
 */
@WebServlet("/InventoryPermissionController")
public class InventoryPermissionController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * private final String apiKey = "BD5WGOBHLE6O1886A6PRIPRQQ61OZ6C4"; private
	 * final String secretKey = "2VLY43W8BRF7Y2TU"; private final String useType =
	 * "stage"; private final String senderId = "SPENDR";
	 */

	SmsApiKeys apiKeys;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InventoryPermissionController() {
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
			if (flag.equals("requestPermission")) {
				requestPermission(request, response);
			} else if (flag.equals("acceptRequest")) {
				acceptRequest(request, response);
			} else if (flag.equals("rejectRequest")) {
				rejectRequest(request, response);
			}
		} catch (Exception exception) {
			String relativePath = "logs";
			String rootPath = getServletContext().getRealPath(relativePath);
			File logFile = new File(rootPath);
			if (!logFile.exists()) {
				logFile.mkdirs();
			}
			FileWriter fileWriter = new FileWriter(logFile + "/error-log.txt", true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.write(exception.getMessage());
			printWriter.write(System.lineSeparator());
			printWriter.close();
		}
	}

	private void rejectRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		SuperUserVo superUserVo = (SuperUserVo) session.getAttribute("superUser");

		String permissionId = request.getParameter("permissionId");
		String userId = request.getParameter("userId");

		List<InventoryPermissionVo> permissionList = new ArrayList<InventoryPermissionVo>();
		if (userId != null) {
			InventoryPermissionDao inventoryPermissionDao = new InventoryPermissionDao();
			permissionList = inventoryPermissionDao.getAllRequestsFromSpecifiedUserId(userId);
		}

		InventoryPermissionVo lastRequest = new InventoryPermissionVo();
		for (InventoryPermissionVo inventoryPermissionVo : permissionList) {
			inventoryPermissionVo.setAdminAction(true);
			inventoryPermissionVo.setRequestStatus(true);

			InventoryPermissionDao inventoryPermissionDao = new InventoryPermissionDao();
			inventoryPermissionDao.updatePermissionRequest(inventoryPermissionVo);
			lastRequest = inventoryPermissionVo;
		}

		UserVo userVo = lastRequest.getUserVo();
		if (userVo.getUserId() == Integer.parseInt(userId)) {
			userVo.setStockPermission(false);
			UserMasterDao userMasterDao = new UserMasterDao();
			userMasterDao.updateUser(userVo);

			NotificationVo notificationVo = new NotificationVo();
			notificationVo.setNotificationDateTime(new Date().toString());
			notificationVo.setNotificationMessage(
					"Your Request for Accessing Inventory Management Module of Smart Spender has been rejected by the admin.");
			notificationVo.setNotificationTitle("Inventory Request Notification");
			notificationVo.setNotificationType("negativeBalance");
			notificationVo.setNotificationUrl("view/user/user-logout.jsp");
			notificationVo.setRead(false);
			notificationVo.setUserVo(lastRequest.getUserVo());

			NotificationDao notificationDao = new NotificationDao();
			notificationDao.addNotification(notificationVo);

			apiKeys = new SmsApiKeys();
			Way2SmsPost way2SmsPost = new Way2SmsPost();
			String phone = lastRequest.getUserVo().getUserMobile();
			String message = "Your Request for Accessing Inventory Management Module of Smart Spender has been rejected by the admin. You can re-request or contact admin for more further inquiry details. Thank you.";
			way2SmsPost.sendCampaign(apiKeys.getApiKey(), apiKeys.getSecretKey(), apiKeys.getUseType(), phone, message,
					apiKeys.getSenderId());
		}

		session.setAttribute("superUser", superUserVo);
		response.sendRedirect(request.getContextPath() + "/SuperUserController?flag=loadAllInventoryPermissions");
	}

	private void acceptRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		SuperUserVo superUserVo = (SuperUserVo) session.getAttribute("superUser");

		String permissionId = request.getParameter("permissionId");
		String userId = request.getParameter("userId");

		List<InventoryPermissionVo> permissionList = new ArrayList<InventoryPermissionVo>();
		if (userId != null) {
			InventoryPermissionDao inventoryPermissionDao = new InventoryPermissionDao();
			permissionList = inventoryPermissionDao.getAllRequestsFromSpecifiedUserId(userId);
		}

		InventoryPermissionVo lastRequest = new InventoryPermissionVo();
		for (InventoryPermissionVo inventoryPermissionVo : permissionList) {
			inventoryPermissionVo.setAdminAction(true);
			inventoryPermissionVo.setRequestStatus(true);

			InventoryPermissionDao inventoryPermissionDao = new InventoryPermissionDao();
			inventoryPermissionDao.updatePermissionRequest(inventoryPermissionVo);
			lastRequest = inventoryPermissionVo;
		}

		UserVo userVo = lastRequest.getUserVo();
		if (userVo.getUserId() == Integer.parseInt(userId)) {
			userVo.setStockPermission(true);
			UserMasterDao userMasterDao = new UserMasterDao();
			userMasterDao.updateUser(userVo);

			NotificationVo notificationVo = new NotificationVo();
			notificationVo.setNotificationDateTime(new Date().toString());
			notificationVo.setNotificationMessage(
					"Your Request for Accessing Inventory Management Module of Smart Spender has been accepted by the admin. You can re-login to your account for accessing the Inventory Management features. Thank you.");
			notificationVo.setNotificationTitle("Inventory Request Notification");
			notificationVo.setNotificationType("negativeBalance");
			notificationVo.setNotificationUrl("view/user/user-logout.jsp");
			notificationVo.setRead(false);
			notificationVo.setUserVo(lastRequest.getUserVo());

			NotificationDao notificationDao = new NotificationDao();
			notificationDao.addNotification(notificationVo);

			apiKeys = new SmsApiKeys();
			Way2SmsPost way2SmsPost = new Way2SmsPost();
			String phone = lastRequest.getUserVo().getUserMobile();
			String message = "Your Request for Accessing Inventory Management Module of Smart Spender has been accepted by the admin. You can re-login to your account for accessing the Inventory Management features. Thank you.";
			way2SmsPost.sendCampaign(apiKeys.getApiKey(), apiKeys.getSecretKey(), apiKeys.getUseType(), phone, message,
					apiKeys.getSenderId());
		}

		session.setAttribute("superUser", superUserVo);
		response.sendRedirect(request.getContextPath() + "/SuperUserController?flag=loadAllInventoryPermissions");
	}

	private void requestPermission(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		InventoryPermissionVo inventoryPermissionVo = new InventoryPermissionVo();
		inventoryPermissionVo.setRequestDateTime(new Date().toString());
		inventoryPermissionVo.setRequestStatus(false);
		inventoryPermissionVo.setUserVo(userVo);
		inventoryPermissionVo.setAdminAction(false);

		InventoryPermissionDao inventoryPermissionDao = new InventoryPermissionDao();
		inventoryPermissionDao.addPermission(inventoryPermissionVo);
		session.setAttribute("user", userVo);

		response.getWriter().println(
				"Your request for accessing Inventory Management Module has been sent to admin. You will be notified on your request shortly.");
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
