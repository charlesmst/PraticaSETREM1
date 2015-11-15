/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.queryresults;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author charles
 */
public class OrdemRelatorio {

    private String cliente;
    private String telefone;
    private String placa;
    private String marca;
    private String modelo;
    private String pedido;
    private String valorTotal;
    private String ano;
    private String cor;
    private List<ItemFichaServico> itens = new ArrayList<>();

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if (telefone == null) {
            this.telefone = "";
        } else {
            this.telefone = telefone;
        }
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        if (placa == null) {
            this.placa = "";
        } else {
            this.placa = placa;
        }
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        if (marca == null) {
            this.marca = "";
        } else {
            this.marca = marca;
        }
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        if (modelo == null) {
            this.modelo = "";
        } else {
            this.modelo = modelo;
        }
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        if (pedido == null) {
            this.pedido = "";
        } else {
            this.pedido = pedido;
        }
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        if (cor == null) {
            this.cor = "";
        } else {
            this.cor = cor;
        }
    }

    public List<ItemFichaServico> getItens() {
        return itens;
    }

    public void setItens(List<ItemFichaServico> itens) {
        this.itens = itens;
    }

}
