package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.saraware.domain.Encontrista;
import br.com.saraware.factory.ConexaoFactory;

public class DAOEncontrista extends ConexaoFactory implements IDAO {

	private Connection conn;

	public Encontrista getByNome(String nome) throws Exception {
		conn = super.getConnection();

		String sqlEncontrista = "SELECT e.codigo as codigo_encontrista, "
				+ "p.codigo as codigo_pessoa, p.nome, p.endereco, p.telefone, p.data_nascimento, "
				+ "p.numero_documento, p.tipo_documento, p.sexo, p.estado_civil, "
				+ " p.ativo, e.reincidente "
				+ "FROM encontrista e INNER JOIN pessoa p ON e.pessoa_codigo = p.codigo "
				+ "WHERE (p.nome = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psEncontrista = conn.prepareStatement(sqlEncontrista);
			psEncontrista.setString(1, nome);
			ResultSet rsEncontrista = psEncontrista.executeQuery();

			if (rsEncontrista.next()) {
				Encontrista encontrista = new Encontrista();

				encontrista.setCodigoEncontrista(rsEncontrista.getInt("codigo_encontrista"));
				encontrista.setCodigoPessoa(rsEncontrista.getInt("codigo_pessoa"));
				encontrista.setNome(rsEncontrista.getString("nome"));
				encontrista.setEndereco(rsEncontrista.getString("endereco"));
				encontrista.setTelefone(rsEncontrista.getString("telefone"));
				encontrista.setDataNascimento(rsEncontrista.getDate("data_nascimento"));
				encontrista.setNumeroDocumento(rsEncontrista.getString("numero_documento"));
				encontrista.setTipoDocumento(rsEncontrista.getString("tipo_documento"));
				encontrista.setSexo(rsEncontrista.getString("sexo"));
				encontrista.setEstadoCivil(rsEncontrista.getString("estado_civil"));
				encontrista.setReincidente(rsEncontrista.getBoolean("reincidente"));
				encontrista.setAtivo(rsEncontrista.getBoolean("ativo"));

				rsEncontrista.close();
				conn.close();

				return encontrista;
			} else {
				rsEncontrista.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Encontrista por nome!\n" + ex.getMessage());
		}

	}

	@Override
	public void cadastrar(Object param) throws Exception {

		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement psPessoa = conn.prepareStatement(
					"INSERT INTO pessoa (nome, endereco, telefone, data_nascimento, numero_documento, tipo_documento, sexo, estado_civil, ativo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			PreparedStatement psEncontrista = conn
					.prepareStatement("INSERT INTO encontrista (pessoa_codigo, reincidente) VALUES ((SELECT MAX(codigo) FROM pessoa), ?)");

			psPessoa.setString(1, ((Encontrista) param).getNome());
			psPessoa.setString(2, ((Encontrista) param).getEndereco());
			psPessoa.setString(3, ((Encontrista) param).getTelefone());
			psPessoa.setDate(4, new Date(((Encontrista) param).getDataNascimento().getTime()));
			psPessoa.setString(5, ((Encontrista) param).getNumeroDocumento());
			psPessoa.setString(6, ((Encontrista) param).getTipoDocumento());
			psPessoa.setString(7, ((Encontrista) param).getSexo());
			psPessoa.setString(8, ((Encontrista) param).getEstadoCivil());
			psPessoa.setBoolean(9, true);
			psPessoa.executeUpdate();
			
			psEncontrista.setBoolean(1, ((Encontrista) param).getReincidente());
			psEncontrista.executeUpdate();

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Encontrista\n" + ex.getMessage());
		}
	}

	@Override
	public void alterar(Object param) throws Exception {
		conn = super.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement("SELECT e.codigo as codigo_encontrista, p.codigo as codigo_pessoa, nome FROM encontrista e INNER JOIN pessoa p ON e.pessoa_codigo = p.codigo WHERE (nome = ? AND ativo = false)");
		ps.setString(1, ((Encontrista) param).getNome());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {

			try {
				Encontrista encontrista = new Encontrista();
				encontrista.setCodigoEncontrista(rs.getInt("codigo_encontrista"));
				encontrista.setCodigoPessoa(rs.getInt("codigo_pessoa"));

				PreparedStatement psCelulaEncontristaDelete = conn.prepareStatement("DELETE FROM celula_encontrista WHERE (encontrista_codigo = ?)");
				psCelulaEncontristaDelete.setInt(1, encontrista.getCodigoEncontrista());
				psCelulaEncontristaDelete.executeUpdate();
				
				PreparedStatement psEncontristaDelete = conn.prepareStatement("DELETE FROM encontrista WHERE (codigo = ?)");
				psEncontristaDelete.setInt(1, encontrista.getCodigoEncontrista());
				psEncontristaDelete.executeUpdate();
				
				PreparedStatement psPessoaDelete = conn.prepareStatement("DELETE FROM pessoa WHERE (codigo = ?)");
				psPessoaDelete.setInt(1, encontrista.getCodigoPessoa());
				psPessoaDelete.executeUpdate();

				PreparedStatement psPessoa = conn.prepareStatement("UPDATE pessoa SET nome = ?, endereco = ?,"
						+ " telefone = ?, data_nascimento = ?, numero_documento = ?, tipo_documento = ?, sexo = ?,"
						+ " estado_civil = ?, ativo = true  WHERE (codigo = ?)");
				psPessoa.setString(1, ((Encontrista) param).getNome());
				psPessoa.setString(2, ((Encontrista) param).getEndereco());
				psPessoa.setString(3, ((Encontrista) param).getTelefone());
				psPessoa.setDate(4, new Date(((Encontrista) param).getDataNascimento().getTime()));
				psPessoa.setString(5, ((Encontrista) param).getNumeroDocumento());
				psPessoa.setString(6, ((Encontrista) param).getTipoDocumento());
				psPessoa.setString(7, ((Encontrista) param).getSexo());
				psPessoa.setString(8, ((Encontrista) param).getEstadoCivil());
				psPessoa.setInt(9, ((Encontrista) param).getCodigoPessoa());
				psPessoa.executeUpdate();
				
				PreparedStatement psEncontrista = conn.prepareStatement("UPDATE encontrista SET codigo_pessoa = (SELECT MAX(codigo) FROM pessoa), "
						+ " WHERE (codigo = ?)");
				
				psEncontrista.setInt(1, ((Encontrista) param).getCodigoEncontrista());
				psEncontrista.executeUpdate();

				conn.commit();
			} catch (Exception ex) {
				conn.rollback();
				throw new Exception("Erro ao alterar Encontrista\n" + ex.getMessage());
			}

		} else {

			try {
				
				PreparedStatement psPessoa = conn.prepareStatement("UPDATE pessoa SET nome = ?, endereco = ?,"
						+ " telefone = ?, data_nascimento = ?, numero_documento = ?, tipo_documento = ?, sexo = ?,"
						+ " estado_civil = ?, ativo = true  WHERE (codigo = ?)");
				psPessoa.setString(1, ((Encontrista) param).getNome());
				psPessoa.setString(2, ((Encontrista) param).getEndereco());
				psPessoa.setString(3, ((Encontrista) param).getTelefone());
				psPessoa.setDate(4, new Date(((Encontrista) param).getDataNascimento().getTime()));
				psPessoa.setString(5, ((Encontrista) param).getNumeroDocumento());
				psPessoa.setString(6, ((Encontrista) param).getTipoDocumento());
				psPessoa.setString(7, ((Encontrista) param).getSexo());
				psPessoa.setString(8, ((Encontrista) param).getEstadoCivil());
				psPessoa.setInt(9, ((Encontrista) param).getCodigoPessoa());
				psPessoa.executeUpdate();
				
				PreparedStatement psEncontrista = conn.prepareStatement("UPDATE encontrista SET pessoa_codigo = (SELECT MAX(codigo) FROM pessoa) "
						+ " WHERE (codigo = ?)");
			
				psEncontrista.setInt(1, ((Encontrista) param).getCodigoEncontrista());
				psEncontrista.executeUpdate();

				conn.commit();
			} catch (Exception ex) {
				conn.rollback();
				throw new Exception("Erro ao alterar Encontrista\n" + ex.getMessage());
			}
		}

		

	}

	@Override
	public void deletar(Object param) throws Exception {
		conn = super.getConnection();

		try {
			conn.setAutoCommit(false);
			Encontrista encontrista = new Encontrista();
			DAOEncontrista dao = new DAOEncontrista();
			encontrista = dao.getByNome(((Encontrista) param).getNome());
			PreparedStatement ps = conn.prepareStatement("DELETE FROM encontrista WHERE (codigo = ?)");
			ps.setInt(1, encontrista.getCodigoEncontrista());
			ps.executeUpdate();
			PreparedStatement psPessoa = conn.prepareStatement("DELETE FROM pessoa WHERE (codigo = ?)");
			psPessoa.setInt(1, encontrista.getCodigoPessoa());
			psPessoa.executeUpdate();
			
			conn.commit();
			conn.close();
		} catch (Exception ex) {
			throw new Exception("- Erro ao deletar Encontrista\n" + ex.getMessage());
		}

	}

	@Override
	public ArrayList<Encontrista> listar() throws Exception {
		conn = super.getConnection();
		ArrayList<Encontrista> al = new ArrayList<Encontrista>();
		Encontrista encontrista = new Encontrista();

		try {
			String sql = "SELECT e.codigo as codigo_encontrista, "
					+ "p.codigo as codigo_pessoa, p.nome, p.endereco, p.telefone, p.data_nascimento, "
					+ "p.numero_documento, p.tipo_documento, p.sexo, p.estado_civil, "
					+ "p.ativo "
					+ "FROM encontrista e  "
					+ "INNER JOIN pessoa p ON e.pessoa_codigo = p.codigo WHERE p.ativo = true AND e.reincidente = false";

			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				encontrista = getByNome(rs.getString("nome"));
				al.add(encontrista);
			}

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar encontristas\n" + ex.getMessage());
		}

		return al;
	}

	@Override
	public ArrayList<Encontrista> buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
