package com.example.ecommerce_a.utils;

import java.nio.charset.StandardCharsets;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.example.ecommerce_a.domain.Order;

/**
 * メールを送るクラス.
 * 
 * @author taka
 *
 */
@Component
public class SendMail {

	private final JavaMailSender javaMailSender;

	@Autowired
	SendMail(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	/**
	 * テキストの注文完了メールを送る
	 * 
	 * @param order 注文情報
	 * @return メールメッセージ
	 */
	public SimpleMailMessage sendMainForOrderConfirmation(Order order) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();

		try {
			mailMessage.setTo(order.getDestinationEmail());
			mailMessage.setReplyTo("rakusupiza@gmail.com");
			mailMessage.setFrom("rakusupiza@gmail.com");
			mailMessage.setSubject("卍ラクラクピザ卍　注文完了のお知らせ");
			mailMessage.setText(order.toString());

			javaMailSender.send(mailMessage);

			return mailMessage;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * HTMLの注文完了メールを送る.
	 * 
	 * @param order 注文情報
	 * 
	 */
	public void sendMailHTML(Order order) {
		javaMailSender.send(new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
				helper.setFrom("rakusupiza@gmail.com");
				helper.setTo(order.getDestinationEmail());
				helper.setSubject("商品一覧");
				Context context = new Context();
				context.setVariable("order", order);
				helper.setText(getMailBody("ordermail", context), true);
			}
		});
	}

	/**
	 * HTMLにコンテキストを入れる.
	 * 
	 * @param templateName HTMLファイルを指定
	 * @param context      セットしたコンテキスト
	 * @return
	 */
	private String getMailBody(String templateName, Context context) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(mailTemplateResolver());
		return templateEngine.process(templateName, context);

	}

	/**
	 * 
	 * @return
	 */
	private ClassLoaderTemplateResolver mailTemplateResolver() {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setPrefix("templates/"); // メールのテンプレートの指定
		templateResolver.setSuffix(".html");
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setCacheable(true);
		return templateResolver;
	}

}
