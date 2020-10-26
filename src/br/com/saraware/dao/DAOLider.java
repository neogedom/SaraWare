package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.saraware.domain.Hierarquia;
import br.com.saraware.domain.Lider;
import br.com.saraware.domain.Membro;
import br.com.saraware.factory.ConexaoFactory;

public class DAOLider extends ConexaoFactory implements IDAO {
	private Connection conn;

	public Lider getByNome(String nome) throws Exception {
		conn = super.getConnection();

		String sqlLider = "SELECT ld.codigo as codigo_lider, mb.codigo as codigo_membro, "
				+ "p.codigo as codigo_pessoa, p.nome, p.endereco, p.telefone, p.data_nascimento, "
				+ "p.numero_documento, p.tipo_documento, p.sexo, p.estado_civil, "
				+ "mb.hierarquia_codigo, mb.data_batismo, mb.status_IV, p.ativo "
				+ "FROM lider ld INNER JOIN membro mb ON ld.membro_codigo = mb.codigo "
				+ "INNER JOIN pessoa p ON mb.pessoa_codigo = p.codigo WHERE (p.nome = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psLider = conn.prepareStatement(sqlLider);
			psLider.setString(1, nome);
			ResultSet rsLider = psLider.executeQuery();

			if (rsLider.next()) {
				Lider lider = new Lider();
				Hierarquia hierarquia = new Hierarquia();
				DAOHierarquia daoh = new DAOHierarquia();

				lider.setCodigoLider(rsLider.getInt("codigo_lider"));
				lider.setCodigoMembro(rsLider.getInt("codigo_membro"));
				lider.setCodigoPessoa(rsLider.getInt("codigo_pessoa"));
				lider.setNome(rsLider.getString("nome"));
				lider.setEndereco(rsLider.getString("endereco"));
				lider.setTelefone(rsLider.getString("telefone"));
				lider.setDataNascimento(rsLider.getDate("data_nascimento"));
				lider.setNumeroDocumento(rsLider.getString("numero_documento"));
				lider.setTipoDocumento(rsLider.getString("tipo_documento"));
				lider.setSexo(rsLider.getString("sexo"));
				lider.setEstadoCivil(rsLider.getString("estado_civil"));
				hierarquia = daoh.getByCodigo(rsLider.getInt("hierarquia_codigo"));
				lider.setHierarquia(hierarquia);
				lider.setAtivo(rsLider.getBoolean("ativo"));

				rsLider.close();
				conn.close();

				return lider;
			} else {
				rsLider.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Lider por nome!\n" + ex.getMessage());
		}

	}

	public Lider getByCodigo(Integer codigo) throws Exception {
		conn = super.getConnection();

		String sqlLider = "SELECT ld.codigo as codigo_lider, mb.codigo as codigo_membro, "
				+ "p.codigo as codigo_pessoa, p.nome, p.endereco, p.telefone, p.data_nascimento, "
				+ "p.numero_documento, p.tipo_documento, p.sexo, p.estado_civil, "
				+ "mb.hierarquia_codigo, mb.data_batismo, mb.status_IV, p.ativo "
				+ "FROM lider ld INNER JOIN membro mb ON ld.membro_codigo = mb.codigo "
				+ "INNER JOIN pessoa p ON mb.pessoa_codigo = p.codigo WHERE (ld.codigo = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psLider = conn.prepareStatement(sqlLider);
			psLider.setInt(1, codigo);
			ResultSet rsLider = psLider.executeQuery();

			if (rsLider.next()) {
				Lider lider = new Lider();
				Hierarquia hierarquia = new Hierarquia();
				DAOHierarquia daoh = new DAOHierarquia();


				lider.setCodigoLider(rsLider.getInt("codigo_lider"));
				lider.setCodigoMembro(rsLider.getInt("codigo_membro"));
				lider.setCodigoPessoa(rsLider.getInt("codigo_pessoa"));
				lider.setNome(rsLider.getString("nome"));
				lider.setEndereco(rsLider.getString("endereco"));
				lider.setTelefone(rsLider.getString("telefone"));
				lider.setDataNascimento(rsLider.getDate("data_nascimento"));
				lider.setNumeroDocumento(rsLider.getString("numero_documento"));
				lider.setTipoDocumento(rsLider.getString("tipo_documento"));
				lider.setSexo(rsLider.getString("sexo"));
				lider.setEstadoCivil(rsLider.getString("estado_civil"));
				hierarquia = daoh.getByCodigo(rsLider.getInt("hierarquia_codigo"));
				lider.setHierarquia(hierarquia);
				lider.setAtivo(rsLider.getBoolean("ativo"));

				rsLider.close();
				conn.close();

				return lider;
			} else {
				rsLider.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Lider por código!\n" + ex.getMessage());
		}

	}

	@Override
	public void cadastrar(Object param) throws Exception {
		// TODO Auto-generated method stub
		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("SELECT ld.codigo as codigo_lider, mb.codigo as codigo_membro, p.codigo as codigo_pessoa,"
					+ " p.nome, p.endereco, p.telefone, p.data_nascimento, p.numero_documento, p.tipo_documento, "
					+ "p.sexo, p.estado_civil, mb.celula_codigo, mb.hierarquia_codigo, mb.data_batismo, mb.status_IV, p.ativo "
					+ "FROM membro mb, pessoa p WHERE (p.nome = ? and p.ativo = true)");
			
			ps.setString(1, ((Lider) param).getNome());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Lider membro = new Lider();
				DAOLider dao = new DAOLider();
				membro = dao.getByNome(((Lider) param).getNome());

				try {
					PreparedStatement psPessoa = conn.prepareStatement(
							"UPDATE pessoa SET endereco = ?, telefone = ?, data_nascimento = ?, numero_documento = ?, tipo_documento = ?, sexo = ?, estado_civil = ? WHERE (codigo = ?)");
					PreparedStatement psMembro = conn.prepareStatement(
							"UPDATE membro SET data_batismo = ?, status_IV = ?, hierarquia_codigo = ?  WHERE (codigo = ?)");

					psPessoa.setString(1, ((Lider) param).getEndereco());
					psPessoa.setString(2, ((Lider) param).getTelefone());
					psPessoa.setDate(3, new Date(((Lider) param).getDataNascimento().getTime()));
					psPessoa.setString(4, ((Lider) param).getNumeroDocumento());
					psPessoa.setString(5, ((Lider) param).getTipoDocumento());
					psPessoa.setString(6, ((Lider) param).getSexo());
					psPessoa.setString(7, ((Lider) param).getEstadoCivil());
					psPessoa.setInt(8, membro.getCodigoPessoa());
					psPessoa.executeUpdate();

					DAOHierarquia daoh = new DAOHierarquia();
					psMembro.setDate(1, new Date(((Lider) param).getDataBatismo().getTime()));
					psMembro.setString(2, ((Lider) param).getStatusIV());
					psMembro.setInt(3, daoh.getByNome(((Lider) param).getHierarquia().getDescricao()).getCodigoHierarquia());
					psMembro.setInt(4, membro.getCodigoMembro());
					psMembro.executeUpdate();

					conn.commit();
				} catch (Exception ex) {
					conn.rollback();
					throw new Exception("Erro ao alterar Líder\n" + ex.getMessage());
				}

			} else {
				PreparedStatement psPessoa = conn.prepareStatement(
						"INSERT INTO pessoa (nome, endereco, telefone, data_nascimento, numero_documento, tipo_documento, sexo, estado_civil, ativo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
				PreparedStatement psMembro = conn.prepareStatement(
						"INSERT INTO membro (data_batismo, status_IV, hierarquia_codigo, pessoa_codigo) VALUES (?, ?, ?, (SELECT MAX(codigo) FROM pessoa))");
				PreparedStatement psLider = conn.prepareStatement(
						"INSERT INTO lider (codigo_membro) VALUES ((SELECT MAX(codigo) FROM membro))");

				psPessoa.setString(1, ((Membro) param).getNome());
				psPessoa.setString(2, ((Membro) param).getEndereco());
				psPessoa.setString(3, ((Membro) param).getTelefone());
				psPessoa.setDate(4, new Date(((Membro) param).getDataNascimento().getTime()));
				psPessoa.setString(5, ((Membro) param).getNumeroDocumento());
				psPessoa.setString(6, ((Membro) param).getTipoDocumento());
				psPessoa.setString(7, ((Membro) param).getSexo());
				psPessoa.setString(8, ((Membro) param).getEstadoCivil());
				psPessoa.setBoolean(9, true);
				psPessoa.executeUpdate();

				DAOHierarquia daoh = new DAOHierarquia();
				
				psMembro.setDate(1, new Date(((Membro) param).getDataBatismo().getTime()));
				psMembro.setString(2, ((Membro) param).getStatusIV());
				psMembro.setInt(3, daoh.getByNome(((Membro) param).getHierarquia().getDescricao()).getCodigoHierarquia());
				psMembro.setInt(4, ((Membro) param).getCodigoMembro());
				psMembro.executeUpdate();
				psLider.executeUpdate();

				conn.commit();
				conn.close();
			}
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Líder\n" + ex.getMessage());
		}

	}

	@Override
	public void alterar(Object param) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletar(Object param) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Lider> listar() throws Exception {
		// TODO Auto-generated method stub
		conn = super.getConnection();
		ArrayList<Lider> al = new ArrayList<Lider>();
		Lider lider = new Lider();

		try {
			String sql = "SELECT ld.codigo as codigo_lider, mb.codigo as codigo_membro, "
					+ "p.codigo as codigo_pessoa, p.nome, p.endereco, p.telefone, p.data_nascimento, "
					+ "p.numero_documento, p.tipo_documento, p.sexo, p.estado_civil, mb.hierarquia_codigo, "
					+ "mb.data_batismo, mb.status_IV, p.ativo "
					+ "FROM lider ld INNER JOIN membro mb ON ld.membro_codigo = mb.codigo "
					+ "INNER JOIN pessoa p ON mb.pessoa_codigo = p.codigo";
			
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				lider = getByNome(rs.getString("nome"));
				al.add(lider);
			}

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar líderes\n" + ex.getMessage());
		}

		return al;

	}

	@Override
	public ArrayList<Lider> buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
