package model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "seq_produto", allocationSize = 1, sequenceName = "seq_produto")

public class Produto {

    @Id
    @GeneratedValue(generator = "seq_produto", strategy = GenerationType.SEQUENCE)
    private int id;
    private String descricao;
    @ManyToOne
    private Marca marca;
//
//    private List<Preco> precos;
//
//    private List<Promocao> promocoes;
    @ManyToOne
    private Segmento segmento;

    public Produto() {
        this(0);
    }

    public Produto(int id) {
        this.setId(id);
    }

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

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

//    public List<Preco> getPrecos() {
//        return precos;
//    }
//
//    public void setPrecos(List<Preco> precos) {
//        this.precos = precos;
//    }

    public Segmento getSegmento() {
        return segmento;
    }

    public void setSegmento(Segmento segmento) {
        this.segmento = segmento;
    }
//
//    public List<Promocao> getPromocoes() {
//        return promocoes;
//    }
//
//    public void setPromocoes(List<Promocao> promocoes) {
//        this.promocoes = promocoes;
//    }

}
