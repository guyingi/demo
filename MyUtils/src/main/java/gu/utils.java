package gu;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Random;
import java.security.MessageDigest;
import java.util.logging.Level;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Encoder;

public class utils {

    private static Random random = new Random();
    private static  MessageDigest MD5;
    private static String LEFT_SLASH = "/";
    /**
     *  Unicode转中文
     */
    private static String unicodeToCn(String unicode) {
        /** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格*/
        String[] strs = unicode.split("\\\\u");
        String returnStr = "";
        // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
        for (int i = 1; i < strs.length; i++) {
            returnStr += (char) Integer.valueOf(strs[i], 16).intValue();
        }
        return returnStr;
    }
    /**
     * 生成n位随机数
     * @return
     */
    public static int getRandonNumber(int n){
        int bound = 1;
        while(n-->1)
            bound*=10;
        int temp = 0;
        while(bound>(temp=random.nextInt(bound*10))){}
        return temp;
    }

    /**格式化小数，保留2位**/
    public String formatDecimalTo2Bit(Double value) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(value);
    }
    /**生成32位定长字符串,MD5*/
    public static String getMD5(String text){
        byte[] bytes = text.getBytes();
        MD5.update(bytes, 0, bytes.length);
        return new String(Hex.encodeHex(MD5.digest()));
    }

    /**
     * 生成/年/月/日格式字符串
     */
    public static String generateDatePath() {
        Calendar now = Calendar.getInstance();
        String year = String.valueOf(now.get(Calendar.YEAR));
        String month = String.valueOf(now.get(Calendar.MONTH)+1);
        String day = String.valueOf(now.get(Calendar.DATE));
        return LEFT_SLASH+year + LEFT_SLASH + month + LEFT_SLASH + day;
    }

    /**获取文件的base64编码字符串**/
    public static byte[] getBase64ByteArray(File file){
        //读取文件字节数组，base64编码得到字符串，再获取utf-8编码的字节数组
        FileInputStream fin = null;
        byte[] data = null;
        try {
            fin = new FileInputStream(file);
            int available = fin.available();
            byte[] bytearr = new byte[available];

            fin.read(bytearr);
            fin.close();
            String encode = new BASE64Encoder().encode(bytearr);
            data = encode.getBytes("utf-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /***将Integer数字格式化为定长字符串，前面补0*/
    public static String formatIntegerToString(Integer number,Integer length){
        if(length>10){
            return null;
        }
        String tempstr = String.valueOf(number);
        tempstr = "0000000000"+tempstr;
        int start = tempstr.length()-length;
        int end = tempstr.length();
        return tempstr.substring(start,end);
    }

    /***将数字格式化为定长字符串，前面补0*/
    public static String formatLongToString(Long number,Integer length){
        if(length>19){
            return null;
        }
        String tempstr = String.valueOf(number);
        tempstr = "0000000000000000000"+tempstr;
        int start = tempstr.length()-length;
        int end = tempstr.length();
        return tempstr.substring(start,end);
    }
    /**
     * 创建本地文件
     */
    public static boolean mkdirs(String dir){
        if(StringUtils.isBlank(dir)){
            return false;
        }
        File file = new File(dir);
        file.deleteOnExit();
        if(!file.exists()){
            new File(dir).mkdirs();
        }
        return new File(dir).exists();
    }

    /**运行cmd命令**/
    public static JSONObject execPython(String pythonCmd,String pythonPath,String srctemp,String destemp){
        JSONObject result = null;
        File desTempDirFile = new File(destemp);
        for(File tempfile : desTempDirFile.listFiles()){
            tempfile.deleteOnExit();
        }
        try {
            String[] args = new String[]{pythonCmd, pythonPath, srctemp,destemp};
            for (String e : args){
                System.out.println(e);
            }
            Process pr = Runtime.getRuntime().exec(args);
            pr.getInputStream();
            pr.waitFor();

            File f = new File(destemp);
            File[] files = f.listFiles();
            if (files.length == 0) {
                System.out.print("文书解析失败");
                result = null;
            } else {
                File jsonFile = files[0];
                BufferedReader br = new BufferedReader(new FileReader(jsonFile));
                String text = br.readLine();
                br.close();
                result = JSON.parseObject(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * rest API 调用方法
     *
     * @param method
     * @param url
     * @param parameter
     * @return
     */
    public static JSONObject rest(String method, String url, JSONObject parameter) {
        JSONObject resultl = null;
        StringBuilder builder = new StringBuilder();
        boolean isSuccess = false;
        try {
            byte[] param = parameter.toString().getBytes("UTF-8");
            URL restServiceURL = new URL(url);

            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod(method);
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            //httpConnection.setRequestProperty("Connection", "Keep-Alive");
            httpConnection.setRequestProperty("Charset", "UTF-8");
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);

            //传递参数
            httpConnection.setRequestProperty("Content-Length", String.valueOf(param));
            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(param);
            outputStream.flush();
            outputStream.close();
            System.out.println("返回码：" + httpConnection.getResponseCode());

            if (httpConnection.getResponseCode() == 200) {
                InputStream inputStream = httpConnection.getInputStream();
                BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                String tempStr = null;
                while ((tempStr = tBufferedReader.readLine()) != null) {
                    builder.append(tempStr);
                }
                inputStream.close();
                isSuccess = true;
            } else {
//                logger.log(Level.INFO, "请求失败");
            }
            httpConnection.disconnect();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (isSuccess) {
            resultl = (JSONObject) JSON.parse(builder.toString());
        }
        return resultl;
    }

    /**做具体的下载操作，将文件写出给前端*/
   /* public static void doDownload(HttpServletResponse response, String tempFilePath, String filetype) throws UnsupportedEncodingException {
        if(tempFilePath!=null) {
            System.out.println("临时文件目录："+tempFilePath);
            response.setCharacterEncoding("utf-8");
            if(filetype.equals("json"))
                response.setContentType("multipart/form-data");
            else if(filetype.equals("xls"))
                response.setContentType("application/msexcel");
            else if(filetype.equals("zip"))
                response.setContentType("application/zip");
            else
                ;

            File file = new File(tempFilePath);
            response.setHeader("Content-Disposition", "attachment;fileName="+ URLEncoder.encode(file.getName(), "UTF-8") );
            long downloadedLength = 0l;
            long available = 0l;
            try {
                //打开本地文件流
                InputStream inputStream = new FileInputStream(tempFilePath);
                available = inputStream.available();
                OutputStream os = response.getOutputStream();
                byte[] b = new byte[2048];
                int length;
                while ((length = inputStream.read(b)) > 0) {
                    os.write(b, 0, length);
                    downloadedLength += b.length;
                }
                os.close();
                inputStream.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }else {
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=download failed");

            try {
                OutputStream os = response.getOutputStream();
                os.write((new String("下载失败")).getBytes("utf-8"));
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/
}
