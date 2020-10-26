package br.com.saraware.domain;

import java.util.ArrayList;

public class Tarefa {
	private Integer codigoTarefa;
	private String descricaoTarefa;
	private ItemCronograma itemCronograma = new ItemCronograma();
	private ArrayList<Encontreiro> encontreiros;
	private GrupoTrabalho grupoTrabalho = new GrupoTrabalho();
	
	public GrupoTrabalho getGrupoTrabalho() {
		return grupoTrabalho;
	}
	
	public void setGrupoTrabalho(GrupoTrabalho grupoTrabalho) {
		this.grupoTrabalho = grupoTrabalho;
	}
	
	public ArrayList<Encontreiro> getEncontreiros() {
		return encontreiros;
	}
	
	public void setEncontreiros(ArrayList<Encontreiro> encontreiros) {
		this.encontreiros = encontreiros;
	}
	
	public ItemCronograma getItemCronograma() {
		return itemCronograma;
	}
	
	public void setItemCronograma(ItemCronograma itemCronograma) {
		this.itemCronograma = itemCronograma;
	}
	
	public Integer getCodigoTarefa() {
		return codigoTarefa;
	}

	public void setCodigoTarefa(Integer codigoTarefa) {
		this.codigoTarefa = codigoTarefa;
	}

	public String getDescricaoTarefa() {
		return descricaoTarefa;
	}

	public void setDescricaoTarefa(String descricaoTarefa) {
		this.descricaoTarefa = descricaoTarefa;
	}

}
