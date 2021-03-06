/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.queryresults;

import utils.Utils;

/**
 *
 * @author charles
 */
public class SomaCategoria {
    private double valor;
    private String categoria;

    public double getValor() {
        return valor;
    }

    public String getValorFormatado(){
        if(valor > 0d)
            return Utils.formataDinheiro(valor);
        else
            return "-";
    }
    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public SomaCategoria(double valor, String categoria) {
        this.valor = valor;
        this.categoria = categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
