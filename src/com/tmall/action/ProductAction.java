package com.tmall.action;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;

import com.tmall.pojo.Category;
import com.tmall.pojo.Product;
import com.tmall.pojo.ProductImage;
import com.tmall.pojo.PropertyValue;
import com.tmall.util.Page;

public class ProductAction extends ActionResult{
	
	
	

	
	 @Action("admin_product_list")//根据分类，分页查询所有产品
	    public String list(){
		 if(page==null){
			 page=new Page();
		 }
	    	ProductService.fill(category);//为该分类注入所有产品信息
	    	products=category.getProducts();
	    	page.setTotal(products.size());
	    	page.setParam("&category.id="+category.getId());
	    	products=ProductService.list(page, category);
	    	category=(Category) CategoryService.get(category.getId());//瞬时状态转换成持久状态
	    	for(Product product:products){
	    		ProductImageService.setFirstProdutImage(product);
	    	}
	    	return "listProduct";
	    }

	 @Action("admin_product_add")//新增产品

	    public String add() {
	        product.setCreateDate(new Date());
	        ProductService.save(product);
	        return "listProductPage";
	    }

	  
	    @Action("admin_product_delete")//删除产品
	    public String delete() {
	    	product=(Product) ProductService.get(product.getId());
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
	    	
	        ProductService.delete(product);
	        return "listProductPage";

	    }
	    @Action("admin_product_edit")//编辑产品
	    public String edit() {
	    	product=(Product) ProductService.get(product.getId());//瞬时状态转换成持久状态
	        return "editProduct";
	    }

	    @Action("admin_product_update")//修改产品
	    public String update() {
	        Product productFromDB= (Product)ProductService.get(product.getId());
	        product.setCreateDate(productFromDB.getCreateDate());
	        ProductService.update(product);
	        return "listProductPage";
	    }
	 
	 



	 
	 
}
