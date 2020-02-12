package com.tmall.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.dao.impl.DAOImpl;
import com.tmall.pojo.Product;
import com.tmall.pojo.Property;
import com.tmall.pojo.PropertyValue;
import com.tmall.service.PropertyService;
import com.tmall.service.PropertyValueService;

@Service
public class PropertyValueServiceImpl extends BaseServiceImpl  implements PropertyValueService {

	@Autowired DAOImpl dao;
	@Autowired PropertyService propertyservice;
	
	@Override
	public void init(Product product) {
		// TODO Auto-generated method stub
		List<Property> propertys=propertyservice.listByParent(product.getCategory());
		for(Property property:propertys){
			 DetachedCriteria dc = DetachedCriteria.forClass(PropertyValue.class);
			 dc.add(Restrictions.eq("product", product));
			 dc.add(Restrictions.eq("property", property));
			 dc.addOrder(Order.desc("id"));
			 List<PropertyValue> propertyValues=dao.findByCriteria(dc);
			 if(propertyValues.isEmpty()){
				 PropertyValue propertyValue = new PropertyValue();
	             propertyValue.setProduct(product);
	             propertyValue.setProperty(property);
	             dao.save(propertyValue);
			 }
		}
	}
}
