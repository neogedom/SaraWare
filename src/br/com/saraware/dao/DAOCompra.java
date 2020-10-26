package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.saraware.domain.Compra;
import br.com.saraware.domain.ItemCompra;
import br.com.saraware.factory.ConexaoFactory;

public class DAOCompra extends ConexaoFactory implements IDAO {

	private Connection conn;

	@Override
	public void cadastrar(Object param) throws Exception {
			}
	
	public void cadastrar(Object param, ArrayList<ItemCompra> produtos) throws Exception {
		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement psCompra = conn
					.prepareStatement("INSERT INTO compra (data, valor, revisao_codigo) VALUES (?, ?, ?)");
			
			PreparedStatement psItemCompra = conn
					.prepareStatement("INSERT INTO item_compra (quantidade, valor_unitario, valor_parcial, estoque, item_codigo, compra_codigo)"
							+ " VALUES (?, ?, ?, 1, ?, (SELECT MAX(codigo) FROM compra))");

			
			for (ItemCompra ic: produtos)
			{
				psItemCompra.setInt(1, ic.getQuantidade());
				psItemCompra.setDouble(2, ic.getValorUnitario());
				psItemCompra.setDouble(3, ic.getValorParcial());
				psItemCompra.setInt(4, ic.getItem().getCodigoItem());
				psItemCompra.executeUpdate();
			}
				
			

			psCompra.setDate(1, new Date(((Compra) param).getData().getTime()));
			psCompra.setInt(2, ((Compra) param).getValor().intValue());
			psCompra.setInt(3, ((Compra) param).getRevisaoDeVidas().getCodigoRevisaoDeVidas());
			psCompra.executeUpdate();
			
			PreparedStatement psCaixa = conn
					.prepareStatement("SELECT * FROM caixa ");
			ResultSet rsCaixa = psCaixa.executeQuery();
			
			
			if (rsCaixa.next())
			{
			
				Double novoValor = rsCaixa.getDouble("saldo") - ((Compra) param).getValor().doubleValue();

				PreparedStatement psAtualizaCaixa = conn
						.prepareStatement("UPDATE caixa SET saldo = ?");
				
				psAtualizaCaixa.setDouble(1, novoValor);
				psAtualizaCaixa.executeUpdate();
			}
			
			else
			{
				Double novoValor = rsCaixa.getDouble("saldo") - ((Compra) param).getValor().doubleValue();
				PreparedStatement psInsereCaixa = conn
						.prepareStatement("INSERT INTO caixa (codigo, saldo) VALUES (1, ?)");
				psInsereCaixa.setDouble(1, novoValor);
			}

			conn.commit();
			conn.close();

		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Compra\n" + ex.getMessage());
		}

	}


	@Override
	public void alterar(Object param) throws Exception {
	}

	@Override
	public void deletar(Object param) throws Exception {
		

	}

	@Override
	public ArrayList<Compra> listar() throws Exception {
		return null;
	}

	@Override
	public ArrayList<Compra> buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



}
