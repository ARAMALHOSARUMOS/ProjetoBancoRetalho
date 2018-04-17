package com.rumos.bancoretalho.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import com.rumos.bancoretalho.db.DatabaseOperations;
import com.rumos.bancoretalho.exceptions.CartaoException;
import com.rumos.bancoretalho.exceptions.ClienteException;
import com.rumos.bancoretalho.exceptions.ContaException;
import com.rumos.bancoretalho.exceptions.EmailException;
import com.rumos.bancoretalho.exceptions.MoradaException;
import com.rumos.bancoretalho.exceptions.TelefoneException;

public class Agencia {

	private int numero;
	private String nome;
	private int nif;
	private Morada morada;
	private Cliente[] clientes;

	public Agencia() {
		this.numero = 0;
		this.nome = "";
		this.morada = null;
		this.nif = 0;
	}

	public Agencia(int numero, String nome, int nif, Morada morada) {
		this.numero = numero;
		this.nome = nome;
		this.morada = morada;
		this.nif = nif;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Morada getMorada() {
		return morada;
	}

	public void setMorada(Morada morada) {
		this.morada = morada;
	}

	public int getNif() {
		return nif;
	}

	public void setNif(int nif) {
		this.nif = nif;
	}

	public Cliente[] getClientes() {
		return clientes;
	}

	public void setClientes(Cliente[] clientes) {
		this.clientes = clientes;
	}

	public void criarCliente(String tipoCliente, String nome,
			int numeroCartaoCidadao, String rua, String localidade,
			String codigoPostal, String pais, String profissao, int telefone,
			String email) throws ClienteException, MoradaException,
			EmailException, TelefoneException, ContaException, CartaoException {

		// - Validar se já existe pessoa com o cartão de cidadao na base de
		// dados

		Cliente novoCliente = new Cliente();

		Morada moradaNovoCliente = new Morada(rua, localidade, codigoPostal,
				pais);

		if (!novoCliente.getClienteByCartaoCidadao(numeroCartaoCidadao)) {

			if (DatabaseOperations.retrieveMorada(rua, localidade,
					codigoPostal, pais) == 0) {

				// Guardar a nova morada na base de dados!
				if (!DatabaseOperations.insertMorada(rua, localidade,
						codigoPostal, pais)) {
					throw new MoradaException(
							"Não foi possível guardar a morada do cliente!");
				}
			}

			Telefone telefoneCliente = new Telefone(telefone);

			Email emailNovoCliente = new Email(email);
			
			novoCliente = new Cliente(nome, numeroCartaoCidadao, profissao,
					tipoCliente, moradaNovoCliente, telefoneCliente,
					emailNovoCliente);

			DatabaseOperations.insertCliente(getNumero(), nome,
					numeroCartaoCidadao, profissao, moradaNovoCliente.getId(),
					tipoCliente);

			Cliente cliente = DatabaseOperations.retrieveClienteByCC(numeroCartaoCidadao);
			
			DatabaseOperations.insertTelefone("CLIENTE", cliente.getId(), telefone);
			
			DatabaseOperations.insertEmail("CLIENTE", cliente.getId(), email);
			
			int ano = LocalDate.now().getYear();
			int mes = LocalDate.now().getMonthValue();
			int dia = LocalDate.now().getDayOfMonth();
			
			DatabaseOperations.insertConta(cliente.getId(), "ORDEM", ano*10000+mes*100+dia);
		
			int idConta = DatabaseOperations.retrieveIDContaOrdemCliente(cliente.getId());
			
			DatabaseOperations.insertCartao(idConta, "DEBITO");
		

		} else {
			throw new ClienteException(
					"Já existe um cliente com o mesmo cartao de cidadao!");
		}

	}

	public void criarCartao(Conta contaCliente, String tipo)
			throws ContaException, CartaoException {

		ArrayList<Cartao> cartaoList = new ArrayList<Cartao>(
				Arrays.asList(contaCliente.getCartoes()));

		for (int i = 0; i < cartaoList.size(); i++) {
			if (cartaoList.get(i).getTipo().equals("Debito")) {
				throw new CartaoException(
						"O cliente já tem um cartão de crédito");
			}
		}

		Cartao novoCartao = new Cartao(tipo);
		cartaoList.add(novoCartao);

		contaCliente.setCartoes((Cartao[]) cartaoList.toArray());

	}

	public void criarMovimento(Conta contaCliente, Cartao cartaoCliente,
			String tipo, long valor, Conta contaDestino)
			throws ContaException, CartaoException {

		if (tipo.equals("Transferencia")) {

			if (contaCliente.getSaldo() < valor) {
				throw new ContaException(
						"O seu saldo não é suficiente para processar o movimento");
			} else {
				Transferencia novaTransferencia = new Transferencia(
						LocalDate.now(), LocalTime.now(), cartaoCliente, valor);
				contaCliente.setSaldo(contaCliente.getSaldo() - valor);
				contaCliente.addMovimento(novaTransferencia);
				contaDestino.setSaldo(contaDestino.getSaldo() + valor);
				contaDestino.addMovimento(novaTransferencia);
			}

		} else if (tipo.equals("Levantamento")) {

			if (contaCliente.getSaldo() < valor) {
				throw new ContaException(
						"O seu saldo não é suficiente para processar o movimento");
			} else {
				Levantamento novoLevantamwnto = new Levantamento(
						LocalDate.now(), LocalTime.now(), cartaoCliente, valor*-1);
				contaCliente.setSaldo(contaCliente.getSaldo() - valor);
				contaCliente.addMovimento(novoLevantamwnto);
			}

		} else if (tipo.equals("Deposito")) {

			Deposito novoDeposito = new Deposito(LocalDate.now(),
					LocalTime.now(), cartaoCliente, valor);
			contaCliente.setSaldo(contaCliente.getSaldo() + valor);
			contaCliente.addMovimento(novoDeposito);

		} else if (tipo.equals("Juros")) {

			Juros novosJuros = new Juros(LocalDate.now(), LocalTime.now(),
					cartaoCliente, valor);
			contaCliente.setSaldo(contaCliente.getSaldo() + valor);
			contaCliente.addMovimento(novosJuros);

		}

	}
}
