package br.xtool.core;


import lombok.Getter;
import lombok.Setter;
import picocli.CommandLine;

/**
 * Classe base para todos os comandos Xtool.
 */
public abstract class AbstractCommand implements Runnable {

    /**
     * Envia o mainCommandLine para inclusao de subcomandos.
     * @param mainCommandLine
     */
    public void setup(CommandLine mainCommandLine) {
        String commandName = this.getClass().getAnnotation(CommandLine.Command.class).name();
        mainCommandLine.addSubcommand(commandName, this);
    }
}