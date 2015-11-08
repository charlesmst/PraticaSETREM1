/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.queryresults;

import java.util.Date;

/**
 *
 * @author charles
 */
public class LivroCaixa {
    private Date data;
    private String categoria;
    private String descricao;
    private double entrada;
    private double saida;
    private int parcela;

    private int numero;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    public LivroCaixa(Date data, String categoria, String descricao, double entrada, double saida, int parcela) {
        this.data = data;
        this.categoria = categoria;
        this.descricao = descricao;
        this.entrada = entrada;
        this.saida = saida;
        this.parcela = parcela;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getEntrada() {
        return entrada;
    }

    public void setEntrada(double entrada) {
        this.entrada = entrada;
    }

    public double getSaida() {
        return saida;
    }

    public void setSaida(double saida) {
        this.saida = saida;
    }

    public int getParcela() {
        return parcela;
    }

    public void setParcela(int parcela) {
        this.parcela = parcela;
    }
}
