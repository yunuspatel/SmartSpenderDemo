<%@page import="java.util.Date"%>
<%@page import="vo.InventoryPermissionVo"%>
<%@page import="vo.UserVo"%>
<%@page import="dao.InventoryPermissionDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="fixed-sidebar-left">
	<ul class="nav navbar-nav side-nav nicescroll-bar">
		<li class="navigation-header"><span>Main</span> <i
			class="zmdi zmdi-more"></i></li>
		<li><a id="page-dashboard"
			href="<%=request.getContextPath()%>/SuperUserController?flag=loadSuperUserDashboard">
				<div class="pull-left">
					<img src="../../img/menu/dashboard.png"><i class="mr-20"></i><span
						class="right-nav-text">Dashboard</span>
				</div>
				<div class="clearfix"></div>
		</a></li>
		<li><a id="page-inventory-permission"
			href="<%=request.getContextPath()%>/SuperUserController?flag=loadAllInventoryPermissions">
				<div class="pull-left">
					<img src="../../img/menu/extra-stock-permission.png"><i
						class="mr-20"></i><span class="right-nav-text">Inventory
						Permission</span>
				</div>
				<div class="clearfix"></div>
		</a></li>
		<li><a id="page-list-users"
			href="<%=request.getContextPath()%>/SuperUserController?flag=listAllUsers">
				<div class="pull-left">
					<img src="../../img/menu/list-users.png"><i class="mr-20"></i><span
						class="right-nav-text">List All Users</span>
				</div>
				<div class="clearfix"></div>
		</a></li>
	</ul>
</div>