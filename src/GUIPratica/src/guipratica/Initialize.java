/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guipratica;

import model.fluxo.ContaCategoria;
import model.fluxo.FormaPagamento;
import services.fluxo.ContaCategoriaService;
import services.fluxo.FormaPagamentoService;
import utils.Parametros;

/**
 *
 * @author charles
 */
public class Initialize {

    public static void init() throws Exception{
        validaParametros();
    }

    private static void validaParametros() throws Exception{
        ContaCategoriaService serviceCategoria = new ContaCategoriaService();
        FormaPagamentoService serviceFormaPagamento = new FormaPagamentoService();

        Parametros.getInstance().validaParametro("categoria_movimento_saida", (value) -> {
            ContaCategoria c = serviceCategoria.findById(Integer.parseInt(value));
            return c != null && c.getTipo() == ContaCategoria.TipoCategoria.saida;
        }, (value) -> {
            ContaCategoria cat = new ContaCategoria();
            cat.setNome("MOVIMENTAÇÃO SAIDA");
            cat.setTipo(ContaCategoria.TipoCategoria.saida);
            cat.setAtivo(true);
            serviceCategoria.insert(cat);
            return cat.getId() + "";
        });

        Parametros.getInstance().validaParametro("categoria_movimento_entrada", (value) -> {
            ContaCategoria c = serviceCategoria.findById(Integer.parseInt(value));
            return c != null && c.getTipo() == ContaCategoria.TipoCategoria.entrada;
        }, (value) -> {
            ContaCategoria cat = new ContaCategoria();
            cat.setNome("MOVIMENTAÇÃO ENTRADA");
            cat.setTipo(ContaCategoria.TipoCategoria.entrada);
            cat.setAtivo(true);
            serviceCategoria.insert(cat);
            return cat.getId() + "";
        });

        //forma_pagamento_a_vista
        Parametros.getInstance().validaParametro("forma_pagamento_a_vista", (value) -> {
            return serviceFormaPagamento.findById(Integer.parseInt(value)) != null;
        }, (value) -> {
            FormaPagamento cat = new FormaPagamento();
            cat.setNome("À VISTA");
            cat.setAtivo(true);
            serviceFormaPagamento.insert(cat);
            return cat.getId() + "";
        });
    }
}
