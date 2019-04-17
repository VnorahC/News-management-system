package cn.yq.oa.mapper;

import cn.yq.oa.pojo.Menu;
import cn.yq.oa.pojo.Permission;
import cn.yq.oa.pojo.PermissionExample;
import cn.yq.oa.pojo.Role;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CustomPermissionMapper {
	/**
	 * 通过角色的id去角色权限表（sys_role_permission） 中获取对应的权限id（一个角色有多个权限，肯定有多个id）
	 * 
	 * @param roleId 角色id
	 * @return 多个权限id的集合
	 */
	List<Integer> selectPermissionIdsByRoleId(Integer roleId);

	/**
	 * 删除角色权限表（sys_role_permission）中对应角色id的所有权限数据
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteRolePermissionByRoleId(@Param("roleId") Integer roleId);

	/**
	 * 插入角色权限表（sys_role_permission）中对应角色id的所有权限数据
	 * 
	 * @param roleId
	 * @return
	 */
	int insertRolePermissionByRoleId(@Param("roleId") Integer roleId, @Param("permissionId") Integer PermissionId);

	/**
	 * 根据角色id查询出对应的所有的权限表达式
	 * 
	 * @param roleId
	 * @return
	 */
	List<String> selectPermissionsByRoleid(@Param("roleId") Integer roleId);
	/**
	 * 根据用户的角色id查询对应的一级菜单
	 * @param roleId
	 * @return
	 */
	List<Menu> selectParentMenu(@Param("roleId")Integer roleId);
	/**
	 * 根据当前的用户的角色和父菜单id查询出对应的子菜单
	 * @param roleId
	 * @param parentId
	 * @return
	 */
	List<Menu> selectSubMenu(@Param("roleId") Integer roleId,@Param("parentId") Integer parentId);
}