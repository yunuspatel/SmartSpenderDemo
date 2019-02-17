<%@page import="java.util.List"%>
<%@page import="vo.NotificationVo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="mobile-only-brand pull-left">
		<div class="nav-header pull-left">
			<div class="logo-wrap">
				<a href="<%=request.getContextPath()%>/view/pages/home.jsp"> <img
					class="brand-img" src="../../img/logo1.png" alt="brand" /> <span
					class="brand-text">Smart Spender</span>
				</a>
			</div>
		</div>
		<a id="toggle_nav_btn"
			class="toggle-left-nav-btn inline-block ml-20 pull-left"
			href="javascript:void(0);"><i class="zmdi zmdi-menu"></i></a> <a
			id="toggle_mobile_search" data-toggle="collapse"
			data-target="#search_form" class="mobile-only-view"
			href="javascript:void(0);"><i class="zmdi zmdi-search"></i></a> <a
			id="toggle_mobile_nav" class="mobile-only-view"
			href="javascript:void(0);"><i class="zmdi zmdi-more"></i></a>
		<form id="search_form" role="search"
			class="top-nav-search collapse pull-left">
			<div class="input-group">
				<input type="text" name="example-input1-group2" class="form-control"
					placeholder="Search"> <span class="input-group-btn">
					<button type="button" class="btn  btn-default"
						data-target="#search_form" data-toggle="collapse"
						aria-label="Close" aria-expanded="true">
						<i class="zmdi zmdi-search"></i>
					</button>
				</span>
			</div>
		</form>
	</div>
	<div id="mobile_only_nav" class="mobile-only-nav pull-right">
		<ul class="nav navbar-right top-nav pull-right">
			<li class="dropdown alert-drp"><a href="#"
				class="dropdown-toggle" data-toggle="dropdown"><i
					class="zmdi zmdi-notifications top-nav-icon"></i><span
					class="top-nav-icon-badge"><%=session.getAttribute("notificationSize")%></span></a>
				<ul class="dropdown-menu alert-dropdown" data-dropdown-in="bounceIn"
					data-dropdown-out="bounceOut">
					<li>
						<div class="notification-box-head-wrap">
							<span class="notification-box-head pull-left inline-block">Notifications</span>
							<a class="txt-danger pull-right clear-notifications inline-block"
								href="<%=request.getContextPath()%>/NotificationController?flag=clearAll">
								clear all </a>
							<div class="clearfix"></div>
							<hr class="light-grey-hr ma-0" />
						</div>
					</li>
					<li>
						<div class="streamline message-nicescroll-bar">
							<c:forEach var="notification"
								items="${ sessionScope.notificationsList }">
								<div class="sl-item">
									<a
										href="<%= request.getContextPath() %>/NotificationController?flag=loadNotification&value=${ notification.notificationId }">
										<c:if
											test="${ notification.notificationType == 'negativeBalance' }">
											<img alt="Negative Balance"
												src="../../img/notification/negative-amount-md.png">
										</c:if> <c:if
											test="${ notification.notificationType == 'budgetAmountAlert' }">
											<img alt="Budget Alert"
												src="../../img/notification/budget-alert-md.png">
										</c:if>
										<div class="sl-content">
											<span
												class="inline-block capitalize-font  pull-left truncate head-notifications">${ notification.notificationTitle }</span>
											<span
												class="inline-block font-11  pull-right notifications-time">${ notification.notificationDateTime }</span>
											<div class="clearfix"></div>
											<p class="truncate">${ notification.notificationMessage }</p>
										</div>
									</a>
								</div>
								<hr class="light-grey-hr ma-0" />
							</c:forEach>
						</div>
					</li>
					<li>
						<div class="notification-box-bottom-wrap">
							<hr class="light-grey-hr ma-0" />
							<a class="block text-center read-all"
								href="<%=request.getContextPath()%>/NotificationController?flag=displayAll">
								Read All </a>
							<div class="clearfix"></div>
						</div>
					</li>
				</ul></li>
			<li class="dropdown auth-drp"><a href="#"
				class="dropdown-toggle pr-0" data-toggle="dropdown"><img
					src="${ sessionScope.user.userImage }" alt="Image Not Found"
					class="user-auth-img img-circle" /><span
					class="user-online-status"></span></a>
				<ul class="dropdown-menu user-auth-dropdown"
					data-dropdown-in="flipInX" data-dropdown-out="flipOutX">
					<li><a href="#"><i class="zmdi zmdi-card"></i><span>My
								Balance:- ( ${ sessionScope.myBalance } )</span></a></li>
					<li><a
						href="<%=request.getContextPath()%>/view/user/user-settings.jsp"><i
							class="zmdi zmdi-settings"></i><span>Settings</span></a></li>
					<li class="divider"></li>
					<li><a
						href="<%=request.getContextPath()%>/view/user/user-logout.jsp"><i
							class="zmdi zmdi-power"></i><span>Log Out</span></a></li>
				</ul></li>
		</ul>
	</div>
</nav>