/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.fluxo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.fluxo.Conta;
import model.fluxo.FormaPagamento;
import model.fluxo.Parcela;
import model.fluxo.ParcelaPagamento;
import services.Service;
import services.ServiceException;

/**
 *
 * @author Charles
 */
public class ParcelaService extends Service<Parcela> {

    public ParcelaService() {
        super(Parcela.class);
    }

    /**
     *
     * @param conta
     * @param parcelas
     * @param valor
     * @param dataPrimeiroPagamento
     * @param fieldIncrementar Deve ser um campo do Calendar ex Calendar.DATE
     * incrementa o dia
     * @param incrementar Quanto deve ser incrementado
     * @return
     */
    public void gerarParcelas(Conta conta, int parcelas, double valor, Date dataPrimeiroPagamento, int fieldIncrementar, int incrementar) {

        if (conta == null) {
            throw new IllegalArgumentException("conta");
        }
        double soma = 0;
        if (conta.getParcelas() != null) {
            for (Parcela parcela : conta.getParcelas()) {
                if (parcela.getPagamentos() != null) {
                    for (ParcelaPagamento pagamento : parcela.getPagamentos()) {
                        soma += pagamento.getValor();
                    }
                }
            }
        }
        if (soma > 0) {
            throw new IllegalArgumentException("Conta não deve possuir pagamentos");
        }
        Calendar c = Calendar.getInstance();
        List<Parcela> l = new ArrayList<>();
        if (parcelas == 0) {
            conta.setParcelas(l);
            return;
        }

        double valorParcela = valor / parcelas;
        double valorPrimeiraAcrescentar = 0d;
        for (int i = 1;
                i <= parcelas;
                i++) {
            Parcela p = new Parcela();
            p.setDataLancamento(c.getTime());
            p.setParcela(i);
//            p.setValor(valor);

            double valorParcelaC = valorParcela + conta.getFormaPagamento().getAcrescimo();
            double v = Math.floor(valorParcelaC * 100) / 100;
            valorPrimeiraAcrescentar += valorParcelaC - v;
            p.setValor(v);
            l.add(p);
            rollCalendar(c, fieldIncrementar, incrementar);
        }
//        Optional<Parcela> p  = l.stream().findFirst();
//        p.get().setValor(p.get().getValor()+ valorPrimeiraAcrescentar);
        l.get(0).setValor(l.get(0).getValor() + valorPrimeiraAcrescentar);

        conta.setParcelas(l);
    }

    private List rollTo = Arrays.asList(Calendar.DATE, Calendar.MONTH, Calendar.YEAR);

    private void rollCalendar(Calendar c, int fieldIncrementar, int quant) {
        Calendar was = (Calendar) c.clone();
        c.roll(fieldIncrementar, quant);

        //Muda outro
        int index = rollTo.indexOf(fieldIncrementar);

        while (was.get((int)rollTo.get(index)) >= c.get((int)rollTo.get(index))) {


            if (index < rollTo.size() - 1) {
                c.roll((Integer) rollTo.get(index + 1), 1);
            }
            index +=1;
        }
    }

}
