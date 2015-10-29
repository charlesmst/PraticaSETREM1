package model.fluxo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "fc_conta_parcela")
@SequenceGenerator(name = "seq_fc_conta_parcela", sequenceName = "seq_fc_conta_parcela", initialValue = 1, allocationSize = 1)
public class Parcela implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    @Id
    @GeneratedValue(generator = "seq_fc_conta_parcela", strategy = GenerationType.SEQUENCE)

    private int id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "conta_id")
    private Conta conta;

    @Column(nullable = false)
    private int parcela;

    @Column(nullable = false, name = "data_lancamento")
    private Date dataLancamento;

    @Column(nullable = false)
    private double valor;

    private boolean fechado;
    private String boleto;

    //Não tem orphanRemoval por causa que as parcelas ja finalizadas nao carregam a parcela
    @OneToMany(mappedBy = "parcela", orphanRemoval = true, cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<ParcelaPagamento> pagamentos;

    @Column(name = "data_quitacao")
    private Date dataQuitado;

    public Date getDataQuitado() {
        return dataQuitado;
    }

    public void setDataQuitado(Date dataQuitado) {
        this.dataQuitado = dataQuitado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        int oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        Conta oldConta = this.conta;
        this.conta = conta;
        changeSupport.firePropertyChange("conta", oldConta, conta);
    }

    public int getParcela() {
        return parcela;
    }

    public void setParcela(int parcela) {
        int oldParcela = this.parcela;
        this.parcela = parcela;
        changeSupport.firePropertyChange("parcela", oldParcela, parcela);
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        Date oldDataLancamento = this.dataLancamento;
        this.dataLancamento = dataLancamento;
        changeSupport.firePropertyChange("dataLancamento", oldDataLancamento, dataLancamento);
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        double oldValor = this.valor;
        this.valor = valor;
        changeSupport.firePropertyChange("valor", oldValor, valor);
    }

    public String getBoleto() {
        return boleto;
    }

    public void setBoleto(String boleto) {
        String oldBoleto = this.boleto;
        this.boleto = boleto;
        changeSupport.firePropertyChange("boleto", oldBoleto, boleto);
    }

    public List<ParcelaPagamento> getPagamentos() {
        if (pagamentos == null) {
            pagamentos = new ArrayList<>();
        }
        return pagamentos;
    }

    public void setPagamentos(List<ParcelaPagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    public boolean isFechado() {
        return fechado;
    }

    public void setFechado(boolean fechado) {
        this.fechado = fechado;
    }

}
