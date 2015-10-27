package model.estoque;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "es_item_tipo")
@SequenceGenerator(name = "seq_item_tipo", sequenceName = "seq_item_tipo", initialValue = 1, allocationSize = 1)
public class ItemTipo implements Serializable {

    @Id
    @GeneratedValue(generator = "seq_item_tipo", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(length = 100)
    private String nome;

    private boolean ativo;

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
}
