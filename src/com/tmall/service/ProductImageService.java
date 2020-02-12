	

package com.tmall.service;

import com.tmall.pojo.Product;
import com.tmall.pojo.ProductImage;

public interface ProductImageService extends BaseService {
	
	public static final String type_single = "type_single";
	public static final String type_detail = "type_detail";
	
	public void setFirstProdutImage(Product product);
	public void setproductSingleImages(Product product);
	public void setproductDetailImages(Product product);

}
