package model.estoque;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "es_item")
@SequenceGenerator(name = "seq_item", allocationSize = 1, sequenceName = "seq_item")
public class Item implements Serializable {

    @Id
    @GeneratedValue(generator = "seq_item", strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne
    @JoinColumn(name = "prateleira_id", nullable = false)
    private Prateleira prateleira;

    @ManyToOne
    @JoinColumn(name = "item_tipo_id", nullable = false)
    private ItemTipo itemTipo;

    @Column(length = 100, nullable = false)
    private String descricao;

    @Column(name = "estoque_minimo", nullable = false)
    private int estoqueMinimo;

    @Column(name = "ultimo_valor_venda", nullable = false)
    private double ultimoValorVenda;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Prateleira getPrateleira() {
        return prateleira;
    }

    public void setPrateleira(Prateleira prateleira) {
        this.prateleira = prateleira;
    }

    public ItemTipo getItemTipo() {
        return itemTipo;
    }

    public void setItemTipo(ItemTipo itemTipo) {
        this.itemTipo = itemTipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(int estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public double getUltimoValorVenda() {
        return ultimoValorVenda;
    }

    public void setUltimoValorVenda(double ultimoValorVenda) {
        this.ultimoValorVenda = ultimoValorVenda;
    }

    @Override
    public String toString() {
        return itemTipo.getNome() + " -> " + descricao;
    }
}
