package com.example.demo.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Mail;
import com.example.demo.model.MailForm;
import com.example.demo.repository.MailRepository;

@Service
public class SendMailService {

	@Autowired
	private MailSender mailSender;

	@Autowired
	private MailRepository mailRepository;

	public void send(MailForm mailForm) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom(mailForm.getFrom());
		msg.setTo(mailForm.getSendTo()); // 管理者アドレス
		msg.setSubject(mailForm.getSubject());
		msg.setText(mailForm.getContent());
		mailSender.send(msg);
	}

	public void create(MailForm mailForm) {
		mailRepository.save(CreateMailData(mailForm));

	}

	public Mail CreateMailData(MailForm mailForm) {
		LocalDateTime dateTime = LocalDateTime.now();
		Timestamp now = Timestamp.valueOf(dateTime);

		Mail mail = new Mail();
		mail.setFrom(mailForm.getFrom());
		mail.setTo(mailForm.getFrom());
		mail.setSubject(mailForm.getSubject());
		mail.setDetail(mailForm.getContent());
		mail.setInsertDate(now);

		return mail;

	}

	//	public String makeContent(MailForm mailForm) {
	//		return "名前: " + mailForm.getName() + "\n" +
	//				"性別: " + mailForm.getSex() + "\n" +
	//				"問い合わせ内容: " + mailForm.getContent();
	//	}
}