package edu.nju.onlineorder.dao;

import java.util.List;

public interface BaseDao {
    public void flush();
    public void clear();
    public Object find(Class c, Integer id);
    public List getAllList(Class c);
    public Long getTotalCount(Class c);
    public void save(Object bean);
    public void update(Object bean);
    public void delete(Object bean);
    public void delete(Class c, String id) ;
    public void delete(Class c, String[] ids) ;
}
