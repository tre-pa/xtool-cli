package br.xtool.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.Pair;

/**
 * String helpers.
 * 
 * @author jcruz
 *
 */
public class StringHelper {

	/**
	 * Retorna o índice (inicio e fim) do pattern na String.
	 * 
	 * @param pattern
	 * @param s
	 * @return
	 */
	public static Pair<Integer, Integer> indexOfPattern(Pattern pattern, String s) {
		Matcher matcher = pattern.matcher(s);
		return matcher.find() ? Pair.of(matcher.start(), matcher.end()) : Pair.of(-1, -1);
	}

	/**
	 * 
	 * Retorna o índice (inicio e fim) da ocorreência do primeiro array na string.
	 * 
	 * @param s
	 * @return
	 */
	public static Pair<Integer, Integer> indexOfFirstArray(String s) {
		int i = 0;
		int startArrayIdx = -1;
		int endArrayIdx = -1;
		int qtdArrays = 0;

		do {
			char c = s.charAt(i);
			if (c == '[') {
				if (qtdArrays == 0) {
					startArrayIdx = i;
				}
				qtdArrays++;
			} else if (c == ']') {
				if (qtdArrays == 1) {
					endArrayIdx = i + 1;
				}
				qtdArrays--;
			}
			i++;
		} while (qtdArrays > 0);

		return Pair.of(startArrayIdx, endArrayIdx);
	}
}
