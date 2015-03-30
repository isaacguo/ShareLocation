package com.isaac.sharelocation;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.os.AsyncTask;

public class EmailSender {
	 
	 String SMTPHost = "smtp.163.com"; // SMTP服务器

	 String user = "TraceLocation2@163.com"; // 登录SMTP服务器的帐号

	 String password = "TraceLocation22";// 登录SMTP服务器的密码

	 String from = "TraceLocation2@163.com"; // 发件人的邮箱

	 String to = "TraceLocation2@163.com"; // 收件人的邮箱

	 String subject = "Test"; // 邮件标题

	 String content = "content"; // 邮件内容
	 
	 String lastErrorMessage="";

	 public EmailSender() {
	 }
	 public String getLastErrorMessage()
	 {
		 return lastErrorMessage;
	 }

	 public String getContent() {
	  return content;
	 }

	 public void setContent(String content) {
	  
	  this.content = content;
	 }

	 public String getFrom() {
	  return from;
	 }

	 public void setFrom(String from) {
	  this.from = from;
	 }

	 public String getPassword() {
	  return password;
	 }

	 public void setPassword(String password) {
	  this.password = password;
	 }

	 public String getSMTPHost() {
	  return SMTPHost;
	 }

	 public void setSMTPHost(String host) {
	  SMTPHost = host;
	 }

	 public String getSubject() {
	  return subject;
	 }

	 public void setSubject(String subject) {
	  this.subject = subject;
	 }

	 public String getTo() {
	  return to;
	 }

	 public void setTo(String to) {
	  this.to = to;
	 }

	 public String getUser() {
	  return user;
	 }

	 public void setUser(String user) {
	  this.user = user;
	 }
	 
	 public void sendAsync()
	 {
		 new AsyncTask<Void, Void, Void>(){

			@Override
			protected Void doInBackground(Void... params)
			{
				// TODO Auto-generated method stub
				send();
				return null;
			}}.execute();
	 }

	 
	 public boolean send() {
	  // 创建一个属性对象
	  Properties props = new Properties();
	  // 指定SMTP服务器 smtp.mail.yahoo.com
	  // props.put("mail.smtp.host", SMTPHost);
	  props.put("mail.smtp.host", SMTPHost);
	  // 指定是否需要SMTP验证
	  props.put("mail.smtp.auth", "true");
	  try {
	   // 创建一个授权验证对象
	   SmtpAuth auth = new SmtpAuth();
	   auth.setAccount(user, password);

	   // 创建一个Session对象
	   Session mailSession = Session.getDefaultInstance(props, auth);
	   // mailSession.setDebug(true);

	   // 创建一个Message对象
	   Message message = new MimeMessage(mailSession);

	   // 指定发件人邮箱
	   message.setFrom(new InternetAddress(from));
	   // 指定收件人邮箱
	   message.addRecipient(Message.RecipientType.TO, new InternetAddress(
	     to));
	   // 指定邮件主题
	   message.setSubject(subject);
	   // 指定邮件内容
	   message.setText(content);
	   // 指定邮件发送日期
	   message.setSentDate(new Date());
	   // 指定邮件优先级 1：紧急 3：普通 5：缓慢
	   message.setHeader("X-Priority", "1");
	   message.saveChanges();

	   // 创建一个Transport对象
	   Transport transport = mailSession.getTransport("smtp");
	   // 连接SMTP服务器
	   transport.connect(SMTPHost, user, password);
	   // 发送邮件
	   transport.sendMessage(message, message.getAllRecipients());
	   transport.close();
	   message=null;
	   return true;
	  } catch (Exception ex) {
		  lastErrorMessage=ex.getMessage();
	   return false;
	  }
	 }
	 
	
	 
	 // 定义一个SMTP授权验证类
	 public static class SmtpAuth extends Authenticator {
	  String user, password;

	  // 设置帐号信息
	  void setAccount(String user, String password) {
	   this.user = user;
	   this.password = password;
	  }

	  // 取得PasswordAuthentication对象
	  protected PasswordAuthentication getPasswordAuthentication() {
	   return new PasswordAuthentication(user, password);
	  }
	 }
}