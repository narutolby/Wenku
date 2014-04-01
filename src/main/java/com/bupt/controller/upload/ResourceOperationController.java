package com.bupt.controller.upload;

import com.bupt.domain.*;
import com.bupt.service.resource.IResourceOperationService;
import com.bupt.service.usesr.IUserService;
import com.bupt.util.*;
import com.bupt.webContext.WebContextThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liboyang01
 * Date: 12-7-27
 * Time: 下午8:32
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/resourceOperation")
public class ResourceOperationController {
    @Autowired
    private IResourceOperationService resourceOperationService;
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(@RequestParam("Filedata") MultipartFile file,ModelMap modelMap, @RequestParam(Constants.USER_INFO) String userId) throws Exception {

        try {
            if ("".equals(userId) || null == userId) {
                modelMap.put("login",Constants.LOGIN);
                return ;
            }
            User user = this.userService.getUserByUserId(userId);
            if (user != null) {
                WebContextThreadLocal.setCurrentUser(user);
            } else {
                modelMap.put("login",Constants.LOGIN);
                return ;
            }
            StringBuilder resourceId = new StringBuilder();
            this.resourceOperationService.uploadResource(file,userId,resourceId);
            modelMap.put("resourceId",resourceId.toString());
        } catch (Exception e) {
            LoggerUtil.error(this.getClass(), e.getMessage());
            throw e;
        }
    }

    @RequestMapping("/download")
    public void download(@RequestParam(value = "resourceId", required = true) String resourceId, HttpServletResponse response) throws Exception {
        this.resourceOperationService.downloadResource(resourceId, response);
    }

    @RequestMapping("/get")
    public void getResources(@RequestParam(value="type")String type,@RequestParam(value="limit")int limit,@RequestParam(value="keyword")String keyword,ModelMap modelMap) throws Exception{
         List<Resource> list = this.resourceOperationService.getResources(type,limit,keyword);
         int size = list.size();
         int pageCount = size%10==0?size/10:size/10+1;
         modelMap.put("resources",list);
         modelMap.put("pageCount",pageCount);
    }

    @RequestMapping("/query")
    public void query(@RequestParam(value="keyword")String keyword){

    }

    @RequestMapping("/preview")
    public void preview(@RequestParam(value="resourceId")String resourceId ,ModelMap modelMap)throws Exception{
        Resource resource = this.resourceOperationService.preview(resourceId);
        if(ResourceUtil.isImg(resource.getResourceType())){
            modelMap.put("isImg",true);
            return;
        }
        modelMap.put("swfPath","upload/swf/" + resourceId +".swf");
        modelMap.put("uploaderName",resource.getUploadUserName());
        modelMap.put("resourceSize",resource.getResourceSize());
        modelMap.put("uploadTime",resource.getUploadTime());
        modelMap.put("downloadTimes",resource.getDownloadTimes());
        modelMap.put("resourceName",resource.getResourceRealName());
    }



    @RequestMapping("imgp")
    public void imgPreview(@RequestParam(value="resourceId")String resourceId,HttpServletResponse response)throws Exception{
       this.resourceOperationService.imgPreview(resourceId,response);
    }
}
