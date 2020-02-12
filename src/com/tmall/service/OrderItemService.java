package com.tmall.service;

import java.util.List;

import com.tmall.pojo.Order;
import com.tmall.pojo.OrderItem;
import com.tmall.pojo.Product;
import com.tmall.pojo.User;

public interface OrderItemService  extends BaseService{

	public void fill(List<Order> orders);
	public void fill(Order order);
	public List<OrderItem> ListOrderitem(User user);
}
