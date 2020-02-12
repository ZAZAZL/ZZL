package com.tmall.action;

import java.io.File;
import java.util.List;

import com.tmall.pojo.Category;
import com.tmall.pojo.Order;
import com.tmall.pojo.OrderItem;
import com.tmall.pojo.Product;
import com.tmall.pojo.ProductImage;
import com.tmall.pojo.Property;
import com.tmall.pojo.PropertyValue;
import com.tmall.pojo.Review;
import com.tmall.pojo.User;
import com.tmall.util.Page;

public class ActionPojo {
	Category category;
	Order order;
	OrderItem orderItem;
	ProductImage productImage;
	Property property;
	PropertyValue propertyValue;
	Product product;
	Review review;
	User user;
    Page page;
    File img;
   
	
	List<Category> categorys;
	List<Order> orders;
	List<Product> products;
	List<PropertyValue> propertyValues;
	List<Review> reviews;
	List<OrderItem> orderItems;
	List<ProductImage> productImages;
	List<ProductImage> productSingleImages;
	List<ProductImage> productDetailImages;
	List<User> users;
	List<Property> propertys;
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public OrderItem getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}
	public ProductImage getProductImage() {
		return productImage;
	}
	public void setProductImage(ProductImage productImage) {
		this.productImage = productImage;
	}
	public Property getProperty() {
		return property;
	}
	public void setProperty(Property property) {
		this.property = property;
	}
	public PropertyValue getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(PropertyValue propertyValue) {
		this.propertyValue = propertyValue;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Review getReview() {
		return review;
	}
	public void setReview(Review review) {
		this.review = review;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Category> getCategorys() {
		return categorys;
	}
	public void setCategorys(List<Category> categorys) {
		this.categorys = categorys;
	}
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public List<PropertyValue> getPropertyValues() {
		return propertyValues;
	}
	public void setPropertyValues(List<PropertyValue> propertyValues) {
		this.propertyValues = propertyValues;
	}
	public List<Review> getReviews() {
		return reviews;
	}
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public List<ProductImage> getProductImages() {
		return productImages;
	}
	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public List<Property> getPropertys() {
		return propertys;
	}
	public void setPropertys(List<Property> propertys) {
		this.propertys = propertys;
	}
    public Page getPage() {
	    return page;
    }
    public void setPage(Page page) {
	    this.page = page;
    }
	public File getImg() {
		return img;
	}
	public void setImg(File img) {
		this.img = img;
	}
	public List<ProductImage> getProductSingleImages() {
		return productSingleImages;
	}
	public void setProductSingleImages(List<ProductImage> productSingleImages) {
		this.productSingleImages = productSingleImages;
	}
	public List<ProductImage> getProductDetailImages() {
		return productDetailImages;
	}
	public void setProductDetailImages(List<ProductImage> productDetailImages) {
		this.productDetailImages = productDetailImages;
	}
	
}
