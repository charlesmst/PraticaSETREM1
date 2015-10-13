package model.fluxo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "fc_forma_pagamento")
@SequenceGenerator(name = "seq_fc_forma_pagamento", sequenceName = "seq_fc_forma_pagamento", initialValue = 1, allocationSize = 1)

public class FormaPagamento {

    @Id
    @GeneratedValue(generator = "seq_fc_forma_pagamento", strategy = GenerationType.SEQUENCE)
  
    private int id;

    private String nome;

    private boolean ativo;

    private double acrescimo;

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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public double getAcrescimo() {
        return acrescimo;
    }

    public void setAcrescimo(double acrescimo) {
        this.acrescimo = acrescimo;
    }

}
