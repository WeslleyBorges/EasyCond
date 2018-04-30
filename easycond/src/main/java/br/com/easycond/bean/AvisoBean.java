package br.com.easycond.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.easycond.model.Aviso;
import br.com.easycond.rn.AvisoRN;

@ManagedBean(name = "avisoBean")
@RequestScoped
public class AvisoBean {

	private Aviso aviso = new Aviso();
	private List<Aviso> lista;
	
	@PostConstruct
	public String novo() {
		this.aviso = new Aviso();
		return "/restrito/aviso/cadastrar";
	}
	
	public String salvar() {
		
		AvisoRN avisoRN = new AvisoRN();
		avisoRN.salvar(this.aviso);
		
		return "/restrito/aviso/lista";
	}
	
	public String editar() {
		return "/restrito/aviso/cadastrar";
	}
	
	public String excluir() {
		AvisoRN avisoRN = new AvisoRN();
		avisoRN.excluir(this.aviso);
		this.lista = null;
		return null;
	}

	public Aviso getAviso() {
		return aviso;
	}

	public void setAviso(Aviso aviso) {
		this.aviso = aviso;
	}
	
	public List<Aviso> getLista() {
		if (this.lista == null) {
			AvisoRN avisoRN = new AvisoRN();
			this.lista = avisoRN.listar();
		}
		return this.lista;
	}
	
}
