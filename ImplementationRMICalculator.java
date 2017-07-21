/**
 * @author Suelen Santos
**/

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ImplementationRMICalculator extends UnicastRemoteObject implements InterfaceCalculator {

    private static final long serialVersionUID = 1L;

    public ImplementationRMICalculator() throws RemoteException {
        super();
    }

    public double somar(double a, double b) throws RemoteException {

        System.out.println("Calculadora remota acionada - somando");
        return a + b;
    }

    public double subtrair(double a, double b) throws RemoteException {

        System.out.println("Calculadora remota acionada - subtraindo");
        return a - b;
    }

    public double multiplicar(double a, double b) throws RemoteException {

        System.out.println("Calculadora remota acionada - multiplicando");
        return a * b;
    }

    public double dividir(double a, double b) throws RemoteException, DivisaoPorZeroException {
        if(((int) b) == 0) {
            System.out.println("Calculadora remota acionada - OOOpppss! Divisao por zero");
            throw new DivisaoPorZeroException("Divisao por zero - Tente novamente!");
        }
        System.out.println("Calculadora remota acionada - divisao");
        return a / b;
    }
}
