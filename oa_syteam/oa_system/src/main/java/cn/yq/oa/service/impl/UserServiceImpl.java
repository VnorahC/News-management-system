package cn.yq.oa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yq.oa.mapper.UserMapper;
import cn.yq.oa.pojo.Role;
import cn.yq.oa.pojo.User;
import cn.yq.oa.pojo.UserExample;
import cn.yq.oa.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public List<User> selectByExample(UserExample example) {
		return userMapper.selectByExample(example);
	}

	@Override
	public int insert(User record) {
		return userMapper.insert(record);
	}

	@Override
	public User selectByPrimaryKey(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(User record) {
		return userMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteByExample(UserExample example) {
		// TODO Auto-generated method stub
		return userMapper.deleteByExample(example);
	}

	@Override
	public User selectByUserCode(String usercode) {
		// TODO Auto-generated method stub
		return userMapper.selectByUserCode(usercode);
	}

	@Override
	public Role findByUserToRoleName(User user) {
		// TODO Auto-generated method stub
		return userMapper.findByUserToRoleName(user);
	}

}
