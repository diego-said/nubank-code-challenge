# Code Challenge - Capital Gains

Este projeto é uma aplicação Java desenvolvida para processar operações financeiras relacionadas a ganhos de capital. Realiza o cálculo dos preços médios ponderados, trata das operações de compra e venda e determina os impostos aplicáveis ​​com base nas operações realizadas.

## Estrutura do Projeto

A arquitetura segue o padrão de separação de responsabilidades, utilizando classes distintas para entidades, processamento de dados e utilitários. O código está estruturado da seguinte forma:

### Entidades

Representam os objetos do domínio, como:
- **Operation**: Representa uma operação financeira com propriedades como type, unit cost, and quantity.
- **Wallet**: Representa a carteira de um usuário contendo operações, impostos e outros detalhes financeiros.
- **Tax**: Representa o imposto calculado sobre operações financeiras.

### Exceções

Definição de exceções personalizadas para tratamento adequado de erros.
- **OperationFromJSONException**: Exceção lançada quando há um erro ao converter uma string JSON em uma lista de objetos `Operation`.
- **TaxToJSONException**: Exceção lançada quando ocorre um erro ao converter os impostos sobre operações de venda uma string JSON.

### Processamento

- **WalletProcessor**: Contém a lógica principal para processamento das operações de uma carteira, cálculo de preços médios ponderados, tratamento de operações de compra e venda e cálculo de impostos.

### Utilitários

- **OperationUtils**: Classe utilitária para converter strings JSON em objetos `Operation`.
- **TaxUtils**: Classe utilitária para converter uma lista de objetos `Tax` em uma string JSON.
- **WalletUtils**: Classe utilitária para criar um objeto `Wallet` com base em uma lista de operações, com id aletório e as demais propriedades inicializadas com valores padrão.

### Configuração

- **ConfigLoader** e **ConfigNames**: Usado para carregar valores de configuração como valor máximo de operação e percentual de imposto.

### Aplicação principal

- **CapitalGains**: A classe principal que lê a entrada, processa as operações e gera os resultados. Ela usa `WalletProcessor` para processar as operações de compra e venda e calcular impostos.

## Dependências

O projeto faz uso das seguintes bibliotecas e frameworks:
- **Jackson**: Utilizado para processamento de JSON.
- **Lombok**: Reduz a quantidade de código boilerplate.
- **SLF4J**: Utilizado para logar as inforamções.
- **JUnit**: Utilizado para testes unitários.

## Build and Run

### Pré-requisitos

- Java 21
- Gradle 9
- Acesso ao Maven central

### Build

Para construir o projeto, execute:

```sh
./gradlew clean build
```

### Run

Temos que estar na raiz do projeto para rodar a aplicação. Existem duas formas para se executar a aplicação, a primeira consiste em rodar o seguinte comando:

```sh
./gradlew runWithInput
```

Isso fará com que a aplicação seja executada com as operações que estiverem no arquivo `/scenarios/case-all.txt`

A segunda forma para executar a aplicação é gerando seu arquivo .jar, para isso rode o seguinte comando:

```sh
./gradlew fatJar
```

Isso fará com que um arquivo chamado `capital-gains-1.0-SNAPSHOT-all.jar` seja gerado na raiz do projeto. Após isso execute o comando:

```sh
java -jar capital-gains-1.0-SNAPSHOT-all.jar
```

A aplicação será executada em modo CLI e ficará aguardando a lista de operações para serem processadas. Também é possível passar um arquivo para ser processado, execute este comando:

```sh
java -jar capital-gains-1.0-SNAPSHOT-all.jar < ./scenarios/case-all.txt
```

Isso fará com que todas as operações do arquivo `case-all.txt` sejam processadas e a aplicação será finalizada.

## Uso

A aplicação lê as operações financeiras da entrada padrão (stdin) no formato JSON, processa-as e gera os impostos calculados. As operações deverão ser fornecidas no seguinte formato:

```json
[
  {
    "operation": "buy",
    "unit-cost": 10.0,
    "quantity": 100
  },
  {
    "operation": "sell",
    "unit-cost": 15.0,
    "quantity": 50
  }
]
```

## Testes

Para executar os testes unitários, execute:

```sh
./gradlew test
```

## Notas Adicionais

- O uso de exceções personalizadas melhora a identificação de erros e a depuração da aplicação.
- A configuração do projeto com Gradle simplifica a gestão de dependências e automação de tarefas.
- O projeto usa o padrão UTF-8.

## Licença

Este projeto está licenciado sob a licença MIT.
