package br.xtool.core;

/**
 * Classe que gerencia o console.
 * 
 * @author jcruz
 *
 */
public interface Console {

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


}
