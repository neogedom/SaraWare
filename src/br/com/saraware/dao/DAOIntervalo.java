package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import br.com.saraware.domain.Intervalo;
import br.com.saraware.factory.ConexaoFactory;

public class DAOIntervalo extends ConexaoFactory implements IDAO {

	private Connection conn;

	public Intervalo getByNome(String nome) throws Exception {
		conn = super.getConnection();

		String sqlIntervalo = "SELECT * FROM intervalo WHERE (descricao = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psIntervalo = conn.prepareStatement(sqlIntervalo);
			psIntervalo.setString(1, nome);
			ResultSet rsIntervalo = psIntervalo.executeQuery();

			if (rsIntervalo.next()) {
				Intervalo intervalo = new Intervalo();

				intervalo.setCodigoIntervalo(rsIntervalo.getInt("codigo"));
				intervalo.setDescricaoIntervalo(rsIntervalo.getString("descricao"));
				intervalo.setMinutos(rsIntervalo.getInt("minutos"));

				rsIntervalo.close();
				conn.close();

				return intervalo;
			} else {
				rsIntervalo.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Intervalo por nome!\n" + ex.getMessage());
		}

	}

	public Intervalo getByCodigo(Integer codigo) throws Exception {
		conn = super.getConnection();

		String sqlIntervalo = "SELECT * FROM intervalo WHERE (codigo = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psIntervalo = conn.prepareStatement(sqlIntervalo);
			psIntervalo.setInt(1, codigo);
			ResultSet rsIntervalo = psIntervalo.executeQuery();

			if (rsIntervalo.next()) {
				Intervalo intervalo = new Intervalo();

				intervalo.setCodigoIntervalo(rsIntervalo.getInt("codigo"));
				intervalo.setDescricaoIntervalo(rsIntervalo.getString("descricao"));
				intervalo.setMinutos(rsIntervalo.getInt("minutos"));


				rsIntervalo.close();
				conn.close();

				return intervalo;
			} else {
				rsIntervalo.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Intervalo por código!\n" + ex.getMessage());
		}

	}

	@Override
	public void cadastrar(Object param) throws Exception {
		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn
					.prepareStatement("SELECT * FROM intervalo WHERE (descricao = ?)");

			ps.setString(1, ((Intervalo) param).getDescricaoIntervalo());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				try {
					Intervalo intervalo = new Intervalo();
					intervalo.setCodigoIntervalo(rs.getInt("codigo"));

					PreparedStatement psIntervalo = conn
							.prepareStatement("UPDATE intervalo SET descricao = ?  WHERE (codigo = ?)");

					psIntervalo.setString(1, ((Intervalo) param).getDescricaoIntervalo());
					psIntervalo.setInt(2, intervalo.getCodigoIntervalo());
					psIntervalo.executeUpdate();

					conn.commit();
				} catch (Exception ex) {
					conn.rollback();
					throw new Exception("Erro ao alterar Intervalo\n" + ex.getMessage());
				}

			} else {
				PreparedStatement psIntervalo = conn
						.prepareStatement("INSERT INTO intervalo (descricao, minutos) VALUES (?, ?)");
				psIntervalo.setString(1, ((Intervalo) param).getDescricaoIntervalo());
				psIntervalo.setInt(2, ((Intervalo) param).getMinutos());
				psIntervalo.executeUpdate();

				conn.commit();
				conn.close();
			}
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Intervalo\n" + ex.getMessage());
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
			Intervalo intervalo = new Intervalo();
			DAOIntervalo dao = new DAOIntervalo();
			intervalo = dao.getByCodigo(((Intervalo) param).getCodigoIntervalo());
			PreparedStatement ps = conn.prepareStatement("DELETE FROM intervalo WHERE (codigo = ?)");
			ps.setInt(1, intervalo.getCodigoIntervalo());
			ps.executeUpdate();

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			throw new Exception("- Erro ao deletar Intervalo\n" + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Intervalo> listar() throws Exception {
		// TODO Auto-generated method stub
		conn = super.getConnection();
		ArrayList<Intervalo> al = new ArrayList<Intervalo>();
		Intervalo intervalo = new Intervalo();

		try {
			String sql = "SELECT descricao FROM intervalo";
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				intervalo = getByNome(rs.getString("descricao"));
				al.add(intervalo);
			}

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar intervalos\n" + ex.getMessage());
		}

		return al;

	}

	@Override
	public ArrayList<Intervalo> buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
