package com.tmall.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.dao.impl.DAOImpl;
import com.tmall.pojo.OrderItem;
import com.tmall.pojo.Product;
import com.tmall.pojo.User;
import com.tmall.service.ProductImageService;
import com.tmall.service.OrderItemService;

@Service
public class OrderItemServiceImpl extends BaseServiceImpl  implements OrderItemService {

	@Autowired DAOImpl dao;
	@Autowired ProductImageService productImageservice;

	@Override
	public void fill(List<com.tmall.pojo.Order> orders) {
		// TODO Auto-generated method stub
		for (com.tmall.pojo.Order order : orders) 
			fill(order);
	}
	@Override
	public void fill(com.tmall.pojo.Order order) {
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(OrderItem.class);
		dc.add(Restrictions.eq("order",order));
		dc.addOrder(Order.desc("id"));
		List<OrderItem> bb=dao.findByCriteria(dc);
		float total = 0;
		int totalNumber = 0;			
		for (OrderItem oi :bb) {
			total+=oi.getNumber()*oi.getProduct().getPromotePrice();
			totalNumber+=oi.getNumber();
			productImageservice.setFirstProdutImage(oi.getProduct());
		}
		order.setTotal(total);
		order.setOrderItems(bb);
		order.setTotalNumber(totalNumber);
	}
	@Override
	public List<OrderItem> ListOrderitem(User user) {
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(OrderItem.class);
		dc.add(Restrictions.eq("user",user));
		dc.add(Restrictions.isNull("order"));
		dc.addOrder(Order.desc("id"));
		return dao.findByCriteria(dc);
	}

}
