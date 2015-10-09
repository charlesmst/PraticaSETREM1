/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.aula;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import model.Usuario;

/**
 *
 * @author Charles
 * @todo Implementar Cep
 */
@Entity
@SequenceGenerator(name = "seq_pessoa", allocationSize = 1, sequenceName = "seq_pessoa")
@Table(name = "sh_pessoa")
public class Pessoa implements Serializable{

    @Id
    @GeneratedValue(generator = "seq_pessoa", strategy = GenerationType.SEQUENCE)
    private int id;
    private String nome;

//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "sh_usuario",optional = true)
    private Usuario usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
