package com.tmall.action;

import org.apache.struts2.convention.annotation.Action;

import com.tmall.pojo.Product;
import com.tmall.pojo.PropertyValue;


public class PropertyValueAction extends ActionResult {


	
	@Action("admin_propertyValue_edit")//根据产品查找所有的属性值
    public String edit() {
		product=(Product) ProductService.get(product.getId());
        PropertyValueService.init(product);
        propertyValues = PropertyValueService.listByParent(product);
        return "editPropertyValue";

    }
	
	@Action("admin_propertyValue_update")//修改产品值
    public String update() {
        String value = propertyValue.getValue();
        propertyValue=(PropertyValue) PropertyValueService.get(propertyValue.getId());
        propertyValue.setValue(value);
        PropertyValueService.update(propertyValue);
        return "success.jsp";
    }
	
	
}
