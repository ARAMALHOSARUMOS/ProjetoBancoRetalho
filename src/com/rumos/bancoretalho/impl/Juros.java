package com.rumos.bancoretalho.impl;

import java.time.LocalDate;
import java.time.LocalTime;

public class Juros extends Movimento {

	public Juros(LocalDate data, LocalTime time, Cartao cartao, long valor) {
		super(data, time, cartao, valor);
	}

	@Override
	public boolean atualizaValor() {
		// TODO Auto-generated method stub
		return false;
	}

}
