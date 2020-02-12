package com.tmall.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.dao.impl.DAOImpl;
import com.tmall.service.BaseService;
import com.tmall.util.Page;

@Service
public class BaseServiceImpl implements BaseService{

	@Autowired DAOImpl dao;
	
	Class clazz;
	public BaseServiceImpl(){
        try{
            throw new Exception();  
        }
        catch(Exception e){
            StackTraceElement stes[]= e.getStackTrace();
            String serviceImpleClassName=   stes[1].getClassName();
            try {
                Class  serviceImplClazz= Class.forName(serviceImpleClassName);
                String serviceImpleClassSimpleName = serviceImplClazz.getSimpleName();
                String pojoSimpleName = serviceImpleClassSimpleName.replaceAll("ServiceImpl", "");
                String pojoPackageName = serviceImplClazz.getPackage().getName().replaceAll(".service.impl", ".pojo");
                String pojoFullName = pojoPackageName +"."+ pojoSimpleName;
                clazz=Class.forName(pojoFullName);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        }       
    }
	
	@Override//保存
	public Integer save(Object object) {
		return (Integer) dao.save(object);
	}

	@Override//修改
	public void update(Object object) {
		// TODO Auto-generated method stub
		dao.update(object);
	}

	@Override//删除
	public void delete(Object object) {
		// TODO Auto-generated method stub
		dao.delete(object);
	}

	@Override//查找一条数据
	public Object get(Class clazz, int id) {
		// TODO Auto-generated method stub
		return dao.get(clazz, id);
	}

	@Override
	public Object get(int id) {
		// TODO Auto-generated method stub
		return dao.get(clazz, id);
	}

	@Override//查找全部数据
	public List list() {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
        dc.addOrder(Order.desc("id"));
        return dao.findByCriteria(dc);
	}

	@Override//分页查找
	public List listByPage(Page page) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
        dc.addOrder(Order.desc("id"));
        return dao.findByCriteria(dc,page.getStart(),page.getCount());
	}

	@Override//查找总数
	public int total() {
		String hql = "select count(*) from " + clazz.getName() ;
        List<Long> l= dao.find(hql);
        if(l.isEmpty())
            return 0;
        Long result= l.get(0);
        return result.intValue();
	}
	
	
	@Override//按条件查找多条数据
    public List listByParent(Object parent) {

        String parentName= parent.getClass().getSimpleName();

        String parentNameWithFirstLetterLower = StringUtils.uncapitalize(parentName);

        DetachedCriteria dc = DetachedCriteria.forClass(clazz);

        dc.add(Restrictions.eq(parentNameWithFirstLetterLower, parent));

        dc.addOrder(Order.desc("id"));

        return dao.findByCriteria(dc);

    }

 

    @Override//安条件查找并分页

    public List list(Page page, Object parent) {

        String parentName= parent.getClass().getSimpleName();

        String parentNameWithFirstLetterLower = StringUtils.uncapitalize(parentName);

        DetachedCriteria dc = DetachedCriteria.forClass(clazz);

        dc.add(Restrictions.eq(parentNameWithFirstLetterLower, parent));

        dc.addOrder(Order.desc("id"));

        return dao.findByCriteria(dc,page.getStart(),page.getCount());

    }

 

    @Override//按条件查找并获取总数

    public int total(Object parentObject) {

        String parentName= parentObject.getClass().getSimpleName();

        String parentNameWithFirstLetterLower = StringUtils.uncapitalize(parentName);

         

        String sqlFormat = "select count(*) from %s bean where bean.%s = ?";

        String hql = String.format(sqlFormat, clazz.getName(), parentNameWithFirstLetterLower);

         

        List<Long> l= dao.find(hql,parentObject);

        if(l.isEmpty())

            return 0;

        Long result= l.get(0);

        return result.intValue();

    }
}
