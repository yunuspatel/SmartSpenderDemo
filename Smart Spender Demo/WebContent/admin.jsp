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
<title>Admin Login-Smart Spender</title>

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
				<%session.removeAttribute("userMsg");
				session.removeAttribute("userExists");
			}%>
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
				<a href="<%=request.getContextPath()%>/view/user/login.jsp"> <img
					class="brand-img mr-10" src="img/logo1.png" alt="brand" /> <span
					class="brand-text">Smart Spender</span>
				</a>
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
										<h3 class="text-center txt-dark mb-10">Admin Panel - Login</h3>
										<h3 class="text-center txt-dark mb-10">Smart Spender</h3>
										<h6 class="text-center nonecase-font txt-grey">Enter your
											details below</h6>
									</div>
									<div class="form-wrap">
										<form name="frm" method="post"
											action="<%=request.getContextPath()%>/SuperUserController">
											<input type=hidden name="flag" value="loginSuperUser">
											<div class="form-group">
												<label class="control-label mb-10" for="userEmail">Email
													address</label>
												<div class="input-group">
													<div class="input-group-addon">
														<i class="icon-envelope-open"></i>
													</div>
													<input type="email" class="form-control" required=""
														autofocus="true" autocomplete="off" id="userEmail"
														onkeyup="checkDataFill()" name="userEmail"
														placeholder="Enter Email">
												</div>
											</div>
											<div class="form-group">
												<div class="input-group">
													<div class="input-group-addon">
														<i class="icon-lock"></i>
													</div>
													<input type="password" class="form-control" required=""
														id="userPassword" autocomplete="off" name="userPassword"
														onkeyup="checkDataFill()" placeholder="Enter password">
												</div>
											</div>
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