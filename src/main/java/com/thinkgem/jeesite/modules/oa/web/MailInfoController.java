package com.thinkgem.jeesite.modules.oa.web;

import javax.mail.internet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.sun.mail.imap.protocol.FLAGS;
import com.thinkgem.jeesite.modules.oa.entity.MailAccount;
import com.thinkgem.jeesite.modules.oa.service.MailAccountService;
import com.thinkgem.jeesite.modules.oa.units.ComparatorMail;
import com.thinkgem.jeesite.modules.sys.entity.Office;
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

import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
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
            mailInfo.setFlag("0");
        } else {
            mailInfo.setFlag("0");
            mailInfo.setState(state);
        }
        Page<MailInfo> page = mailInfoService.findPage(new Page<MailInfo>(request, response), mailInfo);
        MailInfo mailInfo1 = new MailInfo();
        mailInfo1.setReadMark("0");
        mailInfo1.setOwnId(mailInfo.getOwnId());

        model.addAttribute("page", page);
        if (StringUtils.equals(mailInfo.getState(), "DELETED")) {
            mailInfo1.setState("DELETED");
            List<MailInfo> delete = mailInfoService.findList(mailInfo1);
            page.setDelete(delete.size());
            return "modules/oa/delete";
        } else if (StringUtils.equals(mailInfo.getState(), "DRAFTS")) {
            mailInfo1.setState("DRAFTS");
            List<MailInfo> delete = mailInfoService.findList(mailInfo1);
            page.setDelete(delete.size());
            return "modules/oa/drafts";
        } else if (StringUtils.equals(mailInfo.getState(), "INBOX")) {
            mailInfo1.setState("INBOX");
            List<MailInfo> delete = mailInfoService.findList(mailInfo1);
            page.setDelete(delete.size());
            return "modules/oa/receiving";
        } else {
            mailInfo1.setState("SENT");
            List<MailInfo> delete = mailInfoService.findList(mailInfo1);
            page.setDelete(delete.size());
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
        mailInfo.setFlag("0");
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
        mailInfo.setFlag("0");
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
        if (mailInfo.getState() == null) {
            mailInfo.setState("SENT");
            mailInfo.setFlag("0");
        } else {
            mailInfo.setFlag("0");
            mailInfo.setState(mailInfo.getState());
        }

        MailInfo m = new MailInfo();
        m.setOwnId(UserUtils.getUser().getId());
        m.setState(state);
        m.setFlag("0");
        Page<MailInfo> page = mailInfoService.findPage(new Page<MailInfo>(request, response), m);
        MailInfo mailInfo1 = new MailInfo();
        mailInfo1.setReadMark("0");
        mailInfo1.setOwnId(mailInfo.getOwnId());
        model.addAttribute("page", page);
        if (StringUtils.equals(mailInfo.getState(), "DELETED")) {
            mailInfo1.setState("DELETED");
            List<MailInfo> delete = mailInfoService.findList(mailInfo1);
            page.setDelete(delete.size());
            return "modules/oa/delete";
        } else if (StringUtils.equals(mailInfo.getState(), "DRAFTS")) {
            mailInfo1.setState("DRAFTS");
            List<MailInfo> delete = mailInfoService.findList(mailInfo1);
            page.setDelete(delete.size());
            return "modules/oa/drafts";
        } else if (StringUtils.equals(mailInfo.getState(), "INBOX")) {
            mailInfo1.setState("INBOX");
            List<MailInfo> delete = mailInfoService.findList(mailInfo1);
            page.setDelete(delete.size());
            return "modules/oa/receiving";
        } else {
            mailInfo1.setState("SENT");
            List<MailInfo> delete = mailInfoService.findList(mailInfo1);
            page.setDelete(delete.size());
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
        Office office = UserUtils.getUser().getCompany();
        user.setCompanyId(office.getId());
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
        if (mailAccounts.size() > 0 && mailAccounts.get(0).getUsername()!=null && mailAccounts.get(0).getPassword()!=null) {
            Session session = getSession(mailAccounts.get(0).getMailSend(), mailAccounts.get(0).getUsername(), mailAccounts.get(0).getPassword(), mailAccounts.get(0).getPort());
            if (mailInfo.getContent() != null) {
                mailInfo.setContent(StringEscapeUtils.unescapeHtml4(
                        mailInfo.getContent().replace("\"", "")));
            }
            //发送邮件
            send(getMessage2(session, mailInfo, mailAccounts.get(0).getUsername()), session);
            //发送成功后   将发送成功的邮件保存在已发送的文件中
            int j=mailAccounts.get(0).getMailAccept().indexOf("qq");
            if(j>0){
                saveSendEmail(mailAccounts.get(0).getMailAccept(), mailAccounts.get(0).getUsername(), mailAccounts.get(0).getPassword(),
                        getMessage2(session, mailInfo, mailAccounts.get(0).getUsername()));
            }
            return "modules/oa/success";
        } else {
            return "modules/oa/wrong";
        }

    }

    private static Session getSession(String mailSend, final String username, final String password, String port) {
        port = "25";
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

    //发送html格式的正文
    private static Message getMessage3(Session session, MailInfo mailInfo, String username) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(mailInfo.getOutSide()));
        message.setSubject(mailInfo.getTheme());
        message.setContent(mailInfo.getContent(), "text/html");
        return message;
    }


    //含附件信息
    private static Message getMessage2(Session session, MailInfo mailInfo, String username) throws Exception {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(mailInfo.getOutSide()));
        message.setSubject(mailInfo.getTheme());
        BodyPart messageBodyPart = new MimeBodyPart();

        messageBodyPart.setContent(mailInfo.getContent(), "text/html;charset=utf8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        messageBodyPart = new MimeBodyPart();
        if (StringUtils.isNotEmpty(mailInfo.getFiles())) {
            String filename = Global.getUserfilesBaseDir() + URLDecoder.decode(mailInfo.getFiles().replace("|", ""), "utf-8");
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(MimeUtility.encodeText(filename));
            multipart.addBodyPart(messageBodyPart);
        }
        message.setContent(multipart);
        message.setFlag(FLAGS.Flag.RECENT, true);
        message.saveChanges();
        return message;
    }

    public void saveSendEmail(String host, String username, String password, Message message) throws Exception {
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties props = System.getProperties();
        String port1 = "993";
        props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.imap.socketFactory.port", port1);
        props.setProperty("mail.store.protocol", "imap");
        props.setProperty("mail.imap.host", username);
        props.setProperty("mail.imap.port", port1);
        props.setProperty("mail.imap.auth.login.disable", "true");
        Session session = Session.getDefaultInstance(props);
        Store store = session.getStore("imap");  // 使用imap会话机制，连接服务器
        store.connect(host, username, password);

        Folder folder = (Folder) store.getFolder("已发送");
        if (!folder.exists()) {
            folder.create(Folder.HOLDS_MESSAGES);
        }
        folder.open(Folder.READ_WRITE);
        try {
            folder.appendMessages(new Message[]{message});
            message.setFlag(FLAGS.Flag.RECENT, true);
            message.saveChanges();
        } catch (Exception ignore) {
            System.out.println("error processing message " + ignore.getMessage());
        } finally {
            store.close();
        }

    }


    //发送
    private void send(Message message, Session session) {
        try {
            Transport.send(message);
            //保存邮件到已发送邮件夹
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
    public String findOut(MailInfo mailInfo, Model model, HttpServletRequest request, HttpServletResponse response, String state) throws Exception {
        MailAccount mailAccount = new MailAccount();
        mailAccount.setLoginId(UserUtils.getUser().getId());
        List<MailAccount> mailAccounts = mailAccountService.findList(mailAccount);
        PraseMimeMessage praseMimeMessage = new PraseMimeMessage();

        if (StringUtils.equals(state, "SENT")) {
            if (mailAccounts.size() > 0 && mailAccounts.get(0).getUsername()!=null && mailAccounts.get(0).getPassword()!=null) {
                List<MailInfo> list = praseMimeMessage.test(mailAccounts.get(0).getUsername(), mailAccounts.get(0).getPassword(),
                        mailAccounts.get(0).getPort(), mailAccounts.get(0).getMailAccept(), state);
                Page<MailInfo> page = mailInfoService.findPage(new Page<MailInfo>(request, response), mailInfo);
                ComparatorMail comparator=new ComparatorMail();
                Collections.sort(list, comparator);

                page.setCount(list.size());
                page.setList(list);
                model.addAttribute("page", page);
            } else {
                Page<MailInfo> page = new Page<>();
                page.setCount(0);
                page.setMail("或者没有绑定邮箱账户");
                model.addAttribute("page", page);
            }
            return "modules/oa/sent";
        } else if (StringUtils.equals(state, "INBOX")) {
            if (mailAccounts.size() > 0 && mailAccounts.get(0).getUsername()!=null && mailAccounts.get(0).getPassword()!=null) {
                List<MailInfo> list = praseMimeMessage.test(mailAccounts.get(0).getUsername(), mailAccounts.get(0).getPassword(),
                        mailAccounts.get(0).getPort(), mailAccounts.get(0).getMailAccept(), state);
                Page<MailInfo> page = mailInfoService.findPage(new Page<MailInfo>(request, response), mailInfo);


                ComparatorMail comparator=new ComparatorMail();
                Collections.sort(list, comparator);


                page.setCount(list.size());
                page.setList(list);
                model.addAttribute("page", page);
            } else {
                Page<MailInfo> page = new Page<>();
                page.setCount(0);
                page.setMail("或者没有绑定邮箱账户");
                model.addAttribute("page", page);
            }
            return "modules/oa/receiving";
        }
        return null;
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
    public String findMail(MailInfo mailInfo, Model model, String id, String name, String state) throws Exception {
        MailAccount mailAccount = new MailAccount();
        mailAccount.setLoginId(UserUtils.getUser().getId());
        List<MailAccount> mailAccounts = mailAccountService.findList(mailAccount);
        if(mailAccounts.size()>0 && mailAccounts.get(0).getUsername()!=null && mailAccounts.get(0).getPassword()!=null){
            PraseMimeMessage praseMimeMessage = new PraseMimeMessage();
            List<MailInfo> list = praseMimeMessage.test(mailAccounts.get(0).getUsername(), mailAccounts.get(0).getPassword(),
                    mailAccounts.get(0).getPort(), mailAccounts.get(0).getMailAccept(), state);
            if (id != null && id != "") {
                id = StringEscapeUtils.unescapeHtml4(id).replace("\"", "");
                if (StringUtils.isEmpty(id)) {
                    name = StringEscapeUtils.unescapeHtml4(name).replace("\"", "");
                    for (int i = 0; i < list.size(); i++) {
                        if (StringUtils.equals(name, list.get(i).getName())) {

                            mailInfo.setContent(list.get(i).getContent());
                            mailInfo.setTheme(list.get(i).getTheme());
                            mailInfo.setFiles(list.get(i).getFiles());
                            mailInfo.setName(list.get(i).getName());
                            mailInfo.setTime(list.get(i).getTime());
                            mailInfo.setFlag("1");
                            mailInfo.setReceiverNames(list.get(i).getReceiverNames());
                            model.addAttribute("mailInfo", mailInfo);
                        }
                    }
                }
                for (int i = 0; i < list.size(); i++) {
                    if (StringUtils.equals(id, list.get(i).getUID())) {
                        System.out.println(list.get(i).getUID());
                        mailInfo.setContent(list.get(i).getContent());
                        mailInfo.setTheme(list.get(i).getTheme());
                        mailInfo.setFiles(list.get(i).getFiles());
                        mailInfo.setName(list.get(i).getName());
                        mailInfo.setTime(list.get(i).getTime());
                        mailInfo.setFlag("1");
                        mailInfo.setReceiverNames(list.get(i).getReceiverNames());
                        model.addAttribute("mailInfo", mailInfo);
                    }
                }
            }
        }

        return "modules/oa/find";
    }


    /**
     * 删除外部邮件的内容
     *
     * @param mailInfo
     * @param model
     * @param
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"deleteMail", ""})
    public String deleteMail(MailInfo mailInfo, Model model, String ids, String name, String state) throws Exception {
        MailAccount mailAccount = new MailAccount();
        mailAccount.setLoginId(UserUtils.getUser().getId());
        List<MailAccount> mailAccounts = mailAccountService.findList(mailAccount);
        PraseMimeMessage praseMimeMessage = new PraseMimeMessage();
        if (ids != null && ids != "") {
            ids = StringEscapeUtils.unescapeHtml4(ids).replace("\"", "");
            ids = ids.substring(0, ids.length() - 1);
        }
        List<MailInfo> list = praseMimeMessage.mail(mailAccounts.get(0).getUsername(), mailAccounts.get(0).getPassword(),
                mailAccounts.get(0).getPort(), mailAccounts.get(0).getMailAccept(), state, ids);

        return "modules/oa/find";
    }


}