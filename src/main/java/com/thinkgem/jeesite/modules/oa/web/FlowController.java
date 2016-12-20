package com.thinkgem.jeesite.modules.oa.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.form.entity.Component;
import com.thinkgem.jeesite.modules.form.entity.OaFormMaster;
import com.thinkgem.jeesite.modules.form.service.OaFormMasterService;
import com.thinkgem.jeesite.modules.form.util.ComponentUtils;
import com.thinkgem.jeesite.modules.oa.entity.FlowData;
import com.thinkgem.jeesite.modules.oa.service.FlowService;
import com.thinkgem.jeesite.modules.oa.units.CommonUtils;
import com.thinkgem.jeesite.modules.oa.units.FreemarkerUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTableColumn;
import com.thinkgem.jeesite.modules.table.service.OaPersonDefineTableService;
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
	private FlowService flowService;
    @Autowired
    private OaFormMasterService oaFormMasterService;
    @Autowired
    private OaPersonDefineTableService oaPersonDefineTableService;

	@RequestMapping(value = "form")
	public void form(FlowData flow, Model model,HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");

		String view = "flowForm";
		// 查看审批申请单
		if (StringUtils.isNotBlank(flow.getId())){
			// 环节编号
			String taskDefKey = flow.getAct().getTaskDefKey();
			// 查看
			if(flow.getAct().isFinishTask()){
				view = "flowView";
			}
			// 修改环节
			else if ("modify".equals(taskDefKey)){
				view = "flowForm";
			}
            // 审核
            else if (taskDefKey.startsWith("audit") || "apply_end".equals(taskDefKey)){
                view = "flowAudit";
            }
		}
        OaFormMaster form = oaFormMasterService.findByNo(flow.getFormNo(), UserUtils.getUser().getOffice().getId());
        flow.setDatas(flowService.getByProcInsId(form.getTableName(), flow.getAct().getProcInsId()));
        flow.setTableName(form.getTableName());
        Component c = ComponentUtils.getComponent(view);
        initComponent(form,view);
        String html = c.getContent().replace("$flowTableInfo$",form.getContent());
        try {
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

    private void initComponent(OaFormMaster oaFormMaster,String view){
        OaPersonDefineTable oaPersonDefineTable=this.oaPersonDefineTableService.findByTableName(oaFormMaster.getTableName(), oaFormMaster.getOffice().getId());
        List<OaPersonDefineTableColumn> oaPersonDefineTableColumns=this.oaPersonDefineTableService.findColumnListByTableId(oaPersonDefineTable.getId());
        String tableContent=oaFormMaster.getContent();
        for(OaPersonDefineTableColumn column : oaPersonDefineTableColumns){
            if(column != null && !"".equals(column)){
                String content = "";
                if("flowForm".equals(view)){
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
            map.put("act",act);
        }
        return map;
    }
}
