package br.com.saraware.bean;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.saraware.dao.DAOPlanejamento;
import br.com.saraware.dao.DAORevisao;
import br.com.saraware.domain.Planejamento;
import br.com.saraware.domain.RevisaoDeVidas;
import br.com.saraware.util.JSFUtil;

@ManagedBean(name = "MBPlanejamento")
@ViewScoped
public class PlanejamentoBean {
	private Planejamento planejamento;
	private Planejamento planejamentoTela;
	private RevisaoDeVidas revisao1;
	private RevisaoDeVidas revisao2;
	private RevisaoDeVidas revisao3;
	private RevisaoDeVidas revisao4;
	private ArrayList<RevisaoDeVidas> revisoes;
	private Date dataAtual = new Date();
	private Date dataFinal;
	private String msg;
	private Object opcaoRadio;
	private Boolean botaoVisivel = false;
	
	
	public RevisaoDeVidas getRevisao1() {
		return revisao1;
	}
	
	public void setRevisao1(RevisaoDeVidas revisao1) {
		this.revisao1 = revisao1;
	}
	
	public RevisaoDeVidas getRevisao2() {
		return revisao2;
	}
	
	public void setRevisao2(RevisaoDeVidas revisao2) {
		this.revisao2 = revisao2;
	}
	
	public RevisaoDeVidas getRevisao3() {
		return revisao3;
	}
	
	public void setRevisao3(RevisaoDeVidas revisao3) {
		this.revisao3 = revisao3;
	}
	
	public RevisaoDeVidas getRevisao4() {
		return revisao4;
	}
	
	
	public Boolean getBotaoVisivel() {
		return botaoVisivel;
	}
	
	public void setBotaoVisivel(Boolean botaoVisivel) {
		this.botaoVisivel = botaoVisivel;
	}

	public Date getDataAtual() {
		return dataAtual;
	}

	public void setDataAtual(Date dataAtual) {
		this.dataAtual = dataAtual;
	}

	public Planejamento getPlanejamento() {
		return planejamento;
	}

	public void setPlanejamento(Planejamento planejamento) {
		this.planejamento = planejamento;
	}

	public ArrayList<RevisaoDeVidas> getRevisoes() {
		return revisoes;
	}

	public void setRevisoes(ArrayList<RevisaoDeVidas> revisoes) {
		this.revisoes = revisoes;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Planejamento getPlanejamentoTela() {
		return planejamentoTela;
	}
	
	public void setPlanejamentoTela(Planejamento planejamentoTela) {
		this.planejamentoTela = planejamentoTela;
	}
	
	public Object getOpcaoRadio() {
		return opcaoRadio;
	}
	
	public void setOpcaoRadio(Object opcaoRadio) {
		this.opcaoRadio = opcaoRadio;
	}
	
	public Date getDataFinal() {
		return dataFinal;
	}
	
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	@PostConstruct
	public void prepararLabel() {
		DAOPlanejamento dao = new DAOPlanejamento();
		dataAtual = new Date();
		Calendar c = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c.setTime(dataAtual);
		c2.set(Calendar.DAY_OF_MONTH, 31);
		c2.set(Calendar.MONTH, Calendar.DECEMBER);
		c2.set(Calendar.YEAR, c.get(c.YEAR));
		dataFinal = c2.getTime();
		try {
			
			planejamentoTela = dao.getByAno(c.get(c.YEAR));
			if (planejamentoTela != null) {
				msg = "As datas para Revisão de Vidas nesse ano são:<br/> "
						+ planejamentoTela.getRevisao1().getDataFormatada() + "<br/> "
						+ planejamentoTela.getRevisao2().getDataFormatada() + "<br/> "
						+ planejamentoTela.getRevisao3().getDataFormatada() + "<br/>"
						+ planejamentoTela.getRevisao4().getDataFormatada();
				
				botaoVisivel = true;
			} else {
				msg = "Não existe Revisão de Vidas planejado para este ano.";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void novo() {
		try {
			DAOPlanejamento dao = new DAOPlanejamento();
			
			planejamento.setAno(dataAtual);
			dao.cadastrar(planejamento, revisao1, revisao2, revisao3, revisao4);

			JSFUtil.adicionarMensagemSucesso("Planejamento salvo com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void prepararNovo() {
		planejamento = new Planejamento();
		revisao1 = new RevisaoDeVidas();
		revisao2 = new RevisaoDeVidas();
		revisao3 = new RevisaoDeVidas();
		revisao4 = new RevisaoDeVidas();
	}
}
