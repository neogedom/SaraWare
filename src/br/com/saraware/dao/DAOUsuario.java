package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.saraware.domain.Usuario;
import br.com.saraware.factory.ConexaoFactory;

public class DAOUsuario extends ConexaoFactory implements IDAO {
	private Connection conn;

	public Usuario getByNome(String nome) throws Exception {
		conn = super.getConnection();

		String sqlUsuario = "SELECT codigo, usuario, senha, ativo FROM usuario WHERE (usuario = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario);
			psUsuario.setString(1, nome);
			ResultSet rsUsuario = psUsuario.executeQuery();

			if (rsUsuario.next()) {
				Usuario usuario = new Usuario();

				usuario.setCodigoUsuario(rsUsuario.getInt("codigo"));
				usuario.setUsuario(rsUsuario.getString("usuario"));
				usuario.setSenha(rsUsuario.getString("senha"));				
				usuario.setAtivo(rsUsuario.getBoolean("ativo"));

				rsUsuario.close();
				conn.close();

				return usuario;
			} else {
				rsUsuario.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Usuario por nome!\n" + ex.getMessage());
		}

	}

	public Usuario getByCodigo(Integer codigo) throws Exception {
		conn = super.getConnection();

		String sqlUsuario = "SELECT codigo, usuario, senha, ativo FROM usuario WHERE (codigo = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario);
			psUsuario.setInt(1, codigo);
			ResultSet rsUsuario = psUsuario.executeQuery();

			if (rsUsuario.next()) {
				Usuario usuario = new Usuario();

				usuario.setCodigoUsuario(rsUsuario.getInt("codigo"));
				usuario.setUsuario(rsUsuario.getString("usuario"));
				usuario.setSenha(rsUsuario.getString("senha"));				
				usuario.setAtivo(rsUsuario.getBoolean("ativo"));

				rsUsuario.close();
				conn.close();

				return usuario;
			} else {
				rsUsuario.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Usuario por nome!\n" + ex.getMessage());
		}
	}

	@Override
	public void cadastrar(Object param) throws Exception {
		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn
					.prepareStatement("SELECT codigo, usuario, senha, ativo FROM usuario WHERE (usuario = ?)");

			ps.setString(1, ((Usuario) param).getUsuario());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				try {
					Usuario usuario = new Usuario();
					usuario.setCodigoUsuario(rs.getInt("codigo"));

					PreparedStatement psUsuario = conn
							.prepareStatement("UPDATE usuario SET usuario = ?, senha = ?, ativo = true  WHERE (codigo = ?)");

					psUsuario.setString(1, ((Usuario) param).getUsuario());
					psUsuario.setString(2, ((Usuario) param).getSenha());
					psUsuario.setInt(3, usuario.getCodigoUsuario());
					psUsuario.executeUpdate();

					conn.commit();
				} catch (Exception ex) {
					conn.rollback();
					throw new Exception("Erro ao alterar Usuario\n" + ex.getMessage());
				}

			} else {
				PreparedStatement psUsuario = conn
						.prepareStatement("INSERT INTO usuario (usuario, senha, ativo) VALUES (?, ?, ?)");
				psUsuario.setString(1, ((Usuario) param).getUsuario());
				psUsuario.setString(2, ((Usuario) param).getSenha());				
				psUsuario.setBoolean(3, true);
				psUsuario.executeUpdate();

				conn.commit();
				conn.close();
			}
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Usuario\n" + ex.getMessage());
		}

	}

	@Override
	public void alterar(Object param) throws Exception {

		conn = super.getConnection();

		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement(
				"SELECT codigo, usuario, senha, ativo FROM usuario WHERE (usuario = ? AND senha = ? AND ativo = false)");

		ps.setString(1, ((Usuario) param).getUsuario());
		ps.setString(2, ((Usuario) param).getSenha());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {

			try {
				Usuario usuario = new Usuario();
				usuario.setCodigoUsuario(rs.getInt("codigo"));

				PreparedStatement psUsuarioDelete = conn
						.prepareStatement("DELETE FROM usuario  WHERE (codigo = ?)");

				psUsuarioDelete.setInt(1, usuario.getCodigoUsuario());
				psUsuarioDelete.executeUpdate();

				PreparedStatement psUsuario = conn
						.prepareStatement("UPDATE usuario SET usuario = ?, senha = ?, ativo = true  WHERE (codigo = ?)");
				psUsuario.setString(1, ((Usuario) param).getUsuario());
				psUsuario.setString(2, ((Usuario) param).getSenha());
				psUsuario.setInt(3, ((Usuario) param).getCodigoUsuario());
				psUsuario.executeUpdate();

				conn.commit();
			} catch (Exception ex) {
				conn.rollback();
				throw new Exception("Erro ao alterar Usuario\n" + ex.getMessage());
			}

		} else {

			try {
				conn.setAutoCommit(false);
				PreparedStatement psUsuario = conn
						.prepareStatement("UPDATE usuario SET usuario = ?, senha = ? WHERE (codigo = ?)");

				psUsuario.setString(1, ((Usuario) param).getUsuario());
				psUsuario.setString(2, ((Usuario) param).getSenha());
				psUsuario.setInt(3, ((Usuario) param).getCodigoUsuario());
				psUsuario.executeUpdate();

				conn.commit();
			} catch (Exception ex) {
				conn.rollback();
				throw new Exception("Erro ao alterar Usuario\n" + ex.getMessage());
			}
		}

	}

	@Override
	public void deletar(Object param) throws Exception {

		conn = super.getConnection();

		try {
			conn.setAutoCommit(false);
			Usuario usuario = new Usuario();
			DAOUsuario dao = new DAOUsuario();
			usuario = dao.getByCodigo(((Usuario) param).getCodigoUsuario());
			PreparedStatement ps = conn.prepareStatement("UPDATE usuario SET ativo = false WHERE (codigo = ?)");
			ps.setInt(1, usuario.getCodigoUsuario());
			ps.executeUpdate();

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			throw new Exception("- Erro ao deletar Usuario\n" + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Usuario> listar() throws Exception {
		// TODO Auto-generated method stub
		conn = super.getConnection();
		ArrayList<Usuario> al = new ArrayList<Usuario>();
		Usuario usuario = new Usuario();

		try {
			String sql = "SELECT * FROM usuario WHERE (ativo = true )";
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				usuario = getByNome(rs.getString("descricao"));
				al.add(usuario);
			}
		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar usuarios\n" + ex.getMessage());
		}

		return al;

	}
	
	public Usuario autenticar(String _usuario, String senha) throws Exception {
		// TODO Auto-generated method stub
		conn = super.getConnection();
		Usuario usuario = null;

		try {
			String sql = "SELECT * FROM usuario WHERE (usuario = ? AND senha = ? AND ativo = true )";
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, _usuario);
			ps.setString(2, senha);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next())
			{
				usuario = getByNome(rs.getString("usuario"));
			}
		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar usuarios\n" + ex.getMessage());
		}

		return usuario;

	}

	
	
	@Override
	public ArrayList<Usuario> buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
