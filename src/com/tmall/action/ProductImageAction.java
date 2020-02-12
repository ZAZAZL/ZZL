package com.tmall.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;

import com.tmall.pojo.Product;
import com.tmall.pojo.ProductImage;
import com.tmall.util.ImageUtil;


public class ProductImageAction extends ActionResult {

	@Action("admin_productImage_list")//查询产品图片
	public String list() {
		product=(Product) ProductService.get(product.getId());//瞬时状态转换成持久状态
	    ProductImageService.setproductSingleImages(product);//为产品注入sing图片
        ProductImageService.setproductDetailImages(product);//为产品注入detail图片
        productSingleImages=product.getProductSingleImages();
        productDetailImages=product.getProductDetailImages();
        
      	return "listProductImage";
	}
	@Action("admin_productImage_add")//新增产品图片
	public String add(){
		ProductImageService.save(productImage);//首先将前端传过来的图片信息存入数据库
		String folder = "img/";
	    if(ProductImageService.type_single.equals(productImage.getType())){//根据类型找到存放图片的文件夹路径
	        folder +="productSingle";
	    }
	    else{
	        folder +="productDetail";
	    }
	    File  imageFolder= new File(ServletActionContext.getServletContext().getRealPath(folder));//在服务器中获取上一步获取的路径
	    File file = new File(imageFolder,productImage.getId()+".jpg");//在该路径下新建jpg文件
	    String fileName = file.getName();
	    try {
	        FileUtils.copyFile(img, file);//将上传的图片复制给file
	        BufferedImage img = ImageUtil.change2jpg(file);//利用图片的处理类确保图片为JPG文件
	        ImageIO.write(img, "jpg", file); //将图片写入文件夹中        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    if(ProductImageService.type_single.equals(productImage.getType())){//如果上传的图片是single图片，新建两个小图片
	        String imageFolder_small= ServletActionContext.getServletContext().getRealPath("img/productSingle_small");
	        String imageFolder_middle= ServletActionContext.getServletContext().getRealPath("img/productSingle_middle");        
	        File f_small = new File(imageFolder_small, fileName);
	        File f_middle = new File(imageFolder_middle, fileName);
	        f_small.getParentFile().mkdirs();
	        f_middle.getParentFile().mkdirs();
	        ImageUtil.resizeImage(file, 56, 56, f_small);
	        ImageUtil.resizeImage(file, 217, 190, f_middle);
	    } 
		return "listProductImagePage";
	}
	
	@Action("admin_productImage_delete")//删除图片
	public String delete() {
		productImage=(ProductImage) ProductImageService.get(productImage.getId());//瞬时状态转换成持久状态
		ProductImageService.delete(productImage);//删除数据
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
		 
	    return "listProductImagePage";

	}
	
}
