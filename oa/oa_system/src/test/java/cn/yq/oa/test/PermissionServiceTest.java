package cn.yq.oa.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.yq.oa.mapper.CustomPermissionMapper;
import cn.yq.oa.pojo.Permission;
import cn.yq.oa.pojo.PermissionExample;
import cn.yq.oa.service.PermissionService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class PermissionServiceTest {
	
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private CustomPermissionMapper customPermissionMapper;
	@Test
	public void testSelectByExample() {
		
		PermissionExample example = new PermissionExample();
		List<Permission> permissions = permissionService.selectByExample(example );
		
		for (Permission permission : permissions) {
			System.out.println(permission);
		}
	}
	
	
	//根据角色获取对应的权限的id
	@Test
	public void selectrolebyid() throws Exception {
		List<Integer> permissionIds = customPermissionMapper.selectPermissionIdsByRoleId(2);
		System.out.println(permissionIds);
	}

	//根据角色id获取对应的权限表达式
	@Test
	public void se() throws Exception {
		List<String> permissions = customPermissionMapper.selectPermissionsByRoleid(1);
		for (String permission : permissions) {
			System.out.println(permission);
		}
	}
}
