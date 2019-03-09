<%@page import="java.util.List"%>
<%@page import="vo.NotificationVo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="mobile-only-brand pull-left">
		<div class="nav-header pull-left">
			<div class="logo-wrap">
				<a href="<%=request.getContextPath()%>/SuperUserController?flag=loadSuperUserDashboard"> <img
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
			<li class="dropdown auth-drp"><a href="#"
				class="dropdown-toggle pr-0" data-toggle="dropdown"><img
					src="../../img/profile/empty_user_icon.jpg" alt="Image Not Found"
					class="user-auth-img img-circle" /><span
					class="user-online-status"></span></a>
				<ul class="dropdown-menu user-auth-dropdown"
					data-dropdown-in="flipInX" data-dropdown-out="flipOutX">
					<li class="divider"></li>
					<li><a
						href="<%=request.getContextPath()%>/view/admin/admin-logout.jsp"><i
							class="zmdi zmdi-power"></i><span>Log Out</span></a></li>
				</ul></li>
		</ul>
	</div>
</nav>