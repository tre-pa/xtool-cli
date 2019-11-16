package br.xtool.core;

import br.xtool.representation.ProjectRepresentation;

import java.nio.file.Path;

/**
 * Classe que gerencia o console.
 *
 * @author jcruz
 */
public interface Console {

    enum Level {
        NORMAL, DEBUG;
    }

    /**
     * Define o nivel de saida no console.
     *
     * @param level
     */
    void setLevel(Level level);

    /**
     * Retorna o nivel de saida no console.
     *
     * @return
     */
    Level getLevel();

    /**
     * Limpa a tela do console.
     */
    void clearScreen();

    /**
     * Imprime uma mensagem no console.
     *
     * @param msg String com o texto a ser impresso. Aceita definições de estilo jansi.
     * @see https://github.com/fusesource/jansi
     */
    void println(String msg);


    /**
     * Imprime uma mensagem no console. Suporte a substituição via String.format.
     *
     * @param args
     */
    void println(String msg, Object... args);

    /**
     * Imprime uma mensagem de debug.
     *
     * @param msg
     */
    void debug(String msg, Object... args);

    /**
     * Registra o diretório no prompt.
     *
     * @param path
     */
    void registerPromptPath(Path path);

    /**
     * Registra o projeto no prompt.
     *
     * @param project
     */
    void registerPromptProject(ProjectRepresentation project);
}
