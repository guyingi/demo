package gu.controller;

import gu.pojo.ResultInfo;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileUploadController {

    static Logger logger = Logger.getLogger(FileUploadController.class);

    @PostMapping("/upload")
    public ResultInfo head(@RequestParam("file") MultipartFile[]files) throws Exception {
        logger.log(Level.INFO,"/head"+"接收文件数量" + files.length);
        ResultInfo resultInfo = new ResultInfo();
        return resultInfo;
    }

}
