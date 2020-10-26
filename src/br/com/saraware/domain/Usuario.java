package br.com.saraware.domain;

public class Usuario {
	private Integer codigoUsuario;
	private String usuario;
	private String senha;
	private Boolean ativo;
	
	public Integer getCodigoUsuario() {
		return codigoUsuario;
	}
	
	public void setCodigoUsuario(Integer codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	

}
