package br.xtool.core.representation.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Properties;

import br.xtool.core.ConsoleLog;
import br.xtool.core.representation.EBootAppProperties;
import strman.Strman;

public class EBootAppPropertiesImpl implements EBootAppProperties {

	private Path path;

	private Properties properties;

	private EBootAppPropertiesImpl(Path path) {
		super();
		this.path = path;
	}

	@Override
	public Optional<String> get(String key) {
		return Optional.ofNullable(this.properties.getProperty(key));
	}

	@Override
	public EBootAppProperties set(String key, String value) {
		if (!this.properties.containsKey(key)) {
			this.properties.setProperty(key, value);
			ConsoleLog.print(ConsoleLog.bold(ConsoleLog.yellow("\t[~] ")), ConsoleLog.purple("Item: "), ConsoleLog.white("application.properties"), ConsoleLog.gray(" -- "),
					ConsoleLog.gray(Strman.surround(key, "Key [", "]")));
		}
		return this;
	}

	@Override
	public boolean hasProperty(String key) {
		return this.properties.containsKey(key) && this.get(key).isPresent();
	}

	@Override
	public void save() {
		try {
			BufferedWriter fos = Files.newBufferedWriter(this.path);
			this.properties.store(fos, " ");
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static EBootAppProperties of(Path path) {
		if (Files.exists(path)) {
			try {
				EBootAppPropertiesImpl representation = new EBootAppPropertiesImpl(path);
				Properties properties = new Properties();
				properties.load(Files.newBufferedReader(path));
				representation.properties = properties;
				return representation;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		throw new IllegalArgumentException("Não foi possível localizar ou ler o arquivo src/main/resources/application.properties. Verifique se o mesmo existe o contém erros.");

	}
}
