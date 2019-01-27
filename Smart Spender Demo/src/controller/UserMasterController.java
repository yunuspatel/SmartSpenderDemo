package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserMasterDao;
import vo.UserVo;
import vo.Way2SmsPost;

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

		} else if (flag.equals("verifyOtp")) {
			verifyOtp(request, response);
		} else if (flag.equals("forgot-password")) {
			forgotPassword(request, response);
		}
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
			System.out.println(otp);
			session.setAttribute("otpValue", otp);

			// Send OPT Code
			Way2SmsPost smsPost = new Way2SmsPost();
			String apiKey = "J9J5E9UILDG7RNI36ZDEPMZUJ8P4VQZC";
			String secretKey = "1X6EG9LRITX1755Y";
			String useType = "stage";
			String phone = userVo.getUserMobile();
			String message = "Your OTP for Smart-Spender Forgot Password is:- " + otp
					+ ". Enter OTP to complete your forgot password request.";
			String senderId = "SPENDR";
			smsPost.sendCampaign(apiKey, secretKey, useType, phone, message, senderId);

			response.sendRedirect("opt-verification.jsp");
		} else {
			session.setAttribute("userExists", true);
			session.setAttribute("userMsg", "No such user exists with provided email and mobile number");
			response.sendRedirect("login.jsp");
		}
	}

	private void verifyOtp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int otpValue = Integer.parseInt(request.getParameter("value"));
		HttpSession session = request.getSession();
		int otp = (int) session.getAttribute("otpValue");
		System.out.println(otp);
		System.out.println(otpValue);
		if (otp == otpValue) {
			Object userObj = session.getAttribute("userForgotFLag");
			if (userObj!=null) {
				response.getWriter().println("Correct");

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

		UserMasterDao masterDao = new UserMasterDao();
		boolean userExisits = masterDao.checkUserExists(userVo);

		if (userExisits == false) {
			UserMasterDao userMasterDao = new UserMasterDao();
			userMasterDao.registerUser(userVo);

			HttpSession session = request.getSession();
			session.setAttribute("user", userVo);

			Random random = new Random();
			int otp = random.nextInt(999999);
			System.out.println(otp);
			session.setAttribute("otpValue", otp);

			// Send OPT Code
			Way2SmsPost smsPost = new Way2SmsPost();
			String apiKey = "J9J5E9UILDG7RNI36ZDEPMZUJ8P4VQZC";
			String secretKey = "1X6EG9LRITX1755Y";
			String useType = "stage";
			String phone = userVo.getUserMobile();
			String message = "Your OTP for Smart-Spender account verification is:- " + otp
					+ ". Enter OTP to complete your user registration request.";
			String senderId = "SPENDR";
			smsPost.sendCampaign(apiKey, secretKey, useType, phone, message, senderId);

			response.sendRedirect("opt-verification.jsp");
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("userExists", true);
			session.setAttribute("userMsg", "User Already Esists. You can't signup with provided details.");
			response.sendRedirect("login.jsp");
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
