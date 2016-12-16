/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.form.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.form.entity.Component;
import com.thinkgem.jeesite.modules.form.entity.Components;
import com.thinkgem.jeesite.modules.form.util.ComponentUtils;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTableColumn;
import com.thinkgem.jeesite.modules.table.service.OaControlTypeService;
import com.thinkgem.jeesite.modules.table.service.OaPersonDefineTableService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.form.entity.OaFormMaster;
import com.thinkgem.jeesite.modules.form.service.OaFormMasterService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 编辑器设计表单Controller
 * @author chenxy
 * @version 2016-11-18
 */
@Controller
@RequestMapping(value = "${adminPath}/form/oaFormMaster")
public class OaFormMasterController extends BaseController {

	@Autowired
	private OaFormMasterService oaFormMasterService;

    @Autowired
    private OaPersonDefineTableService oaPersonDefineTableService;

    @Autowired
    private OaControlTypeService oaControlTypeService;

	
	@ModelAttribute
	public OaFormMaster get(@RequestParam(required=false) String id) {
		OaFormMaster entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaFormMasterService.get(id);
		}
		if (entity == null){
			entity = new OaFormMaster();
		}
		return entity;
	}
	
	@RequiresPermissions("form:oaFormMaster:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaFormMaster oaFormMaster, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<OaFormMaster> page = oaFormMasterService.findPage(new Page<OaFormMaster>(request, response), oaFormMaster);
		model.addAttribute("page", page);
		return "modules/form/oaFormMasterList";
	}

	@RequiresPermissions("form:oaFormMaster:view")
	@RequestMapping(value = "form")
	public String form(OaFormMaster oaFormMaster, Model model) {
		model.addAttribute("oaFormMaster", oaFormMaster);
		return "modules/form/oaFormMasterForm";
	}

    @RequiresPermissions("form:oaFormMaster:view")
    @RequestMapping(value = "view")
    public String view(OaFormMaster oaFormMaster, Model model) {
        model.addAttribute("oaFormMaster", oaFormMaster);
        return "modules/oa/flowView";
    }

	@RequiresPermissions("form:oaFormMaster:edit")
	@RequestMapping(value = "save")
	public String save(OaFormMaster oaFormMaster, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaFormMaster)){
			return form(oaFormMaster, model);
		}
        OaPersonDefineTable oaPersonDefineTable=this.oaPersonDefineTableService.findByTableName(oaFormMaster.getTableName(), oaFormMaster.getOffice().getId());
        List<OaPersonDefineTableColumn> oaPersonDefineTableColumns=this.oaPersonDefineTableService.findColumnListByTableId(oaPersonDefineTable.getId());
        String tableContent=oaFormMaster.getContent();
        for(OaPersonDefineTableColumn column : oaPersonDefineTableColumns){
            if(column != null && !"".equals(column)){
                Component component = ComponentUtils.getComponent(column.getControlTypeId());
                String content = "";
                if(component != null) {
                    content = component.getContent().replace("name=\"\"", "name=\"" + column.getColumnName() + "\"").replace("value=\"\"", "value=\"${" + column.getColumnName() + "}\"");
                }
                tableContent=tableContent.replace("[" + column.getColumnComment() + "]",content);
            }
        }
        oaFormMaster.setContent(tableContent);
        if (tableContent != null){
            oaFormMaster.setContent(StringEscapeUtils.unescapeHtml4(tableContent));
        }
 		oaFormMasterService.save(oaFormMaster);
		addMessage(redirectAttributes, "保存编辑器设计表单成功");
		return "redirect:"+Global.getAdminPath()+"/form/oaFormMaster/?repage";
	}
	
	@RequiresPermissions("form:oaFormMaster:edit")
	@RequestMapping(value = "delete")
	public String delete(OaFormMaster oaFormMaster, RedirectAttributes redirectAttributes) {
		oaFormMasterService.delete(oaFormMaster);
		addMessage(redirectAttributes, "删除编辑器设计表单成功");
		return "redirect:"+Global.getAdminPath()+"/form/oaFormMaster/?repage";
	}

}