package model.estoque;

import java.util.Date;
import java.util.List;

public class Estoque {
    private int id;
    private EstoqueEntrada entrada;
    private int lote;
    private Item item;
    private int quantidadeCompra;
    private int quantidadeDisponivel;
    private double valorUnitario;
    private double valorUnidadeVenda;
    private Date dataValidade;

    private List movimentacoes;

    public void insert() {
    }

    public void update() {
    }

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

    public int getLote() {
        return lote;
    }

    public void setLote(int lote) {
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

    public List getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(List movimentacoes) {
        this.movimentacoes = movimentacoes;
    }

}
