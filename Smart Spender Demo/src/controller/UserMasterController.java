package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dao.TrackingMasterDao;
import dao.UserMasterDao;
import vo.TrackingVo;
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
			loginUser(request, response);
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
		} else if(flag.equals("deleteUser")) {
			deleteUser(request,response);
		} else if(flag.equals("changePassword")) {
			changeNewPassword(request,response);
		}
	}

	private void changeNewPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		UserVo userVo=(UserVo)session.getAttribute("user");
		
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
		response.sendRedirect(request.getContextPath()+"/view/user/otp-verification.jsp");		
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String userId = request.getParameter("userId");
		
		HttpSession session=request.getSession();
		UserVo userVo=(UserVo)session.getAttribute("user");
		
		if(Integer.parseInt(userId) == userVo.getUserId())
		{
			userVo.setIsDeleted("1");
			UserMasterDao userMasterDao=new UserMasterDao();
			userMasterDao.updateUser(userVo);
		}
		
		response.sendRedirect(request.getContextPath()+"/view/user/user-logout.jsp");
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
		String userDob=request.getParameter("userDob");
		
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
			response.sendRedirect(request.getContextPath()+"/view/user/user-settings.jsp");
		}else {
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

			response.sendRedirect(request.getContextPath()+"/view/user/otp-verification.jsp");
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
		System.out.println(rootPath);
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
			
				File uploadFile = new File(file + File.separator + userVo.getUserName() + ".jpg");
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
						userVo.setUserImage("../../img/profile/" + userVo.getUserName() + ".jpg");
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
		session.setAttribute("imageChanged", true);
		response.sendRedirect(request.getContextPath()+"/view/user/user-settings.jsp");
	}

	private void loginUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
			response.sendRedirect(request.getContextPath()+"/view/user/login.jsp");
		} else {
			userVo = userList.get(0);
			if (userVo.getIsActive().equals("0")) {
				userVo.setIsActive("1");
				UserMasterDao masterDao = new UserMasterDao();
				masterDao.updateUser(userVo);

				TrackingVo trackingVo = new TrackingVo();
				trackingVo.setBrowserName(request.getHeader("user-agent"));
				trackingVo.setHostName(request.getRemoteHost());
				trackingVo.setIpAddress(request.getRemoteAddr());
				trackingVo.setLoginDateTime(new Date().toString());
				trackingVo.setPortNumber("" + request.getRemotePort());
				trackingVo.setUserName(userVo.getUserName());
				trackingVo.setUserVo(userVo);

				TrackingMasterDao trackingMasterDao = new TrackingMasterDao();
				trackingMasterDao.addTrack(trackingVo);

				session.setAttribute("user", userVo);
				response.sendRedirect(request.getContextPath()+"/view/pages/home.jsp");
			} else {
				session.setAttribute("user", userVo);
				session.setAttribute("userExists", true);
				session.setAttribute("userMsg",
						"This account is already logged in from another source. If it wasn't you, please change your password and review account activity after changing your password");
				session.setAttribute("choice", "Do you want to logout?");
				response.sendRedirect(request.getContextPath()+"/view/user/login.jsp");
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

		response.sendRedirect(request.getContextPath()+"/view/user/login.jsp");
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

			response.sendRedirect(request.getContextPath()+"/view/user/otp-verification.jsp");
		} else {
			session.setAttribute("userExists", true);
			session.setAttribute("userMsg", "No such user exists with provided email and mobile number");
			response.sendRedirect(request.getContextPath()+"/view/user/login.jsp");
		}
	}

	private void verifyOtp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int otpValue = Integer.parseInt(request.getParameter("value"));
		HttpSession session = request.getSession();
		int otp = (int) session.getAttribute("otpValue");

		if (otp == otpValue) {
			Object userObj = session.getAttribute("userForgotFLag");
			Object mobileChange=session.getAttribute("mobileChange");
			Object loginPassword=session.getAttribute("loginPassword");
			if (userObj != null) {
				session.removeAttribute("userForgotFLag");
				response.getWriter().println("Correct");
			}else if(mobileChange!=null) {
				session.removeAttribute("mobileChange");
				response.getWriter().println("MobileChange");
			}else if(loginPassword!=null) {
				session.removeAttribute("loginPassword");
				String userNewPassword = (String)session.getAttribute("userNewPassword");
				UserMasterDao userMasterDao=new UserMasterDao();
				UserVo userVo=(UserVo)session.getAttribute("user");
				userVo.setUserPassword(userNewPassword);
				userMasterDao.updateUser(userVo);
				response.getWriter().println("loginPassword");
			}
			else {
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

			response.sendRedirect(request.getContextPath()+"/view/user/otp-verification.jsp");
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("userExists", true);
			session.setAttribute("userMsg", "User Already Esists. You can't signup with provided details.");
			response.sendRedirect(request.getContextPath()+"/view/user/login.jsp");
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