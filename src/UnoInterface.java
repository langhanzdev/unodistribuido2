import java.rmi.Remote;
import java.rmi.RemoteException;

// Interface remota para o exemplo "Hello, world!"
public interface UnoInterface extends Remote {
	// Metodo invocavel remotamente que retorna a mensagem do objeto remoto
	public String say() throws RemoteException;

	public int registraJogador(String nome) throws RemoteException;
	public int encerraPartida(int id) throws RemoteException;
	public int temPartida(int id) throws RemoteException;
	public String obtemOponente(int id) throws RemoteException;
	public int ehMinhaVez(int id) throws RemoteException;
	public int obtemNumCartasBaralho(int id) throws RemoteException;
	public int obtemNumCartas(int id) throws RemoteException;
	public int obtemNumCartasOponente(int id) throws RemoteException;
	public String mostraMao(int id) throws RemoteException;
	public String obtemCartaMesa(int id) throws RemoteException;
	public int obtemCorAtiva(int id) throws RemoteException;
	public int compraCarta(int id) throws RemoteException;
	public int jogaCarta(int id, int i, int cor) throws RemoteException;
	public int obtemPontos(int id) throws RemoteException;
	public int obtemPontosOponente(int id) throws RemoteException;
}

