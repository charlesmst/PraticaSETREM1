package model.ordem;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import model.estoque.EstoqueMovimentacao;

@Entity
@Table(name = "sh_ordem_item")
@SequenceGenerator(name = "seq_ordem_item", sequenceName = "seq_ordem_item", initialValue = 1, allocationSize = 1)
public class OrdemPecas implements Serializable {

    @Id
    @GeneratedValue(generator = "seq_ordem_item", strategy = GenerationType.SEQUENCE)
    private Ordem ordem;

    @JoinColumn(name = "estoque_movimentacao_id")
    private EstoqueMovimentacao movimentacao;

    public Ordem getOrdem() {
        return ordem;
    }

    public void setOrdem(Ordem ordem) {
        this.ordem = ordem;
    }

    public EstoqueMovimentacao getMovimentacao() {
        return movimentacao;
    }

    public void setMovimentacao(EstoqueMovimentacao movimentacao) {
        this.movimentacao = movimentacao;
    }
}
