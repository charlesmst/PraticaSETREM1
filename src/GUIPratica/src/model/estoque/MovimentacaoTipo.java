package model.estoque;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "es_movimentacao_tipo")
@SequenceGenerator(name = "seq_movimentacao_tipo", allocationSize = 1, sequenceName = "seq_movimentacao_tipo")
public class MovimentacaoTipo implements Serializable {

    @Id
    @GeneratedValue(generator = "seq_movimentacao_tipo", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(length = 100)
    private String descricao;

    private boolean ativo;

    @Enumerated(EnumType.ORDINAL)
    private TipoMovimentacao tipo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public TipoMovimentacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimentacao tipo) {
        this.tipo = tipo;
    }

    public enum TipoMovimentacao {

    }
}
