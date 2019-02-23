<%@page import="vo.NotificationVo"%>
<%@page import="dao.NotificationDao"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="dao.TransactionMasterDao"%>
<%@page import="vo.TransactionVo"%>
<%@page import="java.util.List"%>
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
<title>Budget</title>

<!-- Favicon -->
<link rel="shortcut icon" href="../../img/logo2.png">
<link rel="icon" href="../../img/logo2.png" type="image/x-icon">
<link rel="alternate" type="application/rss+xml" title="RSS 2.0"
	href="http://www.datatables.net/rss.xml">

<!-- Data table CSS -->
<link
	href="../../vendors/bower_components/datatables/media/css/jquery.dataTables.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="../../vendors/bower_components/datatables.net-responsive/css/responsive.dataTables.min.css"
	rel="stylesheet" type="text/css" />

<!-- Custom CSS -->
<link href="../../dist/css/style.css" rel="stylesheet" type="text/css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script type="text/javascript">
	function checkActive() {
		var element = document.getElementById('page-budget');
		element.classList.add("active");
		<%Object checkNotification = session.getAttribute("checkNotification");
			if (checkNotification != null) {
				session.removeAttribute("checkNotification");
				NotificationDao notificationDao = new NotificationDao();
				List<NotificationVo> notificationList = notificationDao.getAllNotifications(userVo);
				session.setAttribute("notificationsList", notificationList);
			}
			Object object = session.getAttribute("userMsg");
			if (object != null) {%>
			alert('<%=object%>');
		<%session.removeAttribute("userMsg");
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
	<!--Preloader-->
	<div class="preloader-it">
		<div class="la-anim-1"></div>
	</div>
	<!--/Preloader-->
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
			<div class="container-fluid">

				<!-- Title -->
				<div class="row heading-bg">
					<div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
						<h5 class="txt-dark">Budget</h5>
					</div>
					<!-- Breadcrumb -->
					<div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
						<ol class="breadcrumb">
							<li><a
								href="<%=request.getContextPath()%>/UserMasterController?flag=loadDashboard">Dashboard</a></li>
							<li class="active"><span>Budget</span></li>
						</ol>
					</div>
					<!-- /Breadcrumb -->
				</div>
				<!-- /Title -->

				<!-- Budget Modal Start -->
				<div id="responsive-modal" class="modal fade" tabindex="-1"
					role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
					style="display: none;">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">×</button>
								<h5 class="modal-title">Add Budget</h5>
							</div>
							<form method="post"
								action="<%=request.getContextPath()%>/BudgetMasterController">
								<input type="hidden" name="flag" value="addBudget">
								<div class="modal-body">
									<div class="row row-lg">
										<div class="col-md-12">
											<div class="form-group">
												<label for="budgetName" class="control-label mb-10">Budget
													Name:-</label> <input type="text" class="form-control"
													id="budgetName" name="budgetName" required=""
													placeholder="Enter Budget Name" autocomplete="off">
											</div>
										</div>
										<div class="col-md-12">
											<div class="col-md-6">
												<label for="startDate" class="control-label mb-10">Start
													date:-</label><input type="date" class="form-control"
													id="startDate" name="startDate" required="" place
													autocomplete="off">
											</div>
											<div class="col-md-6">
												<label for="endDate" class="control-label mb-10">End
													Date:-</label><input type="date" class="form-control" id="endDate"
													name="endDate" required="" autocomplete="off">
											</div>
										</div>
										<div class="col-md-12">&nbsp;</div>
										<div class="col-md-12">
											<div class="col-md-6">
												<div class="form-group">
													<label for="budgetAmount" class="control-label mb-10">Budget
														Amount:-</label> <input type="number" class="form-control"
														id="budgetAmount" name="budgetAmount" required=""
														placeholder="Enter Budget Amount" autocomplete="off">
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label for="alertAmount" class="control-label mb-10">Alert
														Amount:-</label> <input type="number" class="form-control"
														id="alertAmount" name="alertAmount" required=""
														placeholder="Enter Alert amount for notification"
														autocomplete="off">
												</div>
											</div>
										</div>
										<div class="col-md-12">
											<div class="form-group">
												<label for="budgetDecription" class="control-label mb-10">Description:-</label>
												<textarea class="form-control" id="budgetDecription"
													rows="4" placeholder="Extra Budget Description"
													name="budgetDecription"></textarea>
											</div>
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Close</button>
									<input type="submit" class="btn btn-danger" value="Add Budget">
								</div>
							</form>
						</div>
					</div>
				</div>
				<!-- Budget Modal End -->

				<!-- Row -->
				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-default card-view">
							<div class="panel-heading">
								<div class="pull-left">
									<h6 class="panel-title txt-dark">Budget</h6>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="panel-wrapper collapse in">
								<div class="panel-body">
									<div class="row row-lg">
										<div class="col-md-5">
											<div class="col-md-1"></div>
											<div class="col-md-11">
												<input type="button" value="Add Budget" name="budgetModal"
													id="budgetModal" data-toggle="modal"
													data-target="#responsive-modal"
													class="btn  btn-primary btn-rounded">
											</div>
										</div>
									</div>
									<div class="table-wrap">
										<div class="">
											<table id="myTable1" class="table table-hover display  pb-30">
												<thead>
													<tr>
														<th>Budget No</th>
														<th>Name</th>
														<th>Budget Amount</th>
														<th>Start Date</th>
														<th>End Date</th>
														<th>Remaining Amount</th>
														<th>Alert Amount</th>
													</tr>
												</thead>
												<tfoot>
													<tr>
														<th>Budget No</th>
														<th>Name</th>
														<th>Budget Amount</th>
														<th>Start Date</th>
														<th>End Date</th>
														<th>Remaining Amount</th>
														<th>Alert Amount</th>
													</tr>
												</tfoot>
												<tbody>
													<%
														int count = 1;
													%>
													<c:forEach var="budget"
														items="${ sessionScope.budgetList }">
														<tr>
															<td><%=count++%></td>
															<td><a style="color: blue"
																href='<%= request.getContextPath() %>/BudgetMasterController?flag=loadSpecificBudget&value=${ budget.budgetId }'>${ budget.budgetName }</a></td>
															<td>${ budget.budgetAmount }</td>
															<td>${ budget.budgetStartDate }</td>
															<td>${ budget.budgetEndDate }</td>
															<td>${ budget.budgetAmountLeft }</td>
															<td>${ budget.budgetAlertAmount }</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
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
	<!-- /#wrapper -->

	<!-- JavaScript -->

	<!-- jQuery -->
	<script src="../../vendors/bower_components/jquery/dist/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script
		src="../../vendors/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Data table JavaScript -->
	<script
		src="../../vendors/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
	<script
		src="../../vendors/bower_components/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
	<script
		src="../../vendors/bower_components/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
	<script src="../../dist/js/responsive-datatable-data.js"></script>

	<!-- Slimscroll JavaScript -->
	<script src="../../dist/js/jquery.slimscroll.js"></script>

	<!-- Fancy Dropdown JS -->
	<script src="../../dist/js/dropdown-bootstrap-extended.js"></script>

	<!-- Sparkline JavaScript -->
	<script
		src="../../vendors/jquery.sparkline/dist/jquery.sparkline.min.js"></script>

	<!-- Owl JavaScript -->
	<script
		src="../../vendors/bower_components/owl.carousel/dist/owl.carousel.min.js"></script>

	<!-- Switchery JavaScript -->
	<script
		src="../../vendors/bower_components/switchery/dist/switchery.min.js"></script>

	<!-- Init JavaScript -->
	<script src="../../dist/js/init.js"></script>
</body>
</html>