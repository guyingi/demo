package gu.service;

import java.util.List;

/**
 * @author gu
 * @date 2019/4/1 14:40
 */
public interface RestService {

    public String uploadFile(List<String> fileList);

    public boolean result(String taskid);

    public boolean downlaod(String taskid,String zipfilepath);
}
