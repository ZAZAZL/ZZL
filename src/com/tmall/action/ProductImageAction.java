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

	@Action("admin_productImage_list")//��ѯ��ƷͼƬ
	public String list() {
		product=(Product) ProductService.get(product.getId());//˲ʱ״̬ת���ɳ־�״̬
	    ProductImageService.setproductSingleImages(product);//Ϊ��Ʒע��singͼƬ
        ProductImageService.setproductDetailImages(product);//Ϊ��Ʒע��detailͼƬ
        productSingleImages=product.getProductSingleImages();
        productDetailImages=product.getProductDetailImages();
        
      	return "listProductImage";
	}
	@Action("admin_productImage_add")//������ƷͼƬ
	public String add(){
		ProductImageService.save(productImage);//���Ƚ�ǰ�˴�������ͼƬ��Ϣ�������ݿ�
		String folder = "img/";
	    if(ProductImageService.type_single.equals(productImage.getType())){//���������ҵ����ͼƬ���ļ���·��
	        folder +="productSingle";
	    }
	    else{
	        folder +="productDetail";
	    }
	    File  imageFolder= new File(ServletActionContext.getServletContext().getRealPath(folder));//�ڷ������л�ȡ��һ����ȡ��·��
	    File file = new File(imageFolder,productImage.getId()+".jpg");//�ڸ�·�����½�jpg�ļ�
	    String fileName = file.getName();
	    try {
	        FileUtils.copyFile(img, file);//���ϴ���ͼƬ���Ƹ�file
	        BufferedImage img = ImageUtil.change2jpg(file);//����ͼƬ�Ĵ�����ȷ��ͼƬΪJPG�ļ�
	        ImageIO.write(img, "jpg", file); //��ͼƬд���ļ�����        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    if(ProductImageService.type_single.equals(productImage.getType())){//����ϴ���ͼƬ��singleͼƬ���½�����СͼƬ
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
	
	@Action("admin_productImage_delete")//ɾ��ͼƬ
	public String delete() {
		productImage=(ProductImage) ProductImageService.get(productImage.getId());//˲ʱ״̬ת���ɳ־�״̬
		ProductImageService.delete(productImage);//ɾ������
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
