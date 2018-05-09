
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;

// Classe remota para o exemplo "Hello, world!"
public class UnoImpl extends UnicastRemoteObject implements UnoInterface {

    private static final long serialVersionUID = 7896795898928782846L;
    private String message;
    
    private int idPartidas = 0;
    private final int nrPartidas = 500;
    private Partida[] partidas = new Partida[nrPartidas];

    // Constroi um objeto remoto armazenando nele o String recebido
    public UnoImpl(String msg) throws RemoteException {
        message = msg;
    }

    
    public int geraIdPartida(){
        this.idPartidas++;
        return idPartidas;
    }
    
    public int geraIdJogador(){
        Random gerador = new Random();
        return gerador.nextInt(this.nrPartidas*10);
         
    }
    
    public boolean usuarioCasdastrado(String nome){
        for(int i=0;i<this.nrPartidas;i++){
            if(partidas[i] != null){
                if((partidas[i].getJogador1() != null && partidas[i].getJogador1().getNome().equals(nome)) || (partidas[i].getJogador2() != null && partidas[i].getJogador2().getNome().equals(nome))){
                    return true;
                }
            }
        }
        return false;
    }
    
    public int encontraPartida(int idJogador){
        for(int i=0;i<this.nrPartidas;i++){
            if(partidas[i] != null){
                if((partidas[i].getJogador1() != null && partidas[i].getJogador1().getId() == idJogador) || (partidas[i].getJogador2() != null && partidas[i].getJogador2().getId() == idJogador)){
                    return i;
                }
            }
        }
        return -1;
    }
    
    public boolean encontraPartidaLivre(Jogador  j){
        for(int i=0;i<this.nrPartidas;i++){
            if(partidas[i] != null){
                if(partidas[i].getJogador2() == null){
                    partidas[i].setJogador2(j);System.out.println("registra jogador 2 "+j.getNome()+" "+i);
                    return true;
                }
            }
            
        }
        for(int i=0;i<this.nrPartidas;i++){
            if(partidas[i] == null){
           
                Partida p = new Partida();
                p.setJogador1(j);
                p.setId(geraIdPartida());
                partidas[i] = p;System.out.println("registra jogador 1 "+j.getNome()+" "+i);
                return true;
            }
        }
        return false;
    }
    
    public int identificaJogador(int partida, int idJogador){
        if(this.partidas[partida].getJogador1() != null){
            if(this.partidas[partida].getJogador1().getId() == idJogador)
                return 1;
        }
        if(this.partidas[partida].getJogador2() != null){
            if(this.partidas[partida].getJogador2().getId() == idJogador)
                return 2;
        }
        return 0;
    }

    @Override
    public int registraJogador(String nome) throws RemoteException {
        if(nome ==  null || nome.equals(""))
            return -3;
        if(usuarioCasdastrado(nome)) return -1;
        Jogador jogador = new Jogador();
        jogador.setNome(nome);
        jogador.setId(geraIdJogador());
        if(encontraPartidaLivre(jogador))
            return jogador.getId();
        else
            return -2;
    }

    @Override
    public int encerraPartida(int idjogador) throws RemoteException {
        int partida = encontraPartida(idjogador);
        if(partida > -1){
            this.partidas[partida] = null;
            return 0;
        }
        return -1;
    }

    @Override
    public int temPartida(int idJogador) throws RemoteException {
        int partida = encontraPartida(idJogador);
        if(partida > -1){
            if(this.partidas[partida].getJogador1() == null || this.partidas[partida].getJogador2() == null){
                return 0;
            }
            if(this.partidas[partida].getJogador1().getId() == idJogador){
                this.partidas[partida].preparaJogo();
                
                return 1;
            }else if(this.partidas[partida].getJogador2().getId() == idJogador){
                this.partidas[partida].preparaJogo();
                
                return 2;
            }
        }
       
        return -1;
    }

    @Override
    public String obtemOponente(int idJogador) throws RemoteException {
        int partida = encontraPartida(idJogador);
        if(partida > -1){
            int nrJogador = identificaJogador(partida, idJogador);
            if(nrJogador == 1){
                if(this.partidas[partida].getJogador2() == null){
                    return "";
                }else{
                   return this.partidas[partida].getJogador2().getNome();
                }
            }else{
                if(this.partidas[partida].getJogador1() == null){
                    return "";
                }else{
                   return this.partidas[partida].getJogador1().getNome();
                }
            }
        }
        return "";
    }
    
    

    @Override
    public int ehMinhaVez(int idJogador) throws RemoteException {
        int partida = encontraPartida(idJogador);
        if(temPartida(idJogador) == 0) return -2; //Não há dois jogadores
        
        if(partida > -1){
            int nrJogador = identificaJogador(partida, idJogador);
            if(nrJogador == this.partidas[partida].getVez()){
                return 1; //Sim
            }else{
                return 0; //Não
            }
        }
        return -1;
    }

    @Override
    public int obtemNumCartasBaralho(int idJogador) throws RemoteException {
        if(temPartida(idJogador) == 0) return -2;
        int partida = encontraPartida(idJogador);
        if(this.partidas[partida].getBaralho() != null)
            return this.partidas[partida].getNumCartas();
        return -1;
    }

    @Override
    public int obtemNumCartas(int idJogador) throws RemoteException {
        if(temPartida(idJogador) == 0) return -2;
        int partida = encontraPartida(idJogador);
        int nrJogador = identificaJogador(partida, idJogador);
        if(nrJogador == 1){
            return this.partidas[partida].getJogador1().getCartas().size();
        }
        if(nrJogador == 2){
            return this.partidas[partida].getJogador2().getCartas().size();
        }
        return -1;
    }

    @Override
    public int obtemNumCartasOponente(int idJogador) throws RemoteException {
        if(temPartida(idJogador) == 0) return -2;
        int partida = encontraPartida(idJogador);
        int nrJogador = identificaJogador(partida, idJogador);
        if(nrJogador == 1){
            return this.partidas[partida].getJogador2().getCartas().size();
        }
        if(nrJogador == 2){
            return this.partidas[partida].getJogador1().getCartas().size();
        }
        return -1;
    }

    @Override
    public String mostraMao(int idJogador) throws RemoteException {
        int partida = encontraPartida(idJogador);
        int nrJogador = identificaJogador(partida, idJogador);
        String mao = "";
        ArrayList<Integer> cartasMao;
        if(nrJogador == 1){
            cartasMao = this.partidas[partida].getJogador1().getCartas();
        }else{
            cartasMao = this.partidas[partida].getJogador2().getCartas();
        }
        for(Integer i:cartasMao){
            mao += "|" + dicionarioCartas(i);
        }
        
        return mao;
    }

    @Override
    public String obtemCartaMesa(int idJogador) throws RemoteException {
        int partida = encontraPartida(idJogador);
        if(partida > -1){
            return dicionarioCartas(this.partidas[partida].topoDescarte());
        }
        return "";
    }

    @Override
    public int obtemCorAtiva(int idJogador) throws RemoteException {
        int partida = encontraPartida(idJogador);
        if(partida > -1){
            return this.partidas[partida].getCorAtiva();
        }
        return -1;
    }

    @Override
    public int compraCarta(int idJogador) throws RemoteException {
        int partida = encontraPartida(idJogador);
        if(partida > -1){
            int nrJogador = identificaJogador(partida, idJogador);
            return this.partidas[partida].compraCarta(idJogador);
        }
        return -1;
    }

    @Override
    public int jogaCarta(int idJogador, int indexCarta, int cor) throws RemoteException {
        
        int carta;
        int partida = encontraPartida(idJogador);
        int nrJogador = identificaJogador(partida, idJogador);
        System.out.println("Cor: "+cor);
        if(temPartida(idJogador) == 0) return -2;
//        if(cor < 0 || cor > 3) return -3;
        
        if(this.partidas[partida].getVez() != nrJogador) return -4;
        
        if(nrJogador == 1){
            carta = this.partidas[partida].getJogador1().getCartas().get(indexCarta);
        }else{
            carta = this.partidas[partida].getJogador2().getCartas().get(indexCarta);
        }
        
        if(partida > -1){
            
            int topoDescarte = this.partidas[partida].getTopoDescarte();
            System.out.println("carta "+carta+" Topo "+topoDescarte);
            if(carta >=0 && carta <=24 && topoDescarte >=0 && topoDescarte <=24){ //Azul
                return this.partidas[partida].jogaCarta(indexCarta, cor, nrJogador);
            }else
            if(carta >=25 && carta <=49 && topoDescarte >=25 && topoDescarte <=49){//Amarela 
                return this.partidas[partida].jogaCarta(indexCarta, cor, nrJogador);
            }else
            if(carta >=50 && carta <=74 && topoDescarte >=50 && topoDescarte <=74){//Verde
                return this.partidas[partida].jogaCarta(indexCarta, cor, nrJogador);
            }else
            if(carta >=75 && carta <=99 && topoDescarte >=75 && topoDescarte <=99){//Vermelha
                return this.partidas[partida].jogaCarta(indexCarta, cor, nrJogador);
            }
            
            int soma;
            
            if(carta % 2 == 0)
                soma = -1;
            else
                soma = 1;
            
            if((carta >= 25 && carta <= 49) || (carta >= 75 && carta <= 99)){
                soma = inverte(soma);
            }
            
            if(carta+soma == topoDescarte)
                return this.partidas[partida].jogaCarta(indexCarta, cor, nrJogador);
            
            int cartaAux = carta;
            int proximo;
            if(carta < topoDescarte){
                proximo = 25;
            }else{
                proximo = -25;
            }
            
            while(cartaAux+proximo < 99 && cartaAux+proximo > 0){
                if(cartaAux+25 == topoDescarte || cartaAux+25+soma == topoDescarte){
                    return this.partidas[partida].jogaCarta(indexCarta, cor, nrJogador);
                }
                cartaAux += 25;
                soma = inverte(soma);
            }
           
        }
        return 0;
    }
    
    public int inverte(int s){
        if(s == 1)
            return -1;
        else
            return 1;
    }

    @Override
    public int obtemPontos(int id) throws RemoteException {
        return -1;
    }

    @Override
    public int obtemPontosOponente(int id) throws RemoteException {
        return -1;
    }
    
    
    public String dicionarioCartas(int carta) {

        if (carta < 0 || carta > 107) {
            return null;
        }

        switch (carta) {
            case 0:
                return "0/Az";
            case 1:
                return "1/Az";
            case 2:
                return "1/Az";
            case 3:
                return "2/Az";
            case 4:
                return "2/Az";
            case 5:
                return "3/Az";
            case 6:
                return "3/Az";
            case 7:
                return "4/Az";
            case 8:
                return "4/Az";
            case 9:
                return "5/Az";
            case 10:
                return "5/Az";
            case 11:
                return "6/Az";
            case 12:
                return "6/Az";
            case 13:
                return "7/Az";
            case 14:
                return "7/Az";
            case 15:
                return "8/Az";
            case 16:
                return "8/Az";
            case 17:
                return "9/Az";
            case 18:
                return "9/Az";
            case 19:
                return "Pu/Az";
            case 20:
                return "Pu/Az";
            case 21:
                return "In/Az";
            case 22:
                return "In/Az";
            case 23:
                return "+2/Az";
            case 24:
                return "+2/Az";
            case 25:
                return "0/Am";
            case 26:
                return "1/Am";
            case 27:
                return "1/Am";
            case 28:
                return "2/Am";
            case 29:
                return "2/Am";
            case 30:
                return "3/Am";
            case 31:
                return "3/Am";
            case 32:
                return "4/Am";
            case 33:
                return "4/Am";
            case 34:
                return "5/Am";
            case 35:
                return "5/Am";
            case 36:
                return "6/Am";
            case 37:
                return "6/Am";
            case 38:
                return "7/Am";
            case 39:
                return "7/Am";
            case 40:
                return "8/Am";
            case 41:
                return "8/Am";
            case 42:
                return "9/Am";
            case 43:
                return "9/Am";
            case 44:
                return "Pu/Am";
            case 45:
                return "Pu/Am";
            case 46:
                return "In/Am";
            case 47:
                return "In/Am";
            case 48:
                return "+2/Am";
            case 49:
                return "+2/Am";
            case 50:
                return "0/Vd";
            case 51:
                return "1/Vd";
            case 52:
                return "1/Vd";
            case 53:
                return "2/Vd";
            case 54:
                return "2/Vd";
            case 55:
                return "3/Vd";
            case 56:
                return "3/Vd";
            case 57:
                return "4/Vd";
            case 58:
                return "4/Vd";
            case 59:
                return "5/Vd";
            case 60:
                return "5/Vd";
            case 61:
                return "6/Vd";
            case 62:
                return "6/Vd";
            case 63:
                return "7/Vd";
            case 64:
                return "7/Vd";
            case 65:
                return "8/Vd";
            case 66:
                return "8/Vd";
            case 67:
                return "9/Vd";
            case 68:
                return "9/Vd";
            case 69:
                return "Pu/Vd";
            case 70:
                return "Pu/Vd";
            case 71:
                return "In/Vd";
            case 72:
                return "In/Vd";
            case 73:
                return "+2/Vd";
            case 74:
                return "+2/Vd";
            case 75:
                return "0/Vm";
            case 76:
                return "1/Vm";
            case 77:
                return "1/Vm";
            case 78:
                return "2/Vm";
            case 79:
                return "2/Vm";
            case 80:
                return "3/Vm";
            case 81:
                return "3/Vm";
            case 82:
                return "4/Vm";
            case 83:
                return "4/Vm";
            case 84:
                return "5/Vm";
            case 85:
                return "5/Vm";
            case 86:
                return "6/Vm";
            case 87:
                return "6/Vm";
            case 88:
                return "7/Vm";
            case 89:
                return "7/Vm";
            case 90:
                return "8/Vm";
            case 91:
                return "8/Vm";
            case 92:
                return "9/Vm";
            case 93:
                return "9/Vm";
            case 94:
                return "Pu/Vm";
            case 95:
                return "Pu/Vm";
            case 96:
                return "In/Vm";
            case 97:
                return "In/Vm";
            case 98:
                return "+2/Vm";
            case 99:
                return "+2/Vm";
            case 100:
                return "Cg/*";
            case 101:
                return "Cg/*";
            case 102:
                return "Cg/*";
            case 103:
                return "Cg/*";
            case 104:
                return "C4/*";
            case 105:
                return "C4/*";
            case 106:
                return "C4/*";
            case 107:
                return "C4/*";

        }

        return "";
    }

}
