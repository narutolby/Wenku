package com.bupt.dao.resource;

import com.bupt.dao.HibernateGenericDaoImpl;
import com.bupt.domain.Resource;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: liboyang01
 * Date: 12-7-27
 * Time: 下午9:02
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ResourceDaoImpl extends HibernateGenericDaoImpl<Resource,String> implements IResourceDao {
}
