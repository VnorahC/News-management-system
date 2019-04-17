package cn.yq.oa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yq.oa.mapper.CustomPermissionMapper;
import cn.yq.oa.mapper.PermissionMapper;
import cn.yq.oa.pojo.Menu;
import cn.yq.oa.pojo.Permission;
import cn.yq.oa.pojo.PermissionExample;
import cn.yq.oa.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionMapper permissionMapper;
	
	//引入自定义权限Mapper
	@Autowired
	private CustomPermissionMapper customPermissionMapper;
	
	@Override
	public List<Permission> selectByExample(PermissionExample example) {
		// TODO Auto-generated method stub
		return permissionMapper.selectByExample(example);
	}

	@Override
	public List<Integer> selectPermissionIdsByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		return customPermissionMapper.selectPermissionIdsByRoleId(roleId);
	}

	@Override
	public List<String> selectPermissionsByRoleid(Integer roleId) {
		// TODO Auto-generated method stub
		return customPermissionMapper.selectPermissionsByRoleid(roleId);
	}

	@Override
	public List<Menu> selectMenuByUsersRoleId(Integer roleId) {
		
		//1.先查出父菜单
		List<Menu> parentMenu = customPermissionMapper.selectParentMenu(roleId);
		//2.在根据每个父菜单的id查询对应的子菜单
		for (Menu menu : parentMenu) {
			List<Menu> subMenu =	customPermissionMapper.selectSubMenu(roleId,menu.getId());
			//2.1把子菜单设置给父菜单的children
			menu.setChidren(subMenu);
		}
		
		return parentMenu;
	}

}
