package com.rumwei.func.hutool;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;

public class MailTest {
    //HuTool发送邮件功能依赖于以下maven
    /*
    <dependency>
        <groupId>javax.mail</groupId>
        <artifactId>mail</artifactId>
        <version>1.4.7</version>
    </dependency>
    * */
    public static void main(String[] args) {
        System.out.println("send mail");
        MailAccount account = new MailAccount();
//        account.setHost("easylinkin.com");
        account.setHost("smtp.163.com");
        account.setPort(25);
        account.setAuth(true);
        account.setUser("guwei1640692664");
        account.setPass("MQXLOTJQYPMUWXLN"); //登录授权码，不是登录密码,授权码需要在网页上申请
        account.setFrom("guwei1640692664@163.com");
        account.setStarttlsEnable(false);
        account.setSslEnable(false);
//        account.setDebug();
//        account.setCharset();
//        account.setSplitlongparameters();
//        account.setSocketFactoryClass();
//        account.setSocketFactoryFallback();
//        account.setSocketFactoryPort();
//        account.setTimeout();
//        account.setConnectionTimeout();
        MailUtil.send(account, CollUtil.newArrayList("guwei@easylinkin.com"),"测试","邮件来自Hutool测试",false);
        System.out.println("done");


    }
}
