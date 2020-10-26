package br.com.saraware.domain;

public class Inscricao {
	private Integer codigoInscricao;
	private Pessoa pessoa = new Pessoa();
	private Receita receita = new Receita();
	private Boolean ativo;
	
	public Integer getCodigoInscricao() {
		return codigoInscricao;
	}
	
	public void setCodigoInscricao(Integer codigoInscricao) {
		this.codigoInscricao = codigoInscricao;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public Receita getReceita() {
		return receita;
	}
	
	public void setReceita(Receita receita) {
		this.receita = receita;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

}
