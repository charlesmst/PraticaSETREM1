package model.estoque;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import model.fluxo.Conta;
import model.Pessoa;
import model.ordem.Ordem;

@Entity
@Table(name = "es_estoque_movimentacao")
@SequenceGenerator(name = "seq_estoque_movimentacao", sequenceName = "seq_estoque_movimentacao", initialValue = 1, allocationSize = 1)
public class EstoqueMovimentacao implements Serializable {

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

    @ManyToOne
    @JoinColumn(name = "movimentacao_tipo_id", nullable = false)
    private MovimentacaoTipo movimentacaoTipo;

    @OneToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;

    @Column(name = "nota_fiscal")
    private String notaFiscal;

    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;

    @ManyToMany(mappedBy = "sh_ordem_peca")
    private List<Ordem> ordem;
}
