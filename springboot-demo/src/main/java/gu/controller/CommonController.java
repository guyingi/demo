package gu.controller;


import com.alibaba.fastjson.JSONObject;
import gu.pojo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Enumeration;
import java.util.Map;

@RestController
@RequestMapping("/school")
public class CommonController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/student/{id}")
    public ResultInfo user(@PathVariable String id, HttpServletRequest request, HttpServletResponse response){
        String url="http://GU:8091/dog_21/info";
        String json = (String)restTemplate.getForObject(url, Object.class);
        ResponseEntity<String> results = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        String resultsBody = results.getBody();

        String filePath = "C:\\Users\\MikanMu\\Desktop\\test.txt";
        RestTemplate rest = new RestTemplate();
        FileSystemResource resource = new FileSystemResource(new File(filePath));
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("jarFile", resource);
        param.add("fileName", "test.txt");
        String string = rest.postForObject(url, param, String.class);

        return null;
    }

    @CrossOrigin
    @GetMapping("/info")
    public String authorize(HttpServletRequest request, HttpServletResponse response) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String key = headerNames.nextElement();
            String header = request.getHeader(key);
            System.out.println(key+":"+header);
        }
        String code = request.getParameter("code");
        return code;
    }

    @CrossOrigin
    @GetMapping("/info")
    public String authorize(@RequestBody Map<String, Object> parameter) {
        for(Map.Entry<String,Object> entry : parameter.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
        return "";
    }

    @ResponseBody
    @RequestMapping(value = "/data", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String authorize(@RequestBody JSONObject jsonParam) {
        System.out.println(jsonParam.toJSONString());
        return "";
    }
}
