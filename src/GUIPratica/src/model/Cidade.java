package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sh_cidade")
public class Cidade {

    @Id
    private int cep;

    private String nome;

    private char uf;

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public char getUf() {
        return uf;
    }

    public void setUf(char uf) {
        this.uf = uf;
    }


}
