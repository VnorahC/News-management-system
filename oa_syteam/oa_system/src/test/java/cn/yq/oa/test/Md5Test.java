package cn.yq.oa.test;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

public class Md5Test {
	/*
	 * 加密
	 */
	@Test
	public void testName() throws Exception {
		//方式一
		String source = "admin";//原密码
		Object salt = "query";
		Integer hashIterations = 2;//散列次数 md5(md5('admin+salt'))
		Md5Hash md5Hash = new Md5Hash(source,salt,hashIterations);
		System.out.println(md5Hash.toString());
		
		//方式二
		SimpleHash simpleHash = new SimpleHash("md5",source,salt,hashIterations);
		System.out.println(simpleHash.toString());
	}
}
