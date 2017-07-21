/**
 * @author Suelen Santos
**/

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.io.*;

public class CalculatorRMIServer {

    public static void main(String[] args) throws MalformedURLException {

        try {

            String endereco = "localhost";
            int porta = 8031;

            if(args.length > 0) {
                endereco = args[0];
                porta = Integer.parseInt(args[1]);
            }

            ImplementationRMICalculator calculadora = new ImplementationRMICalculator();
            String address = "rmi://" + endereco + ":" + porta + "/" + "calculadora";

            System.out.println("Registrando calculadora remota...");
            System.out.println("Endereco da calculadora remota: " + address.toString());
            Naming.rebind(address, calculadora); // rmiregistry deve estar rodando
            System.out.println("Calculadora remota registrada!");

        } catch(RemoteException e) {
            System.out.println("Erro remoto no RMIServer.\nExcecao: " + e.getMessage());
            System.exit(1);
        } catch(IOException e) {
            System.out.println("Erro de conexao!\nExcecao: " + e.getMessage());
            System.exit(1);
        } catch(Exception e) {
            System.out.println("Determinado erro aconteceu.\nExcecao: " + e.getMessage());
            System.exit(1);
        } 

        System.out.println("Objeto RMI registrado e aguardando requisicoes...");
    }
}
