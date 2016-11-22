package com.thinkgem.jeesite.modules.oa.entity;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/11/19 0019.
 */
public class OaVo {

    private String date;
    private String  status;
    private String content;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
