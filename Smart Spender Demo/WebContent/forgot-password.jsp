<!DOCTYPE html>
<html lang="en">

<!-- Mirrored from hencework.com/theme/magilla/full-width-light/forgot-password.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 09 Jan 2019 07:52:02 GMT -->
<head>
<meta charset="UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>Forgot Password-Smart Spender</title>

<!-- Favicon -->
<link rel="shortcut icon" href="favicon.ico">
<link rel="icon" href="img/logo2.png" type="image/x-icon">
<!-- Custom CSS -->
<link href="dist/css/style.css" rel="stylesheet" type="text/css">
</head>
<script type="text/javascript">
	function checkData()
	{
		var userMobile = document.getElementById('userMobile').value;
		if(userMobile.length<10 && userMobile.length>10)
		{
			alert('Enter Proper Mobile Number');
			return false;
		}
		return true;
	}
</script>
<body>
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
				<span class="inline-block pr-10">Already have an account</span> <a
					class="inline-block btn btn-info btn-rounded btn-outline"
					href="login.jsp">Sign In</a>
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
										<h3 class="text-center txt-dark mb-10">Forgot your
											password</h3>
										<h6 class="text-center txt-grey nonecase-font">Enter the
											email and mobile number you use for Smart Spender, and we will help you create
											a new password.</h6>
									</div>
									<div class="form-wrap">
										<form method="post" onsubmit="return checkData()" action="<%= request.getContextPath() %>/UserMasterController">
											<input type="hidden" name="flag" value="forgot-password">
											<div class="form-group">
												<label class="control-label mb-10" for="userEmail">Email
													address</label> <input type="email" class="form-control"
													required="" id="userEmail" autofocus="true" autocomplete="false" name="userEmail" placeholder="Enter email">
											</div>
											<div class="form-group">
												<label class="control-label mb-10" for="userMobile">Mobile
													Number</label> <input type="number" class="form-control" required=""
													id="userMobile" autocomplete="false" name="userMobile" placeholder="Enter Mobile Number">
											</div>
											<div class="form-group text-center">
												<button type="submit" class="btn btn-info btn-rounded">Reset</button>
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