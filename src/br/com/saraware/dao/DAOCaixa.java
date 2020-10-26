package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.saraware.domain.Celula;
import br.com.saraware.factory.ConexaoFactory;

public class DAOCaixa extends ConexaoFactory implements IDAO {

	private Connection conn;

	public Double getValor() throws Exception {
		conn = super.getConnection();
		String sqlCaixa = "SELECT saldo FROM caixa";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psCaixa = conn.prepareStatement(sqlCaixa);
			ResultSet rsCaixa = psCaixa.executeQuery();
			if (rsCaixa.next()) {
				Double valor;
				
				valor = rsCaixa.getDouble("saldo");
				
				rsCaixa.close();
				conn.close();

				return valor;
			} else {
				rsCaixa.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter valor de Caixa!\n" + ex.getMessage());
		}

	}
	@Override
	public void cadastrar(Object param) throws Exception {
			}

	@Override
	public void alterar(Object param) throws Exception {
			}

	@Override
	public void deletar(Object param) throws Exception {
			}

	@Override
	public ArrayList<Celula> listar() throws Exception {
		
		return null;

			}

	@Override
	public ArrayList<Celula> buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
