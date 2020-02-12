package com.tmall.comparator;

import java.util.Comparator;

import com.tmall.pojo.Product;

public class ProductPriceComparator implements Comparator<Product> {

	@Override
	public int compare(Product p1, Product p2) {
		// TODO Auto-generated method stub
		return (int)(p1.getPromotePrice()-p2.getPromotePrice());
	}

}
