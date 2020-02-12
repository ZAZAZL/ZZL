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
	
	
	

	
	 @Action("admin_product_list")//���ݷ��࣬��ҳ��ѯ���в�Ʒ
	    public String list(){
		 if(page==null){
			 page=new Page();
		 }
	    	ProductService.fill(category);//Ϊ�÷���ע�����в�Ʒ��Ϣ
	    	products=category.getProducts();
	    	page.setTotal(products.size());
	    	page.setParam("&category.id="+category.getId());
	    	products=ProductService.list(page, category);
	    	category=(Category) CategoryService.get(category.getId());//˲ʱ״̬ת���ɳ־�״̬
	    	for(Product product:products){
	    		ProductImageService.setFirstProdutImage(product);
	    	}
	    	return "listProduct";
	    }

	 @Action("admin_product_add")//������Ʒ

	    public String add() {
	        product.setCreateDate(new Date());
	        ProductService.save(product);
	        return "listProductPage";
	    }

	  
	    @Action("admin_product_delete")//ɾ����Ʒ
	    public String delete() {
	    	product=(Product) ProductService.get(product.getId());
	    	List<ProductImage> productimages=ProductImageService.listByParent(product);//�ҵ��ò�Ʒ�µ�����ͼƬ
    		List<PropertyValue> propertyvalues=PropertyValueService.listByParent(product);//�ҵ��ò�Ʒ����������ֵ
    		for(ProductImage productImage:productimages)//�����ݿ���ɾ��ÿ��ͼƬ�ļ�¼���ļ����е�ͼƬ
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
    		for(PropertyValue propertyvalue:propertyvalues)//ɾ��ÿ����Ʒ����������ֵ
    		{
    			PropertyValueService.delete(propertyvalue);
    		}
	    	
	        ProductService.delete(product);
	        return "listProductPage";

	    }
	    @Action("admin_product_edit")//�༭��Ʒ
	    public String edit() {
	    	product=(Product) ProductService.get(product.getId());//˲ʱ״̬ת���ɳ־�״̬
	        return "editProduct";
	    }

	    @Action("admin_product_update")//�޸Ĳ�Ʒ
	    public String update() {
	        Product productFromDB= (Product)ProductService.get(product.getId());
	        product.setCreateDate(productFromDB.getCreateDate());
	        ProductService.update(product);
	        return "listProductPage";
	    }
	 
	 



	 
	 
}
