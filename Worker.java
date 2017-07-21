/**
 * @author Suelen Santos
 * Cada conexao vai ser tratada por um Worker
**/

import java.io.*;
import java.net.*;

public class Worker extends Thread {

    private Socket sock;
    private String clientName;

    public Worker(Socket s, String userName) { // recebe o socket ativo no construtor
        this.sock = s;
        this.clientName = userName;
    }

	public void run() {
        BufferedReader receive = null;
        PrintWriter send = null;

        System.out.println("Worker - nova thread iniciando ...");
        String hostServerRMI = Balancer.getInstance().getNextServerIP(); // política de encaminhamento
        System.out.println("Cliente " + this.clientName + " iniciando registro no servidor da calculadora... " + hostServerRMI); 

        try {

            while(true) {
				
                receive = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
                send = new PrintWriter(this.sock.getOutputStream(), true);

                // recebe operacao do cliente
                int operacao =  Integer.parseInt(receive.readLine());

                if(operacao == 5) {
                    System.out.println("Conexao encerrada pelo cliente " + this.clientName + "!");
                    break;
                }

                double a = Double.parseDouble(receive.readLine()); // recebe primeiro operando do cliente
                double b = Double.parseDouble(receive.readLine()); // recebe segundo operando do cliente

                CalculatorRMIClient calc = new CalculatorRMIClient(operacao, a, b, hostServerRMI);
                double resultado = calc.run();
                System.out.println("Calculadora remota registrada!\n" + new java.util.Date().toString());

                send.println(resultado); // envia resultado para o Cliente
			}
        } catch(ConnectException e) {
            System.out.println("Erro de conexao.\nExcecao: " + e.getMessage());
            System.exit(1);
        } catch(IOException e) {
            System.out.println("Erro de I/O.\nExcecao: " + e.getMessage());
            System.exit(1);
        } catch(Exception e) {
            System.out.println("Determinado erro aconteceu.\nExcecao: " + e.getMessage());
            System.exit(1);
        } finally {
            try {
                receive.close();
                send.close();
                if(sock != null) {
                    sock.close();
                }
            } catch(IOException e) {
                System.out.println("Encerramento do socket falhou!\nExcecao: " + e.getMessage());
            }
        }
	}
}

