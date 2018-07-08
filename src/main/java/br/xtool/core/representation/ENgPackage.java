package br.xtool.core.representation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Representação do package.json de um projeto Angular.
 * 
 * @author jcruz
 *
 */
@Getter
@Setter
@ToString(of = { "name" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class ENgPackage {

	private String path;

	private String name;

	private String version;

	private Map<String, String> dependencies;

	private Map<String, String> devDependencies;

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
