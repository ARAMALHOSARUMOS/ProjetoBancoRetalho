package com.rumos.bancoretalho.impl;

import com.rumos.bancoretalho.exceptions.CartaoException;

public class Cartao {
	
	private long numero;
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

	public Cartao(long numero, String tipo) {
		super();
		this.numero = numero;
		this.tipo = tipo;
	}

	public long getNumero() {
		return numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void getCartaoByNumero(long numero) throws CartaoException{
		
		//TODO obter cartao na DB pelo numero
	}
	
	
	public boolean saveDB() throws CartaoException {
		//TODO guardar na base de dados!
		
		return true;
	}

}
