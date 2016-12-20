package com.thinkgem.jeesite.modules.oa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.ActEntity;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;

/**
 * 流程数据
 * @author lgx
 * @version 2016-12-19
 */
public class FlowData extends ActEntity<FlowData> {

	private static final long serialVersionUID = 1L;
    private String tableName;
    private Map<String,Object> datas;
    private String formNo;
    private String flowFlag;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, Object> getDatas() {
        return datas;
    }

    public void setDatas(Map<String, Object> datas) {
        this.datas = datas;
    }

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }

    public String getFlowFlag() {
        return flowFlag;
    }

    public void setFlowFlag(String flowFlag) {
        this.flowFlag = flowFlag;
    }
}