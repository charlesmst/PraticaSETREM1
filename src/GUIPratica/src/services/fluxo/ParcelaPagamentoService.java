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
import model.fluxo.ContaBancaria;
import model.fluxo.ContaCategoria;
import model.fluxo.FormaPagamento;
import model.fluxo.Parcela;
import model.fluxo.ParcelaPagamento;
import services.Service;
import services.ServiceException;

/**
 *
 * @author Charles
 */
public class ParcelaPagamentoService extends Service<ParcelaPagamento> {

    public ParcelaPagamentoService() {
        super(ParcelaPagamento.class);
    }

    private void changeSituation(ParcelaPagamento p) {

    }

    @Override
    public void insert(ParcelaPagamento obj) throws ServiceException {

        super.insert(obj); //To change body of generated methods, choose Tools | Templates.
    }

    public void pagamentoProximasParcelas(int parcelaAtual, double valor, ContaBancaria banco, ContaCategoria categoria, Conta conta, boolean aVista) {
        insereNasProximas(parcelaAtual + 1, valor, banco, categoria, conta, aVista);
    }

    private void insereNasProximas(int parcelaAtual, double valor, ContaBancaria banco, ContaCategoria categoria, Conta conta, boolean aVista) {
        Parcela p = null;
        for (Parcela parcela1 : conta.getParcelas()) {
            if (parcela1.getParcela() == parcelaAtual) {
                p = parcela1;
                break;
            }
        }
        //Se acabaram as parcelas, jora o resto na ultima
        if (p == null) {
            p = conta.getParcelas().stream().max((i1, i2) -> {
                return Integer.compare(i1.getParcela(), i2.getParcela());
            }).get();
            ParcelaPagamento pExtra = new ParcelaPagamento();
            pExtra.setData(new Date());
            pExtra.setContaBancaria(banco);
            pExtra.setParcela(p);
            pExtra.setValor(valor);
            pExtra.setContaCategoria(categoria);
            pExtra.setaVista(aVista);
            p.getPagamentos().add(pExtra);
        } else {
            ParcelaPagamento pExtra = new ParcelaPagamento();
            pExtra.setData(new Date());
            pExtra.setContaBancaria(banco);
            pExtra.setParcela(p);
            pExtra.setaVista(aVista);

            double valorFalta = p.getValor() - new ParcelaService().valorTotalParcela(p);
            if (valorFalta <= 0d) {
                insereNasProximas(parcelaAtual + 1, valor, banco, categoria, conta,aVista);
                return;
            }
            double vPagar = valor;
            if (valor > valorFalta) {
                vPagar = valorFalta;
            }
            double valorRestante = valor - vPagar;

            pExtra.setValor(vPagar);

            pExtra.setContaCategoria(categoria);
            p.getPagamentos().add(pExtra);
            if (valorRestante > 0d) {
                insereNasProximas(parcelaAtual + 1, valorRestante, banco, categoria, conta,aVista);

            }
        }
    }
}
