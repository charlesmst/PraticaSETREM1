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
public class ComprasVendas {
    private Date data;
    private double comprasPrazo;
    private double comprasAVista;
    private double vendasPrazo;
    private double vendasAVista;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public ComprasVendas(Date data, double comprasPrazo, double comprasAVista, double vendasPrazo, double vendasAVista) {
        this.data = data;
        this.comprasPrazo = comprasPrazo;
        this.comprasAVista = comprasAVista;
        this.vendasPrazo = vendasPrazo;
        this.vendasAVista = vendasAVista;
    }

    public double getComprasPrazo() {
        return comprasPrazo;
    }

    public void setComprasPrazo(double comprasPrazo) {
        this.comprasPrazo = comprasPrazo;
    }

    public double getComprasAVista() {
        return comprasAVista;
    }

    public void setComprasAVista(double comprasAVista) {
        this.comprasAVista = comprasAVista;
    }

    public double getVendasPrazo() {
        return vendasPrazo;
    }

    public void setVendasPrazo(double vendasPrazo) {
        this.vendasPrazo = vendasPrazo;
    }

    public double getVendasAVista() {
        return vendasAVista;
    }

    public void setVendasAVista(double vendasAVista) {
        this.vendasAVista = vendasAVista;
    }
}
