package controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserMasterDao;
import vo.UserVo;

/**
 * Servlet implementation class UserMasterController
 */
@WebServlet("/UserMasterController")
public class UserMasterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserMasterController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String flag=request.getParameter("flag");
		
		if(flag.equals("signup")) {
			registerUser(request,response);
		}else if(flag.equals("login")) {
			
		}
	}

	private void registerUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String userName,userMobile,userEmail,userPassword;
		
		userName=request.getParameter("userName");
		userMobile=request.getParameter("userMobile");
		userEmail=request.getParameter("userEmail");
		userPassword=request.getParameter("userPassword");
		
		UserVo userVo = new UserVo();
		userVo.setIsActive("0");
		userVo.setIsDeleted("0");
		userVo.setUserCreationDate(new Date().toString());
		userVo.setUserEmail(userEmail);
		userVo.setUserMobile(userMobile);
		userVo.setUserName(userName);
		userVo.setUserPassword(userPassword);
		
		UserMasterDao userMasterDao=new UserMasterDao();
		userMasterDao.registerUser(userVo);
		
		response.getWriter().println("success");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
