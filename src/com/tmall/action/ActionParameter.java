package com.tmall.action;

public class ActionParameter extends ActionService {

	
	String msg;//错误信息
	String sort;//分类页排序方式
	String keyword;//搜索页的关键字
	int num;//购物数量
	int oiid;//立即购买生成的订单项id
	int[] oiids;//通过购物车选中的多个订单项id
	float total; //结算页面显示的总金额
    boolean showonly;//在进行评论的页面，是否只显示评论记录，而不提供输入
    
    
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getOiid() {
		return oiid;
	}
	public void setOiid(int oiid) {
		this.oiid = oiid;
	}
	public int[] getOiids() {
		return oiids;
	}
	public void setOiids(int[] oiids) {
		this.oiids = oiids;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public boolean isShowonly() {
		return showonly;
	}
	public void setShowonly(boolean showonly) {
		this.showonly = showonly;
	}
    
    
    
}
