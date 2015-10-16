package model.fluxo;

import java.io.Serializable;
import model.Pessoa;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "fc_conta")
@SequenceGenerator(name = "seq_fc_conta", sequenceName = "seq_fc_conta", initialValue = 1, allocationSize = 1)

public class Conta implements Serializable {

    @Id
    @GeneratedValue(generator = "seq_fc_conta", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "nota_fiscal")
    private String notaFiscal;

    @Column(length = 200)
    private String descricao;

    @Column(nullable = false,name = "pessoa_id")
    private Pessoa pessoa;

    
    @ManyToOne
    @JoinColumn(name="forma_pagamento_id",nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(name="conta_categoria_id",nullable = false)
    private ContaCategoria categoria;

    @OneToMany
    private List<Parcela> parcelas;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public ContaCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(ContaCategoria categoria) {
        this.categoria = categoria;
    }

    public List<Parcela> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<Parcela> parcelas) {
        this.parcelas = parcelas;
    }

}
