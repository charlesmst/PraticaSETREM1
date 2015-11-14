/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.queryresults;

import java.util.Date;
import javassist.bytecode.analysis.Util;
import utils.Utils;

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

    private String dataFormatada = "";
    private String comprasPrazoD = "";
    private String comprasAVistaD = "";
    private String vendasPrazoD = "";
    private String vendasAVistaD ="";

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
        if (data != null) {
            dataFormatada = Utils.formataDate(data, "dd/MM");
        }
        if (comprasPrazo > 0d) {
            comprasPrazoD = Utils.formataDinheiro(comprasPrazo);
        }
        if (comprasAVista > 0d) {
            comprasAVistaD = Utils.formataDinheiro(comprasAVista);
        }
        if (vendasPrazo > 0d) {
            vendasPrazoD = Utils.formataDinheiro(vendasPrazo);
        }
        if (vendasAVista > 0d) {
            vendasAVistaD = Utils.formataDinheiro(vendasAVista);
        }
    }

    public String getDataFormatada() {
        return dataFormatada;
    }

    public void setDataFormatada(String dataFormatada) {
        this.dataFormatada = dataFormatada;
    }

    public String getComprasPrazoD() {
        return comprasPrazoD;
    }

    public void setComprasPrazoD(String comprasPrazoD) {
        this.comprasPrazoD = comprasPrazoD;
    }

    public String getComprasAVistaD() {
        return comprasAVistaD;
    }

    public void setComprasAVistaD(String comprasAVistaD) {
        this.comprasAVistaD = comprasAVistaD;
    }

    public String getVendasPrazoD() {
        return vendasPrazoD;
    }

    public void setVendasPrazoD(String vendasPrazoD) {
        this.vendasPrazoD = vendasPrazoD;
    }

    public String getVendasAVistaD() {
        return vendasAVistaD;
    }

    public void setVendasAVistaD(String vendasAVistaD) {
        this.vendasAVistaD = vendasAVistaD;
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
