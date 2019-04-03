package cn.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.web.dao.UserDao;
import cn.web.entity.User;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		if("login".equals(action)) {
			doLogin(request,response);
		}else if("logout".equals(action)) {
			doLogout(request,response);
		}
	}

	private void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath()+"/NewsServlet?action=listNews");
	}

	private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		//获取用户的账号和密码
		String username = request.getParameter("uname");
		String password = request.getParameter("upwd");
		
		UserDao userDao = new UserDao();
		User loginUser = userDao.login(username, password);
		
		if(loginUser!=null) {//登录成功
			//在session中注册用户的信息
			HttpSession session = request.getSession();
			session.setAttribute("loginUser",loginUser);
			response.sendRedirect(request.getContextPath()+"/NewsServlet?action=listNews");
		}else {
			request.setAttribute("error", "登录失败，账号或密码错误！");
			request.getRequestDispatcher("/NewsServlet?action=listNews").forward(request, response);
		}
	}

}
