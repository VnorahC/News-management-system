package cn.yq.oa.service;

import java.util.List;

import cn.yq.oa.pojo.Menu;
import cn.yq.oa.pojo.Permission;
import cn.yq.oa.pojo.PermissionExample;

public interface PermissionService {
	List<Permission> selectByExample(PermissionExample example);
	
	List<Integer> selectPermissionIdsByRoleId(Integer roleId);
	
	List<String> selectPermissionsByRoleid(Integer roleId);

	List<Menu> selectMenuByUsersRoleId(Integer roleId);
}
