package br.com.saraware.domain;

import java.util.ArrayList;

public class GrupoTrabalho {
	private Integer codigoGrupoTabalho;
	private String descricao;
	private Encontreiro coordenador = new Encontreiro();
	private ArrayList<Encontreiro> integrantes = new ArrayList<>();
	public Integer getCodigoGrupoTabalho() {
		return codigoGrupoTabalho;
	}
	public void setCodigoGrupoTabalho(Integer codigoGrupoTabalho) {
		this.codigoGrupoTabalho = codigoGrupoTabalho;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Encontreiro getCoordenador() {
		return coordenador;
	}
	public void setCoordenador(Encontreiro coordenador) {
		this.coordenador = coordenador;
	}
	public ArrayList<Encontreiro> getIntegrantes() {
		return integrantes;
	}
	public void setIntegrantes(ArrayList<Encontreiro> integrantes) {
		this.integrantes = integrantes;
	}
	
}
