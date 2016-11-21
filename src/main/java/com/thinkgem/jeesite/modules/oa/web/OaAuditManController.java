/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

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
import com.thinkgem.jeesite.modules.oa.entity.OaAuditMan;
import com.thinkgem.jeesite.modules.oa.service.OaAuditManService;

/**
 * 新闻审核官Controller
 * @author lgx
 * @version 2016-11-17
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaAuditMan")
public class OaAuditManController extends BaseController {

	@Autowired
	private OaAuditManService oaAuditManService;
	
	@ModelAttribute
	public OaAuditMan get(@RequestParam(required=false) String id) {
		OaAuditMan entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaAuditManService.get(id);
		}
		if (entity == null){
			entity = new OaAuditMan();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaAuditMan:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaAuditMan oaAuditMan, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaAuditMan> page = oaAuditManService.findPage(new Page<OaAuditMan>(request, response), oaAuditMan); 
		model.addAttribute("page", page);
		return "modules/oa/oaAuditManList";
	}

	@RequiresPermissions("oa:oaAuditMan:view")
	@RequestMapping(value = "form")
	public String form(OaAuditMan oaAuditMan, Model model) {
		model.addAttribute("oaAuditMan", oaAuditMan);
		return "modules/oa/oaAuditManForm";
	}

	@RequiresPermissions("oa:oaAuditMan:edit")
	@RequestMapping(value = "save")
	public String save(OaAuditMan oaAuditMan, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaAuditMan)){
			return form(oaAuditMan, model);
		}
		oaAuditManService.save(oaAuditMan);
		addMessage(redirectAttributes, "保存新闻审核官成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaAuditMan/?repage";
	}
	
	@RequiresPermissions("oa:oaAuditMan:edit")
	@RequestMapping(value = "delete")
	public String delete(OaAuditMan oaAuditMan, RedirectAttributes redirectAttributes) {
		oaAuditManService.delete(oaAuditMan);
		addMessage(redirectAttributes, "删除新闻审核官成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaAuditMan/?repage";
	}

}