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

    public enum TipoPessoa{
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
    @JoinColumn(nullable = false,name = "cep")
    private Cidade cidade;

    private String email;

    
    @Column(nullable = false, name = "nascimento")
    private Date dataNascimento;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private TipoPessoa tipo;

    private String endereco;

}
