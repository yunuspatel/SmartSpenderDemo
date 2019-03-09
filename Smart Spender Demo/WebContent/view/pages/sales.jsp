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
	} else if (userVo.isStockPermission() == false) {
		response.sendRedirect(request.getContextPath() + "/view/pages/home.jsp");
	}
%>
<head>
<meta charset="UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>Sales</title>

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
		var element = document.getElementById('page-sales');
		element.classList.add("active");
		<%Object object = session.getAttribute("userMsg");
			if (object != null) {%>
				alert("<%=object%>");
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

	function upload(file) {
		var imgVal = document.getElementById('frmReceipt' + file.id);
		imgVal.submit();
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
						<h5 class="txt-dark">Sales</h5>
					</div>
					<!-- Breadcrumb -->
					<div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
						<ol class="breadcrumb">
							<li><a
								href="<%=request.getContextPath()%>/UserMasterController?flag=loadDashboard">Dashboard</a></li>
							<li class="active"><span>Sales</span></li>
						</ol>
					</div>
					<!-- /Breadcrumb -->
				</div>
				<!-- /Title -->

				<!-- Sales Modal Start -->
				<div id="responsive-modal" class="modal fade" tabindex="-1"
					role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
					style="display: none;">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">×</button>
								<h5 class="modal-title">Add Sales</h5>
							</div>
							<form method="post"
								action="<%=request.getContextPath()%>/SalesController">
								<input type="hidden" name="flag" value="addSales">
								<div class="modal-body">
									<div class="row row-lg">
										<div class="col-md-12">
											<div class="form-group">
												<label for="customerName" class="control-label mb-10">Customer
													Name:-</label> <input type="text" class="form-control"
													id="customerName" name="customerName" required=""
													placeholder="Enter Customer Name" autocomplete="off">
											</div>
										</div>
										<div class="col-md-12">
											<div class="col-md-6">
												<label for="productName" class="control-label mb-10">Select
													Product:-</label><select class="form-control" id="productName"
													name="productName" required="">
													<c:forEach var="product"
														items="${ sessionScope.productList }">
														<option value="${ product.productId }">${ product.productName }</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-md-6">
												<label for="salesPrice" class="control-label mb-10">Sales
													Price:-</label><input type="number" step="any" class="form-control"
													id="salesPrice" name="salesPrice" required=""
													placeholder="Enter Sales Price" autocomplete="off">
											</div>
										</div>
										<div class="col-md-12">
											<div class="col-md-6">
												<label for="salesQuantity" class="control-label mb-10">Sales
													Quantity:-</label><input type="number" class="form-control"
													id="salesQuantity" name="salesQuantity" required=""
													placeholder="Enter Sales Quantity" autocomplete="off">
											</div>
											<div class="col-md-6">
												<label for="discountPrice" class="control-label mb-10">Discount
													Price:-</label><input type="number" class="form-control"
													id="discountPrice" name="discountPrice"
													placeholder="Enter Discount Price. Enter if provided only."
													autocomplete="off">
											</div>
										</div>
										<div class="col-md-12">&nbsp;</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Close</button>
									<input type="submit" class="btn btn-danger" value="Add Sales">
								</div>
							</form>
						</div>
					</div>
				</div>
				<div id="responsive-edit-modal" class="modal fade" tabindex="-1"
					role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
					style="display: none;">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">×</button>
								<h5 class="modal-title">Edit Sales</h5>
							</div>
							<form method="post"
								action="<%=request.getContextPath()%>/SalesController">
								<input type="hidden" name="flag" value="editSales"> <input
									type="hidden" id="editSalesId" name="salesId" value="">
								<div class="modal-body">
									<div class="row row-lg">
										<div class="col-md-12">
											<div class="form-group">
												<label for="editCustomerName" class="control-label mb-10">Customer
													Name:-</label> <input type="text" class="form-control"
													id="editCustomerName" name="customerName" required=""
													placeholder="Enter Customer Name" autocomplete="off">
											</div>
										</div>
										<div class="col-md-12">
											<div class="col-md-6">
												<label for="editProductName" class="control-label mb-10">Select
													Product:-</label><select class="form-control" id="editProductName"
													name="productName" required="">
													<c:forEach var="product"
														items="${ sessionScope.productList }">
														<option value="${ product.productId }">${ product.productName }</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-md-6">
												<label for="editSalesPrice" class="control-label mb-10">Sales
													Price:-</label><input type="number" step="any" class="form-control"
													id="editSalesPrice" name="salesPrice" required=""
													placeholder="Enter Sales Price" autocomplete="off">
											</div>
										</div>
										<div class="col-md-12">
											<div class="col-md-6">
												<label for="editSalesQuantity" class="control-label mb-10">Sales
													Quantity:-</label><input type="number" class="form-control"
													id="editSalesQuantity" name="salesQuantity" required=""
													placeholder="Enter Sales Quantity" autocomplete="off">
											</div>
											<div class="col-md-6">
												<label for="editDiscountPrice" class="control-label mb-10">Discount
													Price:-</label><input type="number" class="form-control"
													id="editDiscountPrice" name="discountPrice"
													placeholder="Enter Discount Price. Enter if provided only."
													autocomplete="off">
											</div>
										</div>
										<div class="col-md-12">&nbsp;</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Close</button>
									<input type="submit" class="btn btn-danger" value="Edit Sales">
								</div>
							</form>
						</div>
					</div>
				</div>
				<!-- Sales Modal End -->

				<!-- Row -->
				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-default card-view">
							<div class="panel-heading">
								<div class="pull-left">
									<h6 class="panel-title txt-dark">Sales</h6>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="panel-wrapper collapse in">
								<div class="panel-body">
									<div class="row row-lg">
										<div class="col-md-5">
											<div class="col-md-1"></div>
											<div class="col-md-11">
												<input type="button" value="Add Sales" name="salesModal"
													id="salesModal" data-toggle="modal"
													data-target="#responsive-modal"
													class="btn  btn-primary btn-rounded">
											</div>
										</div>
									</div>
									<div class="row row-lg">
										<div class="col-md-10">
											<b>Note:- Click on Sales Identification Number to view
												sales receipt image if uploaded.</b>
										</div>
									</div>
									<div class="table-wrap">
										<div class="">
											<table id="myTable1" class="table table-hover display  pb-30">
												<thead>
													<tr>
														<th>Sales Identification Number</th>
														<th>Customer Name</th>
														<th>Product Name</th>
														<th>Sales Price</th>
														<th>Quantity</th>
														<th>Discount Price</th>
														<th>Purchase Date & Time</th>
														<th>Upload Receipt Image</th>
														<th>Actions</th>
													</tr>
												</thead>
												<tfoot>
													<tr>
														<th>Sales Identification Number</th>
														<th>Customer Name</th>
														<th>Product Name</th>
														<th>Sales Price</th>
														<th>Quantity</th>
														<th>Discount Price</th>
														<th>Purchase Date & Time</th>
														<th>Upload Bill Image</th>
														<th>Actions</th>
													</tr>
												</tfoot>
												<tbody>
													<c:forEach var="sales" items="${ sessionScope.salesList }">
														<tr>
															<td><div
																	id="responsive-image-modal${ sales.salesId }"
																	class="modal fade" tabindex="-1" role="dialog"
																	aria-labelledby="myModalLabel" aria-hidden="true"
																	style="display: none;">
																	<div class="modal-dialog">
																		<div class="modal-content">
																			<div class="modal-header">
																				<button type="button" class="close"
																					data-dismiss="modal" aria-hidden="true">×</button>
																				<h5 class="modal-title">Sales Bill Image</h5>
																			</div>
																			<div class="modal-body">
																				<div class="form-group">
																					<div class="row row-md">
																						<div class="col-md-12">
																							<label for="userMobile"
																								class="control-label mb-10">To change
																								bill image goto sales list and upload a new bill
																								image</label>
																						</div>
																					</div>
																					<div class="row row-md">
																						<div class="col-md-10">
																							<center>
																								<img alt="Sales Bill Image Not Found"
																									src="${ sales.salesReceiptImage }"
																									width="200px" height="200px">
																							</center>
																						</div>
																					</div>
																				</div>

																			</div>
																			<div class="modal-footer"></div>
																		</div>
																	</div>
																</div> <a href="#" class="my-receipt-image-modal"
																style="color: blue;" id="${ sales.salesId }">${ sales.salesIdentificationNumber }</a></td>
															<td>${ sales.customerName }<input type="hidden"
																id="cname${ sales.salesId }"
																value="${ sales.customerName }"></td>
															<td>${ sales.productVo.productName }<input
																type="hidden" id="pname${ sales.salesId }"
																value="${ sales.productVo.productId }"></td>
															<td>${ sales.salesPrice }<input type="hidden"
																id="sprice${ sales.salesId }"
																value="${ sales.salesPrice }"></td>
															<td>${ sales.salesQuantity }<input type="hidden"
																id="squantity${ sales.salesId }"
																value="${ sales.salesQuantity }"></td>
															<td>${ sales.discountPrice }<input type="hidden"
																id="sdiscount${ sales.salesId }"
																value="${ sales.discountPrice }"></td>
															<td>${ sales.salesDateTime }</td>
															<td><form name="frmReceipt"
																	id="frmReceipt${ sales.salesId }" method="post"
																	enctype="multipart/form-data"
																	action="<%=request.getContextPath()%>/SalesController?flag=uploadReceiptImage&id=${ sales.salesId }">
																	<input id="${ sales.salesId }"
																		name="receiptImage${ sales.salesId }" class="upload"
																		type="file" onchange="upload(this)">
																</form></td>
															<td><a href="#" id="${ sales.salesId }"
																class="my-edit-class-sales"><i class="fa fa-edit"
																	aria-hidden="true"></i></a>&nbsp;<a
																href="<%= request.getContextPath() %>/SalesController?flag=deleteSales&value=${ sales.salesId }"><i
																	class="ti-trash" aria-hidden="true"></i></a></td>
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
				<script type="text/javascript">
					$(".my-edit-class-sales").on('click', function() {
						var id = $(this).attr("id");
						var cname = $('#cname' + id).val();
						var pname = $('#pname' + id).val();
						var sprice = $('#sprice' + id).val();
						var squantity = $('#squantity' + id).val();
						var sdiscount = $('#sdiscount' + id).val();
						$('#editSalesId').val(id);
						$('#editCustomerName').val(cname);
						$('#editProductName').val(pname);
						$('#editSalesPrice').val(sprice);
						$('#editSalesQuantity').val(squantity);
						$('#editDiscountPrice').val(sdiscount);
						$('#responsive-edit-modal').modal('show');
					});

					$(".my-receipt-image-modal").on('click', function() {
						var id = $(this).attr("id");
						$('#responsive-image-modal' + id).modal('show');
					});
				</script>
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