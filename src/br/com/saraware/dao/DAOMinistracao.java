package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.saraware.domain.Encontreiro;
import br.com.saraware.domain.Ministracao;
import br.com.saraware.factory.ConexaoFactory;

public class DAOMinistracao extends ConexaoFactory implements IDAO {

	private Connection conn;

	public Ministracao getByNome(String nome) throws Exception {
		conn = super.getConnection();
		String sqlMinistracao = "SELECT codigo, titulo, ministrante_codigo, minutos, ativo FROM ministracao WHERE (titulo = ?)";
	

		try {
			conn.setAutoCommit(false);
			PreparedStatement psMinistracao = conn.prepareStatement(sqlMinistracao);
			psMinistracao.setString(1, nome);
			ResultSet rsMinistracao = psMinistracao.executeQuery();
			
			if (rsMinistracao.next()) {
				Ministracao ministracao = new Ministracao();
				Encontreiro encontreiro = new Encontreiro();
				DAOEncontreiro daoe = new DAOEncontreiro();

				ministracao.setCodigoMinistracao(rsMinistracao.getInt("codigo"));
				ministracao.setTitulo(rsMinistracao.getString("titulo"));
				encontreiro = daoe.getByCodigo(rsMinistracao.getInt("ministrante_codigo"));
				ministracao.setMinistrante(encontreiro);
				ministracao.setMinutos(rsMinistracao.getInt("minutos"));
				ministracao.setAtivo(rsMinistracao.getBoolean("ativo"));
				
				rsMinistracao.close();
				conn.close();

				return ministracao;
			} else {
				rsMinistracao.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Ministração por nome!\n" + ex.getMessage());
		}

	}

	public Ministracao getByCodigo(Integer codigo) throws Exception {
		conn = super.getConnection();
		String sqlMinistracao = "SELECT codigo, titulo, ministrante_codigo, minutos, ativo FROM ministracao WHERE (codigo = ?)";
	

		try {
			conn.setAutoCommit(false);
			PreparedStatement psMinistracao = conn.prepareStatement(sqlMinistracao);
			psMinistracao.setInt(1, codigo);
			ResultSet rsMinistracao = psMinistracao.executeQuery();
			
			if (rsMinistracao.next()) {
				Ministracao ministracao = new Ministracao();
				Encontreiro encontreiro = new Encontreiro();
				DAOEncontreiro daoe = new DAOEncontreiro();

				ministracao.setCodigoMinistracao(rsMinistracao.getInt("codigo"));
				ministracao.setTitulo(rsMinistracao.getString("titulo"));
				encontreiro = daoe.getByCodigo(rsMinistracao.getInt("ministrante_codigo"));
				ministracao.setMinistrante(encontreiro);
				ministracao.setMinutos(rsMinistracao.getInt("minutos"));
				ministracao.setAtivo(rsMinistracao.getBoolean("ativo"));
				
				rsMinistracao.close();
				conn.close();

				return ministracao;
			} else {
				rsMinistracao.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Ministração por código!\n" + ex.getMessage());
		}

	}
	
	@Override
	public void cadastrar(Object param) throws Exception {
		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn
					.prepareStatement("SELECT codigo, titulo, ministrante_codigo, ativo FROM ministracao WHERE (titulo = ? )");

			ps.setString(1, ((Ministracao) param).getTitulo());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				try {
					Ministracao ministracao = new Ministracao();
					ministracao.setCodigoMinistracao(rs.getInt("codigo"));

					PreparedStatement psMinistracao = conn.prepareStatement(
							"UPDATE ministracao SET titulo = ?, ministrante_codigo = ?, ativo = true  WHERE (codigo = ?)");

					psMinistracao.setString(1, ((Ministracao) param).getTitulo());
					psMinistracao.setInt(2, ((Ministracao) param).getMinistrante().getCodigoEncontreiro());
					psMinistracao.setInt(3, ministracao.getCodigoMinistracao());
					psMinistracao.executeUpdate();

					conn.commit();
				} catch (Exception ex) {
					conn.rollback();
					throw new Exception("Erro ao alterar Ministração\n" + ex.getMessage());
				}

			} else {
				PreparedStatement psMinistracao = conn
						.prepareStatement("INSERT INTO ministracao (titulo, ministrante_codigo, minutos, ativo) VALUES (?, ?, ?, ?)");

				psMinistracao.setString(1, ((Ministracao) param).getTitulo());
				psMinistracao.setInt(2, ((Ministracao) param).getMinistrante().getCodigoEncontreiro());
				psMinistracao.setInt(3, ((Ministracao) param).getMinutos());
				psMinistracao.setBoolean(4, true);
				psMinistracao.executeUpdate();
				

				conn.commit();
				conn.close();
			}
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Ministração\n" + ex.getMessage());
		}

	}

	@Override
	public void alterar(Object param) throws Exception {
		conn = super.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement("SELECT codigo, titulo, ativo FROM ministracao WHERE (titulo = ? AND ativo = false)");
		ps.setString(1, ((Ministracao) param).getTitulo());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {

			try {
				Ministracao ministracao = new Ministracao();
				ministracao.setCodigoMinistracao(rs.getInt("codigo"));

				PreparedStatement psMinistracaoDelete = conn.prepareStatement("DELETE FROM ministracao WHERE (codigo = ?)");
				psMinistracaoDelete.setInt(1, ministracao.getCodigoMinistracao());
				psMinistracaoDelete.executeUpdate();

				PreparedStatement psMinistracao = conn.prepareStatement("UPDATE ministracao SET titulo = ?, ministrante_codigo = ?, ativo = true  WHERE (codigo = ?)");
				psMinistracao.setString(1, ((Ministracao) param).getTitulo());
				psMinistracao.setInt(2, ((Ministracao) param).getMinistrante().getCodigoEncontreiro());
				psMinistracao.setInt(3, ((Ministracao) param).getCodigoMinistracao());
				psMinistracao.executeUpdate();

				conn.commit();
			} catch (Exception ex) {
				conn.rollback();
				throw new Exception("Erro ao alterar Ministração\n" + ex.getMessage());
			}

		} else {

			try {
				conn.setAutoCommit(false);
				PreparedStatement psMinistracao = conn.prepareStatement("UPDATE ministracao SET titulo = ?, ministrante_codigo = ? WHERE (codigo = ?)");

				psMinistracao.setString(1, ((Ministracao) param).getTitulo());
				psMinistracao.setInt(2, ((Ministracao) param).getMinistrante().getCodigoEncontreiro());
				psMinistracao.setInt(3, ((Ministracao) param).getCodigoMinistracao());
				psMinistracao.executeUpdate();

				conn.commit();
			} catch (Exception ex) {
				conn.rollback();
				throw new Exception("Erro ao alterar Ministração\n" + ex.getMessage());
			}
		}

	}

	@Override
	public void deletar(Object param) throws Exception {
		conn = super.getConnection();

		try {
			conn.setAutoCommit(false);
			Ministracao ministracao = new Ministracao();
			DAOMinistracao dao = new DAOMinistracao();
			ministracao = dao.getByCodigo(((Ministracao) param).getCodigoMinistracao());
			PreparedStatement ps = conn.prepareStatement("UPDATE ministracao SET ativo = false WHERE (codigo = ?)");
			ps.setInt(1, ministracao.getCodigoMinistracao());
			ps.executeUpdate();

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			throw new Exception("- Erro ao deletar Ministração\n" + ex.getMessage());
		}

	}

	@Override
	public ArrayList<Ministracao> listar() throws Exception {

		conn = super.getConnection();
		ArrayList<Ministracao> al = new ArrayList<Ministracao>();
		Ministracao ministracao = new Ministracao();

		try {
			String sql = "SELECT codigo, titulo, ministrante_codigo, ativo FROM ministracao WHERE ativo = true";
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ministracao = getByNome(rs.getString("titulo"));
				al.add(ministracao);
			}

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar ministrações\n" + ex.getMessage());
		}

		return al;
	}

	@Override
	public ArrayList<Ministracao> buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
