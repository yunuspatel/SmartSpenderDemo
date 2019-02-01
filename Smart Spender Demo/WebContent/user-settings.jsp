<%@page import="vo.UserVo"%>
<%@page import="dao.UserMasterDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<%
	UserVo userVo = (UserVo) session.getAttribute("user");
	if (userVo == null) {
		response.sendRedirect("login.jsp");
	}
	String userMale = "", userFemale = "";
	if (userVo.getUserGender().equals("Male")) {
		userMale = "checked";
	} else if (userVo.getUserGender().equals("Female")) {
		userFemale = "checked";
	}
%>
<head>
<meta charset="UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>User Settings</title>

<!-- Favicon -->
<link rel="shortcut icon" href="img/logo2.png">
<link rel="icon" href="img/logo2.png" type="image/x-icon">

<!-- Morris Charts CSS -->
<link href="vendors/bower_components/morris.js/morris.css"
	rel="stylesheet" type="text/css" />

<!-- vector map CSS -->
<link href="vendors/vectormap/jquery-jvectormap-2.0.2.css"
	rel="stylesheet" type="text/css" />

<!-- Calendar CSS -->
<link href="vendors/bower_components/fullcalendar/dist/fullcalendar.css"
	rel="stylesheet" type="text/css" />

<!-- Data table CSS -->
<link
	href="vendors/bower_components/datatables/media/css/jquery.dataTables.min.css"
	rel="stylesheet" type="text/css" />

<!-- Custom CSS -->
<link href="dist/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function upload() {
		var imgVal = document.getElementById('frmImg');
		imgVal.submit();
	}
	
	function checkMessage()
	{
		<%Object object = session.getAttribute("userMsg");
			Object imageObject = session.getAttribute("imageChanged");
			if (object != null) {%>
				alert("<%=object.toString()%>");
			<%session.removeAttribute("userMsg");
			}
			if (imageObject != null) {%>
					location.realod(true);
				<%session.removeAttribute("imageChanged");
			}%>
	}
	
	function checkPassword()
	{
		var userPassword = document.getElementById('userPassword').value;
		if(${ sessionScope.user.userPassword} == userPassword)
		{
			return true;
		}
		alert("Wrong Password");
		return false;
	}
	
	function confirmDeleteAction()
	{
		var userValue = confirm("Are you sure you want to delete your account?");
		if(userValue == true)
		{
			return true;
		}
		return false;
	}
</script>
</head>
<body onload="checkMessage()">
	<!--Preloader-->
	<div class="preloader-it">
		<div class="la-anim-1"></div>
	</div>
	<!--/Preloader-->
	<div class="wrapper theme-1-active pimary-color-red">

		<!-- Top Menu Items -->
		<jsp:include page="top-menu.jsp"></jsp:include>
		<!-- /Top Menu Items -->

		<!-- Left Sidebar Menu -->
		<jsp:include page="left-sidebar-menu.jsp"></jsp:include>
		<!-- /Left Sidebar Menu -->

		<!-- Right Sidebar Menu -->
		<jsp:include page="right-sidebar-menu.jsp"></jsp:include>
		<!-- /Right Sidebar Menu -->

		<!-- Right Setting Menu -->
		<jsp:include page="floating-setting-menu.jsp"></jsp:include>
		<!-- /Right Setting Menu -->

		<!-- Right Sidebar Backdrop -->
		<div class="right-sidebar-backdrop"></div>
		<!-- /Right Sidebar Backdrop -->

		<!-- Main Content -->
		<div class="page-wrapper">
			<div class="container-fluid pt-25">

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
												<img class="inline-block mb-10"
													src="${ sessionScope.user.userImage }" alt="user" />
												<div class="fileupload btn btn-default">
													<span class="btn-text">edit</span>
													<form name="frmImg" id="frmImg" method="post"
														enctype="multipart/form-data"
														action="<%=request.getContextPath()%>/UserMasterController?flag=uploadProfileImage">
														<input id="profileImage" name="profileImage"
															class="upload" type="file" onchange="upload()">
													</form>
												</div>
											</div>
											<h5
												class="block mt-10 mb-5 weight-500 capitalize-font txt-danger">${ sessionScope.user.userName }</h5>
											<h6 class="block capitalize-font pb-20">${ sessionScope.user.userCity }</h6>
										</div>
										<div>
											<button
												class="btn btn-default btn-block btn-outline btn-anim mt-30"
												data-toggle="modal" data-target="#myModal">
												<i class="fa fa-pencil"></i><span class="btn-text">Edit
													Profile</span>
											</button>
											<div id="myModal" class="modal fade in" tabindex="-1"
												role="dialog" aria-labelledby="myModalLabel"
												aria-hidden="true">
												<div class="modal-dialog">
													<div class="modal-content">
														<div class="modal-header">
															<button type="button" class="close" data-dismiss="modal"
																aria-hidden="true">x</button>
															<h5 class="modal-title" id="myModalLabel">Edit
																Profile</h5>
														</div>
														<div class="modal-body">
															<!-- Row -->
															<div class="row">
																<div class="col-lg-12">
																	<div class="">
																		<div class="panel-wrapper collapse in">
																			<div class="panel-body pa-0">
																				<div class="col-sm-12 col-xs-12">
																					<div class="form-wrap">
																						<form method="post" name="frmUpdate"
																							onsubmit="return checkPassword()"
																							action="<%=request.getContextPath()%>/UserMasterController">
																							<input type="hidden" name="flag"
																								value="userUpdate">
																							<div class="form-body overflow-hide">
																								<div class="form-group">
																									<label class="control-label mb-10"
																										for="userName">Name</label>
																									<div class="input-group">
																										<div class="input-group-addon">
																											<i class="icon-user"></i>
																										</div>
																										<input type="text" autocomplete="off"
																											required="" class="form-control"
																											id="userName" name="userName"
																											value="${ sessionScope.user.userName }"
																											placeholder="User Name">
																									</div>
																								</div>
																								<div class="form-group">
																									<label class="control-label mb-10"
																										for="userEmail">Email address</label>
																									<div class="input-group">
																										<div class="input-group-addon">
																											<i class="icon-envelope-open"></i>
																										</div>
																										<input type="email" autocomplete="off"
																											required="" class="form-control"
																											id="userEmail" name="userEmail"
																											value="${ sessionScope.user.userEmail }"
																											placeholder="xyz@gmail.com">
																									</div>
																								</div>
																								<div class="form-group">
																									<label class="control-label mb-10"
																										for="userMobile">Contact number</label>
																									<div class="input-group">
																										<div class="input-group-addon">
																											<i class="icon-phone"></i>
																										</div>
																										<input type="number" autocomplete="off"
																											required="" class="form-control"
																											id="userMobile" name="userMobile"
																											value="${ sessionScope.user.userMobile }"
																											placeholder="+102 9388333">
																									</div>
																								</div>
																								<div class="form-group">
																									<label class="control-label mb-10">Gender</label>
																									<div>
																										<div class="radio">
																											<input type="radio" name="userGender"
																												id="userMale" value="Male" <%=userMale%>>
																											<label for="userMale"> Male </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
																												type="radio" name="userGender"
																												id="userFemale" value="Female"
																												<%=userFemale%>><label
																												for="userFemale"> Female </label>
																										</div>
																									</div>
																								</div>
																								<div class="form-group">
																									<label class="control-label mb-10"
																										for="userCity">City</label>
																									<div class="input-group">
																										<div class="input-group-addon">
																											<i class="ti-direction"></i>
																										</div>
																										<input type="text" required=""
																											autocomplete="off" class="form-control"
																											id="userCity" name="userCity"
																											value="${ sessionScope.user.userCity }"
																											placeholder="Enter Current City">
																									</div>
																								</div>
																								<div class="form-group">
																									<label class="control-label mb-10"
																										for="userPincode">Pincode</label>
																									<div class="input-group">
																										<div class="input-group-addon">
																											<i class="ti-target"></i>
																										</div>
																										<input type="text" required=""
																											autocomplete="off" class="form-control"
																											id="userPincode" name="userPincode"
																											value="${ sessionScope.user.userPinCode }"
																											placeholder="Enter PinCode">
																									</div>
																								</div>
																								<div class="form-group">
																									<label class="control-label mb-10"
																										for="userDob">Date of Birth</label>
																									<div class="input-group">
																										<div class="input-group-addon">
																											<i class="ti-gift"></i>
																										</div>
																										<input type="date" required=""
																											autocomplete="off" class="form-control"
																											id="userDob" name="userDob"
																											value="${ sessionScope.user.userDob }"
																											placeholder="Enter Date of Birth">
																									</div>
																								</div>
																								<div class="form-group">
																									<label class="control-label mb-10"
																										for="userPassword">Enter Password</label>
																									<div class="input-group">
																										<div class="input-group-addon">
																											<i class="icon-lock"></i>
																										</div>
																										<input type="password" required=""
																											autocomplete="off" class="form-control"
																											id="userPassword" name="userPassword"
																											placeholder="Enter Current Password to Confirm Changes">
																									</div>
																								</div>
																							</div>
																							<div class="form-actions mt-10">
																								<button type="submit"
																									class="btn btn-success mr-10 mb-30">Update
																									profile</button>
																							</div>
																						</form>
																					</div>
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
													<!-- /.modal-content -->
												</div>
												<!-- /.modal-dialog -->
											</div>
										</div>
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
											<li role="presentation" class="next"><a
												aria-expanded="true" data-toggle="tab" role="tab"
												id="follo_tab_8" href="#follo_8"><span>Change
														Password</span></a></li>
											<li role="presentation" class=""><a data-toggle="tab"
												id="photos_tab_8" role="tab" href="#photos_8"
												aria-expanded="false"><span>Data Export</span></a></li>
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
																		value="${ sessionScope.user.userName }"
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
																		value="${ sessionScope.user.userEmail }"
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
																		value="${ sessionScope.user.userMobile }"
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
																		value="${ sessionScope.user.userGender }"
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
																		value="${ sessionScope.user.userCity }"
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
																		value="${ sessionScope.user.userPinCode }"
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
																		value="${ sessionScope.user.userDob }"
																		placeholder="Enter Date of Birth from Edit Profile">
																</div>
															</div>
														</div>
													</div>
													<div class="col-md-5">
														<div class="pt-20">
															<div class="form-group">
																<label class="control-label mb-10" for="userCreationDate">Account Created On</label>
																<div class="input-group">
																	<div class="input-group-addon">
																		<i class="ti-calendar"></i>
																	</div>
																	<input type="text" readonly="true" autocomplete="off"
																		class="form-control" id="userCreationDate" name="userCreationDate"
																		value="${ sessionScope.user.userCreationDate }"
																		placeholder="Enter Date of Birth from Edit Profile">
																</div>
															</div>
														</div>
													</div>
												</div>
												<div class="row row-lg">&nbsp;</div>
												<div class="row row-lg">
													<div class="col-md-5"></div>
													<div class="col=md-7">
													<form name="frmDelete" method="post" onsubmit="return confirmDeleteAction()" action="<%= request.getContextPath() %>/UserMasterController">
														<input type="hidden" name="flag" value="deleteUser">
														<input type="hidden" name="userId" value="${ sessionScope.user.userId }">
														<input type="submit" name="userDelete" class="btn btn-danger btn-rounded" value="Delete Account">
													</form>
													</div>
												</div>
												<div class="row row-lg">&nbsp;</div>
											</div>

											<div id="follo_8" class="tab-pane fade" role="tabpanel">
												<div class="row">
													<div class="col-lg-12">
														<div class="followers-wrap">
															<ul class="followers-list-wrap">
																<li class="follow-list">
																	<div class="follo-body">
																		<div class="follo-data">
																			<img class="user-img img-circle" src="img/user.png"
																				alt="user" />
																			<div class="user-data">
																				<span class="name block capitalize-font">Clay
																					Masse</span> <span class="time block truncate txt-grey">No
																					one saves us but ourselves.</span>
																			</div>
																			<button
																				class="btn btn-success pull-right btn-xs fixed-btn">Follow</button>
																			<div class="clearfix"></div>
																		</div>
																		<div class="follo-data">
																			<img class="user-img img-circle" src="img/user1.png"
																				alt="user" />
																			<div class="user-data">
																				<span class="name block capitalize-font">Evie
																					Ono</span> <span class="time block truncate txt-grey">Unity
																					is strength</span>
																			</div>
																			<button
																				class="btn btn-success btn-outline pull-right btn-xs fixed-btn">following</button>
																			<div class="clearfix"></div>
																		</div>
																		<div class="follo-data">
																			<img class="user-img img-circle" src="img/user2.png"
																				alt="user" />
																			<div class="user-data">
																				<span class="name block capitalize-font">Madalyn
																					Rascon</span> <span class="time block truncate txt-grey">Respect
																					yourself if you would have others respect you.</span>
																			</div>
																			<button
																				class="btn btn-success btn-outline pull-right btn-xs fixed-btn">following</button>
																			<div class="clearfix"></div>
																		</div>
																		<div class="follo-data">
																			<img class="user-img img-circle" src="img/user3.png"
																				alt="user" />
																			<div class="user-data">
																				<span class="name block capitalize-font">Mitsuko
																					Heid</span> <span class="time block truncate txt-grey">I’m
																					thankful.</span>
																			</div>
																			<button
																				class="btn btn-success pull-right btn-xs fixed-btn">Follow</button>
																			<div class="clearfix"></div>
																		</div>
																		<div class="follo-data">
																			<img class="user-img img-circle" src="img/user.png"
																				alt="user" />
																			<div class="user-data">
																				<span class="name block capitalize-font">Ezequiel
																					Merideth</span> <span class="time block truncate txt-grey">Patience
																					is bitter.</span>
																			</div>
																			<button
																				class="btn btn-success pull-right btn-xs fixed-btn">Follow</button>
																			<div class="clearfix"></div>
																		</div>
																		<div class="follo-data">
																			<img class="user-img img-circle" src="img/user1.png"
																				alt="user" />
																			<div class="user-data">
																				<span class="name block capitalize-font">Jonnie
																					Metoyer</span> <span class="time block truncate txt-grey">Genius
																					is eternal patience.</span>
																			</div>
																			<button
																				class="btn btn-success btn-outline pull-right btn-xs fixed-btn">following</button>
																			<div class="clearfix"></div>
																		</div>
																	</div>
																</li>
															</ul>
														</div>
													</div>
												</div>
											</div>
											<div id="photos_8" class="tab-pane fade" role="tabpanel">
												<div class="col-md-12 pb-20">
													<div class="gallery-wrap">
														<div class="portfolio-wrap project-gallery">
															<ul id="portfolio_1"
																class="portf auto-construct  project-gallery"
																data-col="4">
																<li class="item"
																	data-src="img/gallery/equal-size/mock1.jpg"
																	data-sub-html="<h6>Bagwati</h6><p>Classic view from Rigwood Jetty on Coniston Water an old archive shot similar to an old post but a little later on.</p>">
																	<a href="#"> <img class="img-responsive"
																		src="img/gallery/equal-size/mock1.jpg"
																		alt="Image description" /> <span class="hover-cap">Bagwati</span>
																</a>
																</li>
																<li class="item"
																	data-src="img/gallery/equal-size/mock2.jpg"
																	data-sub-html="<h6>Not a Keyboard</h6><p>Classic view from Rigwood Jetty on Coniston Water an old archive shot similar to an old post but a little later on.</p>">
																	<a href="#"> <img class="img-responsive"
																		src="img/gallery/equal-size/mock2.jpg"
																		alt="Image description" /> <span class="hover-cap">Not
																			a Keyboard</span>
																</a>
																</li>
																<li class="item"
																	data-src="img/gallery/equal-size/mock3.jpg"
																	data-sub-html="<h6>Into the Woods</h6><p>Classic view from Rigwood Jetty on Coniston Water an old archive shot similar to an old post but a little later on.</p>">
																	<a href="#"> <img class="img-responsive"
																		src="img/gallery/equal-size/mock3.jpg"
																		alt="Image description" /> <span class="hover-cap">Into
																			the Woods</span>
																</a>
																</li>
																<li class="item"
																	data-src="img/gallery/equal-size/mock4.jpg"
																	data-sub-html="<h6>Ultra Saffire</h6><p>Classic view from Rigwood Jetty on Coniston Water an old archive shot similar to an old post but a little later on.</p>">
																	<a href="#"> <img class="img-responsive"
																		src="img/gallery/equal-size/mock4.jpg"
																		alt="Image description" /> <span class="hover-cap">
																			Ultra Saffire</span>
																</a>
																</li>

																<li class="item"
																	data-src="img/gallery/equal-size/mock5.jpg"
																	data-sub-html="<h6>Happy Puppy</h6><p>Classic view from Rigwood Jetty on Coniston Water an old archive shot similar to an old post but a little later on.</p>">
																	<a href="#"> <img class="img-responsive"
																		src="img/gallery/equal-size/mock5.jpg"
																		alt="Image description" /> <span class="hover-cap">Happy
																			Puppy</span>
																</a>
																</li>
																<li class="item"
																	data-src="img/gallery/equal-size/mock6.jpg"
																	data-sub-html="<h6>Wooden Closet</h6><p>Classic view from Rigwood Jetty on Coniston Water an old archive shot similar to an old post but a little later on.</p>">
																	<a href="#"> <img class="img-responsive"
																		src="img/gallery/equal-size/mock6.jpg"
																		alt="Image description" /> <span class="hover-cap">Wooden
																			Closet</span>
																</a>
																</li>
																<li class="item"
																	data-src="img/gallery/equal-size/mock7.jpg"
																	data-sub-html="<h6>Happy Puppy</h6><p>Classic view from Rigwood Jetty on Coniston Water an old archive shot similar to an old post but a little later on.</p>">
																	<a href="#"> <img class="img-responsive"
																		src="img/gallery/equal-size/mock7.jpg"
																		alt="Image description" /> <span class="hover-cap">Happy
																			Puppy</span>
																</a>
																</li>
																<li class="item"
																	data-src="img/gallery/equal-size/mock8.jpg"
																	data-sub-html="<h6>Wooden Closet</h6><p>Classic view from Rigwood Jetty on Coniston Water an old archive shot similar to an old post but a little later on.</p>">
																	<a href="#"> <img class="img-responsive"
																		src="img/gallery/equal-size/mock8.jpg"
																		alt="Image description" /> <span class="hover-cap">Wooden
																			Closet</span>
																</a>
																</li>
															</ul>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>


					</div>
				</div>
				<!-- /Row -->

				<!-- Row -->
				<div class="row">
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
	<!-- /#wrapper -->

	<!-- JavaScript -->

	<!-- jQuery -->
	<script src="vendors/bower_components/jquery/dist/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script
		src="vendors/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Vector Maps JavaScript -->
	<script src="vendors/vectormap/jquery-jvectormap-2.0.2.min.js"></script>
	<script src="vendors/vectormap/jquery-jvectormap-world-mill-en.js"></script>
	<script src="dist/js/vectormap-data.js"></script>

	<!-- Calender JavaScripts -->
	<script src="vendors/bower_components/moment/min/moment.min.js"></script>
	<script src="vendors/jquery-ui.min.js"></script>
	<script
		src="vendors/bower_components/fullcalendar/dist/fullcalendar.min.js"></script>
	<script src="dist/js/fullcalendar-data.js"></script>

	<!-- Counter Animation JavaScript -->
	<script
		src="vendors/bower_components/waypoints/lib/jquery.waypoints.min.js"></script>
	<script
		src="vendors/bower_components/jquery.counterup/jquery.counterup.min.js"></script>

	<!-- Data table JavaScript -->
	<script
		src="vendors/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>

	<!-- Slimscroll JavaScript -->
	<script src="dist/js/jquery.slimscroll.js"></script>

	<!-- Fancy Dropdown JS -->
	<script src="dist/js/dropdown-bootstrap-extended.js"></script>

	<!-- Sparkline JavaScript -->
	<script src="vendors/jquery.sparkline/dist/jquery.sparkline.min.js"></script>

	<script
		src="vendors/bower_components/jquery.easy-pie-chart/dist/jquery.easypiechart.min.js"></script>
	<script src="dist/js/skills-counter-data.js"></script>

	<!-- Morris Charts JavaScript -->
	<script src="vendors/bower_components/raphael/raphael.min.js"></script>
	<script src="vendors/bower_components/morris.js/morris.min.js"></script>
	<script src="dist/js/morris-data.js"></script>

	<!-- Owl JavaScript -->
	<script
		src="vendors/bower_components/owl.carousel/dist/owl.carousel.min.js"></script>

	<!-- Switchery JavaScript -->
	<script src="vendors/bower_components/switchery/dist/switchery.min.js"></script>

	<!-- Data table JavaScript -->
	<script
		src="vendors/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>

	<!-- Gallery JavaScript -->
	<script src="dist/js/isotope.js"></script>
	<script src="dist/js/lightgallery-all.js"></script>
	<script src="dist/js/froogaloop2.min.js"></script>
	<script src="dist/js/gallery-data.js"></script>

	<!-- Spectragram JavaScript -->
	<script src="dist/js/spectragram.min.js"></script>

	<!-- Init JavaScript -->
	<script src="dist/js/init.js"></script>
	<script src="dist/js/widgets-data.js"></script>

</body>
</html>
