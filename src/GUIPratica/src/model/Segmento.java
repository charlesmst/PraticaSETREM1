
package model;


public class Segmento {
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
