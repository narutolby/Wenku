package com.bupt.dao;

import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

public class HibernateGenericDaoImpl<E extends Serializable, PK extends Serializable> extends HibernateDaoSupport implements IHibernateGenericDao<E, PK> {
    private Class<?> entityClass;

    @Autowired
    protected void init(SessionFactory sessionFactory) throws HibernateException {
        this.setSessionFactory(sessionFactory);
    }

    /**
     * 获取E实例类的类型
     */
    public HibernateGenericDaoImpl() throws HibernateException {
        Class<?> c = this.getClass();
        Type t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            this.entityClass = (Class<?>) ((ParameterizedType) t)
                    .getActualTypeArguments()[0];
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> find(String hql) throws HibernateException {
        return this.getHibernateTemplate().find(hql);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> find(final String hql, final Object... values) throws HibernateException {
        HibernateTemplate hibernateTemplate = this.getHibernateTemplate();
        hibernateTemplate.setCacheQueries(true);
        hibernateTemplate.setQueryCacheRegion(this.entityClass.getSimpleName());
        return hibernateTemplate.find(hql, values);
    }

    @Override
    @SuppressWarnings("unchecked")
    public E findEntity(final String hql, final Object... values) throws HibernateException {
        return this.getHibernateTemplate().execute(new HibernateCallback<E>() {
            @Override
            public E doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                SQLQuery query1 = session.createSQLQuery(hql);
                for (int i = 0; i < values.length; i++) {
                    query.setParameter(i, values[i]);
                }
                return (E) query.setMaxResults(1).uniqueResult();
            }
        });

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> findAll() throws HibernateException {
        return (List<E>) this.getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(entityClass));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> findByCriteria(final int start, final int count, final E example) throws HibernateException {
        final Class clazz = this.entityClass;
        return this.getHibernateTemplate().execute(new HibernateCallback<List<E>>() {
            @Override
            public List<E> doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(clazz);
                int startIndex = count * (start - 1) + 1;
                criteria.add(Example.create(example))
                        .setMaxResults(count)
                        .setFirstResult(startIndex);
                List<E> list = criteria.list();
                return list;
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> findByDetachedCriteria(DetachedCriteria detachedCriteria, int firstResult, int maxResult) throws HibernateException {
        return this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResult);
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<E> findByHql(final String hql, final int start, final int count, final Object... params) throws HibernateException {
        return this.getHibernateTemplate().execute(new HibernateCallback<List<E>>() {
            @Override
            public List<E> doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                for (int i = 0; i < params.length; i++) {
                    query.setParameter(i, params[i]);
                }
                query.setMaxResults(count).setFirstResult(start);
                List<E> list = query.list();
                return list;
            }
        });
    }

    @Override
    public void flush() throws HibernateException {
        this.getHibernateTemplate().flush();
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(PK id) throws HibernateException {
        return (E) this.getHibernateTemplate().get(this.entityClass, id);
    }


    public E get(PK id, LockMode lockMode) throws HibernateException {
        return (E) this.getHibernateTemplate().get(this.entityClass, id, lockMode);
    }

    @Override
    public void initialize(Object proxy) throws HibernateException {
        Hibernate.initialize(proxy);

    }

    @Override
    @SuppressWarnings("unchecked")
    public E load(PK id) throws HibernateException {
        return (E) this.getHibernateTemplate().load(this.entityClass, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> loadAll() {
        return this.getHibernateTemplate().execute(new HibernateCallback<List<E>>() {
            @Override
            public List<E> doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery("from " + entityClass.getSimpleName()).setCacheable(true).setCacheRegion(entityClass.getSimpleName());
                return query.list();
            }
        });
    }

    @Override
    public void save(E entity) throws HibernateException {
        this.getHibernateTemplate().save(entity);
    }

    @Override
    public void saveOrUpdate(E entity) throws HibernateException {
        this.getHibernateTemplate().saveOrUpdate(entity);

    }

    @Override
    public void update(E entity) throws HibernateException {
        this.getHibernateTemplate().update(entity);

    }

    @Override
    public <T> T getByCustom(HibernateCallback<T> g) throws HibernateException {
        return this.getHibernateTemplate().execute(g);
    }

    @Override
    public void delete(E entity) throws HibernateException {
        this.getHibernateTemplate().delete(entity);
    }

    @Override
    public Long getCount(final DetachedCriteria detachedCriteria) throws HibernateException {
        return this.getHibernateTemplate().execute(new HibernateCallback<Long>() {
            @Override
            public Long doInHibernate(Session session) throws HibernateException, SQLException {
                return (Long) detachedCriteria.getExecutableCriteria(session).setProjection(Projections.rowCount()).uniqueResult();
            }
        });
    }

    @Override
    public void clear() {
        this.getHibernateTemplate().clear();
    }

    @Override
    public void evict(Object o) {
        this.getHibernateTemplate().evict(o);
    }
}
