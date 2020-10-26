package br.com.saraware.bean;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.saraware.dao.DAOCelula;
import br.com.saraware.dao.DAOHierarquia;
import br.com.saraware.dao.DAOMembro;
import br.com.saraware.domain.Celula;
import br.com.saraware.domain.Hierarquia;
import br.com.saraware.domain.Membro;
import br.com.saraware.util.JSFUtil;

@ManagedBean(name = "MBMembro")
@ViewScoped
public class MembroBean {
	private Membro membro;
	private Celula celula;
	private ArrayList<Hierarquia> comboHierarquias;
	private ArrayList<Celula> comboCelulas;
	private ArrayList<Membro> itens;
	private ArrayList<Membro> itensFiltrados;

	public Membro getMembro() {
		return membro;
	}

	public void setMembro(Membro membro) {
		this.membro = membro;
	}

	public ArrayList<Membro> getItens() {
		return itens;
	}
	
	public void setItens(ArrayList<Membro> itens) {
		this.itens = itens;
	}
	
	public ArrayList<Membro> getItensFiltrados() {
		return itensFiltrados;
	}
	
	public void setItensFiltrados(ArrayList<Membro> itensFiltrados) {
		this.itensFiltrados = itensFiltrados;
	}
	
	public ArrayList<Hierarquia> getComboLideres() {
		return comboHierarquias;
	}
	
	public void setComboLideres(ArrayList<Hierarquia> comboHierarquias) {
		this.comboHierarquias = comboHierarquias;
	}
	
	public void setComboCelulas(ArrayList<Celula> comboCelulas) {
		this.comboCelulas = comboCelulas;
	}
	
	public ArrayList<Celula> getComboCelulas() {
		return comboCelulas;
	}
	
	public void setComboHierarquias(ArrayList<Hierarquia> comboHierarquias) {
		this.comboHierarquias = comboHierarquias;
	}
	
	public ArrayList<Hierarquia> getComboHierarquias() {
		return comboHierarquias;
	}
	
	public Celula getCelula() {
		return celula;
	}
	
	public void setCelula(Celula celula) {
		this.celula = celula;
	}

	@PostConstruct
	public void prepararPesquisa() {
		try {
			DAOMembro dao = new DAOMembro();
			itens = dao.listar();
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void prepararNovo() {
		membro = new Membro();
		DAOHierarquia daoh = new DAOHierarquia();
		DAOCelula daoc = new DAOCelula();
		try{
		comboHierarquias= daoh.listar();
		comboCelulas= daoc.listar();
		}catch (Exception ex)
		{
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void novo() {
		try {
			DAOMembro dao = new DAOMembro();
			
 			dao.cadastrar(membro);
				
			itens = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Membro salvo com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			DAOMembro dao = new DAOMembro();
			dao.deletar(membro);
			
			itens = dao.listar();
			
			JSFUtil.adicionarMensagemSucesso("Membro removido com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void prepararEditar(){
		DAOHierarquia daoh = new DAOHierarquia();
		DAOCelula daoc = new DAOCelula();
		
		try{
			comboHierarquias = daoh.listar();
			comboCelulas = daoc.listar();
		}catch (Exception ex)
		{
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void editar() {
		try {
			DAOMembro dao = new DAOMembro();
			dao.alterar(membro);
			
			itens = dao.listar();
			
			JSFUtil.adicionarMensagemSucesso("Membro alterado com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void editarMembroCelula() {
		try {
			DAOCelula dao = new DAOCelula();
			dao.alterarMembroCelula(celula, membro);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

}
