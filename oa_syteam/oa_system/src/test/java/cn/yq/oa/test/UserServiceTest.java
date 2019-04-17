package cn.yq.oa.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;import java.util.Random;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.yq.oa.pojo.User;
import cn.yq.oa.pojo.UserExample;
import cn.yq.oa.pojo.UserExample.Criteria;
import cn.yq.oa.service.UserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserServiceTest {

	
	@Autowired
	private UserService userService;
	
	@Test
	public void dele() throws Exception {
		int row = userService.deleteByPrimaryKey(180);
		System.out.println("删除了"+row+"条数据");
	}
	
	@Test
	public void testSelectByExample() {
		
		//用户条件对象（用于封装各种查询条件）
		UserExample example = new UserExample();
		
		/*
		 *	使用分页插件
		 * pageNum:当前第几页
		 * pageSize：每页条数
		 * PageHelper.startPage(pageNum, pageSize)
		 */
		PageHelper.startPage(5, 13);
		
		
		List<User> users = userService.selectByExample(example);
		for (User user : users) {
			System.out.println(user);
		}
		
		/*
		 * 创建分页对象
		 * 分页对象中就封装了，当前查询所有信息
		 * 当前页，总条数，总也数，其实位置，结束位置
		 */
		PageInfo<User> pageInfo = new PageInfo<>(users);
		System.out.println(pageInfo);
		System.out.println("当前页："+pageInfo.getPageNum());
		System.out.println("总条数："+pageInfo.getTotal());
	
	}
	
	@Test
	public void testInsert() throws Exception {
			User user = new User();
			user.setUsercode(UUID.randomUUID().toString().substring(0, 5));
			user.setUsername("乔峰 ");
			user.setPassword("123");
			userService.insert(user);
	}
	
	@Test
	public void delall() throws Exception {
		List<Integer> id = new ArrayList<Integer>();
		id.add(181);
		id.add(182);
		UserExample example = new UserExample();
		Criteria criteria  = example.createCriteria(); 
		criteria.andIdIn(id);
		int row = userService.deleteByExample(example);
		System.out.println("删除了"+row+"条数据");
		
	}
	
	@Test
	public void selectbyrole() throws Exception {
		User user = new User();
		user.setId(2);
		userService.findByUserToRoleName(user);
		
	}

}
