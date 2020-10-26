package br.com.saraware.domain;

import java.util.ArrayList;

public class Meta {
	private Integer codigoMeta;
	private Celula celula = new Celula();
	private ArrayList<ItemDoacao> itemsDoacaoMeta = new ArrayList<ItemDoacao>();
	private RevisaoDeVidas revisaoDeVidas = new RevisaoDeVidas();
	
	public Integer getCodigoMeta() {
		return codigoMeta;
	}
	public void setCodigoMeta(Integer codigoMeta) {
		this.codigoMeta = codigoMeta;
	}
	public Celula getCelula() {
		return celula;
	}
	public void setCelula(Celula celula) {
		this.celula = celula;
	}
	public ArrayList<ItemDoacao> getItemsDoacaoMeta() {
		return itemsDoacaoMeta;
	}
	public void setItemsDoacaoMeta(ArrayList<ItemDoacao> itemsDoacaoMeta) {
		this.itemsDoacaoMeta = itemsDoacaoMeta;
	}
	public RevisaoDeVidas getRevisaoDeVidas() {
		return revisaoDeVidas;
	}
	public void setRevisaoDeVidas(RevisaoDeVidas revisaoDeVidas) {
		this.revisaoDeVidas = revisaoDeVidas;
	}

}
