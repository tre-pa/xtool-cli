package br.xtool.core.util;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Inflector {
	private final Collection<String> uncountable = new ArrayList<String>();

	private final Collection<Rule> singular = new ArrayList<Rule>();

	private final Map<String, String> irregular = new HashMap<String, String>();

	private final Collection<Rule> plural = new ArrayList<Rule>();

	private static final ThreadLocal<Map<String, Inflector>> INFLECTORS = new ThreadLocal<Map<String, Inflector>>() {
		private final Map<String, Inflector> map = new HashMap<String, Inflector>();
		{
			final Inflector inflector = new Inflector();

			inflector.addSingular("^([a-zA-z]*)ais$/i", "\1al");
			inflector.addSingular("/^([a-zA-z]*)eis$/i", "\1el");
			inflector.addSingular("/^([a-zA-z]*)ois$/i", "\1ol");
			inflector.addSingular("/^([a-zA-z]*)uis$/i", "\1ul");

			inflector.addPlural("r$", "res");
			inflector.addPlural("ão$", "ões");
			inflector.addPlural("ao$", "oes");
			inflector.addPlural("vil$", "vis");
			inflector.addPlural("il$", "eis");
			inflector.addPlural("l$", "is");
			inflector.addPlural("z$", "zes");
			inflector.addPlural("m$", "ns");
			inflector.addPlural("$", "s");
			inflector.addSingular("res$", "r");
			inflector.addSingular("ões$", "ão");
			inflector.addSingular("oes$", "ao");
			inflector.addSingular("beis$", "bil");
			inflector.addSingular("vis$", "vil");
			inflector.addSingular("is$", "l");
			inflector.addSingular("zes$", "z");
			inflector.addSingular("ns$", "m");
			inflector.addSingular("s$", "");
			this.map.put("pt_BR", inflector);
		}

		@Override
		public Map<String, Inflector> get() {
			return this.map;
		}
	};

	public String singularize(final String orig) {
		if (this.uncountable.contains(orig)) {
			return orig;
		}
		for (final Entry<String, String> entry : this.irregular.entrySet()) {
			if (entry.getValue().equals(orig)) {
				return entry.getKey();
			}
		}
		for (final Rule r : this.singular) {
			final Matcher m = r.regex.matcher(orig);
			if (m.find()) {
				return m.replaceAll(r.replacement);
			}
		}
		return orig;
	}

	public String replaceUpperedBy(final String orig, final String to) {
		final String uncapitalized = uncapitalize(orig);
		final Pattern pattern = Pattern.compile("[A-Z]");
		final Matcher matcher = pattern.matcher(uncapitalized);
		final StringBuilder builder = new StringBuilder();
		Integer start = 0;
		while (matcher.find()) {
			final Integer end = matcher.end() - 1;
			final String part = uncapitalized.substring(start, end);
			builder.append(part);
			builder.append(to);
			builder.append(Character.toLowerCase(uncapitalized.charAt(end)));
			start = matcher.end();
		}
		builder.append(uncapitalized.substring(start));
		return builder.toString();
	}

	public String dasherize(final String orig) {
		return replaceUpperedBy(orig, "-");
	}

	public String underscore(final String orig) {
		return replaceUpperedBy(orig, "_");
	}

	public String pluralize(final String orig) {
		if (this.uncountable.contains(orig)) {
			return orig;
		}
		if (this.irregular.containsKey(orig)) {
			return this.irregular.get(orig);
		}
		for (final Rule r : this.plural) {
			final Matcher m = r.regex.matcher(orig);
			if (m.find()) {
				return m.replaceAll(r.replacement);
			}
		}
		return orig;
	}

	public String capitalize(final String orig) {
		return Character.toUpperCase(orig.charAt(0)) + orig.substring(1);
	}

	public String uncapitalize(final String orig) {
		return Character.toLowerCase(orig.charAt(0)) + orig.substring(1);
	}

	public void addPlural(final String regex, final String replacement) {
		this.plural.add(new Rule(regex, replacement));
	}

	public void addSingular(final String regex, final String replacement) {
		this.singular.add(new Rule(regex, replacement));
	}

	public void addIrregular(final String orig, final String replacement) {
		this.irregular.put(orig, replacement);
	}

	public void addUncountable(final String[] words) {
		this.uncountable.addAll(Arrays.asList(words));
	}

	public void addUncountable(final String word) {
		this.uncountable.add(word);
	}

	private static class Rule {
		private final Pattern regex;

		private final String replacement;

		public Rule(final String regex, final String replacement) {
			this.regex = compile(regex, CASE_INSENSITIVE);
			this.replacement = replacement;
		}
	}

	public static void addLocale(final String locale, Inflector inflector) {
		INFLECTORS.get().put(locale, inflector);
	}

	public static Inflector getInstance() {
		String locale = "pt_BR";
		final Inflector inflector;
		if (INFLECTORS.get().containsKey(locale)) {
			inflector = INFLECTORS.get().get(locale);
		} else {
			inflector = new Inflector();
			INFLECTORS.get().put(locale, inflector);
		}
		return inflector;
	}
}
