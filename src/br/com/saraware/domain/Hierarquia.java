package br.com.saraware.domain;

public class Hierarquia {
	private Integer codigoHierarquia;
	private String descricao;
	private Boolean ativo;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getCodigoHierarquia() {
		return codigoHierarquia;
	}

	public void setCodigoHierarquia(Integer codigoHierarquia) {
		this.codigoHierarquia = codigoHierarquia;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

}
