package cn.yq.oa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yq.oa.mapper.CustomPermissionMapper;
import cn.yq.oa.mapper.RoleMapper;
import cn.yq.oa.pojo.Role;
import cn.yq.oa.pojo.RoleExample;
import cn.yq.oa.service.RoleService;


@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleMapper roleMapper;
	
	//引入自定义权限的Mapper
	@Autowired
	private CustomPermissionMapper customPermissionMapper;
	
	@Override
	public List<Role> selectByExample(RoleExample example) {
		
		return roleMapper.selectByExample(example);
	}

	@Override
	public int updateByPrimaryKeySelective(Role record) {
		
		//修改角色信息
		//1.修改角色的基本信息
		//2.修改角色对应的权限信息
		
		 //修改角色基本信息
		 int row = roleMapper.updateByPrimaryKeySelective(record);
		
		 if(row == 1) {
			//修改角色对应的权限信息
			 //1.删除角色权限表中对应的所有的权限
			customPermissionMapper.deleteRolePermissionByRoleId(record.getRoleId());
			 
				 //2.将用户提交的权限数据添加到角色权限表中
			Integer[] permissionIds = record.getPermissionIds();
			for (Integer permissionId : permissionIds) {
				customPermissionMapper.insertRolePermissionByRoleId(record.getRoleId(), permissionId);
			}
				 
		 }
		return row; 
	}

	@Override
	public Role selectByPrimaryKey(Integer roleId) {
		return roleMapper.selectByPrimaryKey(roleId);
	}

	@Override
	public int deleteByPrimaryKey(Integer roleId) {
		// TODO Auto-generated method stub
		return roleMapper.deleteByPrimaryKey(roleId);
	}

	@Override
	public int insert(Role record) {
		// TODO Auto-generated method stub
		return roleMapper.insert(record);
	}

	@Override
	public int deleteByExample(RoleExample example) {
		// TODO Auto-generated method stub
		return roleMapper.deleteByExample(example);
	}

}
