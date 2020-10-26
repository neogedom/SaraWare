package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import br.com.saraware.domain.Cronograma;
import br.com.saraware.domain.RevisaoDeVidas;
import br.com.saraware.factory.ConexaoFactory;

public class DAOCronograma extends ConexaoFactory implements IDAO {

	private Connection conn;

	public Cronograma getByRevisao(Integer codigo_revisao) throws Exception {
		conn = super.getConnection();

		String sqlCronograma = "SELECT * FROM cronograma WHERE (revisao_codigo = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psCronograma = conn.prepareStatement(sqlCronograma);
			psCronograma.setInt(1, codigo_revisao);
			ResultSet rsCronograma = psCronograma.executeQuery();

			if (rsCronograma.next()) {
				Cronograma cronograma = new Cronograma();
				RevisaoDeVidas revisaoDeVidas = new RevisaoDeVidas();
				DAORevisao daor = new DAORevisao();

				cronograma.setCodigoCronograma(rsCronograma.getInt("codigo"));
				daor.getByCodigo(rsCronograma.getInt("revisao_codigo"));
				cronograma.setRevisaoDeVidas(revisaoDeVidas);

				rsCronograma.close();
				conn.close();

				return cronograma;
			} else {
				rsCronograma.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Cronograma por Revisão!\n" + ex.getMessage());
		}

	}
	
	public Cronograma getByCodigo(Integer codigo) throws Exception {
		conn = super.getConnection();

		String sqlCronograma = "SELECT * FROM cronograma WHERE (codigo = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psCronograma = conn.prepareStatement(sqlCronograma);
			psCronograma.setInt(1, codigo);
			ResultSet rsCronograma = psCronograma.executeQuery();

			if (rsCronograma.next()) {
				Cronograma cronograma = new Cronograma();
				RevisaoDeVidas revisaoDeVidas = new RevisaoDeVidas();
				DAORevisao daor = new DAORevisao();

				cronograma.setCodigoCronograma(rsCronograma.getInt("codigo"));
				daor.getByCodigo(rsCronograma.getInt("revisao_codigo"));
				cronograma.setRevisaoDeVidas(revisaoDeVidas);

				rsCronograma.close();
				conn.close();

				return cronograma;
			} else {
				rsCronograma.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Cronograma por código!\n" + ex.getMessage());
		}

	}

	@Override
	public void cadastrar(Object param) throws Exception {
		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			DAORevisao daor = new DAORevisao();
			RevisaoDeVidas revisaoDeVidas = new RevisaoDeVidas();
			PreparedStatement psCronograma = conn.prepareStatement("INSERT INTO cronograma (revisao_codigo) VALUES (?)");
			
			revisaoDeVidas = daor.getByData(((Cronograma) param).getRevisaoDeVidas().getDataDeAcontecimento());				
			psCronograma.setInt(1, revisaoDeVidas.getCodigoRevisaoDeVidas());
			psCronograma.executeUpdate();

			conn.commit();
			conn.close();
			
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Cronograma\n" + ex.getMessage());
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
			Cronograma cronograma = new Cronograma();
			DAOCronograma dao = new DAOCronograma();
			cronograma = dao.getByCodigo(((Cronograma) param).getCodigoCronograma());
			PreparedStatement ps = conn.prepareStatement("DELETE FROM cronograma WHERE (codigo = ?)");
			ps.setInt(1, cronograma.getCodigoCronograma());
			ps.executeUpdate();

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			throw new Exception("- Erro ao deletar Cronograma\n" + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Cronograma> listar() throws Exception {
		// TODO Auto-generated method stub
		conn = super.getConnection();
		ArrayList<Cronograma> al = new ArrayList<Cronograma>();
		Cronograma cronograma = new Cronograma();

		try {
			String sql = "SELECT descricao FROM cronograma";
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				cronograma = getByCodigo(rs.getInt("codigo"));
				al.add(cronograma);
			}

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar cronogramas\n" + ex.getMessage());
		}

		return al;

	}

	@Override
	public ArrayList<Cronograma> buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
