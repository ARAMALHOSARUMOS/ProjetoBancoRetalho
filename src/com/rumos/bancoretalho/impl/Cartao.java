package com.rumos.bancoretalho.impl;

import com.rumos.bancoretalho.db.DatabaseOperations;
import com.rumos.bancoretalho.exceptions.CartaoException;

public class Cartao {
	
	public static final String CONST_CARTAO_CREDITO = "CREDITO";
	public static final String CONST_CARTAO_DEBITO = "DEBITO";
	
	private int numero;
	private String tipo;
	
	public Cartao(){
		numero = 0;
		tipo = "";
	}

	public Cartao(String tipo) {
		super();

		this.tipo = tipo;
		
		//TODO Onter proximo numero livre para o cartao!
		
	}

	public Cartao(int numero, String tipo) {
		super();
		this.numero = numero;
		this.tipo = tipo;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
		
	public boolean saveDB() throws CartaoException {
		//TODO guardar na base de dados!
		
		return true;
	}

}
