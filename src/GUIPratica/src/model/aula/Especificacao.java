package model.aula;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "seq_especificacao", allocationSize = 1, sequenceName = "seq_especificacao")

public class Especificacao {

    @Id
    @GeneratedValue(generator = "seq_especificacao", strategy = GenerationType.SEQUENCE)

    private int id;
    private String titulo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
