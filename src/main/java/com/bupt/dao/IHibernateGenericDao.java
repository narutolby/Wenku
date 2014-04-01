package com.bupt.dao;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.io.Serializable;
import java.util.List;

public interface IHibernateGenericDao<E extends Serializable, PK extends Serializable> {

    /**
     * @param id 根据主键查询一个实体
     * @return 一个实体对象
     */
    public E get(PK id) throws HibernateException;

    public E get(PK id, LockMode lockMode) throws HibernateException;

    /**
     * @param id 根据主键加裁一个实体对象
     * @return 一个实体对象
     */
    public E load(PK id) throws HibernateException;

    /**
     * @return 加裁所有对象
     */
    public List<E> loadAll() throws HibernateException;

    /**
     * @param entity 保存一个实体
     * @throws org.hibernate.HibernateException
     *          抛出Exception异常
     */
    public void save(E entity) throws HibernateException;

    /**
     * @param entity 删除一个实体
     * @throws org.hibernate.HibernateException
     *          抛出异常
     */
    public void delete(E entity) throws HibernateException;

    /**
     * @param entity 修改一个实体
     * @throws org.hibernate.HibernateException
     *          抛出异常
     */
    public void update(E entity) throws HibernateException;

    /**
     * @param entity 当实体在数据库不在在与之对应记录时,则保存实体,在在实体,则更新实体
     * @throws org.hibernate.HibernateException
     *          抛出异常
     */
    public void saveOrUpdate(E entity) throws HibernateException;


    /**
     * @param hql 使用hql语句,检索数据
     * @return 一个list集合
     */
    public List<E> find(String hql, Object... values) throws HibernateException;


    /**
     * @param hql 使用hql语句,检索数据
     *
     * @return 一个对象
     */
    public E findEntity(String hql, Object... values) throws HibernateException;

    /**
     * @return 从数据库中完全加裁所有对象
     */
    public List<E> findAll() throws HibernateException;


    /**
     * @return 从数据库中完全加裁所有对象
     */
    public List<E> findByCriteria(int start, int count, E example) throws HibernateException;


    public List<E> findByHql(String hql, int start, int count, Object... params) throws HibernateException;

    public List<E> findByDetachedCriteria(DetachedCriteria detachedCriteria, int firstResult, int maxResult) throws HibernateException;

    /**
     * 强制初始化指定的实体
     * 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化.
     * 只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性.
     * 如需初始化关联属性,可实现新的函数.
     * eg.
     * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
     * Hibernate.initialize(user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
     *
     * @param proxy
     */
    public void initialize(Object proxy) throws HibernateException;

    /**
     * 强制立即更新到数据库,否则需要事务提交后,才会提交到数据库
     */
    public void flush() throws HibernateException;

    List<E> find(String hql) throws HibernateException;

    public Long getCount(final DetachedCriteria detachedCriteria) throws HibernateException;

    public void clear() throws HibernateException;

    public void evict(Object o) throws HibernateException;

    public <T> T getByCustom(HibernateCallback<T> g) throws HibernateException;

}
