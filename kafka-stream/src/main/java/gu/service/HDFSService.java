package gu.service;

/**
 * @author gu
 * @date 2019/4/1 11:48
 */
public interface HDFSService {

    boolean copyToLocal(String hdfsdir,String localdir);
}
