package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import br.com.saraware.domain.RevisaoDeVidas;
import br.com.saraware.factory.ConexaoFactory;

public class DAORevisao extends ConexaoFactory implements IDAO {

	private Connection conn;

	public RevisaoDeVidas getByCodigo(Integer codigo) throws Exception {
		conn = super.getConnection();

		String sqlRevisaoDeVidas = "SELECT * FROM revisao_de_vidas WHERE (codigo = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psRevisaoDeVidas = conn.prepareStatement(sqlRevisaoDeVidas);
			psRevisaoDeVidas.setInt(1, codigo);
			ResultSet rsRevisaoDeVidas = psRevisaoDeVidas.executeQuery();

			if (rsRevisaoDeVidas.next()) {
				RevisaoDeVidas revisaoDeVidas = new RevisaoDeVidas();

				revisaoDeVidas.setCodigoRevisaoDeVidas(rsRevisaoDeVidas.getInt("codigo"));
				revisaoDeVidas.setDataDeAcontecimento(rsRevisaoDeVidas.getDate("data_acontecimento"));

				rsRevisaoDeVidas.close();
				conn.close();

				return revisaoDeVidas;
			} else {
				rsRevisaoDeVidas.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Revisao de Vidas por código!\n" + ex.getMessage());
		}

	}
	
	public RevisaoDeVidas getByData(Date data) throws Exception {
		conn = super.getConnection();

		String sqlRevisaoDeVidas = "SELECT * FROM revisao_de_vidas WHERE (data_acontecimento = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psRevisaoDeVidas = conn.prepareStatement(sqlRevisaoDeVidas);
			psRevisaoDeVidas.setDate(1, new java.sql.Date(data.getTime()));
			ResultSet rsRevisaoDeVidas = psRevisaoDeVidas.executeQuery();

			if (rsRevisaoDeVidas.next()) {
				RevisaoDeVidas revisaoDeVidas = new RevisaoDeVidas();

				revisaoDeVidas.setCodigoRevisaoDeVidas(rsRevisaoDeVidas.getInt("codigo"));
				revisaoDeVidas.setDataDeAcontecimento(rsRevisaoDeVidas.getDate("data_acontecimento"));

				rsRevisaoDeVidas.close();
				conn.close();

				return revisaoDeVidas;
			} else {
				rsRevisaoDeVidas.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Revisao de Vidas por código!\n" + ex.getMessage());
		}

	}


	
	@Override
	public void cadastrar(Object param) throws Exception {
		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);

			PreparedStatement psRevisao = conn
					.prepareStatement("INSERT INTO revisao_de_vidas (data_acontecimento) VALUES (?)");

			psRevisao.setDate(1, new java.sql.Date(((RevisaoDeVidas) param).getDataDeAcontecimento().getTime()));
			psRevisao.executeUpdate();

			conn.commit();
			conn.close();

		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Revisão de Vidas\n" + ex.getMessage());
		}

	}

	@Override
	public void alterar(Object param) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletar(Object param) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList listar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
