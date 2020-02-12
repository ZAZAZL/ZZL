package com.tmall.action;

import java.util.Date;
import org.apache.struts2.convention.annotation.Action;

import com.tmall.pojo.Order;
import com.tmall.util.Page;

public class OrderAction extends ActionResult{

	
	@Action("admin_order_list")//�������ж���
    public String list() {
        if (page == null)
            page = new Page();
        int total = OrderService.total();
        page.setTotal(total);
        orders = OrderService.listByPage(page);
        OrderItemService.fill(orders);
        return "listOrder";

    }
	@Action("admin_order_delivery")//�޸Ķ���״̬
    public String delivery() {
		order=(Order) OrderService.get(order.getId());//��˲ʱ״̬ת���ɳ־�״̬
        order.setDeliveryDate(new Date());
        order.setStatus(OrderService.waitConfirm);//������״̬�޸ĳɴ��ջ�
        OrderService.update(order);
        return "listOrderPage";
    }
	
}
