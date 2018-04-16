package com.rumos.bancoretalho.impl;

import java.time.LocalDate;
import java.time.LocalTime;

public class Deposito extends Movimento {

	public Deposito(LocalDate data, LocalTime time, Cartao cartao, long valor) {
		super(data, time, cartao, valor);
	}

	@Override
	public boolean atualizaValor() {
		// TODO Auto-generated method stub
		return false;
	}

}
