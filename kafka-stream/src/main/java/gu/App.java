package gu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import gu.configuration.SystemConfiguration;
import gu.constant.Constant;
import gu.domain.Work;
import org.apache.commons.lang.StringUtils;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
//        if(args.length == 3){
//            String inputTopic = args[0];
//            String outputTopic = args[0];
//            if(StringUtils.isBlank(inputTopic) || StringUtils.isBlank(outputTopic)){
//                errorPrint();
//            }
//        }else{
//            errorPrint();
//        }
        //检查topic是否存在
        //暂时略

//        new Work().route(type);
//        test();
        System.out.println("dsbdhjgvb fh");

    }

    public static void errorPrint(){
        String content = "Usaeg: java -jar run.jar type\n";
        System.out.println(content);
    }
}
