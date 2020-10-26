package br.com.saraware.domain;
import java.util.Date;

public class Planejamento {
	private Integer codigoPlanejamento;
	private Date ano;
	private RevisaoDeVidas revisao1 = new RevisaoDeVidas();
	private RevisaoDeVidas revisao2= new RevisaoDeVidas();
	private RevisaoDeVidas revisao3= new RevisaoDeVidas();
	private RevisaoDeVidas revisao4= new RevisaoDeVidas();
	private Boolean ativo;
	
 	
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

	public void setRevisao4(RevisaoDeVidas revisao4) {
		this.revisao4 = revisao4;
	}
	public void setAno(Date ano) {
		this.ano = ano;
	}

	public Date getAno() {
		return ano;
	}
	
	public void setCodigoPlanejamento(Integer codigoPlanejamento) {
		this.codigoPlanejamento = codigoPlanejamento;
	}
	
	public Integer getCodigoPlanejamento() {
		return codigoPlanejamento;
	}

	public Boolean getAtivo() {
		return ativo;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
}
