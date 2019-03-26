<%@page import="vo.TransactionVo"%>
<%@page import="dao.TransactionMasterDao"%>
<%@page import="java.io.File"%>
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
		String relativePath = "img/profile";
		String rootPath = getServletContext().getRealPath(relativePath);
		File file = new File(rootPath);
		File profileImageFile = new File(file + File.separator + session.getId() + userVo.getUserId() + ".jpg");
		profileImageFile.delete();
		
		TransactionMasterDao transactionMasterDao=new TransactionMasterDao();
		List<TransactionVo> transactionList = transactionMasterDao.getAllTransactions(userVo);
		relativePath = "img/transactionImages";
		rootPath = getServletContext().getRealPath(relativePath);
		File transactionFile = new File(rootPath);
		for(TransactionVo transactionVo : transactionList){
			File transactionImage = new File(transactionFile + File.separator + transactionVo.getTransactionIdentificationNumber() + ".jpg");
			if(transactionImage.exists()){
				transactionImage.delete();
			}
		}
		
		session.invalidate();
		response.sendRedirect(request.getContextPath() + "/view/user/login.jsp");
	%>
</body>
</html>