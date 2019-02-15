<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<div class="fixed-sidebar-left">
	<ul class="nav navbar-nav side-nav nicescroll-bar">
		<li class="navigation-header"><span>Main</span> <i
			class="zmdi zmdi-more"></i></li>
		<li><a id="page-dashboard"
			href="<%=request.getContextPath()%>/view/pages/home.jsp">
				<div class="pull-left">
					<img src="../../img/menu/dashboard.png"><i class="mr-20"></i><span
						class="right-nav-text">Dashboard</span>
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
					<i class="fa fa-inr mr-20"></i><span
						class="right-nav-text">Add Expense</span>
				</div>
				<div class="clearfix"></div>
		</a></li>
		<li><hr class="light-grey-hr mb-10" /></li>
		<li class="navigation-header"><span>component</span> <i
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
	</ul>
</div>