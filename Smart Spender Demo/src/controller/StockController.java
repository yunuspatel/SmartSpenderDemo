package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.StockDao;
import vo.StockVo;
import vo.UserVo;

/**
 * Servlet implementation class StockController
 */
@WebServlet("/StockController")
public class StockController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StockController() {
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

		try {
			if (flag.equals("loadStock")) {
				loadStock(request, response);
			}
		} catch (Exception exception) {
			String relativePathLog = "logs";
			String rootPathLog = getServletContext().getRealPath(relativePathLog);
			File logFile = new File(rootPathLog);
			if (!logFile.exists()) {
				logFile.mkdirs();
			}
			FileWriter fileWriter = new FileWriter(logFile + "/error-log.txt", true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.write(exception.getMessage());
			printWriter.write(System.lineSeparator());
			printWriter.close();
		}
	}

	private void loadStock(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("user");

		StockDao stockDao = new StockDao();
		List<StockVo> stockList = stockDao.loadStock(userVo);

		session.setAttribute("stockList", stockList);
		session.setAttribute("user", userVo);
		response.sendRedirect(request.getContextPath() + "/view/pages/stock.jsp");
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
