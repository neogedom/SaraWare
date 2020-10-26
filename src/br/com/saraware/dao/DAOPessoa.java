package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.saraware.domain.Encontrista;
import br.com.saraware.domain.EncontristaReincidente;
import br.com.saraware.domain.Pessoa;
import br.com.saraware.factory.ConexaoFactory;

public class DAOPessoa extends ConexaoFactory implements IDAO {

	private Connection conn;
	
	public Pessoa getByNome(String nome) throws Exception {
		conn = super.getConnection();

		String sqlPessoa = "SELECT codigo, nome, endereco, "
				+ "telefone, data_nascimento, numero_documento, tipo_documento, sexo, estado_civil,"
				+ " ativo FROM "
				+ "pessoa WHERE (nome = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa);
			psPessoa.setString(1, nome);
			ResultSet rsPessoa = psPessoa.executeQuery();

			if (rsPessoa.next()) {
				Pessoa pessoa = new Pessoa();

				pessoa.setCodigoPessoa(rsPessoa.getInt("codigo"));
				pessoa.setNome(rsPessoa.getString("nome"));
				pessoa.setEndereco(rsPessoa.getString("endereco"));
				pessoa.setTelefone(rsPessoa.getString("telefone"));
				pessoa.setDataNascimento(rsPessoa.getDate("data_nascimento"));
				pessoa.setNumeroDocumento(rsPessoa.getString("numero_documento"));
				pessoa.setTipoDocumento(rsPessoa.getString("tipo_documento"));
				pessoa.setSexo(rsPessoa.getString("sexo"));
				pessoa.setEstadoCivil(rsPessoa.getString("estado_civil"));
				pessoa.setAtivo(rsPessoa.getBoolean("ativo"));

				rsPessoa.close();
				psPessoa.close();
				conn.close();

				return pessoa;
			} else {
				rsPessoa.close();
				psPessoa.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Pessoa por nome!\n" + ex.getMessage());
		}

	}

	public Pessoa getByCodigo(Integer codigo) throws Exception {
		conn = super.getConnection();

		String sqlPessoa = "SELECT codigo, nome, endereco, "
				+ "telefone, data_nascimento, numero_documento, tipo_documento, sexo, estado_civil,"
				+ " ativo FROM "
				+ "pessoa WHERE (codigo = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa);
			psPessoa.setInt(1, codigo);
			ResultSet rsPessoa = psPessoa.executeQuery();

			if (rsPessoa.next()) {
				Pessoa pessoa = new Pessoa();

				pessoa.setCodigoPessoa(rsPessoa.getInt("codigo"));
				pessoa.setNome(rsPessoa.getString("nome"));
				pessoa.setEndereco(rsPessoa.getString("endereco"));
				pessoa.setTelefone(rsPessoa.getString("telefone"));
				pessoa.setDataNascimento(rsPessoa.getDate("data_nascimento"));
				pessoa.setNumeroDocumento(rsPessoa.getString("numero_documento"));
				pessoa.setTipoDocumento(rsPessoa.getString("tipo_documento"));
				pessoa.setSexo(rsPessoa.getString("sexo"));
				pessoa.setEstadoCivil(rsPessoa.getString("estado_civil"));
				pessoa.setAtivo(rsPessoa.getBoolean("ativo"));

				rsPessoa.close();
				psPessoa.close();
				conn.close();

				return pessoa;
			} else {
				rsPessoa.close();
				psPessoa.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Pessoa por nome!\n" + ex.getMessage());
		}
	}
	
	@Override
	public void cadastrar(Object param) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alterar(Object param) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletar(Object param) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void deletarEncontrista(Object param) throws Exception {
		conn = super.getConnection();

		try {
			conn.setAutoCommit(false);
			DAOEncontristaReincidente daoer = new DAOEncontristaReincidente();
			DAOEncontrista daoe = new DAOEncontrista();
			
			String sqlEncontrista = "SELECT p.nome FROM encontrista e "
					+ "INNER JOIN pessoa p ON e.pessoa_codigo = p.codigo "
					+ "WHERE p.nome = ?";
			
			PreparedStatement psEncontrista = conn.prepareStatement(sqlEncontrista);
			psEncontrista.setString(1, ((Pessoa) param).getNome());
			ResultSet rsEncontrista = psEncontrista.executeQuery();
			if (rsEncontrista.next()) {
				System.out.println("AQU");
				Encontrista encontrista = new Encontrista();
				encontrista = daoe.getByNome(((Pessoa) param).getNome());
				daoe.deletar(encontrista);
			} else {
				System.out.println("AQUI");
				EncontristaReincidente encontristaReincidente = new EncontristaReincidente();
				encontristaReincidente = daoer.getByNome(((Pessoa) param).getNome());
				daoer.deletar(encontristaReincidente);
			
			}
			
			conn.commit();
			conn.close();
		} catch (Exception ex) {
			throw new Exception("- Erro ao deletar Encontristas Reincidentes\n" + ex.getMessage());
		}

	}
	
	@Override
	public ArrayList<Pessoa> listar() throws Exception {
		// TODO Auto-generated method stub
		conn = super.getConnection();
		ArrayList<Pessoa> al = new ArrayList<Pessoa>();
		Pessoa pessoa = new Pessoa();

		try {
			String sql = "SELECT codigo, nome, endereco, telefone, data_nascimento, numero_documento, tipo_documento, sexo, estado_civil, ativo FROM pessoa WHERE ativo = true";
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pessoa = getByNome(rs.getString("nome"));
				al.add(pessoa);
			}
		}
		catch (Exception ex) {
			throw new Exception("Erro ao listar pessoa\n" + ex.getMessage());
		}
		
		return al;
	}

	public ArrayList<Pessoa> listarMembros() throws Exception {
		// TODO Auto-generated method stub
		conn = super.getConnection();
		ArrayList<Pessoa> al = new ArrayList<Pessoa>();
		Pessoa pessoa = new Pessoa();

		try {
			String sql = "SELECT p.codigo,"
					+ " nome, endereco, telefone, data_nascimento, numero_documento, tipo_documento, "
					+ "sexo, estado_civil, ativo "
					+ "FROM membro mb INNER JOIN pessoa p ON mb.pessoa_codigo = p.codigo "
					+ " WHERE ativo = true AND mb.codigo NOT IN (SELECT membro_codigo FROM encontreiro)";
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pessoa = getByNome(rs.getString("nome"));
				al.add(pessoa);
			}
		}
		catch (Exception ex) {
			throw new Exception("Erro ao listar membros através do DAOPessoa\n" + ex.getMessage());
		}
		
		return al;
	}
	
	public ArrayList<Pessoa> listarNaoMembros() throws Exception {
		// TODO Auto-generated method stub
		conn = super.getConnection();
		ArrayList<Pessoa> al = new ArrayList<Pessoa>();
		Pessoa pessoa = new Pessoa();

		try {
			String sql = "SELECT * FROM pessoa WHERE nome NOT IN (SELECT nome FROM membro mb INNER JOIN pessoa p ON mb.pessoa_codigo = p.codigo)";
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pessoa = getByNome(rs.getString("nome"));
				al.add(pessoa);
			}
		}
		catch (Exception ex) {
			throw new Exception("Erro ao listar membros através do DAOPessoa\n" + ex.getMessage());
		}
		
		return al;
	}
	
	@Override
	public ArrayList<Pessoa> buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
