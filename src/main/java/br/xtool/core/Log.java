package br.xtool.core;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

public class Log {
	
	public static String bold(String text) {
		return "\u001B[1m" + text + "\u001B[0m";
	}
	
	public static String black(String text) {
		return "\u001B[30m" + text + "\u001B[0m";
	}

	public static String red(String text) {
		return "\u001B[31m" + text + "\u001B[0m";
	}

	public static String green(String text) {
		return "\u001B[92m" + text + "\u001B[0m";
	}

	public static String yellow(String text) {
		return "\u001B[93m" + text + "\u001B[0m";
	}

	public static String blue(String text) {
		return "\u001B[34m" + text + "\u001B[0m";
	}

	public static String purple(String text) {
		return "\u001B[95m" + text + "\u001B[0m";
	}

	public static String cyan(String text) {
		return "\u001B[96m" + text + "\u001B[0m";
	}

	public static String gray(String text) {
		return "\u001B[37m" + text + "\u001B[0m";
	}

	public static String white(String text) {
		return "\u001B[37;1m" + text + "\u001B[0m";
	}

	public static void print(String... text) {
		System.out.println(StringUtils.join(text));
	}
}
