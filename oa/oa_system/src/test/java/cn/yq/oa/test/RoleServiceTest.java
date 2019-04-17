package cn.yq.oa.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.yq.oa.pojo.Role;
import cn.yq.oa.pojo.RoleExample;
import cn.yq.oa.pojo.RoleExample.Criteria;
import cn.yq.oa.service.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class RoleServiceTest {

	@Autowired
	private RoleService roleService;
	
	@Test
	public void insertrole() throws Exception {
		
			Role role = new Role();
			role.setName("开发部");
			role.setRemark("负责公司项目的开发");
			role.setAvailable(1);
			roleService.insert(role);
		
		
		
	}
	@Test
	public void delerole() throws Exception {
		int row = roleService.deleteByPrimaryKey(4);
		System.out.println("删除了"+row+"数据");
		
	}
	
	@Test
	public void deleRoleAllid() throws Exception {
		
		List<Integer> id = new ArrayList<Integer>();
		id.add(26);
		id.add(28);
		RoleExample example = new RoleExample();
		Criteria criteria = example.createCriteria();
		criteria.andRoleIdIn(id);
		int row = roleService.deleteByExample(example);
		System.out.println("删除了"+row+"条数据");
		
	}

}
