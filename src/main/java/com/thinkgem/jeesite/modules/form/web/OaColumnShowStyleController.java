/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.form.web;

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
import com.thinkgem.jeesite.modules.form.entity.OaColumnShowStyle;
import com.thinkgem.jeesite.modules.form.service.OaColumnShowStyleService;

/**
 * 字段显示方式设置Controller
 * @author chenxy
 * @version 2016-11-18
 */
@Controller
@RequestMapping(value = "${adminPath}/form/oaColumnShowStyle")
public class OaColumnShowStyleController extends BaseController {

	@Autowired
	private OaColumnShowStyleService oaColumnShowStyleService;
	
	@ModelAttribute
	public OaColumnShowStyle get(@RequestParam(required=false) String id) {
		OaColumnShowStyle entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaColumnShowStyleService.get(id);
		}
		if (entity == null){
			entity = new OaColumnShowStyle();
		}
		return entity;
	}
	
	@RequiresPermissions("form:oaColumnShowStyle:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaColumnShowStyle oaColumnShowStyle, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaColumnShowStyle> page = oaColumnShowStyleService.findPage(new Page<OaColumnShowStyle>(request, response), oaColumnShowStyle); 
		model.addAttribute("page", page);
		return "modules/form/oaColumnShowStyleList";
	}

	@RequiresPermissions("form:oaColumnShowStyle:view")
	@RequestMapping(value = "form")
	public String form(OaColumnShowStyle oaColumnShowStyle, Model model) {
		model.addAttribute("oaColumnShowStyle", oaColumnShowStyle);
		return "modules/form/oaColumnShowStyleForm";
	}

	@RequiresPermissions("form:oaColumnShowStyle:edit")
	@RequestMapping(value = "save")
	public String save(OaColumnShowStyle oaColumnShowStyle, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaColumnShowStyle)){
			return form(oaColumnShowStyle, model);
		}
		oaColumnShowStyleService.save(oaColumnShowStyle);
		addMessage(redirectAttributes, "保存字段显示方式成功");
		return "redirect:"+Global.getAdminPath()+"/form/oaColumnShowStyle/?repage";
	}
	
	@RequiresPermissions("form:oaColumnShowStyle:edit")
	@RequestMapping(value = "delete")
	public String delete(OaColumnShowStyle oaColumnShowStyle, RedirectAttributes redirectAttributes) {
		oaColumnShowStyleService.delete(oaColumnShowStyle);
		addMessage(redirectAttributes, "删除字段显示方式成功");
		return "redirect:"+Global.getAdminPath()+"/form/oaColumnShowStyle/?repage";
	}

}