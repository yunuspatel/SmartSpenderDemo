<%@page import="dao.UserMasterDao"%>
<%@page import="vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>Login-Smart Spender</title>

<!-- Favicon -->
<link rel="shortcut icon" href="img/logo2.png">
<link rel="icon" href="img/logo2.png" type="image/x-icon">

<!-- Custom CSS -->
<link href="dist/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function checkMsg()
	{
		<%Object msg = session.getAttribute("userExists");
			if (msg != null) {
				String userMsg = (String) session.getAttribute("userMsg");%>
				alert("<%=userMsg%>");
			<%}
			Object choice=session.getAttribute("choice");
			if(choice!=null)
			{ %>
				var userChoice = confirm("<%= choice.toString() %>");
				if(userChoice == true)
				{
					<%
						UserVo userVo=(UserVo)session.getAttribute("user");
						userVo.setIsActive("0");
						UserMasterDao userMasterDao=new UserMasterDao();
						userMasterDao.updateUser(userVo);
						session.invalidate();
					%>
				}
		<%	}
			%>
	}

	function checkDataFill() {
		var userEmail = document.getElementById('userEmail').value;
		var userPassword = document.getElementById('userPassword').value;

		if (userEmail != "" && userPassword != "") {
			document.getElementById('sbmt').disabled = false;
		} else {
			document.getElementById('sbmt').disabled = true;
		}
	}
</script>
</head>
<body onload="checkMsg()">
	<!--Preloader-->
	<div class="preloader-it">
		<div class="la-anim-1"></div>
	</div>
	<!--/Preloader-->

	<div class="wrapper pa-0">
		<header class="sp-header">
			<div class="sp-logo-wrap pull-left">
				<a href="login.jsp"> <img class="brand-img mr-10"
					src="img/logo1.png" alt="brand" /> <span class="brand-text">Smart
						Spender</span>
				</a>
			</div>
			<div class="form-group mb-0 pull-right">
				<span class="inline-block pr-10">Don't have an account?</span> <a
					class="inline-block btn btn-info btn-rounded btn-outline"
					href="signup.jsp">Sign Up</a>
			</div>
			<div class="clearfix"></div>
		</header>

		<!-- Main Content -->
		<div class="page-wrapper pa-0 ma-0 auth-page">
			<div class="container-fluid">
				<!-- Row -->
				<div class="table-struct full-width full-height">
					<div class="table-cell vertical-align-middle auth-form-wrap">
						<div class="auth-form  ml-auto mr-auto no-float">
							<div class="row">
								<div class="col-sm-12 col-xs-12">
									<div class="mb-30">
										<h3 class="text-center txt-dark mb-10">Sign in to Smart
											Spender</h3>
										<h6 class="text-center nonecase-font txt-grey">Enter your
											details below</h6>
									</div>
									<div class="form-wrap">
										<form name="frm" method="post"
											action="<%=request.getContextPath()%>/UserMasterController">
											<input type=hidden name="flag" value="login">
											<div class="form-group">
												<label class="control-label mb-10" for="userEmail">Email
													address</label> <input type="email" class="form-control"
													required="" autofocus="true" autocomplete="off"
													id="userEmail" onkeyup="checkDataFill()" name="userEmail"
													placeholder="Enter Email">
											</div>
											<div class="form-group">
												<label class="pull-left control-label mb-10"
													for="userPassword">Password</label> <a
													class="capitalize-font txt-primary block mb-10 pull-right font-12"
													href="forgot-password.jsp">forgot password ?</a>
												<div class="clearfix"></div>
												<input type="password" class="form-control" required=""
													id="userPassword" autocomplete="off" name="userPassword"
													onkeyup="checkDataFill()" placeholder="Enter password">
											</div>

											<!-- <div class="form-group">
												<div class="checkbox checkbox-primary pr-10 pull-left">
													<input id="checkbox_2" required="" type="checkbox">
													<label for="checkbox_2"> Keep me logged in</label>
												</div>
												<div class="clearfix"></div>
											</div> -->
											<div class="form-group text-center">
												<input type="submit" class="btn btn-info btn-rounded"
													disabled="true" value="Sign in" id="sbmt">
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- /Row -->
			</div>

		</div>
		<!-- /Main Content -->

	</div>
	<!-- jQuery -->
	<script src="vendors/bower_components/jquery/dist/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script
		src="vendors/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Slimscroll JavaScript -->
	<script src="dist/js/jquery.slimscroll.js"></script>

	<!-- Init JavaScript -->
	<script src="dist/js/init.js"></script>
</body>
</html>