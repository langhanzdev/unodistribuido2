
import java.util.ArrayList;
import java.util.Random;


public class Partida{
    
    private Jogador jogador1;
    private Jogador jogador2;
    private int[] baralho;
    private int numCartas;
    private final int totalCartas;
    private int numDescarte;
    private int[] descarte;
    private int corAtiva;
    private int vez;
    private int id;
    private Random gerador;
    private boolean temBaralho;
    private int pontosJogador1;
    private int pontosJogador2;

    public Partida() {
        this.numCartas = 108;
        this.totalCartas = 108;
        this.baralho = new int[this.totalCartas];
        this.descarte = new int[this.totalCartas];
        this.numDescarte = 0;
        this.temBaralho = false;
        this.pontosJogador1 = 0;
        this.pontosJogador2 = 0;
    }
    
    public void distribuiCartas(){
        for(int i=0;i < 2;i++){
            compraCarta(1);
            compraCarta(2);
        }
    }
    
    public void preparaJogo(){
        if(!this.temBaralho){
            geraBaralho();
            embaralha();
            distribuiCartas();
            do{
                this.descarte[numDescarte] = baralho[--numCartas];
            }while(this.descarte[numDescarte] >= 104 && this.descarte[numDescarte] >= 107);
            setVez(1);
        }
        this.temBaralho = true;
    }
    
    public void geraBaralho(){
        // Inicializacao do gerador de numeros aleatorios
        this.gerador = new Random(jogador1.getId()+jogador2.getId());
        
        // Criacao do baralho com as 108 cartas
        for (int i=0;i<totalCartas;++i)
            baralho[i] = i;
        
    }
    
    public void embaralha(){
        // Embaralhamento
        for (int c=0;c<totalCartas;++c) { 
            int outra = gerador.nextInt(totalCartas); 
            int aux = baralho[c]; 
            baralho[c] = baralho[outra]; 
            baralho[outra] = aux; 
        } 
        for (int c=0;c<totalCartas*totalCartas;c++) { 
            int c1 = gerador.nextInt(totalCartas); 
            int c2 = gerador.nextInt(totalCartas); 
            int aux = baralho[c1]; 
            baralho[c1] = baralho[c2]; 
            baralho[c2] = aux; 
        } 
        
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
            if(carta < 0 || carta >= this.jogador1.getCartas().size()) return -3;
            this.numDescarte++;
            this.descarte[this.numDescarte] = this.jogador1.jogaCarta(carta);
            this.setVez(2);
            return 1;
        }else{
            if(carta < 0 || carta >= this.jogador2.getCartas().size()) return -3;
            this.numDescarte++;
            this.descarte[this.numDescarte] = this.jogador2.jogaCarta(carta);
            this.setVez(1);
            return 1;
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
    
    public int getTopoDescarte(){
        return this.descarte[numDescarte];
    }

    public void setDescarte(int[] descarte) {
        this.descarte = descarte;
    }

    
    
//    public int topoDescarte(){
//        if(this.numDescarte == 0) return -1;
//        return this.descarte[numDescarte];
//    }

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

    public int getPontosJogador1() {
        return pontosJogador1;
    }

    public void setPontosJogador1(int pontosJogador1) {
        this.pontosJogador1 = pontosJogador1;
    }
    
    public void somaPontosJogador1(int pontosJogador1) {
        this.pontosJogador1 += pontosJogador1;
    }

    public int getPontosJogador2() {
        return pontosJogador2;
    }

    public void setPontosJogador2(int pontosJogador2) {
        this.pontosJogador2 = pontosJogador2;
    }
    
    public void somaPontosJogador2(int pontosJogador2) {
        this.pontosJogador2 += pontosJogador2;
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