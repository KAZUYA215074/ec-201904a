package com.example.ecommerce_a.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.ecommerce_a.domain.Order;

@Component
public class SendMail {
	
  private final JavaMailSender javaMailSender;

  @Autowired
  SendMail(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  public SimpleMailMessage sendMainForOrderConfirmation(Order order) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();

    try {
        mailMessage.setTo(order.getDestinationEmail());
        mailMessage.setReplyTo("YOUR_MAIL_ADDRESS");
        mailMessage.setFrom("YOUR_MAIL_ADDRESS");
        mailMessage.setSubject("卍ラクラクピザ卍　注文完了のお知らせ");
        mailMessage.setText(order.toString());
        javaMailSender.send(mailMessage);
        
        return mailMessage;
    } catch(Exception e) {
    	e.printStackTrace();
    	return null;
    }
  }
  
}
