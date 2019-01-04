package br.xtool.core.representation.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.xtool.core.representation.NgPackageRepresentation;
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
public class ENgPackageImpl implements NgPackageRepresentation {

	private Path path;

	private String name;

	private String version;

	private Map<String, String> dependencies = new HashMap<>();

	private Map<String, String> devDependencies = new HashMap<>();

	private ENgPackageImpl() {
		super();
	}

	public static Optional<NgPackageRepresentation> of(Path path) {
		if (Files.exists(path)) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				ENgPackageImpl ngPackage = mapper.readValue(Files.newBufferedReader(path), ENgPackageImpl.class);
				return Optional.of(ngPackage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Optional.empty();
	}

}
