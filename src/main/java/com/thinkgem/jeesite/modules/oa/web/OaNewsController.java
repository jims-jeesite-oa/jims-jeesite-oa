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
import com.thinkgem.jeesite.modules.oa.entity.OaNews;
import com.thinkgem.jeesite.modules.oa.service.OaNewsService;

/**
 * 新闻公告Controller
 * @author lgx
 * @version 2016-11-17
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaNews")
public class OaNewsController extends BaseController {

	@Autowired
	private OaNewsService oaNewsService;
	
	@ModelAttribute
	public OaNews get(@RequestParam(required=false) String id) {
		OaNews entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaNewsService.get(id);
		}
		if (entity == null){
			entity = new OaNews();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaNews:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaNews oaNews, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaNews> page = oaNewsService.findPage(new Page<OaNews>(request, response), oaNews);
		model.addAttribute("page", page);
		return "modules/oa/oaNewsList";
	}

	@RequiresPermissions("oa:oaNews:view")
	@RequestMapping(value = "form")
	public String form(OaNews oaNews, Model model) {
        if(oaNews == null || oaNews.getAuditFlag() == null){
            oaNews.setAuditFlag("0");
        }
        if(oaNews == null || oaNews.getIsTopic() == null){
            oaNews.setIsTopic("0");
        }
		model.addAttribute("oaNews", oaNews);
		return "modules/oa/oaNewsForm";
	}

	@RequiresPermissions("oa:oaNews:edit")
	@RequestMapping(value = "save")
	public String save(OaNews oaNews, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaNews)){
			return form(oaNews, model);
		}
		oaNewsService.save(oaNews);
		addMessage(redirectAttributes, "保存新闻公告成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaNews/?repage";
	}

    @RequiresPermissions("oa:oaNews:edit")
    @RequestMapping(value = "toUp")
    public String toUp(String id, String type, HttpServletRequest request, HttpServletResponse response, Model model) {
        oaNewsService.toUp(id, type);
        return "redirect:"+Global.getAdminPath()+"/oa/oaNews/?repage";
    }
	
	@RequiresPermissions("oa:oaNews:edit")
	@RequestMapping(value = "delete")
	public String delete(OaNews oaNews, RedirectAttributes redirectAttributes) {
		oaNewsService.delete(oaNews);
		addMessage(redirectAttributes, "删除新闻公告成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaNews/?repage";
	}

}