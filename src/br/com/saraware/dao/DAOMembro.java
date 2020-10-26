package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.saraware.domain.Hierarquia;
import br.com.saraware.domain.Membro;
import br.com.saraware.factory.ConexaoFactory;

public class DAOMembro extends ConexaoFactory implements IDAO {

	private Connection conn;

	public DAOMembro() {
		super();
	}

	public Membro getByNome(String nome) throws Exception {
		conn = super.getConnection();

		String sqlMembro = "SELECT mb.codigo as codigo_membro, p.codigo as codigo_pessoa, p.nome, p.endereco, "
				+ "p.telefone, p.data_nascimento, p.numero_documento, p.tipo_documento, p.sexo, p.estado_civil,"
				+ "  mb.hierarquia_codigo, mb.data_batismo, mb.status_IV, p.ativo FROM "
				+ "membro mb INNER JOIN pessoa p ON mb.pessoa_codigo = p.codigo WHERE (p.nome = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psMembro = conn.prepareStatement(sqlMembro);
			psMembro.setString(1, nome);
			ResultSet rsMembro = psMembro.executeQuery();

			if (rsMembro.next()) {
				Membro membro = new Membro();
				Hierarquia hierarquia = new Hierarquia();
				DAOHierarquia daoh = new DAOHierarquia();

				membro.setCodigoMembro(rsMembro.getInt("codigo_membro"));
				membro.setCodigoPessoa(rsMembro.getInt("codigo_pessoa"));
				membro.setNome(rsMembro.getString("nome"));
				membro.setEndereco(rsMembro.getString("endereco"));
				membro.setTelefone(rsMembro.getString("telefone"));
				membro.setDataNascimento(rsMembro.getDate("data_nascimento"));
				membro.setNumeroDocumento(rsMembro.getString("numero_documento"));
				membro.setTipoDocumento(rsMembro.getString("tipo_documento"));
				membro.setSexo(rsMembro.getString("sexo"));
				membro.setEstadoCivil(rsMembro.getString("estado_civil"));
				membro.setStatusIV(rsMembro.getString("status_IV"));
				membro.setDataBatismo(rsMembro.getDate("data_batismo"));
				hierarquia = daoh.getByCodigo(rsMembro.getInt("hierarquia_codigo"));
				membro.setHierarquia(hierarquia);
				membro.setAtivo(rsMembro.getBoolean("ativo"));

				rsMembro.close();
				psMembro.close();
				conn.close();

				return membro;
			} else {
				rsMembro.close();
				psMembro.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Membro por nome!\n" + ex.getMessage());
		}

	}

	public Membro getByCodigoPessoa(Integer codigo) throws Exception {
		conn = super.getConnection();

		String sqlMembro = "SELECT mb.codigo as codigo_membro, p.codigo as codigo_pessoa, p.nome, p.endereco, "
				+ "p.telefone, p.data_nascimento, p.numero_documento, p.tipo_documento, p.sexo, p.estado_civil,"
				+ " mb.hierarquia_codigo, mb.data_batismo, mb.status_IV, p.ativo FROM "
				+ "membro mb INNER JOIN pessoa p ON mb.pessoa_codigo = p.codigo WHERE (p.codigo = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psMembro = conn.prepareStatement(sqlMembro);
			psMembro.setInt(1, codigo);
			ResultSet rsMembro = psMembro.executeQuery();

			if (rsMembro.next()) {
				Membro membro = new Membro();
				Hierarquia hierarquia = new Hierarquia();
				DAOHierarquia daoh = new DAOHierarquia();
				
				membro.setCodigoMembro(rsMembro.getInt("codigo_membro"));
				membro.setCodigoPessoa(rsMembro.getInt("codigo_pessoa"));
				membro.setNome(rsMembro.getString("nome"));
				membro.setEndereco(rsMembro.getString("endereco"));
				membro.setTelefone(rsMembro.getString("telefone"));
				membro.setDataNascimento(rsMembro.getDate("data_nascimento"));
				membro.setNumeroDocumento(rsMembro.getString("numero_documento"));
				membro.setTipoDocumento(rsMembro.getString("tipo_documento"));
				membro.setSexo(rsMembro.getString("sexo"));
				membro.setEstadoCivil(rsMembro.getString("estado_civil"));
				membro.setDataBatismo(rsMembro.getDate("data_batismo"));
				membro.setStatusIV(rsMembro.getString("status_IV"));
				hierarquia = daoh.getByCodigo(rsMembro.getInt("hierarquia_codigo"));
				membro.setHierarquia(hierarquia);
				membro.setAtivo(rsMembro.getBoolean("ativo"));

				rsMembro.close();
				psMembro.close();
				conn.close();

				return membro;
			} else {
				rsMembro.close();
				psMembro.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Membro por nome!\n" + ex.getMessage());
		}

	}
	
	public Membro getByCodigo(Integer codigo) throws Exception {
		conn = super.getConnection();

		String sqlMembro = "SELECT mb.codigo as codigo_membro, p.codigo as codigo_pessoa, p.nome, p.endereco, "
				+ "p.telefone, p.data_nascimento, p.numero_documento, p.tipo_documento, p.sexo, p.estado_civil,"
				+ " mb.hierarquia_codigo, mb.data_batismo, mb.status_IV, p.ativo FROM "
				+ "membro mb INNER JOIN pessoa p ON mb.pessoa_codigo = p.codigo WHERE (mb.codigo = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psMembro = conn.prepareStatement(sqlMembro);
			psMembro.setInt(1, codigo);
			ResultSet rsMembro = psMembro.executeQuery();

			if (rsMembro.next()) {
				Membro membro = new Membro();
				Hierarquia hierarquia = new Hierarquia();
				DAOHierarquia daoh = new DAOHierarquia();
				
				membro.setCodigoMembro(rsMembro.getInt("codigo_membro"));
				membro.setCodigoPessoa(rsMembro.getInt("codigo_pessoa"));
				membro.setNome(rsMembro.getString("nome"));
				membro.setEndereco(rsMembro.getString("endereco"));
				membro.setTelefone(rsMembro.getString("telefone"));
				membro.setDataNascimento(rsMembro.getDate("data_nascimento"));
				membro.setNumeroDocumento(rsMembro.getString("numero_documento"));
				membro.setTipoDocumento(rsMembro.getString("tipo_documento"));
				membro.setSexo(rsMembro.getString("sexo"));
				membro.setEstadoCivil(rsMembro.getString("estado_civil"));
				hierarquia = daoh.getByCodigo(rsMembro.getInt("hierarquia_codigo"));
				membro.setHierarquia(hierarquia);
				membro.setAtivo(rsMembro.getBoolean("ativo"));

				rsMembro.close();
				psMembro.close();
				conn.close();

				return membro;
			} else {
				rsMembro.close();
				psMembro.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Membro por nome!\n" + ex.getMessage());
		}

	}

	@Override
	public void cadastrar(Object param) throws Exception {
		// TODO Auto-generated method stub
		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("SELECT mb.codigo as codigo_membro, p.codigo as codigo_pessoa,"
					+ " p.nome, p.endereco, p.telefone, p.data_nascimento, p.numero_documento, p.tipo_documento, "
					+ "p.sexo, p.estado_civil, mb.celula_codigo, mb.hierarquia_codigo, mb.data_batismo, mb.status_IV, p.ativo "
					+ "FROM membro mb, pessoa p WHERE (p.nome = ? and p.ativo = true and mb.pessoa_codigo = p.codigo)");
			
			ps.setString(1, ((Membro) param).getNome());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Membro membro = new Membro();
				DAOMembro dao = new DAOMembro();
				membro = dao.getByNome(((Membro) param).getNome());

				try {
					PreparedStatement psPessoa = conn.prepareStatement(
							"UPDATE pessoa SET endereco = ?, telefone = ?, data_nascimento = ?, numero_documento = ?, tipo_documento = ?, sexo = ?, estado_civil = ? WHERE (codigo = ?)");
					PreparedStatement psMembro = conn.prepareStatement(
							"UPDATE membro SET data_batismo = ?, status_IV = ?, hierarquia_codigo = ? WHERE (codigo = ?)");

					psPessoa.setString(1, ((Membro) param).getEndereco());
					psPessoa.setString(2, ((Membro) param).getTelefone());
					psPessoa.setDate(3, new Date(((Membro) param).getDataNascimento().getTime()));
					psPessoa.setString(4, ((Membro) param).getNumeroDocumento());
					psPessoa.setString(5, ((Membro) param).getTipoDocumento());
					psPessoa.setString(6, ((Membro) param).getSexo());
					psPessoa.setString(7, ((Membro) param).getEstadoCivil());
					psPessoa.setInt(8, membro.getCodigoPessoa());
					psPessoa.executeUpdate();

					DAOHierarquia daoh = new DAOHierarquia();
					psMembro.setDate(1, new Date(((Membro) param).getDataBatismo().getTime()));
					psMembro.setString(2, ((Membro) param).getStatusIV());
					psMembro.setInt(3, daoh.getByNome(((Membro) param).getHierarquia().getDescricao()).getCodigoHierarquia());
					psMembro.setInt(4, membro.getCodigoMembro());
					psMembro.executeUpdate();

					conn.commit();
				} catch (Exception ex) {
					conn.rollback();
					throw new Exception("Erro ao alterar Membro\n" + ex.getMessage());
				}

			} else {
				PreparedStatement psPessoa = conn.prepareStatement(
						"INSERT INTO pessoa (nome, endereco, telefone, data_nascimento, numero_documento, tipo_documento, sexo, estado_civil, ativo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
				PreparedStatement psMembro = conn.prepareStatement(
						"INSERT INTO membro (data_batismo, status_IV, hierarquia_codigo, pessoa_codigo) VALUES (?, ?, ?, (SELECT MAX(codigo) FROM pessoa))");

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

				psMembro.setDate(1, new Date(((Membro) param).getDataBatismo().getTime()));
				psMembro.setString(2, ((Membro) param).getStatusIV());
				psMembro.setInt(3, ((Membro) param).getHierarquia().getCodigoHierarquia());
				psMembro.executeUpdate();

				conn.commit();
				conn.close();
			}
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Membro\n" + ex.getMessage());
		}

	}

	@Override
	public void alterar(Object param) throws Exception {
		conn = super.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement("SELECT mb.codigo as codigo_membro, p.codigo as codigo_pessoa, nome FROM membro mb INNER JOIN pessoa p ON mb.pessoa_codigo = p.codigo WHERE (nome = ? AND ativo = false)");
		ps.setString(1, ((Membro) param).getNome());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {

			try {
				Membro membro = new Membro();
				membro.setCodigoMembro(rs.getInt("codigo_membro"));
				membro.setCodigoPessoa(rs.getInt("codigo_pessoa"));

				PreparedStatement psCelulaMembroDelete = conn.prepareStatement("DELETE FROM celula_membro WHERE (membro_codigo = ?)");
				psCelulaMembroDelete.setInt(1, membro.getCodigoMembro());
				psCelulaMembroDelete.executeUpdate();
				
				PreparedStatement psMembroDelete = conn.prepareStatement("DELETE FROM membro WHERE (codigo = ?)");
				psMembroDelete.setInt(1, membro.getCodigoMembro());
				psMembroDelete.executeUpdate();
				
				PreparedStatement psPessoaDelete = conn.prepareStatement("DELETE FROM pessoa WHERE (codigo = ?)");
				psPessoaDelete.setInt(1, membro.getCodigoPessoa());
				psPessoaDelete.executeUpdate();

				PreparedStatement psPessoa = conn.prepareStatement("UPDATE pessoa SET nome = ?, endereco = ?,"
						+ " telefone = ?, data_nascimento = ?, numero_documento = ?, tipo_documento = ?, sexo = ?,"
						+ " estado_civil = ?, ativo = true  WHERE (codigo = ?)");
				psPessoa.setString(1, ((Membro) param).getNome());
				psPessoa.setString(2, ((Membro) param).getEndereco());
				psPessoa.setString(3, ((Membro) param).getTelefone());
				psPessoa.setDate(4, new Date(((Membro) param).getDataNascimento().getTime()));
				psPessoa.setString(5, ((Membro) param).getNumeroDocumento());
				psPessoa.setString(6, ((Membro) param).getTipoDocumento());
				psPessoa.setString(7, ((Membro) param).getSexo());
				psPessoa.setString(8, ((Membro) param).getEstadoCivil());
				psPessoa.setInt(9, ((Membro) param).getCodigoPessoa());
				psPessoa.executeUpdate();
				
				PreparedStatement psMembro = conn.prepareStatement("UPDATE membro SET codigo_pessoa = (SELECT MAX(codigo) FROM pessoa), "
						+ "status_IV = ?, hierarquia_codigo = ? WHERE (codigo = ?)");
				psMembro.setString(1, ((Membro) param).getStatusIV());
				psMembro.setInt(2, ((Membro) param).getHierarquia().getCodigoHierarquia());
				psMembro.setInt(3, ((Membro) param).getCodigoMembro());
				psMembro.executeUpdate();

				conn.commit();
			} catch (Exception ex) {
				conn.rollback();
				throw new Exception("Erro ao alterar Membro\n" + ex.getMessage());
			}

		} else {

			try {
				
				PreparedStatement psPessoa = conn.prepareStatement("UPDATE pessoa SET nome = ?, endereco = ?,"
						+ " telefone = ?, data_nascimento = ?, numero_documento = ?, tipo_documento = ?, sexo = ?,"
						+ " estado_civil = ?, ativo = true  WHERE (codigo = ?)");
				psPessoa.setString(1, ((Membro) param).getNome());
				psPessoa.setString(2, ((Membro) param).getEndereco());
				psPessoa.setString(3, ((Membro) param).getTelefone());
				psPessoa.setDate(4, new Date(((Membro) param).getDataNascimento().getTime()));
				psPessoa.setString(5, ((Membro) param).getNumeroDocumento());
				psPessoa.setString(6, ((Membro) param).getTipoDocumento());
				psPessoa.setString(7, ((Membro) param).getSexo());
				psPessoa.setString(8, ((Membro) param).getEstadoCivil());
				psPessoa.setInt(9, ((Membro) param).getCodigoPessoa());
				psPessoa.executeUpdate();
				
				PreparedStatement psMembro = conn.prepareStatement("UPDATE membro SET pessoa_codigo = (SELECT MAX(codigo) FROM pessoa), "
						+ "status_IV = ?, hierarquia_codigo = ? WHERE (codigo = ?)");
				psMembro.setString(1, ((Membro) param).getStatusIV());
				psMembro.setInt(2, ((Membro) param).getHierarquia().getCodigoHierarquia());
				psMembro.setInt(3, ((Membro) param).getCodigoMembro());
				psMembro.executeUpdate();

				conn.commit();
			} catch (Exception ex) {
				conn.rollback();
				throw new Exception("Erro ao alterar Membro\n" + ex.getMessage());
			}
		}

		
	}

	@Override
	public void deletar(Object param) throws Exception {
		conn = super.getConnection();

		try {
			conn.setAutoCommit(false);
			Membro membro = new Membro();
			DAOMembro dao = new DAOMembro();
			membro = dao.getByCodigo(((Membro) param).getCodigoMembro());
			PreparedStatement ps = conn.prepareStatement("UPDATE membro mb, pessoa p SET ativo = false WHERE (mb.codigo = ? AND mb.pessoa_codigo = p.codigo)");
			ps.setInt(1, membro.getCodigoMembro());
			ps.executeUpdate();

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			throw new Exception("- Erro ao deletar Membro\n" + ex.getMessage());
		}


	}

	@Override
	public ArrayList<Membro> listar() throws Exception {
		conn = super.getConnection();
		ArrayList<Membro> al = new ArrayList<Membro>();
		Membro membro = new Membro();

		try {
			String sql = "SELECT mb.codigo as codigo_membro, p.codigo as codigo_pessoa,"
					+ " p.nome, p.endereco, p.telefone, p.data_nascimento, p.numero_documento, p.tipo_documento, "
					+ "p.sexo, p.estado_civil, mb.celula_codigo, mb.hierarquia_codigo, mb.data_batismo, mb.status_IV, p.ativo "
					+ "FROM membro mb INNER JOIN pessoa p ON mb.pessoa_codigo = p.codigo"
					+ " WHERE ativo = true";
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				membro = getByNome(rs.getString("nome"));
				al.add(membro);
			}

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar membros\n" + ex.getMessage());
		}

		return al;

	}

	@Override
	public ArrayList<Membro> buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ArrayList<Membro> buscarPorCelula(Integer valor) throws Exception {
		conn = super.getConnection();
		ArrayList<Membro> al = new ArrayList<Membro>();
		Membro membro = new Membro();
		try {
			String sql = "SELECT * FROM celula_membro cm, membro mb INNER JOIN pessoa p ON mb.pessoa_codigo = p.codigo WHERE (ativo = true AND cm.membro_codigo = mb.codigo AND mb.celula_codigo = ?)";
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, (valor));
			ResultSet rs = ps.executeQuery();
		
			while (rs.next())
			{
				membro = getByNome(rs.getString("nome"));
				al.add(membro);
			}
		
			}
	
			catch (Exception ex)
			{
				throw new Exception ("Erro ao buscar membros por célula\n" + ex.getMessage());
			}
		
			return al;
	}
}
