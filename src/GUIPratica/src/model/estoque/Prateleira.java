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
@Table(name = "es_prateleira")
@SequenceGenerator(name = "seq_prateleira", allocationSize = 1, sequenceName = "seq_prateleira")
public class Prateleira implements Serializable {

    @Id
    @GeneratedValue(generator = "seq_prateleira", strategy = GenerationType.SEQUENCE)
    private int id;
    
    @Column(length = 100)
    private String descricao;

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
}
