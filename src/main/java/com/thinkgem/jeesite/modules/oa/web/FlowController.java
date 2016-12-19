package com.thinkgem.jeesite.modules.oa.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.form.entity.OaFormMaster;
import com.thinkgem.jeesite.modules.form.service.OaFormMasterService;
import com.thinkgem.jeesite.modules.oa.entity.FlowData;
import com.thinkgem.jeesite.modules.oa.service.FlowService;
import com.thinkgem.jeesite.modules.oa.units.CommonUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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

	@RequestMapping(value = "form")
	public String form(FlowData flow, Model model) {
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
        flow.setTableName(form.getTableName());
//        form.getContent();
		model.addAttribute("flow", flow);
		return "modules/oa/" + view;
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
    public String saveAudit(FlowData flowData, Model model) {
        if (StringUtils.isBlank(flowData.getAct().getFlag())
                || StringUtils.isBlank(flowData.getAct().getComment())){
            addMessage(model, "请填写审核意见。");
            return form(flowData, model);
        }
        flowService.auditSave(flowData);
        return "redirect:" + adminPath + "/act/task/todo/";
    }
}
