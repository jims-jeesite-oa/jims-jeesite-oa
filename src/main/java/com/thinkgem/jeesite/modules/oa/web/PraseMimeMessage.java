package com.thinkgem.jeesite.modules.oa.web;

import com.sun.mail.imap.IMAPFolder;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.entity.MailInfo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;

import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

public class PraseMimeMessage {
    private MimeMessage mimeMessage = null;
    private String saveAttachPath = "";//附件下载后的存放目录
    private StringBuffer bodytext = new StringBuffer();
    //存放邮件内容的StringBuffer对象
    private String dateformat = "yy-MM-dd HH:mm";//默认的日前显示格式

    /**
     * 构造函数,初始化一个MimeMessage对象
     */
    public PraseMimeMessage() {
    }

    public PraseMimeMessage(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    public void setMimeMessage(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    /**
     * 获得发件人的地址和姓名
     */
    public String getFrom1() throws Exception {
        InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
        String from = address[0].getAddress();
        if (from == null) {
            from = "";
        }
        String personal = address[0].getPersonal();
        if (personal == null) {
            personal = "";
        }
        String fromaddr = personal + "<" + from + ">";
        return fromaddr;
    }

    /**
     * 获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同
     * "to"----收件人　"cc"---抄送人地址　"bcc"---密送人地址
     *
     * @throws Exception
     */
    public String getMailAddress(String type) {
        String mailaddr = "";
        try {
            String addtype = type.toUpperCase();
            InternetAddress[] address = null;
            if (addtype.equals("TO") || addtype.equals("CC") || addtype.equals("BBC")) {
                if (addtype.equals("TO")) {
                    address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.TO);
                } else if (addtype.equals("CC")) {
                    address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.CC);
                } else {
                    address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.BCC);
                }
                if (address != null) {
                    for (int i = 0; i < address.length; i++) {
                        String email = address[i].getAddress();
                        if (email == null) email = "";
                        else {
                            email = MimeUtility.decodeText(email);
                        }
                        String personal = address[i].getPersonal();
                        if (personal == null) personal = "";
                        else {
                            personal = MimeUtility.decodeText(personal);
                        }
                        String compositeto = personal + "<" + email + ">";
                        mailaddr += "," + compositeto;
                    }
                    mailaddr = mailaddr.substring(1);
                }
            } else {
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return mailaddr;
    }

    /**
     * 获得邮件主题
     */
    public String getSubject() {
        String subject = "";
        try {
            subject = MimeUtility.decodeText(mimeMessage.getSubject());
            if (subject == null) subject = "";
        } catch (Exception e) {
            // TODO: handle exception
        }
        return subject;
    }

    /**
     * 获得邮件发送日期
     */
    public String getSendDate() throws Exception {
        Date senddate = mimeMessage.getSentDate();
        SimpleDateFormat format = new SimpleDateFormat(dateformat);
        return format.format(senddate);
    }

    /**
     * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件
     * 主要是根据MimeType类型的不同执行不同的操作，一步一步的解析             同时去除内容重复的问题
     */
    public void getMailContent(Part part, boolean flag) throws Exception {
        String contentType = part.getContentType();
        int nameindex = contentType.indexOf("name");
        boolean conname = false;
        if (nameindex != -1)
            conname = true;
        if (part.isMimeType("text/plain") && !conname && flag) {
            bodytext.append((String) part.getContent());
            flag = false;
        } else if (part.isMimeType("text/html") && !conname && flag == false) {
            bodytext.append((String) part.getContent());
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int counts = multipart.getCount();
            for (int i = 0; i < counts; i++) {
                getMailContent(multipart.getBodyPart(i), flag);
            }
        } else if (part.isMimeType("message/rfc822")) {
            getMailContent((Part) part.getContent(), flag);
        } else {
        }
    }

    public boolean checkHasHtml(Multipart part) throws MessagingException, IOException {
        boolean hasHtml = false;
        int count = part.getCount();
        for (int i = 0; i < count; i++) {
            Part bodyPart = part.getBodyPart(i);
            if (bodyPart.isMimeType("text/html")) {
                hasHtml = true;
                break;
            }
        }
        return hasHtml;
    }


    /**
     * 获得邮件正文内容
     */
    public String getBodyText() {
        return bodytext.toString();
    }

    /**
     * 判断此邮件是否需要回执，如果需要回执返回"true",否则返回"false"
     *
     * @throws javax.mail.MessagingException
     */
    public boolean getReplySign() throws MessagingException {
            boolean replysign = false;
        String needreply[] = mimeMessage.getHeader("Disposition-Notification-To");
        if (needreply != null) {
            replysign = true;
        }
        return replysign;
    }

    /**
     * 获得此邮件的Message-ID
     *
     * @throws javax.mail.MessagingException
     */
    public String getMessageId() throws MessagingException {
        return mimeMessage.getMessageID();
    }

    /**
     * 【判断此邮件是否已读，如果未读返回返回false,反之返回true
     *
     * @throws javax.mail.MessagingException
     */
    public boolean isNew() throws MessagingException {
        boolean isnew = false;
        Flags flags = ((Message) mimeMessage).getFlags();
        Flags.Flag[] flag = flags.getSystemFlags();
        for (int i = 0; i < flag.length; i++) {
            if (flag[i] == Flags.Flag.SEEN) {
                isnew = true;
                break;
            }
        }
        return isnew;
    }

    /**
     * 判断此邮件是否包含附件
     *
     * @throws javax.mail.MessagingException
     */
    public boolean isContainAttach(Part part) throws Exception {
        boolean attachflag = false;
        String contentType = part.getContentType();
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            //获取附件名称可能包含多个附件
            for (int j = 0; j < mp.getCount(); j++) {
                BodyPart mpart = mp.getBodyPart(j);
                String disposition = mpart.getDescription();
                if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))) {
                    attachflag = true;
                } else if (mpart.isMimeType("multipart/*")) {
                    attachflag = isContainAttach((Part) mpart);
                } else {
                    String contype = mpart.getContentType();
                    if (contype.toLowerCase().indexOf("application") != -1) attachflag = true;
                    if (contype.toLowerCase().indexOf("name") != -1) attachflag = true;
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            attachflag = isContainAttach((Part) part.getContent());
        }
        return attachflag;
    }

    /**
     * 【保存附件】
     *
     * @throws Exception
     * @throws java.io.IOException
     * @throws javax.mail.MessagingException
     * @throws Exception
     */
    public String saveAttachMent(Part part) throws Exception {
        String fileName = "";
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            for (int j = 0; j < mp.getCount(); j++) {
                BodyPart mpart = mp.getBodyPart(j);
                String disposition = mpart.getDescription();
                if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))) {
                    fileName = mpart.getFileName();
                    if (fileName.toLowerCase().indexOf("GBK") != -1) {
                        fileName = MimeUtility.decodeText(fileName);
                    }
                    saveFile(fileName, mpart.getInputStream());
                } else if (mpart.isMimeType("multipart/*")) {
                    fileName = mpart.getFileName();
                } else {
                    fileName = mpart.getFileName();
                    if ((fileName != null)) {
                        fileName = MimeUtility.decodeText(fileName);
                        if (fileName != null && fileName != "") {
                            saveFile(fileName, mpart.getInputStream());
                        }

                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            saveAttachMent((Part) part.getContent());
        }

        return fileName;
    }

    /**
     * 【设置附件存放路径】
     */
    public void setAttachPath(String attachpath) {
        this.saveAttachPath = attachpath;
    }

    /**
     * 【设置日期显示格式】
     */
    public void setDateFormat(String format) {
        this.dateformat = format;
    }

    /**
     * 【获得附件存放路径】
     */

    public String getAttachPath() {
        return saveAttachPath;
    }

    /**
     * 【真正的保存附件到指定目录里】
     */
    private void saveFile(String fileName, InputStream in) throws Exception {
        String osName = System.getProperty("os.name");
        String storedir = getAttachPath();
        String separator = "";
        if (osName == null) osName = "";
        if (osName.toLowerCase().indexOf("win") != -1) {
            //如果是window 操作系统
            separator = "/";
            if (storedir == null || storedir.equals("")) storedir = "c:\tmp";
        } else {
            //如果是其他的系统
            separator = "/";
            storedir = "/tmp";
        }
        File strorefile = new File(storedir + separator + fileName);
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(strorefile));
            bis = new BufferedInputStream(in);
            int c;
            while ((c = bis.read()) != -1) {
                bos.write(c);
                bos.flush();
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (bos != null) {
                bos.close();
                bis.close();
            }

        }
    }

    /**
     * PraseMimeMessage类测试
     *
     * @throws Exception
     */
    public List<MailInfo> test(String username1, String password1, String port1, String host1, String state) throws Exception {
        List<MailInfo> list = new ArrayList<>();
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties props = System.getProperties();
        String host = host1;
        String username = username1;
        String password = password1;
        int j = username.indexOf("qq");
        Message message[] = null;
        PraseMimeMessage pmm = null;
        if (j > 0) {
            port1 = "993";
            props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.imap.socketFactory.port", port1);
            props.setProperty("mail.store.protocol", "imap");
            props.setProperty("mail.imap.host", username);
            props.setProperty("mail.imap.port", port1);
            props.setProperty("mail.imap.auth.login.disable", "true");
            Session session = Session.getDefaultInstance(props);
            Store store = session.getStore("imap");  // 使用imap会话机制，连接服务器
            store.connect(host, username, password);
            if (StringUtils.equals(state, "SENT")) {
                Folder folder = store.getFolder("已发送");
                folder.open(Folder.READ_ONLY);
                message = folder.getMessages();
            } else if (StringUtils.equals(state, "INBOX")) {
                Folder folder = store.getFolder(state);
                folder.open(Folder.READ_ONLY);
                message = folder.getMessages();
            }
        } else {
            Properties prop = new Properties();
            Session session = Session.getDefaultInstance(prop, null);
            Store stor = session.getStore("imap");
            stor.connect(host, username, password);
            if (StringUtils.equals(state, "SENT")) {
                Folder folder = stor.getFolder("已发送");
                folder.open(Folder.READ_WRITE);
                message = folder.getMessages();
            } else if (StringUtils.equals(state, "INBOX")) {
                Folder folder = stor.getFolder(state);
                folder.open(Folder.READ_WRITE);
                message = folder.getMessages();
            }
        }
        for (int i = 0; i < message.length; i++) {
            System.out.println("****************************************第"+(i+1)+"封邮件**********************************");
            pmm = new PraseMimeMessage((MimeMessage) message[i]);
            pmm.setDateFormat("yy年MM月dd日 HH:mm");

            String contentType = message[i].getContentType();
            if (contentType.startsWith("text/plain")) {
                pmm.getMailContent((Part) message[i], true);
            } else {
                pmm.getMailContent((Part) message[i], false);
            }

            File file=new File(Global.getUserfilesBaseDir()+"\\userfiles");
            if(!file.exists()){
                file.mkdirs();
            }
            MailInfo mailInfo = new MailInfo();
            mailInfo.setContent(pmm.getBodyText());
            pmm.setAttachPath(file.toString());

            boolean f=  pmm.isContainAttach((Part)message[i]);
            if(f==true) {
                String filename= pmm.saveAttachMent((Part)message[i]);
                mailInfo.setFiles("/userfiles/"+filename);
            }
            System.out.println("主题 :"+pmm.getSubject());
            System.out.println("发送时间 :"+pmm.getSendDate());
            System.out.println("是否回执 :"+pmm.getReplySign());
            System.out.println("是否包含附件 :"+pmm.isContainAttach((Part)message[i]));
            System.out.println("发件人 :"+pmm.getFrom1());
            System.out.println("收件人 :"+pmm.getMailAddress("TO"));
            System.out.println("抄送地址 :"+pmm.getMailAddress("CC"));
            System.out.println("密送地址 :"+pmm.getMailAddress("BCC"));
            System.out.println("邮件ID :"+i+":"+pmm.getMessageId()) ;
            System.out.println("邮件正文 :"+pmm.getBodyText());
            System.out.println("是否已读 :"+pmm.isNew());
            SimpleDateFormat sdf = new SimpleDateFormat();
            Date date = message[i].getSentDate();
            mailInfo.setTime(date);
            mailInfo.setTheme(pmm.getSubject());
            mailInfo.setName(pmm.getFrom1().replace("\"", ""));
            mailInfo.setReadMark(pmm.isNew() + "");
            mailInfo.setUID(pmm.getMessageId());
            mailInfo.setReceiverNames(pmm.getMailAddress("TO").replace("\"", ""));
            mailInfo.setFlag("1");
            list.add(mailInfo);
            System.out.println("*********************************第"+(i+1)+"封邮件结束*************************************");
        }

        return list;
    }


    public List<MailInfo> mail(String username1, String password1, String port1, String host1, String state,String ids) throws Exception {
        List<MailInfo> list = new ArrayList<>();
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties props = System.getProperties();
        String host = host1;
        String username = username1;
        String password = password1;
        int j = username.indexOf("qq");
        Message messages[] = null;
        PraseMimeMessage pmm = null;
        Folder folder =null;

        Properties prop = new Properties();
        Session session = Session.getDefaultInstance(prop, null);
        Store stor = session.getStore("imap");
        stor.connect(host, username, password);
        if (StringUtils.equals(state, "SENT")) {
            folder = stor.getFolder("已发送");
            folder.open(Folder.READ_WRITE);
            messages = folder.getMessages();
        } else if (StringUtils.equals(state, "INBOX")) {
            folder = stor.getFolder(state);
            folder.open(Folder.READ_WRITE);
            messages = folder.getMessages();
            int messageCount = folder.getMessageCount();
            System.out.println(messageCount + "------------------------------------------");
        }
        for (int i = 0; i < messages.length; i++) {
            System.out.println("****************************************第" + (i + 1) + "封邮件**********************************");
            pmm = new PraseMimeMessage((MimeMessage) messages[i]);
              Message message=messages[i];
            String id[]=ids.split(",");
            for(int x=0;x<id.length;x++){
               if(id[x].equals(pmm.getMessageId())){
                   message.setFlag(Flags.Flag.DELETED, true);
                   message.saveChanges();
                   folder.close(true);
               }
            }

            System.out.println("*********************************第" + (i + 1) + "封邮件结束*************************************");
        }

        return list;
    }
}