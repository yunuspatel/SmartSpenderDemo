package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.SuperUserDao;
import vo.SuperUserVo;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String flag = request.getParameter("flag");
		
		if(flag.equals("loginSuperUser")) {
			loginSuperUser(request,response);
		}
	}

	private void loginSuperUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		
		String userEmail = request.getParameter("userEmail");
		String userPassword = request.getParameter("userPassword");
		
		SuperUserVo superUserVo=new SuperUserVo();
		superUserVo.setSuperUserEmail(userEmail);
		superUserVo.setSuperUserPassword(userPassword);
		
		SuperUserDao superUserDao=new SuperUserDao();
		List<SuperUserVo> list = superUserDao.loginSuperUser(superUserVo);
		
		if(list.isEmpty()) {
			session.setAttribute("userExists", true);
			session.setAttribute("userMsg", "Email or Password is incorrect.");
			response.sendRedirect(request.getContextPath() + "/admin.jsp");
		}else {
			superUserVo=list.get(0);
			session.setAttribute("superUser", superUserVo);
			response.sendRedirect(request.getContextPath()+"/view/admin/admin-dashboard.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
