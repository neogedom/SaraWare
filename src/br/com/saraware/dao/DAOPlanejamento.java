package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.saraware.domain.Planejamento;
import br.com.saraware.domain.RevisaoDeVidas;
import br.com.saraware.factory.ConexaoFactory;

public class DAOPlanejamento extends ConexaoFactory implements IDAO {

	private Connection conn;
	
	public Planejamento getByAno(Integer ano) throws Exception {
		conn = super.getConnection();

		String sqlPlanejamento = "SELECT * FROM planejamento WHERE (ano = ?)";

		try {
			conn.setAutoCommit(false);
			PreparedStatement psPlanejamento = conn.prepareStatement(sqlPlanejamento);
			psPlanejamento.setInt(1, ano);
			ResultSet rsPlanejamento = psPlanejamento.executeQuery();

			if (rsPlanejamento.next()) {
				Planejamento planejamento = new Planejamento();
				DAORevisao daor = new DAORevisao();
				RevisaoDeVidas revisao = new RevisaoDeVidas();
				
				planejamento.setCodigoPlanejamento(rsPlanejamento.getInt("codigo"));
				planejamento.setAno(new Date(rsPlanejamento.getInt("ano")));
				revisao = daor.getByCodigo(rsPlanejamento.getInt("codigo_revisao_1"));
				planejamento.setRevisao1(revisao);
				revisao = daor.getByCodigo(rsPlanejamento.getInt("codigo_revisao_2"));
				planejamento.setRevisao2(revisao);
				revisao = daor.getByCodigo(rsPlanejamento.getInt("codigo_revisao_3"));
				planejamento.setRevisao3(revisao);
				revisao = daor.getByCodigo(rsPlanejamento.getInt("codigo_revisao_4"));
				planejamento.setRevisao4(revisao);
				planejamento.setAtivo(rsPlanejamento.getBoolean("ativo"));

				rsPlanejamento.close();
				conn.close();

				return planejamento;
			} else {
				rsPlanejamento.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Planejamento por ano!\n" + ex.getMessage());
		}

	}
	
	@Override
	public void cadastrar(Object param) throws Exception {
		// TODO Auto-generated method stub
		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn
					.prepareStatement("SELECT codigo, ano, ativo FROM planejamento WHERE (ano = ?)");

			Calendar c = Calendar.getInstance();
			c.setTime(((Planejamento) param).getAno());
			ps.setInt(1, c.get(c.YEAR));
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				try {
					Planejamento planejamento = new Planejamento();
					planejamento.setCodigoPlanejamento(rs.getInt("codigo"));

					PreparedStatement psPlanejamento = conn
							.prepareStatement("UPDATE planejamento SET data_revisao_1 = ?, data_revisao_2 = ?, data_revisao_3 = ?, data_revisao_4 = ?, ativo = true  WHERE (codigo = ?)");

					psPlanejamento.setDate(1, new Date(((Planejamento) param).getRevisao1().getDataDeAcontecimento().getTime()));
					psPlanejamento.setDate(2, new Date(((Planejamento) param).getRevisao2().getDataDeAcontecimento().getTime()));
					psPlanejamento.setDate(3, new Date(((Planejamento) param).getRevisao3().getDataDeAcontecimento().getTime()));
					psPlanejamento.setDate(4, new Date(((Planejamento) param).getRevisao4().getDataDeAcontecimento().getTime()));
					psPlanejamento.setInt(5, planejamento.getCodigoPlanejamento());
					psPlanejamento.executeUpdate();

					conn.commit();
				} catch (Exception ex) {
					conn.rollback();
					throw new Exception("Existe um planejamento" + ex.getMessage());
				}

			} else {
				PreparedStatement psPlanejamento = conn
						.prepareStatement("INSERT INTO planejamento (ano, data_revisao_1, data_revisao_2, data_revisao_3, data_revisao_4, ativo) VALUES (?, ?, ?, ?, ?, ?)");
				psPlanejamento.setInt(1, c.get(c.YEAR));
				psPlanejamento.setDate(2, new Date(((Planejamento) param).getRevisao1().getDataDeAcontecimento().getTime()));
				psPlanejamento.setDate(3, new Date(((Planejamento) param).getRevisao2().getDataDeAcontecimento().getTime()));
				psPlanejamento.setDate(4, new Date(((Planejamento) param).getRevisao3().getDataDeAcontecimento().getTime()));
				psPlanejamento.setDate(5, new Date(((Planejamento) param).getRevisao4().getDataDeAcontecimento().getTime()));
				psPlanejamento.setBoolean(6, true);
				psPlanejamento.executeUpdate();

				conn.commit();
				conn.close();
			}
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Planejamento\n" + ex.getMessage());
		}

	}
	
	public void cadastrar(Planejamento p, RevisaoDeVidas r1, RevisaoDeVidas r2, RevisaoDeVidas r3, RevisaoDeVidas r4 ) throws Exception {
		// TODO Auto-generated method stub
		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn
					.prepareStatement("SELECT codigo, ano, ativo FROM planejamento WHERE (ano = ?)");

			Calendar c = Calendar.getInstance();
			c.setTime(p.getAno());
			ps.setInt(1, c.get(c.YEAR));
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				try {
					Planejamento planejamento = new Planejamento();
					planejamento.setCodigoPlanejamento(rs.getInt("codigo"));

					PreparedStatement psRevisao = conn
							.prepareStatement("UPDATE revisao_de_vidas SET data_acontecimento = ? WHERE (codigo = ?)");

					psRevisao.setDate(1, new Date(p.getRevisao1().getDataDeAcontecimento().getTime()));
					psRevisao.setInt(2, planejamento.getRevisao1().getCodigoRevisaoDeVidas());
					psRevisao.executeUpdate();
					
					psRevisao.setDate(1, new Date(p.getRevisao2().getDataDeAcontecimento().getTime()));
					psRevisao.setInt(2, planejamento.getRevisao2().getCodigoRevisaoDeVidas());
					psRevisao.executeUpdate();
					
					psRevisao.setDate(1, new Date(p.getRevisao2().getDataDeAcontecimento().getTime()));
					psRevisao.setInt(2, planejamento.getRevisao1().getCodigoRevisaoDeVidas());
					psRevisao.executeUpdate();
					
					psRevisao.setDate(1, new Date(p.getRevisao2().getDataDeAcontecimento().getTime()));
					psRevisao.setInt(2, planejamento.getRevisao2().getCodigoRevisaoDeVidas());
					psRevisao.executeUpdate();

					conn.commit();
				} catch (Exception ex) {
					conn.rollback();
					throw new Exception("Erro ao alterar planejamento" + ex.getMessage());
				}

			} else {
				PreparedStatement psRevisao = conn
						.prepareStatement("INSERT INTO revisao_de_vidas (data_acontecimento) VALUES (?)");
				
				psRevisao.setDate(1, new Date(r1.getDataDeAcontecimento().getTime()));
				psRevisao.executeUpdate();
				
				PreparedStatement psPlanejamento = conn
						.prepareStatement("INSERT INTO planejamento (ano, codigo_revisao_1, ativo) VALUES (?, (SELECT MAX(codigo) FROM revisao_de_vidas), true)");
				psPlanejamento.setInt(1, c.get(c.YEAR));
				psPlanejamento.executeUpdate();
				
				
				psRevisao.setDate(1, new Date(r2.getDataDeAcontecimento().getTime()));
				psRevisao.executeUpdate();
				
				PreparedStatement psPlanejamentoSelect = conn
						.prepareStatement("SELECT codigo FROM planejamento ORDER BY codigo DESC LIMIT 1");
				ResultSet rsPlanejamentoSelect = psPlanejamentoSelect.executeQuery();
				rsPlanejamentoSelect.next();
				
				PreparedStatement psPlanejamento2Update = conn
						.prepareStatement("UPDATE planejamento SET codigo_revisao_2 = (SELECT MAX(codigo) FROM revisao_de_vidas) WHERE (codigo = ?)");
				psPlanejamento2Update.setInt(1, rsPlanejamentoSelect.getInt("codigo"));
				psPlanejamento2Update.executeUpdate();
				
				psRevisao.setDate(1, new Date(r3.getDataDeAcontecimento().getTime()));
				psRevisao.executeUpdate();
				
				PreparedStatement psPlanejamento3Update = conn
						.prepareStatement("UPDATE planejamento SET codigo_revisao_3 = (SELECT MAX(codigo) FROM revisao_de_vidas) WHERE (codigo = ?)");
				psPlanejamento3Update.setInt(1, rsPlanejamentoSelect.getInt("codigo"));
				psPlanejamento3Update.executeUpdate();
			
				psRevisao.setDate(1, new Date(r4.getDataDeAcontecimento().getTime()));
				psRevisao.executeUpdate();

				PreparedStatement psPlanejamento4Update = conn
						.prepareStatement("UPDATE planejamento SET codigo_revisao_4 = (SELECT MAX(codigo) FROM revisao_de_vidas) WHERE (codigo = ?)");
				psPlanejamento4Update.setInt(1, rsPlanejamentoSelect.getInt("codigo"));
				psPlanejamento4Update.executeUpdate();
			
				conn.commit();
				conn.close();
			}
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Planejamento\n" + ex.getMessage());
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
	public ArrayList listar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
