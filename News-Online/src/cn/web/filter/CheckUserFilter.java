package cn.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 用户身份检测过滤
 * 
 * @author Administrator
 *
 */
public class CheckUserFilter implements Filter {
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {// 初始化
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();

		if (session.getAttribute("loginUser") != null) {// 用户是合法用户-->要访问的资源
			// 过滤链
			// 如果不调用该方法，请求会永远停在过滤器中
			chain.doFilter(request, response);// 进入一个过滤器，如果没有了过滤器就会进入要访问的资源
		} else {// 非法用户
			resp.sendRedirect(req.getContextPath()+"/NewsServlet?action=listNews");
		}
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {// 销毁
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */

}
