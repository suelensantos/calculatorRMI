/**
 * @author Suelen Santos
**/

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.net.MalformedURLException;
import java.io.*;

public class CalculatorRMIClient {

    private int operacao;
    private double a, b;
    private String host;

    public CalculatorRMIClient(int oper, double valorA, double valorB, String host) {
        this.operacao = oper;
        this.a = valorA;
        this.b = valorB;
        this.host = host;
    }

    public double run() throws MalformedURLException {

        InterfaceCalculator calculadora = null;

        try {

            calculadora = (InterfaceCalculator) Naming.lookup("//" + this.host + ":8031/calculadora");
            System.out.println("Lookup Ok.");
        } catch(Exception e) {
            System.out.println("Lookup falhou!\nExcecao: " + e.getMessage());
        }

        try {

            if(calculadora != null) {
                while(true) {

                    double resultado = calcular(calculadora, this.operacao, this.a, this.b);
                    return resultado;
                }
            } else {
                System.out.println("Não foi possível encontrar a calculadora RMI!");
                System.out.println("Verifique o servidor RMI e tente novamente.");
            }
        } catch(RemoteException e) {
            System.out.println("Determinado erro remoto aconteceu.\nExcecao: " + e.getMessage());
            System.exit(1);
        } catch(Exception e) {
            System.out.println("Determinado erro aconteceu.\nExcecao: " + e.getMessage());
            System.exit(1);
        }
        return 0;
    }

    private static double calcular(InterfaceCalculator calculator, int operacao, double a, double b) throws RemoteException {
        switch(operacao) {
            case 1:
                return calculator.somar(a, b);
            case 2:
                return calculator.subtrair(a, b);
            case 3:
                return calculator.multiplicar(a, b);
            case 4:
                try {
                   return  calculator.dividir(a, b);
                }
                catch(DivisaoPorZeroException e) {
                    System.out.println("Excecao de divisao por zero capturada ...\nExcecao: " + e.getMessage());
                    return 0;
                }
        }
        return 0;
    }
}
