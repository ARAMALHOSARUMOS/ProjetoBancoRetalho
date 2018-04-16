package com.rumos.bancoretalho.impl;

import com.rumos.bancoretalho.exceptions.TelefoneException;

public class Telefone {

	private int id;
	private int numero;

	public Telefone(int numero) throws TelefoneException {
		super();
		validaTelefone();
		this.numero = numero;
	}

	public Telefone(int id, int numero) {
		super();
		this.id = id;
		this.numero = numero;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public boolean existsDB() {

		return false;
	}

	public boolean saveDB() {

		return true;
	}

	public boolean validaTelefone() throws TelefoneException {

		// TODO Confirmar se numero telefone é válido

		return true;
	}

}
