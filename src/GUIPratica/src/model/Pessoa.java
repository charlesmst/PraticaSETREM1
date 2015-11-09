package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name = "seq_sh_pessoa", allocationSize = 1, sequenceName = "seq_sh_pessoa")
@Table(name = "sh_pessoa")
public class Pessoa implements Serializable {

    public Pessoa() {
        this(0);
    }

    public Pessoa(int id) {
        setId(id);
    }

    public enum TipoPessoa {

        fisica,
        juridica
    }
    @Id
    @GeneratedValue(generator = "seq_sh_pessoa", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false, length = 100)
    private String nome;

    private String telefone;

    private String telefoneSecundario;

    @ManyToOne
    private Cidade cidade;

    private String email;

    @Column(name = "nascimento")
    private Date dataNascimento;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private TipoPessoa tipo;

    private String endereco;

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefoneSecundario() {
        return telefoneSecundario;
    }

    public void setTelefoneSecundario(String telefoneSecundario) {
        this.telefoneSecundario = telefoneSecundario;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public TipoPessoa getTipo() {
        return tipo;
    }

    public void setTipo(TipoPessoa tipo) {
        this.tipo = tipo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return nome;
    }
    
}
