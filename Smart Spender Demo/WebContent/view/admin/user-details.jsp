<%@page import="vo.SuperUserVo"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
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
<title>User Details</title>

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

	function confirmDeleteAction() {
		var userValue = confirm("Are you sure you want to Deactivate this account?");
		if (userValue == true) {
			return true;
		}
		return false;
	}
</script>
</head>
<body>
	<!--Preloader-->
	<div class="preloader-it">
		<div class="la-anim-1"></div>
	</div>
	<!--/Preloader-->
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
			<div class="container-fluid">

				<!-- Title -->
				<div class="row heading-bg">
					<div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
						<h5 class="txt-dark">Users</h5>
					</div>
					<!-- Breadcrumb -->
					<div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
						<ol class="breadcrumb">
							<li><a
								href="<%=request.getContextPath()%>/SuperUserController?flag=loadSuperUserDashboard">Dashboard</a></li>
							<li class="active"><span>User Details</span></li>
						</ol>
					</div>
					<!-- /Breadcrumb -->
				</div>
				<!-- /Title -->

				<!-- Row -->
				<div class="row">
					<div class="col-lg-3 col-xs-12">
						<div class="panel panel-default card-view  pa-0">
							<div class="panel-wrapper collapse in">
								<div class="panel-body  pa-0">
									<div class="profile-box">
										<div class="profile-cover-pic"></div>
										<div class="profile-info text-center">
											<div class="profile-img-wrap">
												<img class="inline-block mb-10" id="userImage"
													name="userImage"
													src="${ sessionScope.userDetailsForAdmin.userImage }"
													alt="Image Not Found" />
											</div>
											<h5
												class="block mt-10 mb-5 weight-500 capitalize-font txt-danger">${ sessionScope.userDetailsForAdmin.userName }</h5>
											<h6 class="block capitalize-font pb-20">${ sessionScope.userDetailsForAdmin.userCity }</h6>
										</div>
										<div></div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-9 col-xs-12">
						<div class="panel panel-default card-view pa-0">
							<div class="panel-wrapper collapse in">
								<div class="panel-body pb-0">
									<div class="tab-struct custom-tab-1">
										<ul role="tablist" class="nav nav-tabs nav-tabs-responsive"
											id="myTabs_8">
											<li class="active" role="presentation"><a
												data-toggle="tab" id="profile_tab_8" role="tab"
												href="#profile_8" aria-expanded="false"><span>Profile</span></a></li>
										</ul>
										<div class="tab-content" id="myTabContent_8">
											<div id="profile_8" class="tab-pane fade active in"
												role="tabpanel">
												<div class="row row-lg">
													<div class="col-sm-1"></div>
													<div class="col-md-5">
														<div class="pt-20">
															<div class="form-group">
																<label class="control-label mb-10" for="userName">Name</label>
																<div class="input-group">
																	<div class="input-group-addon">
																		<i class="icon-user"></i>
																	</div>
																	<input type="text" readonly="true" autocomplete="off"
																		required="" class="form-control" id="userName"
																		name="userName"
																		value="${ sessionScope.userDetailsForAdmin.userName }"
																		placeholder="User Name">
																</div>
															</div>
														</div>
													</div>
													<div class="col-md-5">
														<div class="pt-20">
															<div class="form-group">
																<label class="control-label mb-10" for="userEmail">Email
																	address</label>
																<div class="input-group">
																	<div class="input-group-addon">
																		<i class="icon-envelope-open"></i>
																	</div>
																	<input type="email" readonly="true" autocomplete="off"
																		required="" class="form-control" id="userEmail"
																		name="userEmail"
																		value="${ sessionScope.userDetailsForAdmin.userEmail }"
																		placeholder="xyz@gmail.com">
																</div>
															</div>
														</div>
													</div>
												</div>
												<div class="row row-lg">
													<div class="col-sm-1"></div>
													<div class="col-md-5">
														<div class="pt-20">
															<div class="form-group">
																<label class="control-label mb-10" for="userMobile">Contact
																	number</label>
																<div class="input-group">
																	<div class="input-group-addon">
																		<i class="icon-phone"></i>
																	</div>
																	<input type="number" readonly="true" autocomplete="off"
																		required="" class="form-control" id="userMobile"
																		name="userMobile"
																		value="${ sessionScope.userDetailsForAdmin.userMobile }"
																		placeholder="+102 9388333">
																</div>
															</div>
														</div>
													</div>
													<div class="col-md-5">
														<div class="pt-20">
															<div class="form-group">
																<label class="control-label mb-10" for="userGender">Gender</label>
																<div class="input-group">
																	<div class="input-group-addon">
																		<i class="ti-tag"></i>
																	</div>
																	<input type="text" readonly="true" autocomplete="off"
																		required="" class="form-control" id="userGender"
																		name="userGender"
																		value="${ sessionScope.userDetailsForAdmin.userGender }"
																		placeholder="Select Gender from Edit Profile">
																</div>
															</div>
														</div>
													</div>
												</div>
												<div class="row row-lg">
													<div class="col-sm-1"></div>
													<div class="col-md-5">
														<div class="pt-20">
															<div class="form-group">
																<label class="control-label mb-10" for="userCity">City</label>
																<div class="input-group">
																	<div class="input-group-addon">
																		<i class="ti-direction"></i>
																	</div>
																	<input type="text" readonly="true" required=""
																		autocomplete="off" class="form-control" id="userCity"
																		name="userCity"
																		value="${ sessionScope.userDetailsForAdmin.userCity }"
																		placeholder="Enter Current City from Edit Profile">
																</div>
															</div>
														</div>
													</div>
													<div class="col-md-5">
														<div class="pt-20">
															<div class="form-group">
																<label class="control-label mb-10" for="userPincode">Pincode</label>
																<div class="input-group">
																	<div class="input-group-addon">
																		<i class="ti-target"></i>
																	</div>
																	<input type="text" readonly="true" required=""
																		autocomplete="off" class="form-control"
																		id="userPincode" name="userPincode"
																		value="${ sessionScope.userDetailsForAdmin.userPinCode }"
																		placeholder="Enter PinCode from Edit Profile">
																</div>
															</div>
														</div>
													</div>
												</div>
												<div class="row row-lg">
													<div class="col-sm-1"></div>
													<div class="col-md-5">
														<div class="pt-20">
															<div class="form-group">
																<label class="control-label mb-10" for="userDob">Date
																	of Birth</label>
																<div class="input-group">
																	<div class="input-group-addon">
																		<i class="ti-gift"></i>
																	</div>
																	<input type="text" readonly="true" autocomplete="off"
																		class="form-control" id="userDob" name="userDob"
																		value="${ sessionScope.userDetailsForAdmin.userDob }"
																		placeholder="Enter Date of Birth from Edit Profile">
																</div>
															</div>
														</div>
													</div>
													<div class="col-md-5">
														<div class="pt-20">
															<div class="form-group">
																<label class="control-label mb-10"
																	for="userCreationDate">Account Created On</label>
																<div class="input-group">
																	<div class="input-group-addon">
																		<i class="ti-calendar"></i>
																	</div>
																	<input type="text" readonly="true" autocomplete="off"
																		class="form-control" id="userCreationDate"
																		name="userCreationDate"
																		value="${ sessionScope.userDetailsForAdmin.userCreationDate }"
																		placeholder="Enter Date of Birth from Edit Profile">
																</div>
															</div>
														</div>
													</div>
												</div>
												<div class="row row-lg">
													<div class="col-sm-1"></div>
													<div class="col-md-5">
														<div class="pt-20">
															<div class="form-group">
																<label class="control-label mb-10" for="userActive">User
																	Active</label>
																<div class="input-group">
																	<div class="input-group-addon">
																		<i class="fa fa-check"></i>
																	</div>
																	<c:if
																		test="${ sessionScope.userDetailsForAdmin.isActive =='0' }">
																		<input type="text" readonly="true" autocomplete="off"
																			class="form-control" id="userActive"
																			name="userActive" value="false"
																			placeholder="User Acive">
																	</c:if>
																	<c:if
																		test="${ sessionScope.userDetailsForAdmin.isActive =='1' }">
																		<input type="text" readonly="true" autocomplete="off"
																			class="form-control" id="userActive"
																			name="userActive" value="true"
																			placeholder="User Acive">
																	</c:if>
																</div>
															</div>
														</div>
													</div>
													<div class="col-md-5">
														<div class="pt-20">
															<div class="form-group">
																<label class="control-label mb-10"
																	for="userStockPermission">Stock Permission</label>
																<div class="input-group">
																	<div class="input-group-addon">
																		<i class="fa fa-gears"></i>
																	</div>
																	<input type="text" readonly="true" autocomplete="off"
																		class="form-control" id="userStockPermission"
																		name="userStockPermission"
																		value="${ sessionScope.userDetailsForAdmin.stockPermission }"
																		placeholder="Stock Permission">
																</div>
															</div>
														</div>
													</div>
												</div>
												<div class="row row-lg">&nbsp;</div>
												<div class="row row-lg">
													<div class="col-md-5"></div>
													<div class="col=md-7">
														<form name="frmDelete" method="post"
															onsubmit="return confirmDeleteAction()"
															action="<%=request.getContextPath()%>/UserMasterController">
															<input type="hidden" name="flag"
																value="deactivateUserByAdmin"> <input
																type="hidden" name="userId"
																value="${ sessionScope.userDetailsForAdmin.userId }">
															<input type="submit" name="userDelete"
																class="btn btn-danger btn-rounded"
																value="Deactivate Account">
														</form>
													</div>
												</div>
												<div class="row row-lg">&nbsp;</div>
											</div>
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

			<div id="responsive-modal" class="modal fade" tabindex="-1"
				role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
				style="display: none;">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">×</button>
							<h5 class="modal-title">Profile Image</h5>
						</div>
						<div class="modal-body">
							<div class="form-group">
								<div class="row row-md">
									<div class="col-md-10">
										<center>
											<img alt="User Profile Image Not Found"
												src="${ sessionScope.userDetailsForAdmin.userImage }"
												width="200px" height="200px">
										</center>
									</div>
								</div>
							</div>

						</div>
						<div class="modal-footer"></div>
					</div>
				</div>
			</div>
		</div>
		<!-- /Main Content -->

	</div>
	<script type="text/javascript">
		$('#userImage').on('click', function() {
			$('#responsive-modal').modal('show');
		});
	</script>
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