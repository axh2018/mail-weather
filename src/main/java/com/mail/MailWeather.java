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

        // ����debug����
        props.setProperty("mail.debug", "true");
        // ���ͷ�������Ҫ�����֤
        props.setProperty("mail.smtp.auth", "true");
        // �����ʼ�������������
        //�˴�Ϊ���������䣬QQ����Ļ��� smtp.qq.com
        props.setProperty("mail.host", "smtp.aliyun.com");
        // �����ʼ�Э������
        props.setProperty("mail.transport.protocol", "smtp");

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);

        Session session = Session.getInstance(props);

        Message msg = new MimeMessage(session);
        msg.setSubject("����С����");
        StringBuilder builder = new StringBuilder();

        builder.append(weather);
        msg.setText(builder.toString());
        // �����˵��ʼ���ַ
        msg.setFrom(new InternetAddress("sender@aliyun.com"));
        Transport transport = session.getTransport();
        // �����˵��ʼ���ַ �Լ������˵���������
        // Ŀǰ��ʹ�ð��������䣺smtp.aliyun.com
        // QQ��������Ҫ��Ȩ�룬���������룬�ʼ������������� smtp.qq.com
        transport.connect("smtp.aliyun.com", "sender@aliyun.com", "passwd");
        // �����˵��ʼ���ַ,��Ⱥ��
        transport.sendMessage(msg, new Address[] { new InternetAddress("receiver@qq.com")});
        transport.close();
    }
}

