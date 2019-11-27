package br.xtool.core;

import java.nio.file.Path;
import java.util.Map;

/**
 * Operação do shell.
 * 
 * @author jcruz
 *
 */
@Deprecated
public interface Shell {

	/**
	 * Roda o comando no caminho especificado.
	 * 
	 * @param path
	 * @param command
	 * @return
	 */
	public int runCmd(Path path, String command);

	/**
	 * Roda o comando no shell no caminho especificado substituindo variáveis.
	 * 
	 * @param path
	 * @param command
	 * @param vars
	 * @return
	 */
	public int runCmd(Path path, String command, Map<String, Object> vars);

}
