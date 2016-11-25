/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.cms.service.CategoryService;
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
import com.thinkgem.jeesite.modules.oa.entity.OaSummaryPermission;
import com.thinkgem.jeesite.modules.oa.service.OaSummaryPermissionService;

/**
 * 评阅管理Controller
 * @author yangruidong
 * @version 2016-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaSummaryPermission")
public class OaSummaryPermissionController extends BaseController {



	@Autowired
	private OaSummaryPermissionService oaSummaryPermissionService;

    @Autowired
    private CategoryService categoryService;



	@ModelAttribute
	public OaSummaryPermission get(@RequestParam(required=false) String id) {
		OaSummaryPermission entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaSummaryPermissionService.get(id);
		}
		if (entity == null){
			entity = new OaSummaryPermission();
		}

		return entity;
	}
    @RequiresPermissions("oa:oaSummaryPermission:view")
    @RequestMapping(value = {"list", ""})
    public String index() {
        return "modules/oa/oaSummaryPermissionList";
    }



    @RequiresPermissions("oa:oaSummaryPermission:view")
    @RequestMapping(value = "form")
    public String form(OaSummaryPermission oaSummaryPermission, Model model) {
        model.addAttribute("oaSummaryPermission", oaSummaryPermission);
        return "modules/oa/oaSummaryPermissionForm";
    }

//    @RequiresPermissions("oa:oaSummaryPermission:edit")
    @RequestMapping(value = "save")
    public String save(OaSummaryPermission oaSummaryPermission,Model model,RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, oaSummaryPermission)){
            return form(oaSummaryPermission, model);
        }
        //获取被评阅人的所有id并根据逗号进行拆分
        String[]  ids=oaSummaryPermission.getEvaluateId().split(",");
        for(int i=0;i<ids.length;i++){
            OaSummaryPermission oaSummaryPermission1=new OaSummaryPermission();
            oaSummaryPermission1.setEvaluateId(ids[i]);
            oaSummaryPermission1.setEvaluateById(oaSummaryPermission.getEvaluateById());
            oaSummaryPermissionService.save(oaSummaryPermission1);
        }
        addMessage(redirectAttributes, "保存评阅成功");
        return "redirect:"+Global.getAdminPath()+"/oa/oaSummaryPermission/?repage";
    }
	
/*	@RequiresPermissions("oa:oaSummaryPermission:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaSummaryPermission oaSummaryPermission, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaSummaryPermission> page = oaSummaryPermissionService.findPage(new Page<OaSummaryPermission>(request, response), oaSummaryPermission); 
		model.addAttribute("page", page);
		return "modules/oa/oaSummaryPermissionList";
	}



	
	@RequiresPermissions("oa:oaSummaryPermission:edit")
	@RequestMapping(value = "delete")
	public String delete(OaSummaryPermission oaSummaryPermission, RedirectAttributes redirectAttributes) {
		oaSummaryPermissionService.delete(oaSummaryPermission);
		addMessage(redirectAttributes, "删除评阅管理成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaSummaryPermission/?repage";
	}*/

}