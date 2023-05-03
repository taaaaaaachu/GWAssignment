package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.MailForm;
import com.example.demo.service.SendMailService;

@Controller
public class ContactController {

	@Autowired
	private SendMailService sendMailService;

	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}

	@PostMapping("/confirm")
	public String confirm(@ModelAttribute @Validated MailForm mailForm, BindingResult result, Model model) {
		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError", errorList);
			return "index";
		}

		//		model.addAttribute("mailForm", mailForm);
		//		return "confirm";

		//		sendMailService.send(mailForm);
		sendMailService.create(mailForm);
		return "complete";

	}

	@PostMapping("/complete")
	public String complete(@ModelAttribute MailForm mailForm) {
		sendMailService.send(mailForm);
		sendMailService.create(mailForm);
		return "complete";
	}
}