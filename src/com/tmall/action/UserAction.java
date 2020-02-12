package com.tmall.action;

import org.apache.struts2.convention.annotation.Action;
import com.tmall.util.Page;


public class UserAction extends ActionResult {
	
	@Action("admin_user_list")//查找所有用户
	public String list() {
		if(page==null)
			page = new Page();
		int total = UserService.total();
		page.setTotal(total);
		users = UserService.listByPage(page);
		return "listUser";
	}
}

