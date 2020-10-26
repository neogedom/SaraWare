package br.com.saraware.domain;

public class Ministracao {

	private Integer codigoMinistracao;
	private String titulo;
	private Encontreiro ministrante = new Encontreiro();
	private Integer minutos;
	private Boolean ativo;

	public Integer getMinutos() {
		return minutos;
	}
	
	public void setMinutos(Integer minutos) {
		this.minutos = minutos;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public Integer getCodigoMinistracao() {
		return codigoMinistracao;
	}

	public void setCodigoMinistracao(Integer codigoMinistracao) {
		this.codigoMinistracao = codigoMinistracao;
	}

	public Encontreiro getMinistrante() {
		return ministrante;
	}

	public void setMinistrante(Encontreiro ministrante) {
		this.ministrante = ministrante;
	}


	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
}