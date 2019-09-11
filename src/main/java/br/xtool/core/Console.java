package br.xtool.core;

import org.fusesource.jansi.Ansi;

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

	/**
	 * Imprime uma mensagem no console.
	 * 
	 * @param ansi Objeto jansi com definição do texto e estilo.
	 * @see https://github.com/fusesource/jansi
	 */
	void println(Ansi ansi);

}
