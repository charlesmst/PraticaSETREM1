package model.estoque;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
@Table(name = "es_estoque")
@SequenceGenerator(name = "seq_estoque", allocationSize = 1, sequenceName = "seq_estoque")
public class Estoque implements Serializable {

    @Override
    public String toString() {
        return lote;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Estoque other = (Estoque) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Id
    @GeneratedValue(generator = "seq_estoque", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(length = 100)
    private String lote;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "data_compra", nullable = false)
    private Date dataCompra;

    @Column(name = "quandidade_disponivel", nullable = false)
    private int quantidadeDisponivel;

    @Column(name = "valor_unidade", nullable = false)
    private double valorUnitario;

    @Column(name = "data_validade")
    private Date dataValidade;

    @OneToMany(mappedBy = "estoque")
    private List<EstoqueMovimentacao> movimentacoes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public List<EstoqueMovimentacao> getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(List<EstoqueMovimentacao> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }
    
    public void addMovimentacoes(EstoqueMovimentacao estMov) {
        this.movimentacoes.add(estMov);
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }
}
