package com.thinkgem.jeesite.modules.oa.web;

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
import java.util.Properties;

/**
 * Created by Administrator on 2016/11/21.
 */
public class MaillController {

    private static String to = "18745200375@163.com";
    //    private static final String username = "690101606@qq.com";
//    private static final String password = "szwixrpvomgaebig               zdarbhcyetcndiaa";
//    private static String host = "smtp.qq.com";
    private static final String username = "2280593260@qq.com";
    private static final String password = "szwixrpvomgaebig";
    private static String host = "smtp.qq.com";
    private static String port = "25";

    public static void main(String args[]) {
        try {
            Session session = getSession();
            send(getMessage1(session));
//            send(getMessage2(session));
//            send(getMessage3(session));
//            send(getMessage4(session));

//            check(host,"",username,password);
        }catch (Exception ex) {
            System.out.println(ex.toString());
        }

    }

    public static void query(){

    }

    public static void check(String host, String storeType, final String user,
                             final String password) {
        try {
            Properties properties = new Properties();
            properties.put("mail.pop3.host", "pop3.163.com");
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            properties.put("mail.pop3.auth", "true");
            Session emailSession = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password);
                }
            });
            Store store = emailSession.getStore("pop3s");
            store.connect("pop3.163.com", user, password);
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);
            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);
            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + message.getContent().toString());

            }
            emailFolder.close(false);
            store.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void send(Message message){
        try {
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private static Session getSession(){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
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
    private static Message getMessage1(Session session) throws MessagingException{
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));
        message.setSubject("Testing Subject");
        message.setText("Hello, this is sample for to check send " +
                "email using JavaMailAPI ");
        return message;
    }
    //含附件信息
    private static Message getMessage2(Session session) throws MessagingException{
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));
        message.setSubject("Testing Subject");
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("This is message body");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        messageBodyPart = new MimeBodyPart();
        String filename = "d:/test.txt";
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
        multipart.addBodyPart(messageBodyPart);
        message.setContent(multipart);
        return message;
    }
    //一个HTML电子邮件
    private static Message getMessage3(Session session) throws MessagingException{
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));
        message.setSubject("Testing Subject");
        message.setContent(
                "<h1>This is actual message embedded in HTML tags</h1>",
                "text/html");
        return message;
    }

    private static Message getMessage4(Session session) throws MessagingException{
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));
        message.setSubject("Testing Subject");
        MimeMultipart multipart = new MimeMultipart("related");
        BodyPart messageBodyPart = new MimeBodyPart();
        String htmlText = "<H1>Hello</H1><img src=\"cid:image\">";
        messageBodyPart.setContent(htmlText, "text/html");
        multipart.addBodyPart(messageBodyPart);
        messageBodyPart = new MimeBodyPart();
        DataSource fds = new FileDataSource(
                "c:/t.jpg");

        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID", "<image>");
        multipart.addBodyPart(messageBodyPart);
        message.setContent(multipart);
        return message;
    }
}
