package com.tmall.service;

import java.util.List;

import com.tmall.pojo.User;
import com.tmall.util.Page;

public interface UserService extends BaseService {
	boolean isExist(String name);
	User get(String name,String password);
}
