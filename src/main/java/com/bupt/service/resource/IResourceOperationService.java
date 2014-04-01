package com.bupt.service.resource;

import com.bupt.domain.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liboyang01
 * Date: 12-7-27
 * Time: 下午8:45
 * To change this template use File | Settings | File Templates.
 */
public interface IResourceOperationService {

    public File uploadResource(MultipartFile file, String userId, StringBuilder resourceId) throws Exception;

    public void downloadResource(String resourceId, HttpServletResponse response) throws Exception;

    public List<Resource> getResources(String type,int limit,String keyword) throws Exception;

    public List<String> query(String keyword) throws Exception;

    public Resource preview(String id)throws Exception;

    public void imgPreview(String resourceId,HttpServletResponse response) throws Exception;
}



