package com.thinkgem.jeesite.modules.oa.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.act.utils.ProcessDefUtils;
import com.thinkgem.jeesite.modules.form.entity.Component;
import com.thinkgem.jeesite.modules.form.entity.OaFormMaster;
import com.thinkgem.jeesite.modules.form.service.OaFormMasterService;
import com.thinkgem.jeesite.modules.form.util.ComponentUtils;
import com.thinkgem.jeesite.modules.oa.entity.FlowData;
import com.thinkgem.jeesite.modules.oa.service.FlowService;
import com.thinkgem.jeesite.modules.oa.units.CommonUtils;
import com.thinkgem.jeesite.modules.oa.units.FreemarkerUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTableColumn;
import com.thinkgem.jeesite.modules.table.service.OaPersonDefineTableService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程Controller
 * @author lgx
 * @version 2016-12-12
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/flow")
public class FlowController extends BaseController {

    @Autowired
    private ActTaskService actTaskService;
	@Autowired
	private FlowService flowService;
    @Autowired
    private OaFormMasterService oaFormMasterService;
    @Autowired
    private OaPersonDefineTableService oaPersonDefineTableService;

	@RequestMapping(value = "form")
	public void form(FlowData flow, Model model,HttpServletResponse response) {
        String view;
        if(StringUtils.isNotBlank(flow.getShowType())) {
            view = flow.getShowType();
        } else {
            view = "flowForm";
            // 查看审批申请单
            if (StringUtils.isNotBlank(flow.getId())) {
                // 环节编号
                String taskDefKey = flow.getAct().getTaskDefKey();
                // 查看
                if (flow.getAct().isFinishTask()) {
                    view = "flowView";
                }
                // 修改环节
                else if ("modify".equals(taskDefKey)) {
                    view = "flowForm";
                }
                // 审核
                else if (taskDefKey.startsWith("audit") || "apply_end".equals(taskDefKey)) {
                    view = "flowAudit";
                }
            }
        }
        OaFormMaster form = oaFormMasterService.findByNo(flow.getFormNo(), null);
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("tableName",form.getTableName());
        paramMap.put("procInsId",flow.getAct().getProcInsId());
        paramMap.put("id",flow.getId());
        flow.setDatas(flowService.getOneInfo(paramMap));
        flow.setTableName(form.getTableName());

        Component c = ComponentUtils.getComponent(view);
        initComponent(form,view);
        String html = c.getContent().replace("$flowTableInfo$",form.getContent());
        try {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(FreemarkerUtils.process(html,toMap(flow)));
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

    @RequestMapping(value = "save")
    public String save(FlowData flowData, HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
        String message = null;
        Map data = request.getParameterMap();
        if (data != null) {
            data = CommonUtils.mapConvert(data);
            String tableName = (String) data.get("tableName");
            String[] filterName = {"tableName", "act.taskId", "act.taskName", "act.taskDefKey",
                    "act.procInsId", "act.procDefId", "act.flag", "id", ""};
            data = CommonUtils.attributeMapFilter(data, filterName);
            String procDefId = flowData.getAct().getProcDefId();
            flowData.setFlowFlag(procDefId.substring(0,procDefId.indexOf(":")));
            flowData.setTableName(tableName);
            flowData.setDatas(data);
            try {
                try {
                    flowService.save(flowData);
                    message = "业务提交成功";
                } catch (Exception e) {
                    e.printStackTrace();
                    message = "业务提交失败";
                }
            } catch (Exception e) {
                e.printStackTrace();
                message = e.getMessage();
            }
        }
        addMessage(redirectAttributes, message);
        return "redirect:" + adminPath + "/act/task/todo/";
    }

    @RequestMapping(value = "saveAudit")
    public String saveAudit(FlowData flowData, Model model,HttpServletResponse response) {
        if (StringUtils.isBlank(flowData.getAct().getFlag())
                || StringUtils.isBlank(flowData.getAct().getComment())){
            addMessage(model, "请填写审核意见。");
            form(flowData, model,response);
        }
        flowService.auditSave(flowData);
        return "redirect:" + adminPath + "/act/task/todo/";
    }

    @RequestMapping(value = "myFlow")
    public String myFlow(String procDefId,HttpServletRequest request,HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        String html = "";
        Map<String,Object> map = new HashMap<>();

        List<Object[]> processList = ProcessDefUtils.processList(null);
        //默认选择第一个流程
        if(StringUtils.isBlank(procDefId)) {
            ProcessDefinition process = (ProcessDefinition)processList.get(1)[0];
            procDefId = process.getId();
        }
        //自定义流程HTML
        StringBuilder selfFlowHTML = new StringBuilder();
        for(Object[] objs : processList) {
            ProcessDefinition process = (ProcessDefinition)objs[0];
            selfFlowHTML.append("<option " + (procDefId.equals(process.getId()) ? "selected=\"selected\"" : "")
                    +" value=\"" + process.getId() + "\">" + process.getName() +"</option>");
        }

        String formKey = actTaskService.getFormKey(procDefId, null);
        if(StringUtils.isNotBlank(formKey) && !"/404".equals(formKey)) {
            OaFormMaster form = oaFormMasterService.findByNo(formKey, null);
            if(form != null) {
                String tableName = form.getTableName();
                map.put("tableName", tableName);
                map.put("formNo", form.getFormNo());
                OaPersonDefineTable table = oaPersonDefineTableService.findByTableName(tableName, null);
                OaPersonDefineTableColumn param = new OaPersonDefineTableColumn();
                param.setIsShow("1");
                param.setTable(table);
                List<OaPersonDefineTableColumn> columns = oaPersonDefineTableService.findColumnList(param);
                StringBuilder theadHTML = new StringBuilder();
                StringBuilder tbodyHTML = new StringBuilder();
                for(OaPersonDefineTableColumn column : columns) {
                    theadHTML.append("<th>" + column.getColumnComment() + "</th>");
                    tbodyHTML.append("<td>${item." + column.getColumnName() + "}</td>");
                }

                Map<String,String> paramMap = new HashMap<>();
                paramMap.put("tableName",form.getTableName());
                User user = UserUtils.getUser();
                if (!user.isAdmin()){
                    paramMap.put("createBy",user.getId());
                }
                Page<Map<String,Object>> page = flowService.getPageFlowInfo(new Page<Map<String, Object>>(request,response),paramMap);
                List<Map<String,Object>> flowInfo = page.getList();

                map.put("flowInfo", flowInfo);
                map.put("page", page);

                Component c = ComponentUtils.getComponent("myFlow");
                html = c.getContent().replace("$selfFlowHTML$", selfFlowHTML.toString())
                        .replace("$tbodyHTML$", tbodyHTML.toString())
                        .replace("$theadHTML$", theadHTML.toString());
            }
        }
        try {
            map.put("ctx", Global.getAdminPath());
            response.getWriter().print(FreemarkerUtils.process(html,map));
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除流程信息
     * @param tableName
     * @param id
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "deleteInfo")
    public String deleteInfo(String tableName,String id,RedirectAttributes redirectAttributes) {
        oaPersonDefineTableService.deleteInfo(tableName,id);
        addMessage(redirectAttributes, "删除已发起流程成功");
        return "redirect:"+Global.getAdminPath()+"/oa/flow/myFlow?repage";
    }

    /**
     * 表单内存处理掉[...]
     * @param oaFormMaster
     * @param view
     */
    private void initComponent(OaFormMaster oaFormMaster,String view){
        OaPersonDefineTable oaPersonDefineTable=this.oaPersonDefineTableService.findByTableName(oaFormMaster.getTableName(), null);
        List<OaPersonDefineTableColumn> oaPersonDefineTableColumns=this.oaPersonDefineTableService.findColumnListByTableId(oaPersonDefineTable.getId());
        String tableContent=oaFormMaster.getContent();
        for(OaPersonDefineTableColumn column : oaPersonDefineTableColumns){
            if(column != null && !"".equals(column)){
                String content = "";
                if("flowForm".equals(view) && !"REMARK".equalsIgnoreCase(column.getColumnType())){
                    Component component = ComponentUtils.getComponent(column.getControlTypeId());
                    if(component != null) {
                        content = component.getContent().replace("name=\"\"", "name=\"" + column.getColumnName() + "\"").replace("value=\"\"", "value=\"${" + column.getColumnName() + "}\"");
                    }
                } else {
                    content = "${" + column.getColumnName() + "}";
                }
                tableContent=tableContent.replace("[" + column.getColumnComment() + "]",content);
            }
        }
        oaFormMaster.setContent(tableContent);
    }

    /**
     * flowData中数据放入到Map
     * @param flowData
     * @return
     */
    private Map<String,Object> toMap(FlowData flowData){
        Map<String,Object> map = flowData.getDatas();
        if(map == null) {
            map = new HashMap<>();
        }
        map.put("tableName",flowData.getTableName());
        map.put("id",flowData.getId());
        map.put("formNo",flowData.getFormNo());
        map.put("ctx", Global.getAdminPath());
        Act act = flowData.getAct();
        if(act != null){
            if(act.getTaskName() != null) {
                act.setTaskName(act.getTaskName());
            }
            map.put("act",act);
        }
        return map;
    }
}
