package br.xtool.core;

import org.springframework.stereotype.Component;

@Component
public class Log {
	private final String ANSI_RESET = "\u001B[0m";
	private final String ANSI_BLACK = "\u001B[30m";
	private final String ANSI_RED = "\u001B[31m";
	private final String ANSI_GREEN = "\u001B[32m";
	private final String ANSI_YELLOW = "\u001B[33m";
	private final String ANSI_BLUE = "\u001B[34m";
	private final String ANSI_PURPLE = "\u001B[35m";
	private final String ANSI_CYAN = "\u001B[36m";
	private final String ANSI_GRAY = "\u001B[37m";
	private final String ANSI_WHITE = "\u001B[37;1m";

	public String black(String text) {
		return ANSI_BLACK + text + ANSI_RESET;
	}

	public String red(String text) {
		return ANSI_RED + text + ANSI_RESET;
	}

	public String green(String text) {
		return ANSI_GREEN + text + ANSI_RESET;
	}

	public String yellow(String text) {
		return ANSI_YELLOW + text + ANSI_RESET;
	}

	public String blue(String text) {
		return ANSI_BLUE + text + ANSI_RESET;
	}

	public String purple(String text) {
		return ANSI_PURPLE + text + ANSI_RESET;
	}

	public String cyan(String text) {
		return ANSI_CYAN + text + ANSI_RESET;
	}

	public String gray(String text) {
		return ANSI_GRAY + text + ANSI_RESET;
	}

	public String white(String text) {
		return ANSI_WHITE + text + ANSI_RESET;
	}

	public void print(String text) {
		System.out.println(text);
	}
}
