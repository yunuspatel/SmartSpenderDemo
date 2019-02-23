<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>Budget Detail</title>

<!-- Favicon -->
<link rel="shortcut icon" href="../../img/logo2.png">
<link rel="icon" href="../../img/logo2.png" type="image/x-icon">

<!-- Custom CSS -->
<link href="../../dist/css/style.css" rel="stylesheet" type="text/css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
	
	function deleteBudget()
	{
		var confirmMessage = confirm('Are you sure you want to delete this transaction?');
		if(confirmMessage == true)
		{
			var url = '<%=request.getContextPath()%>/BudgetMasterController?flag=deleteBudget&budgetId=${ sessionScope.budgetDetail.budgetId }';
			window.location.href = url;
		}
	}
	
	function controlChanges() {
		document.getElementById('btnUpdate').disabled = false;
		document.getElementById('btnReset').disabled = false;
		document.getElementById('btnEdit').disabled = true;
		document.getElementById('budgetName').disabled = false;
		document.getElementById('budgetAmount').disabled = false;
		document.getElementById('startDate').disabled = false;
		document.getElementById('endDate').disabled = false;
		document.getElementById('alertAmount').disabled = false;
		document.getElementById('budgetDescription').disabled = false;
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
<body>
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
				<div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
					<h5 class="txt-dark">Transaction Detail</h5>
				</div>
				<div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
					<ol class="breadcrumb">
						<li><a
							href="<%=request.getContextPath()%>/UserMasterController?flag=loadDashboard">Main</a></li>
						<li><a
							href="<%=request.getContextPath()%>/view/pages/budget.jsp">Budget</a></li>
						<li class="active"><span>Budget Details</span></li>
					</ol>
				</div>

				<!-- Row -->
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-default card-view">
							<div class="panel-heading">
								<div class="pull-left">
									<h6 class="panel-title txt-dark">Budget Detail</h6>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="panel-wrapper collapse in">
								<div class="panel-body">
									<div class="form-wrap">
										<form name="frmBudget" id="frmBudget" method="post"
											action="<%=request.getContextPath()%>/BudgetMasterController">
											<input type="hidden" name="flag" value="editBudget">
											<div class="row row-lg">
												<div class="col-md-6">
													<div class="form-group">
														<label class="control-label mb-10" for="budgetName">Budget
															Name</label>
														<div class="input-group">
															<div class="input-group-addon">
																<i class="fa fa-suitcase"></i>
															</div>
															<input disabled="" type="text" autocomplete="off"
																required="" class="form-control" id="budgetName"
																name="budgetName"
																value="${ sessionScope.budgetDetail.budgetName }"
																placeholder="Enter Budget Name">
														</div>
													</div>
												</div>
												<div class="col-md-6">
													<div class="form-group">
														<label class="control-label mb-10" for="budgetAmount">Budget
															Amount</label>
														<div class="input-group">
															<div class="input-group-addon">
																<i class="fa fa-rupee"></i>
															</div>
															<input disabled="" type="number" autocomplete="off"
																required="" class="form-control" id="budgetAmount"
																name="budgetAmount" onkeyup="amountChanges()"
																value="${ sessionScope.budgetDetail.budgetAmount }"
																placeholder="Enter Budget Amount">
														</div>
													</div>
												</div>
												<div class="col-md-4">
													<div class="form-group">
														<label class="control-label mb-10" for="startDate">Start
															Date</label> <input disabled="" type="date" autocomplete="off"
															required="" class="form-control" id="startDate"
															name="startDate"
															value="${ sessionScope.budgetDetail.budgetStartDate }">
													</div>
												</div>
												<div class="col-md-4">
													<div class="form-group">
														<label class="control-label mb-10" for="endDate">End
															Date</label> <input disabled="" type="date" autocomplete="off"
															required="" class="form-control" id="endDate"
															name="endDate"
															value="${ sessionScope.budgetDetail.budgetEndDate }">
													</div>
												</div>
												<div class="col-md-4">
													<div class="form-group">
														<label class="control-label mb-10" for="alertAmount">Alert
															Amount</label> <input disabled="" type="number"
															autocomplete="off" required="" class="form-control"
															id="alertAmount" name="alertAmount"
															value="${ sessionScope.budgetDetail.budgetAlertAmount }"
															placeholder="Enter Budget Alert Amount">
													</div>
												</div>
												<div class="col-md-12">
													<div class="form-group">
														<label class="control-label mb-10" for="budgetDescription">Extra
															Description/Notes</label>
														<textarea id="budgetDescription" disabled=""
															name="budgetDescription" class="form-control" rows="3">${ sessionScope.budgetDetail.budgetDescription }</textarea>
													</div>
												</div>
												<div class="col-md-6">&nbsp;</div>
												<div class="col-md-3">
													<div class="col-md-6">
														<input type="button" id="btnEdit"
															class="btn btn-warning btn-rounded"
															onclick="controlChanges()" value="Edit">
													</div>
													<div class="col-md-6">
														<input type="button" class="btn btn-danger btn-rounded"
															onclick="deleteBudget()" value="Delete">
													</div>
												</div>
												<div class="col-md-3">
													<div class="col-md-6">
														<input type="submit" id="btnUpdate" disabled=""
															class="btn btn-success btn-rounded" value="Update">
													</div>
													<div class="col-md-6">
														<input type="reset" id="btnReset" disabled=""
															class="btn btn-danger btn-rounded" value="Reset">
													</div>
												</div>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /Row -->
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

	<!-- Switchery JavaScript -->
	<script
		src="../../vendors/bower_components/switchery/dist/switchery.min.js"></script>

	<!-- Slimscroll JavaScript -->
	<script src="../../dist/js/jquery.slimscroll.js"></script>

	<!-- Fancy Dropdown JS -->
	<script src="../../dist/js/dropdown-bootstrap-extended.js"></script>

	<!-- Owl JavaScript -->
	<script
		src="../../vendors/bower_components/owl.carousel/dist/owl.carousel.min.js"></script>

	<!-- Bootstrap Switch JavaScript -->
	<script
		src="../../vendors/bower_components/bootstrap-switch/dist/js/bootstrap-switch.min.js"></script>

	<!-- Init JavaScript -->
	<script src="../../dist/js/init.js"></script>
</body>
</html>