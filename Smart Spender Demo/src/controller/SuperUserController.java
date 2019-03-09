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

import dao.InventoryPermissionDao;
import dao.SuperUserDao;
import dao.UserMasterDao;
import vo.InventoryPermissionVo;
import vo.SuperUserVo;
import vo.UserVo;

/**
 * Servlet implementation class SuperUserController
 */
@WebServlet("/SuperUserController")
public class SuperUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		}
	}

	private void listAllUsers(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	private void loadAllInventoryPermissions(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		SuperUserVo superUserVo=(SuperUserVo)session.getAttribute("superUser");
		
		InventoryPermissionDao inventoryPermissionDao=new InventoryPermissionDao();
		List<InventoryPermissionVo> permission = inventoryPermissionDao.getAllPermissions();
		
		List<InventoryPermissionVo> permissionList = new ArrayList<InventoryPermissionVo>();
		InventoryPermissionVo previousVo=new InventoryPermissionVo();
		UserVo userVo=new UserVo();
		userVo.setUserId(9999);
		previousVo.setUserVo(userVo);
		for(InventoryPermissionVo inventoryPermissionVo : permission) {
			if(previousVo.getUserVo().getUserId() != inventoryPermissionVo.getUserVo().getUserId()) {
				permissionList.add(inventoryPermissionVo);
			}
			previousVo=inventoryPermissionVo;
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
		
		UserMasterDao userMasterDao=new UserMasterDao();
		List<UserVo> userList = userMasterDao.getTotalUsers();
		totalRegisteredusers = userList.size();
		
		List<UserVo> onlineUserList =userMasterDao.getTotalActiveUsers();
		currentOnlineUsers=onlineUserList.size();
		
		InventoryPermissionDao inventoryPermissionDao=new InventoryPermissionDao();
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

		SuperUserVo superUserVo = new SuperUserVo();
		superUserVo.setSuperUserEmail(userEmail);
		superUserVo.setSuperUserPassword(userPassword);

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
