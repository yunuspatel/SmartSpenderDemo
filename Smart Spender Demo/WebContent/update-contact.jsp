<%@page import="dao.UserMasterDao"%>
<%@page import="vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Contact</title>
</head>
<body>
<%
	UserVo userVo=(UserVo)session.getAttribute("user");
	UserMasterDao userMasterDao=new UserMasterDao();
	userMasterDao.updateUser(userVo);
	session.setAttribute("user", userVo);
	response.sendRedirect("user-logout.jsp");
%>
</body>
</html>