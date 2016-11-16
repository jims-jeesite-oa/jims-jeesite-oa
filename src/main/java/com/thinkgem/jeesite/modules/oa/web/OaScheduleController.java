/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.oa.entity.OaNotify;
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
import com.thinkgem.jeesite.modules.oa.entity.OaSchedule;
import com.thinkgem.jeesite.modules.oa.service.OaScheduleService;

/**
 * 保存日程安排Controller
 * @author yangruidong
 * @version 2016-11-15
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaSchedule")
public class OaScheduleController extends BaseController {

	@Autowired
	private OaScheduleService oaScheduleService;
	
	@ModelAttribute
	public OaSchedule get(@RequestParam(required=false) String id) {
		OaSchedule entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaScheduleService.get(id);
		}
		if (entity == null){
			entity = new OaSchedule();
		}
		return entity;
	}

    /**
     * 查询所有的日程安排
     * @param oaSchedule
     * @param request
     * @param response
     * @param model
     * @return
     */
	@RequiresPermissions("oa:oaSchedule:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaSchedule oaSchedule, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaSchedule> page = oaScheduleService.findPage(new Page<OaSchedule>(request, response), oaSchedule); 
		model.addAttribute("page", page);
		return "modules/oa/oaScheduleList";
	}

    /**
     * 我的通知列表
     */
    @RequestMapping(value = "self")
    public String selfList(OaSchedule oaSchedule, HttpServletRequest request, HttpServletResponse response, Model model) {
        oaSchedule.setSelf(true);
        Page<OaSchedule> page = oaScheduleService.find(new Page<OaSchedule>(request, response), oaSchedule);
        model.addAttribute("page", page);
        return "modules/oa/oaScheduleList";
    }

	@RequiresPermissions("oa:oaSchedule:view")
	@RequestMapping(value = "form")
	public String form(OaSchedule oaSchedule, Model model) {
		model.addAttribute("oaSchedule", oaSchedule);
		return "modules/oa/oaScheduleForm";
	}


    /**
     *日程安排完成
     * @param oaSchedule
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("oa:oaSchedule:edit")
    @RequestMapping(value = "complete")
    public String complete(OaSchedule oaSchedule, RedirectAttributes redirectAttributes) {
        if (StringUtils.isNotBlank(oaSchedule.getId())){
           oaScheduleService.complete(oaSchedule);
        }
        addMessage(redirectAttributes, "日程安排已完成");
        return "redirect:"+Global.getAdminPath()+"/oa/oaSchedule/?repage";
    }

    /**
     * 保存日程安排
     * @param oaSchedule
     * @param model
     * @param redirectAttributes
     * @return
     */
	@RequiresPermissions("oa:oaSchedule:edit")
	@RequestMapping(value = "save")
	public String save(OaSchedule oaSchedule, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaSchedule)){
			return form(oaSchedule, model);
		}
        oaSchedule.setFlag("0");
		oaScheduleService.save(oaSchedule);
		addMessage(redirectAttributes, "保存日程安排成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaSchedule/?repage";
	}

    /**
     * 删除日程安排
     * @param oaSchedule
     * @param redirectAttributes
     * @return
     */
	@RequiresPermissions("oa:oaSchedule:edit")
	@RequestMapping(value = "delete")
	public String delete(OaSchedule oaSchedule, RedirectAttributes redirectAttributes) {
		oaScheduleService.delete(oaSchedule);
		addMessage(redirectAttributes, "删除日程安排成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaSchedule/?repage";
	}

}