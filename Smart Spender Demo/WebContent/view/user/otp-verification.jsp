<!DOCTYPE html>
<%@page import="vo.UserVo"%>
<html lang="en">
<head>
<%
	UserVo userVo=(UserVo)session.getAttribute("user");
	if(userVo==null)
	{
		response.sendRedirect(request.getContextPath()+"/view/user/login.jsp");
	}
%>
<meta charset="UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>Sign Up-Smart Spender</title>

<!-- Favicon -->
<link rel="shortcut icon" href="../../img/logo2.png">
<link rel="icon" href="../../img/logo2.png" type="image/x-icon">

<!-- Custom CSS -->
<link href="../../dist/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function enablebtn() {
		if (document.getElementById("checkbox_2").checked) {
			document.getElementById("sbmt").disabled = false;
		} else {
			document.getElementById("sbmt").disabled = true;
		}
	}

	function checkOPT() {
		var otpValue = document.getElementById('opt-number').value;
		if(otpValue.length<1)
		{
			alert("Enter Proper OTP for verification");
		}else{
			var xmlhttp;
			var url = '<%= request.getContextPath() %>/UserMasterController?flag=verifyOtp&value='+otpValue;
			
			if(window.XMLHttpRequest)
			{
				xmlhttp = new XMLHttpRequest();
			}else{
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			
			xmlhttp.onreadystatechange = function() {
				if(xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					var val = new String(xmlhttp.responseText);
					if(val.includes("Success"))
					{
						window.location.href = '<%= request.getContextPath() %>/view/user/login.jsp';
					}else if(val.includes("Correct")){
						window.location.href = '<%= request.getContextPath() %>/view/user/change-password.jsp';
					}else if(val.includes("MobileChange")){
						window.location.href = '<%= request.getContextPath() %>/view/user/update-contact.jsp';
					}else if(val.includes('loginPassword')){
						window.location.href = '<%= request.getContextPath() %>/view/user/user-logout.jsp';
					}
					else{
						alert("Wrong OTP entered");
					}
				}
			}
			
			xmlhttp.open('POST',url,true);
			xmlhttp.send();
		}
	}
</script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
	$(window).on('load', function() {
		$('#responsive-modal').modal('show');
	});
</script>
</head>
<body>

	<!-- Responsive Modal OTP -->
	<div id="responsive-modal" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
		style="display: none;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h5 class="modal-title">Enter OTP sent for Verification</h5>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<div class="row row-md">
							<div class="col-md-6">
								<label for="userMobile" class="control-label mb-10">OPT
									SENT TO MOBILE NUMBER:- </label>
							</div>
							<div class="col-md-6">
								<input type="text" value="${ sessionScope.user.userMobile }" class="form-control" readonly="true"
									id="userMobile">
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row row-md">
							<div class="col-md-4">
								<label for="opt-number" class="control-label mb-10">Enter
									OTP:-</label>
							</div>
							<div class="col-md-8">
								<input type="number" class="form-control" id="opt-number">
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" onclick="checkOPT()" class="btn btn-danger">Save
						changes</button>
				</div>
			</div>
		</div>
	</div>
	<!-- End Responsive Modal OTP -->

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
				<span class="inline-block pr-10">Already have an account?</span> <a
					class="inline-block btn btn-info btn-rounded btn-outline"
					href="<%= request.getContextPath() %>/view/user/login.html">Sign In</a>
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
										<h3 class="text-center txt-dark mb-10">Sign up to Smart
											Spender</h3>
										<h6 class="text-center nonecase-font txt-grey">Enter your
											details below</h6>
									</div>
									<div class="form-wrap">
										<input type=hidden name="flag" value="signup">
										<div class="form-group">
											<label class="control-label mb-10" for="userName">Enter
												Name</label> <input type="text" autocomplete="off"
												class="form-control" name="userName" required=""
												id="userName" placeholder="Enter Full Name">
										</div>
										<div class="form-group">
											<label class="control-label mb-10" for="userEmail">Email
												address</label> <input type="email" autocomplete="off"
												name="userEmail" class="form-control" required=""
												id="userEmail" placeholder="Enter email">
										</div>
										<div class="form-group">
											<label class="control-label mb-10" for="userMobile">Mobile
												Number</label> <input type="number" autocomplete="off"
												name="userMobile" class="form-control" required=""
												id="userMobile" placeholder="Enter Mobile No">
										</div>
										<div class="form-group">
											<label class="pull-left control-label mb-10"
												for="userPassword">Password</label> <input type="password"
												autocomplete="off" name="userPassword" class="form-control"
												required="" id="userPassword" placeholder="Enter Password">
										</div>
										<div class="form-group">
											<label class="pull-left control-label mb-10"
												for="confirmPassword">Confirm Password</label> <input
												type="password" autocomplete="off" name="confirmPassword"
												class="form-control" required="" id="confirmPassword"
												placeholder="Confirm Password">
										</div>
										<div class="form-group">
											<div class="checkbox checkbox-primary pr-10 pull-left">
												<input id="checkbox_2" required="" type="checkbox"
													onchange="enablebtn()"> <label for="checkbox_2">
													I agree to all <span class="txt-primary">Terms</span>
												</label>
											</div>
											<div class="clearfix"></div>
										</div>
										<div class="form-group text-center">
											<img id="loading"> <input type="submit" id="sbmt"
												data-toggle="modal" data-target="#responsive-modal"
												disabled="true" class="btn btn-info btn-rounded"
												value="Sign Up">
										</div>
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