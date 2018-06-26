package br.xtool.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.xtool.core.representation.SpringBootProjectRepresentation;
import lombok.Getter;

/**
 * Bean com as informações de contexto da aplicação.
 * 
 * @author jcruz
 *
 */
@Service
public class PathService {

	/**
	 * Diretório atual
	 */
	private String workingDirectory;

	@PostConstruct
	private void init() {
	}

	public String getWorkingDirectory() {
		if(StringUtils.isEmpty(workingDirectory)) {
			String gitHome = System.getProperty("user.home").concat("/git");
			if (Files.isDirectory(Paths.get(gitHome))) {
				try {
					Files.createDirectories(Paths.get(gitHome));
					this.workingDirectory = gitHome;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return workingDirectory;
	}

	/**
	 * Altera o diretório de trabalho.
	 * 
	 * @param dir
	 */
	public void changeWorkingDirectory(String dir) {
		if (Files.exists(Paths.get(dir))) {
			this.workingDirectory = FilenameUtils.normalizeNoEndSeparator(dir);
			return;
		}
		throw new IllegalArgumentException("Diretório inválido. " + dir);
	}

	public String getWorkingDirectoryBaseName() {
		if (!StringUtils.isEmpty(this.getWorkingDirectory())) {
			return FilenameUtils.getBaseName(this.workingDirectory);
		}
		return "";
	}

	public boolean hasWorkingDirectory() {
		return !StringUtils.isEmpty(this.getWorkingDirectory());
	}

	public Optional<SpringBootProjectRepresentation> getSpringBootProject() {
		return SpringBootProjectRepresentation.of(this.getWorkingDirectory());
	}

	public int exec(String command) {
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.inheritIO();
			builder.command("sh", "-c", command);
			builder.directory(new File(this.getWorkingDirectory()));
			Process process = builder.start();
			return process.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return 1;
	}

}
