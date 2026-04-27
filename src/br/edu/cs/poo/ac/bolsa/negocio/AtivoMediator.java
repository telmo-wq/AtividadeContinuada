package br.edu.cs.poo.ac.bolsa.negocio;

import br.edu.cs.poo.ac.bolsa.dao.*;

import br.edu.cs.poo.ac.bolsa.entidade.Ativo;
import br.edu.cs.poo.ac.bolsa.util.MensagensValidacao;

public class AtivoMediator {
	private DAOAtivo dao = new DAOAtivo();
	
	private MensagensValidacao validar(Ativo ativo) {
		MensagensValidacao msgs = new MensagensValidacao();
		
		if(ativo.getCodigo() <= 0) {
			msgs.adicionar("Código deve ser maior que zero.");
		}
		
		if (ativo.getDescricao().isBlank()){
			msgs.adicionar("Descrição é obrigatória.");
		}
		
		if (ativo.getValorMinimoAplicacao() <= 0 ||
				ativo.getValorMinimoAplicacao() > ativo.getValorMaximoAplicacao()) {
			msgs.adicionar("Valor mínimo deve ser igual ou menor que o valor máximo");
		}
		
		if(ativo.getValorMaximoAplicacao() <= 0 || 
				ativo.getValorMaximoAplicacao() < ativo.getValorMinimoAplicacao()) {
			msgs.adicionar("O valor máximo deve ser maior que o valor mínimo.");
		}
		
		if (ativo.getTaxaMensalMinima() <= 0 || 
				ativo.getTaxaMensalMinima() > ativo.getTaxaMensalMaxima()) {
			msgs.adicionar("Taxa mínima deve ser menor ou igual à taxa máxima.");
		}
		
		if (ativo.getTaxaMensalMaxima() <= 0 || 
				ativo.getTaxaMensalMaxima() < ativo.getTaxaMensalMinima()) {
			msgs.adicionar("Taxa máxima deve ser maior que a taxa mínima.");
		}
		
		if (ativo.getFaixaMinimaPermitida() == null) {
			msgs.adicionar("Faixa mínima permitida é obrigatório.");
		}
		
		if (ativo.getPrazoEmMeses() <= 0) {
			msgs.adicionar("Prazo deve ser maior que zero.");
		}
		
		return msgs;
	}
}
