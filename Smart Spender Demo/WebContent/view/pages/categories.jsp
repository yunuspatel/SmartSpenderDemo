<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="vo.CategoryVo"%>
<%@page import="java.util.List"%>
<%@page import="dao.CategoryMasterDao"%>
<%@page import="dao.UserMasterDao"%>
<%@page import="vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<%
	UserVo userVo = (UserVo) session.getAttribute("user");
	if (userVo == null) {
		response.sendRedirect(request.getContextPath() + "/view/user/login.jsp");
	} else {
		session.setAttribute("user", userVo);
		CategoryMasterDao categoryMasterDao = new CategoryMasterDao();
		List<CategoryVo> incomeList = categoryMasterDao.getCategoryList("income", userVo);
		if (!incomeList.isEmpty()) {
			session.setAttribute("incomeList", incomeList);
		}
		CategoryMasterDao categoryMasterDao2 = new CategoryMasterDao();
		List<CategoryVo> expenseList = categoryMasterDao2.getCategoryList("expense", userVo);
		if (!expenseList.isEmpty()) {
			session.setAttribute("expenseList", expenseList);
		}
	}
%>
<head>
<meta charset="UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>Categories</title>

<!-- Favicon -->
<link rel="shortcut icon" href="../../img/logo2.png">
<link rel="icon" href="../../img/logo2.png" type="image/x-icon">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
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

<script type="text/javascript">
	function checkActive() {
		var element = document.getElementById('page-categories');
		element.classList.add("active");
		<%
			Object object=session.getAttribute("userMsg");
			if(object!=null)
			{
			%>
				alert("<%= object %>");
			<%
				session.removeAttribute("userMsg");
			}
		%>
	}

	function addIncSubControls() {
		var hidVal = document.getElementById('hidValueIncome').value;
		var value = (hidVal * 1) + 1;
		var main = document.createElement('div');
		main.id = value;
		var div = '<div class="col-md-8"><input type="text" required="" autocomplete="off" class="form-control" id="inc' + value + '" name="inc' + value + '" placeholder="Enter Sub-Category Name"></div>';
		div += '<div class="col-md-4"><button type="button" class="btn btn-info btn-icon-anim btn-circle" id="inc'
				+ value
				+ '" onclick="removeIncControl(this)"><i class="icon-trash"></i></button></div>'
		main.innerHTML = div;
		document.getElementById('incomeNodes').appendChild(main);
		document.getElementById('hidValueIncome').value = value;
	}

	function removeIncControl(div) {
		var ctrlParent = div.parentNode;
		document.getElementById("incomeNodes").removeChild(
				ctrlParent.parentNode);
	}

	function addExpSubControls() {
		var hidVal = document.getElementById('hidValueExpense').value;
		var value = (hidVal * 1) + 1;
		var main = document.createElement('div');
		main.id = value;
		var div = '<div class="col-md-8"><input type="text" required="" autocomplete="off" class="form-control" id="exp' + value + '" name="exp' + value + '" placeholder="Enter Sub-Category Name"></div>';
		div += '<div class="col-md-4"><button type="button" class="btn btn-info btn-icon-anim btn-circle" id="exp'
				+ value
				+ '" onclick="removeExpControl(this)"><i class="icon-trash"></i></button></div>'
		main.innerHTML = div;
		document.getElementById('expenseNodes').appendChild(main);
		document.getElementById('hidValueExpense').value = value;
	}

	function removeExpControl(div) {
		var ctrlParent = div.parentNode;
		document.getElementById("expenseNodes").removeChild(
				ctrlParent.parentNode);
	}
</script>
</head>
<body onload="checkActive()">
	<!-- Preloader -->
	<div class="preloader-it">
		<div class="la-anim-1"></div>
	</div>
	<!-- /Preloader -->
	<div class="wrapper theme-1-active pimary-color-red">
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
			<div class="container-fluid pt-25">

				<!-- Modals Content -->
				<div id="modal_income_categories" class="modal fade" tabindex="-1">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h6 class="modal-title">Add Income category</h6>
								<button type="button" class="close" data-dismiss="modal">&times;</button>
							</div>
							<form method="post"
								action="<%=request.getContextPath()%>/CategoryController">
								<input type="hidden" name="flag" value="insertCategory">
								<input type="hidden" name="forCategory" value="income">
								<input type="hidden" id="hidValueIncome" name="hidValueIncome"
									value="1">
								<div class="modal-body">
									<div class="form-group">
										<label class="control-label mb-10" for="income_category_name">Category
											Name:-</label> <input type="text" required="" autocomplete="off"
											class="form-control" id="income_category_name"
											name="income_category_name" placeholder="Enter Category Name">
									</div>
									<div class="form-group">
										<label class="control-label mb-10" for="sub_categories">Enter
											Sub Categories</label>
										<div id="incomeNodes">
											<div class="col-md-8">
												<input type="text" required="" autocomplete="off"
													class="form-control" id="inc1" name="inc1"
													placeholder="Enter Sub-Category Name">
											</div>
											<div class="col-md-4">
												<input type="button" name="inc1" id="inc1" value="+"
													onclick="addIncSubControls()"
													class="btn btn-sm  btn-primary btn-outline btn-rounded">
											</div>
										</div>
										<div class="row row-lg"></div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="submit" class="btn bg-primary">Save
										changes</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div id="modal_expense_categories" class="modal fade" tabindex="-1">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h6 class="modal-title">Add Expense category</h6>
								<button type="button" class="close" data-dismiss="modal">&times;</button>
							</div>
							<form method="post"
								action="<%=request.getContextPath()%>/CategoryController">
								<input type="hidden" name="flag" value="insertCategory">
								<input type="hidden" name="forCategory" value="expense">
								<input type="hidden" id="hidValueExpense" name="hidValueExpense"
									value="1">
								<div class="modal-body">
									<div class="form-group">
										<label class="control-label mb-10" for="expense_category_name">Category
											Name:-</label> <input type="text" required="" autocomplete="off"
											class="form-control" id="expense_category_name"
											name="expense_category_name"
											placeholder="Enter Category Name">
									</div>
									<div class="form-group">
										<label class="control-label mb-10" for="sub_categories">Enter
											Sub Categories</label>
										<div id="expenseNodes">
											<div class="col-md-8">
												<input type="text" required="" autocomplete="off"
													class="form-control" id="exp1" name="exp1"
													placeholder="Enter Sub-Category Name">
											</div>
											<div class="col-md-4">
												<input type="button" name="exp1" id="exp1" value="+"
													onclick="addExpSubControls()"
													class="btn btn-sm  btn-primary btn-outline btn-rounded">
											</div>
										</div>
										<div class="row row-lg"></div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="submit" class="btn bg-primary">Save
										changes</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				<!-- End Modals Content -->

				<!--Edit Category Modal -->
				<div class="modal fade modal-fade-in-scale-up"
					id="editIncCategoryModal" aria-hidden="true"
					aria-labelledby="exampleModalTitle" role="dialog" tabindex="-1">
					<div class="modal-dialog modal-simple">
						<form method="post"
							action="<%=request.getContextPath()%>/CategoryController">
							<input type="hidden" name="flag" value="editIncCategory">
							<input type="hidden" name="editIncCategoryId"
								id="editIncCategoryId">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">x</button>
									<h4 class="modal-title">Edit Category</h4>
								</div>
								<div class="modal-body">
									<div class="row">
										<div class="form-group col-md-8">
											<label class="control-label mb-10" for="editIncCategoryName">Category
												Name</label> <input type="text" class="form-control"
												id="editIncCategoryName" name="editIncCategoryName"
												placeholder="Category Name" autocomplete="off">
										</div>
									</div>
									<div class="row">
										<div class="form-group col-md-8">
											<label class="control-label mb-10" for="editIncSubCategory">Sub-Categories
											</label> <input type="text" class="form-control"
												id="editIncSubCategory" name="editIncSubCategory"
												placeholder="Sub Category Name" autocomplete="off">
											<label class="control-label mb-10" style="color: black">
												Note:- Enter "," to seperate various sub category fileds </label>
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Close</button>
									<button type="submit" class="btn btn-primary">Save</button>
								</div>
							</div>
						</form>
					</div>
				</div>
				<div class="modal fade modal-fade-in-scale-up"
					id="editExpCategoryModal" aria-hidden="true"
					aria-labelledby="exampleModalTitle" role="dialog" tabindex="-1">
					<div class="modal-dialog modal-simple">
						<form method="post"
							action="<%=request.getContextPath()%>/CategoryController">
							<input type="hidden" name="flag" value="editExpCategory">
							<input type="hidden" name="editExpCategoryId"
								id="editExpCategoryId">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">x</button>
									<h4 class="modal-title">Edit Category</h4>
								</div>
								<div class="modal-body">
									<div class="row">
										<div class="form-group col-md-8">
											<label class="control-label mb-10" for="editExpCategoryName">Category
												Name</label> <input type="text" class="form-control"
												id="editExpCategoryName" name="editExpCategoryName"
												placeholder="Category Name" autocomplete="off">
										</div>
									</div>
									<div class="row">
										<div class="form-group col-md-8">
											<label class="control-label mb-10" for="editExpSubCategory">Sub-Categories
											</label> <input type="text" class="form-control"
												id="editExpSubCategory" name="editExpSubCategory"
												placeholder="Sub Category Name" autocomplete="off">
											<label class="control-label mb-10" style="color: black">
												Note:- Enter "," to seperate various sub category fileds </label>
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Close</button>
									<button type="submit" class="btn btn-primary">Save</button>
								</div>
							</div>
						</form>
					</div>
				</div>
				<!-- End Edit Category Modal -->

				<div class="page-header page-header-light">
					<div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
						<h5 class="txt-dark">Add Categories</h5>
					</div>
					<div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
						<ol class="breadcrumb">
							<li><a href="<%= request.getContextPath() %>/view/pages/home.jsp">Main</a></li>
							<li class="active"><span>Categories</span></li>
						</ol>
					</div>
				</div>
				<!-- Row -->
				<div class="row row-lg">
					<div class="col-md-6">
						<div class="panel panel-default card-view panel-refresh">
							<div class="refresh-container" style="display: none;">
								<div class="la-anim-1"></div>
							</div>
							<div class="panel-heading">
								<div class="pull-left">
									<h6 class="panel-title txt-dark">Income Categories</h6>
								</div>
								<div class="pull-right">
									<a href="#" class="pull-left inline-block refresh mr-15"> <i
										class="zmdi zmdi-replay"></i>
									</a>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="panel-wrapper collapse in">
								<div class="panel-body">
									<div class="row row-md col-md-6">
										<button type="button" class="btn bg-primary"
											data-toggle="modal" data-target="#modal_income_categories">Add
											Category & Sub-Categories</button>
									</div>
									<div class="table-wrap">
										<div class="">
											<table id="myTableIncome"
												class="table table-hover display  pb-10">
												<thead>
													<tr>
														<th>Category Name</th>
														<th>Sub-Categories</th>
														<th>Actions</th>
													</tr>
												</thead>
												<tfoot>
													<tr>
														<th>Category Name</th>
														<th>Sub-Categories</th>
														<th>Actions</th>
													</tr>
												</tfoot>
												<tbody>
													<c:forEach var="incomeData"
														items="${ sessionScope.incomeList }">
														<tr>
															<td><input type="hidden"
																id="incCategoryName${ incomeData.categoryId }"
																value="${ incomeData.categoryName }">${ incomeData.categoryName }</td>
															<td>
																<%
																	int count = 1;
																%> <c:forEach var="subIncomeData"
																	items="${ incomeData.subCategories }">
																	<%
																		count++;
																	%>
																	<input type="hidden"
																		id="incSubCategory${ incomeData.categoryId }<%= count-1 %>"
																		value="${subIncomeData.subCategoryName }">
																${subIncomeData.subCategoryName },
																</c:forEach><input type="hidden"
																id="incSubCategoryCount${ incomeData.categoryId }"
																value="<%=count%>">
															</td>
															<td><a href="#" id="${ incomeData.categoryId }"
																class="my-edit-class-incCategory"><i
																	class="fa fa-edit" aria-hidden="true"></i></a> <a
																href="<%= request.getContextPath() %>/CategoryController?flag=deleteCategory&id=${ incomeData.categoryId }"
																id="${ incomeData.categoryId }"><i class="ti-trash"
																	aria-hidden="true"></i></a></td>
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
					<div class="col-md-6">
						<div class="panel panel-default card-view panel-refresh">
							<div class="refresh-container" style="display: none;">
								<div class="la-anim-1"></div>
							</div>
							<div class="panel-heading">
								<div class="pull-left">
									<h6 class="panel-title txt-dark">Expense Categories</h6>
								</div>
								<div class="pull-right">
									<a href="#" class="pull-left inline-block refresh mr-15"> <i
										class="zmdi zmdi-replay"></i>
									</a>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="panel-wrapper collapse in">
								<div class="panel-body">
									<div class="row row-md col-md-6">
										<button type="button" class="btn bg-primary"
											data-toggle="modal" data-target="#modal_expense_categories">Add
											Category & Sub-Categories</button>
									</div>
									<div class="table-wrap">
										<div class="">
											<!-- id="myTable1" for response data table -->
											<table id="myTableExpense" class="table pb-10">
												<thead>
													<tr>
														<th>Category Name</th>
														<th>Sub-Categories</th>
														<th>Actions</th>
													</tr>
												</thead>
												<tfoot>
													<tr>
														<th>Category Name</th>
														<th>Sub-Categories</th>
														<th>Actions</th>
													</tr>
												</tfoot>
												<tbody>
													<c:forEach var="expenseData"
														items="${ sessionScope.expenseList }">
														<tr>
															<td><input type="hidden"
																id="expCategoryName${ expenseData.categoryId }"
																value="${ expenseData.categoryName }">${ expenseData.categoryName }</td>
															<td>
																<%
																	int count = 1;
																%><c:forEach var="subExpenseData"
																	items="${ expenseData.subCategories }">
																	<%
																		count++;
																	%>
																	<input type="hidden"
																		id="expSubCategory${ expenseData.categoryId }<%= count-1 %>"
																		value="${subExpenseData.subCategoryName }">
																${subExpenseData.subCategoryName },	
															</c:forEach><input type="hidden"
																id="expSubCategoryCount${ expenseData.categoryId }"
																value="<%=count%>">
															</td>
															<td><a href="#" id="${ expenseData.categoryId }"
																class="my-edit-class-expCategory"><i
																	class="fa fa-edit" aria-hidden="true"></i></a> <a
																href="<%= request.getContextPath() %>/CategoryController?flag=deleteCategory&id=${ expenseData.categoryId }"
																id="${ expenseData.categoryId }"><i class="ti-trash"
																	aria-hidden="true"></i></a></td>
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
		<script type="text/javascript">
			$(".my-edit-class-incCategory").on(
					'click',
					function() {
						var id = $(this).attr("id");
						$("#editIncCategoryId").val(id);
						$("#editIncCategoryName").val(
								$("#incCategoryName" + id).val());
						var subCount = $('#incSubCategoryCount' + id).val();
						var incSubCategory = "";

						subCount = (subCount * 1);
						var i = (1 * 1);
						while (i < subCount) {
							incSubCategory += $('#incSubCategory' + id + i)
									.val()
									+ ', ';
							i = (i * 1) + 1;
						}
						$('#editIncSubCategory').val(incSubCategory);
						$('#editIncCategoryModal').modal('show');
					});
			$(".my-edit-class-expCategory").on(
					'click',
					function() {
						var id = $(this).attr("id");
						$("#editExpCategoryId").val(id);
						$("#editExpCategoryName").val(
								$("#expCategoryName" + id).val());
						var subCount = $('#expSubCategoryCount' + id).val();
						var expSubCategory = "";

						subCount = (subCount * 1);
						var i = (1 * 1);
						while (i < subCount) {
							expSubCategory += $('#expSubCategory' + id + i)
									.val()
									+ ', ';
							i = (i * 1) + 1;
						}
						$('#editExpSubCategory').val(expSubCategory);
						$('#editExpCategoryModal').modal('show');
					});
		</script>
	</div>

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