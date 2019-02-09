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

import dao.CategoryMasterDao;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String flag=request.getParameter("flag");
		
		if(flag.equals("insertCategory")) {
			String forCategory = request.getParameter("forCategory");
			
			if(forCategory.equals("income")) {
				addIncomeCategory(request,response);
			}else if(forCategory.equals("expense")) {
				addExpenseCategory(request,response);
			}
		}else if(flag.equals("getSubCategory")) {
			getSubCategories(request,response);
		}
	}

	private void getSubCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{
		String value=request.getParameter("value");
		CategoryMasterDao categoryMasterDao=new CategoryMasterDao();
		HttpSession session=request.getSession();
		UserVo userVo=(UserVo)session.getAttribute("user");
		
		List<CategoryVo> subCategoryList = categoryMasterDao.getSubCategoryBasedOnName(value, "income", userVo);
		CategoryVo categoryVo=subCategoryList.get(0);
		Set<SubCategoriesVo> subCategoriesVos = categoryVo.getSubCategories();
		
		PrintWriter out=response.getWriter();
		
		out.println("<subcategory>");
		for(SubCategoriesVo subCategoriesVo : subCategoriesVos) {
			out.println("<subcategoryname>"+subCategoriesVo.getSubCategoryName()+"</subcategoryname>");
		}
		out.println("</subcategory>");
	}

	private void addExpenseCategory(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int hidValueExpense=Integer.parseInt(request.getParameter("hidValueExpense"));
		CategoryVo categoryVo=new CategoryVo();
		HttpSession session=request.getSession();
		UserVo userVo=(UserVo)session.getAttribute("user");
		
		String expense_category_name = request.getParameter("expense_category_name");
		categoryVo.setCategoryName(expense_category_name);
		categoryVo.setForCategory("expense");
		categoryVo.setUserVo(userVo);
		
		Set<SubCategoriesVo> subCategories = new HashSet<SubCategoriesVo>();
		for(int i=1;i<=hidValueExpense;i++)
		{
			String subCategory = request.getParameter("exp"+i);
			if(subCategory!=null && subCategory != "")
			{
				SubCategoriesVo subCategoriesVo=new SubCategoriesVo();
				subCategoriesVo.setCategoryVo(categoryVo);
				subCategoriesVo.setSubCategoryName(subCategory);
				subCategories.add(subCategoriesVo);
			}
		}
		categoryVo.setSubCategories(subCategories);
		
		CategoryMasterDao categoryMasterDao=new CategoryMasterDao();
		categoryMasterDao.addCategory(categoryVo);
				
		CategoryMasterDao categoryMasterDao2=new CategoryMasterDao();
		List<CategoryVo> expenseList = categoryMasterDao2.getCategoryList("expense",userVo);
		session.setAttribute("expenseList", expenseList);
		session.setAttribute("user", userVo);
		
		response.sendRedirect(request.getContextPath()+"/view/pages/categories.jsp");
	}

	private void addIncomeCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException 
	{
		int hidValueIncome=Integer.parseInt(request.getParameter("hidValueIncome"));
		CategoryVo categoryVo=new CategoryVo();
		HttpSession session=request.getSession();
		UserVo userVo=(UserVo)session.getAttribute("user");

		String income_category_name = request.getParameter("income_category_name");
		categoryVo.setCategoryName(income_category_name);
		categoryVo.setForCategory("income");
		categoryVo.setUserVo(userVo);
		
		Set<SubCategoriesVo> subCategories = new HashSet<SubCategoriesVo>();
		for(int i=1;i<=hidValueIncome;i++)
		{
			String subCategory = request.getParameter("inc"+i);
			if(subCategory!=null && subCategory != "")
			{
				SubCategoriesVo subCategoriesVo=new SubCategoriesVo();
				subCategoriesVo.setCategoryVo(categoryVo);
				subCategoriesVo.setSubCategoryName(subCategory);
				subCategories.add(subCategoriesVo);
			}
		}
		categoryVo.setSubCategories(subCategories);
		
		CategoryMasterDao categoryMasterDao=new CategoryMasterDao();
		categoryMasterDao.addCategory(categoryVo);
		
		CategoryMasterDao categoryMasterDao2=new CategoryMasterDao();
		List<CategoryVo> incomeList = categoryMasterDao2.getCategoryList("income",userVo);
		session.setAttribute("incomeList", incomeList);
		session.setAttribute("user", userVo);
		
		response.sendRedirect(request.getContextPath()+"/view/pages/categories.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}