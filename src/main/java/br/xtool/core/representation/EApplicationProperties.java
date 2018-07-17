package br.xtool.core.representation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

import br.xtool.core.ConsoleLog;
import strman.Strman;

public class EApplicationProperties {

	private String path;

	private Properties properties;

	private EApplicationProperties(String path) {
		super();
		this.path = path;
	}

	public Optional<String> get(String key) {
		return Optional.ofNullable(this.properties.getProperty(key));
	}

	public void set(String key, String value) {
		if (!this.properties.containsKey(key)) {
			this.properties.setProperty(key, value);
			ConsoleLog.print(ConsoleLog.bold(ConsoleLog.yellow("\t[~] ")), ConsoleLog.purple("Item: "), ConsoleLog.white("application.properties"), ConsoleLog.gray(" -- "),
					ConsoleLog.gray(Strman.surround(key, "Key [", "]")));
		}
	}

	public boolean hasProperty(String key) {
		return this.properties.containsKey(key) && this.get(key).isPresent();
	}

	public void save() {
		try {
			FileWriter fos = new FileWriter(new File(this.path));
			this.properties.store(fos, " ");
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static EApplicationProperties of(String path) {
		if (Files.exists(Paths.get(path))) {
			try {
				EApplicationProperties representation = new EApplicationProperties(path);
				Properties properties = new Properties();
				properties.load(new FileInputStream(new File(path)));
				representation.properties = properties;
				return representation;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		throw new IllegalArgumentException("Não foi possível localizar ou ler o arquivo src/main/resources/application.properties. Verifique se o mesmo existe o contém erros.");

	}
}
