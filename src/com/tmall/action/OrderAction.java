package com.tmall.action;

import java.util.Date;
import org.apache.struts2.convention.annotation.Action;

import com.tmall.pojo.Order;
import com.tmall.util.Page;

public class OrderAction extends ActionResult{

	
	@Action("admin_order_list")//查找所有订单
    public String list() {
        if (page == null)
            page = new Page();
        int total = OrderService.total();
        page.setTotal(total);
        orders = OrderService.listByPage(page);
        OrderItemService.fill(orders);
        return "listOrder";

    }
	@Action("admin_order_delivery")//修改订单状态
    public String delivery() {
		order=(Order) OrderService.get(order.getId());//将瞬时状态转换成持久状态
        order.setDeliveryDate(new Date());
        order.setStatus(OrderService.waitConfirm);//将订单状态修改成待收货
        OrderService.update(order);
        return "listOrderPage";
    }
	
}
