package com.tmall.action;

import org.apache.struts2.convention.annotation.Action;

import com.tmall.pojo.Product;
import com.tmall.pojo.PropertyValue;


public class PropertyValueAction extends ActionResult {


	
	@Action("admin_propertyValue_edit")//���ݲ�Ʒ�������е�����ֵ
    public String edit() {
		product=(Product) ProductService.get(product.getId());
        PropertyValueService.init(product);
        propertyValues = PropertyValueService.listByParent(product);
        return "editPropertyValue";

    }
	
	@Action("admin_propertyValue_update")//�޸Ĳ�Ʒֵ
    public String update() {
        String value = propertyValue.getValue();
        propertyValue=(PropertyValue) PropertyValueService.get(propertyValue.getId());
        propertyValue.setValue(value);
        PropertyValueService.update(propertyValue);
        return "success.jsp";
    }
	
	
}
