package gu.util;


import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    private Integer sequence = 0;

    //    id:0+13位时间戳（精确到毫秒）+3位机器编号+8位自增序号，满了自动从0开始
    public String snowflakeid(String machineid) {
        long time = new Date().getTime();
        String timestamp = formatLongToString(time, 13);
        String seq = formatIntegerToString(sequence, 8);
        String snowflakeid = timestamp + machineid + seq;
        sequence++;
        if (sequence > 99999999)
            sequence = 0;

        return snowflakeid;

    }

    /***将Integer数字格式化为定长字符串，前面补0*/
    public static String formatIntegerToString(Integer number, Integer length) {
        if (length > 10) {
            return null;
        }
        String tempstr = String.valueOf(number);
        tempstr = "0000000000" + tempstr;
        int start = tempstr.length() - length;
        int end = tempstr.length();
        return tempstr.substring(start, end);
    }

    /***将数字格式化为定长字符串，前面补0*/
    public static String formatLongToString(Long number, Integer length) {
        if (length > 19) {
            return null;
        }
        String tempstr = String.valueOf(number);
        tempstr = "0000000000000000000" + tempstr;
        int start = tempstr.length() - length;
        int end = tempstr.length();
        return tempstr.substring(start, end);
    }

    /**
     * 生成/年/月/日格式字符串
     */
    public static String generateDatePath() {
        Calendar now = Calendar.getInstance();
        String year = String.valueOf(now.get(Calendar.YEAR));
        String month = String.valueOf(now.get(Calendar.MONTH) + 1);
        String day = String.valueOf(now.get(Calendar.DATE));
        String separator = File.separator;
        return separator + year + separator + month + separator + day;
    }

    /**
     * 创建本地文件
     */
    public boolean mkdirs(String dir){
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

    /**将数字格式化为指定长度字符串*/
    public static String formatNumberToString(Long number,Integer length){
        if(length>19){
            return null;
        }
        String tempstr = String.valueOf(number);
        tempstr = "0000000000"+tempstr;
        int start = tempstr.length()-length;
        int end = tempstr.length();
        return tempstr.substring(start,end);
    }
}
