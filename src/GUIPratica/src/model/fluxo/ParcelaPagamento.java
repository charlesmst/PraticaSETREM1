package model.fluxo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "fc_conta_parcela_pagamento")
@SequenceGenerator(name = "seq_fc_conta_parcela_pagamento", sequenceName = "seq_fc_conta_parcela_pagamento", initialValue = 1, allocationSize = 1)

public class ParcelaPagamento implements Serializable {

    @Id
    @GeneratedValue(generator = "seq_fc_conta_parcela_pagamento", strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(nullable = false)
    private double valor;

    private int situacao;

    @Column(nullable = false)
    private Date data;

    @Column(name = "conta_bancaria_id",nullable = false)
    private ContaBancaria contaBancaria;
    
    @Column(name = "conta_categoria_id",nullable = false)
    private ContaCategoria contaCategoria;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getSituacao() {
        return situacao;
    }

    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public ContaBancaria getContaBancaria() {
        return contaBancaria;
    }

    public void setContaBancaria(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
    }

    public ContaCategoria getContaCategoria() {
        return contaCategoria;
    }

    public void setContaCategoria(ContaCategoria contaCategoria) {
        this.contaCategoria = contaCategoria;
    }

}
