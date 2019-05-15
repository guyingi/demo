package gu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import gu.configuration.SystemConfiguration;
import gu.constant.Constant;
import gu.pojo.InputMessage;
import gu.pojo.OutputMessage;
import gu.service.BaseCheckService;
import gu.service.HDFSService;
import gu.service.PicToTextService;
import gu.service.RestService;
import gu.util.Utils;
import gu.util.ZipUtil;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.*;

/**
 * @author gu
 * @date 2019/4/1 11:18
 */
public class PicToTextServiceImpl implements PicToTextService {

    private BaseCheckService baseCheckService = new BaseCheckServiceImpl();

    private HDFSService hdfsService = new HDFSServiceImpl();

    private RestService restService = new RestServiceImpl();


    private SystemConfiguration systemConfiguration = new SystemConfiguration();


    @Override
    public OutputMessage doParse(InputMessage message) {
        boolean issuccess = true;

        issuccess = baseCheckService.checkMessage(message);

        //步骤一、解析message获取各种信息
        String hdfspath = null;
        String hdfsuser = null;
        String database = null;
        String tablename = null;
        String hiveuser = null;
        if(issuccess){
            hdfspath = message.getHdfspath();
            hdfsuser = message.getHdfsuser();
            database = message.getDatabase();
            tablename = message.getTablename();
            hiveuser = message.getHiveuser();
        }

        //步骤二、下载hdfs路径指定的文书文件（图片）到本地临时文件夹
        String filedir = null;
        String wenshuid = null;
        if(issuccess){
            String localTempDir = systemConfiguration.getTempdir();
            wenshuid = hdfspath.substring(hdfspath.lastIndexOf(Constant.LEFT_SLASH)+1,hdfspath.length());
            filedir = localTempDir+ File.separator+wenshuid;
            Utils.rmDir(filedir);
            issuccess = hdfsService.copyToLocal(hdfspath,localTempDir);
        }

        //步骤三、调用接口上传文件获取ocr的文本。
        //D:\lab\kafka-stream test\
        String text = null;
        if(issuccess){
//            filedir = "C:\\Users\\24059\\IdeaProjects\\demo\\kafka-stream\\target\\classes\\temp\\1554105804522100000000";
            text = getText(filedir);
        }

        //步骤四、删除本地临时目录
        Utils.rmDir(filedir);

        //步骤五、返回解析结果
        OutputMessage outputMessage = new OutputMessage();
        outputMessage.setDatabase(database);
        outputMessage.setTablename(tablename);
        outputMessage.setWenshuid(wenshuid);
        outputMessage.setText(text);
        return outputMessage;
    }

    @Override
    public String getText(String tempfiledir) {

        StringBuilder text = new StringBuilder();
        boolean issuccess = true;
        //步骤一、组装图片路径，不需要排序，因为名称有序即可
        File fileDirFile = new File(tempfiledir);
        List<String> filepathList = new ArrayList<String>();
        for(File file : fileDirFile.listFiles()){
            if(file.getName().endsWith("jpg")||file.getName().endsWith("png")){
                filepathList.add(file.getAbsolutePath());
            }
        }
        String taskid = null;
        if(issuccess){
            taskid = restService.uploadFile(filepathList);
            if(StringUtils.isBlank(taskid)){
                issuccess = false;
            }
        }

        if(issuccess){
            while(!restService.result(taskid)){
                try {
                    Thread.sleep(200); //睡眠0.2s
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }

        //步骤三、下载，并获取文本
        String zipFilePath = null;
        String zipFilename = "temp";
        if(issuccess){
           zipFilePath = tempfiledir+File.separator+zipFilename+".zip";
            issuccess = restService.downlaod(taskid,zipFilePath);
        }
        //解压并提取文本
        if(issuccess){
            String unzipFilePath = tempfiledir;
            try {
                ZipUtil.unzip(zipFilePath,unzipFilePath,true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String unzipeddir = tempfiledir+File.separator+zipFilename;
            File unzipeddirFile = new File(unzipeddir);
            if(unzipeddirFile.listFiles().length!=0){
                File jsonFile = unzipeddirFile.listFiles()[0];
                String jsonText  =null;
                //读取存放结果的json文件
                try {
                    BufferedReader br = new BufferedReader(new FileReader(jsonFile));
                    jsonText = br.readLine();
                    br.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject = JSON.parseObject(jsonText);
                JSONArray pages = jsonObject
                        .getJSONArray("JZML")
                        .getJSONObject(0)
                        .getJSONArray("files")
                        .getJSONObject(0)
                        .getJSONArray("pages");
                Map<String,String> pagesMap = new HashMap<>();
                int size = pages.size();
                for(int i=0;i<size;i++){
                    JSONObject page = pages.getJSONObject(i);
                    String image = page.getString("image");
                    String content = page.getString("content");
                    pagesMap.put(image,content);
                }
                //对parseMap根据key排序，并循序
                Object[] key_arr = pagesMap.keySet().toArray();
                Arrays.sort(key_arr);
                for(Object key : key_arr){
                    String k = (String)key;
                    String content = pagesMap.get(k);
                    JSONArray jsonArray = JSON.parseArray(content).getJSONArray(0);
                    for(Object obj : jsonArray.toArray()){
                        String sentence = (String)obj;
                        text.append(sentence+"\n");
                    }
                }
            }
        }
        return text.toString();
    }

    @Override
    public InputMessage convertRecordToMessage(String value) {
        InputMessage message = new InputMessage();
        JSONObject jsonObject = JSON.parseObject(value);
        message.setHdfsuser(jsonObject.getString(Constant.HDFSUSER));
        message.setHdfspath(jsonObject.getString(Constant.HDFSPATH));
        message.setDatabase(jsonObject.getString(Constant.DATABASE));
        message.setTablename(jsonObject.getString(Constant.TABLENAME));
        message.setHiveuser(jsonObject.getString(Constant.HIVEUSER));
        return message;
    }


    public static void main(String[] args) {
        //{"hdfsuser"="hdfs","hdfspath"="/wenshutemp/2019/4/1/1554105804522100000000","hiveuser"="hive","database"="wenshu","tablename"="wenshu"}
        InputMessage message = new InputMessage();
        message.setHdfspath("/wenshutemp/2019/4/1/1554105804522100000000");
        message.setDatabase("dddd");
        message.setHdfsuser("hdfs");
        message.setTablename("table");
        message.setHiveuser("hhh");
//        String text = new PicToTextServiceImpl().doParse(message);
//        System.out.println(text);

    }

}
