package model.ordem;

import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import model.Pessoa;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import model.estoque.EstoqueMovimentacao;
import model.fluxo.Conta;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "sh_ordem")
@SequenceGenerator(name = "seq_ordem", sequenceName = "seq_ordem", initialValue = 1, allocationSize = 1)
public class Ordem implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    @Id
    @GeneratedValue(generator = "seq_ordem", strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;
    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;
    @ManyToOne
    @JoinColumn(name = "ordem_status_id", nullable = false)
    private OrdemStatus ordemStatus;

    private Date prazo;

    @Column(length = 200)
    private String descricao;

    private double valor;

    private double desconto;

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    private int km;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "sh_ordem_pecas", joinColumns = {
        @JoinColumn(name = "ordem_id")
    }, inverseJoinColumns = {
        @JoinColumn(name = "estoque_movimentacao_id")
    })
   
    private Set<EstoqueMovimentacao> estoqueMovimentacaos = new HashSet<>();
    ;

    @OneToMany(mappedBy = "ordem", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<OrdemServico> ordemServicos = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        int oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public Pessoa getPessoa() {

        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        Pessoa old = this.pessoa;
        this.pessoa = pessoa;
        changeSupport.firePropertyChange("pessoa", old, pessoa);

    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        Veiculo old = this.veiculo;
        this.veiculo = veiculo;
        changeSupport.firePropertyChange("veiculo", old, veiculo);
    }

    public OrdemStatus getOrdemStatus() {
        return ordemStatus;
    }

    public void setOrdemStatus(OrdemStatus ordemStatus) {
        OrdemStatus old = this.ordemStatus;
        this.ordemStatus = ordemStatus;
        changeSupport.firePropertyChange("ordemStatus", old, ordemStatus);
    }

    public Date getPrazo() {
        return prazo;
    }

    public void setPrazo(Date prazo) {
        
        Date old = this.prazo;
        this.prazo = prazo;
        changeSupport.firePropertyChange("prazo", old, prazo);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        String old = this.descricao;
        this.descricao = descricao;
        changeSupport.firePropertyChange("descricao", old, descricao);
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        double old = this.valor;
        this.valor = valor;
        changeSupport.firePropertyChange("valor", old, valor);
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {

        int old = this.km;
        this.km = km;
        changeSupport.firePropertyChange("km", old, km);
    }

    public Set<EstoqueMovimentacao> getEstoqueMovimentacaos() {
        return estoqueMovimentacaos;
    }

    public void setEstoqueMovimentacaos(Set<EstoqueMovimentacao> estoqueMovimentacaos) {
        this.estoqueMovimentacaos = estoqueMovimentacaos;
    }

    public Set<OrdemServico> getOrdemServicos() {
        return ordemServicos;
    }

    public void setOrdemServicos(Set<OrdemServico> ordemServicos) {
        this.ordemServicos = ordemServicos;
    }

}
