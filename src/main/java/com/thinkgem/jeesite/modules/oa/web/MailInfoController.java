package com.thinkgem.jeesite.modules.oa.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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
import com.thinkgem.jeesite.modules.oa.entity.MailInfo;
import com.thinkgem.jeesite.modules.oa.service.MailInfoService;

import java.util.Date;

/**
 * 邮件信息Controller
 * @author lgx
 * @version 2016-11-24
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/mailInfo")
public class MailInfoController extends BaseController {

	@Autowired
	private MailInfoService mailInfoService;
	
	@ModelAttribute
	public MailInfo get(@RequestParam(required=false) String id) {
		MailInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = mailInfoService.get(id);
		}
		if (entity == null){
			entity = new MailInfo();
		}
		return entity;
	}

    /**
     * 根据邮件人、邮件状态获取邮件信息
     * @param mailInfo
     * @param request
     * @param response
     * @param model
     * @return
     */
	@RequestMapping(value = {"list", ""})
	public String list(MailInfo mailInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
        mailInfo.setOwnId(UserUtils.getUser().getId());
        if(mailInfo.getState() == null){
            mailInfo.setState("INBOX");
        }
        Page<MailInfo> page = mailInfoService.findPage(new Page<MailInfo>(request, response), mailInfo);
		model.addAttribute("page", page);
		return "modules/oa/mailInfoList";
	}

    /**
     * 邮件信息查看
     * @param mailInfo
     * @param model
     * @return
     */
	@RequestMapping(value = "info")
	public String info(MailInfo mailInfo, Model model) {
		model.addAttribute("mailInfo", mailInfo);
		return "modules/oa/mailInfoForm";
	}

    /**
     * 发送邮件
     * @param mailInfo
     * @param model
     * @param redirectAttributes
     * @return
     */
	@RequestMapping(value = "send")
	public String send(MailInfo mailInfo, Model model, RedirectAttributes redirectAttributes) {
        mailInfo.setSenderId(UserUtils.getUser().getId());
        mailInfo.setTime(new Date());
        if (!beanValidator(model, mailInfo)){
			return info(mailInfo, model);
		}
		mailInfoService.send(mailInfo);
		addMessage(redirectAttributes, "邮件发送成功");
		return "modules/oa/mailInfoList";
	}

    /**
     * 保存邮件到草稿箱
     * @param mailInfo
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "saveDrafts")
    public String saveDrafts(MailInfo mailInfo, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, mailInfo)){
            return info(mailInfo, model);
        }
        mailInfoService.saveDrafts(mailInfo);
        addMessage(redirectAttributes, "邮件成功保存到邮件箱");
        return "modules/oa/mailInfoList";
    }

    /**
     * 删除邮件
     * @param mailInfo
     * @param redirectAttributes
     * @return
     */
	@RequestMapping(value = "delete")
	public String delete(MailInfo mailInfo, RedirectAttributes redirectAttributes) {
		mailInfoService.delete(mailInfo);
		addMessage(redirectAttributes, "删除邮件成功");
		return "modules/oa/mailInfoList";
	}

    /**
     * 彻底删除邮件
     * @param mailInfo
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "thoroughDelete")
    public String thoroughDelete(MailInfo mailInfo, RedirectAttributes redirectAttributes) {
        mailInfoService.thoroughDelete(mailInfo);
        addMessage(redirectAttributes, "彻底删除邮件成功");
        return "modules/oa/mailInfoList";
    }

}