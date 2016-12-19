package com.thinkgem.jeesite.modules.oa.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.oa.entity.MailAccount;
import com.thinkgem.jeesite.modules.oa.service.MailAccountService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
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

import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;

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

    @Autowired
    private MailAccountService mailAccountService;

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
            List<MailInfo> mailInfos = page.getList();
            List<MailInfo> infos = new ArrayList<>();
            for (int i = 0; i < mailInfos.size(); i++) {
                MailInfo mailInfo1 = mailInfoService.getMail(mailInfos.get(i).getId());
                mailInfo1.setFlag("0");
                infos.add(mailInfo1);
            }
            page.setList(infos);
            mailInfo.setReadMark("0");
            page.setCount(list.size());
            page.setDelete(delete.size());
            model.addAttribute("page", page);
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
        if (StringUtils.equals(mail.getReadMark(), "0")) {
            mail.setReadMark("1");
            mailInfoService.save(mail);
        }
        model.addAttribute("mailInfo", mail);
        return "modules/oa/find";
    }


    /**
     * 点击人员写信
     *
     * @param mailInfo
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "phoneWrite")
    public String phoneWrite(MailInfo mailInfo, Model model, RedirectAttributes redirectAttributes, String ids) {
        mailInfo = mailInfoService.getWrite(ids);
        mailInfo.setReceiverId(ids);
        model.addAttribute("mailInfo", mailInfo);
        return "modules/oa/write";
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
        mailInfo = mailInfoService.getDrafts(mailInfo.getId());
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
            List<MailInfo> mailInfos = page.getList();
            List<MailInfo> infos = new ArrayList<>();
            for (int i = 0; i < mailInfos.size(); i++) {
                MailInfo mailInfo2 = mailInfoService.getMail(mailInfos.get(i).getId());
                mailInfo2.setFlag("0");
                infos.add(mailInfo2);
            }
            page.setList(infos);
            page.setCount(list.size());
            page.setDelete(delete.size());
            model.addAttribute("page", page);
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
            List<MailInfo> mailInfos = page.getList();
            List<MailInfo> infos = new ArrayList<>();
            for (int i = 0; i < mailInfos.size(); i++) {
                MailInfo mailInfo2 = mailInfoService.getMail(mailInfos.get(i).getId());
                mailInfo2.setFlag("0");
                infos.add(mailInfo2);
            }
            page.setList(infos);
            page.setCount(list.size());
            page.setDelete(delete.size());
            model.addAttribute("page", page);
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
            List<MailInfo> mailInfos = page.getList();
            List<MailInfo> infos = new ArrayList<>();
            for (int i = 0; i < mailInfos.size(); i++) {
                MailInfo mailInfo2 = mailInfoService.getMail(mailInfos.get(i).getId());
                mailInfo2.setFlag("0");
                infos.add(mailInfo2);
            }
            page.setList(infos);
            page.setCount(list.size());
            page.setDelete(delete.size());
            model.addAttribute("page", page);
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
            List<MailInfo> mailInfos = page.getList();
            List<MailInfo> infos = new ArrayList<>();
            for (int i = 0; i < mailInfos.size(); i++) {
                MailInfo mailInfo2 = mailInfoService.getMail(mailInfos.get(i).getId());
                mailInfo1.setFlag("0");
                infos.add(mailInfo2);
            }
            page.setList(infos);
            page.setCount(list.size());
            page.setDelete(delete.size());
            model.addAttribute("page", page);
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
        mailInfo = mailInfoService.getDrafts(id);


        model.addAttribute("mailInfo", mailInfo);
        return "modules/oa/write";
    }

    /**
     * 联系人查看
     *
     * @return
     */
    @RequestMapping(value = "phone")
    public String phone(User user, Model model, HttpServletRequest request, HttpServletResponse response) {
        Page<User> page = mailInfoService.findPage1(new Page<User>(request, response), user);
        model.addAttribute("page", page);
        return "modules/oa/phone";
    }


    /**
     * 邮件帐户设置
     *
     * @return
     */
    @RequestMapping(value = {"account", ""})
    public String account(MailAccount mailAccount, Model model) {
        mailAccount.setLoginId(UserUtils.getUser().getId());
        List<MailAccount> mailAccounts = mailAccountService.findList(mailAccount);
        if (mailAccounts.size() > 0) {
            model.addAttribute("mailAccount", mailAccounts.get(0));
        } else {
            model.addAttribute("mailAccount", new MailAccount());
        }
        return "modules/oa/mail_account";
    }


    /**
     * 保存邮件帐户设置
     *
     * @return
     */
    @RequestMapping(value = {"saveAccount", ""})
    public String saveAccount(MailAccount mailAccount, Model model) {
        mailAccount.setLoginId(UserUtils.getUser().getId());
        mailAccountService.save(mailAccount);
        mailAccount = mailAccountService.get(mailAccount.getId());
        model.addAttribute("mailAccount", mailAccount);
        return "modules/oa/mail_account";
    }


    /**
     * 发送外部邮件
     *
     * @return
     */
    @RequestMapping(value = {"sendOut", ""})
    public String sendOut(MailInfo mailInfo, Model model) throws Exception {
        MailAccount mailAccount = new MailAccount();
        mailAccount.setLoginId(UserUtils.getUser().getId());
        List<MailAccount> mailAccounts = mailAccountService.findList(mailAccount);
        if (mailAccounts.size() > 0) {
            Session session = getSession(mailAccounts.get(0).getMailSend(), mailAccounts.get(0).getUsername(), mailAccounts.get(0).getPassword(), mailAccounts.get(0).getPort());
            if (mailInfo.getContent() != null) {
                mailInfo.setContent(StringEscapeUtils.unescapeHtml4(
                        mailInfo.getContent()));
            }
            send(getMessage1(session, mailInfo, mailAccounts.get(0).getUsername()));
            return "modules/oa/success";
        } else {
            return "modules/oa/wrong";
        }

    }

    private static Session getSession(String mailSend, final String username, final String password, String port) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", mailSend);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        return session;
    }

    //不含附件
    private static Message getMessage1(Session session, MailInfo mailInfo, String username) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(mailInfo.getOutSide()));
        message.setSubject(mailInfo.getTheme());
        message.setText(mailInfo.getContent());
        return message;
    }

    //发送
    private static void send(Message message) {
        try {
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 接收并查看外部邮件
     *
     * @return
     */
    @RequestMapping(value = {"findOut", ""})
    public String findOut(MailInfo mailInfo, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MailAccount mailAccount = new MailAccount();
        mailAccount.setLoginId(UserUtils.getUser().getId());
        List<MailAccount> mailAccounts = mailAccountService.findList(mailAccount);
        PraseMimeMessage praseMimeMessage = new PraseMimeMessage();
       /* List<MailInfo> list=lender.test(mailAccounts.get(0).getUsername(), mailAccounts.get(0).getPassword(),
                mailAccounts.get(0).getPort(), mailAccounts.get(0).getMailSend());*/
        List<MailInfo> list = praseMimeMessage.test(mailAccounts.get(0).getUsername(), mailAccounts.get(0).getPassword(),
                mailAccounts.get(0).getPort(), mailAccounts.get(0).getMailAccept());
        Page<MailInfo> page = mailInfoService.findPage(new Page<MailInfo>(request, response), mailInfo);
        page.setCount(list.size());
        page.setList(list);
        model.addAttribute("page", page);
        return "modules/oa/receiving";
    }


    /**
     * 点击查看外部邮件的内容
     *
     * @param mailInfo
     * @param model
     * @param
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"findMail", ""})
    public String findMail(MailInfo mailInfo, Model model, String content, String theme, String name, String time) throws Exception {

//        mailInfo.setContent(content);
        mailInfo.setTheme(theme);
        mailInfo.setName(name);
       /* SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");
        Date date=sdf.parse(time);
        mailInfo.setTime(date);*/
        model.addAttribute("mailInfo", mailInfo);
        return "modules/oa/find";
    }


}