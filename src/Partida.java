
import java.util.ArrayList;


public class Partida{
    
    private Jogador jogador1;
    private Jogador jogador2;
    private int[] baralho;
    private int numCartas;
    private int numDescarte;
    private int[] descarte;
    private int corAtiva;
    private int vez;
    private int id;

    public Partida() {
        this.numCartas = 108;
        this.baralho = new int[this.numCartas];
        this.descarte = new int[this.numCartas];
        this.numDescarte = 0;
    }
    
    
    
    public int compraCarta(int nrJogador){
        if(nrJogador == 1){
            this.jogador1.compraCarta(baralho[--numCartas]);
            return 0;
        }else{
            this.jogador2.compraCarta(baralho[--numCartas]);
            return 0;
        }
        
    }
    
    public int jogaCarta(int carta, int cor, int nrJogador){
        this.corAtiva = cor;
        
        if(nrJogador == 1){
            if(carta >= 0 && carta < this.jogador1.getCartas().size()) return -3;
            this.descarte[this.numDescarte] = this.jogador1.jogaCarta(carta);
            this.numDescarte++;
            return 0;
        }else{
            if(carta >= 0 && carta < this.jogador1.getCartas().size()) return -3;
            this.descarte[this.numDescarte] = this.jogador2.jogaCarta(carta);
            this.numDescarte++;
            return 0;
        }
    }

    public int getNumCartas() {
        return numCartas;
    }

    public void setNumCartas(int numCartas) {
        this.numCartas = numCartas;
    }

    public int getNumDescarte() {
        return numDescarte;
    }

    public void setNumDescarte(int numDescarte) {
        this.numDescarte = numDescarte;
    }

    public int[] getDescarte() {
        return descarte;
    }

    public void setDescarte(int[] descarte) {
        this.descarte = descarte;
    }

    
    
    public int topoDescarte(){
        if(this.numDescarte == 0) return -1;
        return this.descarte[numDescarte];
    }

    public int getCorAtiva() {
        return corAtiva;
    }

    public void setCorAtiva(int corAtiva) {
        this.corAtiva = corAtiva;
    }

    public int[] getBaralho() {
        return baralho;
    }

    public void setBaralho(int[] baralho) {
        this.baralho = baralho;
    }
    
    
    
    public int getVez() {
        return vez;
    }

    public void setVez(int vez) {
        this.vez = vez;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Jogador getJogador1() {
        return jogador1;
    }

    public void setJogador1(Jogador jogador1) {
        this.jogador1 = jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }

    public void setJogador2(Jogador jogador2) {
        this.jogador2 = jogador2;
    }

    @Override
    public String toString() {
        return "Partida{" + "jogador1=" + jogador1 + ", jogador2=" + jogador2 + ", id=" + id + '}';
    }

    

}