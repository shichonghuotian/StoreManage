package com.wy.store.modules.login;

import com.wy.store.modules.base.BaseView;

//用户登陆页面
public interface LoginView extends BaseView<LoginPresenter> {
	
	public void login(boolean canLogin);
}
