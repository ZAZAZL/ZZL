

package com.tmall.action;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.math.RandomUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.web.util.HtmlUtils;

import com.tmall.comparator.ProductReviewComparator;
import com.tmall.comparator.ProductAllComparator;
import com.tmall.comparator.ProductDateComparator;
import com.tmall.comparator.ProductPriceComparator;
import com.tmall.comparator.ProductSaleCountComparator;
import com.tmall.pojo.Order;
import com.tmall.pojo.OrderItem;
import com.opensymphony.xwork2.ActionContext;
import com.tmall.pojo.Product;
import com.tmall.pojo.User;

public class ForeAction extends ActionResult {

	
	
	
	@Action("foredoreview")
	public String doreview(){
		order=(Order) OrderService.get(order.getId());
		order.setStatus("finish");
		OrderService.update(order);
		
		String content = review.getContent();
	    content = HtmlUtils.htmlEscape(content);     
		User user =(User) ActionContext.getContext().getSession().get("user");
		review.setUser(user);;
		review.setProduct(product);
		review.setCreateDate(new Date());
		ReviewService.save(review);
		showonly = true;
		return "reviewPage";
	}
	
	@Action("forereview")
	public String review() {
		order=(Order) OrderService.get(order.getId());
		OrderItemService.fill(order);
	    product = order.getOrderItems().get(0).getProduct();
	    reviews = ReviewService.listByParent(product);
	    ProductService.setSaleAndReviewNumber(product);
	    return "review.jsp";        
	}
	
	@Action("foredeleteOrder")
	public String deleteOrder(){
		order=(Order) OrderService.get(order.getId());
	    order.setStatus(OrderService.delete);
	    OrderService.update(order);
	    return "success.jsp";       
	}
	
	@Action("foreorderConfirmed")
	public String orderConfirmed() {
		order=(Order) OrderService.get(order.getId());
	    order.setStatus(OrderService.waitReview);
	    order.setConfirmDate(new Date());
	    OrderService.update(order);
	    return "orderConfirmed.jsp";
	}
	
	@Action("foreconfirmPay")
	public String confirmPay() {
	  order=(Order) OrderService.get(order.getId());
	  OrderItemService.fill(order);
	    return "confirmPay.jsp";        
	}
	@Action("forebought")//查看我的订单页
	public String bought() {
	    User user =(User) ActionContext.getContext().getSession().get("user");
	    orders= OrderService.listByUserWithoutDelete(user);
	    OrderItemService.fill(orders);
	    return "bought.jsp";        
	} 
	@Action("forepayed")//支付成功
	public String payed() {
		order=(Order) OrderService.get(order.getId());
	    order.setStatus(OrderService.waitDelivery);//修改订单状态
	    order.setPayDate(new Date());
	    OrderService.update(order);//更新到数据库
	    return "payed.jsp";     
	}
	
	@Action("forealipay")//服务端跳转到支付页面
	public String alipay(){
		return "alipay.jsp";
	}
	
	@Action("forecreateOrder")//生成订单
	public String createOrder(){
		List<OrderItem> ois=(List<OrderItem>) ActionContext.getContext().getSession().get("orderItems");//从session获取订单项集合
		User user=(User) ActionContext.getContext().getSession().get("user");
		String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) +RandomUtils.nextInt(10000);
	    order.setOrderCode(orderCode);
	    order.setCreateDate(new Date());
	    order.setUser(user);
	    order.setStatus(OrderService.waitPay);//把订单状态设置为等待支付
	    total = OrderService.createOrder(order, ois);//把订单插入到数据库，并设置每个订单项的Order属性，更新到数据库
	    return "alipayPage";
	}
	
	
	@Action("foredeleteOrderItem")//购物车删除订单项
	public String deleteOrderitem(){
		OrderItemService.delete(orderItem);
		return "success.jsp";
		
	}

    @Action("forechangeOrderItem")//购物车修改订单项数量
    public String changeOrderItem() {
        User user =(User) ActionContext.getContext().getSession().get("user");
        List<OrderItem> ois = OrderItemService.listByParent(user);
        for (OrderItem oi : ois) {
            if(oi.getProduct().getId()==product.getId()){
                oi.setNumber(num);
                OrderItemService.update(oi);
                break;
            }     
        }       
        return "success.jsp";
    } 
	
	
	
	@Action("forecart")//查看购物车
	public String cart() {
	    User user =(User) ActionContext.getContext().getSession().get("user");
	    orderItems = OrderItemService.ListOrderitem(user);
	    for (OrderItem orderItem : orderItems) {
	    	ProductImageService.setFirstProdutImage(orderItem.getProduct());
	    }
	    return "cart.jsp";
	}
	@Action("foreaddCart")//加入购物车
	public String addCart(){
		 User u =(User) ActionContext.getContext().getSession().get("user");
		 boolean found = false;
		  List<OrderItem> ois = OrderItemService.ListOrderitem(u);
		  for(OrderItem orderItem:ois){
			  if(product.getId()==orderItem.getProduct().getId()){
				  orderItem.setNumber(orderItem.getNumber()+num);
				  OrderItemService.update(orderItem);
		            found = true;
		            break;
			  }
		  }
		  if(!found){
		        OrderItem oi = new OrderItem();
		        oi.setUser(u);
		        oi.setNumber(num);
		        oi.setProduct(product);
		        OrderItemService.save(oi);
		    }
		return "success.jsp";
		
	}
	
	
	@Action("forebuy")//结算页面
	public String buy() {
	    orderItems = new ArrayList<>();
	    for (int oiid : oiids) {
	        OrderItem oi= (OrderItem) OrderItemService.get(oiid);
	        total +=oi.getProduct().getPromotePrice()*oi.getNumber();//总金额
	        orderItems.add(oi);
	        ProductImageService.setFirstProdutImage(oi.getProduct());
	    }
	     
	    ActionContext.getContext().getSession().put("orderItems", orderItems);
	    return "buy.jsp";
	}
	@Action("forebuyone")//立即购买
	public String buyone(){
		
		 User u =(User) ActionContext.getContext().getSession().get("user");//获取用户
		 boolean found = false;
		  List<OrderItem> ois = OrderItemService.listByParent(u);//获取用户的所有订单项
		  for(OrderItem orderItem:ois){//如果有次订单项便修改其数量
			  if(product.getId()==orderItem.getProduct().getId()){
				  orderItem.setNumber(orderItem.getNumber()+num);
				  OrderItemService.update(orderItem);
		            found = true;
		            oiid = orderItem.getId();
		            break;
			  }
		  }
		  if(!found){//如果没有创建新订单项
		        OrderItem oi = new OrderItem();
		        oi.setUser(u);
		        oi.setNumber(num);
		        oi.setProduct(product);
		        OrderItemService.save(oi);
		        oiid = oi.getId();
		    }
		return "buyPage";
		
	}
	@Action("foresearch")//搜索
	public String search(){
	    products=ProductService.search(keyword, 0, 20);//通过前端传过来的keyword搜索20个类似的产品
		ProductService.setSaleAndReviewNumber(products);//为所有产品设置价格和销售数量
		for(Product product:products){
			ProductImageService.setFirstProdutImage(product);//设置产品的首张图片
		}
		return "searchResult.jsp";
		
	}
	
	
	@Action("forecategory")//分类查看
	public String category(){
		ProductService.fill(category);//为此分类注入信息
		ProductService.setSaleAndReviewNumber(category.getProducts());//为产品注入信息
		if(sort!=null){//通过5个比较器查看产品
			switch(sort){
			case "sort":
				Collections.sort(category.getProducts(),new ProductReviewComparator());
			case "date" :
	            Collections.sort(category.getProducts(),new ProductDateComparator());
	            break;
	             
	        case "saleCount" :
	            Collections.sort(category.getProducts(),new ProductSaleCountComparator());
	            break;
	             
	        case "price":
	            Collections.sort(category.getProducts(),new ProductPriceComparator());
	            break;
	             
	        case "all":
	            Collections.sort(category.getProducts(),new ProductAllComparator());
	            break;
			};
		
			
		}
		return "category.jsp"; 
	}
	
	
	
	
	@Action("foreloginAjax")//ajax登录
	public String loginAjax() {

	    user.setName(HtmlUtils.htmlEscape(user.getName()));
	    User user_session = UserService.get(user.getName(),user.getPassword());
	    if(null==user_session)
	        return "fail.jsp"; 

	    ActionContext.getContext().getSession().put("user", user_session);
	    return "success.jsp";       
	}
	@Action("forecheckLogin")//ajax验证是否登录
	public String checkLogin() {
	    User u =(User) ActionContext.getContext().getSession().get("user");
	    if(null==u)
			return "fail.jsp";
		else
			return "success.jsp";
	}
	@Action("foreproduct")//产品页
	public String product(){
		ProductService.fill(product);//为点击的产品注入图片等信息
		propertyValues=PropertyValueService.listByParent(product);//查找当前产品的各种属性信息
		reviews=ReviewService.listByParent(product);//获取此产品的所有评论
		ProductService.setSaleAndReviewNumber(product);
		return "product.jsp";
		
	}
	
	@Action("forelogout")//退出
	public String logout() {
		ActionContext.getContext().getSession().remove("user");//从session中删除用户
		return "homePage"; 	//返回首页
	}
	@Action("forelogin")//登陆
	public String login(){
		user.setName(HtmlUtils.htmlEscape(user.getName()));//将前端数据转译斌赋值
		User user_session=UserService.get(user.getName(), user.getPassword());//判断数据库中是否存在改用户
		if(null==user_session){
	        msg= "账号密码错误";
	        return "login.jsp"; //将错误信息返回登陆界面
	    }
	    ActionContext.getContext().getSession().put("user", user_session);//登入成功并将用户存入session
	    return "homePage"; 
	}
	

	@Action("foreregister")//注册
	public String register() {
		user.setName(HtmlUtils.htmlEscape(user.getName()));//将前端数据转译并赋值
		boolean exist = UserService.isExist(user.getName());//判断数据库中是否已存在
		
		 if(exist){
			 msg = "用户名已经被使用,不能使用";
			 return "register.jsp";  //将错误信息返回注册界面
			
		 }
		 UserService.save(user);
		 return "registerSuccessPage";
	}	
	
    @Action("forehome")//首页
    public String home() {
        categorys = CategoryService.list();//获取所有分类
        ProductService.fill(categorys);//为每个分类注入所有product
        ProductService.fillByRow(categorys);//实现List<List<Product>>
        Map m = ActionContext.getContext().getSession();
        m.put("categorys", categorys);//将category存入session
        return "home.jsp";
    }

    
    
    
	
	
}