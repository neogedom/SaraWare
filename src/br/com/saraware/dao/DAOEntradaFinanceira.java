package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.saraware.domain.EntradaFinanceira;
import br.com.saraware.factory.ConexaoFactory;

public class DAOEntradaFinanceira extends ConexaoFactory implements IDAO {

	private Connection conn;

	@Override
	public void cadastrar(Object param) throws Exception {
		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement psReceita = conn
					.prepareStatement("INSERT INTO receita (valor, caixa_codigo) VALUES (?, 1)"); 
			PreparedStatement psEntradaFinanceira = conn
					.prepareStatement("INSERT INTO entrada_financeira (origem, receita_codigo) VALUES (?, (SELECT MAX(codigo) FROM receita))");
			
			
			psReceita.setDouble(1, ((EntradaFinanceira) param).getReceita().getValor());
			psReceita.executeUpdate();
		
			
			psEntradaFinanceira.setString(1, ((EntradaFinanceira) param).getOrigem());
			psEntradaFinanceira.executeUpdate();
			
			PreparedStatement psCaixa = conn
					.prepareStatement("SELECT * FROM caixa ");
			ResultSet rsCaixa = psCaixa.executeQuery();
			
			
			if (rsCaixa.next())
			{
				Double novoValor = rsCaixa.getDouble("saldo") + ((EntradaFinanceira) param).getReceita().getValor();

				PreparedStatement psAtualizaCaixa = conn
						.prepareStatement("UPDATE caixa SET saldo = ?");
				
				psAtualizaCaixa.setDouble(1, novoValor);
				psAtualizaCaixa.executeUpdate();
			}
			
			else
			{
				Double novoValor = rsCaixa.getDouble("saldo") + ((EntradaFinanceira) param).getReceita().getValor();
				PreparedStatement psInsereCaixa = conn
						.prepareStatement("INSERT INTO caixa (codigo, saldo) VALUES (1, ?)");
				psInsereCaixa.setDouble(1, novoValor);
			}

			conn.commit();
			conn.close();

		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Entrada Financeira\n" + ex.getMessage());
		}

	}

	@Override
	public void alterar(Object param) throws Exception {

		

	}

	@Override
	public void deletar(Object param) throws Exception {

		
	}

	@Override
	public ArrayList<EntradaFinanceira> listar() throws Exception {
		
		return null;

	}

	@Override
	public ArrayList<EntradaFinanceira> buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
