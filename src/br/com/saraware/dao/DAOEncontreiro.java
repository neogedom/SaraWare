package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.saraware.domain.Encontreiro;
import br.com.saraware.domain.GrupoTrabalho;
import br.com.saraware.domain.Hierarquia;
import br.com.saraware.domain.Tarefa;
import br.com.saraware.factory.ConexaoFactory;

public class DAOEncontreiro extends ConexaoFactory implements IDAO {

	private Connection conn;

	public Encontreiro getByNome(String nome) throws Exception {
		conn = super.getConnection();

		String sqlEncontreiro = "SELECT e.codigo as codigo_encontreiro, mb.codigo as codigo_membro, "
				+ "p.codigo as codigo_pessoa, p.nome, p.endereco, p.telefone, p.data_nascimento, "
				+ "p.numero_documento, p.tipo_documento, p.sexo, p.estado_civil, "
				+ "mb.hierarquia_codigo, mb.data_batismo, mb.status_IV, p.ativo "
				+ "FROM encontreiro e INNER JOIN membro mb ON e.membro_codigo = mb.codigo "
				+ "INNER JOIN pessoa p ON mb.pessoa_codigo = p.codigo WHERE (p.nome = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psEncontreiro = conn.prepareStatement(sqlEncontreiro);
			psEncontreiro.setString(1, nome);
			ResultSet rsEncontreiro = psEncontreiro.executeQuery();

			if (rsEncontreiro.next()) {
				Encontreiro encontreiro = new Encontreiro();
				Hierarquia hierarquia = new Hierarquia();
				DAOHierarquia daoh = new DAOHierarquia();

				encontreiro.setCodigoEncontreiro(rsEncontreiro.getInt("codigo_encontreiro"));
				encontreiro.setCodigoMembro(rsEncontreiro.getInt("codigo_membro"));
				encontreiro.setCodigoPessoa(rsEncontreiro.getInt("codigo_pessoa"));
				encontreiro.setNome(rsEncontreiro.getString("nome"));
				encontreiro.setEndereco(rsEncontreiro.getString("endereco"));
				encontreiro.setTelefone(rsEncontreiro.getString("telefone"));
				encontreiro.setDataNascimento(rsEncontreiro.getDate("data_nascimento"));
				encontreiro.setNumeroDocumento(rsEncontreiro.getString("numero_documento"));
				encontreiro.setTipoDocumento(rsEncontreiro.getString("tipo_documento"));
				encontreiro.setSexo(rsEncontreiro.getString("sexo"));
				encontreiro.setEstadoCivil(rsEncontreiro.getString("estado_civil"));
				hierarquia = daoh.getByCodigo(rsEncontreiro.getInt("hierarquia_codigo"));
				encontreiro.setHierarquia(hierarquia);
				encontreiro.setAtivo(rsEncontreiro.getBoolean("ativo"));

				rsEncontreiro.close();
				conn.close();

				return encontreiro;
			} else {
				rsEncontreiro.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Encontreiro por nome!\n" + ex.getMessage());
		}

	}
	
	public Encontreiro getByCodigo(Integer codigo) throws Exception {
		conn = super.getConnection();

		String sqlEncontreiro = "SELECT e.codigo as codigo_encontreiro, mb.codigo as codigo_membro, "
				+ "p.codigo as codigo_pessoa, p.nome, p.endereco, p.telefone, p.data_nascimento, "
				+ "p.numero_documento, p.tipo_documento, p.sexo, p.estado_civil, "
				+ "mb.hierarquia_codigo, mb.data_batismo, mb.status_IV, p.ativo "
				+ "FROM encontreiro e INNER JOIN membro mb ON e.membro_codigo = mb.codigo "
				+ "INNER JOIN pessoa p ON mb.pessoa_codigo = p.codigo WHERE (e.codigo = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psEncontreiro = conn.prepareStatement(sqlEncontreiro);
			psEncontreiro.setInt(1, codigo);
			ResultSet rsEncontreiro = psEncontreiro.executeQuery();

			if (rsEncontreiro.next()) {
				Encontreiro encontreiro = new Encontreiro();
				Hierarquia hierarquia = new Hierarquia();
				DAOHierarquia daoh = new DAOHierarquia();

				encontreiro.setCodigoEncontreiro(rsEncontreiro.getInt("codigo_encontreiro"));
				encontreiro.setCodigoMembro(rsEncontreiro.getInt("codigo_membro"));
				encontreiro.setCodigoPessoa(rsEncontreiro.getInt("codigo_pessoa"));
				encontreiro.setNome(rsEncontreiro.getString("nome"));
				encontreiro.setEndereco(rsEncontreiro.getString("endereco"));
				encontreiro.setTelefone(rsEncontreiro.getString("telefone"));
				encontreiro.setDataNascimento(rsEncontreiro.getDate("data_nascimento"));
				encontreiro.setNumeroDocumento(rsEncontreiro.getString("numero_documento"));
				encontreiro.setTipoDocumento(rsEncontreiro.getString("tipo_documento"));
				encontreiro.setSexo(rsEncontreiro.getString("sexo"));
				encontreiro.setEstadoCivil(rsEncontreiro.getString("estado_civil"));
				hierarquia = daoh.getByCodigo(rsEncontreiro.getInt("hierarquia_codigo"));
				encontreiro.setHierarquia(hierarquia);
				encontreiro.setAtivo(rsEncontreiro.getBoolean("ativo"));

				rsEncontreiro.close();
				conn.close();

				return encontreiro;
			} else {
				rsEncontreiro.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Encontreiro por código!\n" + ex.getMessage());
		}

	}

	@Override
	public void cadastrar(Object param) throws Exception {

		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement psEncontreiro = conn
					.prepareStatement("INSERT INTO encontreiro (membro_codigo) VALUES (?)");

			psEncontreiro.setInt(1, ((Encontreiro) param).getCodigoMembro());
			psEncontreiro.executeUpdate();

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Encontreiro\n" + ex.getMessage());
		}
	}

	@Override
	public void alterar(Object param) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletar(Object param) throws Exception {
		conn = super.getConnection();

		try {
			conn.setAutoCommit(false);
			Encontreiro encontreiro = new Encontreiro();
			DAOEncontreiro dao = new DAOEncontreiro();
			encontreiro = dao.getByNome(((Encontreiro) param).getNome());
			PreparedStatement ps = conn.prepareStatement("DELETE FROM encontreiro WHERE (codigo = ?)");
			ps.setInt(1, encontreiro.getCodigoEncontreiro());
			ps.executeUpdate();

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			throw new Exception("- Erro ao deletar Encontreiro\n" + ex.getMessage());
		}

	}

	@Override
	public ArrayList<Encontreiro> listar() throws Exception {
		conn = super.getConnection();
		ArrayList<Encontreiro> al = new ArrayList<Encontreiro>();
		Encontreiro encontreiro = new Encontreiro();

		try {
			String sql = "SELECT e.codigo as codigo_encontreiro, mb.codigo as codigo_membro, "
					+ "p.codigo as codigo_pessoa, p.nome, p.endereco, p.telefone, p.data_nascimento, "
					+ "p.numero_documento, p.tipo_documento, p.sexo, p.estado_civil, mb.hierarquia_codigo, "
					+ "mb.data_batismo, mb.status_IV, p.ativo "
					+ "FROM encontreiro e INNER JOIN membro mb ON e.membro_codigo = mb.codigo "
					+ "INNER JOIN pessoa p ON mb.pessoa_codigo = p.codigo";

			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				encontreiro = getByNome(rs.getString("nome"));
				al.add(encontreiro);
			}

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar encontreiros\n" + ex.getMessage());
		}

		return al;
	}
	
	public ArrayList<Encontreiro> listarDoze() throws Exception {
		conn = super.getConnection();
		ArrayList<Encontreiro> al = new ArrayList<Encontreiro>();
		Encontreiro encontreiro = new Encontreiro();

		try {
			String sql = "SELECT e.codigo as codigo_encontreiro, mb.codigo as codigo_membro, "
					+ "p.codigo as codigo_pessoa, p.nome, p.endereco, p.telefone, p.data_nascimento, "
					+ "p.numero_documento, p.tipo_documento, p.sexo, p.estado_civil, mb.hierarquia_codigo, "
					+ "mb.data_batismo, mb.status_IV, p.ativo "
					+ "FROM encontreiro e INNER JOIN membro mb ON e.membro_codigo = mb.codigo "
					+ "INNER JOIN pessoa p ON mb.pessoa_codigo = p.codigo WHERE mb.hierarquia_codigo = 1";

			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				encontreiro = getByNome(rs.getString("nome"));
				al.add(encontreiro);
			}

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar doze\n" + ex.getMessage());
		}

		return al;
	}

	public ArrayList<Encontreiro> listarEChecar(Integer grupo, Integer naoListar) throws Exception {
		conn = super.getConnection();
		ArrayList<Encontreiro> al = new ArrayList<Encontreiro>();
		Encontreiro encontreiro = new Encontreiro();

		try {
			String sql = "SELECT e.codigo as codigo_encontreiro, mb.codigo as codigo_membro, "
					+ "p.codigo as codigo_pessoa, p.nome, p.endereco, p.telefone, p.data_nascimento, "
					+ "p.numero_documento, p.tipo_documento, p.sexo, p.estado_civil, mb.hierarquia_codigo, "
					+ "mb.data_batismo, mb.status_IV, p.ativo "
					+ "FROM encontreiro e INNER JOIN membro mb ON e.membro_codigo = mb.codigo "
					+ "INNER JOIN pessoa p ON mb.pessoa_codigo = p.codigo WHERE e.codigo != ?";

			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, naoListar);
			
			ResultSet rs = ps.executeQuery();
			
			DAOGrupoTrabalho daogt = new DAOGrupoTrabalho();
			GrupoTrabalho gt = new GrupoTrabalho();
			gt = daogt.getByCodigo(grupo);
			int cont;
			while (rs.next()) {
				encontreiro = getByNome(rs.getString("nome"));
				for (cont = 0; cont <= (gt.getIntegrantes().size()-1); cont++)
				{
					Encontreiro integrante = (Encontreiro) gt.getIntegrantes().get(cont);
					if (integrante.getCodigoEncontreiro() == encontreiro.getCodigoEncontreiro())
						encontreiro.setChecked(true);
					}
				al.add(encontreiro);
			}

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar Encontreiros\n" + ex.getMessage());
		}

		return al;
	}
	
	public ArrayList<Encontreiro> listarEChecar(Integer tarefa) throws Exception {
		conn = super.getConnection();
		ArrayList<Encontreiro> al = new ArrayList<Encontreiro>();
		Encontreiro encontreiro = new Encontreiro();

		try {
			String sql = "SELECT e.codigo as codigo_encontreiro, mb.codigo as codigo_membro, "
					+ "p.codigo as codigo_pessoa, p.nome, p.endereco, p.telefone, p.data_nascimento, "
					+ "p.numero_documento, p.tipo_documento, p.sexo, p.estado_civil, mb.hierarquia_codigo, "
					+ "mb.data_batismo, mb.status_IV, p.ativo "
					+ "FROM grupo_trabalho_encontreiro gte INNER JOIN encontreiro e ON gte.encontreiro_codigo = e.codigo"
					+ " INNER JOIN membro mb ON e.membro_codigo = mb.codigo "
					+ "INNER JOIN pessoa p ON mb.pessoa_codigo = p.codigo";

			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			DAOTarefa daot = new DAOTarefa();
			Tarefa t = new Tarefa();
			t = daot.getByCodigo(tarefa);
			
			
			int cont;
			while (rs.next()) {
				encontreiro = getByNome(rs.getString("nome"));
				for (cont = 0; cont <= (t.getEncontreiros().size()-1); cont++)
				{
					Encontreiro integrante = (Encontreiro) t.getEncontreiros().get(cont);
					if (integrante.getCodigoEncontreiro() == encontreiro.getCodigoEncontreiro())
						encontreiro.setChecked(true);
					}
				al.add(encontreiro);
			}

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar Encontreiros\n" + ex.getMessage());
		}

		return al;
	}


	public ArrayList<Encontreiro> listarSemCoordenador(Integer codigo) throws Exception {
		conn = super.getConnection();
		ArrayList<Encontreiro> al = new ArrayList<Encontreiro>();
		Encontreiro encontreiro = new Encontreiro();

		try {
			String sql = "SELECT e.codigo as codigo_encontreiro, mb.codigo as codigo_membro, "
					+ "p.codigo as codigo_pessoa, p.nome, p.endereco, p.telefone, p.data_nascimento, "
					+ "p.numero_documento, p.tipo_documento, p.sexo, p.estado_civil, mb.hierarquia_codigo, "
					+ "mb.data_batismo, mb.status_IV, p.ativo "
					+ "FROM encontreiro e INNER JOIN membro mb ON e.membro_codigo = mb.codigo "
					+ "INNER JOIN pessoa p ON mb.pessoa_codigo = p.codigo WHERE e.codigo != ?";

			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, codigo);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				encontreiro = getByNome(rs.getString("nome"));
				al.add(encontreiro);
			}

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar líderes\n" + ex.getMessage());
		}

		return al;
	}

	@Override
	public ArrayList<Encontreiro> buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
