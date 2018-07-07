package br.xtool.core;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class FS {

	@Autowired
	private VelocityEngine vEngine;

	@Autowired
	private WorkContext workContext;

	/**
	 * Realiza uma cópia com substituição de variáveis no template.
	 * 
	 * @param templatePath
	 *            Caminho relativo do arquivo de template
	 * @param relativeDestination
	 *            Caminho relativo do arquivo final
	 * @param vars
	 *            Mapa com variáveis para substituição no template
	 * @throws IOException
	 */
	public void copy(String templatePath, String relativeDestination, Map<String, Object> vars) throws IOException {
		this.copy(templatePath, relativeDestination, vars, false);
	}

	/**
	 * 
	 * @param templatePath
	 * @param relativeDestination
	 * @param vars
	 * @param binary
	 * @throws IOException
	 */
	public void copy(String templatePath, String relativeDestination, Map<String, Object> vars, boolean binary) throws IOException {
		VelocityContext vContext = new VelocityContext(vars);
		templatePath = this.inlineTemplate(templatePath, vars);
		relativeDestination = this.inlineTemplate(relativeDestination, vars);
		String finalDestination = FilenameUtils.concat(workContext.getDirectory().getPath(), relativeDestination);
		if (!Files.exists(Paths.get(finalDestination))) {
			FileUtils.forceMkdirParent(new File(finalDestination));
			if (binary) {
				FileUtils.copyInputStreamToFile(new ClassPathResource(String.format("templates/%s", templatePath)).getInputStream(), new File(finalDestination));
				Log.print(Log.bold(Log.green("\t[+] ")), Log.purple("File: "), Log.white(relativeDestination));
				return;
			}
			Template t = vEngine.getTemplate(String.format("templates/%s", templatePath), "UTF-8");
			FileWriterWithEncoding writer = new FileWriterWithEncoding(finalDestination, "UTF-8");
			t.merge(vContext, writer);
			writer.flush();
			writer.close();
			Log.print(Log.bold(Log.green("\t[+] ")), Log.purple("File: "), Log.white(relativeDestination));
			return;
		}
		Log.print(Log.bold(Log.gray("\t[!] ")), Log.purple("File: "), Log.gray(relativeDestination), Log.yellow(" -- Skip "));
	}

	/**
	 * Realiza substituição inline do template.
	 * 
	 * @param inlineTemplate
	 * @param vars
	 * @return
	 */
	public String inlineTemplate(String inlineTemplate, Map<String, Object> vars) {
		VelocityContext vContext = new VelocityContext(vars);
		StringWriter stringWriter = new StringWriter();
		vEngine.evaluate(vContext, stringWriter, new String(), inlineTemplate);
		return stringWriter.toString();
	}

	/**
	 * Cria um diretório vazio.
	 * 
	 * @param relativeDestination
	 * @param vars
	 */
	public void createEmptyPath(String relativeDestination, Map<String, Object> vars) {
		try {
			relativeDestination = this.inlineTemplate(relativeDestination, vars);
			String finalDestination = FilenameUtils.concat(workContext.getDirectory().getPath(), relativeDestination);
			if (!Files.exists(Paths.get(finalDestination))) {
				FileUtils.forceMkdir(new File(finalDestination));
				FileUtils.touch(new File(FilenameUtils.concat(finalDestination, ".gitkeep")));
				Log.print(Log.bold(Log.green("\t[+] ")), Log.purple("Path: "), Log.white(relativeDestination));
				return;
			}
			Log.print(Log.bold(Log.gray("\t[!] ")), Log.purple("Path: "), Log.gray(relativeDestination), Log.yellow(" -- Skip "));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
