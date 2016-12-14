/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.table.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.modules.table.entity.OaControlType;
import com.thinkgem.jeesite.modules.table.service.OaControlTypeService;

/**
 * 控件添加Controller
 * @author chenxy
 * @version 2016-11-24
 */
@Controller
@RequestMapping(value = "${adminPath}/table/oaControlType")
public class OaControlTypeController extends BaseController {

	@Autowired
	private OaControlTypeService oaControlTypeService;
	
	@ModelAttribute
	public OaControlType get(@RequestParam(required=false) String id) {
		OaControlType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaControlTypeService.get(id);
		}
		if (entity == null){
			entity = new OaControlType();
		}
		return entity;
	}
	
	@RequiresPermissions("table:oaControlType:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaControlType oaControlType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaControlType> page = oaControlTypeService.findPage(new Page<OaControlType>(request, response), oaControlType); 
		model.addAttribute("page", page);
		return "modules/table/oaControlTypeList";
	}

	@RequiresPermissions("table:oaControlType:view")
	@RequestMapping(value = "form")
	public String form(OaControlType oaControlType, Model model) {
		model.addAttribute("oaControlType", oaControlType);
		return "modules/table/oaControlTypeForm";
	}

	@RequiresPermissions("table:oaControlType:edit")
	@RequestMapping(value = "save")
	public String save(OaControlType oaControlType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaControlType)){
			return form(oaControlType, model);
		}
		oaControlTypeService.save(oaControlType);
		addMessage(redirectAttributes, "保存控件成功");
		return "redirect:"+Global.getAdminPath()+"/table/oaControlType/?repage";
	}
	
	@RequiresPermissions("table:oaControlType:edit")
	@RequestMapping(value = "delete")
	public String delete(OaControlType oaControlType, RedirectAttributes redirectAttributes) {
		oaControlTypeService.delete(oaControlType);
		addMessage(redirectAttributes, "删除控件成功");
		return "redirect:"+Global.getAdminPath()+"/table/oaControlType/?repage";
	}

}