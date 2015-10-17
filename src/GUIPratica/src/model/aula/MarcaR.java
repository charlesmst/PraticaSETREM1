/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.aula;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Charles
 */

@Entity
@SequenceGenerator(name = "seq_marca",allocationSize = 1,sequenceName = "seq_marca")
public class MarcaR {
    @Id
    @GeneratedValue(generator = "seq_marca",strategy = GenerationType.SEQUENCE)
    private int id;
    private String nome;
    @OneToMany(mappedBy = "marca")
    private List<Produto> produtos;
    public MarcaR(int id){
        this.setId(id);
    }
    public MarcaR(){}
    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the value of nome
     *
     * @return the value of nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Set the value of nome
     *
     * @param nome new value of nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

}
