package com.tmall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.pojo.ProductImage;
import com.tmall.dao.impl.DAOImpl;
import com.tmall.pojo.Product;
import com.tmall.service.ProductImageService;


@Service
public class ProductImageServiceImpl extends BaseServiceImpl implements ProductImageService{

	 @Autowired DAOImpl dao;
	@Override
	public void setFirstProdutImage(Product product) {
		// TODO Auto-generated method stub
		if(product.getFirstProductImage()!=null){
			return;
		}
		else{
			List<ProductImage> pis;
			DetachedCriteria dc = DetachedCriteria.forClass(ProductImage.class);
	        dc.addOrder(Order.desc("id"));
	        pis= dao.findByCriteria(dc);
	        for (ProductImage productImage : pis) {
				if(productImage.getProduct().getId()==product.getId()&&productImage.getType().equals("type_single"))
				{
					product.setFirstProductImage(productImage);
					break;
				}
			}
		}
	}
	@Override
	public void setproductSingleImages(Product product) {
		// TODO Auto-generated method stub
		List<ProductImage> pis;
		List<ProductImage> singleimage=new ArrayList<>();
		DetachedCriteria dc = DetachedCriteria.forClass(ProductImage.class);
        dc.addOrder(Order.desc("id"));
        pis=dao.findByCriteria(dc);
        for (ProductImage productImage : pis) {
			if(productImage.getProduct().getId()==product.getId()&&productImage.getType().equals("type_single"))
			{
				singleimage.add(productImage);
			}
		}
        product.setProductSingleImages(singleimage);
        }
	@Override
	public void setproductDetailImages(Product product) {
		// TODO Auto-generated method stub
		List<ProductImage> pis;
		List<ProductImage> detaillimage=new ArrayList<>();
		DetachedCriteria dc = DetachedCriteria.forClass(ProductImage.class);
        dc.addOrder(Order.desc("id"));
        pis=dao.findByCriteria(dc);
        for (ProductImage productImage : pis) {
			if(productImage.getProduct().getId()==product.getId()&&productImage.getType().equals("type_detail"))
			{
				detaillimage.add(productImage);
			}
		}
        product.setProductDetailImages(detaillimage);
	}

}
