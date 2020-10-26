package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.saraware.domain.Encontreiro;
import br.com.saraware.domain.Inscricao;
import br.com.saraware.domain.Pessoa;
import br.com.saraware.domain.Receita;
import br.com.saraware.factory.ConexaoFactory;

public class DAOInscricao extends ConexaoFactory implements IDAO {
	
	private Connection conn;

	public Inscricao getByNome(String nome) throws Exception {
		conn = super.getConnection();

		String sqlInscricao = "SELECT p.codigo as codigo_pessoa, i.codigo as codigo_inscricao, r.codigo as receita_codigo, "
					+ "p.nome, p.endereco, p.telefone, p.data_nascimento, p.numero_documento, p.tipo_documento,"
					+ "p.sexo, p.estado_civil, r.valor FROM inscricao i "
					+ "INNER JOIN pessoa p ON i.pessoa_codigo = p.codigo WHERE (i.receita_codigo = r.codigo AND p.nome = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psInscricao = conn.prepareStatement(sqlInscricao);
			psInscricao.setString(1, nome);
			ResultSet rsInscricao = psInscricao.executeQuery();

			if (rsInscricao.next()) {
				Inscricao inscricao = new Inscricao();
				Pessoa pessoa = new Pessoa();
				DAOPessoa daop = new DAOPessoa();
				Receita receita = new Receita();
				DAOReceita daor = new DAOReceita();
				

				inscricao.setCodigoInscricao(rsInscricao.getInt("codigo"));
				pessoa = daop.getByNome(rsInscricao.getString("nome"));
				inscricao.setPessoa(pessoa);
				receita = daor.getByCodigo(rsInscricao.getInt("receita_codigo"));
				inscricao.setReceita(receita);
				inscricao.setAtivo(rsInscricao.getBoolean("ativo"));

				rsInscricao.close();
				conn.close();

				return inscricao;
			} else {
				rsInscricao.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Inscrição por nome!\n" + ex.getMessage());
		}

	}
	
	@Override
	public void cadastrar(Object param) throws Exception {
		// TODO Auto-generated method stub

	}
	
	public void cadastrar(Object param, Double valorInscricao) throws Exception {
		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);

			PreparedStatement psReceita = conn
					.prepareStatement("INSERT INTO receita (valor, caixa_codigo) VALUES (?, 1)");
	
			PreparedStatement psInscricao = conn
						.prepareStatement("INSERT INTO inscricao (pessoa_codigo, receita_codigo) VALUES (?, (SELECT MAX(codigo) FROM receita))");

				psReceita.setDouble(1, valorInscricao);
				psReceita.executeUpdate();
			
				psInscricao.setInt(1, ((Inscricao) param).getPessoa().getCodigoPessoa());
				psInscricao.executeUpdate();
				
				PreparedStatement psCaixa = conn
						.prepareStatement("SELECT * FROM caixa ");
				ResultSet rsCaixa = psCaixa.executeQuery();
				
				
				if (rsCaixa.next())
				{
					Double novoValor = rsCaixa.getDouble("saldo") + valorInscricao;

					PreparedStatement psAtualizaCaixa = conn
							.prepareStatement("UPDATE caixa SET saldo = ?");
					
					psAtualizaCaixa.setDouble(1, novoValor);
					psAtualizaCaixa.executeUpdate();
				} else
				{
					Double novoValor = rsCaixa.getDouble("saldo") + valorInscricao;
					
					PreparedStatement psInsereCaixa = conn
							.prepareStatement("INSERT INTO caixa (codigo, saldo) VALUES (1, ?)");
					psInsereCaixa.setDouble(1, novoValor);
				}


				conn.commit();
				conn.close();
			
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Inscrição de Encontreiro\n" + ex.getMessage());
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
	public ArrayList<Inscricao> listar() throws Exception {
		
		conn = super.getConnection();
		ArrayList<Inscricao> al = new ArrayList<Inscricao>();
		Inscricao celula = new Inscricao();

		try {
			String sql = "SELECT p.codigo as codigo_pessoa, i.codigo as codigo_inscricao, r.codigo as receita_codigo, "
					+ "p.nome, p.endereco, p.telefone, p.data_nascimento, p.numero_documento, p.tipo_documento,"
					+ "p.sexo, p.estado_civil, r.valor FROM inscricao i "
					+ "INNER JOIN pessoa p ON i.pessoa_codigo = p.codigo WHERE i.receita_codigo = r.codigo AND ativo = true";
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				celula = getByNome(rs.getString("nome"));
				al.add(celula);
			}

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar inscrições\n" + ex.getMessage());
		}

		return al;
	}

	@Override
	public ArrayList<Inscricao> buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void inscreverEncontreiro(ArrayList<Encontreiro> al)
	{
		
	}

}
