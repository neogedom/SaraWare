package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.saraware.domain.Item;
import br.com.saraware.factory.ConexaoFactory;

public class DAOItem extends ConexaoFactory implements IDAO {

	private Connection conn;

	public Item getByNome(String nome) throws Exception {
		conn = super.getConnection();

		String sqlItem = "SELECT codigo, descricao, ativo FROM item WHERE (descricao = ?)";
		String sqlItemDoacao = "SELECT i.codigo as codigo_item, i.descricao, i.estoque, id.quantidade_alcancada FROM item_doacao id INNER JOIN item i ON id.item_codigo = i.codigo WHERE (i.descricao = ?)";
		String sqlItemCompra = "SELECT i.codigo as codigo_item, i.descricao, i.estoque, ic.quantidade FROM item_compra ic INNER JOIN item i ON ic.item_codigo = i.codigo WHERE (i.descricao = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psItem = conn.prepareStatement(sqlItem);
			psItem.setString(1, nome);
			ResultSet rsItem = psItem.executeQuery();

			if (rsItem.next()) {
				Item item = new Item();

				PreparedStatement psItemDoacao = conn.prepareStatement(sqlItemDoacao);
				psItemDoacao.setString(1, nome);
				ResultSet rsItemDoacao = psItemDoacao.executeQuery();

				PreparedStatement psItemCompra = conn.prepareStatement(sqlItemCompra);
				psItemCompra.setString(1, nome);
				ResultSet rsItemCompra = psItemCompra.executeQuery();

				item.setCodigoItem(rsItem.getInt("codigo"));
				item.setDescricao(rsItem.getString("descricao"));
				item.setAtivo(rsItem.getBoolean("ativo"));

				int quantidade = 0;
				boolean estoque;
				
				
				if (rsItemCompra.next()) {
				
					estoque = rsItemCompra.getBoolean("estoque");
					
				if (estoque) {
						
						int quantidade_compra = rsItemCompra.getInt("quantidade");
						quantidade = quantidade + quantidade_compra;
					}
				}

				if (rsItemDoacao.next()) {

					estoque = rsItemDoacao.getBoolean("estoque");

					if (estoque) {
						int quantidade_alcancada = rsItemDoacao.getInt("quantidade_alcancada");
						quantidade = quantidade + quantidade_alcancada;

					}
				}

				item.setQuantidade(quantidade);

				rsItem.close();
				conn.close();

				return item;
			} else {
				rsItem.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Item por nome!\n" + ex.getMessage());
		}

	}

	public Item getByCodigo(Integer codigo) throws Exception {
		conn = super.getConnection();

		String sqlItem = "SELECT codigo, descricao, ativo FROM item WHERE (descricao = ?)";
		String sqlItemDoacao = "SELECT i.codigo as codigo_item, i.descricao, i.estoque, id.quantidade_alcancada FROM item_doacao id INNER JOIN item i ON id.item_codigo = i.codigo WHERE (i.codigo = ?)";
		String sqlItemCompra = "SELECT i.codigo as codigo_item, i.descricao, i.estoque, ic.quantidade FROM item_compra ic INNER JOIN item i ON ic.item_codigo = i.codigo WHERE (i.codigo = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psItem = conn.prepareStatement(sqlItem);
			psItem.setInt(1, codigo);
			ResultSet rsItem = psItem.executeQuery();

			if (rsItem.next()) {
				Item item = new Item();

				PreparedStatement psItemDoacao = conn.prepareStatement(sqlItemDoacao);
				psItemDoacao.setInt(1, codigo);
				ResultSet rsItemDoacao = psItemDoacao.executeQuery();

				PreparedStatement psItemCompra = conn.prepareStatement(sqlItemCompra);
				psItemCompra.setInt(1, codigo);
				ResultSet rsItemCompra = psItemCompra.executeQuery();

				item.setCodigoItem(rsItem.getInt("codigo"));
				item.setDescricao(rsItem.getString("descricao"));
				item.setAtivo(rsItem.getBoolean("ativo"));

				int quantidade = 0;
				boolean estoque;

				if (rsItemCompra.next()) {
					estoque = rsItemCompra.getBoolean("estoque");
					if (estoque) {
						int quantidade_compra = rsItemDoacao.getInt("quantidade");
						quantidade = quantidade + quantidade_compra;
					}
				}

				if (rsItemDoacao.next()) {

					estoque = rsItemDoacao.getBoolean("estoque");

					if (estoque) {
						int quantidade_alcancada = rsItemDoacao.getInt("quantidade_alcancada");
						quantidade = quantidade + quantidade_alcancada;

					}
				}

				item.setQuantidade(quantidade);

				rsItem.close();
				conn.close();

				return item;
			} else {
				rsItem.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Item por nome!\n" + ex.getMessage());
		}

	}

	@Override
	public void cadastrar(Object param) throws Exception {
		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn
					.prepareStatement("SELECT codigo, descricao, ativo FROM item WHERE (descricao = ?)");

			ps.setString(1, ((Item) param).getDescricao());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				try {
					Item item = new Item();
					item.setCodigoItem(rs.getInt("codigo"));

					PreparedStatement psItem = conn
							.prepareStatement("UPDATE item SET descricao = ?, ativo = true  WHERE (codigo = ?)");

					psItem.setString(1, ((Item) param).getDescricao());
					psItem.setInt(2, item.getCodigoItem());
					psItem.executeUpdate();

					conn.commit();
				} catch (Exception ex) {
					conn.rollback();
					throw new Exception("Erro ao alterar Item\n" + ex.getMessage());
				}

			} else {
				PreparedStatement psItem = conn
						.prepareStatement("INSERT INTO item (descricao, estoque, ativo) VALUES (?, 0, ?)");
				psItem.setString(1, ((Item) param).getDescricao());
				psItem.setBoolean(2, true);
				psItem.executeUpdate();

				conn.commit();
				conn.close();
			}
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Item\n" + ex.getMessage());
		}

	}

	public void comprar(Object param, Double valor, Integer quantidade) throws Exception {
		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			DAOItem daoi = new DAOItem();
			Item i = new Item();
			
			PreparedStatement psItemCompra = conn
					.prepareStatement("INSERT INTO item_compra (valor_unitario, quantidade, item_codigo, estoque) VALUES (?, ?, ?, 1)");
			PreparedStatement psItem = conn
					.prepareStatement("UPDATE item SET estoque = 1, ativo = true WHERE (codigo = ?)");
			
			i = daoi.getByNome(((Item) param).getDescricao());
			
			psItemCompra.setDouble(1, valor);
			psItemCompra.setInt(2, quantidade);
			psItemCompra.setInt(3, i.getCodigoItem() );
			psItemCompra.executeUpdate();
			
			psItem.setInt(1, i.getCodigoItem() );
			psItem.executeUpdate();

			conn.commit();
			conn.close();
			
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao comprar Item\n" + ex.getMessage());
		}

	}
	
	public void doar(Object param, Integer quantidade) throws Exception {
		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			DAOItem daoi = new DAOItem();
			Item i = new Item();
			i = daoi.getByNome(((Item) param).getDescricao());
			
			PreparedStatement ps = conn
					.prepareStatement("SELECT i.codigo as codigo_item, id.quantidade_alcancada, descricao, ativo FROM item_doacao id INNER JOIN item i ON id.item_codigo = i.codigo WHERE (descricao = ?)");

			ps.setString(1, ((Item) param).getDescricao());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				
				PreparedStatement psItem = conn
						.prepareStatement("UPDATE item_doacao SET quantidade_alcancada = ? WHERE (item_codigo = ?)");

				int quantidadeNova = rs.getInt("quantidade_alcancada") + quantidade; 
				psItem.setInt(1, quantidadeNova);
				psItem.setInt(2, i.getCodigoItem());
				psItem.executeUpdate();

				conn.commit();
			}
			
			PreparedStatement psItemCompra = conn
					.prepareStatement("INSERT INTO item_doacao (quantidade_alcancada, item_codigo) VALUES (?, ?)");
			PreparedStatement psItem = conn
					.prepareStatement("UPDATE item SET estoque = 1, ativo = true WHERE (codigo = ?)");
			
			
			
			psItemCompra.setInt(1, quantidade);
			psItemCompra.setInt(2, i.getCodigoItem() );
			psItemCompra.executeUpdate();
			
			psItem.setInt(1, i.getCodigoItem() );
			psItem.executeUpdate();

			conn.commit();
			conn.close();
			
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao comprar Item\n" + ex.getMessage());
		}

	}

	@Override
	public void alterar(Object param) throws Exception {

		conn = super.getConnection();

		conn.setAutoCommit(false);
		PreparedStatement ps = conn
				.prepareStatement("SELECT codigo, descricao, ativo FROM item WHERE (descricao = ? AND ativo = false)");

		ps.setString(1, ((Item) param).getDescricao());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {

			try {
				Item item = new Item();
				item.setCodigoItem(rs.getInt("codigo"));

				PreparedStatement psItemDelete = conn.prepareStatement("DELETE FROM item  WHERE (codigo = ?)");

				psItemDelete.setInt(1, item.getCodigoItem());
				psItemDelete.executeUpdate();

				PreparedStatement psItem = conn
						.prepareStatement("UPDATE item SET descricao = ?, ativo = true  WHERE (codigo = ?)");
				psItem.setString(1, ((Item) param).getDescricao());
				psItem.setInt(2, ((Item) param).getCodigoItem());
				psItem.executeUpdate();

				conn.commit();
			} catch (Exception ex) {
				conn.rollback();
				throw new Exception("Erro ao alterar Item\n" + ex.getMessage());
			}

		} else {

			try {
				conn.setAutoCommit(false);
				PreparedStatement psItem = conn.prepareStatement("UPDATE item SET descricao = ? WHERE (codigo = ?)");

				psItem.setString(1, ((Item) param).getDescricao());
				psItem.setInt(2, ((Item) param).getCodigoItem());
				psItem.executeUpdate();

				conn.commit();
			} catch (Exception ex) {
				conn.rollback();
				throw new Exception("Erro ao alterar Item\n" + ex.getMessage());
			}
		}

	}

	@Override
	public void deletar(Object param) throws Exception {

		conn = super.getConnection();

		try {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("UPDATE item SET ativo = false WHERE (codigo = ?)");
			ps.setInt(1, ((Item) param).getCodigoItem());
			ps.executeUpdate();

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			throw new Exception("- Erro ao deletar Item\n" + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Item> listar() throws Exception {
		// TODO Auto-generated method stub
		conn = super.getConnection();
		ArrayList<Item> al = new ArrayList<Item>();
		Item item = new Item();

		try {
			String sql = "SELECT descricao FROM item WHERE (ativo = true )";
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				item = getByNome(rs.getString("descricao"));
				al.add(item);
			}

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar items\n" + ex.getMessage());
		}

		return al;

	}

	@Override
	public ArrayList<Item> buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
