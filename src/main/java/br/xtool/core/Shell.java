package br.xtool.core;

import java.nio.file.Path;
import java.util.Map;

/**
 * Operação do shell.
 * 
 * @author jcruz
 *
 */
public interface Shell {

	/**
	 * Roda o comando no shell.
	 * 
	 * @param command
	 * @return
	 */
	public int runCmd(String command);

	/**
	 * Roda o comando no shell substituindo variáveis no comando.
	 * 
	 * @param command
	 * @param vars
	 * @return
	 */
	public int runCmd(String command, Map<String, Object> vars);

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

	/**
	 * Executa um arquivo shell do diretório src/main/resources/scripts
	 * 
	 * @param path
	 * @return
	 */
	public int runScript(String name);

}
