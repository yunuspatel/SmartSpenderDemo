package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.SalesDao;
import vo.SalesVo;
import vo.UserVo;

/**
 * Servlet implementation class SalesController
 */
@WebServlet("/SalesController")
public class SalesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SalesController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String flag = request.getParameter("flag");
		
		if(flag.equals("loadSales")) {
			loadSales(request,response);
		}
	}

	private void loadSales(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		UserVo userVo=(UserVo)session.getAttribute("user");
		
		SalesDao salesDao=new SalesDao();
		List<SalesVo> salesList = salesDao.loadSales(userVo);
		session.setAttribute("salesList", salesList);
		
		session.setAttribute("user", userVo);
		response.sendRedirect(request.getContextPath() + "/view/pages/sales.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
