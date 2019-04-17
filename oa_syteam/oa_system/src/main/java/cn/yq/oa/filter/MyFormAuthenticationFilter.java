package cn.yq.oa.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
	
	/**
	 * 此方法解决登录退出后 无法再次访问问题
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		//清除shiro 在session存储的上一次访问地址 shiroSavedReques
		//1.获取session
		Session session = subject.getSession(false);
		if(session != null) {
			//清除shiro共享的上一次地址
			session.removeAttribute(WebUtils.SAVED_REQUEST_KEY);
		}
		return super.onLoginSuccess(token, subject, request, response);
	}
	
	//此方法在Shiro认证前执行
	//认证前先比对验证码
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		HttpServletRequest req = (HttpServletRequest)request;
		
		//1.获取session的随机数
		String rand = (String) req.getSession().getAttribute("rand");
		
		//2.获取用户提交的验证码参数
		String randomCode = req.getParameter("randomCode");
		
		//3.比对值是否相等，相等，默认执行父类方法，不相等，直接返回一个true
		if(rand !=null) {
			if(!rand.toLowerCase().equals(randomCode.toLowerCase())) {
				//共享一个错误信息到 shiroLoginFailure
				request.setAttribute("shiroLoginFailure", "verifyCodeError");
				
				//返回true，shiro就不在进行下一操作（数据库认证）了，直接返回了
				return true;
			}
		}
		
		//正常执行父类方法
		return super.onAccessDenied(request, response, mappedValue);
	}
}
