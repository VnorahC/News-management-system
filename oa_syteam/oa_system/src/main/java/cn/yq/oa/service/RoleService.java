package cn.yq.oa.service;

import java.util.List;

import cn.yq.oa.pojo.Role;
import cn.yq.oa.pojo.RoleExample;
import cn.yq.oa.pojo.User;
import cn.yq.oa.pojo.UserExample;

public interface RoleService {
	List<Role> selectByExample(RoleExample example);

	int updateByPrimaryKeySelective(Role role);

	Role selectByPrimaryKey(Integer roleId);
	
	int deleteByPrimaryKey(Integer roleId);
	
	int insert(Role record);
	
	int deleteByExample(RoleExample example);
}
