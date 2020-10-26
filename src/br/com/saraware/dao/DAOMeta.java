package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.saraware.domain.Celula;
import br.com.saraware.domain.ItemDoacao;
import br.com.saraware.domain.Meta;
import br.com.saraware.domain.RevisaoDeVidas;
import br.com.saraware.factory.ConexaoFactory;

public class DAOMeta extends ConexaoFactory implements IDAO {

	private Connection conn;

	public Meta getByNome(Integer codigo_celula, Integer codigo_revisao) throws Exception {
		conn = super.getConnection();
		String sqlMeta = "SELECT * FROM meta WHERE (celula_codigo = ? AND revisao_codigo = ?)";

		String sqlItemsDoacao = " SELECT id.codigo as codigo_item_doacao, i.codigo as codigo_item, i.descricao, "
				+ "m.celula_codigo, m.revisao_codigo, id.quantidade_meta, i.ativo "
				+ "FROM item i INNER JOIN item_doacao id ON id.item_codigo = i.codigo "
				+ "INNER JOIN item_doacao_meta idm ON idm.item_doacao_codigo = id.codigo "
				+ "INNER JOIN meta m ON idm.meta_codigo = m.codigo "
				+ "WHERE (m.celula_codigo = ? AND m.revisao_codigo = ?)";
		try {
			conn.setAutoCommit(false);
			PreparedStatement psMeta = conn.prepareStatement(sqlMeta);
			psMeta.setInt(1, codigo_celula);
			psMeta.setInt(2, codigo_revisao);
			ResultSet rsMeta = psMeta.executeQuery();
			PreparedStatement psItemsDoacao = conn.prepareStatement(sqlItemsDoacao);
			psItemsDoacao.setInt(1, codigo_celula);
			psItemsDoacao.setInt(2, codigo_revisao);
			ResultSet rsItemsDoacao = psItemsDoacao.executeQuery();
			if (rsMeta.next()) {
				Meta meta = new Meta();
				Celula celula = new Celula();
				DAOCelula daoc = new DAOCelula();
				RevisaoDeVidas revisaoDeVidas = new RevisaoDeVidas();
				DAORevisao daor = new DAORevisao();

				meta.setCodigoMeta(rsMeta.getInt("codigo"));
				celula = daoc.getByCodigo(rsMeta.getInt("celula_codigo"));
				meta.setCelula(celula);
				revisaoDeVidas = daor.getByCodigo(rsMeta.getInt("revisao_codigo"));
				meta.setRevisaoDeVidas(revisaoDeVidas);
				ArrayList<ItemDoacao> al = new ArrayList<ItemDoacao>();
				while (rsItemsDoacao.next()) {
					
					ItemDoacao itemDoacao = new ItemDoacao();
					itemDoacao.setCodigoItemDoacao(rsItemsDoacao.getInt("codigo_item_doacao"));
					itemDoacao.setDescricao(rsItemsDoacao.getString("descricao"));
					al.add(itemDoacao);
				}
				meta.setItemsDoacaoMeta(al);
				rsItemsDoacao.close();
				rsMeta.close();
				conn.close();

				return meta;
			} else {

				rsMeta.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Meta por nome!\n" + ex.getMessage());
		}

	}

	public Meta getByCodigo(Integer codigo) throws Exception {
		conn = super.getConnection();
		String sqlMeta = "SELECT * FROM meta WHERE (codigo = ?)";

		String sqlItemsDoacao = " SELECT id.codigo as codigo_item_doacao, i.codigo as codigo_item, i.descricao, "
				+ "m.celula_codigo, m.revisao_codigo, id.quantidade_meta, i.ativo "
				+ "FROM item i INNER JOIN item_doacao id ON id.item_codigo = i.codigo "
				+ "INNER JOIN item_doacao_meta idm ON idm.item_doacao_codigo = id.codigo "
				+ "INNER JOIN meta m ON idm.meta_codigo = m.codigo "
				+ "WHERE (m.codigo = ?)";
		try {
			conn.setAutoCommit(false);
			PreparedStatement psMeta = conn.prepareStatement(sqlMeta);
			psMeta.setInt(1, codigo);
			ResultSet rsMeta = psMeta.executeQuery();
			PreparedStatement psItemsDoacao = conn.prepareStatement(sqlItemsDoacao);
			psItemsDoacao.setInt(1, codigo);
			ResultSet rsItemsDoacao = psItemsDoacao.executeQuery();
			if (rsMeta.next()) {
				Meta meta = new Meta();
				Celula celula = new Celula();
				DAOCelula daoc = new DAOCelula();
				RevisaoDeVidas revisaoDeVidas = new RevisaoDeVidas();
				DAORevisao daor = new DAORevisao();

				meta.setCodigoMeta(rsMeta.getInt("codigo"));
				celula = daoc.getByCodigo(rsMeta.getInt("celula_codigo"));
				meta.setCelula(celula);
				revisaoDeVidas = daor.getByCodigo(rsMeta.getInt("revisao_codigo"));
				meta.setRevisaoDeVidas(revisaoDeVidas);
				ArrayList<ItemDoacao> al = new ArrayList<ItemDoacao>();
				while (rsItemsDoacao.next()) {
					ItemDoacao itemDoacao = new ItemDoacao();
					itemDoacao.setCodigoItemDoacao(rsItemsDoacao.getInt("codigo_item_doacao"));
					itemDoacao.setDescricao(rsItemsDoacao.getString("descricao"));
					al.add(itemDoacao);
				}
				meta.setItemsDoacaoMeta(al);
				rsItemsDoacao.close();
				rsMeta.close();
				conn.close();

				return meta;
			} else {

				rsMeta.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Meta por código!\n" + ex.getMessage());
		}
	}

	@Override
	public void cadastrar(Object param) throws Exception {
		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM meta WHERE (celula_codigo = ? AND revisao_codigo = ? )");

			ps.setInt(1, ((Meta) param).getCelula().getCodigoCelula());
			ps.setInt(2, ((Meta) param).getRevisaoDeVidas().getCodigoRevisaoDeVidas());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				try {
					Meta meta = new Meta();
					meta.setCodigoMeta(rs.getInt("codigo"));

					PreparedStatement psMeta = conn.prepareStatement(
							"UPDATE meta SET celula_codigo = ?, revisao_codigo = ? WHERE (codigo = ?)");

					PreparedStatement psMetaItemDoacaoDelete = conn
							.prepareStatement("DELETE FROM item_doacao_meta WHERE meta_codigo = ?");

					PreparedStatement psMetaItemDoacao = conn.prepareStatement(
							"INSERT INTO item_doacao_meta (meta_codigo, item_doacao_codigo) "
									+ "VALUES (?, ?)");

					PreparedStatement psItemDoacaoSelect = conn
							.prepareStatement("SELECT id.codigo as codigo_item_doacao FROM item_doacao id "
									+ "INNER JOIN item i ON id.item_codigo = i.codigo"
									+ " WHERE i.descricao = ?");

					psMeta.setInt(1, ((Meta) param).getCelula().getCodigoCelula());
					psMeta.setInt(2, ((Meta) param).getRevisaoDeVidas().getCodigoRevisaoDeVidas());
					psMeta.setInt(3, meta.getCodigoMeta());
					psMeta.executeUpdate();

					psMetaItemDoacaoDelete.setInt(1, meta.getCodigoMeta());
					psMetaItemDoacaoDelete.executeUpdate();

					ResultSet rsExiste;
					for (int cont = 0; cont <= (((Meta) param).getItemsDoacaoMeta().size() - 1); cont++) {
						psMetaItemDoacao.setInt(1, meta.getCodigoMeta());

						psItemDoacaoSelect.setString(1, ((Meta) param).getItemsDoacaoMeta().get(cont).getDescricao());
						rsExiste = psItemDoacaoSelect.executeQuery();
						rsExiste.next();
						psMetaItemDoacao.setInt(2, rsExiste.getInt("codigo_item_doacao"));

						psMetaItemDoacao.execute();

					}

					conn.commit();
				} catch (Exception ex) {
					conn.rollback();
					throw new Exception("Erro ao alterar Meta\n" + ex.getMessage());
				}

			} else {
				PreparedStatement psMeta = conn
						.prepareStatement("INSERT INTO meta (celula_codigo, revisao_codigo) VALUES (?, ?)");

				PreparedStatement psMetaItemDoacao = conn.prepareStatement(
						"INSERT INTO item_doacao_meta (meta_codigo, item_doacao_codigo) "
								+ "VALUES ((SELECT MAX(codigo) FROM meta), ?)");

				psMeta.setInt(1, ((Meta) param).getCelula().getCodigoCelula());
				psMeta.setInt(2, ((Meta) param).getRevisaoDeVidas().getCodigoRevisaoDeVidas());
				psMeta.executeUpdate();

				ItemDoacao itemDoacao = new ItemDoacao();
				DAOItemDoacao daoid = new DAOItemDoacao();
				for (int cont = 0; cont <= (((Meta) param).getItemsDoacaoMeta().size() - 1); cont++) {
					itemDoacao = daoid.getByNome(((Meta) param).getItemsDoacaoMeta().get(cont).getDescricao());
					psMetaItemDoacao.setInt(1, itemDoacao.getCodigoItemDoacao());

					psMetaItemDoacao.execute();

				}

				conn.commit();
				conn.close();
			}
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Grupo de Trabalho\n" + ex.getMessage());
		}

	}

	@Override
	public void alterar(Object param) throws Exception {
		conn = super.getConnection();
		conn.setAutoCommit(false);

		try {
			conn.setAutoCommit(false);

			PreparedStatement psMeta = conn.prepareStatement(
					"UPDATE meta SET celula_codigo = ?, revisao_codigo = ? WHERE (codigo = ?)");
			PreparedStatement psMetaItemDoacaoDelete = conn
					.prepareStatement("DELETE FROM item_doacao_meta WHERE meta_codigo = ?");
			PreparedStatement psMetaItemDoacao = conn.prepareStatement(
					"INSERT INTO item_doacao_meta (meta_codigo, item_doacao_codigo) "
							+ "VALUES (?, ?)");

			psMeta.setInt(1, ((Meta) param).getCelula().getCodigoCelula());
			psMeta.setInt(2, ((Meta) param).getRevisaoDeVidas().getCodigoRevisaoDeVidas());
			psMeta.setInt(3, ((Meta) param).getCodigoMeta());
			psMeta.executeUpdate();

			psMetaItemDoacaoDelete.setInt(1, ((Meta) param).getCodigoMeta());
			psMetaItemDoacaoDelete.executeUpdate();

			ItemDoacao itemDoacao = new ItemDoacao();
			DAOItemDoacao daoe = new DAOItemDoacao();
			for (int cont = 0; cont <= (((Meta) param).getItemsDoacaoMeta().size() - 1); cont++) {

				psMetaItemDoacao.setInt(1, ((Meta) param).getCodigoMeta());
				itemDoacao = daoe.getByNome(((Meta) param).getItemsDoacaoMeta().get(cont).getDescricao());
				psMetaItemDoacao.setInt(2, itemDoacao.getCodigoItemDoacao());
				psMetaItemDoacao.execute();

			}

			conn.commit();
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("Erro ao alterar Meta\n" + ex.getMessage());
		}
	}

	@Override
	public void deletar(Object param) throws Exception {
		conn = super.getConnection();

		try {
			conn.setAutoCommit(false);
			Meta meta = new Meta();
			DAOMeta dao = new DAOMeta();
			meta = dao.getByCodigo(((Meta) param).getCodigoMeta());

			PreparedStatement psMetaItemDoacao = conn
					.prepareStatement("DELETE FROM item_doacao_meta WHERE(meta_codigo = ?)");
			psMetaItemDoacao.setInt(1, meta.getCodigoMeta());

			PreparedStatement ps = conn.prepareStatement("DELETE FROM meta WHERE (codigo = ?)");
			ps.setInt(1, meta.getCodigoMeta());

			psMetaItemDoacao.executeUpdate();
			ps.executeUpdate();

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			throw new Exception("- Erro ao deletar Meta\n" + ex.getMessage());
		}

	}

	@Override
	public ArrayList<Meta> listar() throws Exception {

		conn = super.getConnection();
		ArrayList<Meta> al = new ArrayList<Meta>();
		Meta meta = new Meta();

		try {
			String sql = "SELECT * FROM meta";
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				meta = getByCodigo(rs.getInt("codigo"));
				al.add(meta);
			}

		}

		catch (Exception ex) {
			
			throw new Exception("Erro ao listar metas\n" + ex.getMessage());
		}

		return al;
	}

	@Override
	public ArrayList<Meta> buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
