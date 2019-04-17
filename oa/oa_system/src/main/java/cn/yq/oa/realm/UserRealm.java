package cn.yq.oa.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import cn.yq.oa.pojo.User;
import cn.yq.oa.service.PermissionService;
import cn.yq.oa.service.UserService;

public class UserRealm extends AuthorizingRealm{
	
	
	//注入userService
	@Autowired
	private UserService userService;
	
	@Autowired
	private PermissionService permissionService;
	

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		System.out.println("UserRealm.doGetAuthorizationInfo()");
		/*
		 * 授权思路
		 * 1.根据当前用户的角色id查询出角色权限表中的所有的对应的权限id
		 * 
		 * 2.在根据所有的权限id查询出权限表中，对应的所有的权限表达式
		 * 
		 * 3.将这些权限赋给 AuthorizationInfo 对象
		 */
		//1.获取当前认证的用户信息
		User user = (User) principals.getPrimaryPrincipal();
		
		//2.查询出当前用户对应的角色所拥有的全部权限
		List<String> permissions = permissionService.selectPermissionsByRoleid(user.getRoleId());
		for (String permission : permissions) {
			System.out.println(permission);
		}
		
		//3.创建授权对象
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		
		//4.把当前身份的所有权限设置授权对象中取
		for (String permission : permissions) {
			if(permission!=null) {
				authorizationInfo.addStringPermission(permission);
			}
		}
		
		return authorizationInfo;
	}
	//认证方法
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("UserRealm.doGetAuthenticationInfo()");
		/*
		 *认证思路：
		 *1.先根据当前的身份（账号）去数据库中查询出对应的User对象 
		 * 如果返回结果为null,说明数据库没有此账号
		 * 如果有：返回user对象，user对象中封装此用户的所有信息
		 *2.在进行认证，把当前用户对象对应的密码传递给认证对象
		 * 最终交给shiro底层认证
		 */
		//1.获取身份信息(账号)
		String usercode = (String)token.getPrincipal();
		//*先根据当前的身份（账号）去数据库中查询出对应的User对象 
		User user = userService.selectByUserCode(usercode);
		
		//UnknownAccountException异常
		if(user == null) {
			return null;
		}
		
		//2.创建简单认证对象
		//没有加密，直接明文密码认证
		//SimpleAuthenticationInfo authenticationInfo = new  SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
		
		//使用加密密码认证
		Object principal = user;//身份
		Object hashedCredentials = user.getPassword();//数据库查询的user对象的密码
		ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());//用户数据库中的盐
		
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(principal, hashedCredentials, credentialsSalt, this.getName());
		return authenticationInfo;
	}

}
