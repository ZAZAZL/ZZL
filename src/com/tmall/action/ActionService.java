package com.tmall.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.tmall.service.CategoryService;
import com.tmall.service.OrderService;
import com.tmall.service.ProductImageService;
import com.tmall.service.ProductService;
import com.tmall.service.PropertyService;
import com.tmall.service.PropertyValueService;
import com.tmall.service.ReviewService;
import com.tmall.service.UserService;
import com.tmall.service.OrderItemService;

public class ActionService extends ActionPojo {

	@Autowired
    CategoryService CategoryService;
	@Autowired
	ProductService ProductService;
	@Autowired
	UserService UserService;
	@Autowired
	PropertyValueService PropertyValueService;
	@Autowired
	ReviewService ReviewService;
	@Autowired
	ProductImageService ProductImageService;
	@Autowired
	OrderItemService OrderItemService;
	@Autowired
	OrderService OrderService;
	@Autowired
	PropertyService PropertyService;

	
	
}
