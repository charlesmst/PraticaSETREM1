package model.ordem;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "sh_ordem_tipo_servico")
@SequenceGenerator(name = "seq_ordem_tipo_servico", sequenceName = "seq_ordem_tipo_servico", initialValue = 1, allocationSize = 1)
public class OrdemTipoServico implements Serializable {


    @Id
    @GeneratedValue(generator = "seq_ordem_tipo_servico", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(nullable = false)
    private boolean ativo;

    @Column(name = "valor_entrada")
    private double valorEntrada;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public double getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(double valorEntrada) {
        this.valorEntrada = valorEntrada;
    }
    
    @Override
    public String toString() {
        return nome;
    }
}
