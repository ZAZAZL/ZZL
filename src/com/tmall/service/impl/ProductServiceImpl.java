package com.tmall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.dao.impl.DAOImpl;
import com.tmall.pojo.Category;
import com.tmall.pojo.Product;
import com.tmall.service.ProductImageService;
import com.tmall.service.ProductService;
import com.tmall.service.ReviewService;
import com.tmall.service.OrderItemService;
import com.tmall.util.Page;




@Service
public class ProductServiceImpl extends BaseServiceImpl implements ProductService  {

	 @Autowired DAOImpl dao;
	 @Autowired ProductImageService productImageService;
	 @Autowired OrderItemService ordeItemService;
	 @Autowired ReviewService reviewservice;
	@Override
	public void fill(List<Category> categorys) {
        for (Category category : categorys) {
            fill(category);
        }
    }

	@Override
	public void fill(Category category) {
		// TODO Auto-generated method stub
		List<Product> products;
		List<Product> productss=new ArrayList<>();
		 DetachedCriteria dc = DetachedCriteria.forClass(Product.class);
	        dc.addOrder(Order.desc("id"));
	        products= dao.findByCriteria(dc);
	        for (Product product : products) {
	           if(product.getCategory().getId()==category.getId()){
	        	   productImageService.setFirstProdutImage(product);
	        	  productss.add(product);
	           }
	        }
	        category.setProducts(productss);
	}

	@Override
	public void fillByRow(List<Category> categorys) {
		// TODO Auto-generated method stub
		int productNumberEachRow = 8;
		for (Category category : categorys) {
			List<Product> products =  category.getProducts();
			List<List<Product>> productsByRow =  new ArrayList<>();
			for (int i = 0; i < products.size(); i+=productNumberEachRow) {
				int size = i+productNumberEachRow;
				size= size>products.size()?products.size():size;
				List<Product> productsOfEachRow =products.subList(i, size);
				productsByRow.add(productsOfEachRow);
			}
			category.setProductsByRow(productsByRow);
		}
	}

	@Override
	public void fill(Product product) {
		// TODO Auto-generated method stub
		List<Product> products;
		DetachedCriteria dc = DetachedCriteria.forClass(Product.class);
		    dc.add(Restrictions.eq("id", product.getId()));
	        dc.addOrder(Order.desc("id"));
	        products= dao.findByCriteria(dc);
//	        product=products.get(0);
	        product.setCategory(products.get(0).getCategory());
	        product.setName(products.get(0).getName());
	        product.setSubTitle(products.get(0).getSubTitle());
	        product.setOriginalPrice(products.get(0).getOriginalPrice());
	        product.setPromotePrice(products.get(0).getPromotePrice());
	        product.setStock(products.get(0).getStock());
	        product.setCreateDate(products.get(0).getCreateDate());
	        productImageService.setFirstProdutImage(product);
			productImageService.setproductSingleImages(product);
			productImageService.setproductDetailImages(product);
	}

	@Override
	public void setSaleAndReviewNumber(Product product) {
		// TODO Auto-generated method stub
	int totalSale=ordeItemService.total(product);
	int reviewtotal=reviewservice.total(product);
	product.setSaleCount(totalSale);
	product.setReviewCount(reviewtotal);
	}

	@Override
	public void setSaleAndReviewNumber(List<Product> products) {
		// TODO Auto-generated method stub
		for (Product product : products) {
            setSaleAndReviewNumber(product);
        }
	}

	@Override
	public List<Product> search(String keyword, int start, int count) {
		// TODO Auto-generated method stub
		 DetachedCriteria dc = DetachedCriteria.forClass(Product.class);
		 dc.add(Restrictions.like("name", "%"+keyword+"%"));
	        return dao.findByCriteria(dc,start,count);
	}
}
