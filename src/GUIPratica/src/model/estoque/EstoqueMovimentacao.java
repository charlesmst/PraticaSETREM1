package model.estoque;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import model.fluxo.Conta;
import model.Pessoa;
import model.ordem.Ordem;

@Entity
@Table(name = "es_estoque_movimentacao")
@SequenceGenerator(name = "seq_estoque_movimentacao", sequenceName = "seq_estoque_movimentacao", initialValue = 1, allocationSize = 1)
public class EstoqueMovimentacao implements Serializable {

    @Override
    public String toString() {
        return descricao;
    }

    @Id
    @GeneratedValue(generator = "seq_estoque_movimentacao", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false)
    private int quantidade;

    @Column(name = "data_lancamento", nullable = false)
    private Date dataLancamento;

    @ManyToOne
    @JoinColumn(name = "estoque_id", nullable = false)
    private Estoque estoque;

    @Column(length = 200)
    private String descricao;

    @Column(name = "valor_unitario", nullable = false)
    private double valorUnitario;

    @Column(name = "valor_unitario_venda", nullable = false)
    private double valorUnitarioVenda;

    @ManyToOne
    @JoinColumn(name = "movimentacao_tipo_id", nullable = false)
    private MovimentacaoTipo movimentacaoTipo;

    @OneToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;
    
    @Transient
    private String data;
    
    @Transient
    private String valorEntrada;
    
    @Transient
    private String valorSaida;
    
    @Transient
    private String valorTotal;

//    @ManyToMany
//    private Set<Ordem> ordem = new HashSet<>();

    @Column(name = "nota_fiscal")
    private String notaFiscal;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public MovimentacaoTipo getMovimentacaoTipo() {
        return movimentacaoTipo;
    }

    public void setMovimentacaoTipo(MovimentacaoTipo movimentacaoTipo) {
        this.movimentacaoTipo = movimentacaoTipo;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

//    public void setOrdem(Set<Ordem> ordem) {
//        this.ordem = ordem;
//    }

    public double getValorUnitarioVenda() {
        return valorUnitarioVenda;
    }

    public void setValorUnitarioVenda(double valorUnitarioVenda) {
        this.valorUnitarioVenda = valorUnitarioVenda;
    }
//
//    public Set<Ordem> getOrdem() {
//        return ordem;
//    }

    public String getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(String valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public String getValorSaida() {
        return valorSaida;
    }

    public void setValorSaida(String valorSaida) {
        this.valorSaida = valorSaida;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    
}
