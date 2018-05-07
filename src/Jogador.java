
import java.util.ArrayList;

public class Jogador{
    
    private String nome;
    private ArrayList<Integer> cartas;
    private int id;

    public Jogador(String nome, int id) {
        this.nome = nome;
        this.id = id;
        this.cartas = new ArrayList<>();
    }

    public Jogador() {
        this.cartas = new ArrayList<>();
    }
    
    public int compraCarta(int carta){
        this.cartas.add(carta);
        return 0;
    }
    
    public int jogaCarta(int carta){
        return this.cartas.remove(carta);
    }

    public ArrayList<Integer> getCartas() {
        return cartas;
    }

    public void setCartas(ArrayList<Integer> cartas) {
        this.cartas = cartas;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Jogador{" + "nome=" + nome + ", id=" + id + '}';
    }
    
    
    
    
}