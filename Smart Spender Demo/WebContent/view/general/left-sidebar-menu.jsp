<%@page import="java.util.Date"%>
<%@page import="vo.InventoryPermissionVo"%>
<%@page import="vo.UserVo"%>
<%@page import="dao.InventoryPermissionDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="fixed-sidebar-left">
	<script type="text/javascript">
		function modalShow()
		{
			var value = false;
			value = confirm('Are you sure, You want to access the Invenory Management Module of Smart Spender!');
			if(value == true){
				var xmlhttp;
				var url = '<%=request.getContextPath()%>/InventoryPermissionController?flag=requestPermission';

				if (window.XMLHttpRequest) {
					xmlhttp = new XMLHttpRequest();
				} else {
					xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
				}

				xmlhttp.onreadystatechange = function() {
					if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
						alert(xmlhttp.responseText);
					}
				}

				xmlhttp.open('POST', url, true);
				xmlhttp.send();
			} else {
				alert('You cancelled your request for accessing Inventory Management Module of Smart Spender.');
			}
		}
	</script>
	<ul class="nav navbar-nav side-nav nicescroll-bar">
		<li class="navigation-header"><span>Main</span> <i
			class="zmdi zmdi-more"></i></li>
		<li><a id="page-dashboard"
			href="<%=request.getContextPath()%>/UserMasterController?flag=loadDashboard">
				<div class="pull-left">
					<img src="../../img/menu/dashboard.png"><i class="mr-20"></i><span
						class="right-nav-text">Dashboard</span>
				</div>
				<div class="clearfix"></div>
		</a></li>
		<li><a id="page-budget"
			href="<%=request.getContextPath()%>/BudgetMasterController?flag=loadAllBudgets">
				<div class="pull-left">
					<img src="../../img/menu/budget.png"><i class="mr-20"></i><span
						class="right-nav-text">Budget</span>
				</div>
				<div class="clearfix"></div>
		</a></li>
		<li><a id="page-add-income"
			href="<%=request.getContextPath()%>/view/pages/add-income.jsp">
				<div class="pull-left">
					<img src="../../img/menu/add-income.png"><i class="mr-20"></i><span
						class="right-nav-text">Add Income</span>
				</div>
				<div class="clearfix"></div>
		</a></li>
		<li><a id="page-add-expense"
			href="<%=request.getContextPath()%>/view/pages/add-expense.jsp">
				<div class="pull-left">
					<i class="fa fa-inr mr-20"></i><span class="right-nav-text">Add
						Expense</span>
				</div>
				<div class="clearfix"></div>
		</a></li>
		<li><hr class="light-grey-hr mb-10" /></li>
		<li class="navigation-header"><span>Components</span> <i
			class="zmdi zmdi-more"></i></li>
		<li><a id="page-categories"
			href="<%=request.getContextPath()%>/view/pages/categories.jsp"><div
					class="pull-left">
					<img src="../../img/menu/add-category.png"><i class="mr-20"></i><span
						class="right-nav-text">Categories</span>
				</div>
				<div class="clearfix"></div></a></li>
		<li><a id="transaction-list"
			href="<%=request.getContextPath()%>/TransactionMasterController?flag=loadIncomeTransaction"><div
					class="pull-left">
					<img src="../../img/menu/transaction-list.png"><i
						class="mr-20"></i><span class="right-nav-text">Transaction
						List</span>
				</div>
				<div class="clearfix"></div></a></li>
		<li><hr class="light-grey-hr mb-10" /></li>
		<li class="navigation-header"><span>Extra's</span> <i
			class="zmdi zmdi-more"></i></li>
		<c:if test="${ sessionScope.user.stockPermission == false }">
			<li><a id="sa-params" onclick="modalShow()" href="#"><div
						class="pull-left">
						<img src="../../img/menu/extra-stock-permission.png"><i
							class="mr-20"></i><span class="right-nav-text">Inventory
							Management</span>
					</div>
					<div class="clearfix"></div></a></li>
		</c:if>
	</ul>
</div>