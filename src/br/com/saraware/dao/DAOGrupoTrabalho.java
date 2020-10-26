package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.saraware.domain.Encontreiro;
import br.com.saraware.domain.GrupoTrabalho;
import br.com.saraware.factory.ConexaoFactory;

public class DAOGrupoTrabalho extends ConexaoFactory implements IDAO {

	private Connection conn;

	public GrupoTrabalho getByNome(String nome) throws Exception {
		conn = super.getConnection();
		String sqlGrupoTrabalho = "SELECT codigo, descricao, coordenador_codigo FROM grupo_trabalho WHERE (descricao = ?)";

		String sqlEncontreiros = "SELECT e.codigo as codigo_encontreiro, mb.codigo as codigo_membro, p.codigo as codigo_pessoa,"
				+ " p.nome, p.endereco, p.telefone, p.data_nascimento, p.numero_documento, p.tipo_documento, "
				+ "p.sexo, p.estado_civil, mb.celula_codigo, mb.hierarquia_codigo, mb.data_batismo, mb.status_IV, p.ativo"
				+ " FROM grupo_trabalho gt, encontreiro e, membro mb, grupo_trabalho_encontreiro gte, pessoa p WHERE "
				+ "(gte.grupo_trabalho_codigo = gt.codigo AND gte.encontreiro_codigo = e.codigo AND "
				+ "e.membro_codigo = mb.codigo AND mb.pessoa_codigo = p.codigo AND gt.descricao = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psGrupoTrabalho = conn.prepareStatement(sqlGrupoTrabalho);
			psGrupoTrabalho.setString(1, nome);
			ResultSet rsGrupoTrabalho = psGrupoTrabalho.executeQuery();
			PreparedStatement psEncontreiros = conn.prepareStatement(sqlEncontreiros);
			psEncontreiros.setString(1, nome);
			ResultSet rsEncontreiros = psEncontreiros.executeQuery();
			if (rsGrupoTrabalho.next()) {
				GrupoTrabalho grupoTrabalho = new GrupoTrabalho();
				Encontreiro coordenador = new Encontreiro();
				DAOEncontreiro daoe = new DAOEncontreiro();

				grupoTrabalho.setCodigoGrupoTabalho(rsGrupoTrabalho.getInt("codigo"));
				grupoTrabalho.setDescricao(rsGrupoTrabalho.getString("descricao"));
				coordenador = daoe.getByCodigo(rsGrupoTrabalho.getInt("coordenador_codigo"));
				grupoTrabalho.setCoordenador(coordenador);
				ArrayList<Encontreiro> al = new ArrayList<Encontreiro>();
				while (rsEncontreiros.next()) {
					Encontreiro encontreiro = new Encontreiro();
					encontreiro.setCodigoEncontreiro(rsEncontreiros.getInt("codigo_encontreiro"));
					encontreiro.setNome(rsEncontreiros.getString("nome"));
					/*
					 * membro.setEndereco(rsEncontreiros.getString("endereco"));
					 * membro.setTelefone(rsEncontreiros.getString("telefone"));
					 * membro.setDataNascimento(rsEncontreiros.getDate(
					 * "data_nascimento"));
					 * membro.setNumeroDocumento(rsEncontreiros.getString(
					 * "numero_documento"));
					 * membro.setTipoDocumento(rsEncontreiros.getString(
					 * "tipo_documento"));
					 * membro.setSexo(rsEncontreiros.getString("sexo"));
					 * membro.setEstadoCivil(rsEncontreiros.getString("nome"));
					 * membro.setAtivo(rsEncontreiros.getBoolean("ativo"));
					 */

					al.add(encontreiro);
				}
				grupoTrabalho.setIntegrantes(al);
				rsEncontreiros.close();
				rsGrupoTrabalho.close();
				conn.close();

				return grupoTrabalho;
			} else {
				rsGrupoTrabalho.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Grupo de Trabalho por nome!\n" + ex.getMessage());
		}

	}

	public GrupoTrabalho getByCodigo(Integer codigo) throws Exception {
		conn = super.getConnection();
		String sqlGrupoTrabalho = "SELECT codigo, descricao, coordenador_codigo FROM grupo_trabalho WHERE (codigo = ?)";

		String sqlEncontreiros = "SELECT e.codigo as codigo_encontreiro, mb.codigo as codigo_membro, p.codigo as codigo_pessoa,"
				+ " p.nome, p.endereco, p.telefone, p.data_nascimento, p.numero_documento, p.tipo_documento, "
				+ "p.sexo, p.estado_civil, mb.celula_codigo, mb.hierarquia_codigo, mb.data_batismo, mb.status_IV, p.ativo"
				+ " FROM grupo_trabalho gt, encontreiro e, membro mb, grupo_trabalho_encontreiro gte, pessoa p WHERE "
				+ "(gte.grupo_trabalho_codigo = gt.codigo AND gte.encontreiro_codigo = e.codigo AND "
				+ "e.membro_codigo = mb.codigo AND mb.pessoa_codigo = p.codigo AND gt.codigo = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psGrupoTrabalho = conn.prepareStatement(sqlGrupoTrabalho);
			psGrupoTrabalho.setInt(1, codigo);
			ResultSet rsGrupoTrabalho = psGrupoTrabalho.executeQuery();
			PreparedStatement psEncontreiros = conn.prepareStatement(sqlEncontreiros);
			psEncontreiros.setInt(1, codigo);
			ResultSet rsEncontreiros = psEncontreiros.executeQuery();
			if (rsGrupoTrabalho.next()) {
				GrupoTrabalho grupoTrabalho = new GrupoTrabalho();
				Encontreiro coordenador = new Encontreiro();
				DAOEncontreiro daoe = new DAOEncontreiro();

				grupoTrabalho.setCodigoGrupoTabalho(rsGrupoTrabalho.getInt("codigo"));
				grupoTrabalho.setDescricao(rsGrupoTrabalho.getString("descricao"));
				coordenador = daoe.getByCodigo(rsGrupoTrabalho.getInt("coordenador_codigo"));
				grupoTrabalho.setCoordenador(coordenador);
				ArrayList<Encontreiro> al = new ArrayList<Encontreiro>();
				while (rsEncontreiros.next()) {
					Encontreiro encontreiro = new Encontreiro();
					encontreiro.setCodigoEncontreiro(rsEncontreiros.getInt("codigo_encontreiro"));
					encontreiro.setNome(rsEncontreiros.getString("nome"));
					/*
					 * membro.setEndereco(rsEncontreiros.getString("endereco"));
					 * membro.setTelefone(rsEncontreiros.getString("telefone"));
					 * membro.setDataNascimento(rsEncontreiros.getDate(
					 * "data_nascimento"));
					 * membro.setNumeroDocumento(rsEncontreiros.getString(
					 * "numero_documento"));
					 * membro.setTipoDocumento(rsEncontreiros.getString(
					 * "tipo_documento"));
					 * membro.setSexo(rsEncontreiros.getString("sexo"));
					 * membro.setEstadoCivil(rsEncontreiros.getString("nome"));
					 * membro.setAtivo(rsEncontreiros.getBoolean("ativo"));
					 */

					al.add(encontreiro);
				}
				grupoTrabalho.setIntegrantes(al);
				rsEncontreiros.close();
				rsGrupoTrabalho.close();
				conn.close();

				return grupoTrabalho;
			} else {
				rsGrupoTrabalho.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Grupo de Trabalho por código!\n" + ex.getMessage());
		}

	}

	@Override
	public void cadastrar(Object param) throws Exception {
		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM grupo_trabalho WHERE (descricao = ? )");

			ps.setString(1, ((GrupoTrabalho) param).getDescricao());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				try {
					GrupoTrabalho grupoTrabalho = new GrupoTrabalho();
					grupoTrabalho.setCodigoGrupoTabalho(rs.getInt("codigo"));

					PreparedStatement psGrupoTrabalho = conn.prepareStatement(
							"UPDATE grupo_trabalho SET descricao = ?, coordenador_codigo = ? WHERE (codigo = ?)");

					PreparedStatement psGrupoTrabalhoEncontreiroDelete = conn
							.prepareStatement("DELETE FROM grupo_trabalho_encontreiro WHERE grupo_trabalho_codigo = ?");

					PreparedStatement psGrupoTrabalhoEncontreiro = conn.prepareStatement(
							"INSERT INTO grupo_trabalho_encontreiro (grupo_trabalho_codigo, encontreiro_codigo) "
									+ "VALUES (?, ?)");

					PreparedStatement psEncontreiroSelect = conn
							.prepareStatement("SELECT e.codigo as codigo_encontreiro FROM encontreiro e "
									+ "INNER JOIN membro mb ON e.membro_codigo = mb.codigo"
									+ "INNER JOIN pessoa p ON mb.pessoa_codigo = p.codigo " + "WHERE p.nome = ?");

					psGrupoTrabalho.setString(1, ((GrupoTrabalho) param).getDescricao());
					psGrupoTrabalho.setInt(2, ((GrupoTrabalho) param).getCoordenador().getCodigoEncontreiro());
					psGrupoTrabalho.setInt(3, grupoTrabalho.getCodigoGrupoTabalho());
					psGrupoTrabalho.executeUpdate();

					psGrupoTrabalhoEncontreiroDelete.setInt(1, grupoTrabalho.getCodigoGrupoTabalho());
					psGrupoTrabalhoEncontreiroDelete.executeUpdate();

					ResultSet rsExiste;
					for (int cont = 0; cont <= (((GrupoTrabalho) param).getIntegrantes().size() - 1); cont++) {
						psGrupoTrabalhoEncontreiro.setInt(1, grupoTrabalho.getCodigoGrupoTabalho());

						psEncontreiroSelect.setString(1, ((GrupoTrabalho) param).getIntegrantes().get(cont).getNome());
						rsExiste = psEncontreiroSelect.executeQuery();
						rsExiste.next();
						psGrupoTrabalhoEncontreiro.setInt(2, rsExiste.getInt("codigo_encontreiro"));

						psGrupoTrabalhoEncontreiro.execute();

					}

					conn.commit();
				} catch (Exception ex) {
					conn.rollback();
					throw new Exception("Erro ao alterar Grupo de Trabalho\n" + ex.getMessage());
				}

			} else {
				PreparedStatement psGrupoTrabalho = conn
						.prepareStatement("INSERT INTO grupo_trabalho(descricao, coordenador_codigo) VALUES (?, ?)");

				PreparedStatement psGrupoTrabalhoEncontreiro = conn.prepareStatement(
						"INSERT INTO grupo_trabalho_encontreiro (grupo_trabalho_codigo, encontreiro_codigo) "
								+ "VALUES ((SELECT MAX(codigo) FROM grupo_trabalho), ?)");

				psGrupoTrabalho.setString(1, ((GrupoTrabalho) param).getDescricao());
				psGrupoTrabalho.setInt(2, ((GrupoTrabalho) param).getCoordenador().getCodigoEncontreiro());
				psGrupoTrabalho.executeUpdate();

				Encontreiro encontreiro = new Encontreiro();
				DAOEncontreiro daoe = new DAOEncontreiro();
				for (int cont = 0; cont <= (((GrupoTrabalho) param).getIntegrantes().size() - 1); cont++) {
					encontreiro = daoe.getByNome(((GrupoTrabalho) param).getIntegrantes().get(cont).getNome());
					psGrupoTrabalhoEncontreiro.setInt(1, encontreiro.getCodigoEncontreiro());

					psGrupoTrabalhoEncontreiro.execute();

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

			PreparedStatement psGrupoTrabalho = conn.prepareStatement(
					"UPDATE grupo_trabalho SET descricao = ?, coordenador_codigo = ? WHERE (codigo = ?)");
			PreparedStatement psGrupoTrabalhoEncontreiroDelete = conn
					.prepareStatement("DELETE FROM grupo_trabalho_encontreiro WHERE grupo_trabalho_codigo = ?");
			PreparedStatement psGrupoTrabalhoEncontreiro = conn.prepareStatement(
					"INSERT INTO grupo_trabalho_encontreiro (grupo_trabalho_codigo, encontreiro_codigo) "
							+ "VALUES (?, ?)");

			psGrupoTrabalho.setString(1, ((GrupoTrabalho) param).getDescricao());
			psGrupoTrabalho.setInt(2, ((GrupoTrabalho) param).getCoordenador().getCodigoEncontreiro());
			psGrupoTrabalho.setInt(3, ((GrupoTrabalho) param).getCodigoGrupoTabalho());
			psGrupoTrabalho.executeUpdate();

			psGrupoTrabalhoEncontreiroDelete.setInt(1, ((GrupoTrabalho) param).getCodigoGrupoTabalho());
			psGrupoTrabalhoEncontreiroDelete.executeUpdate();

			Encontreiro encontreiro = new Encontreiro();
			DAOEncontreiro daoe = new DAOEncontreiro();
			for (int cont = 0; cont <= (((GrupoTrabalho) param).getIntegrantes().size() - 1); cont++)
			{
			
				psGrupoTrabalhoEncontreiro.setInt(1, ((GrupoTrabalho) param).getCodigoGrupoTabalho());
				encontreiro = daoe.getByNome(((GrupoTrabalho) param).getIntegrantes().get(cont).getNome());
				psGrupoTrabalhoEncontreiro.setInt(2, encontreiro.getCodigoEncontreiro());
				psGrupoTrabalhoEncontreiro.execute();

			}

			conn.commit();
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("Erro ao alterar Grupo de Trabalho\n" + ex.getMessage());
		}
	}

	@Override
	public void deletar(Object param) throws Exception {
		conn = super.getConnection();

		try {
			conn.setAutoCommit(false);
			GrupoTrabalho grupoTrabalho = new GrupoTrabalho();
			DAOGrupoTrabalho dao = new DAOGrupoTrabalho();
			grupoTrabalho = dao.getByCodigo(((GrupoTrabalho) param).getCodigoGrupoTabalho());

			PreparedStatement psGrupoTrabalhoEncontreiro = conn
					.prepareStatement("DELETE FROM grupo_trabalho_encontreiro WHERE(grupo_trabalho_codigo = ?)");
			psGrupoTrabalhoEncontreiro.setInt(1, grupoTrabalho.getCodigoGrupoTabalho());
			
			PreparedStatement ps = conn.prepareStatement("DELETE FROM grupo_trabalho WHERE (codigo = ?)");
			ps.setInt(1, grupoTrabalho.getCodigoGrupoTabalho());
			
			psGrupoTrabalhoEncontreiro.executeUpdate();
			ps.executeUpdate();
			
			conn.commit();
			conn.close();
		} catch (Exception ex) {
			throw new Exception("- Erro ao deletar Grupo de Trabalho\n" + ex.getMessage());
		}

	}

	@Override
	public ArrayList<GrupoTrabalho> listar() throws Exception {

		conn = super.getConnection();
		ArrayList<GrupoTrabalho> al = new ArrayList<GrupoTrabalho>();
		GrupoTrabalho grupoTrabalho = new GrupoTrabalho();

		try {
			String sql = "SELECT descricao, coordenador_codigo FROM grupo_trabalho";
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				grupoTrabalho = getByNome(rs.getString("descricao"));
				al.add(grupoTrabalho);
			}

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar células\n" + ex.getMessage());
		}

		return al;
	}

	@Override
	public ArrayList<GrupoTrabalho> buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
