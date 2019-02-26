package controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.InventoryPermissionDao;
import vo.InventoryPermissionVo;
import vo.UserVo;

/**
 * Servlet implementation class InventoryPermissionController
 */
@WebServlet("/InventoryPermissionController")
public class InventoryPermissionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InventoryPermissionController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String flag = request.getParameter("flag");
		
		if(flag.equals("requestPermission")) {
			requestPermission(request,response);
		}
	}

	private void requestPermission(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		UserVo userVo=(UserVo)session.getAttribute("user");
		
		InventoryPermissionVo inventoryPermissionVo=new InventoryPermissionVo();
		inventoryPermissionVo.setRequestDateTime(new Date().toString());
		inventoryPermissionVo.setRequestStatus(false);
		inventoryPermissionVo.setUserVo(userVo);;
		
		InventoryPermissionDao inventoryPermissionDao=new InventoryPermissionDao();
		inventoryPermissionDao.addPermission(inventoryPermissionVo);	
		session.setAttribute("user", userVo);
		
		response.getWriter().println("Your request for accessing Inventory Management Module has been sent to admin. You will be notified on your request shortly.");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
