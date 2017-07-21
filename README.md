# calculatorSocket&RMI

### Sistema Distribuído - Calculadora com Socket & RMI

**Obs.: Para este trabalho de faculdade, foram disponibilizados duas máquinas (11 e 16) para realização do sistema.**

_Sistema para utilizar o recurso da calculadora, serviço proveniente do Servidor RMI._

_Neste sistema é feito uma conexão via camada de Socket com o protocolo de transporte TCP, para que dados sejam encaminhados assim que o cliente estabelece uma requisição para o serviço da calculadora. Tal comunicação tem como retorno o resultado dessa requisição._

### Como rodar o programa:

- Compilar as classes: 
    ` javac *.java `

- Criar os stubs rodando o comando nas respectivas máquinas (11 e 16): 
    ` rmic ImplementationRMICalculator `

- Levantar o registry nas respectivas máquinas (11 e 16): 
    ` rmiregistry 8031 & ` 

- Levantar o servidor da calculadora RMI nas respectivas máquinas (11 e 16): 
    ` ./srv11.sh `
    ` ./srv16.sh `

    **Obs.: É preciso dar permissão total a esses dois arquivos executáveis com o comando:**
        ` chmod 777 <nomeDoArquivo> `

- Levantar o servidor TCP na máquina 11 ou na máquina 16: 
    ` java <nameClassServer> <porta> ` 
    _Exemplo:_
        ` java CalculatorTCPServer 9031 `

- Rodar o cliente (podendo ser mais de um): 
    ` java <nameClassCliente> <host> <porta> <nomeDoCliente> `
    _Exemplo:_
        ` java CalculatorTCPClient 152.92.236.11 9031 Suelen `

    **Obs.: Independente de qual máquina esteja, o host precisa ser igual ao host do servidor TCP levantado!**

