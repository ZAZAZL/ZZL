package com.tmall.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.dao.impl.DAOImpl;
import com.tmall.pojo.User;
import com.tmall.service.UserService;
import com.tmall.util.Page;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService{

	 @Autowired DAOImpl dao;
	@Override
	public boolean isExist(String name) {
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		dc.add(Restrictions.eq("name", name));
		dc.addOrder(Order.desc("id"));
		List l=dao.findByCriteria(dc);
		if(!l.isEmpty()){
			return true;
		}
		return false;
	}

	@Override
	public User get(String name, String password) {
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		dc.add(Restrictions.eq("name",name));
		dc.addOrder(Order.desc("id"));
		List<User> l=dao.findByCriteria(dc);
		if(l.isEmpty()){
			return null;
		}
		else{
			if(l.get(0).getPassword().equals(password)){
				return l.get(0);
			}
			return null;
		}
		
	}
}
