package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import dao.CategoryMasterDao;
import dao.SubCategoryDao;
import vo.CategoryVo;
import vo.SubCategoriesVo;
import vo.UserVo;

/**
 * Servlet implementation class CategoryController
 */
@WebServlet("/CategoryController")
public class CategoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CategoryController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());

		String flag = request.getParameter("flag");

		if (flag.equals("insertCategory")) {
			String forCategory = request.getParameter("forCategory");

			if (forCategory.equals("income")) {
				addIncomeCategory(request, response);
			} else if (forCategory.equals("expense")) {
				addExpenseCategory(request, response);
			}
		} else if (flag.equals("getSubCategory")) {
			getSubCategories(request, response);
		} else if (flag.equals("editIncCategory")) {
			editIncCategory(request, response);
		} else if (flag.equals("deleteCategory")) {
			deleteCategory(request, response);
		} else if (flag.equals("editExpCategory")) {
			editExpCategory(request, response);
		}
	}

	private void editExpCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int categoryId = Integer.parseInt(request.getParameter("editExpCategoryId"));
		String categoryName = request.getParameter("editExpCategoryName");
		String subCategoryArray = request.getParameter("editExpSubCategory");

		String subCategory[] = subCategoryArray.split(",");

		CategoryVo categoryVo = new CategoryVo();
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		categoryVo.setCategoryId(categoryId);
		categoryVo.setCategoryName(categoryName);
		categoryVo.setForCategory("expense");
		categoryVo.setUserVo(userVo);

		Set<SubCategoriesVo> subCategoriesVos = new HashSet<SubCategoriesVo>();

		for (String subCategories : subCategory) {
			SubCategoriesVo subCategoriesVo = new SubCategoriesVo();
			subCategoriesVo.setCategoryVo(categoryVo);
			subCategoriesVo.setSubCategoryName(subCategories);
			subCategoriesVo.setUserVo(userVo);
			subCategoriesVos.add(subCategoriesVo);
		}
		categoryVo.setSubCategories(subCategoriesVos);

		CategoryMasterDao categoryMasterDao = new CategoryMasterDao();
		categoryMasterDao.updateCategory(categoryVo);

		session.setAttribute("user", userVo);
		response.sendRedirect(request.getContextPath() + "/view/pages/categories.jsp");
	}

	private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		int categoryId = Integer.parseInt(request.getParameter("id"));

		SubCategoryDao subCategoryDao = new SubCategoryDao();
		subCategoryDao.deleteSubCategory(categoryId);

		CategoryMasterDao categoryMasterDao = new CategoryMasterDao();
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setCategoryId(categoryId);

		HttpSession session = request.getSession();

		try {
			categoryMasterDao.deleteCategory(categoryVo);
		} catch (Exception exception) {
			session.setAttribute("userMsg", "Cannot delete as categories are used for transaction.");
		}
	
		session.removeAttribute("incomeList");
		session.removeAttribute("expenseList");
		response.sendRedirect(request.getContextPath() + "/view/pages/categories.jsp");
	}

	private void editIncCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int categoryId = Integer.parseInt(request.getParameter("editIncCategoryId"));
		String categoryName = request.getParameter("editIncCategoryName");
		String subCategoryArray = request.getParameter("editIncSubCategory");

		String subCategory[] = subCategoryArray.split(",");

		CategoryVo categoryVo = new CategoryVo();
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		categoryVo.setCategoryId(categoryId);
		categoryVo.setCategoryName(categoryName);
		categoryVo.setForCategory("income");
		categoryVo.setUserVo(userVo);

		Set<SubCategoriesVo> subCategoriesVos = new HashSet<SubCategoriesVo>();

		for (String subCategories : subCategory) {
			SubCategoriesVo subCategoriesVo = new SubCategoriesVo();
			subCategoriesVo.setCategoryVo(categoryVo);
			subCategoriesVo.setSubCategoryName(subCategories);
			subCategoriesVo.setUserVo(userVo);
			subCategoriesVos.add(subCategoriesVo);
		}
		categoryVo.setSubCategories(subCategoriesVos);

		CategoryMasterDao categoryMasterDao = new CategoryMasterDao();
		categoryMasterDao.updateCategory(categoryVo);

		session.setAttribute("user", userVo);
		response.sendRedirect(request.getContextPath() + "/view/pages/categories.jsp");
	}

	private void getSubCategories(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String value = request.getParameter("value");
		String category = request.getParameter("forCategory");
		CategoryMasterDao categoryMasterDao = new CategoryMasterDao();
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		List<CategoryVo> subCategoryList = categoryMasterDao.getSubCategoryBasedOnName(value, category, userVo);
		CategoryVo categoryVo = subCategoryList.get(0);
		Set<SubCategoriesVo> subCategoriesVos = categoryVo.getSubCategories();

		PrintWriter out = response.getWriter();

		out.println("<subcategory>");
		for (SubCategoriesVo subCategoriesVo : subCategoriesVos) {
			out.println("<subcategoryname>" + subCategoriesVo.getSubCategoryName() + "</subcategoryname>");
		}
		out.println("</subcategory>");
	}

	private void addExpenseCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int hidValueExpense = Integer.parseInt(request.getParameter("hidValueExpense"));
		CategoryVo categoryVo = new CategoryVo();
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		String expense_category_name = request.getParameter("expense_category_name");
		categoryVo.setCategoryName(expense_category_name);
		categoryVo.setForCategory("expense");
		categoryVo.setUserVo(userVo);

		Set<SubCategoriesVo> subCategories = new HashSet<SubCategoriesVo>();
		for (int i = 1; i <= hidValueExpense; i++) {
			String subCategory = request.getParameter("exp" + i);
			if (subCategory != null && subCategory != "") {
				SubCategoriesVo subCategoriesVo = new SubCategoriesVo();
				subCategoriesVo.setCategoryVo(categoryVo);
				subCategoriesVo.setSubCategoryName(subCategory);
				subCategoriesVo.setUserVo(userVo);
				subCategories.add(subCategoriesVo);
			}
		}
		categoryVo.setSubCategories(subCategories);

		CategoryMasterDao categoryMasterDao = new CategoryMasterDao();
		categoryMasterDao.addCategory(categoryVo);

		CategoryMasterDao categoryMasterDao2 = new CategoryMasterDao();
		List<CategoryVo> expenseList = categoryMasterDao2.getCategoryList("expense", userVo);
		session.setAttribute("expenseList", expenseList);
		session.setAttribute("user", userVo);

		response.sendRedirect(request.getContextPath() + "/view/pages/categories.jsp");
	}

	private void addIncomeCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int hidValueIncome = Integer.parseInt(request.getParameter("hidValueIncome"));
		CategoryVo categoryVo = new CategoryVo();
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		String income_category_name = request.getParameter("income_category_name");
		categoryVo.setCategoryName(income_category_name);
		categoryVo.setForCategory("income");
		categoryVo.setUserVo(userVo);

		Set<SubCategoriesVo> subCategories = new HashSet<SubCategoriesVo>();
		for (int i = 1; i <= hidValueIncome; i++) {
			String subCategory = request.getParameter("inc" + i);
			if (subCategory != null && subCategory != "") {
				SubCategoriesVo subCategoriesVo = new SubCategoriesVo();
				subCategoriesVo.setCategoryVo(categoryVo);
				subCategoriesVo.setSubCategoryName(subCategory);
				subCategoriesVo.setUserVo(userVo);
				subCategories.add(subCategoriesVo);
			}
		}
		categoryVo.setSubCategories(subCategories);

		CategoryMasterDao categoryMasterDao = new CategoryMasterDao();
		categoryMasterDao.addCategory(categoryVo);

		CategoryMasterDao categoryMasterDao2 = new CategoryMasterDao();
		List<CategoryVo> incomeList = categoryMasterDao2.getCategoryList("income", userVo);
		session.setAttribute("incomeList", incomeList);
		session.setAttribute("user", userVo);

		response.sendRedirect(request.getContextPath() + "/view/pages/categories.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}