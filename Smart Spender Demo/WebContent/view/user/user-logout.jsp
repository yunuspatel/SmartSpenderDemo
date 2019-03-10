<%@page import="java.util.List"%>
<%@page import="dao.UserMasterDao"%>
<%@page import="vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Log out Page</title>
</head>
<body>
	<%
		UserVo userVo = (UserVo) session.getAttribute("user");
		userVo.setIsActive("0");
		UserVo adminVo = new UserVo();
		if (true) {
			UserMasterDao userMasterDao = new UserMasterDao();
			List<UserVo> list = userMasterDao.getUserDetails(userVo);
			adminVo = list.get(0);
		}

		UserMasterDao userMasterDao = new UserMasterDao();
		if (adminVo.isStockPermission() == userVo.isStockPermission()) {
			userMasterDao.updateUser(userVo);
		}else{
			userVo.setStockPermission(adminVo.isStockPermission());
			userMasterDao.updateUser(userVo);
		}
		
		if(adminVo.isDeactivated() != userVo.isDeactivated()){
			userVo.setDeactivated(adminVo.isDeactivated());
			UserMasterDao masterDao=new UserMasterDao();
			masterDao.updateUser(userVo);
		}
		session.invalidate();
		response.sendRedirect(request.getContextPath() + "/view/user/login.jsp");
	%>
</body>
</html>