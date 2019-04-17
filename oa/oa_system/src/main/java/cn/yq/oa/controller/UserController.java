package cn.yq.oa.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.yq.oa.pojo.Role;
import cn.yq.oa.pojo.RoleExample;
import cn.yq.oa.pojo.User;
import cn.yq.oa.pojo.UserExample;
import cn.yq.oa.pojo.UserExample.Criteria;
import cn.yq.oa.service.RoleService;
import cn.yq.oa.service.UserService;
import cn.yq.oa.vo.MessageObject;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	/**
	 * 用户登录与认证
	 * @param request
	 * @param m
	 * @return
	 */
	@RequestMapping("/login.do")
	public String login(HttpServletRequest request,Model m) {
		String exceptionName = (String) request.getAttribute("shiroLoginFailure");
		
		System.out.println(exceptionName);
		if(UnknownAccountException.class.getName().equals(exceptionName)) {
			m.addAttribute("errorMsg", "账号不存在！");
		}else if(IncorrectCredentialsException.class.getName().equals(exceptionName)) {
			m.addAttribute("errorMsg", "密码错误！");
		}else if("verifyCodeError".equals(exceptionName)){
			m.addAttribute("errorMsg", "验证码错误");
		}
		return "forward:/login.jsp";
	}

	/**
	 * 退出系统
	 * @return
	 */
	@RequestMapping("/logout.do")
	public String logout() {

		return "redirect:/login.jsp";
	}

	/**
	 * 跳转到用户管理
	 * @return
	 */
	@RequestMapping("/userPage.do")
	@RequiresPermissions("user:userPage")
	public String userPage() {

		return "user_list";
	}

	/**
	 * 查询出所有用户信息展示
	 * @param pageNum
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/list.do")
	@ResponseBody
	@RequiresPermissions("user:list")
	public PageInfo list(Integer pageNum) {
		PageHelper.startPage(pageNum, 15);
		UserExample example = new UserExample();
		List<User> users = userService.selectByExample(example);
		PageInfo<User> pageInfo = new PageInfo<>(users);

		return pageInfo;
	}

	/**
	 * 将用户信息传入编辑页面
	 * @param id
	 * @param m
	 * @return
	 */
	@RequestMapping("/edit.do")
	public String userEdit(Integer id, Model m) {
		User user = userService.selectByPrimaryKey(id);
		m.addAttribute("user", user);
		RoleExample example = new RoleExample();
		List<Role> roles = roleService.selectByExample(example);
		m.addAttribute("roles", roles);

		return "user_edit";
	}

	/**
	 * 添加用户信息
	 * @param m
	 * @return
	 */
	@RequestMapping("/add.do")
	@RequiresPermissions("user:insert")
	public String userEdit(Model m) {
		RoleExample example = new RoleExample();
		List<Role> roles = roleService.selectByExample(example);
		m.addAttribute("roles", roles);

		return "user_edit";
	}

	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	@RequestMapping("/update.do")
	@ResponseBody
	@RequiresPermissions("user:update")
	public MessageObject update(User user) {
		System.out.println(user);
		MessageObject mo = null;
		if (user.getId() != null) {
			int row = userService.updateByPrimaryKeySelective(user);

			if (row == 1) {
				mo = new MessageObject(1, "修改用户成功");
			} else {
				mo = new MessageObject(0, "修改用户失败，请联系管理员");
			}
		} else {
			int row = userService.insert(user);
			if (row == 1) {
				mo = new MessageObject(1, "修改用户成功");
			} else {
				mo = new MessageObject(0, "修改用户失败，请联系管理员");
			}
		}
		return mo;
	}

	/**
	 * 删除指定id的用户
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete.do")
	@ResponseBody
	@RequiresPermissions("user:delete")
	public MessageObject delete(Integer id) {

		MessageObject mo = null;

		int row = userService.deleteByPrimaryKey(id);

		if (row == 1) {
			mo = new MessageObject(1, "修改用户成功");
		} else {
			mo = new MessageObject(0, "修改用户失败，请联系管理员");
		}
		return mo;
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteAll.do")
	@ResponseBody
	public MessageObject deleteAll(@RequestParam(value = "ids[]")Integer[]  ids) {
		MessageObject mo = null;
		UserExample example = new UserExample();
		 List<Integer>   list= Arrays.asList(ids); 
		Criteria criteria  = example.createCriteria(); 
		criteria.andIdIn(list);
		int row = userService.deleteByExample(example);

		if (row == 1) {
			mo = new MessageObject(1, "修改用户成功");
		} else {
			mo = new MessageObject(0, "修改用户失败，请联系管理员");
		}
		return mo;
	}

}
