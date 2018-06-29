package br.xtool.core.representation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

public class ApplicationPropertiesRepresentation {

	private String path;

	private Properties properties;

	private ApplicationPropertiesRepresentation(String path) {
		super();
		this.path = path;
	}

	public Optional<String> get(String key) {
		return Optional.ofNullable(properties.getProperty(key));
	}

	public void set(String key, String value) {
		if (!this.get(key).isPresent()) {
			properties.setProperty(key, value);
		}
	}

	public void commitUpdates() {
		try {
			FileOutputStream fos = new FileOutputStream(new File(this.path));
			this.properties.store(fos, "");
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Optional<ApplicationPropertiesRepresentation> of(String path) {
		if (Files.exists(Paths.get(path))) {
			try {
				ApplicationPropertiesRepresentation representation = new ApplicationPropertiesRepresentation(path);
				Properties properties = new Properties();
				properties.load(new FileInputStream(new File(path)));
				representation.properties = properties;
				return Optional.of(representation);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Optional.empty();
	}
}
