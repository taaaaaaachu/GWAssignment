package com.example.demo.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "send_result")
public class Mail {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "from")
	private String from;

	@Column(name = "to")
	private String to;

	@Column(name = "subject")
	private String subject;

	@Column(name = "detail")
	private String detail;

	@Column(name = "insert_date")
	private Timestamp insertDate;

}
