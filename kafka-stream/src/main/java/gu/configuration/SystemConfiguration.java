package gu.configuration;

import gu.App;
import gu.constant.Constant;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author gu
 * @date 2019/4/1 11:43
 */
public class SystemConfiguration {

    public String rootdir = "";

    public String tempdir = "";

    public String hadoopUsername;

    String ocrUrlStep1 = "";
    String ocrUrlStep2 = "";
    String ocrUrlStep3 = "";

    String applicationId = "";
    String bootstrapServers = "";
    String inputTopic = "";
    String outputTopic = "";

    public SystemConfiguration(){
        init();
    }

    private void init(){
        String jarpath = new App().getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        rootdir = jarpath.substring(0,jarpath.lastIndexOf(Constant.LEFT_SLASH));
        tempdir = rootdir+File.separator+Constant.TEMP;
        if(!new File(tempdir).exists()){
            new File(tempdir).mkdir();
        }

        InputStreamReader reader =new InputStreamReader(SystemConfiguration.class.getClassLoader().getResourceAsStream("application.properties"));
        Properties props = new Properties();
        try {
            props.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        hadoopUsername = props.getProperty(Constant.HADOOP_USERNAME);
        ocrUrlStep1 = props.getProperty(Constant.OCR_URL_STEP1);
        ocrUrlStep2 = props.getProperty(Constant.OCR_URL_STEP2);
        ocrUrlStep3 = props.getProperty(Constant.OCR_URL_STEP3);

        applicationId = props.getProperty(Constant.APPLICATION_ID_CONFIG);
        bootstrapServers = props.getProperty(Constant.BOOTSTRAP_SERVERS_CONFIG);
        inputTopic = props.getProperty(Constant.INPUT_TOPIC);
        outputTopic = props.getProperty(Constant.OUTPUT_TOPIC);

    }

    public String getRootdir() {
        return rootdir;
    }

    public String getTempdir() {
        return tempdir;
    }

    public String getHadoopUsername() {
        return hadoopUsername;
    }

    public String getOcrUrlStep1() {
        return ocrUrlStep1;
    }

    public String getOcrUrlStep2() {
        return ocrUrlStep2;
    }

    public String getOcrUrlStep3() {
        return ocrUrlStep3;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public String getInputTopic() {
        return inputTopic;
    }

    public String getOutputTopic() {
        return outputTopic;
    }

    @Override
    public String toString() {
        return "SystemConfiguration{" +
                "rootdir='" + rootdir + '\'' +
                ", tempdir='" + tempdir + '\'' +
                ", hadoopUsername='" + hadoopUsername + '\'' +
                ", ocrUrlStep1='" + ocrUrlStep1 + '\'' +
                ", ocrUrlStep2='" + ocrUrlStep2 + '\'' +
                ", ocrUrlStep3='" + ocrUrlStep3 + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", bootstrapServers='" + bootstrapServers + '\'' +
                ", inputTopic='" + inputTopic + '\'' +
                ", outputTopic='" + outputTopic + '\'' +
                '}';
    }
}
