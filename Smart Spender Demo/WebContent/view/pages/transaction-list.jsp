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
<title>Transaction List</title>

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
		var element = document.getElementById('transaction-list');
		element.classList.add("active");
			<%Object incomeObject = session.getAttribute("incomeFlag");
			if (incomeObject != null) {%>
				document.getElementById("forCategory").selectedIndex = "0";
			<%session.removeAttribute("incomeFlag");
			}
			Object expenseObject = session.getAttribute("expenseFlag");
			if (expenseObject != null) {%>
				document.getElementById("forCategory").selectedIndex = "1";
			<%session.removeAttribute("expenseFlag");
			}
			Object userMsg = session.getAttribute("userMsg");
			if (userMsg != null) {%>
				alert('<%=userMsg%>');
			<%session.removeAttribute("userMsg");
			}
			Object checkNotification = session.getAttribute("checkNotification");
			if (checkNotification != null) {
				session.removeAttribute("checkNotification");
				NotificationDao notificationDao = new NotificationDao();
				List<NotificationVo> notificationList = notificationDao.getAllNotifications(userVo);
				session.setAttribute("notificationsList", notificationList);
				session.setAttribute("notificationSize", notificationList.size());
			}%>
	}
	
	function reloadData()
	{
		var ddlValue = document.getElementById('forCategory').value;
		var url = '<%=request.getContextPath()%>/TransactionMasterController?flag='+ddlValue;
		window.location.href = url;
	}
	
	function upload(file) {
		var imgVal = document.getElementById('frmReceipt'+file.id);
		imgVal.submit();
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
						<h5 class="txt-dark">Transaction List</h5>
					</div>
					<!-- Breadcrumb -->
					<div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
						<ol class="breadcrumb">
							<li><a
								href="<%=request.getContextPath()%>/UserMasterController?flag=loadDashboard">Dashboard</a></li>
							<li class="active"><span>Transaction List</span></li>
						</ol>
					</div>
					<!-- /Breadcrumb -->
				</div>
				<!-- /Title -->

				<!-- Row -->
				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-default card-view">
							<div class="panel-heading">
								<div class="pull-left">
									<h6 class="panel-title txt-dark">Transaction Listing</h6>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="panel-wrapper collapse in">
								<div class="panel-body">
									<div class="row row-lg">
										<div class="col-md-5">
											<div class="form-group">
												<label class="control-label mb-10" for="forCategory">Select
													Transaction For:-</label> <select
													class="form-control select2 select2-hidden-accessible"
													tabindex="-1" aria-hidden="true" name="forCategory"
													id="forCategory" onchange="reloadData()">
													<option value="loadIncomeTransaction">Income</option>
													<option value="loadExpenseTransaction">Expense</option>
												</select>
											</div>
										</div>
									</div>
									<form
										action="<%=request.getContextPath()%>/TransactionMasterController"
										method="post">
										<input type="hidden" name="flag" value="generateReport">
										<div class="row row-lg">
											<div class="col-md-4">
												<div class="form-group">
													<label class="control-label mb-10" for="month">Select
														Month:-</label> <select
														class="form-control select2 select2-hidden-accessible"
														tabindex="-1" aria-hidden="true" name="month" id="month">
														<option value="1">January</option>
														<option value="2">February</option>
														<option value="3">March</option>
														<option value="4">April</option>
														<option value="5">May</option>
														<option value="6">June</option>
														<option value="7">July</option>
														<option value="8">August</option>
														<option value="9">September</option>
														<option value="10">October</option>
														<option value="11">November</option>
														<option value="12">December</option>
													</select>
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<label class="control-label mb-10" for="year">Enter
														Year:-</label> <input type="text" autocomplete="off"
														class="form-control" id="year" name="year" value=""
														required="" placeholder="Enter Year (yyyy) Format">
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<div class="col-md-5">
														<label class="control-label mb-10" for="btnReport">&nbsp;</label>
														<input type="submit" id="btnReport" name="btnReport"
															class="btn  btn-success btn-rounded"
															value="Generate Report">
													</div>
												</div>
											</div>
										</div>
									</form>
									<div class="table-wrap">

										<div class="">
											<table id="myTable1" class="table table-hover display  pb-30">
												<thead>
													<tr>
														<th>Transaction No</th>
														<th>Payee Name</th>
														<th>Sub Category Name</th>
														<th>Transaction Amount</th>
														<th>Available Balance</th>
														<th>Date & Time</th>
														<th>Payment Method</th>
														<th>Status Of Transaction</th>
														<th>Upload Receipt</th>
													</tr>
												</thead>
												<tfoot>
													<tr>
														<th>Transaction No</th>
														<th>Payee Name</th>
														<th>Sub Category Name</th>
														<th>Transaction Amount</th>
														<th>Available Balance</th>
														<th>Date & Time</th>
														<th>Payment Method</th>
														<th>Status Of Transaction</th>
														<th>Upload Receipt</th>
													</tr>
												</tfoot>
												<tbody>
													<%
														int count = 1;
													%>
													<c:forEach var="transactionData"
														items="${ sessionScope.transactionList }">
														<tr>
															<td><a style="color: blue"
																href="<%= request.getContextPath() %>/TransactionMasterController?flag=loadTransactionDetails&id=${ transactionData.transactionIdentificationNumber }">${ transactionData.transactionIdentificationNumber }</a></td>
															<td>${ transactionData.payeeName }</td>
															<td>${ transactionData.subCategoriesVo.subCategoryName }</td>
															<td>${ transactionData.transactionAmount }</td>
															<td>${ transactionData.totalAvailableBalance }</td>
															<td>${ transactionData.transactionDateTime }</td>
															<td>${ transactionData.paymentMethod }</td>
															<td>${ transactionData.statusOfTransaction }</td>
															<td><form name="frmReceipt"
																	id="frmReceipt${ transactionData.transactionId }"
																	method="post" enctype="multipart/form-data"
																	action="<%=request.getContextPath()%>/TransactionMasterController?flag=uploadReceiptImage&id=${ transactionData.transactionIdentificationNumber }">
																	<input id="${ transactionData.transactionId }"
																		name="receiptImage${ transactionData.transactionId }"
																		class="upload" type="file" onchange="upload(this)">
																</form></td>
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

	<!-- Bootstrap Select JavaScript -->
	<script
		src="../../vendors/bower_components/bootstrap-select/dist/js/bootstrap-select.min.js"></script>

	<!-- Select2 JavaScript -->
	<script
		src="../../vendors/bower_components/select2/dist/js/select2.full.min.js"></script>

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