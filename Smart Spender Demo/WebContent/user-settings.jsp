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
</head>

<body>
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
												<img class="inline-block mb-10" src="${ sessionScope.user.userImage }"
													alt="user" />
												<div class="fileupload btn btn-default">
													<span class="btn-text">edit</span> <input class="upload"
														type="file">
												</div>
											</div>
											<h5 class="block mt-10 mb-5 weight-500 capitalize-font txt-danger">${ sessionScope.user.userName }</h5>
											<h6 class="block capitalize-font pb-20">${ sessionScope.user.userCity }</h6>
										</div>
										<div>
											<button
												class="btn btn-default btn-block btn-outline btn-anim mt-30"
												data-toggle="modal" data-target="#myModal">
												<i class="fa fa-pencil"></i><span class="btn-text">edit
													profile</span>
											</button>
											<div id="myModal" class="modal fade in" tabindex="-1"
												role="dialog" aria-labelledby="myModalLabel"
												aria-hidden="true">
												<div class="modal-dialog">
													<div class="modal-content">
														<div class="modal-header">
															<button type="button" class="close" data-dismiss="modal"
																aria-hidden="true">×</button>
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
																						<form action="#">
																							<div class="form-body overflow-hide">
																								<div class="form-group">
																									<label class="control-label mb-10"
																										for="exampleInputuname_1">Name</label>
																									<div class="input-group">
																										<div class="input-group-addon">
																											<i class="icon-user"></i>
																										</div>
																										<input type="text" class="form-control"
																											id="exampleInputuname_1"
																											placeholder="willard bryant">
																									</div>
																								</div>
																								<div class="form-group">
																									<label class="control-label mb-10"
																										for="exampleInputEmail_1">Email
																										address</label>
																									<div class="input-group">
																										<div class="input-group-addon">
																											<i class="icon-envelope-open"></i>
																										</div>
																										<input type="email" class="form-control"
																											id="exampleInputEmail_1"
																											placeholder="xyz@gmail.com">
																									</div>
																								</div>
																								<div class="form-group">
																									<label class="control-label mb-10"
																										for="exampleInputContact_1">Contact
																										number</label>
																									<div class="input-group">
																										<div class="input-group-addon">
																											<i class="icon-phone"></i>
																										</div>
																										<input type="email" class="form-control"
																											id="exampleInputContact_1"
																											placeholder="+102 9388333">
																									</div>
																								</div>
																								<div class="form-group">
																									<label class="control-label mb-10"
																										for="exampleInputpwd_1">Password</label>
																									<div class="input-group">
																										<div class="input-group-addon">
																											<i class="icon-lock"></i>
																										</div>
																										<input type="password" class="form-control"
																											id="exampleInputpwd_1"
																											placeholder="Enter pwd" value="password">
																									</div>
																								</div>
																								<div class="form-group">
																									<label class="control-label mb-10">Gender</label>
																									<div>
																										<div class="radio">
																											<input type="radio" name="radio1"
																												id="radio_1" value="option1" checked="">
																											<label for="radio_1"> M </label>
																										</div>
																										<div class="radio">
																											<input type="radio" name="radio1"
																												id="radio_2" value="option2"> <label
																												for="radio_2"> F </label>
																										</div>
																									</div>
																								</div>
																								<div class="form-group">
																									<label class="control-label mb-10">Country</label>
																									<select class="form-control"
																										data-placeholder="Choose a Category"
																										tabindex="1">
																										<option value="Category 1">USA</option>
																										<option value="Category 2">Austrailia</option>
																										<option value="Category 3">India</option>
																										<option value="Category 4">UK</option>
																									</select>
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
														<div class="modal-footer">
															<button type="button"
																class="btn btn-success waves-effect"
																data-dismiss="modal">Save</button>
															<button type="button"
																class="btn btn-default waves-effect"
																data-dismiss="modal">Cancel</button>
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
												id="follo_tab_8" href="#follo_8"><span>Change Password</span></a></li>
											<li role="presentation" class=""><a data-toggle="tab"
												id="photos_tab_8" role="tab" href="#photos_8"
												aria-expanded="false"><span>Data Export</span></a></li>
										</ul>
										<div class="tab-content" id="myTabContent_8">
											<div id="profile_8" class="tab-pane fade active in"
												role="tabpanel">
												<div class="col-md-12">
													<div class="pt-20">
														<div class="streamline user-activity">
															<div class="sl-item">
																<a href="javascript:void(0)">
																	<div class="sl-avatar avatar avatar-sm avatar-circle">
																		<img class="img-responsive img-circle"
																			src="img/user.png" alt="avatar" />
																	</div>
																	<div class="sl-content">
																		<p class="inline-block">
																			<span
																				class="capitalize-font txt-success mr-5 weight-500">Clay
																				Masse</span><span>invited to join the meeting in the
																				conference room at 9.45 am</span>
																		</p>
																		<span class="block txt-grey font-12 capitalize-font">3
																			Min</span>
																	</div>
																</a>
															</div>

															<div class="sl-item">
																<a href="javascript:void(0)">
																	<div class="sl-avatar avatar avatar-sm avatar-circle">
																		<img class="img-responsive img-circle"
																			src="img/user1.png" alt="avatar" />
																	</div>
																	<div class="sl-content">
																		<p class="inline-block">
																			<span
																				class="capitalize-font txt-success mr-5 weight-500">Evie
																				Ono</span><span>added three new photos in the
																				library</span>
																		</p>
																		<div class="activity-thumbnail">
																			<img src="img/thumb-1.jpg" alt="thumbnail" /> <img
																				src="img/thumb-2.jpg" alt="thumbnail" /> <img
																				src="img/thumb-3.jpg" alt="thumbnail" />
																		</div>
																		<span class="block txt-grey font-12 capitalize-font">8
																			Min</span>
																	</div>
																</a>
															</div>

															<div class="sl-item">
																<a href="javascript:void(0)">
																	<div class="sl-avatar avatar avatar-sm avatar-circle">
																		<img class="img-responsive img-circle"
																			src="img/user2.png" alt="avatar" />
																	</div>
																	<div class="sl-content">
																		<p class="inline-block">
																			<span
																				class="capitalize-font txt-success mr-5 weight-500">madalyn
																				rascon</span><span>assigned a new task</span>
																		</p>
																		<span class="block txt-grey font-12 capitalize-font">28
																			Min</span>
																	</div>
																</a>
															</div>

															<div class="sl-item">
																<a href="javascript:void(0)">
																	<div class="sl-avatar avatar avatar-sm avatar-circle">
																		<img class="img-responsive img-circle"
																			src="img/user3.png" alt="avatar" />
																	</div>
																	<div class="sl-content">
																		<p class="inline-block">
																			<span
																				class="capitalize-font txt-success mr-5 weight-500">Ezequiel
																				Merideth</span><span>completed project wireframes</span>
																		</p>
																		<span class="block txt-grey font-12 capitalize-font">yesterday</span>
																	</div>
																</a>
															</div>

															<div class="sl-item">
																<a href="javascript:void(0)">
																	<div class="sl-avatar avatar avatar-sm avatar-circle">
																		<img class="img-responsive img-circle"
																			src="img/user4.png" alt="avatar" />
																	</div>
																	<div class="sl-content">
																		<p class="inline-block">
																			<span
																				class="capitalize-font txt-success mr-5 weight-500">jonnie
																				metoyer</span><span>created a group 'Hencework' in
																				the discussion forum</span>
																		</p>
																		<span class="block txt-grey font-12 capitalize-font">18
																			feb</span>
																	</div>
																</a>
															</div>
														</div>
													</div>
												</div>
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
