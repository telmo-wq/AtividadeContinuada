package br.edu.cs.poo.ac.bolsa.negocio;

import br.edu.cs.poo.ac.bolsa.entidade.*;
import br.edu.cs.poo.ac.bolsa.dao.DAO;
import br.edu.cs.poo.ac.bolsa.util.ExcecaoNegocio;
import br.edu.cs.poo.ac.bolsa.util.ExcecaoOobjetoNaoExistente;
import br.edu.cs.poo.ac.bolsa.util.MensagensValidacao;
import br.edu.cs.poo.ac.bolsa.util.ExcecaoObjetoJaExistente;

import java.io.ObjectInputFilter;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TituloMediator {
    private static TituloMediator instancia;

    public static TituloMediator getInstancia() {
        if (instancia == null){
            instancia = new TituloMediator();
        }

        return instancia;
    }

    private DAO<Titulo> daoTitulo;
    private AtivoMediator ativoMediator;
    private InvestidorMediator investidorMediator;

    private TituloMediator() {
        daoTitulo = new DAO<>(Titulo.class);
        ativoMediator = new AtivoMediator();
        investidorMediator = new InvestidorMediator();
    }

    public void incluir(DadosTitulo dados) throws ExcecaoNegocio{
        MensagensValidacao msgs = new MensagensValidacao();

        if (dados.getCpfOuCnpj() == null || dados.getCpfOuCnpj().isBlank()){
            msgs.adicionar("CPF/CNPJ inválido");
        }
        if (dados.getCodigoAtivo() <= 0){
            msgs.adicionar("Código do ativo inválido");
        }
        if (dados.getValorInvestido() == null){
            msgs.adicionar("Valor investido não pode ser nulo");
        }
        if (dados.getTaxaDiaria() == null){
            msgs.adicionar("Taxa diária não pode ser nula");
        }

        if (!msgs.estaVazio()){
            throw new ExcecaoNegocio(msgs);
        }

        Ativo ativo = ativoMediator.buscar(dados.getCodigoAtivo());
        if (ativo == null){
            msgs.adicionar("Ativo não encontrado");
        }

        Investidor investidor = investidorMediator.buscarInvestidor(dados.getCpfOuCnpj());
        if (investidor == null){
            msgs.adicionar("Investidor não encontrado");
        }

        if (!msgs.estaVazio()){
            throw new ExcecaoNegocio(msgs);
        }

        if (ativo != null && investidor != null){
            if (dados.getValorInvestido().compareTo(BigDecimal.valueOf(ativo.getValorMinimoAplicacao())) < 0) {
                msgs.adicionar("Valor investido fora da faixa permitida");
            }

            if (dados.getValorInvestido().compareTo(BigDecimal.valueOf(ativo.getValorMaximoAplicacao())) > 0) {
                msgs.adicionar("Valor investido fora da faixa permitida");
            }

            double taxaDiaria = dados.getTaxaDiaria().doubleValue();
            double taxaMensal = 100 * (Math.pow(1 + taxaDiaria/100, 30) - 1);

            if (taxaMensal < ativo.getTaxaMensalMinima()){
                msgs.adicionar("Taxa mensal abaixo do mínimo.");
            }

            if (taxaMensal > ativo.getTaxaMensalMaxima()){
                msgs.adicionar("Taxa mensal acima do máximo.");
            }

            FaixaRenda faixaInvestidor = null;
            for (FaixaRenda faixa : FaixaRenda.values()){
                if (investidor.getEntradaFinanceira().compareTo(BigDecimal.valueOf(faixa.getValorInicial())) >= 0 &&
                        investidor.getEntradaFinanceira().compareTo(BigDecimal.valueOf(faixa.getValorFinal())) <= 0){
                    faixaInvestidor = faixa;
                }
            }

            if (faixaInvestidor != null && faixaInvestidor.getCodigo() < ativo.getFaixaMinimaPermitida().getCodigo()){
                msgs.adicionar("Faixa de renda insuficiente.");
            }

            if (msgs.estaVazio()){
                Titulo titulo = new Titulo(investidor, ativo,dados.getValorInvestido(),
                        dados.getValorInvestido(), dados.getTaxaDiaria(), LocalDate.now(),
                        LocalDate.now().plusMonths(ativo.getPrazoEmMeses()),null,
                        StatusTitulo.ATIVO);
                try {
                    daoTitulo.incluir(titulo);
                }catch (ExcecaoObjetoJaExistente e){
                    throw new ExcecaoNegocio(msgs);
                }
            }else {
                throw new ExcecaoNegocio(msgs);
            }
        }


    }

    public void cancelarTitulo(String numero) throws ExcecaoNegocio{
        MensagensValidacao msgs = new MensagensValidacao();
        Titulo titulo = daoTitulo.buscar(numero);

        if (titulo == null){
            msgs.adicionar("Título não encontrado");
            throw new ExcecaoNegocio(msgs);
        }
        if (titulo.getStatus() == StatusTitulo.VENCIDO ||
                titulo.getStatus() == StatusTitulo.CANCELADO) {
            msgs.adicionar("Título não pode ser cancelado");
            throw new ExcecaoNegocio(msgs);
        }

        titulo.setStatus(StatusTitulo.CANCELADO);
        try {
            daoTitulo.alterar(titulo);
        }catch (ExcecaoOobjetoNaoExistente e){
            msgs.adicionar("Erro ao alterar titulo");
            throw new ExcecaoNegocio(msgs);
        }


        Investidor investidor = investidorMediator.buscarInvestidor(titulo.getInvestidor().getIdentificador());
        BigDecimal debito = titulo.getValorAtual().multiply(BigDecimal.valueOf(0.07));
        investidor.debitarBonus(debito);
        investidorMediator.alterarInvestidor(investidor);

    }

    public void processarRendimentos(){
        Titulo[] titulos = daoTitulo.buscarTodos();

        if (titulos == null){
            return;
        }

        for (Titulo titulo: titulos){
            if (titulo.render()){
                BigDecimal bonus = (titulo.getValorAtual().subtract(titulo.getValorInvestido())).multiply(BigDecimal.valueOf(0.0001));

                Investidor investidor = investidorMediator.buscarInvestidor(titulo.getInvestidor().getIdentificador());
                investidor.creditarBonus(bonus);
                investidorMediator.alterarInvestidor(investidor);
            }

            if (!titulo.getDataVencimento().isAfter(LocalDate.now())){
                titulo.setStatus(StatusTitulo.VENCIDO);
            }
            daoTitulo.alterar(titulo);
        }
    }
}
