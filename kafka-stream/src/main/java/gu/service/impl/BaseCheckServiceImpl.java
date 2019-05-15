package gu.service.impl;

import gu.pojo.InputMessage;
import gu.service.BaseCheckService;
import org.apache.commons.lang3.StringUtils;

/**
 * @author gu
 * @date 2019/4/1 11:26
 */
public class BaseCheckServiceImpl implements BaseCheckService {

    @Override
    public boolean checkMessage(InputMessage message) {
        boolean qualified = true;
        if(message==null)
            qualified = false;
        if(StringUtils.isBlank(message.getDatabase()))
            qualified = false;
        if(StringUtils.isBlank(message.getHdfspath()))
            qualified = false;
        if(StringUtils.isBlank(message.getHdfsuser()))
            qualified = false;
        if(StringUtils.isBlank(message.getHiveuser()))
            qualified = false;
        if(StringUtils.isBlank(message.getTablename()))
            qualified = false;
        return qualified;
    }
}
