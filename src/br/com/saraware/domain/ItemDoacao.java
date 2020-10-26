package br.com.saraware.domain;

public class ItemDoacao extends Item {
	private Integer codigoItemDoacao;
	private Integer quantidadeMeta;
	private Integer quantidadeAlcancada = null;
	private Boolean estoque;
	private Boolean checked = false;

	public Integer getCodigoItemDoacao() {
		return codigoItemDoacao;
	}

	public void setCodigoItemDoacao(Integer codigoItemDoacao) {
		this.codigoItemDoacao = codigoItemDoacao;
	}

	public Integer getQuantidadeAlcancada() {
		return quantidadeAlcancada;
	}

	public void setQuantidadeAlcancada(Integer quantidadeAlcancada) {
		this.quantidadeAlcancada = quantidadeAlcancada;
	}

	public Integer getQuantidadeMeta() {
		return quantidadeMeta;
	}

	public void setQuantidadeMeta(Integer quantidadeMeta) {
		this.quantidadeMeta = quantidadeMeta;
	}

	public Boolean getEstoque() {
		return estoque;
	}

	public void setEstoque(Boolean estoque) {
		this.estoque = estoque;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
}
