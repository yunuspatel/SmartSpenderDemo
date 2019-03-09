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
<title>Purchase</title>

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
		var element = document.getElementById('page-purchase');
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
						<h5 class="txt-dark">Purchase</h5>
					</div>
					<!-- Breadcrumb -->
					<div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
						<ol class="breadcrumb">
							<li><a
								href="<%=request.getContextPath()%>/UserMasterController?flag=loadDashboard">Dashboard</a></li>
							<li class="active"><span>Purchase</span></li>
						</ol>
					</div>
					<!-- /Breadcrumb -->
				</div>
				<!-- /Title -->

				<!-- Purchase Modal Start -->
				<div id="responsive-modal" class="modal fade" tabindex="-1"
					role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
					style="display: none;">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">×</button>
								<h5 class="modal-title">Add Purchase</h5>
							</div>
							<form method="post"
								action="<%=request.getContextPath()%>/PurchaseController">
								<input type="hidden" name="flag" value="addPurchase">
								<div class="modal-body">
									<div class="row row-lg">
										<div class="col-md-12">
											<div class="form-group">
												<label for="dealerName" class="control-label mb-10">Dealer
													Name:-</label> <input type="text" class="form-control"
													id="dealerName" name="dealerName" required=""
													placeholder="Enter Dealer Name" autocomplete="off">
											</div>
										</div>
										<div class="col-md-12">
											<div class="col-md-4">
												<label for="productName" class="control-label mb-10">Select
													Product:-</label><select class="form-control" id="productName"
													name="productName" required="">
													<c:forEach var="product"
														items="${ sessionScope.productList }">
														<option value="${ product.productId }">${ product.productName }</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-md-4">
												<label for="purchasePrice" class="control-label mb-10">Purchase
													Price:-</label><input type="number" step="any" class="form-control"
													id="purchasePrice" name="purchasePrice" required=""
													placeholder="Enter Purchase Price" autocomplete="off">
											</div>
											<div class="col-md-4">
												<label for="purchaseQuantity" class="control-label mb-10">Purchase
													Quantity:-</label><input type="number" class="form-control"
													id="purchaseQuantity" name="purchaseQuantity" required=""
													placeholder="Enter Purchase Quantity" autocomplete="off">
											</div>
										</div>
										<div class="col-md-12">&nbsp;</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Close</button>
									<input type="submit" class="btn btn-danger" value="Add Purchase">
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
								<h5 class="modal-title">Edit Purchase</h5>
							</div>
							<form method="post"
								action="<%=request.getContextPath()%>/PurchaseController">
								<input type="hidden" name="flag" value="editPurchase">
								<input type="hidden" name="purchaseId" id="editPurchaseId" value="">
								<div class="modal-body">
									<div class="row row-lg">
										<div class="col-md-12">
											<div class="form-group">
												<label for="editDealerName" class="control-label mb-10">Dealer
													Name:-</label> <input type="text" class="form-control"
													id="editDealerName" name="dealerName" required=""
													placeholder="Enter Dealer Name" autocomplete="off">
											</div>
										</div>
										<div class="col-md-12">
											<div class="col-md-4">
												<label for="editProductName" class="control-label mb-10">Select
													Product:-</label><select class="form-control" id="editProductName"
													name="productName" required="">
													<c:forEach var="product"
														items="${ sessionScope.productList }">
														<option value="${ product.productId }">${ product.productName }</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-md-4">
												<label for="editPurchasePrice" class="control-label mb-10">Purchase
													Price:-</label><input type="number" step="any" class="form-control"
													id="editPurchasePrice" name="purchasePrice" required=""
													placeholder="Enter Purchase Price" autocomplete="off">
											</div>
											<div class="col-md-4">
												<label for="editPurchaseQuantity" class="control-label mb-10">Purchase
													Quantity:-</label><input type="number" class="form-control"
													id="editPurchaseQuantity" name="purchaseQuantity" required=""
													placeholder="Enter Purchase Quantity" autocomplete="off">
											</div>
										</div>
										<div class="col-md-12">&nbsp;</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Close</button>
									<input type="submit" class="btn btn-danger" value="Edit Purchase">
								</div>
							</form>
						</div>
					</div>
				</div>
				<!-- Purchase Modal End -->

				<!-- Row -->
				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-default card-view">
							<div class="panel-heading">
								<div class="pull-left">
									<h6 class="panel-title txt-dark">Purchases</h6>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="panel-wrapper collapse in">
								<div class="panel-body">
									<div class="row row-lg">
										<div class="col-md-5">
											<div class="col-md-1"></div>
											<div class="col-md-11">
												<input type="button" value="Add Purchase"
													name="purchaseModal" id="purchaseModal" data-toggle="modal"
													data-target="#responsive-modal"
													class="btn  btn-primary btn-rounded">
											</div>
										</div>
									</div>
									<div class="row row-lg">
										<div class="col-md-10">
											<b>Note:- Click on Purchase Identification Number to view
												purchase receipt image if uploaded.</b>
										</div>
									</div>
									<div class="table-wrap">
										<div class="">
											<table id="myTable1" class="table table-hover display  pb-30">
												<thead>
													<tr>
														<th>Purchase Identification Number</th>
														<th>Dealer Name</th>
														<th>Product Name</th>
														<th>Purchase Price</th>
														<th>Quantity</th>
														<th>Purchase Date & Time</th>
														<th>Upload Receipt Image</th>
														<th>Actions</th>
													</tr>
												</thead>
												<tfoot>
													<tr>
														<th>Purchase Identification Number</th>
														<th>Dealer Name</th>
														<th>Product Name</th>
														<th>Purchase Price</th>
														<th>Quantity</th>
														<th>Purchase Date & Time</th>
														<th>Upload Receipt Image</th>
														<th>Actions</th>
													</tr>
												</tfoot>
												<tbody>
													<c:forEach var="purchase"
														items="${ sessionScope.purchaseList }">
														<tr>
															<td><div
																	id="responsive-image-modal${ purchase.purchaseId }"
																	class="modal fade" tabindex="-1" role="dialog"
																	aria-labelledby="myModalLabel" aria-hidden="true"
																	style="display: none;">
																	<div class="modal-dialog">
																		<div class="modal-content">
																			<div class="modal-header">
																				<button type="button" class="close"
																					data-dismiss="modal" aria-hidden="true">×</button>
																				<h5 class="modal-title">Purchase Receipt Image</h5>
																			</div>
																			<div class="modal-body">
																				<div class="form-group">
																					<div class="row row-md">
																						<div class="col-md-12">
																							<label for="userMobile"
																								class="control-label mb-10">To change
																								receipt image goto purchase list and upload a
																								new receipt image</label>
																						</div>
																					</div>
																					<div class="row row-md">
																						<div class="col-md-10">
																							<center>
																								<img alt="Purchase Receipt Image Not Found"
																									src="${ purchase.purchaseReceiptImage }"
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
																style="color: blue;" id="${ purchase.purchaseId }">${ purchase.purchaseIdentificationNumber }</a></td>
															<td>${ purchase.dealerName }<input type="hidden"
																id="dname${ purchase.purchaseId }"
																value="${ purchase.dealerName }"></td>
															<td>${ purchase.productVo.productName }<input
																type="hidden" id="pname${ purchase.purchaseId }"
																value="${ purchase.productVo.productId }"></td>
															<td>${ purchase.purchasePrice }<input type="hidden"
																id="pprice${ purchase.purchaseId }"
																value="${ purchase.purchasePrice }"></td>
															<td>${ purchase.quantity }<input type="hidden"
																id="pquantity${ purchase.purchaseId }"
																value="${ purchase.quantity }"></td>
															<td>${ purchase.purchaseDateTime }</td>
															<td><form name="frmReceipt"
																	id="frmReceipt${ purchase.purchaseId }" method="post"
																	enctype="multipart/form-data"
																	action="<%=request.getContextPath()%>/PurchaseController?flag=uploadReceiptImage&id=${ purchase.purchaseId }">
																	<input id="${ purchase.purchaseId }"
																		name="receiptImage${ purchase.purchaseId }"
																		class="upload" type="file" onchange="upload(this)">
																</form></td>
															<td><a href="#" id="${ purchase.purchaseId }"
																class="my-edit-class-purchase"><i class="fa fa-edit"
																	aria-hidden="true"></i></a>&nbsp;<a
																href="<%= request.getContextPath() %>/PurchaseController?flag=deletePurchase&value=${ purchase.purchaseId }"><i
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
					$(".my-edit-class-purchase").on('click', function() {
						var id = $(this).attr("id");
						var dname = $('#dname' + id).val();
						var pname = $('#pname' + id).val();
						var pprice = $('#pprice' + id).val();
						var pquantity = $('#pquantity' + id).val();
						$('#editPurchaseId').val(id);
						$('#editDealerName').val(dname);
						$('#editProductName').val(pname);
						$('#editPurchasePrice').val(pprice);
						$('#editPurchaseQuantity').val(pquantity);
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