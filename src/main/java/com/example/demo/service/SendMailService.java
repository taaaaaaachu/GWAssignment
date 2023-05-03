package com.example.demo.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Mail;
import com.example.demo.model.MailForm;
import com.example.demo.repository.MailRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class SendMailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailRepository mailRepository;

	public void send(MailForm mailForm) throws MessagingException {
		MimeMessage mimeMsg = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true);

		//メールフォームの宛先をカンマ区切りで配列化
		String[] to = mailForm.getSendTo().split(",");

		helper.setFrom(mailForm.getFrom());
		helper.setTo(to);
		helper.setSubject(mailForm.getSubject());
		helper.setText(mailForm.getContent());
		helper.addAttachment(mailForm.getFile().getOriginalFilename(), mailForm.getFile());
		mailSender.send(mimeMsg);
	}

	public void create(MailForm mailForm) {
		mailRepository.save(CreateMailData(mailForm));

	}

	public Mail CreateMailData(MailForm mailForm) {
		LocalDateTime dateTime = LocalDateTime.now();
		Timestamp now = Timestamp.valueOf(dateTime);

		MultipartFile multipartFile = mailForm.getFile();

		byte[] file = fileByteConverter(multipartFile);

		Mail mail = new Mail();
		mail.setFrom(mailForm.getFrom());
		mail.setTo(mailForm.getSendTo());
		mail.setSubject(mailForm.getSubject());
		mail.setDetail(mailForm.getContent());
		mail.setInsertDate(now);
		mail.setFile(file);

		return mail;

	}

	public byte[] fileByteConverter(MultipartFile multipartFile) {
		try {
			//アップロードファイルをバイト配列に変換
			byte[] file = multipartFile.getBytes();

			return file;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//	public String makeContent(MailForm mailForm) {
	//		return "名前: " + mailForm.getName() + "\n" +
	//				"性別: " + mailForm.getSex() + "\n" +
	//				"問い合わせ内容: " + mailForm.getContent();
	//	}
}