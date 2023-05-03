package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MailForm {
	private String from;

	@NotBlank(message = "宛先を入力してください")
	private String sendTo;

	@NotBlank(message = "件名を入力してください")
	private String subject;

	@NotBlank(message = "本文を入力してください")
	private String content;
}
