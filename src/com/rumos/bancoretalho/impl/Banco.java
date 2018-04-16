package com.rumos.bancoretalho.impl;

import javafx.application.Application;
import javafx.stage.Stage;

import com.rumos.bancoretalho.db.DatabaseOperations;

public class Banco extends Application {

	private int id;
	private String nome;
	private Agencia[] agencias;
	
	public Banco() {
		this.id = 0;
		this.nome = "";
		this.agencias = null;
	}

	public Banco(int id, String nome, Agencia[] agencias) {
		super();
		this.id = id;
		this.nome = nome;
		this.agencias = agencias;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Agencia[] getAgencias() {
		return agencias;
	}

	public void setAgencias(Agencia[] agencias) {
		this.agencias = agencias;
	}

	public boolean criarAgencia(String nome, int nif, String rua, String localidade, String codigoPostal, String pais) {
		
		int idMoradaAgencia = DatabaseOperations.retrieveMorada(rua, localidade, codigoPostal, pais);

		DatabaseOperations.insertAgencia(nome, getId(), nif, idMoradaAgencia);

		return true;
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("Passei aqui!");
		
	}

}
