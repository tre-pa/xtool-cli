package br.xtool.core.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.SneakyThrows;

/**
 * Json Helper.
 *
 * @author jcruz
 *
 */
public class JsonHelper {

	private static ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		mapper.configure(JsonParser.Feature.ALLOW_TRAILING_COMMA, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// mapper.setDefaultPrettyPrinter(new DefaultPrettyPrinter()
		// .withObjectIndenter(new DefaultIndenter("", ""))
		// .withArrayIndenter(new DefaultIndenter()));
	}

	@SneakyThrows
	public static <T> T deserialize(String content, Class<T> clazz) {
		return mapper.readValue(content, clazz);
	}

	@SneakyThrows
	public static <T> T deserialize(String content, TypeReference<T> clazz) {
		return mapper.readValue(content, clazz);
	}

	@SneakyThrows
	public static <T> String serialize(T obj) {
		return mapper.setDefaultPrettyPrinter(new DefaultPrettyPrinter()).writeValueAsString(obj);
	}

	@SneakyThrows
	public static <T> String serialize(T obj, PrettyPrinter pp) {
		return mapper.setDefaultPrettyPrinter(pp).writeValueAsString(obj);
	}

}
