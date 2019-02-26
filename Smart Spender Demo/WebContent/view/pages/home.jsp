<%@page import="vo.NotificationVo"%>
<%@page import="java.util.List"%>
<%@page import="dao.NotificationDao"%>
<%@page import="dao.UserMasterDao"%>
<%@page import="vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<%
	UserVo userVo = (UserVo) session.getAttribute("user");
	if (userVo == null) {
		response.sendRedirect(request.getContextPath() + "/view/user/login.jsp");
	}
%>
<head>
<meta charset="UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>Dashboard</title>

<!-- Favicon -->
<link rel="shortcut icon" href="../../img/logo2.png">
<link rel="icon" href="../../img/logo2.png" type="image/x-icon">

<!-- Morris Charts CSS -->
<link href="../../vendors/bower_components/morris.js/morris.css"
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
			<%Object checkNotification = session.getAttribute("checkNotification");
			if (checkNotification != null) {
				session.removeAttribute("checkNotification");
				NotificationDao notificationDao = new NotificationDao();
				List<NotificationVo> notificationList = notificationDao.getAllNotifications(userVo);
				session.setAttribute("notificationsList", notificationList);
				session.setAttribute("notificationSize", notificationList.size());
			}%>
		}
		
		function changeThemeClass(div)
		{
			var xmlhttp;
			var url = '<%=request.getContextPath()%>/UserMasterController?flag=changeThemeDiv&value='+div.classList;

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
	<!-- /Preloader -->
	<div id="themeClass" onchange="changeThemeClass(this)"
		class="${ sessionScope.user.preLoaderClass }">
		<!-- Top Menu Items -->
		<jsp:include page="../general/top-menu.jsp"></jsp:include>
		<!-- /Top Menu Items -->

		<!-- Left Sidebar Menu -->
		<jsp:include page="../general/left-sidebar-menu.jsp"></jsp:include>
		<!-- /Left Sidebar Menu -->

		<!-- Right Sidebar Menu -->
		<jsp:include page="../general/right-sidebar-menu.jsp"></jsp:include>
		<!-- /Right Sidebar Menu -->

		<!-- Right Setting Menu -->
		<jsp:include page="../general/floating-setting-menu.jsp"></jsp:include>
		<!-- /Right Setting Menu -->

		<!-- Right Sidebar Backdrop -->
		<div class="right-sidebar-backdrop"></div>
		<!-- /Right Sidebar Backdrop -->

		<!-- Main Content -->
		<div class="page-wrapper">
			<div class="container-fluid pt-25">

				<!-- Row -->
				<div class="row row-lg">
					<div class="col-md-3">&nbsp;</div>
					<div class="col-md-6">
						<div class="panel panel-default card-view pa-0">
							<div class="panel-wrapper collapse in">
								<div class="panel-body pa-0">
									<div class="sm-data-box bg-blue">
										<div class="container-fluid">
											<div class="row">
												<div class="col-xs-6 text-center pl-0 pr-0 data-wrap-left">
													<span class="txt-light block counter"><span
														class="counter-anim">${ sessionScope.currentBalance }</span></span>
													<span
														class="weight-500 uppercase-font txt-light block font-13">Current
														Balance</span>
												</div>
												<div class="col-xs-6 text-center  pl-0 pr-0 data-wrap-right">
													<i class="zmdi zmdi txt-light data-right-rep-icon"><img
														alt="Current Balance" src="../../img/menu/bank-md.png"></i>
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
					<div class="col-md-3">
						<div class="panel panel-default card-view pa-0">
							<div class="panel-wrapper collapse in">
								<div class="panel-body pa-0">
									<div class="sm-data-box bg-green">
										<div class="container-fluid">
											<div class="row">
												<div class="col-xs-6 text-center pl-0 pr-0 data-wrap-left">
													<span class="txt-light block counter"><span
														class="counter-anim">${ sessionScope.todayIncome }</span></span>
													<span class="weight-500 uppercase-font txt-light block">Today's
														Income</span>
												</div>
												<div class="col-xs-6 text-center  pl-0 pr-0 data-wrap-right">
													<i class="zmdi txt-light data-right-rep-icon"><img
														alt="Today's Income" src="../../img/menu/income-md.png"></i>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="panel panel-default card-view pa-0">
							<div class="panel-wrapper collapse in">
								<div class="panel-body pa-0">
									<div class="sm-data-box bg-green">
										<div class="container-fluid">
											<div class="row">
												<div class="col-xs-6 text-center pl-0 pr-0 data-wrap-left">
													<span class="txt-light block counter"><span
														class="counter-anim">${ sessionScope.weekIncome }</span></span> <span
														class="weight-500 uppercase-font txt-light block">Current
														Week's Income</span>
												</div>
												<div class="col-xs-6 text-center  pl-0 pr-0 data-wrap-right">
													<i class="zmdi txt-light data-right-rep-icon"><img
														alt="Week's Income" src="../../img/menu/income-md.png"></i>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="panel panel-default card-view pa-0">
							<div class="panel-wrapper collapse in">
								<div class="panel-body pa-0">
									<div class="sm-data-box bg-green">
										<div class="container-fluid">
											<div class="row">
												<div class="col-xs-6 text-center pl-0 pr-0 data-wrap-left">
													<span class="txt-light block counter"><span
														class="counter-anim">${ sessionScope.monthIncome }</span></span>
													<span class="weight-500 uppercase-font txt-light block">Month's
														Income</span>
												</div>
												<div class="col-xs-6 text-center  pl-0 pr-0 data-wrap-right">
													<i class="zmdi txt-light data-right-rep-icon"><img
														alt="Month's Income" src="../../img/menu/income-md.png"></i>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="panel panel-default card-view pa-0">
							<div class="panel-wrapper collapse in">
								<div class="panel-body pa-0">
									<div class="sm-data-box bg-green">
										<div class="container-fluid">
											<div class="row">
												<div class="col-xs-6 text-center pl-0 pr-0 data-wrap-left">
													<span class="txt-light block counter"><span
														class="counter-anim">${ sessionScope.yearIncome }</span></span> <span
														class="weight-500 uppercase-font txt-light block">Current
														Year's Income</span>
												</div>
												<div class="col-xs-6 text-center  pl-0 pr-0 data-wrap-right">
													<i class="zmdi txt-light data-right-rep-icon"><img
														alt="Years's Income" src="../../img/menu/income-md.png"></i>
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
					<div class="col-lg-3">
						<div class="panel panel-default card-view pa-0">
							<div class="panel-wrapper collapse in">
								<div class="panel-body pa-0">
									<div class="sm-data-box bg-red">
										<div class="container-fluid">
											<div class="row">
												<div class="col-xs-6 text-center pl-0 pr-0 data-wrap-left">
													<span class="txt-light block counter"><span
														class="counter-anim">${ sessionScope.todayExpense }</span></span>
													<span
														class="weight-500 uppercase-font txt-light block font-13">Today's
														Expense</span>
												</div>
												<div class="col-xs-6 text-center  pl-0 pr-0 data-wrap-right">
													<i class="zmdi txt-light data-right-rep-icon"><img
														alt="Today's Expense" src="../../img/menu/expense-md.png"></i>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-3">
						<div class="panel panel-default card-view pa-0">
							<div class="panel-wrapper collapse in">
								<div class="panel-body pa-0">
									<div class="sm-data-box bg-red">
										<div class="container-fluid">
											<div class="row">
												<div class="col-xs-6 text-center pl-0 pr-0 data-wrap-left">
													<span class="txt-light block counter"><span
														class="counter-anim">${ sessionScope.weekExpense }</span></span>
													<span
														class="weight-500 uppercase-font txt-light block font-13">Current
														Week's Expense</span>
												</div>
												<div class="col-xs-6 text-center  pl-0 pr-0 data-wrap-right">
													<i class="zmdi txt-light data-right-rep-icon"><img
														alt="Week's Expense" src="../../img/menu/expense-md.png"></i>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-3">
						<div class="panel panel-default card-view pa-0">
							<div class="panel-wrapper collapse in">
								<div class="panel-body pa-0">
									<div class="sm-data-box bg-red">
										<div class="container-fluid">
											<div class="row">
												<div class="col-xs-6 text-center pl-0 pr-0 data-wrap-left">
													<span class="txt-light block counter"><span
														class="counter-anim">${ sessionScope.monthExpense }</span></span>
													<span
														class="weight-500 uppercase-font txt-light block font-13">Month's
														Expense</span>
												</div>
												<div class="col-xs-6 text-center  pl-0 pr-0 data-wrap-right">
													<i class="zmdi txt-light data-right-rep-icon"><img
														alt="Month's Expense" src="../../img/menu/expense-md.png"></i>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-3">
						<div class="panel panel-default card-view pa-0">
							<div class="panel-wrapper collapse in">
								<div class="panel-body pa-0">
									<div class="sm-data-box bg-red">
										<div class="container-fluid">
											<div class="row">
												<div class="col-xs-6 text-center pl-0 pr-0 data-wrap-left">
													<span class="txt-light block counter"><span
														class="counter-anim">${ sessionScope.yearExpense }</span></span>
													<span
														class="weight-500 uppercase-font txt-light block font-13">Current
														Year's Expense</span>
												</div>
												<div class="col-xs-6 text-center  pl-0 pr-0 data-wrap-right">
													<i class="zmdi txt-light data-right-rep-icon"><img
														alt="Year's Expense" src="../../img/menu/expense-md.png"></i>
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
					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12">
						<div class="panel panel-default card-view">
							<div class="panel-heading">
								<div class="pull-left">
									<h6 class="panel-title txt-dark">Yearly Statistics</h6>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="panel-wrapper collapse in">
								<div class="panel-body">
									<div id="morris_extra_line_chart" class="morris-chart"
										style="height: 293px;"></div>
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

	<script type="text/javascript">
	$(document).ready(function(){
		if($('#morris_extra_line_chart').length > 0) {
			var data=[{
	            period: 'Son',
	            income: 50,
	            expense: 80
	        }, {
	            period: 'Mon',
	            income: 50,
	            expense: 80
	        }, {
	            period: 'Tue',
	            income: 50,
	            expense: 80
	        }, {
	            period: 'Wed',
	            income: 50,
	            expense: 80
	        }, {
	            period: 'Thu',
	            income: 50,
	            expense: 80
	        }, {
	            period: 'Fri',
	            income: 50,
	            expense: 80
	        },
	         {
	            period: 'Sat',
	            income: 50,
	            expense: 80
	        }];
			var dataNew = [{
	            period: 'Jan',
	            income: ${ sessionScope.graphData.janIncome },
	            expense: ${ sessionScope.graphData.janExpense }
	        }, 
			{
	            period: 'Feb',
	            income: ${ sessionScope.graphData.febIncome },
	            expense: ${ sessionScope.graphData.febExpense }
	        },
			{
	            period: 'Mar',
	            income: ${ sessionScope.graphData.marIncome },
	            expense: ${ sessionScope.graphData.marExpense }
	        },
			{
	            period: 'April',
	            income: ${ sessionScope.graphData.aprIncome },
	            expense: ${ sessionScope.graphData.aprExpense }
	        },
			{
	            period: 'May',
	            income: ${ sessionScope.graphData.mayIncome },
	            expense: ${ sessionScope.graphData.mayExpense }
	        },
			{
	            period: 'June',
	            income: ${ sessionScope.graphData.juneIncome },
	            expense: ${ sessionScope.graphData.juneExpense }
	        },
			{
	            period: 'July',
	            income: ${ sessionScope.graphData.julyIncome },
	            expense: ${ sessionScope.graphData.julyExpense }
	        },
			{
	            period: 'Aug',
	            income: ${ sessionScope.graphData.augIncome },
	            expense: ${ sessionScope.graphData.augExpense }
	        },
			{
	            period: 'Sep',
	            income: ${ sessionScope.graphData.sepIncome },
	            expense: ${ sessionScope.graphData.sepExpense }
	        },
			{
	            period: 'Oct',
	            income: ${ sessionScope.graphData.octIncome },
	            expense: ${ sessionScope.graphData.octExpense }
	        },
			{
	            period: 'Nov',
	            income: ${ sessionScope.graphData.novIncome },
	            expense: ${ sessionScope.graphData.novExpense }
	        },
			{
	            period: 'Dec',
	            income: ${ sessionScope.graphData.decIncome },
	            expense: ${ sessionScope.graphData.decExpense }
	        }
			];
			var lineChart = Morris.Line({
	        element: 'morris_extra_line_chart',
	        data: data ,
	        xkey: 'period',
	        ykeys: ['income', 'expense'],
	        labels: ['income', 'expense'],
	        pointSize: 2,
	        fillOpacity: 0,
			lineWidth:2,
			pointStrokeColors:['#f2b701', '#b10058'],
			behaveLikeLine: true,
			gridLineColor: '#878787',
			hideHover: 'auto',
			lineColors: ['#f2b701', '#b10058'],
			resize: true,
			redraw: true,
			gridTextColor:'#878787',
			gridTextFamily:"Roboto",
	        parseTime: false
	    });

		}
		/* Switchery Init*/
		var elems = Array.prototype.slice.call(document.querySelectorAll('.js-switch'));
		$('#morris_switch').each(function() {
			new Switchery($(this)[0], $(this).data());
		});
		var swichMorris = function() {
			if($("#morris_switch").is(":checked")) {
				lineChart.setData(data);
				lineChart.redraw();
			} else {
				lineChart.setData(dataNew);
				lineChart.redraw();
			}
		}
		swichMorris();	
		$(document).on('change', '#morris_switch', function () {
			swichMorris();
		});
		
	});
	/*****Ready function end*****/

	/*****Load function start*****/
	</script>

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

	<!-- Init JavaScript -->
	<script src="../../dist/js/init.js"></script>
	<script src="../../dist/js/dashboard-data.js"></script>
</body>
</html>