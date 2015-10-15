package model.fluxo;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "fc_conta_parcela")
@SequenceGenerator(name = "seq_fc_conta_parcela", sequenceName = "seq_fc_conta_parcela", initialValue = 1, allocationSize = 1)

public class Parcela {

    public enum SituacaoParcela {

        previsto,
        realizado
    }
    @Id
    @GeneratedValue(generator = "seq_fc_conta_parcela", strategy = GenerationType.SEQUENCE)

    private int id;

    @Column(nullable = false, name = "conta_id")
    private Conta conta;

    @Column(nullable = false)
    private int parcela;

    @Column(nullable = false,name = "data_lancamento")
    private Date dataLancamento;

    @Column(nullable = false)
    private double valor;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private SituacaoParcela situacao;


    private String boleto;

    @OneToMany
    private List<ParcelaPagamento> pagamentos;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public int getParcela() {
        return parcela;
    }

    public void setParcela(int parcela) {
        this.parcela = parcela;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public SituacaoParcela getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoParcela situacao) {
        this.situacao = situacao;
    }

    public String getBoleto() {
        return boleto;
    }

    public void setBoleto(String boleto) {
        this.boleto = boleto;
    }

    public List<ParcelaPagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<ParcelaPagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }



}
