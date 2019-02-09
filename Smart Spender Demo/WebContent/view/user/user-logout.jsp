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
	UserVo userVo=(UserVo)session.getAttribute("user");
	userVo.setIsActive("0");
	UserMasterDao userMasterDao=new UserMasterDao();
	userMasterDao.updateUser(userVo);
	session.invalidate();
	response.sendRedirect(request.getContextPath()+"/view/user/login.jsp");
%>
</body>
</html>