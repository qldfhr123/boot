package com.care.boot.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {
	@Autowired	private JavaMailSender mailSender;

	public String sendMail(String to, String subject, String content) {
		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			
			messageHelper.setSubject(subject); // 이메일의 제목
			messageHelper.setText(content);// 이메일의 본문
			messageHelper.setTo(to); // 수신자

			mailSender.send(message);
		} catch (MailSendException e) {
			e.printStackTrace();
			return "이메일 주소가 잘못된 주소 입니다";
		} catch (Exception e) {
			e.printStackTrace();
			return "이메일 전송에 문제가 발생했습니다. 잠시 후에 다시 시도 하세요.";
		}
		return "입력한 이메일에서 인증번호를 확인하세요.";
	}
}
//package pom.xml
	










