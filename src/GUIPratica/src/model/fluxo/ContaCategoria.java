package model.fluxo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "fc_conta_categoria")
@SequenceGenerator(name = "seq_fc_conta_categoria",sequenceName = "seq_fc_conta_categoria", initialValue = 1,allocationSize = 1)

public class ContaCategoria implements Serializable {

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.id;
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
        final ContaCategoria other = (ContaCategoria) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public ContaCategoria(int id){
        setId(id);
    }
    public ContaCategoria(){
        this(0);
    }
    @Override
    public String toString() {
        return  nome;
    }

    @Id    
    @GeneratedValue(generator = "seq_fc_conta_categoria", strategy = GenerationType.SEQUENCE)
    private int id;

    private String nome;

    @Enumerated(EnumType.ORDINAL)
    private TipoCategoria tipo;

    private boolean ativo;

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

    public TipoCategoria getTipo() {
        return tipo;
    }

    public void setTipo(TipoCategoria tipo) {
        this.tipo = tipo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public enum TipoCategoria {

        entrada,
        saida
    }
}
