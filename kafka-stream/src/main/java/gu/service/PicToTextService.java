package gu.service;

import gu.pojo.InputMessage;
import gu.pojo.OutputMessage;

/**
 * @author gu
 * @date 2019/4/1 11:17
 */
public interface PicToTextService {

    OutputMessage doParse(InputMessage message);

    String getText(String tempfiledir);

    InputMessage convertRecordToMessage(String value);
}
