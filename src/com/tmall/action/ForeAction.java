

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
	@Action("forebought")//�鿴�ҵĶ���ҳ
	public String bought() {
	    User user =(User) ActionContext.getContext().getSession().get("user");
	    orders= OrderService.listByUserWithoutDelete(user);
	    OrderItemService.fill(orders);
	    return "bought.jsp";        
	} 
	@Action("forepayed")//֧���ɹ�
	public String payed() {
		order=(Order) OrderService.get(order.getId());
	    order.setStatus(OrderService.waitDelivery);//�޸Ķ���״̬
	    order.setPayDate(new Date());
	    OrderService.update(order);//���µ����ݿ�
	    return "payed.jsp";     
	}
	
	@Action("forealipay")//�������ת��֧��ҳ��
	public String alipay(){
		return "alipay.jsp";
	}
	
	@Action("forecreateOrder")//���ɶ���
	public String createOrder(){
		List<OrderItem> ois=(List<OrderItem>) ActionContext.getContext().getSession().get("orderItems");//��session��ȡ�������
		User user=(User) ActionContext.getContext().getSession().get("user");
		String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) +RandomUtils.nextInt(10000);
	    order.setOrderCode(orderCode);
	    order.setCreateDate(new Date());
	    order.setUser(user);
	    order.setStatus(OrderService.waitPay);//�Ѷ���״̬����Ϊ�ȴ�֧��
	    total = OrderService.createOrder(order, ois);//�Ѷ������뵽���ݿ⣬������ÿ���������Order���ԣ����µ����ݿ�
	    return "alipayPage";
	}
	
	
	@Action("foredeleteOrderItem")//���ﳵɾ��������
	public String deleteOrderitem(){
		OrderItemService.delete(orderItem);
		return "success.jsp";
		
	}

    @Action("forechangeOrderItem")//���ﳵ�޸Ķ���������
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
	
	
	
	@Action("forecart")//�鿴���ﳵ
	public String cart() {
	    User user =(User) ActionContext.getContext().getSession().get("user");
	    orderItems = OrderItemService.ListOrderitem(user);
	    for (OrderItem orderItem : orderItems) {
	    	ProductImageService.setFirstProdutImage(orderItem.getProduct());
	    }
	    return "cart.jsp";
	}
	@Action("foreaddCart")//���빺�ﳵ
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
	
	
	@Action("forebuy")//����ҳ��
	public String buy() {
	    orderItems = new ArrayList<>();
	    for (int oiid : oiids) {
	        OrderItem oi= (OrderItem) OrderItemService.get(oiid);
	        total +=oi.getProduct().getPromotePrice()*oi.getNumber();//�ܽ��
	        orderItems.add(oi);
	        ProductImageService.setFirstProdutImage(oi.getProduct());
	    }
	     
	    ActionContext.getContext().getSession().put("orderItems", orderItems);
	    return "buy.jsp";
	}
	@Action("forebuyone")//��������
	public String buyone(){
		
		 User u =(User) ActionContext.getContext().getSession().get("user");//��ȡ�û�
		 boolean found = false;
		  List<OrderItem> ois = OrderItemService.listByParent(u);//��ȡ�û������ж�����
		  for(OrderItem orderItem:ois){//����дζ�������޸�������
			  if(product.getId()==orderItem.getProduct().getId()){
				  orderItem.setNumber(orderItem.getNumber()+num);
				  OrderItemService.update(orderItem);
		            found = true;
		            oiid = orderItem.getId();
		            break;
			  }
		  }
		  if(!found){//���û�д����¶�����
		        OrderItem oi = new OrderItem();
		        oi.setUser(u);
		        oi.setNumber(num);
		        oi.setProduct(product);
		        OrderItemService.save(oi);
		        oiid = oi.getId();
		    }
		return "buyPage";
		
	}
	@Action("foresearch")//����
	public String search(){
	    products=ProductService.search(keyword, 0, 20);//ͨ��ǰ�˴�������keyword����20�����ƵĲ�Ʒ
		ProductService.setSaleAndReviewNumber(products);//Ϊ���в�Ʒ���ü۸����������
		for(Product product:products){
			ProductImageService.setFirstProdutImage(product);//���ò�Ʒ������ͼƬ
		}
		return "searchResult.jsp";
		
	}
	
	
	@Action("forecategory")//����鿴
	public String category(){
		ProductService.fill(category);//Ϊ�˷���ע����Ϣ
		ProductService.setSaleAndReviewNumber(category.getProducts());//Ϊ��Ʒע����Ϣ
		if(sort!=null){//ͨ��5���Ƚ����鿴��Ʒ
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
	
	
	
	
	@Action("foreloginAjax")//ajax��¼
	public String loginAjax() {

	    user.setName(HtmlUtils.htmlEscape(user.getName()));
	    User user_session = UserService.get(user.getName(),user.getPassword());
	    if(null==user_session)
	        return "fail.jsp"; 

	    ActionContext.getContext().getSession().put("user", user_session);
	    return "success.jsp";       
	}
	@Action("forecheckLogin")//ajax��֤�Ƿ��¼
	public String checkLogin() {
	    User u =(User) ActionContext.getContext().getSession().get("user");
	    if(null==u)
			return "fail.jsp";
		else
			return "success.jsp";
	}
	@Action("foreproduct")//��Ʒҳ
	public String product(){
		ProductService.fill(product);//Ϊ����Ĳ�Ʒע��ͼƬ����Ϣ
		propertyValues=PropertyValueService.listByParent(product);//���ҵ�ǰ��Ʒ�ĸ���������Ϣ
		reviews=ReviewService.listByParent(product);//��ȡ�˲�Ʒ����������
		ProductService.setSaleAndReviewNumber(product);
		return "product.jsp";
		
	}
	
	@Action("forelogout")//�˳�
	public String logout() {
		ActionContext.getContext().getSession().remove("user");//��session��ɾ���û�
		return "homePage"; 	//������ҳ
	}
	@Action("forelogin")//��½
	public String login(){
		user.setName(HtmlUtils.htmlEscape(user.getName()));//��ǰ������ת���ֵ
		User user_session=UserService.get(user.getName(), user.getPassword());//�ж����ݿ����Ƿ���ڸ��û�
		if(null==user_session){
	        msg= "�˺��������";
	        return "login.jsp"; //��������Ϣ���ص�½����
	    }
	    ActionContext.getContext().getSession().put("user", user_session);//����ɹ������û�����session
	    return "homePage"; 
	}
	

	@Action("foreregister")//ע��
	public String register() {
		user.setName(HtmlUtils.htmlEscape(user.getName()));//��ǰ������ת�벢��ֵ
		boolean exist = UserService.isExist(user.getName());//�ж����ݿ����Ƿ��Ѵ���
		
		 if(exist){
			 msg = "�û����Ѿ���ʹ��,����ʹ��";
			 return "register.jsp";  //��������Ϣ����ע�����
			
		 }
		 UserService.save(user);
		 return "registerSuccessPage";
	}	
	
    @Action("forehome")//��ҳ
    public String home() {
        categorys = CategoryService.list();//��ȡ���з���
        ProductService.fill(categorys);//Ϊÿ������ע������product
        ProductService.fillByRow(categorys);//ʵ��List<List<Product>>
        Map m = ActionContext.getContext().getSession();
        m.put("categorys", categorys);//��category����session
        return "home.jsp";
    }

    
    
    
	
	
}