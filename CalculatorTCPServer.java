/**
 * @author Suelen Santos
**/

import java.io.*;
import java.net.*;

public class CalculatorTCPServer {

    private int port; 

    public CalculatorTCPServer(int port) {
        this.port = port;
    }

    public void executar() {
        Socket servidor = null;
        ServerSocket serverSock = null;
        BufferedReader receive = null; 

        try {

            serverSock = new ServerSocket(this.port);
            System.out.println("Server is listening ...");
        } catch(IOException e) {
            System.out.println("Nao foi possivel estabelecer conexao Socket do servidor TCP na porta " + this.port + ".\nExcecao: " + e.getMessage());
            System.exit(1);
        }

        try {
    
            while(true) {

                servidor = serverSock.accept(); // socket ativo eh criado
                System.out.println("Nova conexao com o cliente " + servidor.getRemoteSocketAddress() + " estabelecida.");

                receive = new BufferedReader(new InputStreamReader(servidor.getInputStream()));

                String userName = receive.readLine();
                System.out.println("Nome do Cliente: " + userName);

                Worker w = new Worker(servidor, userName);
                w.start();

                System.out.println("Server aguardando nova conexao na porta: " + this.port + " ....");
            }
        } catch(IOException e) {
            System.out.println("Erro de conexao.\nExcecao: " + e.getMessage());
            System.exit(1);
        } catch(Exception e) {
            System.out.println("Determinado erro aconteceu.\nExcecao: " + e.getMessage());
            System.out.println("Caso Cliente tenha entrado com letra, perde-se a conexão do Servidor. Execute novamente!");
            System.exit(1);
        } finally {
            try {
                receive.close();
                if(servidor != null) {
                    servidor.close();
                }
            } catch(IOException e) {
                System.out.println("Encerramento do socket falhou!\nExcecao: " + e.getMessage());
            }
        }
    }

    public static void main(String args[]) {
        try {

            CalculatorTCPServer server = new CalculatorTCPServer(Integer.parseInt(args[0]));
            server.executar();

        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Voce precisa entrar com 1 argumento: Numero da Porta 9031!\nExcecao: " + e.getMessage());
            System.exit(1);
        } catch(NumberFormatException e) {
            System.out.println("Entrada com formato errado!\nExcecao: " + e.getMessage());
            System.exit(1);
        }
    }
}

