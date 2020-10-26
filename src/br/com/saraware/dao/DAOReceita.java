package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import br.com.saraware.domain.Receita;
import br.com.saraware.factory.ConexaoFactory;

public class DAOReceita extends ConexaoFactory implements IDAO {
	
	private Connection conn;
	
	public Receita getByCodigo(Integer codigo) throws Exception {
		conn = super.getConnection();

		String sqlReceita = "SELECT codigo, valor, ativo FROM receita WHERE (codigo = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psReceita = conn.prepareStatement(sqlReceita);
			psReceita.setInt(1, codigo);
			ResultSet rsReceita = psReceita.executeQuery();

			if (rsReceita.next()) {
				Receita receita = new Receita();

				receita.setCodigoReceita(rsReceita.getInt("codigo"));
				receita.setValor(rsReceita.getDouble("valor"));
				receita.setAtivo(rsReceita.getBoolean("ativo"));

				rsReceita.close();
				conn.close();

				return receita;
			} else {
				rsReceita.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Receita por nome!\n" + ex.getMessage());
		}

	}

	@Override
	public void cadastrar(Object param) throws Exception {
		// TODO Auto-generated method stub

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
