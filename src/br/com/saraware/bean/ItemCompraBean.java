package br.com.saraware.bean;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.saraware.dao.DAOItemCompra;
import br.com.saraware.domain.ItemCompra;
import br.com.saraware.util.JSFUtil;

@ManagedBean(name = "MBItemCompra")
@ViewScoped
public class ItemCompraBean {
	private ItemCompra itemCompra;
	private ArrayList<ItemCompra> itens;
	private ArrayList<ItemCompra> itensFiltrados;

	public ItemCompra getProduto() {
		return itemCompra;
	}

	public void setProduto(ItemCompra itemCompra) {
		this.itemCompra = itemCompra;
	}

	public ArrayList<ItemCompra> getItens() {
		return itens;
	}
	
	public void setItens(ArrayList<ItemCompra> itens) {
		this.itens = itens;
	}
	
	public ArrayList<ItemCompra> getItensFiltrados() {
		return itensFiltrados;
	}
	
	public void setItensFiltrados(ArrayList<ItemCompra> itensFiltrados) {
		this.itensFiltrados = itensFiltrados;
	}

	@PostConstruct
	public void prepararPesquisa() {
		try {
			DAOItemCompra dao = new DAOItemCompra();
			itens = dao.listar();
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void prepararNovo() {
		itemCompra = new ItemCompra();
	}

	public void novo() {
		try {
			DAOItemCompra dao = new DAOItemCompra();
			dao.cadastrar(itemCompra);
			
			itens = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Item de Compra salvo com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			DAOItemCompra dao = new DAOItemCompra();
			dao.deletar(itemCompra);
			
			itens = dao.listar();
			
			JSFUtil.adicionarMensagemSucesso("Item de Compra removido com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	
	public void editar() {
		try {
			DAOItemCompra dao = new DAOItemCompra();
			dao.alterar(itemCompra);
			
			itens = dao.listar();
			
			JSFUtil.adicionarMensagemSucesso("Item de Compra alterado com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

}
