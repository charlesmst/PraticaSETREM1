package model.ordem;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "sh_ordem_tipo_servico")
@SequenceGenerator(name = "seq_ordem_tipo_servico", sequenceName = "seq_ordem_tipo_servico", initialValue = 1, allocationSize = 1)
public class OrdemTipoServico implements Serializable {

    @Id
    @GeneratedValue(generator = "seq_ordem_tipo_servico", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(length = 100)
    private String nome;

    private boolean ativo;

    @Column(name = "valor_entrada")
    private double valorEntrada;
}
