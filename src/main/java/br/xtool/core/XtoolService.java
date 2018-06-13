package br.xtool.core;

import java.nio.file.Files;
import java.nio.file.Paths;

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
			this.workingDirectory = dir;
		}
		throw new IllegalArgumentException("Diretório inválido. " + dir);
	}

}
