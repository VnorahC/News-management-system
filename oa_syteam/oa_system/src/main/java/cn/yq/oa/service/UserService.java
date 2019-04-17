package cn.yq.oa.service;

import java.util.List;

import cn.yq.oa.pojo.Role;
import cn.yq.oa.pojo.User;
import cn.yq.oa.pojo.UserExample;

public interface UserService {
	List<User> selectByExample(UserExample example);
	
	int insert(User record);
	
	 User selectByPrimaryKey(Integer id);
	 
	 int updateByPrimaryKeySelective(User record);
	 
	 int deleteByPrimaryKey(Integer id);
	 
	 int deleteByExample(UserExample example);
	 
	 /*
	  *根据用户名查询出用户对象
	  */
	User selectByUserCode(String usercode);

	Role findByUserToRoleName(User user);
}
