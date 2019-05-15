package gu.service;

import gu.enumeration.TypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class UploadFileService {

    public String upload(MultipartFile[]files, TypeEnum tsktype){
        File localFile = null;
        for(MultipartFile multipartFile : files) {
            System.out.println(multipartFile.getName());//获取表单中文件组件的名字
            System.out.println(multipartFile.getOriginalFilename());//获取上传文件的名字
            System.out.println(multipartFile.getSize());//文件的上传大小
            //根据路径+时间戳+文件后缀名来创建文件
            //如果是传入服务器  file.getInputStream();用输入输出流来读取
            //储存为本地文件
            try {
                multipartFile.transferTo(localFile);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public boolean parameterCheck(MultipartFile[]files, TypeEnum tsktype){
        switch (tsktype) {
            case HEAD:  //只能接收图片
                if(files.length==0){
                    return false;
                }
                for(MultipartFile file : files){
                    String originalFilename = file.getOriginalFilename().toLowerCase();
                    if(!originalFilename.endsWith("png") && !originalFilename.endsWith("jpg")){
                        return false;
                    }
                }
                break;
        }
        return true;
    }

}
