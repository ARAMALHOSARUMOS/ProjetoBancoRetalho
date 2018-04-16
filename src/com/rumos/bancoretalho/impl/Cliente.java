package com.rumos.bancoretalho.impl;


public class Cliente {

	private int id;
	private Conta[] contas;
	private String tipoCliente;
	private String nome;
	private int numeroCartaoCidadao;
	private Morada morada;
	private Telefone[] telefones;
	private Email[] emails;
	private String profissao;

	public Cliente() {
		this.nome = "";
		this.numeroCartaoCidadao = 0;
		this.profissao = "";
	}

	public Cliente(String nome, int numCC, String profissao, String tipoCliente,
			Morada morada, Telefone telefone, Email email) {
		this.nome = nome;
		this.numeroCartaoCidadao = numCC;
		this.profissao = profissao;
		this.profissao = profissao;
		this.telefones = new Telefone[1];
		this.telefones[0] = telefone; 
		this.emails = new Email[1];
		this.emails[0] = email;
		this.morada = morada;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Conta[] getContas() {
		return contas;
	}

	public void setContas(Conta[] contas) {
		this.contas = contas;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getNumeroCartaoCidadao() {
		return numeroCartaoCidadao;
	}

	public void setNumeroCartaoCidadao(int numeroCartaoCidadao) {
		this.numeroCartaoCidadao = numeroCartaoCidadao;
	}

	public Morada getMorada() {
		return morada;
	}

	public void setMorada(Morada morada) {
		this.morada = morada;
	}

	public Telefone[] getTelefones() {
		return telefones;
	}

	public void setTelefones(Telefone[] telefones) {
		this.telefones = telefones;
	}

	public Email[] getEmails() {
		return emails;
	}

	public void setEmails(Email[] emails) {
		this.emails = emails;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public boolean getClienteByCartaoCidadao(int numeroCartaoCidadao) {

		// TODO Obter cliente da base de dados

		setNome("");
		setNumeroCartaoCidadao(0);
		setMorada(null);
		setTelefones(null);
		setEmails(null);
		setProfissao("");

		return false;

	}

}
