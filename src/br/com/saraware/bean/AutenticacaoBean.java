package br.com.saraware.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.saraware.dao.DAOUsuario;
import br.com.saraware.domain.Usuario;
import br.com.saraware.util.JSFUtil;

@ManagedBean(name = "MBAutenticacao")
@SessionScoped
public class AutenticacaoBean {
	private Usuario usuarioLogado;

	public Usuario getUsuarioLogado() {
		if (usuarioLogado == null) {
			usuarioLogado = new Usuario();
		}
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public void entrar() {
		try {
			DAOUsuario dao = new DAOUsuario();
			usuarioLogado = dao.autenticar(usuarioLogado.getUsuario(), usuarioLogado.getSenha());
			if (usuarioLogado == null) {
				JSFUtil.adicionarMensagemErro("Usuário e/ou Senha inválidos");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("faces/pages/principal.xhtml");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JSFUtil.adicionarMensagemErro(ex.getMessage());
		}
	}
	
	public String sair(){
		usuarioLogado = null;
		return "/pages/autenticacao.xhtml?faces-redirect=true";
	}

} 	
