package model.ordem;

import java.io.Serializable;
import model.Pessoa;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import model.estoque.EstoqueMovimentacao;

@Entity
@Table(name = "sh_ordem")
@SequenceGenerator(name = "seq_ordem", sequenceName = "seq_ordem", initialValue = 1, allocationSize = 1)
public class Ordem implements Serializable {

    @Id
    @GeneratedValue(generator = "seq_ordem", strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;

    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @JoinColumn(name = "sh_ordem_status_id", nullable = false)
    private OrdemStatus ordemStatus;

    private Date prazo;

    @Column(length = 200)
    private String descricao;

    private double valor;

    private int km;

    @ManyToMany
    private List<EstoqueMovimentacao> estoqueMovimentacaos;

    private List<OrdemServico> ordemServicos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public OrdemStatus getOrdemStatus() {
        return ordemStatus;
    }

    public void setOrdemStatus(OrdemStatus ordemStatus) {
        this.ordemStatus = ordemStatus;
    }

    public Date getPrazo() {
        return prazo;
    }

    public void setPrazo(Date prazo) {
        this.prazo = prazo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public List<EstoqueMovimentacao> getOrdemPecas() {
        return estoqueMovimentacaos;
    }

    public void setOrdemPecas(List<EstoqueMovimentacao> estMov) {
        this.estoqueMovimentacaos = estMov;
    }

    public List<OrdemServico> getOrdemServicos() {
        return ordemServicos;
    }

    public void setOrdemServicos(List<OrdemServico> ordemServicos) {
        this.ordemServicos = ordemServicos;
    }
}
