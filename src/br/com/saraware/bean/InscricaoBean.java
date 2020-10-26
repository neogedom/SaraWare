package br.com.saraware.bean;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.saraware.dao.DAOInscricao;
import br.com.saraware.dao.DAOPessoa;
import br.com.saraware.domain.Inscricao;
import br.com.saraware.domain.Pessoa;
import br.com.saraware.util.JSFUtil;

@ManagedBean(name = "MBInscricao")
@ViewScoped
public class InscricaoBean {
	private Inscricao inscricao;
	private ArrayList<Inscricao> itens;
	private ArrayList<Inscricao> itensFiltrados;
	private ArrayList<Pessoa> comboPessoas;

	public Inscricao getInscricao() {
		return inscricao;
	}
	
	public void setInscricao(Inscricao inscricao) {
		this.inscricao = inscricao;
	}
	
	public ArrayList<Inscricao> getItens() {
		return itens;
	}
	
	public void setItens(ArrayList<Inscricao> itens) {
		this.itens = itens;
	}
	
	public ArrayList<Inscricao> getItensFiltrados() {
		return itensFiltrados;
	}
	
	public void setItensFiltrados(ArrayList<Inscricao> itensFiltrados) {
		this.itensFiltrados = itensFiltrados;
	}
	
	public ArrayList<Pessoa> getComboPessoas() {
		return comboPessoas;
	}
	
	public void setComboPessoas(ArrayList<Pessoa> comboPessoas) {
		this.comboPessoas = comboPessoas;
	}
	
	
	@PostConstruct
	public void prepararPesquisa() {
		try {
			DAOInscricao dao = new DAOInscricao();
			itens = dao.listar();
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public void prepararNovo() {
		DAOPessoa dao = new DAOPessoa();
		try {
			comboPessoas = dao.listar();
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
}
