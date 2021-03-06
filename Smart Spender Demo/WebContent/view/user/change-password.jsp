<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>Change Password-Smart Spender</title>

<!-- Favicon -->
<link rel="shortcut icon" href="../../img/logo2.png">
<link rel="icon" href="../../img/logo2.png" type="image/x-icon">
<!-- Custom CSS -->
<link href="../../dist/css/style.css" rel="stylesheet" type="text/css">
</head>
<script type="text/javascript">
	function checkData() {
		var userPassword = document.getElementById('userPassword').value;
		var confirmPassword = document.getElementById('confirmPassword').value;
		if (userPassword != confirmPassword) {
			alert('Enter both passwords same');
			return false;
		}
		return true;
	}
</script>
<body>
<%
	Object myObj=session.getAttribute("userForgot");
	if(myObj==null)
	{
		response.sendRedirect(request.getContextPath()+"/view/user/login.jsp");
	}
%>
	<!--Preloader-->
	<div class="preloader-it">
		<div class="la-anim-1"></div>
	</div>
	<!--/Preloader-->

	<div class="wrapper pa-0">
		<header class="sp-header">
			<div class="sp-logo-wrap pull-left">
				<a href="<%= request.getContextPath() %>/view/user/login.jsp"> <img class="brand-img mr-10"
					src="../../img/logo1.png" alt="brand" /> <span class="brand-text">Smart
						Spender</span>
				</a>
			</div>
			<div class="form-group mb-0 pull-right">
				<span class="inline-block pr-10">Remembered your password ? </span> <a
					class="inline-block btn btn-info btn-rounded btn-outline"
					href="<%= request.getContextPath() %>/view/userlogin.jsp">Sign In</a>
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
										<h3 class="text-center txt-dark mb-10">Set your new
											password</h3>
									</div>
									<div class="form-wrap">
										<form method="post" onsubmit="return checkData()"
											action="<%=request.getContextPath()%>/UserMasterController">
											<input type="hidden" name="flag" value="change-password">
											<div class="form-group">
												<label class="control-label mb-10" for="userPassword">New
													Password</label> <input type="password" class="form-control"
													required="" id="userPassword" autofocus="true"
													autocomplete="off" name="userPassword"
													placeholder="Enter New Password">
											</div>
											<div class="form-group">
												<label class="control-label mb-10" for="confirmPassword">Confirm
													Password</label> <input type="password" class="form-control"
													required="" id="confirmPassword" autocomplete="off"
													name="confirmPassword" placeholder="Enter Confirm Password">
											</div>
											<div class="form-group text-center">
												<button type="submit" class="btn btn-info btn-rounded">Change
													Password</button>
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
	<script src="../../vendors/bower_components/jquery/dist/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script
		src="../../vendors/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Slimscroll JavaScript -->
	<script src="../../dist/js/jquery.slimscroll.js"></script>

	<!-- Init JavaScript -->
	<script src="../../dist/js/init.js"></script>
</body>
</html>