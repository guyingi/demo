package gu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import gu.configuration.SystemConfiguration;
import gu.service.RestService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gu
 * @date 2019/4/1 14:41
 */
public class RestServiceImpl implements RestService {

    SystemConfiguration systemConfiguration = new SystemConfiguration();
    RestTemplate restTemplate = new RestTemplate();

    @Override
    public String uploadFile(List<String> fileList) {
        String taskid = null;
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        headers.setContentType(type);

        //设置请求体，注意是LinkedMultiValueMap
        List<FileSystemResource> fileSystemResourceList = new ArrayList<FileSystemResource>();
        for(String file : fileList){
            FileSystemResource fileSystemResource = new FileSystemResource(file);
            fileSystemResourceList.add(fileSystemResource);
        }
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.addAll("file",fileSystemResourceList);

        //用HttpEntity封装整个请求报文
        HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, headers);
//        String url = "http://143.3.119.51:9000/at/s1/ocr";
        //步骤一的url为上传文件
        String resultsBody = restTemplate.postForObject(systemConfiguration.getOcrUrlStep1(), files, String.class);
        System.out.println(resultsBody);
        JSONObject parse = JSON.parseObject(resultsBody);

        if(parse.getString("code").equals("200")){
           taskid = parse.getString("taskid");
        }
        return taskid;
    }

    @Override
    public boolean result(String taskid) {
        String url = systemConfiguration.getOcrUrlStep2();
//        String url = "http://143.3.119.51:9000/at/s2/result/";
        String requesturl = url+taskid;
//        System.out.println(requesturl);
        ResponseEntity<String> results = restTemplate.exchange(requesturl, HttpMethod.GET, null, String.class);
        String resultsBody = results.getBody();
        JSONObject parse = JSON.parseObject(resultsBody);
        if(parse.getString("code").equals("200")){
            System.out.println(resultsBody);
            return true;
        }
        return false;
    }

    @Override
    public boolean downlaod(String taskid, String zipfilepath) {
//        String url =  systemConfiguration.getOcrUrlStep3();
        boolean issuccess = true;
        String url = "http://143.3.119.51:9000/at/s3/download/";
        url = url+taskid;
        byte[] response = restTemplate.getForObject(url, byte[].class);
        try {
            FileOutputStream fout = new FileOutputStream(new File(zipfilepath));
            fout.write(response);
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            issuccess = false;
        } catch (IOException e) {
            e.printStackTrace();
            issuccess = false;
        }
        if(issuccess){
            return true;
        }else{
            return false;
        }
    }

    public static void main(String[] args) {
//        new RestServiceImpl().uploadFile(null);
//        new RestServiceImpl().result("1554101285116100000078");
//        new RestServiceImpl().downlaod("1554101285116100000078","D:\\lab\\kafka-stream test\\a.zip");
    }
}
