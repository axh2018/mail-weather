package com.mail;

import com.sun.mail.util.MailSSLSocketFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailWeather
{
    public static String readFile()
    {
        String weather = "";
        String pathname = "/opt/weather.txt";
        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader)
        )
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                weather +=line+"\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weather;
    }
    public static void main(String[] args) throws MessagingException, GeneralSecurityException, GeneralSecurityException
    {
        String weather = readFile();
        Properties props = new Properties();

        // 开启debug调试
        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        //此处为阿里云邮箱，QQ邮箱的话填 smtp.qq.com
        props.setProperty("mail.host", "smtp.aliyun.com");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);

        Session session = Session.getInstance(props);

        Message msg = new MimeMessage(session);
        msg.setSubject("天气小助手");
        StringBuilder builder = new StringBuilder();

        builder.append(weather);
        msg.setText(builder.toString());
        // 发送人的邮件地址
        msg.setFrom(new InternetAddress("sender@aliyun.com"));
        Transport transport = session.getTransport();
        // 发送人的邮件地址 以及发送人的邮箱密码
        // 目前是使用阿里云邮箱：smtp.aliyun.com
        // QQ邮箱则需要授权码，而不是密码，邮件服务器主机名 smtp.qq.com
        transport.connect("smtp.aliyun.com", "sender@aliyun.com", "passwd");
        // 接收人的邮件地址,可群发
        transport.sendMessage(msg, new Address[] { new InternetAddress("receiver@qq.com")});
        transport.close();
    }
}

