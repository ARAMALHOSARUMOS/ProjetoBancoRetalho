package com.rumos.bancoretalho.impl;

<<<<<<< HEAD
=======
import com.rumos.bancoretalho.exceptions.CartaoException;
>>>>>>> 7e7066a68d3609deee76fedb2f6ad00138aa0fa9

public class Cartao {
	
	public static final String CONST_CARTAO_CREDITO = "CREDITO";
	public static final String CONST_CARTAO_DEBITO = "DEBITO";
	
	private int numero;
	private String tipo;
	private int plafond;
	private int valorPlafond;
	
	public Cartao(){
		numero = 0;
		tipo = "";
	}

	public Cartao(String tipo) {
		super();

		this.tipo = tipo;
		
		//TODO Onter proximo numero livre para o cartao!
		
	}

	public Cartao(int numero, String tipo, int plafond, int valorPlafond) {
		super();
		this.numero = numero;
		this.tipo = tipo;
		this.plafond = plafond;
		this.valorPlafond = valorPlafond;
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
		
	public int getPlafond() {
		return plafond;
	}

	public void setPlafond(int plafond) {
		this.plafond = plafond;
	}

	public int getValorPlafond() {
		return valorPlafond;
	}

	public void setValorPlafond(int valorPlafond) {
		this.valorPlafond = valorPlafond;
<<<<<<< HEAD
=======
	}

	public boolean saveDB() throws CartaoException {
		//TODO guardar na base de dados!
		
		return true;
>>>>>>> 7e7066a68d3609deee76fedb2f6ad00138aa0fa9
	}

}
