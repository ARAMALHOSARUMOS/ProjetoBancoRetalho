package com.rumos.bancoretalho.impl;

public class Morada {

	private int id; 
	private String rua;
	private String localidade;
	private String codipoPostal;
	private String pais;

	public Morada(String rua, String localidade, String codipoPostal,
			String pais) {
		super();
		this.rua = rua;
		this.localidade = localidade;
		this.codipoPostal = codipoPostal;
		this.pais = pais;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}	
	
	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getCodipoPostal() {
		return codipoPostal;
	}

	public void setCodipoPostal(String codipoPostal) {
		this.codipoPostal = codipoPostal;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

}
