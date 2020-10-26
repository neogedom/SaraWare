package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.saraware.domain.ItemDoacao;
import br.com.saraware.domain.Meta;
import br.com.saraware.factory.ConexaoFactory;

public class DAOItemDoacao extends ConexaoFactory implements IDAO {

	private Connection conn;

	public DAOItemDoacao() {
		super();
	}

	public ItemDoacao getByNome(String nome) throws Exception {
		conn = super.getConnection();

		String sqlItemDoacao = "SELECT id.codigo as codigo_item_doacao, i.codigo as codigo_item, i.descricao, id.quantidade_meta, id.quantidade_alcancada, i.ativo, i.estoque FROM "
				+ "item_doacao id INNER JOIN item i ON id.item_codigo = i.codigo WHERE (i.descricao = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psItemDoacao = conn.prepareStatement(sqlItemDoacao);
			psItemDoacao.setString(1, nome);
			ResultSet rsItemDoacao = psItemDoacao.executeQuery();

			if (rsItemDoacao.next()) {
				ItemDoacao itemDoacao = new ItemDoacao();

				itemDoacao.setCodigoItemDoacao(rsItemDoacao.getInt("codigo_item_doacao"));
				itemDoacao.setCodigoItem(rsItemDoacao.getInt("codigo_item"));
				itemDoacao.setDescricao(rsItemDoacao.getString("descricao"));
				itemDoacao.setQuantidadeMeta(rsItemDoacao.getInt("quantidade_meta"));
				itemDoacao.setQuantidadeAlcancada(rsItemDoacao.getInt("quantidade_alcancada"));
				itemDoacao.setEstoque(rsItemDoacao.getBoolean("estoque"));
				itemDoacao.setAtivo(rsItemDoacao.getBoolean("ativo"));

				rsItemDoacao.close();
				conn.close();

				return itemDoacao;
			} else {
				rsItemDoacao.close();
				psItemDoacao.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Item de Doacao por nome!\n" + ex.getMessage());
		}

	}

	public ItemDoacao getByCodigo(Integer codigo) throws Exception {
		conn = super.getConnection();

		String sqlItemDoacao = "SELECT id.codigo as codigo_item_doacao, i.codigo as codigo_item, i.descricao, id.quantidade_meta, id.quantidade_alcancada, i.estoque, i.ativo FROM "
				+ "item_doacao id INNER JOIN item i ON id.item_codigo = i.codigo WHERE (id.codigo = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psItemDoacao = conn.prepareStatement(sqlItemDoacao);
			psItemDoacao.setInt(1, codigo);
			ResultSet rsItemDoacao = psItemDoacao.executeQuery();

			if (rsItemDoacao.next()) {
				ItemDoacao itemDoacao = new ItemDoacao();

				itemDoacao.setCodigoItemDoacao(rsItemDoacao.getInt("codigo_item_doacao"));
				itemDoacao.setCodigoItem(rsItemDoacao.getInt("codigo_item"));
				itemDoacao.setDescricao(rsItemDoacao.getString("descricao"));
				itemDoacao.setQuantidadeMeta(rsItemDoacao.getInt("quantidade_meta"));
				itemDoacao.setQuantidadeAlcancada(rsItemDoacao.getInt("quantidade_alcancada"));
				itemDoacao.setEstoque(rsItemDoacao.getBoolean("estoque"));
				itemDoacao.setAtivo(rsItemDoacao.getBoolean("ativo"));

				rsItemDoacao.close();
				conn.close();

				return itemDoacao;
			} else {
				rsItemDoacao.close();
				psItemDoacao.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Item de Doação por código!\n" + ex.getMessage());
		}

	}

	@Override
	public void cadastrar(Object param) throws Exception {
		// TODO Auto-generated method stub
		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement psItem = conn.prepareStatement("INSERT INTO item (descricao, ativo, estoque) VALUES (?, ?, false)");
			PreparedStatement psItemDoacao = conn.prepareStatement(
					"INSERT INTO item_doacao (quantidade_meta, item_codigo) VALUES (?,(SELECT MAX(codigo) FROM item))");

			psItem.setString(1, ((ItemDoacao) param).getDescricao());
			psItem.setBoolean(2, true);
			psItem.executeUpdate();

			psItemDoacao.setInt(1, ((ItemDoacao) param).getQuantidadeMeta());
			psItemDoacao.executeUpdate();

			conn.commit();
			conn.close();

		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Item de Doação\n" + ex.getMessage());
		}

	}

	@Override
	public void alterar(Object param) throws Exception {
		conn = super.getConnection();
		conn.setAutoCommit(false);

		try {

			PreparedStatement psItem = conn
					.prepareStatement("UPDATE item SET descricao = ?, ativo = true WHERE (codigo = ?)");
			PreparedStatement psItemDoacao = conn
					.prepareStatement("UPDATE item_doacao SET quantidade_meta = ? WHERE (codigo = ?)");

			psItem.setString(1, ((ItemDoacao) param).getDescricao());
			psItem.setInt(2, ((ItemDoacao) param).getCodigoItem());
			psItem.executeUpdate();

			psItemDoacao.setInt(1, ((ItemDoacao) param).getQuantidadeMeta());
			psItemDoacao.setInt(2, ((ItemDoacao) param).getCodigoItemDoacao());
			psItemDoacao.executeUpdate();

			conn.commit();
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("Erro ao alterar Item de Doação\n" + ex.getMessage());
		}
	}

	@Override
	public void deletar(Object param) throws Exception {
		conn = super.getConnection();

		try {
			conn.setAutoCommit(false);
			ItemDoacao itemDoacao = new ItemDoacao();
			DAOItemDoacao dao = new DAOItemDoacao();
			itemDoacao = dao.getByCodigo(((ItemDoacao) param).getCodigoItemDoacao());
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE item_doacao id, item i SET ativo = false WHERE (id.codigo = ? AND id.item_codigo = i.codigo)");
			ps.setInt(1, itemDoacao.getCodigoItemDoacao());
			ps.executeUpdate();

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			throw new Exception("- Erro ao deletar Item de Doação\n" + ex.getMessage());
		}

	}
	
	public void addEstoque(Integer codigo, Integer quantidade) throws Exception {
		conn = super.getConnection();

		try {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE item_doacao id, item i SET quantidade_alcancada = ?, estoque = true WHERE (id.codigo = ? AND id.item_codigo = i.codigo)");
			ps.setInt(1, quantidade);
			ps.setInt(2, codigo);
			ps.executeUpdate();

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			throw new Exception("- Erro ao colocar Item de Doação no estoque\n" + ex.getMessage());
		}

	}


	@Override
	public ArrayList<ItemDoacao> listar() throws Exception {
		conn = super.getConnection();
		ArrayList<ItemDoacao> al = new ArrayList<ItemDoacao>();
		ItemDoacao itemDoacao = new ItemDoacao();

		try {
			String sql = "SELECT id.codigo as codigo_item_doacao, i.codigo as codigo_item," + " i.descricao, i.ativo "
					+ "FROM item_doacao id INNER JOIN item i ON id.item_codigo = i.codigo WHERE ativo = true";
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				itemDoacao = getByNome(rs.getString("descricao"));
				al.add(itemDoacao);
			}

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar itens de doação\n" + ex.getMessage());
		}

		return al;

	}

	public ArrayList<ItemDoacao> listarEChecar(Integer meta) throws Exception {
		conn = super.getConnection();
		ArrayList<ItemDoacao> al = new ArrayList<ItemDoacao>();
		ItemDoacao itemDoacao = new ItemDoacao();

		try {
			String sql = "SELECT id.codigo as codigo_item_doacao, " + "i.codigo as codigo_item, i.descricao, "
					+ " i.ativo "
					+ "FROM item_doacao_meta idm INNER JOIN item_doacao id ON idm.item_doacao_codigo = id.codigo "
					+ "INNER JOIN item i ON id.item_codigo = i.codigo";

			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			DAOMeta daom = new DAOMeta();
			Meta m = new Meta();
			m = daom.getByCodigo(meta);

			int cont;
			while (rs.next()) {
				itemDoacao = getByNome(rs.getString("descricao"));
				for (cont = 0; cont <= (m.getItemsDoacaoMeta().size() - 1); cont++) {
					ItemDoacao integrante = (ItemDoacao) m.getItemsDoacaoMeta().get(cont);
					if (integrante.getCodigoItemDoacao() == itemDoacao.getCodigoItemDoacao())
						itemDoacao.setChecked(true);
				}
				al.add(itemDoacao);
			}

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar Itens de Doação\n" + ex.getMessage());
		}

		return al;
	}

	public ArrayList<ItemDoacao> listarEChecarMeta(Integer meta) throws Exception {
		conn = super.getConnection();
		ArrayList<ItemDoacao> al = new ArrayList<ItemDoacao>();
		ItemDoacao itemDoacao = new ItemDoacao();
		try {
			String sql = "SELECT id.codigo as codigo_item_doacao, " + "i.codigo as codigo_item, i.descricao, "
					+ " i.ativo, i.estoque "
					+ "FROM item_doacao_meta idm INNER JOIN item_doacao id ON idm.item_doacao_codigo = id.codigo "
					+ "INNER JOIN item i ON id.item_codigo = i.codigo WHERE idm.meta_codigo = ?";

			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, meta);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				itemDoacao = getByNome(rs.getString("descricao"));
				if (itemDoacao.getEstoque())
					itemDoacao.setChecked(true);
				}
			al.add(itemDoacao);

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar Itens de Doação\n" + ex.getMessage());
		}

		return al;
	}

	@Override
	public ArrayList<ItemDoacao> buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
