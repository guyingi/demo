package gu.pojo;

import org.springframework.stereotype.Component;

@Component
public class ResultInfo {
    private String taskid;
    private Integer code;
    private String msg;

    public String getTaskid() {
        return taskid;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "taskid='" + taskid + '\'' +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
