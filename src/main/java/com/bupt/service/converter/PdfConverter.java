package com.bupt.service.converter;

import com.bupt.webContext.BRWebApplicationContext;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: meng.zm
 * Date: 14-3-20
 * Time: 下午3:16
 * To change this template use File | Settings | File Templates.
 */

public class PdfConverter extends AbstractConverter{

    @Override
    public String File2Swf(String path,String id)throws Exception{
        String SwfToolCommand = BRWebApplicationContext.getWebRootPath().replace("upload","SWFTools") + File.separator + "pdf2swf.exe";
        String swfRootPath = BRWebApplicationContext.getWebRootPath() + File.separator + "swf";
        Runtime runtime = Runtime.getRuntime();
        File file  = new File(swfRootPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String command = SwfToolCommand + " " + path + " " + "-o" + " " + swfRootPath + File.separator + id + ".swf";
        Process process = runtime.exec(command);
        InputStream is = process.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = "";
        while ((line = br.readLine())!=null){
            System.out.println(line);
        }
        System.out.println("转换成功!");
        return swfRootPath + File.separator + id +".swf";
    }


}
