/**
 * @author Suelen Santos
**/

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class CalculatorTCPClient {

    private String host, userName;
    private int port;

    public CalculatorTCPClient(String host, int port, String userName) {
        this.host = host;
        this.port = port;
        this.userName = userName;
    }

    public void executar() {
        Socket cliente = null;
        BufferedReader receive = null;
        PrintWriter send = null;

        try {

            try {

                cliente = new Socket(this.host, this.port); // cria socket
                System.out.println(this.userName + " conectado ao servidor " + cliente.getRemoteSocketAddress());
            } catch(IOException e) {
                System.out.println("Erro de conexao com servidor TCP: " + e.getMessage());
                System.exit(1);
            }   

            receive = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            send = new PrintWriter(cliente.getOutputStream(), true);

            // envia nome do cliente para o servidor
            send.println(this.userName);

            while(true) {
                int operacao = obterOperacao();

                if(operacao == 5) {

                    send.println(operacao); // envia objeto para o servidor a operacao
                    System.out.println("Operacao encerrada!");
                    break;
                }

                if(operacao > 5) {

                    System.out.println("Somente valores de 1 a 5!");
                    operacao = obterOperacao();
                }

                System.out.print("Operador A: "); // sem quebra de linha
                double a = new Scanner(System.in).nextDouble();
                System.out.print("Operador B: "); // sem quebra de linha
                double b = new Scanner(System.in).nextDouble();

                send.println(operacao); // envia objeto para o servidor a operacao
                send.println(a); // envia objeto para o servidor operador A
                send.println(b); // envia objeto para o servidor operador B

                double resultado = Double.parseDouble(receive.readLine()); // recebe do servidor o resultado da operacao
                System.out.println ("Resultado da operacao: " + resultado);
            }
        } catch(UnknownHostException e) {
            System.out.println("Servidor desconhecido!!\nExcecao: " + e.getMessage());
            System.exit(1);
        } catch(EOFException e) {
            System.out.println("Nao ha mais dados de entrada.\nExcecao: " + e.getMessage());
            System.exit(1);
        } catch(Exception e) {
            System.out.println("Determinado erro aconteceu.\nExcecao: " + e.getMessage());
            System.out.println("Caso tenha entrado com letra, perde-se a conexão do Cliente. Execute novamente!");
            System.exit(1);
        } finally {
            try {
                receive.close();
                send.close();
                if(cliente != null) {
                    cliente.close();
                }
            } catch(IOException e) {
                System.out.println("Encerramento do socket falhou!\nExcecao: " + e.getMessage());
            }
        }
    }

    public static int obterOperacao() throws IOException {
        int entrada = 0;

        try {

            System.out.println("\nQual operacao deseja realizar?");
            System.out.println("1 - Somar / 2 - Subtrair / 3 - Multiplicar / 4 - Dividir / 5 - Sair do programa");
            System.out.print("Operacao: "); // sem quebra de linha
            entrada = new Scanner(System.in).nextInt();

            if(entrada > 5) {

                throw new EntradaDeOperacaoInvalidaException();
            }
        } catch(EntradaDeOperacaoInvalidaException e){

            System.out.println("\nExcecao: " + e);
        }

        return entrada;
    }

    public static void main(String args[]) {
        try {

            CalculatorTCPClient client = new CalculatorTCPClient(args[0], Integer.parseInt(args[1]), args[2]);
            client.executar();

        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Voce precisa entrar com 3 argumentos!\nIP, Porta (9031) e seu nome!\nExcecao: " + e.getMessage());
            System.exit(1);
        } catch(NumberFormatException e) {
            System.out.println("Alguma entrada com formato errado!");
            System.out.println("Observe que sao 3 entradas (IP, Porta, Nome).\nExcecao: " + e.getMessage());
            System.exit(1);
        }
    }
}
