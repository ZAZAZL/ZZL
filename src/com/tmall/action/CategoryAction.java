package com.tmall.action;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;

import com.tmall.pojo.Category;
import com.tmall.pojo.Product;
import com.tmall.pojo.ProductImage;
import com.tmall.pojo.Property;
import com.tmall.pojo.PropertyValue;
import com.tmall.util.ImageUtil;
import com.tmall.util.Page;

public class CategoryAction extends ActionResult {


    @Action("admin_category_update")//修改分类
    public String update(){
    	CategoryService.update(category);
    	 if(null!=img){
    	File  imageFolder= new File(ServletActionContext.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder,category.getId()+".jpg");
        try {
            FileUtils.copyFile(img, file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);            
        } catch (IOException e) {
            e.printStackTrace();
        }
    	 }
    	return "listCategoryPage";
    }
    @Action("admin_category_edit")//编辑分类跳转页面
    public String edit() {
        category = (Category) CategoryService.get(category.getId());
        return "editCategory";
    }
    
    @Action("admin_category_delete")//删除分类
    public String delete() {
    	List<Product> products=ProductService.listByParent(category);//找到该分类下的所有产品
    	for(Product product:products){//遍历所有产品
    		List<ProductImage> productimages=ProductImageService.listByParent(product);//找到该产品下的所有图片
    		List<PropertyValue> propertyvalues=PropertyValueService.listByParent(product);//找到该产品的所有属性值
    		for(ProductImage productImage:productimages)//在数据库中删除每张图片的记录和文件夹中的图片
    		{
    			ProductImageService.delete(productImage);
    			if(productImage.getType().equals("type_single")){
    				File  imageSingle= new File(ServletActionContext.getServletContext().getRealPath("img/productSingle"));
    				File  imageFolder_small= new File(ServletActionContext.getServletContext().getRealPath("img/productSingle_small"));
    				File  imageFolder_middle= new File(ServletActionContext.getServletContext().getRealPath("img/productSingle_middle"));
    				 File Singlefile = new File(imageSingle,productImage.getId()+".jpg");
    				 File smallfile=new File(imageFolder_small,productImage.getId()+".jpg");
    				 File middlefile=new File(imageFolder_middle,productImage.getId()+".jpg");
    				 if(Singlefile.exists()){
    					 Singlefile.delete();
    				 }
    				 if(smallfile.exists()){
    					 smallfile.delete();
    				 }
    				 if(middlefile.exists()){
    					 middlefile.delete();
    				 }
    			}
    			else{
    				File  image= new File(ServletActionContext.getServletContext().getRealPath("img/productDetail"));
    				File file=new File(image,productImage.getId()+".jpg");
    				if(file.exists()){
    					file.delete();
    				}
    			}
    		}
    		for(PropertyValue propertyvalue:propertyvalues)//删除每个产品的所有属性值
    		{
    			PropertyValueService.delete(propertyvalue);
    		}
    		ProductService.delete(product);//删除每个产品的信息
    	}
    	List<Property> propertys= PropertyService.listByParent(category);//找到该分类的所有属性
    	for(Property property:propertys){//删除所有属性
    		PropertyService.delete(property);
    	}
        CategoryService.delete(category);//删除所有分类
        File  imageFolder= new File(ServletActionContext.getServletContext().getRealPath("img/category"));//删除分类图片
        File file = new File(imageFolder,category.getId()+".jpg");
        if(file.exists()){
        	file.delete();
		 }
        return "listCategoryPage";
    }   
    
    @Action("admin_category_add")//新增分类
    public String add() {
        CategoryService.save(category);
        File  imageFolder= new File(ServletActionContext.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder,category.getId()+".jpg");
        try {
            FileUtils.copyFile(img, file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "listCategoryPage";
    }  
    
    
    @Action("admin_category_list")//查找全部分类并分页
    public String list() {
    	if(page==null)
            page = new Page();
        int total = CategoryService.total();
        page.setTotal(total);
        categorys = CategoryService.listByPage(page);
        return "listCategory";
    }
    @Action("admin")
    public String admin(){
    	return "admin";
    }

   
}
 

