package edu.wctc.dj.week3.controller;

import edu.wctc.dj.week3.model.Name;
import edu.wctc.dj.week3.model.NameService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NameController extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			/* TODO output your page here. You may use following sample code. */
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet ProductServlet</title>");			
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet ProductServlet at " + request.getContextPath() + "</h1>");
			out.println("</body>");
			out.println("</html>");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		NameService nameService = new NameService();

		String id = request.getParameter("id");
		String search = request.getParameter("search");
		if (id != null) {
			request.setAttribute("nameList", Arrays.asList(nameService.getName(id)));
			request.getRequestDispatcher("/nameList.jsp").forward(request, response);
		} else if (search != null) {
			// TODO
			request.getRequestDispatcher("/nameDetails.jsp").forward(request, response);
		} else {
			request.setAttribute("nameList", nameService.getAllNames());
			request.getRequestDispatcher("/nameList.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}
}
