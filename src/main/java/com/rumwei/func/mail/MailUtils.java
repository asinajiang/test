package com.rumwei.func.mail;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailUtils {
    public static final Logger log = LoggerFactory.getLogger(MailUtils.class);

    public static String PASSWORD_KEY = "0123456789ABCDEF";

    public static String CHARSET = "utf-8";

    // MIME邮件对象
    private MimeMessage mimeMsg;

    // 邮件会话对象
    private Session session;

    // 系统属性
    private Properties props;

    // smtp是否需要认证
    private boolean needAuth = false;

    // smtp认证用户名和密码
    private String username;

    private String password;

    // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象
    private Multipart mp;

    /**
     * Constructor
     *
     * @param smtp 邮件发送服务器
     */
    public MailUtils(String smtp) {
        setSmtpHost(smtp);
        createMimeMessage();
    }

    /**
     * 设置邮件发送服务器
     *
     * @param hostName String
     */
    public void setSmtpHost(String hostName) {
        // 获得系统属性对象
        if (props == null)
            props = System.getProperties();
        // 设置SMTP主机
        props.put("mail.smtp.host", hostName);
    }

    /**
     * 创建MIME邮件对象
     *
     * @return
     */
    public boolean createMimeMessage() {
        try {
            // 获得邮件会话对象
            session = Session.getDefaultInstance(props, null);
        } catch (Exception e) {
            log.error("获取邮件会话对象时发生错误！" + e);
            //System.err.println("获取邮件会话对象时发生错误！" + e);
            return false;
        }

        try {
            // 创建MIME邮件对象
            mimeMsg = new MimeMessage(session);
            mp = new MimeMultipart();
            return true;
        } catch (Exception e) {
            log.error("创建MIME邮件对象失败！" + e);
            //System.err.println("创建MIME邮件对象失败！" + e);
            return false;
        }
    }

    /**
     * 设置SMTP是否需要验证
     *
     * @param need
     */
    public void setNeedAuth(boolean need) {
        if (props == null)
            props = System.getProperties();
        if (need) {
            props.put("mail.smtp.auth", "true");
        } else {
            props.put("mail.smtp.auth", "false");
        }
    }

    /**
     * 设置用户名和密码
     *
     * @param name
     * @param pass
     */
    public void setNamePass(String name, String pass) {
        username = name;
        password = pass;
    }

    /**
     * 设置邮件主题
     *
     * @param mailSubject
     * @return
     */
    public boolean setSubject(String mailSubject) {
        try {
            mimeMsg.setSubject(mailSubject);
            return true;
        } catch (Exception e) {
            log.error("设置邮件主题发生错误！" + e);
            //System.err.println("设置邮件主题发生错误！");
            return false;
        }
    }

    /**
     * 设置邮件正文
     *
     * @param mailBody String
     */
    public boolean setBody(String mailBody) {
        try {
            BodyPart bp = new MimeBodyPart();
            bp.setContent("" + mailBody, "text/html;charset=utf-8");
            mp.addBodyPart(bp);
            return true;
        } catch (Exception e) {
            log.error("设置邮件正文时发生错误！" + e);
            //System.err.println("设置邮件正文时发生错误！" + e);
            return false;
        }
    }

    /**
     * 添加附件
     *
     * @param filename String
     */
    public boolean addFileAffix(String filename) {
        try {
            BodyPart bp = new MimeBodyPart();
            FileDataSource fileds = new FileDataSource(filename);
            bp.setDataHandler(new DataHandler(fileds));
            bp.setFileName(fileds.getName());
            mp.addBodyPart(bp);
            return true;
        } catch (Exception e) {
            log.error("增加邮件附件：" + filename + "发生错误！" + e);
            //System.err.println("增加邮件附件：" + filename + "发生错误！" + e);
            return false;
        }
    }

    /**
     * 设置发信人
     *
     * @param from String
     */
    public boolean setFrom(String from) {
        try {
            // 设置发信人
            mimeMsg.setFrom(new InternetAddress(from));
            return true;
        } catch (Exception e) {
            log.error("设置发信人发生错误！" + e);
            return false;
        }
    }

    /**
     * 设置收信人
     *
     * @param to String
     */
    public boolean setTo(String to) {
        if (to == null)
            return false;
        try {
            mimeMsg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            return true;
        } catch (Exception e) {
            log.error("设置收信人发生错误！" + e);
            return false;
        }
    }

    /**
     * 设置抄送人
     *
     * @param copyto String
     */
    public boolean setCopyTo(String copyto) {
        if (copyto == null)
            return false;
        try {
            mimeMsg.setRecipients(Message.RecipientType.CC,
                    (Address[]) InternetAddress.parse(copyto));
            return true;
        } catch (Exception e) {
            log.error("设置抄送人发生错误！" + e);
            return false;
        }
    }

    /**
     * 发送邮件
     *
     * @throws MessagingException
     */
    public void sendOut()
            throws MessagingException {
        mimeMsg.setContent(mp);
        mimeMsg.saveChanges();

        Session mailSession = Session.getInstance(props, null);
        Transport transport = mailSession.getTransport("smtp");
        transport.connect((String) props.get("mail.smtp.host"),
                username,
                password);
        transport.sendMessage(mimeMsg,
                mimeMsg.getRecipients(Message.RecipientType.TO));
        // transport.sendMessage(mimeMsg,
        // mimeMsg.getRecipients(Message.RecipientType.CC));
        // transport.send(mimeMsg);

        transport.close();
    }

    /**
     * 调用sendOut方法完成邮件发送
     *
     * @param smtp
     * @param from
     * @param to
     * @param subject
     * @param content
     * @param username
     * @param password
     * @return boolean
     * @throws MessagingException
     */
    public static void send(String smtp, String from, String to,
                            String subject, String content, String username, String password)
            throws MessagingException {
        MailUtils theMail = new MailUtils(smtp);
        theMail.setSmtpPort("465");
        theMail.setEnableSSL(true);
        theMail.setNeedAuth(true);
        theMail.setSubject(subject);
        theMail.setBody(content);
        theMail.setTo(to);
        theMail.setFrom(from);
        theMail.setNamePass(username, password);
        theMail.sendOut();
    }

    public void setSmtpPort(String port) {
        // 获得系统属性对象
        if (props == null)
            props = System.getProperties();
        // 设置SMTP主机
        props.put("mail.smtp.port", port);
    }

    public void setEnableSSL(boolean enable) {
        if (props == null)
            props = System.getProperties();
        if (enable) {
            props.put("mail.smtp.ssl.enable", "true");
        } else {
            props.remove("mail.smtp.ssl.enable");
        }
    }

    /**
     * 调用sendOut方法完成邮件发送,带抄送
     *
     * @param smtp
     * @param from
     * @param to
     * @param copyto
     * @param subject
     * @param content
     * @param username
     * @param password
     * @return boolean
     * @throws MessagingException
     */
    public static void sendAndCc(String smtp, String from, String to,
                                 String copyto, String subject, String content, String username,
                                 String password)
            throws MessagingException {
        MailUtils theMail = new MailUtils(smtp);
        // 需要验证
        theMail.setNeedAuth(true);
        theMail.setSubject(subject);
        theMail.setBody(content);
        theMail.setTo(to);
        theMail.setCopyTo(copyto);
        theMail.setFrom(from);
        theMail.setNamePass(username, password);
        theMail.sendOut();
    }

    /**
     * 调用sendOut方法完成邮件发送,带附件
     *
     * @param smtp
     * @param from
     * @param to
     * @param subject
     * @param content
     * @param username
     * @param password
     * @param filename 附件路径
     * @return
     * @throws MessagingException
     */
    public static void send(String smtp, String from, String to,
                            String subject, String content, String username, String password,
                            String filename)
            throws MessagingException {
        MailUtils theMail = new MailUtils(smtp);
        // 需要验证
        theMail.setNeedAuth(true);
        theMail.setSubject(subject);
        theMail.setBody(content);
        theMail.addFileAffix(filename);
        theMail.setTo(to);
        theMail.setFrom(from);
        theMail.setNamePass(username, password);
        theMail.sendOut();
    }

    /**
     * 调用sendOut方法完成邮件发送,带附件和抄送
     *
     * @param smtp
     * @param from
     * @param to
     * @param copyto
     * @param subject
     * @param content
     * @param username
     * @param password
     * @param filename
     * @return
     * @throws MessagingException
     */
    public static void sendAndCc(String smtp, String from, String to,
                                 String copyto, String subject, String content, String username,
                                 String password, String filename)
            throws MessagingException {
        MailUtils theMail = new MailUtils(smtp);
        // 需要验证
        theMail.setNeedAuth(true);
        theMail.setSubject(subject);
        theMail.setBody(content);
        theMail.addFileAffix(filename);
        theMail.setTo(to);
        theMail.setCopyTo(copyto);
        theMail.setFrom(from);
        theMail.setNamePass(username, password);
        theMail.sendOut();
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */

    public static boolean checkEmail(String email) {
        boolean flag = false;
        String check =
                "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        flag = matcher.matches();
        return flag;
    }

    /**
     * 同时发送多条邮件
     * <br>
     *
     * @param emailList
     * @param title
     * @param message
     * @throws MessagingException
     * @author ZhangKaiHua
     * @see [类、类#方法、类#成员]
     */
    public static void sendEmails(List<String> emailList, String title,
                                  String message, String address, String password)
            throws MessagingException {
        for (String email : emailList) {
            sendEmail(address, email, title, message, password);
        }
    }

    /**
     * 发送单个邮件
     * <br>
     *
     * @param email
     * @param title
     * @param message
     * @throws MessagingException
     * @author ZhangKaiHua
     * @see [类、类#方法、类#成员]
     */
    public static void sendEmail(String from, String to, String title,
                                 String message, String password)
            throws MessagingException {
        // 通过邮箱地址解析出smtp服务器，对大多数邮箱都管用
        final String smtpHostName = "smtp.exmail.qq.com";
        // 发送邮件
        MailUtils.send(smtpHostName, from, to, title, message, from, password);
    }


    public static void main(String[] args)
            throws MessagingException {
        try {
            sendEmail("linkos@easylinkin.com",
                    "guwei@easylinkin.com",
                    "测试",
                    "http://59.173.19.66:8580/#/manage/task?status=pre",
                    "AomruakoTSsDQQBL");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ok");
    }


    public static void sendMail(String title,String content){
        MailAccount account = new MailAccount();
        account.setHost("smtp.163.com");
        account.setPort(25);
        account.setAuth(true);
        account.setUser("guwei1640692664");
        account.setPass("MQXLOTJQYPMUWXLN"); //登录授权码，不是登录密码,授权码需要在网页上申请
        account.setFrom("guwei1640692664@163.com");
        account.setStarttlsEnable(false);
        account.setSslEnable(false);
        cn.hutool.extra.mail.MailUtil.send(account, CollUtil.newArrayList("guwei@easylinkin.com"),title,content,false);
    }
}
