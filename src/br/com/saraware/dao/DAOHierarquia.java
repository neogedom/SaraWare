package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.saraware.domain.Hierarquia;
import br.com.saraware.factory.ConexaoFactory;

public class DAOHierarquia extends ConexaoFactory implements IDAO {

	private Connection conn;

	public Hierarquia getByNome(String nome) throws Exception {
		conn = super.getConnection();

		String sqlHierarquia = "SELECT codigo, descricao, ativo FROM hierarquia WHERE (descricao = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psHierarquia = conn.prepareStatement(sqlHierarquia);
			psHierarquia.setString(1, nome);
			ResultSet rsHierarquia = psHierarquia.executeQuery();

			if (rsHierarquia.next()) {
				Hierarquia hierarquia = new Hierarquia();

				hierarquia.setCodigoHierarquia(rsHierarquia.getInt("codigo"));
				hierarquia.setDescricao(rsHierarquia.getString("descricao"));
				hierarquia.setAtivo(rsHierarquia.getBoolean("ativo"));

				rsHierarquia.close();
				conn.close();

				return hierarquia;
			} else {
				rsHierarquia.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Hierarquia por nome!\n" + ex.getMessage());
		}

	}

	public Hierarquia getByCodigo(Integer codigo) throws Exception {
		conn = super.getConnection();

		String sqlHierarquia = "SELECT codigo, descricao, ativo FROM hierarquia WHERE (codigo = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psHierarquia = conn.prepareStatement(sqlHierarquia);
			psHierarquia.setInt(1, codigo);
			ResultSet rsHierarquia = psHierarquia.executeQuery();

			if (rsHierarquia.next()) {
				Hierarquia hierarquia = new Hierarquia();

				hierarquia.setCodigoHierarquia(rsHierarquia.getInt("codigo"));
				hierarquia.setDescricao(rsHierarquia.getString("descricao"));
				hierarquia.setAtivo(rsHierarquia.getBoolean("ativo"));

				rsHierarquia.close();
				conn.close();

				return hierarquia;
			} else {
				rsHierarquia.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Hierarquia por nome!\n" + ex.getMessage());
		}

	}

	@Override
	public void cadastrar(Object param) throws Exception {
		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn
					.prepareStatement("SELECT codigo, descricao, ativo FROM hierarquia WHERE (descricao = ?)");

			ps.setString(1, ((Hierarquia) param).getDescricao());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				try {
					Hierarquia hierarquia = new Hierarquia();
					hierarquia.setCodigoHierarquia(rs.getInt("codigo"));

					PreparedStatement psHierarquia = conn
							.prepareStatement("UPDATE hierarquia SET descricao = ?, ativo = true  WHERE (codigo = ?)");

					psHierarquia.setString(1, ((Hierarquia) param).getDescricao());
					psHierarquia.setInt(2, hierarquia.getCodigoHierarquia());
					psHierarquia.executeUpdate();

					conn.commit();
				} catch (Exception ex) {
					conn.rollback();
					throw new Exception("Erro ao alterar Hierarquia\n" + ex.getMessage());
				}

			} else {
				PreparedStatement psHierarquia = conn
						.prepareStatement("INSERT INTO hierarquia (descricao, ativo) VALUES (?, ?)");
				psHierarquia.setString(1, ((Hierarquia) param).getDescricao());
				psHierarquia.setBoolean(2, true);
				psHierarquia.executeUpdate();

				conn.commit();
				conn.close();
			}
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Hierarquia\n" + ex.getMessage());
		}

	}

	@Override
	public void alterar(Object param) throws Exception {

		conn = super.getConnection();

		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement(
				"SELECT codigo, descricao, ativo FROM hierarquia WHERE (descricao = ? AND ativo = false)");

		ps.setString(1, ((Hierarquia) param).getDescricao());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {

			try {
				Hierarquia hierarquia = new Hierarquia();
				hierarquia.setCodigoHierarquia(rs.getInt("codigo"));

				PreparedStatement psHierarquiaDelete = conn
						.prepareStatement("DELETE FROM hierarquia  WHERE (codigo = ?)");

				psHierarquiaDelete.setInt(1, hierarquia.getCodigoHierarquia());
				psHierarquiaDelete.executeUpdate();

				PreparedStatement psHierarquia = conn
						.prepareStatement("UPDATE hierarquia SET descricao = ?, ativo = true  WHERE (codigo = ?)");
				psHierarquia.setString(1, ((Hierarquia) param).getDescricao());
				psHierarquia.setInt(2, ((Hierarquia) param).getCodigoHierarquia());
				psHierarquia.executeUpdate();

				conn.commit();
			} catch (Exception ex) {
				conn.rollback();
				throw new Exception("Erro ao alterar Hierarquia\n" + ex.getMessage());
			}

		} else {

			try {
				conn.setAutoCommit(false);
				PreparedStatement psHierarquia = conn
						.prepareStatement("UPDATE hierarquia SET descricao = ? WHERE (codigo = ?)");

				psHierarquia.setString(1, ((Hierarquia) param).getDescricao());
				psHierarquia.setInt(2, ((Hierarquia) param).getCodigoHierarquia());
				psHierarquia.executeUpdate();

				conn.commit();
			} catch (Exception ex) {
				conn.rollback();
				throw new Exception("Erro ao alterar Hierarquia\n" + ex.getMessage());
			}
		}

	}

	@Override
	public void deletar(Object param) throws Exception {

		conn = super.getConnection();

		try {
			conn.setAutoCommit(false);
			Hierarquia hierarquia = new Hierarquia();
			DAOHierarquia dao = new DAOHierarquia();
			hierarquia = dao.getByCodigo(((Hierarquia) param).getCodigoHierarquia());
			PreparedStatement ps = conn.prepareStatement("UPDATE hierarquia SET ativo = false WHERE (codigo = ?)");
			ps.setInt(1, hierarquia.getCodigoHierarquia());
			ps.executeUpdate();

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			throw new Exception("- Erro ao deletar Hierarquia\n" + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Hierarquia> listar() throws Exception {
		// TODO Auto-generated method stub
		conn = super.getConnection();
		ArrayList<Hierarquia> al = new ArrayList<Hierarquia>();
		Hierarquia hierarquia = new Hierarquia();

		try {
			String sql = "SELECT descricao FROM hierarquia WHERE (ativo = true )";
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				hierarquia = getByNome(rs.getString("descricao"));
				al.add(hierarquia);
			}

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar hierarquias\n" + ex.getMessage());
		}

		return al;

	}

	@Override
	public ArrayList<Hierarquia> buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
