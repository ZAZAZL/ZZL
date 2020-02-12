package com.tmall.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.tmall.dao.impl.DAOImpl;
import com.tmall.pojo.Order;
import com.tmall.pojo.OrderItem;
import com.tmall.pojo.User;
import com.tmall.service.OrderService;
import com.tmall.service.OrderItemService;
import com.tmall.util.Page;

@Service
public class OrderServiceImpl extends BaseServiceImpl implements OrderService {

	@Autowired
	DAOImpl dao;
	@Autowired
	OrderItemService orderitemservice;
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public float createOrder(Order order, List<OrderItem> ois) {
		// TODO Auto-generated method stub
		dao.save(order);
		float total =0;
		for(OrderItem orderitem:ois){
			orderitem.setOrder(order);
			orderitemservice.update(orderitem);
			total+=orderitem.getProduct().getPromotePrice()*orderitem.getNumber();
		}
		return total;
	}
	@Override
	public List<Order> listByUserWithoutDelete(User user){
        DetachedCriteria dc = DetachedCriteria.forClass(Order.class);
        dc.add(Restrictions.eq("user", user));
        dc.add(Restrictions.ne("status", "delete"));
        return dao.findByCriteria(dc);
    }

}
