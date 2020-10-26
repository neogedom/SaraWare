package br.com.saraware.domain;

import java.util.ArrayList;

public class Celula {

	private Integer codigoCelula;
	private String nome;
	private Lider lider = new Lider();
	private Boolean ativo;
	private ArrayList<Membro> membros;

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Lider getLider() {
		return lider;
	}

	public void setLider(Lider lider) {
		this.lider = lider;
	}

	public Integer getCodigoCelula() {
		return codigoCelula;
	}

	public void setCodigoCelula(Integer codigoCelula) {
		this.codigoCelula = codigoCelula;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	public ArrayList<Membro> getMembros() {
		return membros;
	}

	public void setMembros(ArrayList<Membro> membros) {
		this.membros = membros;
	}
}
