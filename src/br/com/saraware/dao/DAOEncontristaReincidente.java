package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.saraware.domain.Encontrista;
import br.com.saraware.domain.EncontristaReincidente;
import br.com.saraware.domain.Hierarquia;
import br.com.saraware.domain.Membro;
import br.com.saraware.domain.Pessoa;
import br.com.saraware.factory.ConexaoFactory;

public class DAOEncontristaReincidente extends ConexaoFactory implements IDAO {

	private Connection conn;

	public EncontristaReincidente getByNome(String nome) throws Exception {
		conn = super.getConnection();

		String sqlEncontristaReincidente = "SELECT er.codigo as codigo_encontrista_reincidente, mb.codigo as codigo_membro,"
				+ "p.codigo as codigo_pessoa, p.nome, p.endereco, p.telefone, p.data_nascimento, "
				+ "p.numero_documento, p.tipo_documento, p.sexo, p.estado_civil, " + " p.ativo, mb.hierarquia_codigo "
				+ "FROM encontrista_reincidente er INNER JOIN membro mb ON er.membro_codigo = mb.codigo INNER JOIN pessoa p ON mb.pessoa_codigo = p.codigo "
				+ "WHERE (p.nome = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psEncontristaReincidente = conn.prepareStatement(sqlEncontristaReincidente);
			psEncontristaReincidente.setString(1, nome);
			ResultSet rsEncontristaReincidente = psEncontristaReincidente.executeQuery();

			if (rsEncontristaReincidente.next()) {
				EncontristaReincidente encontristaReincidente = new EncontristaReincidente();
				Hierarquia hierarquia = new Hierarquia();
				DAOHierarquia daoh = new DAOHierarquia();

				encontristaReincidente.setCodigoEncontristaReincidente(
						rsEncontristaReincidente.getInt("codigo_encontrista_reincidente"));
				encontristaReincidente.setCodigoMembro(rsEncontristaReincidente.getInt("codigo_membro"));
				encontristaReincidente.setCodigoPessoa(rsEncontristaReincidente.getInt("codigo_pessoa"));
				encontristaReincidente.setNome(rsEncontristaReincidente.getString("nome"));
				encontristaReincidente.setEndereco(rsEncontristaReincidente.getString("endereco"));
				encontristaReincidente.setTelefone(rsEncontristaReincidente.getString("telefone"));
				encontristaReincidente.setDataNascimento(rsEncontristaReincidente.getDate("data_nascimento"));
				encontristaReincidente.setNumeroDocumento(rsEncontristaReincidente.getString("numero_documento"));
				encontristaReincidente.setTipoDocumento(rsEncontristaReincidente.getString("tipo_documento"));
				encontristaReincidente.setSexo(rsEncontristaReincidente.getString("sexo"));
				encontristaReincidente.setEstadoCivil(rsEncontristaReincidente.getString("estado_civil"));
				hierarquia = daoh.getByCodigo(rsEncontristaReincidente.getInt("hierarquia_codigo"));
				encontristaReincidente.setHierarquia(hierarquia);
				encontristaReincidente.setAtivo(rsEncontristaReincidente.getBoolean("ativo"));

				rsEncontristaReincidente.close();
				conn.close();

				return encontristaReincidente;
			} else {
				rsEncontristaReincidente.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Encontrista Reincidente por nome!\n" + ex.getMessage());
		}

	}

	@Override
	public void cadastrar(Object param) throws Exception {

		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			String sqlTemPessoa = "SELECT nome FROM pessoa WHERE nome = ?";

			PreparedStatement psTemPessoa = conn.prepareStatement(sqlTemPessoa);
			psTemPessoa.setString(1, ((EncontristaReincidente) param).getNome());
			ResultSet rsTemPessoa = psTemPessoa.executeQuery();
			
			if (rsTemPessoa.next()) {
				PreparedStatement psEncontristaReincidente = conn.prepareStatement(
						"INSERT INTO encontrista_reincidente (membro_codigo) VALUES (?)");
				DAOMembro daom = new DAOMembro();
				Membro membro = new Membro();
				membro = daom.getByNome(((EncontristaReincidente) param).getNome());
				
				psEncontristaReincidente.setInt(1, membro.getCodigoMembro());
				psEncontristaReincidente.executeUpdate();

			} else {
				PreparedStatement psPessoa = conn.prepareStatement(
						"INSERT INTO pessoa (nome, endereco, telefone, data_nascimento, numero_documento, tipo_documento, sexo, estado_civil, ativo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
				PreparedStatement psMembro = conn.prepareStatement(
						"INSERT INTO membro (data_batismo, status_IV, hierarquia_codigo, pessoa_codigo) VALUES (?, ?, ?, (SELECT MAX(codigo) FROM pessoa))");
				PreparedStatement psEncontristaReincidente = conn.prepareStatement(
						"INSERT INTO encontrista_reincidente (membro_codigo) VALUES ((SELECT MAX(codigo) FROM membro))");

				psPessoa.setString(1, ((EncontristaReincidente) param).getNome());
				psPessoa.setString(2, ((EncontristaReincidente) param).getEndereco());
				psPessoa.setString(3, ((EncontristaReincidente) param).getTelefone());
				psPessoa.setDate(4, new Date(((EncontristaReincidente) param).getDataNascimento().getTime()));
				psPessoa.setString(5, ((EncontristaReincidente) param).getNumeroDocumento());
				psPessoa.setString(6, ((EncontristaReincidente) param).getTipoDocumento());
				psPessoa.setString(7, ((EncontristaReincidente) param).getSexo());
				psPessoa.setString(8, ((EncontristaReincidente) param).getEstadoCivil());
				psPessoa.setBoolean(9, true);
				psPessoa.executeUpdate();

				psMembro.setDate(1, new Date(((EncontristaReincidente) param).getDataBatismo().getTime()));
				psMembro.setString(2, ((EncontristaReincidente) param).getStatusIV());
				psMembro.setInt(3, ((EncontristaReincidente) param).getHierarquia().getCodigoHierarquia());
				psMembro.executeUpdate();

				psEncontristaReincidente.executeUpdate();
			}
			conn.commit();
			conn.close();
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Encontrista Reincidente\n" + ex.getMessage());
		}
	}

	@Override
	public void alterar(Object param) throws Exception {

	}

	@Override
	public void deletar(Object param) throws Exception {
		conn = super.getConnection();

		try {
			conn.setAutoCommit(false);
			Membro encontristaReincidente = new Membro();
			DAOMembro daom = new DAOMembro();

			String sqlEncontrista = "SELECT p.nome FROM encontrista e "
					+ "INNER JOIN pessoa p ON e.pessoa_codigo = p.codigo " + "WHERE p.nome = ?";

			PreparedStatement psEncontrista = conn.prepareStatement(sqlEncontrista);
			psEncontrista.setString(1, ((Pessoa) param).getNome());
			ResultSet rsEncontrista = psEncontrista.executeQuery();
			if (rsEncontrista.next()) {
				DAOEncontrista daoe = new DAOEncontrista();
				Encontrista encontrista = new Encontrista();

				PreparedStatement psEncontristaUpdate = conn
						.prepareStatement("UPDATE encontrista SET reincidente = ?" + "  WHERE (codigo = ?)");

				encontrista = daoe.getByNome(((Pessoa) param).getNome());
				psEncontristaUpdate.setBoolean(1, false);
				psEncontristaUpdate.setInt(2, encontrista.getCodigoEncontrista());
				psEncontristaUpdate.executeUpdate();

			} else {
				encontristaReincidente = daom.getByNome(((Pessoa) param).getNome());
				PreparedStatement ps = conn
						.prepareStatement("DELETE FROM encontrista_reincidente WHERE (membro_codigo = ?)");
				ps.setInt(1, encontristaReincidente.getCodigoMembro());
				ps.executeUpdate();

			}

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			throw new Exception("- Erro ao deletar Encontristas Reincidentes\n" + ex.getMessage());
		}

	}

	@Override
	public ArrayList<EncontristaReincidente> listar() throws Exception {
		conn = super.getConnection();
		ArrayList<EncontristaReincidente> al = new ArrayList<EncontristaReincidente>();
		EncontristaReincidente encontristaReincidente = new EncontristaReincidente();

		try {
			String sql = "SELECT er.codigo as codigo_encontrista_reincidente, mb.codigo as codigo_membro, "
					+ "p.codigo as codigo_pessoa, p.nome, p.endereco, p.telefone, p.data_nascimento, "
					+ "p.numero_documento, p.tipo_documento, p.sexo, p.estado_civil, " + "p.ativo "
					+ "FROM encontrista_reincidente er  " + "INNER JOIN membro mb ON er.membro_codigo = mb.codigo "
					+ "INNER JOIN pessoa p ON mb.pessoa_codigo = p.codigo WHERE p.ativo = true";

			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				encontristaReincidente = getByNome(rs.getString("nome"));
				al.add(encontristaReincidente);
			}

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar encontristas reincidentes\n" + ex.getMessage());
		}

		return al;
	}

	public ArrayList<Pessoa> listarCompleto() throws Exception {
		conn = super.getConnection();
		ArrayList<Pessoa> al = new ArrayList<Pessoa>();
		Pessoa encontristaReincidente = new Pessoa();

		try {
			String sql = "SELECT mb.codigo as codigo_membro, p.codigo as codigo_pessoa, p.nome, "
					+ "p.endereco, p.telefone, p.data_nascimento, p.numero_documento, p.tipo_documento, "
					+ "p.sexo, p.estado_civil, p.ativo " + "FROM encontrista_reincidente er "
					+ "INNER JOIN membro mb ON er.membro_codigo = mb.codigo "
					+ "INNER JOIN pessoa p ON mb.pessoa_codigo = p.codigo " + "WHERE p.ativo = true " + "UNION "
					+ "SELECT e.codigo as codigo_encontrista, p.codigo as codigo_pessoa, p.nome, "
					+ "p.endereco, p.telefone, p.data_nascimento, p.numero_documento, "
					+ "p.tipo_documento, p.sexo, p.estado_civil, p.ativo  " + "FROM pessoa p "
					+ "INNER JOIN encontrista e ON e.pessoa_codigo = p.codigo " + "WHERE e.reincidente = true";

			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			DAOPessoa daop = new DAOPessoa();

			while (rs.next()) {
				encontristaReincidente = daop.getByNome(rs.getString("nome"));
				al.add(encontristaReincidente);
			}

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar encontristas reincidentes\n" + ex.getMessage());
		}

		return al;
	}

	@Override
	public ArrayList<EncontristaReincidente> buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
