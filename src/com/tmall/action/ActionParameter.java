package com.tmall.action;

public class ActionParameter extends ActionService {

	
	String msg;//������Ϣ
	String sort;//����ҳ����ʽ
	String keyword;//����ҳ�Ĺؼ���
	int num;//��������
	int oiid;//�����������ɵĶ�����id
	int[] oiids;//ͨ�����ﳵѡ�еĶ��������id
	float total; //����ҳ����ʾ���ܽ��
    boolean showonly;//�ڽ������۵�ҳ�棬�Ƿ�ֻ��ʾ���ۼ�¼�������ṩ����
    
    
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
