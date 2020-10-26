package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.saraware.domain.Celula;
import br.com.saraware.domain.Lider;
import br.com.saraware.domain.Membro;
import br.com.saraware.factory.ConexaoFactory;

public class DAOCelula extends ConexaoFactory implements IDAO {

	private Connection conn;

	public Celula getByNome(String nome) throws Exception {
		conn = super.getConnection();
		String sqlCelula = "SELECT codigo, nome, lider_codigo, ativo FROM celula WHERE (nome = ?)";

		String sqlMembros = "SELECT mb.codigo as codigo_membro, p.codigo as codigo_pessoa,"
				+ " p.nome, p.endereco, p.telefone, p.data_nascimento, p.numero_documento, p.tipo_documento, "
				+ "p.sexo, p.estado_civil, mb.celula_codigo, mb.hierarquia_codigo, mb.data_batismo, mb.status_IV, p.ativo"
				+ " FROM celula c, membro mb, celula_membro cm, pessoa p WHERE "
				+ "(cm.celula_codigo = c.codigo AND cm.membro_codigo = mb.codigo AND "
				+ "mb.pessoa_codigo = p.codigo AND c.nome = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psCelula = conn.prepareStatement(sqlCelula);
			psCelula.setString(1, nome);
			ResultSet rsCelula = psCelula.executeQuery();
			PreparedStatement psMembros = conn.prepareStatement(sqlMembros);
			psMembros.setString(1, nome);
			ResultSet rsMembros = psMembros.executeQuery();
			if (rsCelula.next()) {
				Celula celula = new Celula();
				Lider lider = new Lider();
				DAOLider daol = new DAOLider();

				celula.setCodigoCelula(rsCelula.getInt("codigo"));
				celula.setNome(rsCelula.getString("nome"));
				lider = daol.getByCodigo(rsCelula.getInt("lider_codigo"));
				celula.setLider(lider);
				celula.setAtivo(rsCelula.getBoolean("ativo"));
				ArrayList<Membro> al = new ArrayList<Membro>();
				while (rsMembros.next()) {
					Membro membro = new Membro();
					membro.setCodigoMembro(rsMembros.getInt("codigo_membro"));
					membro.setNome(rsMembros.getString("nome"));
					/*
					 * membro.setEndereco(rsMembros.getString("endereco"));
					 * membro.setTelefone(rsMembros.getString("telefone"));
					 * membro.setDataNascimento(rsMembros.getDate(
					 * "data_nascimento"));
					 * membro.setNumeroDocumento(rsMembros.getString(
					 * "numero_documento"));
					 * membro.setTipoDocumento(rsMembros.getString(
					 * "tipo_documento"));
					 * membro.setSexo(rsMembros.getString("sexo"));
					 * membro.setEstadoCivil(rsMembros.getString("nome"));
					 * membro.setAtivo(rsMembros.getBoolean("ativo"));
					 */

					al.add(membro);
				}
				celula.setMembros(al);
				rsMembros.close();
				rsCelula.close();
				conn.close();

				return celula;
			} else {
				rsCelula.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Célula por nome!\n" + ex.getMessage());
		}

	}

	public Celula getByCodigo(Integer codigo) throws Exception {
		conn = super.getConnection();
		String sqlCelula = "SELECT codigo, nome, lider_codigo, ativo FROM celula WHERE (codigo = ?)";

		String sqlMembros = "SELECT mb.codigo as codigo_membro, p.codigo as codigo_pessoa,"
				+ " p.nome, p.endereco, p.telefone, p.data_nascimento, p.numero_documento, p.tipo_documento, "
				+ "p.sexo, p.estado_civil, mb.celula_codigo, mb.hierarquia_codigo, mb.data_batismo, mb.status_IV, p.ativo"
				+ " FROM celula c, membro mb, celula_membro cm, pessoa p WHERE "
				+ "(cm.celula_codigo = c.codigo AND cm.membro_codigo = mb.codigo AND "
				+ "mb.pessoa_codigo = p.codigo AND c.codigo = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psCelula = conn.prepareStatement(sqlCelula);
			psCelula.setInt(1, codigo);
			ResultSet rsCelula = psCelula.executeQuery();

			PreparedStatement psMembros = conn.prepareStatement(sqlMembros);
			psMembros.setInt(1, codigo);
			ResultSet rsMembros = psMembros.executeQuery();

			if (rsCelula.next()) {
				Celula celula = new Celula();
				Lider lider = new Lider();
				DAOLider daol = new DAOLider();

				celula.setCodigoCelula(rsCelula.getInt("codigo"));
				celula.setNome(rsCelula.getString("nome"));
				lider = daol.getByCodigo(rsCelula.getInt("lider_codigo"));
				celula.setLider(lider);
				celula.setAtivo(rsCelula.getBoolean("ativo"));
				ArrayList<Membro> al = new ArrayList<Membro>();
				while (rsMembros.next()) {
					Membro membro = new Membro();
					membro.setCodigoMembro(rsMembros.getInt("codigo_membro"));
					membro.setNome(rsMembros.getString("nome"));
					/*
					 * membro.setEndereco(rsMembros.getString("endereco"));
					 * membro.setTelefone(rsMembros.getString("telefone"));
					 * membro.setDataNascimento(rsMembros.getDate(
					 * "data_nascimento"));
					 * membro.setNumeroDocumento(rsMembros.getString(
					 * "numero_documento"));
					 * membro.setTipoDocumento(rsMembros.getString(
					 * "tipo_documento"));
					 * membro.setSexo(rsMembros.getString("sexo"));
					 * membro.setEstadoCivil(rsMembros.getString("nome"));
					 * membro.setAtivo(rsMembros.getBoolean("ativo"));
					 */
					al.add(membro);
				}
				celula.setMembros(al);
				rsCelula.close();
				rsMembros.close();
				conn.close();

				return celula;
			} else {
				rsCelula.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Célula por código!\n" + ex.getMessage());
		}

	}

	@Override
	public void cadastrar(Object param) throws Exception {
		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn
					.prepareStatement("SELECT codigo, nome, lider_codigo, ativo FROM celula WHERE (nome = ? )");

			ps.setString(1, ((Celula) param).getNome());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				try {
					Celula celula = new Celula();
					celula.setCodigoCelula(rs.getInt("codigo"));

					PreparedStatement psCelula = conn.prepareStatement(
							"UPDATE celula SET nome = ?, lider_codigo = ?, ativo = true  WHERE (codigo = ?)");

					psCelula.setString(1, ((Celula) param).getNome());
					psCelula.setInt(2, ((Celula) param).getLider().getCodigoLider());
					psCelula.setInt(3, celula.getCodigoCelula());
					psCelula.executeUpdate();

					conn.commit();
				} catch (Exception ex) {
					conn.rollback();
					throw new Exception("Erro ao alterar Celula\n" + ex.getMessage());
				}

			} else {
				PreparedStatement psCelula = conn
						.prepareStatement("INSERT INTO celula (nome, lider_codigo, ativo) VALUES (?, ?, ?)");

				psCelula.setString(1, ((Celula) param).getNome());
				psCelula.setInt(2, ((Celula) param).getLider().getCodigoLider());
				psCelula.setBoolean(3, true);
				psCelula.executeUpdate();

				conn.commit();
				conn.close();
			}
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Celula\n" + ex.getMessage());
		}

	}

	@Override
	public void alterar(Object param) throws Exception {
		conn = super.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn
				.prepareStatement("SELECT codigo, nome, ativo FROM celula WHERE (nome = ? AND ativo = false)");
		ps.setString(1, ((Celula) param).getNome());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {

			try {
				Celula celula = new Celula();
				celula.setCodigoCelula(rs.getInt("codigo"));

				PreparedStatement psCelulaDelete = conn.prepareStatement("DELETE FROM celula  WHERE (codigo = ?)");
				psCelulaDelete.setInt(1, celula.getCodigoCelula());
				psCelulaDelete.executeUpdate();

				PreparedStatement psCelula = conn.prepareStatement(
						"UPDATE celula SET nome = ?, lider_codigo = ?, ativo = true  WHERE (codigo = ?)");
				psCelula.setString(1, ((Celula) param).getNome());
				psCelula.setInt(2, ((Celula) param).getLider().getCodigoLider());
				psCelula.setInt(3, ((Celula) param).getCodigoCelula());
				psCelula.executeUpdate();

				conn.commit();
			} catch (Exception ex) {
				conn.rollback();
				throw new Exception("Erro ao alterar Hierarquia\n" + ex.getMessage());
			}

		} else {

			try {
				conn.setAutoCommit(false);
				PreparedStatement psCelula = conn
						.prepareStatement("UPDATE celula SET nome = ?, lider_codigo = ? WHERE (codigo = ?)");

				psCelula.setString(1, ((Celula) param).getNome());
				psCelula.setInt(2, ((Celula) param).getLider().getCodigoLider());
				psCelula.setInt(3, ((Celula) param).getCodigoCelula());
				psCelula.executeUpdate();

				conn.commit();
			} catch (Exception ex) {
				conn.rollback();
				throw new Exception("Erro ao alterar Célula\n" + ex.getMessage());
			}
		}

	}

	@Override
	public void deletar(Object param) throws Exception {
		conn = super.getConnection();

		try {
			conn.setAutoCommit(false);
			Celula celula = new Celula();
			DAOCelula dao = new DAOCelula();
			celula = dao.getByCodigo(((Celula) param).getCodigoCelula());
			PreparedStatement ps = conn.prepareStatement("UPDATE celula SET ativo = false WHERE (codigo = ?)");
			ps.setInt(1, celula.getCodigoCelula());
			ps.executeUpdate();

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			throw new Exception("- Erro ao deletar Célula\n" + ex.getMessage());
		}

	}

	@Override
	public ArrayList<Celula> listar() throws Exception {

		conn = super.getConnection();
		ArrayList<Celula> al = new ArrayList<Celula>();
		Celula celula = new Celula();

		try {
			String sql = "SELECT codigo, nome, lider_codigo, ativo FROM celula WHERE ativo = true";
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				celula = getByNome(rs.getString("nome"));
				al.add(celula);
			}

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar células\n" + ex.getMessage());
		}

		return al;
	}

	@Override
	public ArrayList<Celula> buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void incluirMembroCelula(Celula celula) throws Exception {
		conn = super.getConnection();
		conn.setAutoCommit(false);
		try {
			PreparedStatement psCelulaMembro = conn.prepareStatement("INSERT INTO celula_membro "
					+ "(celula_codigo, membro_codigo) VALUES (?, (SELECT MAX(codigo) FROM membro))");

			psCelulaMembro.setInt(1, celula.getCodigoCelula());
			psCelulaMembro.executeUpdate();

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			throw new Exception("Erro ao inserir membro na célula\n" + ex.getMessage());
		}
	}

	public void alterarMembroCelula(Celula celula, Membro membro) throws Exception {
		conn = super.getConnection();
		conn.setAutoCommit(false);
		try {
			
			System.out.println(celula.getNome() + " " + membro.getNome());
			/*PreparedStatement psCelulaMembro = conn.prepareStatement("UPDATE celula_membro SET "
					+ "celula_codigo = ? WHERE (membro_codigo = ? AND celula_codigo = ?");

			Celula celulaAntiga = 
			psCelulaMembro.setInt(1, celula.getCodigoCelula());
			psCelulaMembro.executeUpdate();*/

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			throw new Exception("Erro ao inserir membro na célula\n" + ex.getMessage());
		}

	}

}
