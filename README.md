# Ferramenta xtool-cli

Ferramenta de Produtividade para Desenvolvimento de Aplicações Angular e Spring Boot. O projeto é baseado no Spring Shell e é 100% java.

# Utilização

A ferrament `xtool-cli` é um projeto Spring Boot utilizando a biblioteca Spring Shell. A ferramenta é baseada em linha de comando.


**1.** Clonar o projeto 

```sh
$ git clone git@git.tre-pa.jus.br:sds/support/xtool-cli.git
$ cd xtool-cli
```

**2.** Para rodar o projeto se faz necessária a instalação de uma JDK, utulizando o comando abaixo:

```sh
$ sudo dnf install java-1.8.0-openjdk.x86_64
```

**3.** Rodar o projeto com comando `run.sh` indicando o diretório de workspace (*na maioria dos casos ~/git/*):

```sh
$ ./run.sh ~/git/
```

**4.** Após rodar a aplicação o prompt da ferramenta será exibido.

```sh
xtool ~

## Help do Sistema

Com o comando `help` é possível visualizar os comandos disponíveis:  

```

**1.** Para visualizar os comando disponéveis digitar no prompt o comando `help`:

```sh
xtool ~ help
```

**2.** Para visualizar o help de um comando específico digitar: 

```sh
xtool ~ help NOME_COMANDO
```


## Criando projeto Spring Boot

Para criar um novo projeto Spring Boot 1.5.x digitar o comando abaixo:

```sh
xtool ~ new:springboot NOME_PROJETO
```

## Criando projeto Angular 5.x

```sh
xtool ~ new:angular NOME_PROJETO
```
