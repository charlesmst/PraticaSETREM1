package model.ordem;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "sh_modelo")
@SequenceGenerator(name = "seq_modelo", sequenceName = "seq_modelo", initialValue = 1, allocationSize = 1)
public class Modelo implements Serializable {

    @Id
    @GeneratedValue(generator = "seq_modelo", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(length = 100)
    private String nome;

    @OneToMany
    @JoinColumn(name = "marca_id")
    private Marca marca;

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

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
}
