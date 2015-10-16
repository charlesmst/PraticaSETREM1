package model.estoque;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "seq_item", allocationSize = 1, sequenceName = "seq_item")
public class Item {

    @Id
    @GeneratedValue(generator = "seq_item", strategy = GenerationType.SEQUENCE)
    private int id;
    private Prateleira pratelaira;
    private ItemTipo itemTipo;
    private String nome;

}
