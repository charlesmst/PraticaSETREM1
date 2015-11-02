package model.ordem;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import model.fluxo.Conta;

@Entity
@Table(name = "sh_ordem_servico")
@SequenceGenerator(name = "seq_ordem_servico", sequenceName = "seq_ordem_servico", initialValue = 1, allocationSize = 1)

public class OrdemServico implements Serializable {

    @Id
    @GeneratedValue(generator = "seq_ordem_servico", strategy = GenerationType.SEQUENCE)
    private int id;

    @JoinColumn(name = "tipo_servico_id")
    private OrdemTipoServico tipoServico;

    
    @OneToOne
    @JoinColumn(nullable = false, name = "ordem_id")
    private Ordem ordem;

    @Column(name = "valor_entrada")
    private double valorEntrada;

    private int quantidade;

    @Column(name = "data_realizada")
    private Date dataRealizada;

    @JoinColumn(name = "conta_id")
    private Conta conta;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrdemTipoServico getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(OrdemTipoServico tipoServico) {
        this.tipoServico = tipoServico;
    }

    public Ordem getOrdem() {
        return ordem;
    }

    public void setOrdem(Ordem ordem) {
        this.ordem = ordem;
    }

    public double getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(double valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Date getDataRealizada() {
        return dataRealizada;
    }

    public void setDataRealizada(Date dataRealizada) {
        this.dataRealizada = dataRealizada;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

}
