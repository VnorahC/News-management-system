package cn.yq.oa.controller;


import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.yq.oa.pojo.Menu;
import cn.yq.oa.pojo.Role;
import cn.yq.oa.pojo.User;
import cn.yq.oa.service.PermissionService;
import cn.yq.oa.service.UserService;


@Controller
public class IndexController {
	@Autowired
	UserService userService;
	
	
	@Autowired
	private PermissionService permissionService;
	
	/**
	 * 跳转到主页
	 * @param m
	 * @return
	 */
	@RequestMapping("/index.do")
	public String index(Model m) {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		m.addAttribute("user", user);
		Role role = userService.findByUserToRoleName(user);
		m.addAttribute("role", role);
		List<Menu> menu = permissionService.selectMenuByUsersRoleId(user.getRoleId());
		m.addAttribute("menu", menu);
		return "index";
	}
	
	/**
	 * 欢迎界面
	 * @return
	 */
	@RequestMapping("/welcome.do")
	public String welcome() {
		
		return "welcome";
	}
	
}
