package br.xtool.core.utils;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

public class JSONUtils {

	/**
	 * Realiza o parse de um arquivo json.
	 * 
	 * @param file
	 * @return
	 */
	public static Optional<DocumentContext> readFromFile(File file) {
		try {
			return Optional.of(JsonPath.parse(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

}
