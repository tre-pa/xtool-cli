package br.xtool.core;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

/**
 * Bean com as informações de contexto da aplicação.
 * 
 * @author jcruz
 *
 */
@Service
@Getter
@Setter
public class XtoolService {

	/**
	 * Diretório atual
	 */
	private String currentDirectory;
}
