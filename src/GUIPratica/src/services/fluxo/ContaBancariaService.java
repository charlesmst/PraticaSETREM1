/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.fluxo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jdk.nashorn.internal.objects.Global;
import model.Pessoa;
import model.fluxo.Conta;
import model.fluxo.ContaBancaria;
import model.fluxo.ContaCategoria;
import model.fluxo.FormaPagamento;
import model.fluxo.Parcela;
import model.fluxo.ParcelaPagamento;
import services.Service;
import services.ServiceException;
import utils.Globals;
import utils.Parametros;

/**
 *
 * @author Charles
 */
public class ContaBancariaService extends Service<ContaBancaria> {

    @Override
    public void delete(Serializable key) throws ServiceException {
        //Faz update pra inativo
        ContaBancaria c = findById(key);
        c.setAtivo(false);

        update(c); //To change body of generated methods, choose Tools | Templates.
    }

    public ContaBancariaService() {
        super(ContaBancaria.class);
    }

    @Override
    public void update(ContaBancaria obj) throws ServiceException {
        obj.setNome(obj.getNome().toUpperCase());
        super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(ContaBancaria obj) throws ServiceException {
        obj.setNome(obj.getNome().toUpperCase());
        super.insert(obj); //To change body of generated methods, choose Tools | Templates.
    }

    public List<ContaBancaria> findAtivos() {
        return findBy("ativo", true);
    }

    public void movimentacao(ContaBancaria contaOrigem, ContaBancaria contaDestino, double valor) {
        int categoria_entrada = Integer.parseInt(Parametros.getInstance().getValue("categoria_movimento_entrada"));
        int categoria_saida = Integer.parseInt(Parametros.getInstance().getValue("categoria_movimento_saida"));
        int pagamentoC = Integer.parseInt(Parametros.getInstance().getValue("forma_pagamento_a_vista"));

        Conta contaSaida = new Conta();
        contaSaida.setTipo(Conta.ContaTipo.movimentacao);

        contaSaida.setFormaPagamento(new FormaPagamento(pagamentoC));
        contaSaida.setPessoa(new Pessoa(Globals.idUsuarioOn));
        contaSaida.setCategoria(new ContaCategoria(categoria_saida));
        contaSaida.setNotaFiscal("");
        contaSaida.setDescricao("SAIDA DA MOVIMENTAÇÃO DE " + contaOrigem.getNome() + " PARA A CONTA " + contaDestino.getNome());
        Parcela p = new Parcela();
        p.setParcela(1);
        p.setConta(contaSaida);
        p.setDataLancamento(new Date());
        p.setDataQuitado(new Date());
        p.setFechado(true);
        p.setValor(valor);
        contaSaida.getParcelas().add(p);

        ParcelaPagamento pagamento = new ParcelaPagamento();
        pagamento.setContaBancaria(contaOrigem);
        pagamento.setContaCategoria(new ContaCategoria(categoria_saida));
        pagamento.setData(new Date());
        pagamento.setValor(valor);

        p.getPagamentos().add(pagamento);

        Conta contaEntrada = new Conta();
        contaEntrada.setTipo(Conta.ContaTipo.movimentacao);

        contaEntrada.setFormaPagamento(new FormaPagamento(pagamentoC));
        contaEntrada.setPessoa(new Pessoa(Globals.idUsuarioOn));
        contaEntrada.setCategoria(new ContaCategoria(categoria_entrada));
        contaEntrada.setNotaFiscal("");
        contaEntrada.setDescricao("ENTRADA DA MOVIMENTAÇÃO DE " + contaDestino.getNome() + " PARA O CONTA " + contaDestino.getNome());
        Parcela pe = new Parcela();

        pe.setParcela(1);
        pe.setConta(contaEntrada);
        pe.setDataLancamento(new Date());
        pe.setDataQuitado(new Date());
        pe.setFechado(true);
        pe.setValor(valor);
        contaEntrada.getParcelas().add(pe);

        pagamento = new ParcelaPagamento();
        pagamento.setContaBancaria(contaDestino);
        pagamento.setContaCategoria(new ContaCategoria(categoria_entrada));
        pagamento.setData(new Date());
        pagamento.setValor(valor);

        pe.getPagamentos().add(pagamento);

        new ContaService().insert(contaSaida, contaEntrada);
    }
}
