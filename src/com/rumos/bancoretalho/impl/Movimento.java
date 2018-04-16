package com.rumos.bancoretalho.impl;

import java.time.LocalDate;
import java.time.LocalTime;


public abstract class Movimento {
	
	private LocalDate data;
	private LocalTime hora;
	private Cartao cartao;
	private long valor;
	
	public Movimento(LocalDate data, LocalTime hora, Cartao cartao, long valor) {
		super();
		this.data = data;
		this.hora = hora;
		this.cartao = cartao;
		this.valor = valor;
	}
	
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}
	public Cartao getCartao() {
		return cartao;
	}

	public void setCartao(Cartao cartao) {
		this.cartao = cartao;
	}

	public long getValor() {
		return valor;
	}
	public void setValor(long valor) {
		this.valor = valor;
	}
	
	public abstract boolean atualizaValor(); 

}
