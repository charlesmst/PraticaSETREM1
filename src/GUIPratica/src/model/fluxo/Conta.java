package model.fluxo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import model.Pessoa;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import services.PessoaService;

@Entity
@Table(name = "fc_conta")
@SequenceGenerator(name = "seq_fc_conta", sequenceName = "seq_fc_conta", initialValue = 1, allocationSize = 1)

public class Conta implements Serializable {

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public enum ContaTipo {

        conta,
        movimentacao,
        estoque,
        ordem
    }

    @Override
    public String toString() {
        String descConta = (getId() > 0 ? getId() : "") + "";
        if (getDescricao() != null && !getDescricao().equals("")) {
            descConta += " - " + (getDescricao() + "").trim();
        }

        if (getPessoa() != null) {
            if (getPessoa().getNome() == null) {
                getPessoa().setNome(new PessoaService().findById(getPessoa().getId()).getNome());
            }
            descConta += " - " + getPessoa().getNome();

        }
        return descConta;
    }

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    @Id
    @GeneratedValue(generator = "seq_fc_conta", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "nota_fiscal")
    private String notaFiscal;

//    @Column(length = 200)
    private String descricao;

    @ManyToOne
    @JoinColumn(nullable = false, name = "pessoa_id")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "forma_pagamento_id", nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(name = "conta_categoria_id", nullable = false)
    private ContaCategoria categoria;

    @OneToMany(mappedBy = "conta", orphanRemoval = true, cascade = CascadeType.ALL)

//    @Column(name = "conta_id")
    private List<Parcela> parcelas ;//= new ArrayList<>();

    private double valorPago;

    private double valorTotal;

    @Column(name = "data_lancamento")
    private Date dataLancamento;
    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        double old = this.valorTotal;
        this.valorTotal = valorTotal;
        changeSupport.firePropertyChange("valorTotal", old, valorTotal);

    }
    @Enumerated(EnumType.ORDINAL)
    private ContaTipo tipo = ContaTipo.conta;

    public ContaTipo getTipo() {
        return tipo;
    }

    public void setTipo(ContaTipo tipo) {
        this.tipo = tipo;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public List<Parcela> getParcelas() {
        if(parcelas == null)
            parcelas = new ArrayList<>();
        return parcelas;
    }

    public void setParcelas(List<Parcela> parcelas) {
        this.parcelas = parcelas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        int oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        String oldNotaFiscal = this.notaFiscal;
        this.notaFiscal = notaFiscal;
        changeSupport.firePropertyChange("notaFiscal", oldNotaFiscal, notaFiscal);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        String oldDescricao = this.descricao;
        this.descricao = descricao;
        changeSupport.firePropertyChange("descricao", oldDescricao, descricao);
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        Pessoa oldPessoa = this.pessoa;
        this.pessoa = pessoa;
        changeSupport.firePropertyChange("pessoa", oldPessoa, pessoa);
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        FormaPagamento oldFormaPagamento = this.formaPagamento;
        this.formaPagamento = formaPagamento;
        changeSupport.firePropertyChange("formaPagamento", oldFormaPagamento, formaPagamento);
    }

    public ContaCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(ContaCategoria categoria) {
        ContaCategoria oldCategoria = this.categoria;
        this.categoria = categoria;
        changeSupport.firePropertyChange("categoria", oldCategoria, categoria);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
