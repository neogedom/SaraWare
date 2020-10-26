package br.com.saraware.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.saraware.dao.DAOCaixa;
import br.com.saraware.dao.DAOEntradaFinanceira;
import br.com.saraware.domain.EntradaFinanceira;
import br.com.saraware.util.JSFUtil;

@ManagedBean(name = "MBCaixa")
@ViewScoped
public class CaixaBean {
	private Double valorEmCaixa;
	private EntradaFinanceira entradaFinanceira = new EntradaFinanceira();
	
	public EntradaFinanceira getEntradaFinanceira() {
		return entradaFinanceira;
	}
	
	public void setEntradaFinanceira(EntradaFinanceira entradaFinanceira) {
		this.entradaFinanceira = entradaFinanceira;
	}
	
	public Double getValorEmCaixa() {
		return valorEmCaixa;
	}
	
	public void setValorEmCaixa(Double valorEmCaixa) {
		this.valorEmCaixa = valorEmCaixa;
	}
	
	@PostConstruct
	public void prepararPesquisa() {
		try {
			DAOCaixa dao = new DAOCaixa();
			valorEmCaixa = dao.getValor();
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

	public void prepararNovo() {
		entradaFinanceira = new EntradaFinanceira();
		
	}

	public void novo() {
		try {
			DAOEntradaFinanceira dao = new DAOEntradaFinanceira();
			DAOCaixa daoc = new DAOCaixa();
			dao.cadastrar(entradaFinanceira); 
			valorEmCaixa = daoc.getValor();
			
			JSFUtil.adicionarMensagemSucesso("Entrada Financeira salva com sucesso!");
		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}

}
