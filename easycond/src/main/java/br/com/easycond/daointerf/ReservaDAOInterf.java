package br.com.easycond.daointerf;

import java.util.List;

import br.com.easycond.model.Reserva;

public interface ReservaDAOInterf {

	public void salvar(Reserva reserva);
	
	public void atualizar(Reserva reserva);
	
	public void excluir(Reserva reserva);
	
	public Reserva carregar(Integer id);
	
	public List<Reserva> listar();
}
