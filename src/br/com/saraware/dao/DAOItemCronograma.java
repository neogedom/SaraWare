package br.com.saraware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import br.com.saraware.domain.Cronograma;
import br.com.saraware.domain.Intervalo;
import br.com.saraware.domain.ItemCronograma;
import br.com.saraware.domain.Ministracao;
import br.com.saraware.domain.RevisaoDeVidas;
import br.com.saraware.factory.ConexaoFactory;

public class DAOItemCronograma extends ConexaoFactory implements IDAO {

	private Connection conn;

	public ItemCronograma getByCodigo(Integer codigo) throws Exception {
		conn = super.getConnection();
		String sqlItemCronograma = "SELECT * FROM item_cronograma WHERE (codigo = ?)";


		try {
			conn.setAutoCommit(false);
			PreparedStatement psItemCronograma = conn.prepareStatement(sqlItemCronograma);
			psItemCronograma.setInt(1, codigo);
			ResultSet rsItemCronograma = psItemCronograma.executeQuery();

			if (rsItemCronograma.next()) {
				ItemCronograma itemCronograma = new ItemCronograma();
				Ministracao ministracao = new Ministracao();
				DAOMinistracao daom = new DAOMinistracao();
				Intervalo intervalo = new Intervalo();
				DAOIntervalo daoi = new DAOIntervalo();
				Cronograma cronograma = new Cronograma();
				DAOCronograma daoc = new DAOCronograma();

				itemCronograma.setCodigoItemCronograma(rsItemCronograma.getInt("codigo"));
				itemCronograma.setDia(rsItemCronograma.getString("dia"));
				itemCronograma.setDescricao(rsItemCronograma.getString("descricao"));
				itemCronograma.setHoraInicio(new java.sql.Date(rsItemCronograma.getTime("hora_inicio").getTime()));
				itemCronograma.setHoraFim(new java.sql.Date(rsItemCronograma.getTime("hora_fim").getTime()));
				ministracao = daom.getByCodigo(rsItemCronograma.getInt("ministracao_codigo"));
				itemCronograma.setMinistracao(ministracao);
				intervalo = daoi.getByCodigo(rsItemCronograma.getInt("intervalo_codigo"));
				itemCronograma.setIntervalo(intervalo);
				cronograma = daoc.getByCodigo(rsItemCronograma.getInt("cronograma_codigo"));
				itemCronograma.setCronograma(cronograma);

				conn.close();

				return itemCronograma;
			} else {
				rsItemCronograma.close();
				conn.close();

				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("- Erro ao obter Item de Cronograma por código!\n" + ex.getMessage());
		}

	}

	@Override
	public void cadastrar(Object param) throws Exception {
		conn = super.getConnection();
		try {
		
		conn.setAutoCommit(false);
		DAOCronograma daoc = new DAOCronograma();
		Cronograma cronograma = new Cronograma();
		RevisaoDeVidas revisaoDeVidas = new RevisaoDeVidas();
		DAORevisao daor = new DAORevisao();
		
		String sqlTemCronograma = "SELECT * FROM cronograma WHERE (revisao_codigo = ?)";
		
		revisaoDeVidas = daor.getByData(((ItemCronograma) param).getCronograma().getRevisaoDeVidas().getDataDeAcontecimento());
		
		PreparedStatement psTemCronograma = conn.prepareStatement(sqlTemCronograma);
		psTemCronograma.setInt(1, revisaoDeVidas.getCodigoRevisaoDeVidas());
		ResultSet rsTemCronograma = psTemCronograma.executeQuery();

		if (!rsTemCronograma.next()) {
			daoc.cadastrar(((ItemCronograma) param).getCronograma());
		}

		PreparedStatement psItemCronograma = conn.prepareStatement(
				"INSERT INTO item_cronograma (descricao, dia, hora_inicio, hora_fim, intervalo_codigo, cronograma_codigo) VALUES (?, ?, ?, ?, ?, ?)");
		
		System.out.println(((ItemCronograma) param).getDescricao());
		psItemCronograma.setString(1, ((ItemCronograma) param).getDescricao());
		psItemCronograma.setString(2, ((ItemCronograma) param).getDia());
		psItemCronograma.setTime(3, new java.sql.Time(((ItemCronograma) param).getHoraInicio().getTime()));
		
		
	    Intervalo intervalo = new Intervalo();
	    intervalo = ((ItemCronograma) param).getIntervalo();
	    GregorianCalendar gc = new GregorianCalendar();  
	    gc.setTime(((ItemCronograma) param).getHoraInicio());
	    System.out.println(intervalo.getMinutos());
	    gc.add(Calendar.MINUTE, intervalo.getMinutos());
	    
	    psItemCronograma.setTime(4, new java.sql.Time(gc.getTimeInMillis()));
		
		psItemCronograma.setInt(5, ((ItemCronograma) param).getIntervalo().getCodigoIntervalo());
		
		System.out.println(revisaoDeVidas.getCodigoRevisaoDeVidas());
		cronograma = daoc.getByRevisao(revisaoDeVidas.getCodigoRevisaoDeVidas());
		System.out.println(cronograma.getCodigoCronograma());
		psItemCronograma.setInt(6, cronograma.getCodigoCronograma());
		psItemCronograma.executeUpdate();

		conn.commit();
		conn.close();
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Item de Cronograma\n" + ex.getMessage());
		}

	}

	public void cadastrarComMinistracao(Object param) throws Exception {
		conn = super.getConnection();
		try {
			conn.setAutoCommit(false);
			DAOCronograma daoc = new DAOCronograma();
			Cronograma cronograma = new Cronograma();
			RevisaoDeVidas revisaoDeVidas = new RevisaoDeVidas();
			DAORevisao daor = new DAORevisao();
			
			String sqlTemCronograma = "SELECT * FROM cronograma WHERE (revisao_codigo = ?)";
			
			revisaoDeVidas = daor.getByData(((ItemCronograma) param).getCronograma().getRevisaoDeVidas().getDataDeAcontecimento());
				
			PreparedStatement psTemCronograma = conn.prepareStatement(sqlTemCronograma);
			psTemCronograma.setInt(1, revisaoDeVidas.getCodigoRevisaoDeVidas());
			ResultSet rsTemCronograma = psTemCronograma.executeQuery();


			if (!rsTemCronograma.next()) {
				daoc.cadastrar(((ItemCronograma) param).getCronograma());
			}

			PreparedStatement psItemCronograma = conn.prepareStatement(
					"INSERT INTO item_cronograma (descricao, dia, hora_inicio, hora_fim, ministracao_codigo, cronograma_codigo) VALUES (?, ?, ?, ?, ?, ?)");

			psItemCronograma.setString(1, ((ItemCronograma) param).getDescricao());
			psItemCronograma.setString(2, ((ItemCronograma) param).getDia());
			psItemCronograma.setTime(3, new java.sql.Time(((ItemCronograma) param).getHoraInicio().getTime()));
			
			
		    Ministracao ministracao = new Ministracao();
		    ministracao = ((ItemCronograma) param).getMinistracao();
		    GregorianCalendar gc = new GregorianCalendar();  
		    gc.setTime(((ItemCronograma) param).getHoraInicio());  
		    gc.add(Calendar.MINUTE, ministracao.getMinutos());
		    
		    psItemCronograma.setTime(4, new java.sql.Time(gc.getTimeInMillis()));
			
			psItemCronograma.setInt(5, ((ItemCronograma) param).getMinistracao().getCodigoMinistracao());
			
			cronograma = daoc.getByRevisao(revisaoDeVidas.getCodigoRevisaoDeVidas());
			psItemCronograma.setInt(6, cronograma.getCodigoCronograma());
			psItemCronograma.executeUpdate();

			conn.commit();
			conn.close();

		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("- Erro ao cadastrar Item de Cronograma\n" + ex.getMessage());
		}

	}
	
	@Override
	public void alterar(Object param) throws Exception {
		conn = super.getConnection();
		conn.setAutoCommit(false);

		try {
			conn.setAutoCommit(false);
			PreparedStatement psItemCronograma = conn
					.prepareStatement("UPDATE item_cronograma SET dia = ?, descricao = ?, hora_inicio = ? "
							+ "hora_fim = ?, intervalo_codigo = ?, ministracao_codigo = ?, cronograma_codigo = ? WHERE (codigo = ?)");

			psItemCronograma.setString(1, ((ItemCronograma) param).getDia());
			psItemCronograma.setString(2, ((ItemCronograma) param).getDescricao());
			psItemCronograma.setTime(3, new java.sql.Time(((ItemCronograma) param).getHoraInicio().getTime()));
			psItemCronograma.setTime(4, new java.sql.Time(((ItemCronograma) param).getHoraFim().getTime()));
			psItemCronograma.setInt(5, ((ItemCronograma) param).getIntervalo().getCodigoIntervalo());
			psItemCronograma.setInt(6, ((ItemCronograma) param).getMinistracao().getCodigoMinistracao());
			psItemCronograma.setInt(7, ((ItemCronograma) param).getCronograma().getCodigoCronograma());
			psItemCronograma.executeUpdate();

			conn.commit();
		} catch (Exception ex) {
			conn.rollback();
			throw new Exception("Erro ao alterar Item de Cronograma\n" + ex.getMessage());
		}

	}

	@Override
	public void deletar(Object param) throws Exception {
		conn = super.getConnection();

		try {
			conn.setAutoCommit(false);
			ItemCronograma itemCronograma = new ItemCronograma();
			DAOItemCronograma dao = new DAOItemCronograma();
			itemCronograma = dao.getByCodigo(((ItemCronograma) param).getCodigoItemCronograma());
			PreparedStatement ps = conn.prepareStatement("DELETE FROM item_cronograma WHERE (codigo = ?)");
			ps.setInt(1, itemCronograma.getCodigoItemCronograma());
			ps.executeUpdate();

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			throw new Exception("- Erro ao deletar Célula\n" + ex.getMessage());
		}

	}

	@Override
	public ArrayList<ItemCronograma> listar() throws Exception {

		conn = super.getConnection();
		ArrayList<ItemCronograma> al = new ArrayList<ItemCronograma>();
		ItemCronograma itemCronograma = new ItemCronograma();

		try {
			String sql = "SELECT * FROM item_cronograma";
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				itemCronograma = getByCodigo(rs.getInt("codigo"));
				al.add(itemCronograma);
			}

		}

		catch (Exception ex) {
			throw new Exception("Erro ao listar itens de cronograma\n" + ex.getMessage());
		}

		return al;
	}

	@Override
	public ArrayList<ItemCronograma> buscar(String valor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void incluirMembroItemCronograma(ItemCronograma itemCronograma) throws Exception {
		conn = super.getConnection();
		conn.setAutoCommit(false);
		try {
			PreparedStatement psItemCronogramaMembro = conn.prepareStatement("INSERT INTO itemCronograma_membro "
					+ "(itemCronograma_codigo, membro_codigo) VALUES (?, (SELECT MAX(codigo) FROM membro))");

			System.out.println(itemCronograma.getCodigoItemCronograma());
			psItemCronogramaMembro.setInt(1, itemCronograma.getCodigoItemCronograma());
			psItemCronogramaMembro.executeUpdate();

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			throw new Exception("Erro ao inserir membro na célula\n" + ex.getMessage());
		}

	}

}
