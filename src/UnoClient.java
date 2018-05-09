
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

            uc.iniciaJogo();
        } catch (Exception e) {
            System.out.println("UnoClient failed:");
            e.printStackTrace();
        }
    }
    public void iniciaJogo() throws RemoteException, InterruptedException{
        System.out.println("Jogo de Uno!");
        registra();
        //Tem partida
        System.out.println("Aguardando partida...");
        int temPartida;
        do{
            temPartida = uno.temPartida(jogador.getId());
            switch(temPartida){
                case -2:
                    System.out.println("Tempo de espera esgotado!");
                    uno.encerraPartida(jogador.getId());
                    break;
                case -1:
                    System.out.println("Erro ao buscar partida.");
                    break;
                case 0:
                    System.out.println("Ainda não há partida. Aguarde...");
                    break;
                case 1:
                    System.out.println("A partida vai começar! Você inicia jogando.");
                    break;
                case 2:
                    System.out.println("A partida vai começar! O oponente começa jogando.");
                    break;
            }
            Thread.sleep(2000);
        }while(temPartida != 1 && temPartida != 2);
        
        Scanner entrada = new Scanner (System.in);
        int jogou= -5;
        int ehMinhaVez = 0;
        
        String nomeOponente = uno.obtemOponente(jogador.getId());
        System.out.println("Seu oponente é "+nomeOponente);
        do{
            System.out.println("---------------------------------------------");
            int nrCartasBaralho = uno.obtemNumCartasBaralho(jogador.getId());
            if(nrCartasBaralho == -2) System.out.println("Ainda não tem oponente!");
            if(nrCartasBaralho == -1) System.out.println("Erro ao buscar número de cartas do baralho.");

            int nrCartas = uno.obtemNumCartas(jogador.getId());
            if(nrCartas == -2) System.out.println("Ainda não oponente!");
            if(nrCartas == -1) System.out.println("Erro ao buscar número de cartas da mão.");

            int nrCartasOponente = uno.obtemNumCartasOponente(jogador.getId());
            if(nrCartasOponente == -2) System.out.println("Ainda não oponente!");
            if(nrCartasOponente == -1) System.out.println("Erro ao buscar número de cartas do oponente.");

            
            System.out.println("Cartas do baralho: "+nrCartasBaralho);
            System.out.println("Cartas do oponente: "+nrCartasOponente);
            System.out.println("Você tem "+nrCartas+" cartas.");
            String mao = uno.mostraMao(jogador.getId());
            if(mao.equals(""))
                System.out.println("Erro ao buscar cartas da mão.");
            else
                System.out.println("Suas Cartas: "+mao);

            String cartaMesa = uno.obtemCartaMesa(jogador.getId());
            if(cartaMesa.equals("")){
                System.out.println("Erro ao buscar carta da mesa.");
            }
            System.out.println("Mesa: "+cartaMesa);

            ehMinhaVez = uno.ehMinhaVez(jogador.getId());

            switch(ehMinhaVez){
                case -2:
                    System.out.println("erro: ainda não há 2 jogadores registrados na partida.");
                    break;
                case -1:
                    System.out.println("Erro ao verificar se era minha vez.");
                    break;
                case 0:
                    System.out.println("É a vez do oponente.");
                    break;
                case 1:
                    System.out.println("É a sua vez.");
                    break;
                case 2:
                    System.out.println("Você ganhou!!!");
                    break;
                case 3:
                    System.out.println("Você perdeu.");
                    break;
                case 4:
                    System.out.println("Houve empate.");
                    break;
                case 5:
                    System.out.println("Você venceu por WO!!");
                    break;
                case 6:
                    System.out.println("Você perdeu por WO!!");
                    break;
            }

            //Joga carta
            int opcao;


            if(ehMinhaVez == 1){
               do{
                do{
                    System.out.println("Escolha uma carta de 0 a "+(nrCartas-1)+" para jogar ou 0 para comprar. ");

                    opcao = entrada.nextInt();
                    if(opcao < 0 || opcao > nrCartas){
                        System.out.println("Opção invalida.");
                    }
                }while(opcao < 0 || opcao > nrCartas);

                String[] splitMao = mao.split("|");
                int cor = 0;
                if(splitMao[opcao].equals("Cg/*") || splitMao[opcao].equals("C4/*")){
                    do{
                        System.out.println("Escola uma cor: ");
                        System.out.println("0 - Azul ");
                        System.out.println("1 - Amarelo ");
                        System.out.println("2 - Verde ");
                        System.out.println("3 - Vermelho");
                        cor = entrada.nextInt();
                        if(cor < 0 || cor > 3)
                            System.out.println("Cor invalida.");
                    }while(cor < 0 || cor > 3);
                }

                jogou = uno.jogaCarta(jogador.getId(), opcao, cor);
                System.out.println("Joga carta "+jogou);
                if(jogou == 0){
                    System.out.println("Jogada inválida: carta inválida.");
                }
            }while(jogou != 1);

            }else{
                System.out.println("Aguardando adversário jogar...");
                Thread.sleep(2000);
                jogou = -5;
            }
        }while(ehMinhaVez < 2);
        uno.encerraPartida(jogador.getId());
        System.out.println("Fim.");
        
    }

    public void registra() throws RemoteException{
        
        int id = -1;
        do{
            System.out.println("Digite o seu nome: ");
            Scanner entrada = new Scanner (System.in);
            String nome = entrada.nextLine();
            id = uno.registraJogador(nome);

            if(id == -1){
                System.out.println("Nome já está cadastrado.");
            }else if(id == -2){
                if(id == -2) System.out.println("Não há partidas disponíveis.");
            }else if(id == -3){
                System.out.println("Nome informado é inválido.");
            }else if(id > -1){
                this.jogador = new Jogador(nome, id);
                System.out.println("Registrado com sucesso, seu ID: "+id);
            }
        }while(id < 0);
    }
    

}
