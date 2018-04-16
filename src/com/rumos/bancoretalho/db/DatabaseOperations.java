package com.rumos.bancoretalho.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import com.rumos.bancoretalho.impl.Agencia;
import com.rumos.bancoretalho.impl.Banco;
import com.rumos.bancoretalho.impl.Cartao;
import com.rumos.bancoretalho.impl.Cliente;
import com.rumos.bancoretalho.impl.Conta;
import com.rumos.bancoretalho.impl.Deposito;
import com.rumos.bancoretalho.impl.Email;
import com.rumos.bancoretalho.impl.Juros;
import com.rumos.bancoretalho.impl.Levantamento;
import com.rumos.bancoretalho.impl.Morada;
import com.rumos.bancoretalho.impl.Movimento;
import com.rumos.bancoretalho.impl.Telefone;
import com.rumos.bancoretalho.impl.Transferencia;

public class DatabaseOperations {

	// JDBC driver name and database URL
	// static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
	static final String DB_URL = "jdbc:derby://localhost:1527/BancoRetalho;create=true";

	// Database credentials
	static final String USER = "user";
	static final String PASS = "user";

	private static Connection conn = null;
	private static Statement stmt = null;

	public static void main(String[] args) {

		// insertBanco("Banco Rumos", 123456789);
		// int moradaTestes = retrieveMorada("Rua dos Testes", "Baixa",
		// "4200-001 Posto", "Portugal");

		// System.out.println("A morada de testes tem o código " +
		// moradaTestes);

		// insertAgencia("AgenciaRumos", 1, 12345345, 1);

		// insertEmail("Agencia", 1, "agenciarumos@gmail.com");

		// Email[] mailsTeste = retrieveEmails("Agencia", 1);
		//
		// for (int i = 0; i < mailsTeste.length; i++) {
		// System.out.println("Emails : " + mailsTeste[i].getEmail());
		// }

		// insertCliente(1, "António Ramalhosa", 12334234, "Programador", 1,
		// "VIP");

		// Banco x = new Banco();
		// x = retrieveBancoByNome("BANCO RUMOS");
		// System.out.println(x.getNome());

		// insertAgencia("Agencia 321", 1, 123123123, 1);

		ArrayList<Agencia> x = new ArrayList<Agencia>();

		x = new ArrayList<Agencia>(Arrays.asList(retrieveAgenciasByBanco(1)));

		for (int i = 0; i < x.size(); i++) {
			System.out.println(x.get(i).getNome());
		}

	}

	public static void insertBanco(String nome, int nif) {

		createConnection();

		String tablename = "BANCORETALHOSCHEMA.BANCO";

		try {
			stmt = conn.createStatement();
			stmt.execute("INSERT INTO " + tablename + " (NOME, NIF) values ('"
					+ nome.toUpperCase() + "'," + nif + ")");
			stmt.close();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();

	}

	public static boolean insertMorada(String rua, String localidade,String  codigoPostal, String pais){

		createConnection();

		String tableName = "BANCORETALHOSCHEMA.MORADAS";

		try {

			stmt = conn.createStatement();

			String instruction = "INSERT INTO " + tableName
					+ " (RUA, LOCALIDADE, CODIGO_POSTAL, PAIS) values ('" + rua
					+ "', '" + localidade + "', '" + codigoPostal + "', '" + pais + "')";

			stmt.execute(instruction);

			stmt.close();

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
			return false;
		}

		shutdown();
		return true;

	}
	
	public static Banco retrieveBancoByNome(String nome) {

		createConnection();

		String tableName = "BANCORETALHOSCHEMA.BANCO";

		Banco bancoRetornar = new Banco();

		try {
			stmt = conn.createStatement();

			String instruction = "SELECT * FROM " + tableName.toUpperCase()
					+ " WHERE NOME = '" + nome.toUpperCase() + "'";

			ResultSet results = stmt.executeQuery(instruction);

			if (results.next()) {

				bancoRetornar = new Banco(results.getInt(1),
						results.getString(2),
						retrieveAgenciasByBanco(results.getInt(1)));
			}

			stmt.close();

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();

		return bancoRetornar;

	}

	public static Agencia[] retrieveAgenciasByBanco(int codigoBanco) {

		createConnection();

		String tableName = "BANCORETALHOSCHEMA.AGENCIAS";

		ArrayList<Agencia> agenciasRetornar = new ArrayList<Agencia>();

		try {
			stmt = conn.createStatement();

			String instruction = "SELECT * FROM " + tableName.toUpperCase()
					+ " WHERE ID_BANCO = " + codigoBanco;

			ResultSet results = stmt.executeQuery(instruction);

			while (results.next()) {
				agenciasRetornar.add(new Agencia(results.getInt(1), results
						.getString(3), results.getInt(4),
						retrieveMoradaById(results.getInt(5), false)));
			}

			stmt.close();

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();

		return (Agencia[]) agenciasRetornar.toArray(new Agencia[1]);

	}
	
	public static Agencia retrieveAgenciaById(int codigoAgencia) {

		createConnection();

		String tableName = "BANCORETALHOSCHEMA.AGENCIAS";

		Agencia agenciaRetornar = new Agencia();

		try {
			stmt = conn.createStatement();

			String instruction = "SELECT * FROM " + tableName.toUpperCase()
					+ " WHERE ID = " + codigoAgencia;

			ResultSet results = stmt.executeQuery(instruction);

			if (results.next()) {
				agenciaRetornar = new Agencia(results.getInt(1),
						results.getString(3), results.getInt(4),
						retrieveMoradaById(results.getInt(5), false));
			}

			stmt.close();

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();

		return agenciaRetornar;

	}
	
	public static int retrieveMorada(String rua, String localidade,
			String codigoPostal, String pais) {

		createConnection();

		String tableName = "BANCORETALHOSCHEMA.MORADAS";
		int idMorada = 0;

		try {
			stmt = conn.createStatement();

			int numeroMoradas = 0;

			ResultSet results = stmt.executeQuery("SELECT * FROM "
					+ tableName.toUpperCase() + " WHERE RUA='"
					+ rua.toUpperCase() + "' AND LOCALIDADE='"
					+ localidade.toUpperCase() + "' AND CODIPO_POSTAL='"
					+ codigoPostal.toUpperCase() + "' AND PAIS = '"
					+ pais.toUpperCase() + "'");

			while (results.next()) {
				numeroMoradas++;
				idMorada = results.getInt(1);
			}

			if (numeroMoradas == 0) {

				stmt.execute("INSERT INTO " + tableName
						+ " (RUA, LOCALIDADE, CODIPO_POSTAL, PAIS) values ('"
						+ rua.toUpperCase() + "','" + localidade.toUpperCase()
						+ "','" + codigoPostal.toUpperCase() + "','"
						+ pais.toUpperCase() + "')");

				results = stmt.executeQuery("SELECT * FROM "
						+ tableName.toUpperCase() + " WHERE RUA='"
						+ rua.toUpperCase() + "' AND LOCALIDADE='"
						+ localidade.toUpperCase() + "' AND CODIPO_POSTAL='"
						+ codigoPostal.toUpperCase() + "' AND PAIS = '"
						+ pais.toUpperCase() + "'");

				while (results.next()) {
					numeroMoradas++;
					idMorada = results.getInt(1);
				}

			}

			stmt.close();

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();

		return idMorada;

	}

	public static Morada retrieveMoradaById(int idMorada,
			boolean createConnection) {

		if (createConnection) {
			createConnection();
		}

		String tableName = "BANCORETALHOSCHEMA.MORADAS";

		Morada moradaRetornar = null;

		try {
			stmt = conn.createStatement();

			String instruction = "SELECT * FROM " + tableName.toUpperCase()
					+ " WHERE ID = " + idMorada;

			ResultSet results = stmt.executeQuery(instruction);

			if (results.next()) {

				moradaRetornar = new Morada(results.getString(2),
						results.getString(3), results.getString(4),
						results.getString(5));
			}

			stmt.close();

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		if (createConnection) {
			shutdown();
		}

		return moradaRetornar;

	}

	public static void insertEmail(String tipoEntidade, int idEntidade,
			String email) {

		createConnection();

		String tableName = "BANCORETALHOSCHEMA.EMAILS";

		try {
			stmt = conn.createStatement();

			String instruction = "INSERT INTO " + tableName
					+ " ( ID_ENTIDADE, TIPO, EMAIL) values (" + idEntidade
					+ ", '" + tipoEntidade.toUpperCase() + "', '" + email
					+ "')";

			stmt.execute(instruction);

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();
	}

	public static Email[] retrieveEmails(String tipoEntidade, int idEntidade) {

		createConnection();

		String tableName = "BANCORETALHOSCHEMA.EMAILS";

		ArrayList<Email> emailsRetornar = new ArrayList<Email>();

		try {
			stmt = conn.createStatement();

			String instruction = "SELECT * FROM " + tableName.toUpperCase()
					+ " WHERE TIPO='" + tipoEntidade.toUpperCase()
					+ "' AND ID_ENTIDADE = " + idEntidade;

			ResultSet results = stmt.executeQuery(instruction);

			while (results.next()) {

				emailsRetornar.add(new Email(results.getInt(1), results
						.getString(4)));
			}

			stmt.close();

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();

		return (Email[]) emailsRetornar.toArray(new Email[1]);

	}

	public static void insertAgencia(String nome, int idBanco, int nif,
			int idMorada) {

		createConnection();

		String tableName = "BANCORETALHOSCHEMA.AGENCIAS";

		try {

			stmt = conn.createStatement();

			String instruction = "INSERT INTO " + tableName
					+ " (ID_BANCO, NOME, NIF, ID_MORADA) values (" + idBanco
					+ ", '" + nome + "', " + nif + ", " + idMorada + ")";

			stmt.execute(instruction);

			stmt.close();

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();

	}

	public static void insertCliente(int idAgencia, String nome,
			long cartaoCidadao, String profissao, int idMorada,
			String tipoCliente) {

		createConnection();

		String tableName = "BANCORETALHOSCHEMA.CLIENTES";

		try {
			stmt = conn.createStatement();

			String instruction = "INSERT INTO "
					+ tableName
					+ " (ID_AGENCIA, NOME, CARTAO_CIDADAO, PROFISSAO, ID_MORADA) values ("
					+ idAgencia + ", '" + nome + "'," + cartaoCidadao + ", '"
					+ profissao + "', " + idMorada + ")";

			stmt.execute(instruction);

			stmt.close();

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();

	}

	public static Cliente retrieveClienteByCC(int numeroCartaoCidadao) {

		createConnection();

		String tableName = "BANCORETALHOSCHEMA.CLIENTES";

		Cliente clienteRetonar = new Cliente();

		try {

			stmt = conn.createStatement();

			String instruction = "SELECT * FROM " + tableName.toUpperCase()
					+ " WHERE CARTAO_CIDADAO = " + numeroCartaoCidadao;

			ResultSet results = stmt.executeQuery(instruction);

			while (results.next()) {

				clienteRetonar.setId(results.getInt(1));
				clienteRetonar.setNome(results.getString(3));
				clienteRetonar.setNumeroCartaoCidadao(results.getInt(4));
				clienteRetonar.setProfissao(results.getString(5));
				clienteRetonar.setMorada(retrieveMoradaById(results.getInt(6), false));
				clienteRetonar.setEmails(retrieveEmails("Cliente",
						results.getInt(1)));
				clienteRetonar.setContas(retrieveContasCliente(results
						.getInt(1)));
				clienteRetonar.setTelefones(retrieveTelefonesByCliente(
						"CLIENTE", results.getInt(1)));
			}

			stmt.close();

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();

		return clienteRetonar;

	}

	public static void insertConta(int idCliente, String tipo, int dataAbertura) {

		createConnection();

		String tableName = "BANCORETALHOSCHEMA.CONTAS";

		try {
			stmt = conn.createStatement();

			String instruction = "INSERT INTO " + tableName
					+ " ( ID_CLIENTE, TIPO, DATA_ABERTURA) values ("
					+ idCliente + ", '" + tipo.toUpperCase() + "', "
					+ dataAbertura + ")";

			stmt.execute(instruction);

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();
	}

	public static Conta[] retrieveContasCliente(int numeroCliente) {

		createConnection();

		String tableName = "BANCORETALHOSCHEMA.CONTAS";

		ArrayList<Conta> listaContas = new ArrayList<Conta>();

		try {

			stmt = conn.createStatement();

			String instruction = "SELECT * FROM " + tableName.toUpperCase()
					+ " WHERE ID_CLIENTE = " + numeroCliente;

			ResultSet results = stmt.executeQuery(instruction);

			while (results.next()) {

				Integer value = results.getInt(6);
				int year = value / 10000;
				int month = (value % 10000) / 100;
				int day = value % 100;

				Conta novaConta = new Conta(results.getLong(1),
						retrieveCartoesByConta(results.getInt(1)),
						results.getString(3),
						retrieveMovimentosConta(results.getInt(1)),
						retrieveSaldoConta(results.getInt(1)), LocalDate.of(
								year, month, day));

				listaContas.add(novaConta);

			}

			stmt.close();

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();

		return (Conta[]) listaContas.toArray(new Conta[1]);

	}

	public static int retrieveContaOrdemCliente(int numeroCliente) {

		createConnection();

		String tableName = "BANCORETALHOSCHEMA.CONTAS";

		int contaRetornar = 0;

		try {

			stmt = conn.createStatement();

			String instruction = "SELECT ID FROM " + tableName.toUpperCase()
					+ " WHERE ID_CLIENTE = " + numeroCliente + " AND TIPO = 'ORDEM'";

			ResultSet results = stmt.executeQuery(instruction);

			if (results.next()) {

				contaRetornar = results.getInt(1);

			}

			stmt.close();

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();

		return contaRetornar;

	}	
	
	public static Cartao[] retrieveCartoesByConta(int numeroConta) {

		createConnection();

		String tableName = "BANCORETALHOSCHEMA.CARTOES";

		ArrayList<Cartao> listaCartoes = new ArrayList<Cartao>();

		try {

			stmt = conn.createStatement();

			String instruction = "SELECT * FROM " + tableName.toUpperCase()
					+ " WHERE ID_CONTA = " + numeroConta;

			ResultSet results = stmt.executeQuery(instruction);

			while (results.next()) {

				Cartao novoCartao = new Cartao(results.getInt(1),
						results.getString(3));
				listaCartoes.add(novoCartao);
			}

			stmt.close();

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();

		return (Cartao[]) listaCartoes.toArray(new Cartao[1]);

	}

	public static Cartao retrieveCartaoById(int idCartao) {

		createConnection();

		String tableName = "BANCORETALHOSCHEMA.CARTOES";

		Cartao novoCartao = new Cartao();

		ArrayList<Cartao> listaCartoes = new ArrayList<Cartao>();

		try {

			stmt = conn.createStatement();

			String instruction = "SELECT * FROM " + tableName.toUpperCase()
					+ " WHERE ID = " + idCartao;

			ResultSet results = stmt.executeQuery(instruction);

			if (results.next()) {

				novoCartao = new Cartao(results.getInt(1), results.getString(3));
				listaCartoes.add(novoCartao);
			}

			stmt.close();

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();

		return novoCartao;

	}

	public static Telefone[] retrieveTelefonesByCliente(String tipoEntidade,
			int idEntidade) {

		createConnection();

		String tableName = "BANCORETALHOSCHEMA.TELEFONES";

		ArrayList<Telefone> listaTelefones = new ArrayList<Telefone>();

		try {

			stmt = conn.createStatement();

			String instruction = "SELECT ID, NUMERO FROM "
					+ tableName.toUpperCase() + " WHERE TIPO = " + tipoEntidade
					+ " AND ID_ENTIDADE = " + idEntidade;

			ResultSet results = stmt.executeQuery(instruction);

			while (results.next()) {

				Telefone novoTelefone = new Telefone(results.getInt(1),
						results.getInt(2));
				listaTelefones.add(novoTelefone);
			}

			stmt.close();

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();

		return (Telefone[]) listaTelefones.toArray(new Telefone[1]);
	}

	public static void insertCartao(int idConta, String tipo) {

		createConnection();

		String tableName = "BANCORETALHOSCHEMA.CARTOES";

		try {
			stmt = conn.createStatement();

			String instruction = "INSERT INTO " + tableName
					+ " ( ID_CONTA, TIPO) values (" + idConta + ", '"
					+ tipo.toUpperCase() + "')";

			stmt.execute(instruction);

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();
	}

	public static void insertMovimento(int idConta, int idCartao, String tipo,
			long valor) {

		createConnection();

		String tableName = "BANCORETALHOSCHEMA.MOVIMENTOS";

		try {
			stmt = conn.createStatement();

			String instruction = "INSERT INTO " + tableName
					+ " (ID_CONTA, ID_CARTAO, TIPO, VALOR) values (" + idConta
					+ ", " + idCartao + ", '" + tipo.toUpperCase() + "', "
					+ valor + ")";

			stmt.execute(instruction);

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();
	}

	public static Movimento[] retrieveMovimentosConta(int numeroConta) {

		createConnection();

		String tableName = "BANCORETALHOSCHEMA.MOVIMENTOS";

		ArrayList<Movimento> listaMovimentos = new ArrayList<Movimento>();

		try {

			stmt = conn.createStatement();

			String instruction = "SELECT * FROM " + tableName.toUpperCase()
					+ " WHERE ID_CONTA = " + numeroConta;

			ResultSet results = stmt.executeQuery(instruction);

			while (results.next()) {

				Integer value = results.getInt(5);
				int year = value / 10000;
				int month = (value % 10000) / 100;
				int day = value % 100;

				Integer value1 = results.getInt(6);
				int hour = value1 / 10000;
				int minute = (value1 % 10000) / 100;
				int second = value1 % 100;

				if (results.getString(4).equals("LEVANTAMENTO")) {

					Levantamento novoLevantamento = new Levantamento(
							LocalDate.of(year, month, day), LocalTime.of(hour,
									minute, second),
							retrieveCartaoById(results.getInt(3)),
							results.getInt(7));

					listaMovimentos.add(novoLevantamento);

				} else if (results.getString(4).equals("DEPOSITO")) {

					Deposito novoDeposito = new Deposito(LocalDate.of(year,
							month, day), LocalTime.of(hour, minute, second),
							retrieveCartaoById(results.getInt(3)),
							results.getInt(7));

					listaMovimentos.add(novoDeposito);

				} else if (results.getString(4).equals("TRANSFERENCIA")) {

					Transferencia novaTransferencia = new Transferencia(
							LocalDate.of(year, month, day), LocalTime.of(hour,
									minute, second),
							retrieveCartaoById(results.getInt(3)),
							results.getInt(7));

					listaMovimentos.add(novaTransferencia);

				} else if (results.getString(4).equals("JUROS")) {

					Juros novosJuros = new Juros(
							LocalDate.of(year, month, day), LocalTime.of(hour,
									minute, second),
							retrieveCartaoById(results.getInt(3)),
							results.getInt(7));

					listaMovimentos.add(novosJuros);
				}

			}

			stmt.close();

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();

		return (Movimento[]) listaMovimentos.toArray(new Movimento[1]);

	}

	public static int retrieveSaldoConta(int numeroConta) {

		createConnection();

		String tableName = "BANCORETALHOSCHEMA.MOVIMENTOS";

		int saldo = 0;

		try {

			stmt = conn.createStatement();
			String instruction = "SELECT TIPO, VALOR FROM "
					+ tableName.toUpperCase() + " WHERE ID_CONTA = "
					+ numeroConta;

			ResultSet results = stmt.executeQuery(instruction);

			while (results.next()) {
				saldo += results.getInt(2);
			}

			stmt.close();

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();

		return saldo;
	}

	public static void insertTelefone(String tipoEntidade, int idEntidade,
			int numero) {

		createConnection();

		String tableName = "BANCORETALHOSCHEMA.TELEFONES";

		try {
			stmt = conn.createStatement();

			String instruction = "INSERT INTO " + tableName
					+ " (TIPOENTIDADE, ID_ENTIDADE, NUMERO) values ('"
					+ tipoEntidade + "', " + idEntidade + ", " + numero + ")";

			stmt.execute(instruction);

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();
	}

	private static void createConnection() {
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
			// Get a connection
			conn = DriverManager.getConnection(DB_URL);
		} catch (Exception except) {
			except.printStackTrace();
		}
	}

	private static void shutdown() {
		try {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				DriverManager.getConnection(DB_URL + ";shutdown=true");
				conn.close();
			}
		} catch (SQLException sqlExcept) {

		}

	}

}
