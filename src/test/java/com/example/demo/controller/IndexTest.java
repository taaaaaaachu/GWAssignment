package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.model.MailForm;
import com.example.demo.service.SendMailService;

@AutoConfigureMockMvc
@WebMvcTest(ContactController.class)
class IndexTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SendMailService sendMailService;

	@Test
	void トップ画面遷移テスト() throws Exception {
		System.out.println("トップ画面遷移テスト 開始");

		mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("index")).andReturn();

		System.out.println("トップ画面遷移テスト 終了");
	}

	@Test
	void 確認画面遷移テスト() throws Exception {
		System.out.println("確認画面遷移テスト 開始");

		// メールオブジェクトを生成
		MailForm mailForm = new MailForm();
		mailForm.setFrom("info@linkc-19.com");
		mailForm.setSendTo("test@example.com");
		mailForm.setSubject("test");
		mailForm.setContent("testtest");
		System.out.println("mailForm" + mailForm);

		mockMvc.perform(MockMvcRequestBuilders.post("/confirm").flashAttr("mailForm", mailForm))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("complete"))
				.andReturn();

		System.out.println("確認画面遷移テスト 終了");
	}

	@Test
	void 完了画面遷移テスト() throws Exception {
		System.out.println("完了画面遷移テスト 開始");

		mockMvc.perform(MockMvcRequestBuilders.post("/complete")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("complete")).andReturn();

		System.out.println("完了画面遷移テスト 終了");
	}
}
