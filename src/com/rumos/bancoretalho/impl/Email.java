package com.rumos.bancoretalho.impl;

import com.rumos.bancoretalho.exceptions.EmailException;

public class Email {

	private int id;
	private String email;

	public Email(String email) throws EmailException {

		if (validate(email)) {
			this.setEmail(email);
		} else {
			throw new EmailException("O email não é válido!");
		}

	}

	public Email(int id, String email) {
		super();
		this.id = id;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private boolean validate(String email) {

		if (email.matches("[a-zA-Z0-9\\.]+@[a-zA-Z0-9\\-\\_\\.]+\\.[a-zA-Z0-9]{3}")){
			return true;
		}
		
		return false;
	}	
	
}
