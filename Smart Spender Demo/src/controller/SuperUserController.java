package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.InventoryPermissionDao;
import dao.NotificationDao;
import dao.SuperUserDao;
import dao.UserMasterDao;
import global.MD5Encryption;
import global.SmsApiKeys;
import vo.InventoryPermissionVo;
import vo.NotificationVo;
import vo.SuperUserVo;
import vo.UserVo;
import vo.Way2SmsPost;

/**
 * Servlet implementation class SuperUserController
 */
@WebServlet("/SuperUserController")
public class SuperUserController extends HttpServlet {
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
	public SuperUserController() {
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
			if (flag.equals("loginSuperUser")) {
				loginSuperUser(request, response);
			} else if (flag.equals("loadSuperUserDashboard")) {
				loadSuperUserDashboard(request, response);
			} else if (flag.equals("changeThemeDiv")) {
				changeThemeDiv(request, response);
			} else if (flag.equals("loadAllInventoryPermissions")) {
				loadAllInventoryPermissions(request, response);
			} else if (flag.equals("listAllUsers")) {
				listAllUsers(request, response);
			} else if (flag.equals("loadSpecificUser")) {
				loadSpecificUser(request, response);
			} else if (flag.equals("reactivateUserAccount")) {
				reactivateUserAccount(request, response);
			} else if (flag.equals("revokeStockPermission")) {
				revokeStockPermission(request, response);
			}
		} catch (Exception exception) {
			String relativePathLog = "logs";
			String rootPathLog = getServletContext().getRealPath(relativePathLog);
			File logFile = new File(rootPathLog);
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

	private void revokeStockPermission(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		SuperUserVo superUserVo = (SuperUserVo) session.getAttribute("superUser");

		String userId = request.getParameter("userId");
		UserVo userVo = new UserVo();
		if (userId != null) {
			UserMasterDao userMasterDao = new UserMasterDao();
			userVo.setUserId(Integer.parseInt(userId));
			List<UserVo> list = userMasterDao.getUserDetails(userVo);
			userVo = list.get(0);
		}

		userVo.setStockPermission(false);
		UserMasterDao userMasterDao = new UserMasterDao();
		userMasterDao.updateUser(userVo);

		NotificationVo notificationVo = new NotificationVo();
		notificationVo.setNotificationDateTime(new Date().toString());
		notificationVo.setNotificationMessage(
				"Your access to Inventory Management Module of Smart Spender has been revoked by the admin.");
		notificationVo.setNotificationTitle("Inventory Request Notification");
		notificationVo.setNotificationType("negativeBalance");
		notificationVo.setNotificationUrl("view/user/user-logout.jsp");
		notificationVo.setRead(false);
		notificationVo.setUserVo(userVo);

		NotificationDao notificationDao = new NotificationDao();
		notificationDao.addNotification(notificationVo);

		apiKeys = new SmsApiKeys();
		Way2SmsPost way2SmsPost = new Way2SmsPost();
		String phone = userVo.getUserMobile();
		String message = "Your access to Inventory Management Module of Smart Spender has been revoked by the admin. You can re-request or contact admin for more further inquiry details. Thank you.";
		way2SmsPost.sendCampaign(apiKeys.getApiKey(), apiKeys.getSecretKey(), apiKeys.getUseType(), phone, message,
				apiKeys.getSenderId());

		session.setAttribute("superUser", superUserVo);
		response.sendRedirect(request.getContextPath() + "/SuperUserController?flag=listAllUsers");
	}

	private void reactivateUserAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		SuperUserVo superUserVo = (SuperUserVo) session.getAttribute("superUser");

		String userId = request.getParameter("userId");
		UserVo userVo = new UserVo();
		if (userId != null) {
			UserMasterDao userMasterDao = new UserMasterDao();
			userVo.setUserId(Integer.parseInt(userId));
			List<UserVo> list = userMasterDao.getUserDetails(userVo);
			userVo = list.get(0);
		}

		userVo.setDeactivated(false);
		userVo.setIsDeleted("0");
		UserMasterDao userMasterDao = new UserMasterDao();
		userMasterDao.updateUser(userVo);
		session.setAttribute("superUser", superUserVo);
		response.sendRedirect(request.getContextPath() + "/SuperUserController?flag=listAllUsers");
	}

	private void loadSpecificUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		SuperUserVo superUserVo = (SuperUserVo) session.getAttribute("superUser");
		String userId = request.getParameter("userId");

		UserVo userVo = null;
		if (userId != null) {
			UserMasterDao userMasterDao = new UserMasterDao();
			List<UserVo> list = userMasterDao.getUserDetailsForSuperUser(userId);
			userVo = list.get(0);
			
			//New code for image displaying starts here
			String relativePath = "img/profile";
			String rootPath = getServletContext().getRealPath(relativePath);
			File file = new File(rootPath);
			FileOutputStream fos = new FileOutputStream(file + File.separator + userVo.getUserEmail() + ".jpg");
			fos.write(userVo.getUserDatabaseImage());
			fos.close();
			
			userVo.setUserImage("../../img/profile/" + userVo.getUserEmail() + ".jpg");
		}

		session.setAttribute("userDetailsForAdmin", userVo);
		session.setAttribute("superUser", superUserVo);
		response.sendRedirect(request.getContextPath() + "/view/admin/user-details.jsp");
	}

	private void listAllUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		SuperUserVo superUserVo = (SuperUserVo) session.getAttribute("superUser");

		UserMasterDao userMasterDao = new UserMasterDao();
		List<UserVo> usersList = userMasterDao.getTotalUsers();

		session.setAttribute("usersList", usersList);
		session.setAttribute("superUser", superUserVo);
		response.sendRedirect(request.getContextPath() + "/view/admin/list-users.jsp");
	}

	private void loadAllInventoryPermissions(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		SuperUserVo superUserVo = (SuperUserVo) session.getAttribute("superUser");

		InventoryPermissionDao inventoryPermissionDao = new InventoryPermissionDao();
		List<InventoryPermissionVo> permission = inventoryPermissionDao.getAllPermissions();

		List<InventoryPermissionVo> permissionList = new ArrayList<InventoryPermissionVo>();
		InventoryPermissionVo previousVo = new InventoryPermissionVo();
		UserVo userVo = new UserVo();
		userVo.setUserId(9999);
		previousVo.setUserVo(userVo);
		for (InventoryPermissionVo inventoryPermissionVo : permission) {
			if (previousVo.getUserVo().getUserId() != inventoryPermissionVo.getUserVo().getUserId()) {
				permissionList.add(inventoryPermissionVo);
			}
			previousVo = inventoryPermissionVo;
		}

		session.setAttribute("permissionList", permissionList);
		session.setAttribute("superUser", superUserVo);
		response.sendRedirect(request.getContextPath() + "/view/admin/list-inventory-permission.jsp");
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
		SuperUserVo superUserVo = (SuperUserVo) session.getAttribute("superUser");
		superUserVo.setPreLoaderClass(finalClass);

		SuperUserDao superUserDao = new SuperUserDao();
		superUserDao.updateSuperUser(superUserVo);

		session.setAttribute("superUser", superUserVo);
		response.getWriter().println(finalClass);
	}

	private void loadSuperUserDashboard(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		SuperUserVo superUserVo = (SuperUserVo) session.getAttribute("superUser");

		int totalRegisteredusers = 0, currentOnlineUsers = 0, inventoryPermissionRequests = 0;

		UserMasterDao userMasterDao = new UserMasterDao();
		List<UserVo> userList = userMasterDao.getTotalUsers();
		totalRegisteredusers = userList.size();

		List<UserVo> onlineUserList = userMasterDao.getTotalActiveUsers();
		currentOnlineUsers = onlineUserList.size();

		InventoryPermissionDao inventoryPermissionDao = new InventoryPermissionDao();
		List<InventoryPermissionVo> permissionList = inventoryPermissionDao.getAllPermissionListForDashboard();
		inventoryPermissionRequests = permissionList.size();

		session.setAttribute("totalRegisteredusers", totalRegisteredusers);
		session.setAttribute("currentOnlineUsers", currentOnlineUsers);
		session.setAttribute("inventoryPermissionRequests", inventoryPermissionRequests);
		session.setAttribute("superUser", superUserVo);
		response.sendRedirect(request.getContextPath() + "/view/admin/admin-dashboard.jsp");
	}

	private void loginSuperUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();

		String userEmail = request.getParameter("userEmail");
		String userPassword = request.getParameter("userPassword");
		
		ServletContext servletContext=getServletContext();
		MD5Encryption md5Encryption = new MD5Encryption();
		String hashPassword = md5Encryption.getEncrptedString(userPassword, servletContext);

		SuperUserVo superUserVo = new SuperUserVo();
		superUserVo.setSuperUserEmail(userEmail);
		superUserVo.setSuperUserPassword(hashPassword);

		SuperUserDao superUserDao = new SuperUserDao();
		List<SuperUserVo> list = superUserDao.loginSuperUser(superUserVo);

		if (list.isEmpty()) {
			session.setAttribute("userExists", true);
			session.setAttribute("userMsg", "Email or Password is incorrect.");
			response.sendRedirect(request.getContextPath() + "/admin.jsp");
		} else {
			superUserVo = list.get(0);
			session.setAttribute("superUser", superUserVo);
			loadSuperUserDashboard(request, response);
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
