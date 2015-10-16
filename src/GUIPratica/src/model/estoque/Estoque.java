package model.estoque;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "seq_estoque", allocationSize = 1, sequenceName = "seq_estoque")
public class Estoque {

    @Id
    @GeneratedValue(generator = "seq_estoque", strategy = GenerationType.SEQUENCE)
    private int id;
    private EstoqueEntrada entrada;
    private String lote;
    private Item item;
    private int quantidadeCompra;
    private int quantidadeDisponivel;
    private double valorUnitario;
    private double valorUnidadeVenda;
    private Date dataValidade;
    @OneToMany(mappedBy = "estoque")
    private List<EstoqueMovimentacao> movimentacoes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EstoqueEntrada getEntrada() {
        return entrada;
    }

    public void setEntrada(EstoqueEntrada entrada) {
        this.entrada = entrada;
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

    public int getQuantidadeCompra() {
        return quantidadeCompra;
    }

    public void setQuantidadeCompra(int quantidadeCompra) {
        this.quantidadeCompra = quantidadeCompra;
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

    public double getValorUnidadeVenda() {
        return valorUnidadeVenda;
    }

    public void setValorUnidadeVenda(double valorUnidadeVenda) {
        this.valorUnidadeVenda = valorUnidadeVenda;
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

}
