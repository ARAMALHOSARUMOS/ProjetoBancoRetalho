package com.rumos.bancoretalho.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import com.rumos.bancoretalho.db.DatabaseOperations;
import com.rumos.bancoretalho.exceptions.ContaException;

public class Conta {

	private long numero;
	private Cartao[] cartoes;
	private String tipoConta;
	private Movimento[] movimentos;
	private long saldo;
	private LocalDate dataAbertura;

	public Conta() {
		super();
		this.tipoConta = "";
		this.numero = 0;
		this.cartoes = null;
		this.dataAbertura = LocalDate.now();
		this.movimentos = null;
		this.saldo = 0;
	}

	public Conta(String tipoConta) {
		super();
		this.tipoConta = tipoConta;
		// TODO Obter proximo numero conta
		this.cartoes = null;
		this.dataAbertura = LocalDate.now();
		this.movimentos = null;
		this.saldo = 0;
	}

	public Conta(long numero, Cartao[] cartoes, String tipoConta,
			Movimento[] movimentos, long saldo, LocalDate dataAbertura) {
		super();
		this.numero = numero;
		this.cartoes = cartoes;
		this.tipoConta = tipoConta;
		this.movimentos = movimentos;
		this.saldo = saldo;
		this.dataAbertura = dataAbertura;
	}

	public long getNumero() {
		return numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public Cartao[] getCartoes() {
		return cartoes;
	}

	public void setCartoes(Cartao[] cartoes) {
		this.cartoes = cartoes;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}

	public Movimento[] getMovimentos() {
		return movimentos;
	}

	public void setMovimentos(Movimento[] movimentos) {
		this.movimentos = movimentos;
	}

	public long getSaldo() {
		return saldo;
	}

	public void setSaldo(long saldo) {
		this.saldo = saldo;
	}

	public LocalDate getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDate dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public void addMovimento(Movimento novoMovimento) throws ContaException {

		ArrayList<Movimento> listaMovimento = new ArrayList<Movimento>(
				Arrays.asList(getMovimentos()));

		listaMovimento.add(novoMovimento);
		
		String tipoMovimento = "";
		
		if (novoMovimento instanceof Deposito){
			tipoMovimento = "Deposito";
		} else if (novoMovimento instanceof Levantamento) {
			tipoMovimento = "Levantamento";
		} else if (novoMovimento instanceof Transferencia){
			tipoMovimento = "Transferencia";
		} else if (novoMovimento instanceof Juros){
			tipoMovimento = "Juros";
		}
		
		DatabaseOperations.insertMovimento(getNumero(), novoMovimento.getCartao().getNumero(), tipoMovimento, novoMovimento.getValor());
		
		setMovimentos((Movimento[]) listaMovimento.toArray(new Movimento[1]));
	}


}
