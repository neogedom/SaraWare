package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.saraware.domain.Item;
import br.com.saraware.domain.ItemCompra;
import br.com.saraware.factory.ConexaoFactory;

public class DAOItemCompra extends ConexaoFactory implements IDAO {

	private Connection conn;

	public DAOItemCompra() {
		super();
	}

	public ItemCompra getByNome(String nome) throws Exception {
		conn = super.getConnection();

		String sqlItemCompra = "SELECT ic.codigo as codigo_item_compra, i.codigo as codigo_item, i.descricao, ic.quantidade, ic.valor_unitario, i.ativo, i.estoque FROM "
				+ "item_compra ic INNER JOIN item i ON ic.item_codigo = i.codigo WHERE (i.descricao = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psItemCompra = conn.prepareStatement(sqlItemCompra);
			psItemCompra.setString(1, nome);
			ResultSet rsItemCompra = psItemCompra.executeQuery();

			if (rsItemCompra.next()) {
				ItemCompra itemCompra = new ItemCompra();

				itemCompra.setCodigoItemCompra(rsItemCompra.getInt("codigo_item_compra"));
				itemCompra.setCodigoItem(rsItemCompra.getInt("codigo_item"));
				itemCompra.setDescricao(rsItemCompra.getString("descricao"));
				itemCompra.setQuantidade(rsItemCompra.getInt("quantidade"));
				itemCompra.setValorUnitario(rsItemCompra.getDouble("valor_unitario"));
				itemCompra.setEstoque(rsItemCompra.getBoolean("estoque"));
				itemCompra.setAtivo(rsItemCompra.getBoolean("ativo"));

				rsItemCompra.close();
				conn.close();

				return itemCompra;
			} else {
				rsItemCompra.close();
				psItemCompra.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Item de Compra por nome!\n" + ex.getMessage());
		}

	}

	public ItemCompra getByCodigo(Integer codigo) throws Exception {
		conn = super.getConnection();

		String sqlItemCompra = "SELECT ic.codigo as codigo_item_compra, i.codigo as codigo_item, i.descricao, ic.quantidade, ic.valor_unitario, i.ativo, i.estoque FROM "
				+ "item_compra ic INNER JOIN item i ON ic.item_codigo = i.codigo WHERE (ic.codigo = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psItemCompra = conn.prepareStatement(sqlItemCompra);
			psItemCompra.setInt(1, codigo);
			ResultSet rsItemCompra = psItemCompra.executeQuery();

			if (rsItemCompra.next()) {
				ItemCompra itemCompra = new ItemCompra();

				itemCompra.setCodigoItemCompra(rsItemCompra.getInt("codigo_item_compra"));
				itemCompra.setCodigoItem(rsItemCompra.getInt("codigo_item"));
				itemCompra.setDescricao(rsItemCompra.getString("descricao"));
				itemCompra.setQuantidade(rsItemCompra.getInt("quantidade"));
				itemCompra.setValorUnitario(rsItemCompra.getDouble("valor"));
				itemCompra.setEstoque(rsItemCompra.getBoolean("estoque"));
				itemCompra.setAtivo(rsItemCompra.getBoolean("ativo"));

				rsItemCompra.close();
				conn.close();

				return itemCompra;
			} else {
				rsItemCompra.close();
				psItemCompra.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Item de Compra por código!\n" + ex.getMessage());
		}

	}

	@Override
	public void cadastrar(Object param) throws Exception {

		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement psItemCompra = conn.prepareStatement(
					"INSERT INTO item_compra (quantidade, valor_unitario, item_codigo) VALUES (?, ?, ?)");
			DAOItem daoi = new DAOItem();
			Item i = new Item();

			psItemCompra.setInt(1, ((ItemCompra) param).getQuantidade());
			psItemCompra.setDouble(2, ((ItemCompra) param).getValorUnitario());
			i = daoi.getByNome(((ItemCompra) param).getDescricao());
			psItemCompra.setDouble(3, i.getCodigoItem());
			psItemCompra.executeUpdate();

			conn.commit();
			conn.close();

		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Item de Compra\n" + ex.getMessage());
		}

	}

	@Override
	public void alterar(Object param) throws Exception {
		conn = super.getConnection();
		conn.setAutoCommit(false);

		try {

			PreparedStatement psItem = conn
					.prepareStatement("UPDATE item SET descricao = ?, ativo = true WHERE (codigo = ?)");
			PreparedStatement psItemCompra = conn
					.prepareStatement("UPDATE item_compra SET quantidade_meta = ?, valor_unitario = ? WHERE (codigo = ?)");

			psItem.setString(1, ((ItemCompra) param).getDescricao());
			psItem.setInt(2, ((ItemCompra) param).getCodigoItem());
			psItem.executeUpdate();

			psItemCompra.setInt(1, ((ItemCompra) param).getQuantidade());
			psItemCompra.setDouble(2, ((ItemCompra) param).getValorUnitario());
			psItemCompra.setInt(3, ((ItemCompra) param).getCodigoItemCompra());
			psItemCompra.executeUpdate();

			conn.commit();
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("Erro ao alterar Item de Compra\n" + ex.getMessage());
		}
	}

	@Override
	public void deletar(Object param) throws Exception {
		conn = super.getConnection();

		try {
			conn.setAutoCommit(false);
			ItemCompra itemCompra = new ItemCompra();
			DAOItemCompra dao = new DAOItemCompra();
			itemCompra = dao.getByCodigo(((ItemCompra) param).getCodigoItemCompra());
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE item_compra ic, item i SET ativo = false WHERE (ic.codigo = ? AND ic.item_codigo = i.codigo)");
			ps.setInt(1, itemCompra.getCodigoItemCompra());
			ps.executeUpdate();

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			throw new Exception("- Erro ao deletar Item de Compra\n" + ex.getMessage());
		}

	}
	
	public void addEstoque(Integer codigo, Integer quantidade) throws Exception {
		conn = super.getConnection();

		try {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE item_compra ic, item i SET quantidade_alcancada = ?, estoque = true WHERE (ic.codigo = ? AND ic.item_codigo = i.codigo)");
			ps.setInt(1, quantidade);
			ps.setInt(2, codigo);
			ps.executeUpdate();

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			throw new Exception("- Erro ao colocar Item de Compra no estoque\n" + ex.getMessage());
		}

	}


	@Override
	public ArrayList<ItemCompra> listar() throws Exception {
		conn = super.getConnection();
		ArrayList<ItemCompra> al = new ArrayList<ItemCompra>();
		ItemCompra itemCompra = new ItemCompra();

		try {
			String sql = "SELECT ic.codigo as codigo_item_compra, i.codigo as codigo_item," + " i.descricao, i.ativo "
					+ "FROM item_compra ic INNER JOIN item i ON ic.item_codigo = i.codigo WHERE ativo = true";
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				itemCompra = getByNome(rs.getString("descricao"));
				al.add(itemCompra);
			}

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar itens de compra\n" + ex.getMessage());
		}

		return al;

	}

	@Override
	public ArrayList<ItemCompra> buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
