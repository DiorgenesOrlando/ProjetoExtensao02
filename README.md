# Sobre o Projeto
Este Projeto consiste em uma API Rest, na pasta Back-End - em Java, uma aplicação Front-End, utilizando Node.js + Express, consumindo esta api, e uma aplicação Kotlin 
para Android que serve como coletor de dados.
O objetivo do projeto é permitir a rastreabilidade no consumo de combustiveis por equipamento, permitindo que estes dados gerem informações importantes como : qual equipamento consome mais combustivel,( ou departamento), quantidade consumida por periodo, alem de fazer o rateio do consumo entre os departamentos responsaveis pelo equipamento.
O sistema gera um QR Code para cada equipamento cadastrado, e quando ele é abastecido, o abastecedor le este QR Code e informa a quantidade abastecida.

## Exemplo de Histórico
![histórico_1](https://github.com/DiorgenesOrlando/ProjetoExtensao02/blob/main/assets/exemplo_historico_consumo.png)
Nesta tela podemos ver informações como ORIGEM DO COMBUSTIVEL, DESTINO, QUANTIDADE E DATA E HORA do abastecimento, na opção Download, é possivel baixar estes dados
em formato .XLS (Excel).

# Tecnologias Utilizadas

## Back End
  - Java
  - Spring Web / Spring MVC
  - JPA / Spring Data JPA
  - Maven
## Front End
-HTML / CSS / JS / TS
- Bootstap
- Node.js
- Express
## Mobile
- Kotlin
- JetPack Compose
- Retrofit e Moshi


 
    
