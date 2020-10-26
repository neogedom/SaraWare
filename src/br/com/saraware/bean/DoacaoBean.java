package br.com.saraware.bean;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.saraware.dao.DAOItem;
import br.com.saraware.domain.Item;
import br.com.saraware.domain.ItemCompra;
import br.com.saraware.util.JSFUtil;

@ManagedBean(name = "MBProduto")
@ViewScoped
public class DoacaoBean {
	private Item produto;
	private ArrayList<Item> itens;
	private ArrayList<Item> itensFiltrados;
	private Double valorCompra;
	private Integer quantidadeCompra;
	private Boolean checkBoxMarcado;
	
	public Boolean getCheckBoxMarcado() {
		return checkBoxMarcado;
	}
	
	public void setCheckBoxMarcado(Boolean checkBoxMarcado) {
		this.checkBoxMarcado = checkBoxMarcado;
	}
	
	public Integer getQuantidadeCompra() {
		return quantidadeCompra;
	}
	
	public void setQuantidadeCompra(Integer quantidadeCompra) {
		this.quantidadeCompra = quantidadeCompra;
	}
	
	public Double getValorCompra() {
		return valorCompra;
	}
	
	public void setValorCompra(Double valorCompra) {
		this.valorCompra = valorCompra;
	}
	

	public Item getProduto() {
		return produto;
	}

	public void setProduto(Item produto) {
		this.produto = produto;
	}

	public ArrayList<Item> getItens() {
		return itens;
	}
	
	public void setItens(ArrayList<Item> itens) {
		this.itens = itens;
	}
	
	public ArrayList<Item> getItensFiltrados() {
		return itensFiltrados;
	}
	
	public void setItensFiltrados(ArrayList<Item> itensFiltrados) {
		this.itensFiltrados = itensFiltrados;
	}

	@PostConstruct
	public void prepararPesquisa() {
		try {
			DAOItem dao = new DAOItem();
			itens = dao.listar();
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void prepararNovo() {
		produto = new Item();
	}

	public void novo() {
		try {
			DAOItem dao = new DAOItem();
			dao.cadastrar(produto);
			
			itens = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Produto salvo com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			DAOItem dao = new DAOItem();
			dao.deletar(produto);
			
			itens = dao.listar();
			
			JSFUtil.adicionarMensagemSucesso("Produto removido com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	
	public void editar() {
		try {
			DAOItem dao = new DAOItem();
			dao.alterar(produto);
			
			itens = dao.listar();
			
			JSFUtil.adicionarMensagemSucesso("Produto alterado com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void comprar() {
		try {
			DAOItem dao = new DAOItem();
			if (checkBoxMarcado)
			{
				dao.doar(produto, quantidadeCompra);
			}
			else {
				
				
				dao.comprar(produto, valorCompra, quantidadeCompra);
			}
			
			itens = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Produto salvo com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

}
