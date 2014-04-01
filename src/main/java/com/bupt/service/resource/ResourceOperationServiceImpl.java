package com.bupt.service.resource;

import com.bupt.dao.resource.IResourceDao;
import com.bupt.dao.user.IUserDao;
import com.bupt.domain.Resource;
import com.bupt.domain.User;
import com.bupt.service.converter.ConverterContext;
import com.bupt.util.*;
import com.bupt.webContext.BRWebApplicationContext;
import com.bupt.webContext.WebContextThreadLocal;
import javassist.convert.TransformReadField;
import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: liboyang01
 * Date: 12-7-27
 * Time: 下午9:00
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ResourceOperationServiceImpl implements IResourceOperationService {
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IResourceDao resourceDao;

    private static final int PAGE_SIZE = 13;

    private boolean isImgPreview = false;

    @Override
    public File uploadResource(MultipartFile file,String userId,final StringBuilder resourceId) throws Exception {
        if (!file.isEmpty()) {
            Resource resource = new Resource();
            User user = this.userDao.get(userId,LockMode.UPGRADE);
            user.setUserMark(user.getUserMark()+2);
            String realName = file.getOriginalFilename();
            final String type = realName.substring(realName.lastIndexOf(".") + 1);
            resource.setResourceRealName(file.getOriginalFilename());
            resource.setUploadUser(user);
            resource.setUploadTime(new Date());
            resource.setResourceSize(file.getSize());
            resource.setResourceType(type);
            this.resourceDao.save(resource);
            resourceId.append(resource.getId());
            String savePath = ResourceUtil.getRealSavePathByResourceId(resourceId.toString());
            File directory = new File(savePath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            InputStream from = file.getInputStream();
            final File uploadFile = new File(directory, resourceId + "." + type);
            OutputStream to = new FileOutputStream(uploadFile);
            FileUtil.copy(from, to);
            WebContextThreadLocal.getCurrentUser().setUserMark(user.getUserMark());
            ConverterContext.convert(type,uploadFile.getAbsolutePath(),resourceId.toString())   ;
            return uploadFile;
        } else {
            LoggerUtil.error(this.getClass(), "文件不能为空！");
            throw new RuntimeException("文件内容不能为空");
        }
    }
    @Override
    public void downloadResource(String resourceId, HttpServletResponse response) throws Exception {
        Resource resource = this.resourceDao.get(resourceId, LockMode.UPGRADE);
        resource.setDownloadTimes(resource.getDownloadTimes() + 1);
        String userId = resource.getUploadUser().getId();
        User user = this.userDao.get(userId,LockMode.UPGRADE);
        if(!WebContextThreadLocal.getCurrentUser().getId().equals(user.getId()) && !isImgPreview){
            User currentUser = this.userDao.get(WebContextThreadLocal.getCurrentUser().getId(),LockMode.UPGRADE);
            currentUser.setUserMark(currentUser.getUserMark()-2);
            user.setUserMark(user.getUserMark()+1);
            WebContextThreadLocal.getCurrentUser().setUserMark(currentUser.getUserMark());
        }
       // currentUser.getDownloadResources().add(resource);
        //currentUser.setUserMark(currentUserMark);
        String fileName = resource.getResourceRealName();
        String savePath = ResourceUtil.getRealSavePathByResourceId(resourceId);
        if(!isImgPreview){
            response.setContentType("application/octet-stream ");
            response.setHeader("Content-disposition", "attachment; filename="
                    + new String(fileName.getBytes(), "ISO-8859-1"));
        }
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(new File(savePath, resourceId + "." + resource.getResourceType()));
            os = response.getOutputStream();
            byte[] buff = new byte[1024];
            int length = -1;
            while ((length = is.read(buff)) != -1) {
                os.write(buff, 0, length);
                os.flush();
            }
            isImgPreview = false;
            LoggerUtil.info(this.getClass(), WebContextThreadLocal.getCurrentUser().getUserName() + "下载:" + resource.getResourceRealName() + ":" + resource.getDownloadTimes());
        } catch (FileNotFoundException e) {
            LoggerUtil.error(this.getClass(), "downLoadResource时没有找到下载文件" + e);
            e.printStackTrace();
        } catch (IOException e) {
            LoggerUtil.error(this.getClass(), "IOException" + e);
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
    }

    @Override
    public List<Resource> getResources(String type,int limit,String keyword) throws Exception{
        List<Resource> list = null;
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Resource.class);
        detachedCriteria.add(Restrictions.eq("deleteFlag",0));
        if(!keyword.trim().equals("")){
          detachedCriteria.add(Restrictions.like("resourceRealName","%" + keyword + "%"));
        }
        if(type.equals("new")){
            detachedCriteria.addOrder(Order.desc("uploadTime"));
        }else if(type.equals("max")){
            detachedCriteria.addOrder(Order.desc("downloadTimes"));
        }else if(type.equals("pdf")){
            detachedCriteria.add(Restrictions.eq("resourceType","pdf"));
        }else if(type.equals("word")){
            detachedCriteria.add(Restrictions.in("resourceType",new String[]{"doc","docx"}));
        }else if(type.equals("ppt")){
            detachedCriteria.add(Restrictions.in("resourceType",new String[]{"ppt","pptx"}));
        }else if(type.equals("txt")){
            detachedCriteria.add(Restrictions.eq("resourceType","txt"));
        }else if(type.equals("excel")){
            detachedCriteria.add(Restrictions.in("resourceType",new String[]{"xls","xlsx"}));
        }else{
            detachedCriteria.add(Restrictions.not(Restrictions.in("resourceType",new String[]{"pdf","doc","docx","ppt","txt","pptx","xls","xlsx"})));
        }
        list = this.resourceDao.findByDetachedCriteria(detachedCriteria,0,limit);
        return list;
    }

    @Override
    public List<String> query(String keyword) throws Exception {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Resource.class) ;
        detachedCriteria.add(Restrictions.like("resourceRealName","keyword%"));
        detachedCriteria.addOrder(Order.desc("downloadTimes"));
        List<Resource> list = this.resourceDao.findByDetachedCriteria(detachedCriteria,0,10);
        List<String> ret = new ArrayList<String>();
        for(Resource r : list){
           ret.add(r.getResourceRealName());
        }
        return ret;
    }

    @Override
    public Resource preview(String resourceId) throws Exception {
        Resource resource = this.resourceDao.get(resourceId);
        String userId = resource.getUploadUser().getId();
        if(!WebContextThreadLocal.getCurrentUser().getId().equals(userId)){
            User currentUser = this.userDao.get(WebContextThreadLocal.getCurrentUser().getId(),LockMode.UPGRADE);
            currentUser.setUserMark(currentUser.getUserMark()-2);
            WebContextThreadLocal.getCurrentUser().setUserMark(currentUser.getUserMark());
        }
        return resource;
    }
    @Override
    public void imgPreview(String resourceId,HttpServletResponse response)throws Exception{
        this.isImgPreview = true;
        this.downloadResource(resourceId,response);
    }
}
