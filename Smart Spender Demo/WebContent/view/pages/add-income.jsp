<%@page import="vo.CategoryVo"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<%@page import="dao.CategoryMasterDao"%>
<%@page import="dao.UserMasterDao"%>
<%@page import="vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<%
	UserVo userVo = (UserVo) session.getAttribute("user");
	if (userVo == null) {
		response.sendRedirect(request.getContextPath()+"/view/user/login.jsp");
	} else {
		CategoryMasterDao categoryMasterDao = new CategoryMasterDao();
		List<CategoryVo> incomeList = categoryMasterDao.getCategoryList("income", userVo);
		session.setAttribute("incomeList", incomeList);
	}
%>
<head>
<meta charset="UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>Add Income</title>

<!-- Favicon -->
<link rel="shortcut icon" href="../../img/logo2.png">
<link rel="icon" href="../../img/logo2.png" type="image/x-icon">


<!-- select2 CSS -->
<link href="../../vendors/bower_components/select2/dist/css/select2.min.css"
	rel="stylesheet" type="text/css" />

<!-- bootstrap-select CSS -->
<link
	href="../../vendors/bower_components/bootstrap-select/dist/css/bootstrap-select.min.css"
	rel="stylesheet" type="text/css" />

<!-- Bootstrap Datetimepicker CSS -->
<link
	href="../../vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet" type="text/css" />


<!-- Custom CSS -->
<link href="../../dist/css/style.css" rel="stylesheet" type="text/css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">

	function checkActive() {
		var element = document.getElementById("page-add-income");
		element.classList.add("active");
		<%
			Object object=session.getAttribute("userMsg");
			if(object!=null)
			{
			%>
				alert("<%= object %>");
			<%
				session.removeAttribute("userMsg");
			}
		%>
	}
	
	function fillSubCategoryDropdown()
	{
		var elementValue = document.getElementById('categorySelect').value;
		var xmlhttp;
		var url = '<%=request.getContextPath()%>/CategoryController?flag=getSubCategory&forCategory=income&value='+ elementValue;

		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var z = (1 * 1) - 1;
				document.getElementById('subCategorySelect').options.length = 0;
				var cus = xmlhttp.responseXML.documentElement;
				var cusname = cus.getElementsByTagName("subcategoryname")[z].childNodes[0].nodeValue;
				var opt = document.createElement("OPTION");
				opt.text = "Select";
				opt.value = null;
				document.getElementById("subCategorySelect").add(opt);
				while (cusname != null) {
					addoptions(cusname);
					z++;
					var cusname = cus.getElementsByTagName("subcategoryname")[z].childNodes[0].nodeValue;
				}
			}

			function addoptions(cusname) {
				var opt = document.createElement("OPTION");
				opt.text = cusname;
				opt.value = cusname;
				document.getElementById("subCategorySelect").add(opt);
			}
		}

		xmlhttp.open('POST', url, true);
		xmlhttp.send();
	}

	function checkMethod() {
		var value = document.getElementById('paymentMethod').value;
		if (value.includes("Cash")) {
			document.getElementById('transactionDetails').hidden = true;
		} else {
			document.getElementById('transactionDetails').hidden = false;
		}
	}
	
</script>
</head>
<body onload="checkActive()">
	<!-- Preloader -->
	<div class="preloader-it">
		<div class="la-anim-1"></div>
	</div>
	<!-- /Preloader -->
	<div class="wrapper theme-1-active pimary-color-red">
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
					<h5 class="txt-dark">Add Income</h5>
				</div>
				<div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
					<ol class="breadcrumb">
						<li><a href="<%= request.getContextPath() %>/view/pages/home.jsp">Main</a></li>
						<li class="active"><span>Add Income</span></li>
					</ol>
				</div>

				<!-- Row -->
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-default card-view">
							<div class="panel-heading">
								<div class="pull-left">
									<h6 class="panel-title txt-dark">Add your income
										transaction</h6>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="panel-wrapper collapse in">
								<div class="panel-body">
									<div class="form-wrap">
										<form name="frmIncome" id="frmIncome" method="post" action="<%= request.getContextPath() %>/TransactionMasterController">
											<input type="hidden" name="flag" value="addIncomeTransaction">
											<div class="row row-lg">
												<div class="col-md-4">
													<div class="form-group">
														<label class="control-label mb-10" for="payeeName">Payee
															Name</label>
														<div class="input-group">
															<div class="input-group-addon">
																<i class="icon-user"></i>
															</div>
															<input type="text" autocomplete="off" required=""
																class="form-control" id="payeeName" name="payeeName"
																placeholder="Enter Payee Name">
														</div>
													</div>
												</div>
												<div class="col-md-4">
													<div class="form-group">
														<label class="control-label mb-10" for="transactionAmount">Amount</label>
														<div class="input-group">
															<div class="input-group-addon">
																<i class="fa fa-rupee"></i>
															</div>
															<input type="number" step="any" autocomplete="off" required=""
																class="form-control" id="transactionAmount" name="transactionAmount"
																placeholder="Enter Amount">
														</div>
													</div>
												</div>
												<div class="col-md-4">
													<div class="form-group">
														<label class="control-label mb-10 text-left"
															for="datetimepicker1">Select Date and Time:-</label>
														<div class="input-group date" id="datetimepicker1">
															<input type="text" required="" name="transactionDate" autocomplete="off"
																placeholder="Select Date from calendar icon and Time from watch icon"
																class="form-control"> <span
																class="input-group-addon"> <span
																class="fa fa-calendar"></span>
															</span>
														</div>
													</div>
												</div>
												<div class="col-md-6">
													<div class="form-group">
														<div class="col-md-6">
															<label class="control-label mb-10" for="categoryName">Enter/Select
																Category Name</label> <input type="text" autocomplete="off"
																class="form-control" id="categoryName"
																name="categoryName" value=""
																placeholder="Enter Category Name if option not found">
														</div>
														<div class="col-md-6">
															<label class="control-label mb-10" for="categorySelect">&nbsp;</label>
															<select
																class="form-control select2 select2-hidden-accessible"
																tabindex="-1" aria-hidden="true" name="categorySelect"
																id="categorySelect" onchange="fillSubCategoryDropdown()">
																<option value="null">Select</option>
																<c:forEach var="incomeData"
																	items="${ sessionScope.incomeList }">
																	<option value="${ incomeData.categoryName }">${ incomeData.categoryName }</option>
																</c:forEach>
															</select>
														</div>
													</div>
												</div>
												<div class="col-md-6">
													<div class="form-group">
														<div class="col-md-6">
															<label class="control-label mb-10" for="subCategoryName">Enter/Select
																Sub Category Name</label> <input type="text" autocomplete="off"
																class="form-control" id="subCategoryName"
																name="subCategoryName" value=""
																placeholder="Enter Category Name if option not found">
														</div>
														<div class="col-md-6">
															<label class="control-label mb-10"
																for="subCategorySelect">&nbsp;</label> <select
																class="form-control select2 select2-hidden-accessible"
																tabindex="-1" aria-hidden="true"
																name="subCategorySelect" id="subCategorySelect">
																<option value="null">Select</option>
															</select>
														</div>
													</div>
												</div>
												<div class="col-md-12">
													<div class="row row-md">&nbsp;</div>
												</div>
												<div class="col-md-4">
													<div class="form-group">
														<label class="control-label mb-10" for="paymentMethod">Select
															Payment Method</label> <select
															class="form-control select2 select2-hidden-accessible"
															tabindex="-1" aria-hidden="true" name="paymentMethod"
															id="paymentMethod" onchange="checkMethod()">
															<option value="Cash">Cash</option>
															<option value="Cheque">Cheque</option>
															<option value="Credit Card">Credit Card</option>
															<option value="Debit Card">Debit Card</option>
															<option value="Net Banking">Net Banking</option>
														</select>
													</div>
												</div>
												<div class="col-md-4">
													<div class="form-group">
														<label class="control-label mb-10" for="paymentStatus">Select
															Status of transaction</label> <select
															class="form-control select2 select2-hidden-accessible"
															tabindex="-1" aria-hidden="true" name="paymentStatus"
															id="paymentStatus">
															<option value="Cleared">Cleared</option>
															<option value="Uncleared">Uncleared</option>
														</select>
													</div>
												</div>
												<div class="col-md-4" id="transactionDetails" hidden="">
													<div class="form-group">
														<label class="control-label mb-10" id="transactionLabel"
															for="transactionNumber">Transaction/Cheque Number</label>
														<input type="text" autocomplete="off" class="form-control"
															id="transactionNumber" name="transactionNumber"
															placeholder="Enter Transaction/Cheque No">
													</div>
												</div>
												<div class="col-md-12">
													<div class="form-group">
														<label class="control-label mb-10"
															for="paymentDescription">Extra Description/Notes</label>
														<textarea id="paymentDescription"
															name="paymentDescription" class="form-control" rows="3"></textarea>
													</div>
												</div>
												<div class="col-md-9">&nbsp;</div>
												<div class="col-md-3">
													<div class="col-md-6">
														<input type="submit" class="btn  btn-success btn-rounded"
															value="Save">
													</div>
													<div class="col-md-6">
														<input type="reset" class="btn btn-danger btn-rounded"
															value="Reset">
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

	<!-- Moment JavaScript -->
	<script type="text/javascript"
		src="../../vendors/bower_components/moment/min/moment-with-locales.min.js"></script>

	<!-- Switchery JavaScript -->
	<script src="../../vendors/bower_components/switchery/dist/switchery.min.js"></script>

	<!-- Select2 JavaScript -->
	<script
		src="../../vendors/bower_components/select2/dist/js/select2.full.min.js"></script>

	<!-- Bootstrap Select JavaScript -->
	<script
		src="../../vendors/bower_components/bootstrap-select/dist/js/bootstrap-select.min.js"></script>

	<!-- Bootstrap Datetimepicker JavaScript -->
	<script type="text/javascript"
		src="../../vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>

	<!-- Form Advance Init JavaScript -->
	<script src="../../dist/js/form-advance-data.js"></script>

	<!-- Slimscroll JavaScript -->
	<script src="../../dist/js/jquery.slimscroll.js"></script>

	<!-- Fancy Dropdown JS -->
	<script src="../../dist/js/dropdown-bootstrap-extended.js"></script>

	<!-- Owl JavaScript -->
	<script
		src="../../vendors/bower_components/owl.carousel/dist/owl.carousel.min.js"></script>

	<!-- Bootstrap Datetimepicker JavaScript -->
	<script type="text/javascript"
		src="../../vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>

	<!-- Form Picker Init JavaScript -->
	<script src="../../dist/js/form-picker-data.js"></script>

	<!-- Bootstrap Touchspin JavaScript -->
	<script
		src="../../vendors/bower_components/bootstrap-touchspin/dist/jquery.bootstrap-touchspin.min.js"></script>

	<!-- Multiselect JavaScript -->
	<script
		src="../../vendors/bower_components/multiselect/js/jquery.multi-select.js"></script>

	<!-- Bootstrap Switch JavaScript -->
	<script
		src="../../vendors/bower_components/bootstrap-switch/dist/js/bootstrap-switch.min.js"></script>

	<!-- Bootstrap Colorpicker JavaScript -->
	<script
		src="../../vendors/bower_components/mjolnic-bootstrap-colorpicker/dist/js/bootstrap-colorpicker.min.js"></script>

	<!-- Init JavaScript -->
	<script src="../../dist/js/init.js"></script>
</body>
</html>