/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.aula;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.ManyToOne;

/**
 *
 * @author Charles
 */
public class EspecificacaoProdutoPK implements Serializable{

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.produto);
        hash = 13 * hash + Objects.hashCode(this.especificacao);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EspecificacaoProdutoPK other = (EspecificacaoProdutoPK) obj;
        if (!Objects.equals(this.produto, other.produto)) {
            return false;
        }
        if (!Objects.equals(this.especificacao, other.especificacao)) {
            return false;
        }
        return true;
    }

    @ManyToOne
    private Produto produto;
    @ManyToOne
    private Especificacao especificacao;
   
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Especificacao getEspecificacao() {
        return especificacao;
    }

    public void setEspecificacao(Especificacao especificacao) {
        this.especificacao = especificacao;
    }


}
