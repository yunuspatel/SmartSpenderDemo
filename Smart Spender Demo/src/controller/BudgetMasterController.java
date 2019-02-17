package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BudgetMasterDao;
import vo.BudgetVo;
import vo.UserVo;

/**
 * Servlet implementation class BudgetMasterController
 */
@WebServlet("/BudgetMasterController")
public class BudgetMasterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BudgetMasterController() {
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

		String flag = request.getParameter("flag");

		if (flag.equals("loadAllBudgets")) {
			loadAllBudgets(request,response);
		}else if(flag.equals("addBudget")) {
			addBudget(request,response);
		}
	}

	private void addBudget(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		UserVo userVo=(UserVo)session.getAttribute("user");
		
		BudgetVo budgetVo=new BudgetVo();
		budgetVo.setBudgetAlertAmount(Float.parseFloat(request.getParameter("alertAmount")));
		budgetVo.setBudgetAmount(Float.parseFloat(request.getParameter("budgetAmount")));
		budgetVo.setBudgetAmountLeft(budgetVo.getBudgetAmount());
		budgetVo.setBudgetDescription(request.getParameter("budgetDecription"));
		budgetVo.setBudgetEndDate(request.getParameter("endDate"));
		budgetVo.setBudgetName(request.getParameter("budgetName"));
		budgetVo.setBudgetStartDate(request.getParameter("startDate"));
		budgetVo.setDeleted(false);
		budgetVo.setUserVo(userVo);
		
		BudgetMasterDao budgetMasterDao=new BudgetMasterDao();
		budgetMasterDao.addBudget(budgetVo);
		
		session.setAttribute("user", userVo);
		response.sendRedirect(request.getContextPath() + "/BudgetMasterController?flag=loadAllBudgets");
	}

	private void loadAllBudgets(HttpServletRequest request, HttpServletResponse response) throws IOException{
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		UserVo userVo=(UserVo)session.getAttribute("user");
		
		BudgetMasterDao budgetMasterDao=new BudgetMasterDao();
		List<BudgetVo> budgetList = budgetMasterDao.getAllBudgets(userVo);
		
		session.setAttribute("budgetList", budgetList);
		session.setAttribute("user", userVo);
		response.sendRedirect(request.getContextPath() + "/view/pages/budget.jsp");
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
