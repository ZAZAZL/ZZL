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


    @Action("admin_category_update")//�޸ķ���
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
    @Action("admin_category_edit")//�༭������תҳ��
    public String edit() {
        category = (Category) CategoryService.get(category.getId());
        return "editCategory";
    }
    
    @Action("admin_category_delete")//ɾ������
    public String delete() {
    	List<Product> products=ProductService.listByParent(category);//�ҵ��÷����µ����в�Ʒ
    	for(Product product:products){//�������в�Ʒ
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
    		ProductService.delete(product);//ɾ��ÿ����Ʒ����Ϣ
    	}
    	List<Property> propertys= PropertyService.listByParent(category);//�ҵ��÷������������
    	for(Property property:propertys){//ɾ����������
    		PropertyService.delete(property);
    	}
        CategoryService.delete(category);//ɾ�����з���
        File  imageFolder= new File(ServletActionContext.getServletContext().getRealPath("img/category"));//ɾ������ͼƬ
        File file = new File(imageFolder,category.getId()+".jpg");
        if(file.exists()){
        	file.delete();
		 }
        return "listCategoryPage";
    }   
    
    @Action("admin_category_add")//��������
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
    
    
    @Action("admin_category_list")//����ȫ�����ಢ��ҳ
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
 

