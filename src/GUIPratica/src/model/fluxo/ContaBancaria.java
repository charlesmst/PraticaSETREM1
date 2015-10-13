package model.fluxo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "fc_conta_bancaria")
@SequenceGenerator(name = "seq_fc_conta_bancaria",sequenceName = "seq_fc_conta_bancaria", initialValue = 1,allocationSize = 1)
public class ContaBancaria {

    public enum TipoContaBancaria{
        caixa,
        banco
    }
    @Id
    @GeneratedValue(generator = "seq_fc_conta_bancaria", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false,length = 100)
    private String nome;

    @Enumerated(EnumType.ORDINAL)
    private TipoContaBancaria tipo;

    private int agencia;

    private int contaCorrente;

    private int carteira;

    private int modalidade;

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


    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public int getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(int contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    public TipoContaBancaria getTipo() {
        return tipo;
    }

    public void setTipo(TipoContaBancaria tipo) {
        this.tipo = tipo;
    }

    public int getCarteira() {
        return carteira;
    }

    public void setCarteira(int carteira) {
        this.carteira = carteira;
    }

    public int getModalidade() {
        return modalidade;
    }

    public void setModalidade(int modalidade) {
        this.modalidade = modalidade;
    }

}
