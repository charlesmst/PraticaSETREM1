package model.ordem;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "sh_marca")
@SequenceGenerator(name = "seq_marca", sequenceName = "seq_marca", initialValue = 1, allocationSize = 1)
public class Marca implements Serializable {

    @Override
    public String toString() {
        return  nome;
    }

    @Id
    @GeneratedValue(generator = "seq_marca", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(length = 100, nullable = false)
    private String nome;

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
}
