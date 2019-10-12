package br.xtool.core;

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
     * Imprime uma mensagem de debug.
     *
     * @param msg
     */
    void debug(String msg);


}
