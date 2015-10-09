
package model.aula;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity
@SequenceGenerator(name = "seq_segmento",allocationSize = 1,sequenceName = "seq_segmento")
public class Segmento {
    @Id
    @GeneratedValue(generator = "seq_segmento",strategy = GenerationType.SEQUENCE)
    private int id;
    private String nome;
    
    
    public Segmento(int id){
        this.setId(id);
    }
    public Segmento(){
    }
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

}
