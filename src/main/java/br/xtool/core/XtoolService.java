package br.xtool.core;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.Getter;

/**
 * Bean com as informações de contexto da aplicação.
 * 
 * @author jcruz
 *
 */
@Service
@Getter
public class XtoolService {

	/**
	 * Diretório atual
	 */
	private String workingDirectory;

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
		if (!StringUtils.isEmpty(this.workingDirectory)) {
			return FilenameUtils.getBaseName(this.workingDirectory);
		}
		return "";
	}

	public boolean hasWorkingDirectory() {
		return !StringUtils.isEmpty(this.workingDirectory);
	}

}
