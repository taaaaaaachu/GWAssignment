package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.MailForm;
import com.example.demo.repository.MailRepository;

@SpringBootTest
class ContactControllerTest {

	@Autowired
	private ContactController contactController;

	@Autowired
	private MailRepository mailRepository;

	@Autowired
	private Validator validator;

	private BindingResult bindingResult;

	/**
	 * 各テストメソッドを実行する前に実行する処理
	 */
	@BeforeEach
	void setUp() throws Exception {
		bindingResult = new BindException(new MailForm(), "mailForm");
	}

	/**
	 * Formオブジェクト(エラー無し)の検証を行うテストを実行する
	 */
	@Test
	void メール送信_正常系() throws Exception {
		System.out.println("メール送信_正常系 開始");

		// テスト対象となる検証対象のFormオブジェクト(エラー無し)を生成
		MailForm mailForm = new MailForm();
		mailForm.setFrom("info@linkc-19.com");
		mailForm.setSendTo("test@example.com");
		mailForm.setSubject("test");
		mailForm.setContent("testtest");
		System.out.println("mailForm" + mailForm);

		// 生成したFormオブジェクトを検証
		validator.validate(mailForm, bindingResult);

		// Formオブジェクトの検証結果を確認
		assertFalse(bindingResult.hasErrors());
		System.out.println("bindingResult.hasErrors()の値 : false");

		System.out.println("メール送信_正常系 終了");
	}

	/**
	 * Formオブジェクト(宛先でエラー)の検証を行うテストを実行する
	 */
	@Test
	void メール送信_宛先がnull() throws Exception {
		System.out.println("メール送信_宛先がnull 開始");

		// テスト対象となる検証対象のFormオブジェクト(宛先がnull)を生成
		MailForm mailForm = new MailForm();
		mailForm.setFrom("info@linkc-19.com");
		mailForm.setSendTo(null);
		mailForm.setSubject("test");
		mailForm.setContent("testtest");
		System.out.println("mailForm" + mailForm);

		// 生成したFormオブジェクトを検証
		validator.validate(mailForm, bindingResult);

		assertTrue(bindingResult.hasErrors());
		assertEquals("宛先を入力してください", bindingResult.getFieldError().getDefaultMessage());
		assertEquals("sendTo", bindingResult.getFieldError().getField());
		System.out.println("bindingResultの設定値 : "
				+ bindingResult.getFieldError().toString());

		System.out.println("メール送信_宛先がnull 終了");

	}

	/**
	 * Formオブジェクト(件名でエラー)の検証を行うテストを実行する
	 */
	@Test
	void メール送信_件名がnull() throws Exception {
		System.out.println("メール送信_宛先がnull 開始");

		// テスト対象となる検証対象のFormオブジェクト(件名がnull)を生成
		MailForm mailForm = new MailForm();
		mailForm.setFrom("info@linkc-19.com");
		mailForm.setSendTo("test@example.com");
		mailForm.setSubject(null);
		mailForm.setContent("testtest");
		System.out.println("mailForm" + mailForm);

		// 生成したFormオブジェクトを検証
		validator.validate(mailForm, bindingResult);

		assertTrue(bindingResult.hasErrors());
		assertEquals("件名を入力してください", bindingResult.getFieldError().getDefaultMessage());
		assertEquals("subject", bindingResult.getFieldError().getField());
		System.out.println("bindingResultの設定値 : "
				+ bindingResult.getFieldError().toString());

		System.out.println("メール送信_件名がnull 終了");

	}

	/**
	 * Formオブジェクト(本文でエラー)の検証を行うテストを実行する
	 */
	@Test
	void メール送信_本文がnull() throws Exception {
		System.out.println("メール送信_宛先がnull 開始");

		// テスト対象となる検証対象のFormオブジェクト(本文がnull)を生成
		MailForm mailForm = new MailForm();
		mailForm.setFrom("info@linkc-19.com");
		mailForm.setSendTo("test@example.com");
		mailForm.setSubject("test");
		mailForm.setContent(null);
		System.out.println("mailForm" + mailForm);

		// 生成したFormオブジェクトを検証
		validator.validate(mailForm, bindingResult);

		assertTrue(bindingResult.hasErrors());
		assertEquals("本文を入力してください", bindingResult.getFieldError().getDefaultMessage());
		assertEquals("content", bindingResult.getFieldError().getField());
		System.out.println("bindingResultの設定値 : "
				+ bindingResult.getFieldError().toString());

		System.out.println("メール送信_本文がnull 終了");

	}

	/**
	 * Formオブジェクト(複数項目でエラー)の検証を行うテストを実行する
	 */
	@Test
	void メール送信_複数項目がnull() throws Exception {
		System.out.println("メール送信_複数項目がnull 開始");

		// テスト対象となる検証対象のFormオブジェクト(複数項目がnull)を生成
		MailForm mailForm = new MailForm();
		mailForm.setFrom("info@linkc-19.com");
		mailForm.setSendTo(null);
		mailForm.setSubject(null);
		mailForm.setContent(null);
		System.out.println("mailForm" + mailForm);

		// 生成したFormオブジェクトを検証
		validator.validate(mailForm, bindingResult);

		assertTrue(bindingResult.hasErrors());
		assertEquals(3, bindingResult.getFieldErrors().size());
		assertEquals("宛先を入力してください", bindingResult.getFieldErrors("sendTo").get(0).getDefaultMessage());
		assertEquals("件名を入力してください", bindingResult.getFieldErrors("subject").get(0).getDefaultMessage());
		assertEquals("本文を入力してください", bindingResult.getFieldErrors("content").get(0).getDefaultMessage());

		System.out.println("エラー数: 3");
		System.out.println("bindingResultの設定値 : " + bindingResult.getAllErrors());

		System.out.println("メール送信_複数項目がnull 終了");

	}

	@Test
	void メール送信確認() throws Exception {
		System.out.println("メール送信確認 開始");

		MultipartFile multipartFile = new MockMultipartFile("test.text",
				new FileInputStream(new File("../mail/src/test/java/testfile/test.text")));

		// メールオブジェクトを生成
		MailForm mailForm = new MailForm();
		mailForm.setFrom("info@linkc-19.com");
		mailForm.setSendTo("test@example.com");
		mailForm.setSubject("test");
		mailForm.setContent("testtest");
		mailForm.setFile(multipartFile);
		System.out.println("mailForm" + mailForm);

		try {
			contactController.complete(mailForm);
		} catch (Exception e) {
			fail();
		}

		System.out.println("メール送信確認 終了");
	}

	// 空のファイルを生成
	static void createNewFile(String fileName) {

		// ファイルのパスを設定(\はエスケープしなければいけないため\の前に\を記述)
		String filePath = "C:\\sample\\" + fileName + ".txt";

		// Fileオブジェクトの生成
		File file = new File(filePath);

		try {

			// ファイルを生成
			Boolean createNewFile = file.createNewFile();

			if (createNewFile) {

				System.out.println("ファイル作成：成功");

			} else {

				System.out.println("ファイル作成：失敗");

			}

		} catch (IOException e) {

			System.out.println("例外発生");
			System.out.println(e);

		}

	}

	@Test
	void データベース登録確認() throws Exception {
		System.out.println("データベース登録確認 開始");

		// メールオブジェクトを生成
		MailForm mailForm = new MailForm();
		mailForm.setFrom("info@linkc-19.com");
		mailForm.setSendTo("test@example.com");
		mailForm.setSubject("test");
		mailForm.setContent("testtest");
		System.out.println("mailForm" + mailForm);

		//メール送信前と送信後のデータベースの件数比較
		int pre_count = mailRepository.findAll().size();
		contactController.complete(mailForm);
		int after_count = mailRepository.findAll().size();

		//実行前より1件増えている
		assertEquals(pre_count + 1, after_count);

		System.out.println("データベース登録確認 終了");
	}

}
