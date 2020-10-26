package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.saraware.domain.Encontreiro;
import br.com.saraware.domain.GrupoTrabalho;
import br.com.saraware.domain.ItemCronograma;
import br.com.saraware.domain.Tarefa;
import br.com.saraware.factory.ConexaoFactory;

public class DAOTarefa extends ConexaoFactory implements IDAO {

	private Connection conn;

	public Tarefa getByNome(String nome) throws Exception {
		conn = super.getConnection();
		String sqlTarefa = "SELECT * FROM tarefa WHERE (descricao = ?)";

		String sqlEncontreiros = "SELECT e.codigo as codigo_encontreiro, mb.codigo as codigo_membro, p.codigo as codigo_pessoa,"
				+ " p.nome, p.endereco, p.telefone, p.data_nascimento, p.numero_documento, p.tipo_documento, "
				+ "p.sexo, p.estado_civil, mb.celula_codigo, mb.hierarquia_codigo, mb.data_batismo, mb.status_IV, p.ativo"
				+ " FROM tarefa t, encontreiro e, membro mb, tarefa_encontreiro te, pessoa p WHERE "
				+ "(te.tarefa_codigo = t.codigo AND te.encontreiro_codigo = e.codigo AND "
				+ "e.membro_codigo = mb.codigo AND mb.pessoa_codigo = p.codigo AND t.descricao = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psTarefa = conn.prepareStatement(sqlTarefa);
			psTarefa.setString(1, nome);
			ResultSet rsTarefa = psTarefa.executeQuery();
			PreparedStatement psEncontreiros = conn.prepareStatement(sqlEncontreiros);
			psEncontreiros.setString(1, nome);
			ResultSet rsEncontreiros = psEncontreiros.executeQuery();
			if (rsTarefa.next()) {
				Tarefa tarefa = new Tarefa();
				GrupoTrabalho grupoTrabalho = new GrupoTrabalho();
				DAOGrupoTrabalho daogt = new DAOGrupoTrabalho();
				ItemCronograma itemCronograma = new ItemCronograma();
				DAOItemCronograma daoi = new DAOItemCronograma();

				
				tarefa.setCodigoTarefa(rsTarefa.getInt("codigo"));
				tarefa.setDescricaoTarefa(rsTarefa.getString("descricao"));
				grupoTrabalho = daogt.getByCodigo(rsTarefa.getInt("grupo_trabalho_codigo"));
				tarefa.setGrupoTrabalho(grupoTrabalho);
				itemCronograma = daoi.getByCodigo(rsTarefa.getInt("item_cronograma_codigo"));
				tarefa.setItemCronograma(itemCronograma);
				ArrayList<Encontreiro> al = new ArrayList<Encontreiro>();
				while (rsEncontreiros.next()) {
					Encontreiro encontreiro = new Encontreiro();
					encontreiro.setCodigoEncontreiro(rsEncontreiros.getInt("codigo_encontreiro"));
					encontreiro.setNome(rsEncontreiros.getString("nome"));
					al.add(encontreiro);
				}
				tarefa.setEncontreiros(al);
				rsEncontreiros.close();
				rsTarefa.close();
				conn.close();

				return tarefa;
			} else {
				rsTarefa.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Tarefa por nome!\n" + ex.getMessage());
		}

	}

	public Tarefa getByCodigo(Integer codigo) throws Exception {
		conn = super.getConnection();
		String sqlTarefa = "SELECT * FROM tarefa WHERE (codigo = ?)";

		String sqlEncontreiros = "SELECT e.codigo as codigo_encontreiro, mb.codigo as codigo_membro, p.codigo as codigo_pessoa,"
				+ " p.nome, p.endereco, p.telefone, p.data_nascimento, p.numero_documento, p.tipo_documento, "
				+ "p.sexo, p.estado_civil, mb.celula_codigo, mb.hierarquia_codigo, mb.data_batismo, mb.status_IV, p.ativo"
				+ " FROM tarefa t, encontreiro e, membro mb, tarefa_encontreiro te, pessoa p WHERE "
				+ "(te.tarefa_codigo = t.codigo AND te.encontreiro_codigo = e.codigo AND "
				+ "e.membro_codigo = mb.codigo AND mb.pessoa_codigo = p.codigo AND t.codigo = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psTarefa = conn.prepareStatement(sqlTarefa);
			psTarefa.setInt(1, codigo);
			ResultSet rsTarefa = psTarefa.executeQuery();
			PreparedStatement psEncontreiros = conn.prepareStatement(sqlEncontreiros);
			psEncontreiros.setInt(1, codigo);
			ResultSet rsEncontreiros = psEncontreiros.executeQuery();
			if (rsTarefa.next()) {
				Tarefa tarefa = new Tarefa();
				GrupoTrabalho grupoTrabalho = new GrupoTrabalho();
				DAOGrupoTrabalho daogt = new DAOGrupoTrabalho();
				ItemCronograma itemCronograma = new ItemCronograma();
				DAOItemCronograma daoi = new DAOItemCronograma();

				
				tarefa.setCodigoTarefa(rsTarefa.getInt("codigo"));
				tarefa.setDescricaoTarefa(rsTarefa.getString("descricao"));
				grupoTrabalho = daogt.getByCodigo(rsTarefa.getInt("grupo_trabalho_codigo"));
				tarefa.setGrupoTrabalho(grupoTrabalho);
				itemCronograma = daoi.getByCodigo(rsTarefa.getInt("item_cronograma_codigo"));
				tarefa.setItemCronograma(itemCronograma);
				ArrayList<Encontreiro> al = new ArrayList<Encontreiro>();
				while (rsEncontreiros.next()) {
					Encontreiro encontreiro = new Encontreiro();
					encontreiro.setCodigoEncontreiro(rsEncontreiros.getInt("codigo_encontreiro"));
					encontreiro.setNome(rsEncontreiros.getString("nome"));
					al.add(encontreiro);
				}
				tarefa.setEncontreiros(al);
				rsEncontreiros.close();
				rsTarefa.close();
				conn.close();

				return tarefa;
			} else {
				rsTarefa.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Tarefa por nome!\n" + ex.getMessage());
		}

	}

	@Override
	public void cadastrar(Object param) throws Exception {
		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM tarefa WHERE (descricao = ? )");

			ps.setString(1, ((Tarefa) param).getDescricaoTarefa());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				try {
					Tarefa tarefa = new Tarefa();
					tarefa.setCodigoTarefa(rs.getInt("codigo"));

					PreparedStatement psTarefa = conn.prepareStatement(
							"UPDATE tarefa SET descricao = ?, item_cronograma_codigo = ?, grupo_trabalho_codigo = ? WHERE (codigo = ?)");

					PreparedStatement psTarefaEncontreiroDelete = conn
							.prepareStatement("DELETE FROM tarefa_encontreiro WHERE tarefa_codigo = ?");

					PreparedStatement psTarefaEncontreiro = conn.prepareStatement(
							"INSERT INTO tarefa_encontreiro (tarefa_codigo, encontreiro_codigo) "
									+ "VALUES (?, ?)");

					PreparedStatement psEncontreiroSelect = conn
							.prepareStatement("SELECT e.codigo as codigo_encontreiro FROM encontreiro e "
									+ "INNER JOIN membro mb ON e.membro_codigo = mb.codigo"
									+ " INNER JOIN pessoa p ON mb.pessoa_codigo = p.codigo " + "WHERE p.nome = ?");

					psTarefa.setString(1, ((Tarefa) param).getDescricaoTarefa());
					psTarefa.setInt(2, ((Tarefa) param).getItemCronograma().getCodigoItemCronograma());
					psTarefa.setInt(3, ((Tarefa) param).getGrupoTrabalho().getCodigoGrupoTabalho());
					psTarefa.setInt(4, tarefa.getCodigoTarefa());
					psTarefa.executeUpdate();

					psTarefaEncontreiroDelete.setInt(1, tarefa.getCodigoTarefa());
					psTarefaEncontreiroDelete.executeUpdate();

					ResultSet rsExiste;
					for (int cont = 0; cont <= (((Tarefa) param).getEncontreiros().size() - 1); cont++) {
						psTarefaEncontreiro.setInt(1, tarefa.getCodigoTarefa());

						psEncontreiroSelect.setString(1, ((Tarefa) param).getEncontreiros().get(cont).getNome());
						rsExiste = psEncontreiroSelect.executeQuery();
						rsExiste.next();
						psTarefaEncontreiro.setInt(2, rsExiste.getInt("codigo_encontreiro"));

						psTarefaEncontreiro.execute();

					}

					conn.commit();
				} catch (Exception ex) {
					conn.rollback();
					throw new Exception("Erro ao alterar Tarefa\n" + ex.getMessage());
				}

			} else {
				PreparedStatement psTarefa = conn
						.prepareStatement("INSERT INTO tarefa(descricao, item_cronograma_codigo, grupo_trabalho_codigo) VALUES (?, ?, ?)");

				PreparedStatement psTarefaEncontreiro = conn.prepareStatement(
						"INSERT INTO tarefa_encontreiro (tarefa_codigo, encontreiro_codigo) "
								+ "VALUES ((SELECT MAX(codigo) FROM tarefa), ?)");

				psTarefa.setString(1, ((Tarefa) param).getDescricaoTarefa());
				psTarefa.setInt(2, ((Tarefa) param).getItemCronograma().getCodigoItemCronograma());
				psTarefa.setInt(3, ((Tarefa) param).getGrupoTrabalho().getCodigoGrupoTabalho());
				psTarefa.executeUpdate();

				Encontreiro encontreiro = new Encontreiro();
				DAOEncontreiro daoe = new DAOEncontreiro();
				for (int cont = 0; cont <= (((Tarefa) param).getEncontreiros().size() - 1); cont++) {
					encontreiro = daoe.getByNome(((Tarefa) param).getEncontreiros().get(cont).getNome());
					psTarefaEncontreiro.setInt(1, encontreiro.getCodigoEncontreiro());

					psTarefaEncontreiro.execute();

				}

				conn.commit();
				conn.close();
			}
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Tarefa\n" + ex.getMessage());
		}

	}

	@Override
	public void alterar(Object param) throws Exception {
		conn = super.getConnection();
		conn.setAutoCommit(false);

		try {
			conn.setAutoCommit(false);

			PreparedStatement psTarefa = conn.prepareStatement(
					"UPDATE tarefa SET descricao = ?, item_cronograma_codigo = ?, grupo_trabalho_codigo = ? WHERE (codigo = ?)");
			PreparedStatement psTarefaEncontreiroDelete = conn
					.prepareStatement("DELETE FROM tarefa_encontreiro WHERE tarefa_codigo = ?");
			PreparedStatement psTarefaEncontreiro = conn.prepareStatement(
					"INSERT INTO tarefa_encontreiro (tarefa_codigo, encontreiro_codigo) "
							+ "VALUES (?, ?)");

			psTarefa.setString(1, ((Tarefa) param).getDescricaoTarefa());
			psTarefa.setInt(2, ((Tarefa) param).getItemCronograma().getCodigoItemCronograma());
			psTarefa.setInt(3, ((Tarefa) param).getGrupoTrabalho().getCodigoGrupoTabalho());
			psTarefa.setInt(4, ((Tarefa) param).getCodigoTarefa());
			psTarefa.executeUpdate();

			psTarefaEncontreiroDelete.setInt(1, ((Tarefa) param).getCodigoTarefa());
			psTarefaEncontreiroDelete.executeUpdate();

			Encontreiro encontreiro = new Encontreiro();
			DAOEncontreiro daoe = new DAOEncontreiro();
			for (int cont = 0; cont <= (((Tarefa) param).getEncontreiros().size() - 1); cont++)
			{
			
				psTarefaEncontreiro.setInt(1, ((Tarefa) param).getCodigoTarefa());
				encontreiro = daoe.getByNome(((Tarefa) param).getEncontreiros().get(cont).getNome());
				psTarefaEncontreiro.setInt(2, encontreiro.getCodigoEncontreiro());
				psTarefaEncontreiro.execute();

			}

			conn.commit();
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("Erro ao alterar Tarefa \n" + ex.getMessage());
		}
	}

	@Override
	public void deletar(Object param) throws Exception {
		conn = super.getConnection();

		try {
			conn.setAutoCommit(false);
			Tarefa tarefa = new Tarefa();
			DAOTarefa dao = new DAOTarefa();
			tarefa = dao.getByCodigo(((Tarefa) param).getCodigoTarefa());

			PreparedStatement psTarefaEncontreiro = conn
					.prepareStatement("DELETE FROM tarefa_encontreiro WHERE(tarefa_codigo = ?)");
			psTarefaEncontreiro.setInt(1, tarefa.getCodigoTarefa());
			
			PreparedStatement ps = conn.prepareStatement("DELETE FROM tarefa WHERE (codigo = ?)");
			ps.setInt(1, tarefa.getCodigoTarefa());
			
			psTarefaEncontreiro.executeUpdate();
			ps.executeUpdate();
			
			conn.commit();
			conn.close();
		} catch (Exception ex) {
			throw new Exception("- Erro ao deletar Tarefa\n" + ex.getMessage());
		}

	}

	@Override
	public ArrayList<Tarefa> listar() throws Exception {

		conn = super.getConnection();
		ArrayList<Tarefa> al = new ArrayList<Tarefa>();
		Tarefa tarefa = new Tarefa();

		try {
			String sql = "SELECT * FROM tarefa";
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				tarefa = getByNome(rs.getString("descricao"));
				al.add(tarefa);
			}

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar tarefas\n" + ex.getMessage());
		}

		return al;
	}

	@Override
	public ArrayList<Tarefa> buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
