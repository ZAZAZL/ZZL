package com.tmall.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.dao.impl.DAOImpl;
import com.tmall.pojo.Product;
import com.tmall.pojo.Review;
import com.tmall.service.ReviewService;

@Service
public class ReviewServiceImpl extends BaseServiceImpl implements ReviewService{

	@Autowired DAOImpl dao;

}
