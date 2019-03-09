<%@page import="vo.SuperUserVo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<%
	SuperUserVo superUserVo = (SuperUserVo) session.getAttribute("superUser");
	if (superUserVo == null) {
		response.sendRedirect(request.getContextPath() + "/admin.jsp");
	}
%>
<head>
<meta charset="UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>Admin Dashboard</title>

<!-- Favicon -->
<link rel="shortcut icon" href="../../img/logo2.png">
<link rel="icon" href="../../img/logo2.png" type="image/x-icon">

<!-- Morris Charts CSS -->
<link href="../../vendors/bower_components/morris.js/morris.css"
	rel="stylesheet" type="text/css" />

<!-- Calendar CSS -->
<link
	href="../../vendors/bower_components/fullcalendar/dist/fullcalendar.css"
	rel="stylesheet" type="text/css" />

<!-- Data table CSS -->
<link
	href="../../vendors/bower_components/datatables/media/css/jquery.dataTables.min.css"
	rel="stylesheet" type="text/css" />

<link
	href="../../vendors/bower_components/jquery-toast-plugin/dist/jquery.toast.min.css"
	rel="stylesheet" type="text/css">

<!-- Custom CSS -->
<link href="../../dist/css/style.css" rel="stylesheet" type="text/css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script type="text/javascript">
	function checkActive()
	{
		var element = document.getElementById('page-dashboard');
		element.classList.add("active");
	}
		
	function changeThemeClass(div)
	{
		var xmlhttp;
		var url = '<%=request.getContextPath()%>/SuperUserController?flag=changeThemeDiv&value='+div.classList;

		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var themeClass = document.getElementById('themeClass');
				themeClass.classList = xmlhttp.responseText;
			}
		}

		xmlhttp.open('POST', url, true);
		xmlhttp.send();
	}
</script>
</head>

<body onload="checkActive()">
	<!-- Preloader -->
	<div class="preloader-it">
		<div class="la-anim-1"></div>
	</div>
	<!-- End Preloader -->

	<div id="themeClass" onchange="changeThemeClass(this)"
		class="${ sessionScope.superUser.preLoaderClass }">
		
		<!-- Top Menu Items -->
		<jsp:include page="../admin-general/top-menu.jsp"></jsp:include>
		<!-- /Top Menu Items -->

		<!-- Left Sidebar Menu -->
		<jsp:include page="../admin-general/left-sidebar-menu.jsp"></jsp:include>
		<!-- /Left Sidebar Menu -->

		<!-- Right Setting Menu -->
		<jsp:include page="../admin-general/floating-setting-menu.jsp"></jsp:include>
		<!-- /Right Setting Menu -->

		<!-- Right Sidebar Backdrop -->
		<div class="right-sidebar-backdrop"></div>
		<!-- /Right Sidebar Backdrop -->

		<!-- Main Content -->
		<div class="page-wrapper">
			<div class="container-fluid pt-25">

				<!-- Row -->
				<div class="row row-lg">
					<div class="col-md-4">
						<div class="panel panel-default card-view pa-0">
							<div class="panel-wrapper collapse in">
								<div class="panel-body pa-0">
									<div class="sm-data-box bg-green">
										<div class="container-fluid">
											<div class="row">
												<div class="col-xs-6 text-center pl-0 pr-0 data-wrap-left">
													<span class="txt-light block counter"><span
														class="counter-anim">${ sessionScope.totalRegisteredusers }</span></span>
													<span
														class="weight-500 uppercase-font txt-light block font-13">Total
														Registered users</span>
												</div>
												<div class="col-xs-6 text-center  pl-0 pr-0 data-wrap-right">
													<i class="zmdi zmdi txt-light data-right-rep-icon"><img
														alt="Total registered users"
														src="../../img/menu/registered-users-md.png"></i>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="panel panel-default card-view pa-0">
							<div class="panel-wrapper collapse in">
								<div class="panel-body pa-0">
									<div class="sm-data-box bg-yellow">
										<div class="container-fluid">
											<div class="row">
												<div class="col-xs-6 text-center pl-0 pr-0 data-wrap-left">
													<span class="txt-light block counter"><span
														class="counter-anim">${ sessionScope.currentOnlineUsers }</span></span>
													<span
														class="weight-500 uppercase-font txt-light block font-13">Current
														Online Users</span>
												</div>
												<div class="col-xs-6 text-center  pl-0 pr-0 data-wrap-right">
													<i class="zmdi zmdi txt-light data-right-rep-icon"><img
														alt="Current Online Users"
														src="../../img/menu/online-users-md.png"></i>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="panel panel-default card-view pa-0">
							<div class="panel-wrapper collapse in">
								<div class="panel-body pa-0">
									<div class="sm-data-box bg-green">
										<div class="container-fluid">
											<div class="row">
												<div class="col-xs-6 text-center pl-0 pr-0 data-wrap-left">
													<span class="txt-light block counter"><span
														class="counter-anim">${ sessionScope.inventoryPermissionRequests }</span></span>
													<span
														class="weight-500 uppercase-font txt-light block font-13">Total
														Inventory Permission Requests</span>
												</div>
												<div class="col-xs-6 text-center  pl-0 pr-0 data-wrap-right">
													<i class="zmdi zmdi txt-light data-right-rep-icon"><img
														alt="Total Inventory Permission Requests"
														src="../../img/menu/inventory-permission-md.png"></i>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row row-lg">
					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
						<div class="panel panel-default card-view">
							<div class="panel-wrapper collapse in">
								<div class="panel-body">
									<div class="calendar-wrap">
										<div id="calendar_small" class="small-calendar"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- /Row -->
			</div>
			<!-- Footer -->
			<footer class="footer container-fluid pl-30 pr-30">
				<div class="row">
					<div class="col-sm-12">
						<p>2019 &copy; Smart Spender. Developed by SMCodeTech</p>
					</div>
				</div>
			</footer>
			<!-- /Footer -->

		</div>
		<!-- /Main Content -->

	</div>

	<!-- jQuery -->
	<script src="../../vendors/bower_components/jquery/dist/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script
		src="../../vendors/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Data table JavaScript -->
	<script
		src="../../vendors/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>

	<!-- Slimscroll JavaScript -->
	<script src="../../dist/js/jquery.slimscroll.js"></script>

	<!-- simpleWeather JavaScript -->
	<script src="../../vendors/bower_components/moment/min/moment.min.js"></script>
	<script
		src="../../vendors/bower_components/simpleWeather/jquery.simpleWeather.min.js"></script>
	<script src="../../dist/js/simpleweather-data.js"></script>

	<!-- Progressbar Animation JavaScript -->
	<script
		src="../../vendors/bower_components/waypoints/lib/jquery.waypoints.min.js"></script>
	<script
		src="../../vendors/bower_components/jquery.counterup/jquery.counterup.min.js"></script>

	<!-- Fancy Dropdown JS -->
	<script src="../../dist/js/dropdown-bootstrap-extended.js"></script>

	<!-- Sparkline JavaScript -->
	<script
		src="../../vendors/jquery.sparkline/dist/jquery.sparkline.min.js"></script>

	<!-- Owl JavaScript -->
	<script
		src="../../vendors/bower_components/owl.carousel/dist/owl.carousel.min.js"></script>

	<!-- ChartJS JavaScript -->
	<script src="../../vendors/chart.js/Chart.min.js"></script>

	<!-- Morris Charts JavaScript -->
	<script src="../../vendors/bower_components/raphael/raphael.min.js"></script>
	<script src="../../vendors/bower_components/morris.js/morris.min.js"></script>
	<script
		src="../../vendors/bower_components/jquery-toast-plugin/dist/jquery.toast.min.js"></script>

	<!-- Switchery JavaScript -->
	<script
		src="../../vendors/bower_components/switchery/dist/switchery.min.js"></script>

	<!-- Calender JavaScripts -->
	<script src="../../vendors/bower_components/moment/min/moment.min.js"></script>
	<script src="../../vendors/jquery-ui.min.js"></script>
	<script
		src="../../vendors/bower_components/fullcalendar/dist/fullcalendar.min.js"></script>
	<script src="../../dist/js/fullcalendar-data.js"></script>

	<!-- Init JavaScript -->
	<script src="../../dist/js/init.js"></script>
	<script src="../../dist/js/dashboard-data.js"></script>
</body>
</html>