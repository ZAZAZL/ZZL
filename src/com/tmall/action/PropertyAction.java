package com.tmall.action;

import org.apache.struts2.convention.annotation.Action;

import com.tmall.pojo.Category;
import com.tmall.pojo.Property;
import com.tmall.util.Page;


public class PropertyAction extends ActionResult {
	
	@Action("admin_property_list")//���ݷ��������������
	public String list() {
	    if(page==null)
	        page = new Page();
	    int total = PropertyService.total(category);
	    category=(Category) CategoryService.get(category.getId());
	    page.setTotal(total);
	    page.setParam("&category.id="+category.getId());
	    propertys = PropertyService.list(page,category);
	    return "listProperty";
	}
	
	@Action("admin_property_add")//��������
    public String add() {
        PropertyService.save(property);
        return "listPropertyPage";
    }
	
    @Action("admin_property_delete")//ɾ������
    public String delete() {
    	property=(Property)PropertyService.get(property.getId());
//    	property.setCategory();
        PropertyService.delete(property);
        return "listPropertyPage";
    }
   
    @Action("admin_property_edit")//�༭����
    public String edit() {
    	property=(Property)PropertyService.get(property.getId());
//    	property.setCategory(PropertyService.get(property.getId()));
        return "editProperty";
    }

    @Action("admin_property_update")//�޸�����
    public String update() {
    	
        PropertyService.update(property);
        return "listPropertyPage";
    }
    
    
	
	
}
