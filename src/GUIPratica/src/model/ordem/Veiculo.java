package model.ordem;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "sh_veiculo")
@SequenceGenerator(name = "seq_veiculo", sequenceName = "seq_veiculo", initialValue = 1, allocationSize = 1)
public class Veiculo implements Serializable {

    @Override
    public String toString() {
        return placa+" "+ modelo.toString()+ " "+cor ;
    }

    @Id
    @GeneratedValue(generator = "seq_veiculo", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(length = 7)
    private String placa;

    @ManyToOne
    @JoinColumn(name = "modelo_id", nullable = false)
    private Modelo modelo;

    @ManyToOne

    @JoinColumn(name = "cor_id", nullable = false)
    private Cor cor;

    private int ano;

    private String observacao;

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    @Column(length = 50)
    private String chassi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

   
    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }
}
