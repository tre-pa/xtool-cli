package br.xtool.implementation.representation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.xtool.representation.angular.NgPackageJsonRepresentation;
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
public class NgPackageJsonRepresentationImpl implements NgPackageJsonRepresentation {

	private Path path;

	private String name;

	private String version;

	private Map<String, String> dependencies = new HashMap<>();

	private Map<String, String> devDependencies = new HashMap<>();

	private NgPackageJsonRepresentationImpl() {
		super();
	}

	public static Optional<NgPackageJsonRepresentation> of(Path path) {
		if (Files.exists(path)) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				NgPackageJsonRepresentationImpl ngPackage = mapper.readValue(Files.newBufferedReader(path), NgPackageJsonRepresentationImpl.class);
				return Optional.of(ngPackage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Optional.empty();
	}

}
