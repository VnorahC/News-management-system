package cn.yq.oa.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.yq.oa.pojo.Permission;
import cn.yq.oa.pojo.PermissionExample;
import cn.yq.oa.pojo.Role;
import cn.yq.oa.pojo.RoleExample;
import cn.yq.oa.pojo.RoleExample.Criteria;
import cn.yq.oa.service.PermissionService;
import cn.yq.oa.service.RoleService;
import cn.yq.oa.vo.MessageObject;

@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private PermissionService permissionService;

	/**
	 * 跳转到角色管理
	 * @return
	 */
	@RequestMapping("/rolePage.do")
	@RequiresPermissions("role:rolePage")
	public String rolePage() {

		return "role_list";
	}

	/**
	 * 查询出所有角色展示
	 * @param pageNum
	 * @return
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	@RequiresPermissions("role:list")
	public PageInfo<Role> list(Integer pageNum) {

		PageHelper.startPage(pageNum, 15);

		RoleExample example = new RoleExample();
		List<Role> roles = roleService.selectByExample(example);

		PageInfo<Role> pageInfo = new PageInfo<>(roles);

		return pageInfo;
	}

	/**
	 * 修改角色信息
	 * @param m
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/edit.do")
	public String edit(Model m, Integer roleId) {

		Role role = roleService.selectByPrimaryKey(roleId);

		m.addAttribute("role", role);

		return "role_edit";
	}

	/**
	 * 跳转添加角色页面
	 * @return
	 */
	@RequestMapping("/add.do")
	public String edit( ) {

		return "role_add";
	}


	/**
	 * 添加角色
	 * @param role
	 * @return
	 */
	@RequestMapping("/addRole.do")
	@ResponseBody
	@RequiresPermissions("role:insert")
	public MessageObject add(Role role) {
		System.out.println(role.getRemark());
		role.setAvailable(1);
		MessageObject mo = null;
			int row = roleService.insert(role);
			if(row == 1) {
				mo = new MessageObject(1, "修改用户成功");
			}else {
				mo = new MessageObject(0, "修改用户失败，请联系管理员");
			}
		
		return mo;
	}
		
	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	@RequestMapping("/update.do")
	@ResponseBody
	@RequiresPermissions("role:update")
	public MessageObject update(Role role) {
		MessageObject mo = null;
			int row = roleService.updateByPrimaryKeySelective(role);
			if (row == 1) {
				mo = new MessageObject(1, "修改角色成功");
			} else {
				mo = new MessageObject(0, "修改角色失败，请联系管理员");
			}
		return mo;
	}

	/**
	 * 获得所有权限
	 * @param role
	 * @return
	 */
	@RequestMapping("/getAllPermissions.do")
	@ResponseBody
	public List<Permission> getAllPermissions(Role role) {
		PermissionExample example = new PermissionExample();
		List<Permission> permissions = permissionService.selectByExample(example);
		return permissions;
	}

	/**
	 * 根据角色id获取出对应的多个权限id
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/getPermissionIdsByRoleId.do")
	@ResponseBody
	public List<Integer> getPermissionIdsByRoleId(Integer roleId) {
		List<Integer> permissionIds = permissionService.selectPermissionIdsByRoleId(roleId);
		return permissionIds;
	}

	/**
	 *  删除角色
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete.do")
	@ResponseBody
	@RequiresPermissions("role:delete")
	public MessageObject delete(Integer id) {

		MessageObject mo = null;

		int row = roleService.deleteByPrimaryKey(id);

		if (row == 1) {
			mo = new MessageObject(1, "删除角色成功");
		} else {
			mo = new MessageObject(0, "删除角色失败，请联系管理员");
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
		 List<Integer>  list= Arrays.asList(ids); 
		RoleExample example = new RoleExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andRoleIdIn(list);
		int row = roleService.deleteByExample(example);

		if (row == 1) {
			mo = new MessageObject(1, "修改用户成功");
		} else {
			mo = new MessageObject(0, "修改用户失败，请联系管理员");
		}
		return mo;
	}

	
}
