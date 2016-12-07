package com.thinkgem.jeesite.modules.oa.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 邮件信息Controller
 *
 * @author lgx
 * @version 2016-11-24
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/mailInfo")
public class MailInfoController extends BaseController {

    @Autowired
    private MailInfoService mailInfoService;

    @ModelAttribute
    public MailInfo get(@RequestParam(required = false) String id) {
        MailInfo entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = mailInfoService.get(id);
        }
        if (entity == null) {
            entity = new MailInfo();
        }
        return entity;
    }


    /**
     * 根据邮件人、邮件状态查询已发送,已删除，草稿箱，收件箱的邮件
     *
     * @param mailInfo
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"listBySend"})
    public String listBySend(MailInfo mailInfo, HttpServletRequest request, HttpServletResponse response, Model model, String state) {
        mailInfo.setOwnId(UserUtils.getUser().getId());
        if (mailInfo.getState() == null) {
            mailInfo.setState("SENT");
        } else {
            mailInfo.setState(state);
        }
        Page<MailInfo> page = mailInfoService.findPage(new Page<MailInfo>(request, response), mailInfo);
        List<MailInfo> list = mailInfoService.findList(mailInfo);
        mailInfo.setReadMark("0");
        List<MailInfo> delete = mailInfoService.findList(mailInfo);
        page.setCount(list.size());
        page.setDelete(delete.size());
        model.addAttribute("page", page);
        if (StringUtils.equals(mailInfo.getState(), "DELETED")) {
            return "modules/oa/delete";
        } else if (StringUtils.equals(mailInfo.getState(), "DRAFTS")) {
            return "modules/oa/drafts";
        } else if (StringUtils.equals(mailInfo.getState(), "INBOX")) {
            return "modules/oa/receiving";
        } else {
            return "modules/oa/sent";
        }
    }


    /**
     * 写信
     *
     * @return
     */
    @RequestMapping(value = {"none", ""})
    public String none() {
        return "modules/oa/write";
    }


    /**
     * 邮件信息查看
     *
     * @param mailInfo
     * @param model
     * @return
     */
    @RequestMapping(value = "info")
    public String info(MailInfo mailInfo, Model model) {
        model.addAttribute("mailInfo", mailInfo);
        return "modules/oa/write";
    }

    /**
     * 发送邮件
     *
     * @param mailInfo
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "send")
    public String send(MailInfo mailInfo, Model model, RedirectAttributes redirectAttributes) {
        mailInfo.setSenderId(UserUtils.getUser().getId());
        mailInfo.setTime(new Date());
        if (!beanValidator(model, mailInfo)) {
            return info(mailInfo, model);
        }
        mailInfoService.send(mailInfo);
        addMessage(redirectAttributes, "邮件发送成功");
        return "modules/oa/success";
    }

    /**
     * 查看邮件
     *
     * @param mailInfo
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "find")
    public String find(MailInfo mailInfo, Model model, RedirectAttributes redirectAttributes, String id) {
        MailInfo mail = mailInfoService.getMail(id);
        model.addAttribute("mailInfo", mail);
        return "modules/oa/find";
    }

    /**
     * 保存邮件到草稿箱
     *
     * @param mailInfo
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "saveDrafts")
    public String saveDrafts(MailInfo mailInfo, Model model, RedirectAttributes redirectAttributes) {
        mailInfo.setSenderId(UserUtils.getUser().getId());
        mailInfo.setTime(new Date());
        if (!beanValidator(model, mailInfo)) {
            return info(mailInfo, model);
        }
        mailInfoService.saveDrafts(mailInfo);
        addMessage(redirectAttributes, "邮件成功保存到草稿箱");
        model.addAttribute("mailInfo", mailInfo);
        return "modules/oa/write";
    }

    /**
     * 删除邮件  并返回到界面
     *
     * @param mailInfo
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "move")
    public String move(MailInfo mailInfo, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request, HttpServletResponse response, String ids, String state) {
        String[] id = ids.split(",");
        if (id != null) {
            for (int i = 0; i < id.length; i++) {
                MailInfo mail = mailInfoService.get(id[i]);
                mail.setState("DELETED");
                mailInfoService.save(mail);
            }
            addMessage(redirectAttributes, "删除邮件成功");
        }
        //返回界面
        MailInfo mailInfo1 = new MailInfo();
        mailInfo1.setOwnId(UserUtils.getUser().getId());
        mailInfo1.setState(state);
        Page<MailInfo> page = mailInfoService.findPage(new Page<MailInfo>(request, response), mailInfo1);
        List<MailInfo> list = mailInfoService.findList(mailInfo1);
        mailInfo1.setReadMark("0");
        List<MailInfo> delete = mailInfoService.findList(mailInfo1);
        page.setCount(list.size());
        page.setDelete(delete.size());
        model.addAttribute("page", page);
        if (StringUtils.equals(mailInfo.getState(), "DELETED")) {
            return "modules/oa/delete";
        } else if (StringUtils.equals(mailInfo.getState(), "DRAFTS")) {
            return "modules/oa/drafts";
        } else if (StringUtils.equals(mailInfo.getState(), "INBOX")) {
            return "modules/oa/receiving";
        } else {
            return "modules/oa/sent";
        }
    }

    /**
     * 彻底删除邮件  并返回到界面
     *
     * @param mailInfo
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "thoroughDelete")
    public String thoroughDelete(MailInfo mailInfo, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request, HttpServletResponse response, String ids, String state) {
        String[] id = ids.split(",");
        if (id != null) {
            for (int i = 0; i < id.length; i++) {
                mailInfo.setId(id[i]);
                mailInfoService.delete(mailInfo);
            }
            addMessage(redirectAttributes, "彻底删除邮件成功");
        }
        //返回界面
        MailInfo mailInfo1 = new MailInfo();
        mailInfo1.setOwnId(UserUtils.getUser().getId());
        mailInfo1.setState(state);
        Page<MailInfo> page = mailInfoService.findPage(new Page<MailInfo>(request, response), mailInfo1);
        List<MailInfo> list = mailInfoService.findList(mailInfo1);
        mailInfo1.setReadMark("0");
        List<MailInfo> delete = mailInfoService.findList(mailInfo1);
        page.setCount(list.size());
        page.setDelete(delete.size());
        model.addAttribute("page", page);
        if (StringUtils.equals(mailInfo.getState(), "DELETED")) {
            return "modules/oa/delete";
        } else if (StringUtils.equals(mailInfo.getState(), "DRAFTS")) {
            return "modules/oa/drafts";
        } else if (StringUtils.equals(mailInfo.getState(), "INBOX")) {
            return "modules/oa/receiving";
        } else {
            return "modules/oa/sent";
        }
    }

    /**
     * 标记为 (已读/未读)
     *
     * @param
     * @param readMark
     * @return
     */
    @RequestMapping(value = "read")
    public String mark(MailInfo mailInfo, String ids, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request, HttpServletResponse response, String readMark, String state) {
        mailInfo.setOwnId(UserUtils.getUser().getId());
        if (StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(readMark)) {
            mailInfoService.readMark(ids, readMark);
        }

        MailInfo mailInfo1 = new MailInfo();
        mailInfo1.setState(state);
        mailInfo1.setOwnId(UserUtils.getUser().getId());
        Page<MailInfo> page = mailInfoService.findPage(new Page<MailInfo>(request, response), mailInfo1);
        List<MailInfo> list = mailInfoService.findList(mailInfo1);
        mailInfo1.setReadMark("0");
        List<MailInfo> delete = mailInfoService.findList(mailInfo1);
        page.setCount(list.size());
        page.setDelete(delete.size());
        model.addAttribute("page", page);
        if (StringUtils.equals(mailInfo.getState(), "DELETED")) {
            return "modules/oa/delete";
        } else if (StringUtils.equals(mailInfo.getState(), "DRAFTS")) {
            return "modules/oa/drafts";
        } else if (StringUtils.equals(mailInfo.getState(), "INBOX")) {
            return "modules/oa/receiving";
        } else {
            return "modules/oa/sent";
        }
    }


    /**
     * 移动到(收件箱/已发送)
     *
     * @param
     * @param readMark
     * @return
     */
    @RequestMapping(value = "remove")
    public String remove(MailInfo mailInfo, String ids, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request, HttpServletResponse response, String readMark, String state, String state1) {
        mailInfo.setOwnId(UserUtils.getUser().getId());
        String[] id = ids.split(",");
        if (id != null) {
            for (int i = 0; i < id.length; i++) {
                MailInfo mail = mailInfoService.get(id[i]);
                mail.setState(state1);
                mailInfoService.save(mail);
            }
            addMessage(redirectAttributes, "移动邮件成功");
        }


        MailInfo mailInfo1 = new MailInfo();
        mailInfo1.setOwnId(UserUtils.getUser().getId());
        mailInfo1.setState(state);
        Page<MailInfo> page = mailInfoService.findPage(new Page<MailInfo>(request, response), mailInfo1);
        List<MailInfo> list = mailInfoService.findList(mailInfo1);
        mailInfo1.setReadMark("0");
        List<MailInfo> delete = mailInfoService.findList(mailInfo1);
        page.setCount(list.size());
        page.setDelete(delete.size());
        model.addAttribute("page", page);
        if (StringUtils.equals(mailInfo.getState(), "DELETED")) {
            return "modules/oa/delete";
        } else if (StringUtils.equals(mailInfo.getState(), "DRAFTS")) {
            return "modules/oa/drafts";
        } else if (StringUtils.equals(mailInfo.getState(), "INBOX")) {
            return "modules/oa/receiving";
        } else {
            return "modules/oa/sent";
        }
    }

    /**
     * 标记为全部已读
     *
     * @return
     */
    @RequestMapping(value = "allRead")
    public String allRead() {
        mailInfoService.allRead(UserUtils.getUser().getId());
        return "modules/oa/mailInfoList";
    }

    /**
     * 草稿箱查看
     *
     * @return
     */
    @RequestMapping(value = "draftsById")
    public String draftsById(MailInfo mailInfo, Model model, String id) {
        mailInfo = mailInfoService.get(id);
        model.addAttribute("mailInfo",mailInfo);
        return "modules/oa/write";
    }

    /**
     * 联系人查看
     *
     * @return
     */
    @RequestMapping(value = "phone")
    public String phone(User user, Model model,HttpServletRequest request,HttpServletResponse response) {
        Page<User> page = mailInfoService.findPage1(new Page<User>(request, response), user);
        model.addAttribute("page",page);
        return "modules/oa/phone";
    }

    /**
     * 删除联系人
     *
     * @return
     */
    /*@RequestMapping(value = "deletePhone")
    public String deletePhone(User user, Model model,HttpServletRequest request,HttpServletResponse response,String ids) {
        String id[]=ids.split(",");
        for(int i=0;i<id.length;i++){

        }
        Page<User> page = mailInfoService.findPage1(new Page<User>(request, response), user);
        model.addAttribute("page",page);
        return "modules/oa/phone";
    }*/

   /* @RequestMapping(value = "writeX")
    public String write(User user, Model model,HttpServletRequest request,HttpServletResponse response,String ids) {
        String id[]=ids.split(",");
        for(int i=0;i<id.length;i++){

        }
        Page<User> page = mailInfoService.findPage1(new Page<User>(request, response), user);
        model.addAttribute("page",page);
        return "modules/oa/phone";
    }*/



}