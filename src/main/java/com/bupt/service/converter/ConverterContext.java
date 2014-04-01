package com.bupt.service.converter;

import com.bupt.util.ResourceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: meng.zm
 * Date: 14-3-20
 * Time: 下午3:12
 * To change this template use File | Settings | File Templates.
 */
public class ConverterContext {

   private static Map<String,AbstractConverter> map = new HashMap<String,AbstractConverter>();

    static {
       OfficeConverter officeConverter = new OfficeConverter();
       map.put("pdf",new PdfConverter());
       map.put("txt",officeConverter);
       map.put("doc",officeConverter);
       map.put("docx",officeConverter);
       map.put("pptx",officeConverter);
       map.put("ppt",officeConverter);
       map.put("xlsx",officeConverter);
       map.put("xls",officeConverter);

    }
    public static String convert(String type,String path,String id) throws Exception{
        if(ResourceUtil.isImg(type)){
            return null;
        }
        return map.get(type).File2Swf(path,id);
    }
}

