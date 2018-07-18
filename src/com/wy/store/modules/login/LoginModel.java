package com.wy.store.modules.login;

import com.wy.store.bean.User;

//可以考虑使用fat model，这个看自己的意思了
public class LoginModel {

	public boolean hasUser(String name,String pwd) {
		
		User user = new User();
		
		//这个地方就是判断一些简单的内容，可以从网络获取，也可以直接从本地数据库获取
		
		if(name.equals("1") ) {
			
			return true;
		}
		
		return false;
 	}
}
