package gu.service.impl;

import gu.configuration.SystemConfiguration;
import gu.constant.Constant;
import gu.service.HDFSService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

/**
 * @author gu
 * @date 2019/4/1 11:49
 */
public class HDFSServiceImpl implements HDFSService {

    SystemConfiguration systemConfiguration = new SystemConfiguration();

    FileSystem fileSystem = null;

    @Override
    public boolean copyToLocal(String hdfsdir, String localdir) {
        if(fileSystem==null){
            fileSystem = getFileSystem();
        }
        Path hdfsdirPath = new Path(hdfsdir);
        Path localdirPath = new Path(localdir);
        try {
            fileSystem.copyToLocalFile(hdfsdirPath,localdirPath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public FileSystem getFileSystem(){
//        System.setProperty(Constant.HADOOP_USER_NAME, confBean.getHadoopusername());
//        System.setProperty(Constant.HADOOP_USER_NAME, systemConfiguration.getHadoopUsername());
        System.setProperty(Constant.HADOOP_USER_NAME, "hdfs");
        Configuration conf = new Configuration();
        FileSystem fs = null;
        try {
            fs = FileSystem.get(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fs;
    }

    public static void main(String[] args) {
        String local = "D:\\临时文件\\aaaaaa";
        String hdfs = "/wenshutemp/2019/3/29/1553850426847100000014";
        new HDFSServiceImpl().copyToLocal(hdfs,local);
    }
}
