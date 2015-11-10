package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name = "seq_sh_cidade", allocationSize = 1, sequenceName = "seq_sh_cidade")
@Table(name = "sh_cidade")
public class Cidade implements Serializable {

    @Id
    @GeneratedValue(generator = "seq_sh_cidade", strategy = GenerationType.SEQUENCE)
    private int id;

    private String cep;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 2)
    private String uf;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return nome + " -> " + uf;
    }
}
