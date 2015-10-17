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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "sh_ordem")
@SequenceGenerator(name = "seq_ordem", sequenceName = "seq_ordem", initialValue = 1, allocationSize = 1)
public class Ordem implements Serializable {

    @Id
    @GeneratedValue(generator = "seq_ordem", strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @JoinColumn(name = "ordem_status")
    private OrdemStatus ordemStatus;

    private Date prazo;

    @Column(length = 200)
    private String descricao;

    private double valor;

    private int km;

}
