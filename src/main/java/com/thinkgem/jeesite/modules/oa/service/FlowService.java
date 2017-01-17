package com.thinkgem.jeesite.modules.oa.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.oa.dao.FlowDao;
import com.thinkgem.jeesite.modules.oa.entity.FlowData;
import com.thinkgem.jeesite.modules.oa.units.OConvertUtils;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.table.dao.OaPersonDefineTableColumnDao;
import com.thinkgem.jeesite.modules.table.dao.OaPersonDefineTableDao;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTableColumn;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;

/**
 * 审批Service
 * @author thinkgem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class FlowService extends CrudService<FlowDao, FlowData> {

	@Autowired
	private ActTaskService actTaskService;
    @Autowired
    private OaPersonDefineTableDao oaPersonDefineTableDao;
    @Autowired
    private OaPersonDefineTableColumnDao oaPersonDefineTableColumnDao;

    /**
     * 表单添加
     * @param flowData
     * @throws Exception
     */
    public void insertTable(FlowData flowData){
        String tableName = flowData.getTableName();
        Map<String, Object> data = flowData.getDatas();
        OaPersonDefineTable defineTable = oaPersonDefineTableDao.findByTableName(tableName, null);
        if (defineTable != null && data != null && StringUtils.isNotBlank(tableName)) {
            List<OaPersonDefineTableColumn> columns = oaPersonDefineTableColumnDao.findColumnListByTableId(defineTable.getId());
            dataAdapter(columns, data);
            String comma = "";
            StringBuffer insertKey = new StringBuffer();
            StringBuffer insertValue = new StringBuffer();
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                // 判断key是否为表配置的属性
                if (isContainsFieled(columns, entry.getKey())) {
                    insertKey.append(comma + entry.getKey());
                    if (entry.getValue() != null && entry.getValue().toString().length() > 0) {
                        insertValue.append(comma + entry.getValue());
                    } else {
                        insertValue.append(comma + "null");
                    }
                    comma = ", ";
                }
            }
            insertKey.append(",id,create_by,create_date,update_by,update_date,remarks,del_flag,proc_def_id");
            insertValue.append(comma + handleSqlValue(flowData.getId(),"varchar2"))
                    .append(comma + handleSqlValue(flowData.getCreateBy(), "varchar2"))
                    .append(comma + handleSqlValue(DateUtils.formatDateTime(flowData.getCreateDate()), "date"))
                    .append(comma + handleSqlValue(flowData.getUpdateBy(), "varchar2"))
                    .append(comma + handleSqlValue(DateUtils.formatDateTime(flowData.getUpdateDate()), "date"))
                    .append(comma + handleSqlValue(flowData.getRemarks(), "varchar2"))
                    .append(comma + handleSqlValue(flowData.getDelFlag(), "varchar2"))
                    .append(comma + handleSqlValue(flowData.getAct().getProcDefId(), "varchar2"));
            String sql = "INSERT INTO " + tableName + " (" + insertKey + ") VALUES (" + insertValue + ")";
            oaPersonDefineTableDao.executeSql(sql);
        }
    }
    /**
     * 表单添加
     * @param flowData
     * @throws Exception
     */
    public void updateTable(FlowData flowData) {
        String tableName = flowData.getTableName();
        Map<String, Object> data = flowData.getDatas();
        OaPersonDefineTable defineTable = oaPersonDefineTableDao.findByTableName(tableName, UserUtils.getUser().getOffice().getId());
        if (defineTable != null && data != null && StringUtils.isNotBlank(tableName)) {
            List<OaPersonDefineTableColumn> columns = oaPersonDefineTableColumnDao.findColumnListByTableId(defineTable.getId());
            dataAdapter(columns, data);
            String comma = "";
            StringBuffer updateValue = new StringBuffer();
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                // 判断key是否为表配置的属性
                if (isContainsFieled(columns, entry.getKey())) {
                    updateValue.append(comma + entry.getKey() + "=");
                    if (entry.getValue() != null && entry.getValue().toString().length() > 0) {
                        updateValue.append(entry.getValue());
                    } else {
                        updateValue.append("null");
                    }
                    comma = ", ";
                }
            }
            updateValue.append(comma + "update_by=" + handleSqlValue(flowData.getUpdateBy(), "varchar2"))
                    .append(",update_date=" + handleSqlValue(flowData.getUpdateDate() == null ? null : DateUtils.formatDateTime(flowData.getUpdateDate()), "date"));
            String sql = "UPDATE " + tableName + " SET " + updateValue + " where id='" + flowData.getId() + "'";
            oaPersonDefineTableDao.executeSql(sql);
        }
    }

	public List<Map<String,Object>> getFlowInfo(Map<String,String> paramMap) {
        String sql = getSelectSql(paramMap);
        if(StringUtils.isNotBlank(sql)) {
            return oaPersonDefineTableDao.getFlowInfo(sql);
        }
		return Lists.newArrayList();
	}

    public Page<Map<String,Object>> getPageFlowInfo(Page<FlowData> page,Map<String,String> paramMap){
        FlowData flow = new FlowData();
        flow.setPage(page);
        flow.setSql(getSelectSql(paramMap));
        List<Map<String,Object>> list = oaPersonDefineTableDao.getFlowInfo(flow);
        Page<Map<String,Object>> result = new Page<>(page.getPageNo(),page.getPageSize(),page.getCount());
        result.setList(list);
        return result;
    }

    public Map<String,Object> getOneInfo(Map<String,String> paramMap) {
        List<Map<String,Object>> infos = getFlowInfo(paramMap);
        if(infos != null && infos.size() > 0) {
            return infos.get(0);
        }
        return Maps.newHashMap();
    }


//
//	public Page<TestAudit> findPage(Page<TestAudit> page, TestAudit testAudit) {
//		testAudit.setPage(page);
//		page.setList(dao.findList(testAudit));
//		return page;
//	}
//
	/**
	 * 审核新增或编辑
	 * @param flowData
	 */
	@Transactional(readOnly = false)
	public void save(FlowData flowData) {
        User user= UserUtils.getUser();
        Map<String, Object> vars = handlerVar(flowData.getTableName(),flowData.getDatas());
          vars.put("PostGrade",user.getGrade());
          vars.put("parentId",user.getAcName());
          vars.put("dept",user.getAcDeptName());
		// 申请发起
		if (StringUtils.isBlank(flowData.getId())){
            flowData.preInsert();
			insertTable(flowData);
			// 启动流程

			actTaskService.startProcess(flowData.getFlowFlag(), flowData.getTableName(), flowData.getId(),"自定义流程",vars);
		}
		// 重新编辑申请
		else{
            flowData.preUpdate();
			updateTable(flowData);
            if(flowData.getAct().getComment() == null) {
                flowData.getAct().setComment("");
            }
            flowData.getAct().setComment(("yes".equals(flowData.getAct().getFlag())?"[重申] ":"[销毁] ")+flowData.getAct().getComment());

			// 完成流程任务
			vars.put("pass", "yes".equals(flowData.getAct().getFlag())? "1" : "0");
			actTaskService.complete(flowData.getAct().getTaskId(), flowData.getAct().getProcInsId(), flowData.getAct().getComment(), "自定义流程", vars);
            if("yes".equals(flowData.getAct().getFlag())) {
                OaPersonDefineTable table = oaPersonDefineTableDao.findByTableName(flowData.getTableName(), null);
                OaPersonDefineTableColumn param = new OaPersonDefineTableColumn(table);
                param.setIsAudit("1");
                List<OaPersonDefineTableColumn> columns = oaPersonDefineTableColumnDao.findList(param);
                StringBuilder sb = new StringBuilder("update " + flowData.getTableName() + " set ");
                String split = "";
                for(OaPersonDefineTableColumn column : columns) {
                    sb.append(split + column.getColumnName() + "=''");
                    split = ",";
                }
                sb.append(" where id='" + flowData.getId() + "'");
                oaPersonDefineTableDao.executeSql(sb.toString());
            }
		}
	}

	/**
	 * 审核审批保存
	 * @param flowData
	 */
	@Transactional(readOnly = false)
	public void auditSave(FlowData flowData) {
        User user= UserUtils.getUser();
		// 设置意见
        flowData.getAct().setComment(("yes".equals(flowData.getAct().getFlag())?"[同意] ":"[驳回] ")+flowData.getAct().getComment());
        flowData.preUpdate();
		// 对不同环节的业务逻辑进行操作
		String taskDefKey = flowData.getAct().getTaskDefKey();

		// 审核环节
		if (taskDefKey.startsWith("audit") || "apply_execute".equals(taskDefKey)){
            OaPersonDefineTable table = oaPersonDefineTableDao.findByTableName(flowData.getTableName(), null);
            OaPersonDefineTableColumn param = new OaPersonDefineTableColumn(table);
            param.setIsAudit("1");
            List<OaPersonDefineTableColumn> columns = oaPersonDefineTableColumnDao.findList(param);
            for(OaPersonDefineTableColumn column : columns) {
                if(taskDefKey.equals(column.getAuditPost())) {
                    String sql = "update " + flowData.getTableName() + " set "
                            + column.getColumnName() + "='" + flowData.getAct().getComment()
                            + "' where id='" + flowData.getId() + "'";
                    oaPersonDefineTableDao.executeSql(sql);
                    break;
                }
            }

        }
		else if ("apply_end".equals(taskDefKey)){}
		// 未知环节，直接返回
		else{
			return;
		}
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(flowData.getAct().getFlag())? "1" : "0");
//        vars.put("parentId",user.getAcName());
		actTaskService.complete(flowData.getAct().getTaskId(), flowData.getAct().getProcInsId(), flowData.getAct().getComment(), vars);
	}


    /**
     * 数据类型适配-根据表单配置的字段类型将前台传递的值将map-value转换成相应的类型
     * @param columns
     * @param data 数据
     */
    private Map<String, Object> dataAdapter(List<OaPersonDefineTableColumn> columns,Map<String, Object> data) {
        //step.2 迭代将要持久化的数据
        for(OaPersonDefineTableColumn column : columns){
            //根据表单配置的字段名 获取 前台数据
            String columnName = column.getColumnName();
            Object beforeV = data.get(columnName);
            //如果值不为空
            if(OConvertUtils.isNotEmpty(beforeV)){
                //获取字段配置-字段类型
                String type = column.getColumnType();
                //根据类型进行值的适配
                data.put(columnName,handleSqlValue(beforeV,type));
            }
        }
        return data;
    }

    private String handleSqlValue(Object value,String type){
        if(value == null) return null;
        Object newV = "";
        if("date".equalsIgnoreCase(type) || "datetime".equalsIgnoreCase(type)){
            newV = "to_date('" + String.valueOf(value) + "','yyyy-MM-dd HH24:mi:ss')";
        }else if("number".equalsIgnoreCase(type)){
            newV = new Double(0);
            try{
                newV = Double.parseDouble(String.valueOf(value));
            }catch (Exception e) {
                e.printStackTrace();
            }
        } else if("varchar2".equalsIgnoreCase(type)) {
            newV = "'" + String.valueOf(value) + "'";
        } else {
            return null;
        }
        return String.valueOf(newV);
    }
    //判断key是否为表配置的属性
    private boolean isContainsFieled(List<OaPersonDefineTableColumn> columns,String fieledName){
        for(int i=0;i<columns.size();i++) {
            if (columns.get(i).getColumnName().equals(fieledName.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    private String getSelectSql(Map<String,String> paramMap) {
        String tableName = paramMap.get("tableName");
        String procInsId = paramMap.get("procInsId");
        String id = paramMap.get("id");
        String createBy = paramMap.get("createBy");
        String procDefId = paramMap.get("procDefId");
        StringBuilder sql = null;
        OaPersonDefineTable defineTable = oaPersonDefineTableDao.findByTableName(tableName,null);
        if(defineTable != null) {
            List<OaPersonDefineTableColumn> columns = oaPersonDefineTableColumnDao.findColumnListByTableId(defineTable.getId());
            if(columns != null && columns.size() >0) {
                sql = new StringBuilder("select id,proc_ins_id procInsId");
                for (OaPersonDefineTableColumn column : columns) {
                    if ("DATE".equals(column.getColumnType().toUpperCase())) {
                        sql.append(",replace(to_char(" + column.getColumnName() + ",'yyyy-MM-dd HH24:mi:ss'),' 00:00:00','') " + column.getColumnName());
                    } else {
                        sql.append("," + column.getColumnName());
                    }
                }
                sql.append(" from " + tableName + " where del_flag = '0'");
                if (StringUtils.isNotBlank(id)) {
                    sql.append(" and id='" + id + "' ");
                }
                if (StringUtils.isNotBlank(procInsId)) {
                    sql.append(" and proc_ins_id='" + procInsId + "' ");
                }
                if (StringUtils.isNotBlank(createBy)) {
                    sql.append(" and create_by='" + createBy + "' ");
                }
                if (StringUtils.isNotBlank(procDefId)) {
                    sql.append(" and proc_def_id='" + procDefId + "' ");
                }
            }
        }
        if(sql != null) return sql.toString();
        return null;
    }

    private Map<String, Object> handlerVar(String tableName,Map<String,Object> datas) {
        Map<String, Object> vars = Maps.newHashMap();
        if(StringUtils.isNotBlank(tableName) && datas != null && datas.keySet().size() > 0) {
            List<OaPersonDefineTableColumn> columns = oaPersonDefineTableColumnDao.getColumns(tableName);
            if (columns != null && columns.size() > 0) {
                for (OaPersonDefineTableColumn column : columns) {
                    vars.put(column.getColumnName(),datas.get(column.getColumnName()));
                }
            }
        }
        return vars;
    }
}
