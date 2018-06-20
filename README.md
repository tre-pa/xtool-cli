# Ferramenta xtool-cli

Ferramenta de Produtividade para Desenvolvimento de Aplicações Angular e Spring Boot. O projeto é baseado no Spring Shell e é 100% java.

# Utilização

A ferrament `xtool-cli` é um projeto Spring Boot utilizando a biblioteca Spring Shell. A ferramenta é baseada em linha de comando.


**1.** Clonar o projeto 

```sh
$ git clone git@git.tre-pa.jus.br:sds/support/xtool-cli.git
$ cd xtool-cli
```

**2.** Rodar o projeto com comando `run.sh`:

```sh
$ ./run.sh
```

**3.** Após rodar a aplicação o prompt da ferramenta será exibido. O **Diretório de trabalho padrão** é `$HOME/git`

```sh
xtool@git >

## Help do Sistema

Com o comando `help` é possível visualizar os comandos disponíveis:  

```

**1.** Para visualizar os comando disponéveis digitar no prompt o comando `help`:

```sh
xtool@git > help
```

**2.** Para visualizar o help de um comando específico digitar: 

```sh
xtool@git > help NOME_COMANDO
```


## Criando projeto Spring Boot

Para criar um novo projeto Spring Boot 1.5.x digitar o comando abaixo:

```sh
xtool@git > new-springboot-project NOME_PROJETO
```