package com.wy.store.modules.login;

import com.wy.store.modules.base.BaseView;

public interface LoginView extends BaseView<LoginPresenter> {
	
	public void login(boolean canLogin);
}
