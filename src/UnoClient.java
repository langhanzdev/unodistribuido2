
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

class UnoClient {

    private static UnoInterface uno;
    
    private Jogador jogador;

    public static void main(String[] args) {
        UnoClient uc = new UnoClient();
        
        
        if (args.length != 1) {
            System.err.println("UnoClient <server host>\n  ERRO: Nome de dominio ou IP nao fornecido!");
            System.exit(1);
        }
        System.setProperty("java.security.policy", "UnoClient.policy");
        try {
            uno = (UnoInterface) Naming.lookup("//" + args[0] + "/Uno");

            uc.registra();
        } catch (Exception e) {
            System.out.println("UnoClient failed:");
            e.printStackTrace();
        }
    }

    public void registra() throws RemoteException {
        System.out.println("Jogo de Uno!");
        System.out.println("Digite o seu nome: ");
        Scanner entrada = new Scanner (System.in);
        String nome = entrada.nextLine();
        int id = uno.registraJogador(nome);
        if(id == -3) System.out.println("Nome informado é inválido.");
        if(id == -2) System.out.println("Não há partidas disponíveis.");
        if(id == -1) System.out.println("Nome já está cadastrado.");
        this.jogador = new Jogador(nome, id);
        System.out.println("ID:"+id);
    }

}
