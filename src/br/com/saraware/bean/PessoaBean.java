package br.com.saraware.bean;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.saraware.dao.DAOPessoa;
import br.com.saraware.domain.Pessoa;
import br.com.saraware.util.JSFUtil;

@ManagedBean(name = "MBPessoa")
@ViewScoped
public class PessoaBean {
	private Pessoa pessoa;
	private ArrayList<Pessoa> itens;
	private ArrayList<Pessoa> itensFiltrados;
	private ArrayList<Pessoa> comboPessoas;

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public ArrayList<Pessoa> getItens() {
		return itens;
	}

	public void setItens(ArrayList<Pessoa> itens) {
		this.itens = itens;
	}

	public ArrayList<Pessoa> getItensFiltrados() {
		return itensFiltrados;
	}

	public void setItensFiltrados(ArrayList<Pessoa> itensFiltrados) {
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
			DAOPessoa dao = new DAOPessoa();
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
