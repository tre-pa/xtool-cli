package br.xtool.core.representation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

/**
 * Representação do package.json de um projeto Angular.
 * 
 * @author jcruz
 *
 */
@Getter
@Setter
public class ENgPackage {

	private String path;

	private String name;

	private ENgPackage() {
		super();
	}

	public static Optional<ENgPackage> of(String path) {
		if (Files.exists(Paths.get(path))) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				ENgPackage ngPackage = mapper.readValue(new File(path), ENgPackage.class);
				return Optional.of(ngPackage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Optional.empty();
	}

}
